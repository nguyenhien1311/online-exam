package com.example.onlineExam.entities;

public class PaperDTO {
	private String subName;
	private String examName;
	private Integer totalMarks;
	private Integer earnedMark;
	private boolean isPassed;
	public PaperDTO(String subName, String examName, Integer totalMarks, Integer earnedMark, boolean isPassed) {
		super();
		this.subName = subName;
		this.examName = examName;
		this.totalMarks = totalMarks;
		this.earnedMark = earnedMark;
		this.isPassed = isPassed;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
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
	public Integer getEarnedMark() {
		return earnedMark;
	}
	public void setEarnedMark(Integer earnedMark) {
		this.earnedMark = earnedMark;
	}
	public boolean isPassed() {
		return isPassed;
	}
	public void setPassed(boolean isPassed) {
		this.isPassed = isPassed;
	}
	
	
	
}
