package com.example.onlineExam.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@Table(name="Exams")
public class Exams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Exam_Id")
	private Integer exam_Id;
	@Column(name="ExamName")
	private String examName;
	@Column(name="TotalMarks")
	private Integer totalMarks;
	@Column(name="PassMarks")
	private Integer passMarks;
	@Column(name="Deadline")
	private Date deadline;
	@Column(name="Enable")
	private boolean enable;
	@Column(name="Status")
	private boolean e_status;
	
	@ManyToOne
	@JoinColumn(name = "Sub_Id", referencedColumnName = "Sub_Id")
	 @JsonBackReference(value = "listExams")
	private Subjects subject;
	
	@OneToMany(mappedBy = "examOwn",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference(value = "listQuestions")
	private Collection<Questions> listQuestions;
	
	@OneToMany(mappedBy = "exam",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference(value = "listPapers")
	private Collection<Papers> listPapers;
	
	public Exams() {
		// TODO Auto-generated constructor stub
	}

	public Exams(Integer exam_Id, String examName, Integer totalMarks, Integer passMarks, Date deadline, boolean enable,
			boolean e_status, Subjects subject, Collection<Questions> listQuestions, Collection<Papers> listPapers) {
		super();
		this.exam_Id = exam_Id;
		this.examName = examName;
		this.totalMarks = totalMarks;
		this.passMarks = passMarks;
		this.deadline = deadline;
		this.enable = enable;
		this.e_status = e_status;
		this.subject = subject;
		this.listQuestions = listQuestions;
		this.listPapers = listPapers;
	}

	public Exams(String examName, Integer totalMarks, Integer passMarks, Date deadline, boolean enable,
			boolean e_status, Subjects subject) {
		super();
		this.examName = examName;
		this.totalMarks = totalMarks;
		this.passMarks = passMarks;
		this.deadline = deadline;
		this.enable = enable;
		this.e_status = e_status;
		this.subject = subject;
	}

	public Integer getExam_Id() {
		return exam_Id;
	}

	public void setExam_Id(Integer exam_Id) {
		this.exam_Id = exam_Id;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}

	public Integer getPassMarks() {
		return passMarks;
	}

	public void setPassMarks(Integer passMarks) {
		this.passMarks = passMarks;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isE_status() {
		return e_status;
	}

	public void setE_status(boolean e_status) {
		this.e_status = e_status;
	}

	public Subjects getSubject() {
		return subject;
	}

	public void setSubject(Subjects subject) {
		this.subject = subject;
	}

	public Collection<Questions> getListQuestions() {
		return listQuestions;
	}

	public void setListQuestions(Collection<Questions> listQuestions) {
		this.listQuestions = listQuestions;
	}

	public Collection<Papers> getListPapers() {
		return listPapers;
	}

	public void setListPapers(Collection<Papers> listPapers) {
		this.listPapers = listPapers;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
