package com.example.onlineExam.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.onlineExam.dao.ExamsDAO;
import com.example.onlineExam.dao.PapersDAO;
import com.example.onlineExam.dao.QuestionsDAO;
import com.example.onlineExam.entities.Exams;
import com.example.onlineExam.entities.Papers;
import com.example.onlineExam.entities.QuestionDTO;
import com.example.onlineExam.entities.Questions;
import com.example.onlineExam.entities.Users;

@RestController
@RequestMapping("/api/quest")
public class QuestionAPIController {

	@Autowired
	private QuestionsDAO repo;
	@Autowired
	private ExamsDAO examRepo;

	@Autowired
	private PapersDAO papersDAO;

	@PostMapping("/addOrEdit/{id}")
	private ResponseEntity<?> add(@PathVariable("id") Integer id, @RequestBody Questions quest) {
		Exams exams = examRepo.getById(id);
		if (exams != null) {
			quest.setExamOwn(exams);
			Questions questions = repo.getById(quest.getQuest_Id());
			if (questions != null) {
				boolean b = repo.updateEntity(quest);
				if (b) {
					return new ResponseEntity(HttpStatus.OK);
				} else {
					return new ResponseEntity(HttpStatus.NOT_FOUND);
				}
			} else {
				boolean b = repo.addEntity(quest);
				if (b) {
					return new ResponseEntity(HttpStatus.OK);
				} else {
					return new ResponseEntity(HttpStatus.NOT_FOUND);
				}
			}
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Questions> getById(@PathVariable("id") Integer id) {
		Questions questions = repo.getById(id);
		if (questions == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(questions);
	}
	
	@GetMapping("/list/{eid}/{qid}")
	public ResponseEntity<?> getByExam(@PathVariable("eid") Integer eid,@PathVariable("qid") Integer qid,Model model) {
		Exams exams = examRepo.getById(eid);
		List<Questions> listQuestions = (List<Questions>) exams.getListQuestions();
		boolean isLast = false;
		if (qid != 0) {
			Questions byId = repo.getById(qid);
			int indexOf = 0;
			for (Questions q : listQuestions) {
				if (q.getQuest_Id() == byId.getQuest_Id()) {
					indexOf = listQuestions.indexOf(q);
				}
			}
			Questions questions = listQuestions.get(indexOf+1);
			if (questions!=null) {
				if(listQuestions.indexOf(questions) == (listQuestions.size() - 1)) {
					isLast = true;
					QuestionDTO qDTO = new QuestionDTO(questions, isLast);
					return ResponseEntity.ok(qDTO);
				}
				return ResponseEntity.ok(new QuestionDTO(questions, isLast));
			}
		}
		Questions questions = listQuestions.get(0);
		int indexOf = listQuestions.indexOf(questions);
		if (indexOf == (listQuestions.size()-1)) {
			isLast=true;
		}
		return ResponseEntity.ok(new QuestionDTO(questions,isLast));
	}
	@PostMapping("/answer/{id}")
	public ResponseEntity<?> answer(@RequestBody List<Questions> lstQ,
			@PathVariable("id") Integer eid,
			HttpServletRequest request) {
		Exams ex = examRepo.getById(eid);
		int totalMark = 0;
		boolean isPass = false;
		Users student = (Users) request.getSession().getAttribute("student");
		if (lstQ != null) {
			for (Questions q : lstQ) {
				Questions byId = repo.getById(q.getQuest_Id());
				if (byId != null) {
					if (q.getAnswer().equals(byId.getAnswer())) {
						totalMark += byId.getQuestMark();
					}
				}
			}
			if (ex.getTotalMarks() < totalMark) {
				totalMark = ex.getTotalMarks();
			}
			if (ex.getPassMarks()<= totalMark) {
				isPass = true;
			}
			Papers p = new Papers();
			p.setEarnedMark(totalMark);
			p.setIsPassed(isPass);
			p.setExam(ex);
			p.setUser(student);
			boolean b = papersDAO.addEntity(p);
			if (b) {
				return ResponseEntity.ok(p);
			}else {
				return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
			}
		}
		Papers p = new Papers();
		p.setEarnedMark(totalMark);
		p.setIsPassed(isPass);
		p.setExam(ex);
		p.setUser(student);
		boolean b = papersDAO.addEntity(p);
		if (b) {
			return ResponseEntity.ok(p);
		}else {
			return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
		}
	}
	
	
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<Questions> delete(@PathVariable("id") Integer id) {
		Questions questions = repo.getById(id);
		if (questions != null) {
			boolean b = repo.deleteEntity(id);
			if (b) {
				return new ResponseEntity(HttpStatus.OK);
			} else {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
}
