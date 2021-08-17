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
@Table(name="Quesions")
public class Questions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Quest_Id")
	private Integer quest_Id;
	@Column(name="Question")
	private String question;
	@Column(name="QuestMark")
	private Integer questMark;
	@Column(name="Option1")
	private String option1;
	@Column(name="Option2")
	private String option2;
	@Column(name="Option3")
	private String option3;
	@Column(name="Option4")
	private String option4;
	@Column(name="Answer")
	private String answer;
	
	@ManyToOne
	@JoinColumn(name = "Exam_Id",referencedColumnName = "Exam_Id")
    @JsonBackReference(value = "listQuestions")
	private Exams examOwn;
	
	public Questions() {
		// TODO Auto-generated constructor stub
	}

	public Questions(Integer quest_Id, String question, Integer questMark, String option1, String option2,
			String option3, String option4, String answer, Exams examOwn) {
		super();
		this.quest_Id = quest_Id;
		this.question = question;
		this.questMark = questMark;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.answer = answer;
		this.examOwn = examOwn;
	}

	public Questions(String question, Integer questMark, String option1, String option2, String option3, String option4,
			String answer, Exams examOwn) {
		super();
		this.question = question;
		this.questMark = questMark;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.answer = answer;
		this.examOwn = examOwn;
	}

	public Integer getQuest_Id() {
		return quest_Id;
	}

	public void setQuest_Id(Integer quest_Id) {
		this.quest_Id = quest_Id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getQuestMark() {
		return questMark;
	}

	public void setQuestMark(Integer questMark) {
		this.questMark = questMark;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Exams getExamOwn() {
		return examOwn;
	}

	public void setExamOwn(Exams examOwn) {
		this.examOwn = examOwn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
