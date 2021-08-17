package com.example.onlineExam.dao;

import java.util.List;

public interface EntityDAO<T,K> {
	public List<T> getAll();
	public T getById(K id);
	public boolean addEntity(T newEntity);
	public boolean updateEntity(T updateEntity);
	public boolean deleteEntity(K id);
}
