package com.example.onlineExam.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

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
import com.example.onlineExam.dao.PapersDAO;
import com.example.onlineExam.dao.SubjectsDAO;
import com.example.onlineExam.entities.Exams;
import com.example.onlineExam.entities.Papers;
import com.example.onlineExam.entities.Subjects;

@RestController
@RequestMapping("/api/subs")
public class SubjectAPIController {
	@Autowired
	private SubjectsDAO repo;
	@Autowired
	private ExamsDAO examRepo;
	@Autowired
	private PapersDAO papersDAO;

	@GetMapping("/list")
	public ResponseEntity<List<Subjects>> getAll() {
		List<Subjects> list = repo.getAll();
		if (list.isEmpty() || list == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Subjects>>(list, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Integer id) {
		Subjects subjects = repo.getById(id);
		if (subjects == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(subjects);
	}

	@GetMapping("/fetch/{subName}")
	public ResponseEntity<?> fetchBySubject(@PathVariable("subName") String name) {
		Subjects subjects = repo.findByName(name);
		List<Exams> data = null;
		if (subjects != null) {
			data = new ArrayList<>(subjects.getListExams());
			if (data != null) {
				Iterator<Exams> iterator = data.iterator();
				while (iterator.hasNext()) {
					Exams exams = iterator.next();
					if (exams.isE_status()) {
						Date deadline = exams.getDeadline();
						Date date = new Date();
						if (date.after(deadline)) {
							exams.setEnable(false);
							boolean b = examRepo.updateEntity(exams);
							if (!b) {
								return new ResponseEntity(HttpStatus.NOT_FOUND);
							}
						}
					}
				}
				data.sort(Comparator.comparing(Exams::getDeadline).reversed());
				return ResponseEntity.ok(data);
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/fetch/{subName}/{stdId}")
	public ResponseEntity<?> fetchToStudent(@PathVariable("subName") String name, @PathVariable("stdId") Integer id) {
		Subjects subjects = repo.findByName(name);
		List<Exams> data = null;
		List<Exams> toRemove = new ArrayList<>();
		if (subjects != null) {
			data = new ArrayList<>(subjects.getListExams());
			if (data != null) {
				Iterator<Exams> iterator = data.iterator();
				while (iterator.hasNext()) {
					Exams exams = iterator.next();
					if (exams.getListQuestions().size() > 0) {
						if (exams.isE_status()) {
							Date deadline = exams.getDeadline();
							Date date = new Date();
							if (date.after(deadline)) {
								exams.setEnable(false);
								boolean b = examRepo.updateEntity(exams);
								if (!b) {
									return new ResponseEntity(HttpStatus.NOT_FOUND);
								} else {
									toRemove.add(exams);
								}
							}

							List<Papers> listPapers = new ArrayList<>(exams.getListPapers());
							for (Papers pp : listPapers) {
								if (pp.getUser().getU_Id() != id) {
									if (!exams.isEnable()) {
										toRemove.add(exams);
									}
								} else {
									toRemove.add(exams);
								}
							}
						} else {
							toRemove.add(exams);
						}
					}
				}
				data.removeAll(toRemove);
				data.sort(Comparator.comparing(Exams::getDeadline));
				return ResponseEntity.ok(data);
			}
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Subjects sub) {
		Subjects findByName = repo.findByName(sub.getSubName());
		if (findByName == null) {
			sub.setEnable(true);
			boolean addEntity = repo.addEntity(sub);
			if (addEntity) {
				return new ResponseEntity(HttpStatus.OK);
			} else {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/edit")
	public ResponseEntity<?> edit(@RequestBody Subjects sub) {
		Subjects getById = repo.getById(sub.getSub_Id());
		if (getById != null) {
			sub.setEnable(getById.isEnable());

			boolean b = repo.updateEntity(sub);
			if (b) {
				return new ResponseEntity(HttpStatus.OK);
			} else {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
		Subjects subjects = repo.getById(id);
		if (subjects != null) {
			if (subjects.isEnable()) {
				boolean b = repo.deleteEntity(id);
				if (b) {
					return new ResponseEntity(HttpStatus.OK);
				}
				return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
			} else {
				subjects.setEnable(true);
				boolean b = repo.updateEntity(subjects);
				if (b) {
					return new ResponseEntity(HttpStatus.OK);
				}
				return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
			}

		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
}
