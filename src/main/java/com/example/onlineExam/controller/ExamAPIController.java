package com.example.onlineExam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.onlineExam.dao.ExamsDAO;
import com.example.onlineExam.dao.SubjectsDAO;
import com.example.onlineExam.entities.Exams;
import com.example.onlineExam.entities.Subjects;

@RestController
@RequestMapping("/api/exam")
public class ExamAPIController {

	@Autowired
	private ExamsDAO repo;
	@Autowired
	private SubjectsDAO subRepo;

	@PostMapping("/add/{id}")
	public ResponseEntity<?> add(@PathVariable("id") Integer id, @RequestBody Exams exam, HttpServletRequest request) {
		Subjects subjects = subRepo.getById(id);
		if (subjects == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		Exams exams = repo.getById(exam.getExam_Id());
		if (exams == null) {
			exam.setSubject(subjects);
			exam.setEnable(true);
			exam.setE_status(true);
			boolean b = repo.addEntity(exam);
			if (b) {
				return new ResponseEntity(HttpStatus.OK);
			} else {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}else {
			exam.setSubject(subjects);
			exam.setEnable(true);
			exam.setE_status(true);
			exam.setExam_Id(exams.getExam_Id());
			boolean b = repo.updateEntity(exam);
			if (b) {
				return new ResponseEntity(HttpStatus.OK);
			} else {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Integer id) {
		Exams exams = repo.getById(id);
		if (exams == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(exams);
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
		Exams exams = repo.getById(id);
		if (exams != null) {
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
