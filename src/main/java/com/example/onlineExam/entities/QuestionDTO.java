package com.example.onlineExam.entities;

public class QuestionDTO {
	private Integer quest_Id;
	private String question;
	private Integer questMark;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String answer;

	private boolean isLast;
	
	public QuestionDTO(Questions q,boolean isLast) {
		this.quest_Id = q.getQuest_Id();
		this.question = q.getQuestion();
		this.questMark = q.getQuestMark();
		this.option1 = q.getOption1();
		this.option2 = q.getOption2();
		this.option3 = q.getOption3();
		this.option4 = q.getOption4();
		this.answer = q.getAnswer();
		this.isLast = isLast;
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

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}
	
	
}
