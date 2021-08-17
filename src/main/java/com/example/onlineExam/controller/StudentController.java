package com.example.onlineExam.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onlineExam.dao.ExamsDAO;
import com.example.onlineExam.dao.UsersDAO;
import com.example.onlineExam.entities.Exams;
import com.example.onlineExam.entities.Questions;
import com.example.onlineExam.entities.Users;

@Controller
public class StudentController {
	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private ExamsDAO examsDAO;
	
	@RequestMapping(value = {"","/"},method = RequestMethod.GET)
	public String hello(HttpServletRequest request) {
		Users user = (Users) request.getSession().getAttribute("student");
		if (user == null) {
			return "user/index";
		}
		return "user/home";
	}

	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}
	@RequestMapping("/loginProcessing")
	public String loginProcessing(Model model,@RequestParam("username")String username,
			@RequestParam("password")String password,HttpServletRequest request) {
		Users users = usersDAO.findByUserName(username);
		if (users != null) {
			if (users.getPassword().equalsIgnoreCase(password)) {
				HttpSession session = request.getSession();
				if(users.getRole().equals("ROLE_ADMIN") || users.getRole().equals("ROLE_TEACHER")) {
					session.setAttribute("user", users);
					session.setAttribute("ROLE", users.getRole());
					return "redirect:/admin/";
				}
				session.setAttribute("student", users);
				return "redirect:/";
			}else {
				model.addAttribute("err", "Mật khẩu không đúng!");
				return "user/login";
			}
		}
		model.addAttribute("err", "Tài khoản không đúng!");
		return "user/login";
	}
	
	@RequestMapping(value = {"/logout"})
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
	@RequestMapping(value = {"/search"})
	public String search() {
		return "user/search";
	}
	@RequestMapping(value = {"/make_exam/{id}"})
	public String make_exam(@PathVariable("id")Integer id,Model model,HttpServletRequest request) {
		Users user = (Users) request.getSession().getAttribute("student");
		if (user == null) {
			return "redirect:/";
		}
		Exams exams = examsDAO.getById(id);
		if (exams == null) {
			model.addAttribute("err", "Không tìm thấy đề!");
			return "user/home";
		}
		Collection<Questions> listQuestions = exams.getListQuestions();
		model.addAttribute("listQuestions", listQuestions);
		model.addAttribute("examId", id);
		return "user/make_exam";
	}
	@RequestMapping(value = {"/err404"})
	public String err404() {
		return "redirect:/err404";
	}
	
	
}
