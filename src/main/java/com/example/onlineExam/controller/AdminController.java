package com.example.onlineExam.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlineExam.dao.ExamsDAO;
import com.example.onlineExam.dao.QuestionsDAO;
import com.example.onlineExam.dao.SubjectsDAO;
import com.example.onlineExam.dao.UsersDAO;
import com.example.onlineExam.entities.Exams;
import com.example.onlineExam.entities.Questions;
import com.example.onlineExam.entities.Subjects;
import com.example.onlineExam.entities.Users;

@Controller
@RequestMapping(value = { "/admin" })
public class AdminController {

	@Autowired
	private UsersDAO usersDAO;
@Autowired
private QuestionsDAO questDAO;	
@Autowired
private ExamsDAO examDAO;	
@Autowired
private SubjectsDAO subjectsDAO;	
	
	@RequestMapping(value = { "", "/", "/home" })
	public String hello(Model model, HttpServletRequest request) {
		List<Users> list = usersDAO.getAll();
		String role = (String) request.getSession().getAttribute("ROLE");
		if (role != null) {
			if (role.equalsIgnoreCase("ROLE_ADMIN") || role.equalsIgnoreCase("ROLE_TEACHER")) {
				int stdNumb = 0;
				int tchNumb = 0;
				for (Users users : list) {
					if (users.getRole().equalsIgnoreCase("ROLE_TEACHER")) {
						tchNumb++;
					} else if (users.getRole().equalsIgnoreCase("ROLE_STUDENT")) {
						stdNumb++;
					}
				}
				model.addAttribute("students", stdNumb);
				model.addAttribute("teachers", tchNumb);
				return "admin/index";
			}
			return "admin/login";
		}
		return "admin/login";
	}

	@RequestMapping(value = { "/login" })
	public String login(Model model) {
		return "admin/login";
	}

	@RequestMapping(value = { "/admin_profile" })
	public String profile(Model model, HttpServletRequest request) {
		Users user = (Users) request.getSession().getAttribute("user");
		model.addAttribute("user", user);
		return "admin/admin_profile";
	}

	@RequestMapping(value = { "/update_profile/{id}" })
	public String update_profile(Model model, @PathVariable("id") Integer id, @Valid Users user, BindingResult result,
			HttpServletRequest request) {
		if (user.getPassword().isBlank() || user.getPassword().isEmpty()) {
			Users uById = usersDAO.getById(id);
			user.setPassword(uById.getPassword());
		}
		boolean b = usersDAO.updateEntity(user);

		if (b) {
			Users newUsers = usersDAO.getById(id);
			model.addAttribute("user", newUsers);
			model.addAttribute("suc", "Cập nhật thành công!");
			request.getSession().setAttribute("user", newUsers);
			return "admin/admin_profile";
		}
		model.addAttribute("user", user);
		model.addAttribute("err", "Cập nhật thất bại! (tài khoản hoặc địa chỉ email đã được sử dụng)");
		return "admin/admin_profile";

	}

	@RequestMapping(value = { "/loginProcessing" }, method = RequestMethod.POST)
	public String login(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password, HttpServletRequest request) {
		Users users = usersDAO.findByUserName(username);
		if (users != null) {
			if (users.getPassword().equalsIgnoreCase(password)) {
				if (users.isEnable()) {
					HttpSession session = request.getSession();
					session.setAttribute("user", users);
					session.setAttribute("ROLE", users.getRole());
					return "redirect:/admin/";
				} else {
					model.addAttribute("err", "Đăng nhất bại");
					return "admin/login";
				}
			} else {
				model.addAttribute("err", "Sai mật khẩu");
				return "admin/login";
			}
		}
		model.addAttribute("err", "Tài khoản không đúng");
		return "admin/login";
	}

