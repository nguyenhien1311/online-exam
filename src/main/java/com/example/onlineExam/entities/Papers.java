package com.example.onlineExam.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name="Papers")
public class Papers implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Paper_Id")
	private Integer paper_Id;
	@Column(name="EarnedMark")
	private Integer earnedMark;
	@Column(name="IsPassed")
	private boolean isPassed;
	
	@ManyToOne
	@JoinColumn(name = "StudentId",referencedColumnName = "U_Id")
	@JsonBackReference(value = "user")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name = "ExamId",referencedColumnName = "Exam_Id")
	@JsonBackReference(value = "listPapers")
	private Exams exam;
	
	public Papers() {
		// TODO Auto-generated constructor stub
	}

	public Papers(Integer paper_Id, Integer earnedMark, boolean isPassed, Users user, Exams exam) {
		super();
		this.paper_Id = paper_Id;
		this.earnedMark = earnedMark;
		this.isPassed = isPassed;
		this.user = user;
		this.exam = exam;
	}

	public Papers(Integer earnedMark, boolean isPassed, Users user, Exams exam) {
		super();
		this.earnedMark = earnedMark;
		this.isPassed = isPassed;
		this.user = user;
		this.exam = exam;
	}

	public Integer getPaper_Id() {
		return paper_Id;
	}

	public void setPaper_Id(Integer paper_Id) {
		this.paper_Id = paper_Id;
	}

	public Integer getEarnedMark() {
		return earnedMark;
	}

	public void setEarnedMark(Integer earnedMark) {
		this.earnedMark = earnedMark;
	}

	public boolean getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(boolean isPassed) {
		this.isPassed = isPassed;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Exams getExam() {
		return exam;
	}

	public void setExam(Exams exam) {
		this.exam = exam;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
