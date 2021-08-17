package com.example.onlineExam.dao;

import java.util.List;

import com.example.onlineExam.entities.Users;

public interface UsersDAO extends EntityDAO<Users, Integer>{
	public List<Users> getByRole(String role,String name);
	public Users findByEmail(String email);
	public Users findByUserName(String username);
	public List<Users> getByRole(String role);
}
