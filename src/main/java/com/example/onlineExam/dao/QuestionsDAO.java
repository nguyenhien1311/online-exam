package com.example.onlineExam.dao;

import java.util.List;

import com.example.onlineExam.entities.Questions;


public interface QuestionsDAO extends EntityDAO<Questions, Integer>{
	public List<Questions> getAllByExamId(Integer id);
}
