package com.example.onlineExam.dao;

import java.util.List;

import com.example.onlineExam.entities.Subjects;


public interface SubjectsDAO extends EntityDAO<Subjects, Integer>{
	public Subjects findByName(String name);
	public List<Subjects> getAll(String name);
}