	@RequestMapping(value = { "/logout" })
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/admin/login";
	}

	@RequestMapping(value = "/list_admin")
	public String viewListAdmin(HttpServletRequest request) {
		String role = (String) request.getSession().getAttribute("ROLE");
		if (role != null) {
			if (role.equalsIgnoreCase("ROLE_ADMIN") || role.equalsIgnoreCase("ROLE_TEACHER")) {
				 request.getSession().setAttribute("listUsers",null);
				 return "redirect:/admin/list/admin/1";
			}
		}
		return "redirect:/admin/login";
	}

	@RequestMapping(value = "/list_teacher")
	public String viewListTeacher(HttpServletRequest request) {
		String role = (String) request.getSession().getAttribute("ROLE");
		if (role != null) {
			if (role.equalsIgnoreCase("ROLE_ADMIN") || role.equalsIgnoreCase("ROLE_TEACHER")) {
				 request.getSession().setAttribute("listUsers",null);
				 return "redirect:/admin/list/teacher/1";
			}
		}
		return "redirect:/admin/login";
	}

	@RequestMapping(value = "/list_student")
	public String viewListStudent(HttpServletRequest request) {
		String role = (String) request.getSession().getAttribute("ROLE");
		if (role != null) {
			if (role.equalsIgnoreCase("ROLE_ADMIN") || role.equalsIgnoreCase("ROLE_TEACHER")) {
				 request.getSession().setAttribute("listUsers",null);
				return "redirect:/admin/list/student/1";
			}
		}
		return "redirect:/admin/login";
	}
	
	@GetMapping("/list/{role}/{pageNumb}")
	public String getListUser(@PathVariable("role") String role,@Param("keyword") String keyword ,
			@PathVariable("pageNumb") Integer pageNumb, HttpServletRequest request,Model model) {
		int pageSize = 10;
		List<Users> data ;
		if (keyword != null) {
			data = new ArrayList<>(usersDAO.getByRole(role,keyword));
		}else {
			data = new ArrayList<>(usersDAO.getByRole(role));
		}
		Users users = (Users) request.getSession().getAttribute("user");
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listUsers");
		if (pages == null) {
			pages = new PagedListHolder<>(data);
			pages.setPageSize(pageSize);
		} else {
			if (keyword == null) {
				final int goToPage = pageNumb - 1;
				if (goToPage <= pages.getPageCount() && goToPage >= 0) {
					pages.setPage(goToPage);
				}
			}
			data.remove(users);
			model.addAttribute("keyword", keyword);
			pages = new PagedListHolder<>(data);
			pages.setPageSize(pageSize);
		}
		request.getSession().setAttribute("listUsers", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - data.size());
		int end = pages.getPageCount();
		int totalPage = pages.getPageCount();
		String baseUrl = "/admin/list/" + role+"/";
		
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("maxSize",data.size());
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		model.addAttribute("current", current);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("list", pages);
		return "admin/list_"+role;
	}

	@RequestMapping("/list_subject")
	public String viewListSubject(HttpServletRequest request) {
		String role = (String) request.getSession().getAttribute("ROLE");
		if (role != null) {
			if (role.equalsIgnoreCase("ROLE_ADMIN") || role.equalsIgnoreCase("ROLE_TEACHER")) {
				request.getSession().setAttribute("listSubs", null);
				return "redirect:/admin/subs/1";
			}
		}
		return "redirect:/admin/login";
	}
	
	@RequestMapping("/subs/{pageNumb}")
	public String pageSubs(@Param("keyword") String keyword ,
			@PathVariable("pageNumb") Integer pageNumb, HttpServletRequest request,Model model) {
		int pageSize = 10;
		List<Subjects> data;
		if (keyword != null) {
			data = new ArrayList<>(subjectsDAO.getAll(keyword));
		}else {
			data = new ArrayList<>(subjectsDAO.getAll());
		}
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listSubs");
		if (pages == null) {
			pages = new PagedListHolder<>(data);
			pages.setPageSize(pageSize);
		}else {
			if (keyword == null) {
				final int goToPage = pageNumb - 1;
				if (goToPage <= pages.getPageCount() && goToPage >= 0) {
					pages.setPage(goToPage);
				}
			}
			model.addAttribute("keyword", keyword);
			pages = new PagedListHolder<>(data);
			pages.setPageSize(pageSize);
		}
		request.getSession().setAttribute("listSubs", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - data.size());
		int end = pages.getPageCount();
		int totalPage = pages.getPageCount();
		String baseUrl = "/admin/subs/";
		
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("maxSize",data.size());
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		model.addAttribute("current", current);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("list", pages);
		
		return "admin/list_subject";
	}

	@RequestMapping("/list_exam")
	public String viewListExam(HttpServletRequest request) {
		String role = (String) request.getSession().getAttribute("ROLE");
		if (role != null) {
			if (role.equalsIgnoreCase("ROLE_ADMIN") || role.equalsIgnoreCase("ROLE_TEACHER")) {
				return "admin/list_exam";
			}
		}
		return "redirect:/admin/login";
	}

	@RequestMapping("/questions/{id}")
	public String viewQuestions(@PathVariable("id")Integer id,Model model,HttpServletRequest request) {
		String role = (String) request.getSession().getAttribute("ROLE");
		if (role != null) {
			if (role.equalsIgnoreCase("ROLE_ADMIN") || role.equalsIgnoreCase("ROLE_TEACHER")) {
				List<Questions> questions = questDAO.getAllByExamId(id);
				int currentMark= 0;
				for(Questions quest : questions) {
					currentMark += quest.getQuestMark();
				};
				Exams exams = examDAO.getById(id);
				
				model.addAttribute("questions",questions);
				model.addAttribute("maxMark",exams.getTotalMarks());
				model.addAttribute("currentMark",currentMark);
				model.addAttribute("examId",id);
				return "/admin/question";
			}
		}
		return "redirect:/admin/login";
	}
}
