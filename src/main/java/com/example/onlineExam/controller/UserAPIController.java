package com.example.onlineExam.controller;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.onlineExam.dao.UsersDAO;
import com.example.onlineExam.entities.PaperDTO;
import com.example.onlineExam.entities.Papers;
import com.example.onlineExam.entities.Users;
import com.example.onlineExam.exporter.ResultExcelExporter;

@RestController
@RequestMapping(value = "/api/user")
public class UserAPIController {

	@Autowired
	private UsersDAO repo;

	@Autowired
	private JavaMailSender sender;

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getListUser(@PathVariable("id") Integer id, HttpServletRequest request) {
		Users users = repo.getById(id);
		if (users == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(users);
	}

	@PostMapping("/add/{role}")
	public ResponseEntity<?> add(@PathVariable("role") String role, @RequestBody Users user,
			HttpServletRequest request) {
		String adminUrl = "admin";
		String teacherUrl = "teacher";
		String studentUrl = "student";
		if (role.toLowerCase().contains(adminUrl.toLowerCase())) {
			user.setRole("ROLE_ADMIN");
		} else if (role.toLowerCase().contains(teacherUrl.toLowerCase())) {
			user.setRole("ROLE_TEACHER");
		} else if (role.toLowerCase().contains(studentUrl.toLowerCase())) {
			user.setRole("ROLE_STUDENT");
		}
		user.setEnable(true);

		boolean b = repo.addEntity(user);
		if (b) {
			try {
				String contentToSend = "<h3><b>Xin chào</b> <i>" + user.getFullName() + "</i></h3>"
						+ "<p>Tài khoản <b>Learn.</b> của bạn vừa được tạo</p>" + "<p>Tài khoản : " + user.getUsername()
						+ "</p>" + "<p>Mật khẩu : " + user.getPassword() + "</p>";

				MimeMessage msg = sender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true);
				messageHelper.setTo(user.getEmail());
				messageHelper.setSubject("Tài khoản đăng nhập Learn.");
				msg.setContent(contentToSend, "text/html; charset=utf-8");
				this.sender.send(msg);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/edit")
	public ResponseEntity<?> edit(@RequestBody Users user, HttpServletRequest request) {
		Users byId = repo.getById(user.getU_Id());
		if (byId == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		user.setEnable(byId.isEnable());
		user.setRole(byId.getRole());
		boolean b = repo.updateEntity(user);
		if (b) {
			try {
				String contentToSend = "<h3><b>Xin chào</b> <i>" + user.getFullName() + "</i></h3>"
						+ "<p>Tài khoản <b>Learn.</b> của bạn vừa được cập nhật</p>" + "<p>Tài khoản : "
						+ user.getUsername() + "</p>" + "<p>Mật khẩu : " + user.getPassword() + "</p>";

				MimeMessage msg = sender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true);
				messageHelper.setTo(user.getEmail());
				messageHelper.setSubject("Tài khoản đăng nhập Learn.");
				msg.setContent(contentToSend, "text/html; charset=utf-8");
				this.sender.send(msg);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<?> edit(@PathVariable("id") Integer id, HttpServletRequest request) {
		Users userFromRequest = repo.getById(id);
		if (userFromRequest != null) {
			Users userFromSession = (Users) request.getSession().getAttribute("user");
			if (userFromRequest.getU_Id() != userFromSession.getU_Id()) {
				if (userFromRequest.isEnable()) {
					boolean b = repo.deleteEntity(id);
					if (b) {
						return new ResponseEntity(HttpStatus.OK);
					} else {
						return new ResponseEntity(HttpStatus.NOT_FOUND);
					}
				} else {
					userFromRequest.setEnable(true);
					boolean b = repo.updateEntity(userFromRequest);
					if (b) {
						return new ResponseEntity(HttpStatus.OK);
					} else {
						return new ResponseEntity(HttpStatus.NOT_FOUND);
					}
				}
			}
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/search/{email}")
	public ResponseEntity<?> search(@PathVariable("email") String email) {
		Users byEmail = repo.findByEmail(email);
		List<Papers> listPapers = null;
		List<PaperDTO> data = new ArrayList<>();
		if (byEmail != null) {
			listPapers = new ArrayList<>(byEmail.getListPapers());
			for (Papers pp : listPapers) {
				String subName = pp.getExam().getSubject().getSubName();
				String examName = pp.getExam().getExamName();
				Integer totalMarks = pp.getExam().getTotalMarks();
				boolean isPassed = pp.getIsPassed();
				Integer earnedMark = pp.getEarnedMark();
				PaperDTO paperDTO = new PaperDTO(subName, examName, totalMarks, earnedMark, isPassed);
				data.add(paperDTO);
			}
			return ResponseEntity.ok(data);
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/export")
	public void export(@ModelAttribute("txtEmail") String email, HttpServletResponse response) throws IOException {
		Users byEmail = repo.findByEmail(email);
		List<Papers> listPapers = null;
		List<PaperDTO> data = new ArrayList<>();
		if (byEmail != null) {
			listPapers = new ArrayList<>(byEmail.getListPapers());
			for (Papers pp : listPapers) {
				String subName = pp.getExam().getSubject().getSubName();
				String examName = pp.getExam().getExamName();
				Integer totalMarks = pp.getExam().getTotalMarks();
				boolean isPassed = pp.getIsPassed();
				Integer earnedMark = pp.getEarnedMark();
				PaperDTO paperDTO = new PaperDTO(subName, examName, totalMarks, earnedMark, isPassed);
				data.add(paperDTO);
			}
			response.setContentType("application/octet-stream");
			String normalName = normalizeName(byEmail.getFullName());
			String headerKey = "Content-Disposition";
			String headerValue = "attachment;filename=ketqua_" + normalName + ".xlsx";
			response.setHeader(headerKey, headerValue);

			ResultExcelExporter exporter = new ResultExcelExporter(data);

			exporter.export(response);
		}
	}

	private String normalizeName(String name) {
		try {
			String temp = Normalizer.normalize(name, Normalizer.Form.NFD);
			Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
			return pattern.matcher(temp).replaceAll("").replace(" ", "_");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
