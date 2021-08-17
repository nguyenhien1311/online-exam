package com.example.onlineExam.dao;

import java.util.List;

import com.example.onlineExam.entities.Papers;


public interface PapersDAO extends EntityDAO<Papers, Integer> {
	public List<Papers> getByUserId(Integer id);
}	
