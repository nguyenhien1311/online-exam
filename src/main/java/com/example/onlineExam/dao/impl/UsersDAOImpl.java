package com.example.onlineExam.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onlineExam.dao.UsersDAO;
import com.example.onlineExam.entities.Users;

@Repository
public class UsersDAOImpl implements UsersDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Users> getAll() {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			List list = session.createQuery("from Users").list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public Users getById(Integer id) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Users user = (Users) session.createQuery("From Users u WHERE u.u_Id = :u_Id").setParameter("u_Id", id).uniqueResult();
			session.getTransaction().commit();
			return user;
		} catch (Exception e) {
			
			e.printStackTrace();
			
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public boolean addEntity(Users newEntity) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(newEntity);
			session.getTransaction().commit();
			return true;	
		} catch (Exception e) {
			
			e.printStackTrace();
			
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		return false;
	}

	@Override
	public boolean updateEntity(Users updateEntity) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.update(updateEntity);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			
			e.printStackTrace();
			
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		return false;
	}

	@Override
	public boolean deleteEntity(Integer id) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.createQuery("Update Users u Set u.enable = :status where u.u_Id = :id")
			.setParameter("status", false)
			.setParameter("id", id).executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			
			e.printStackTrace();
			
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		return false;
	}

	@Override
	public Users findByEmail(String email) {
		Session session = sessionFactory.openSession();
		try {
			email="%"+email+"%";
			session.beginTransaction();
			Users user = (Users) session.createQuery("from Users u WHERE u.email like :email").setParameter("email",  "%"+email+"%").uniqueResult();
			session.getTransaction().commit();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public Users findByUserName(String username) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Users user = (Users) session.createQuery("from Users u WHERE u.username = :username").setParameter("username", username).uniqueResult();
			session.getTransaction().commit();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<Users> getByRole(String role,String name) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			List list = session.createQuery("from Users u WHERE u.role like :role AND u.fullName like :name")
					.setParameter("role","%"+role+"%")
					.setParameter("name","%"+name+"%")
					.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}
	@Override
	public List<Users> getByRole(String role) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			List list = session.createQuery("from Users u WHERE u.role like :role")
					.setParameter("role", "%"+role+"%")
					.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}

}
