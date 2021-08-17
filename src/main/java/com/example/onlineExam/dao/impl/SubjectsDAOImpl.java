package com.example.onlineExam.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onlineExam.dao.SubjectsDAO;
import com.example.onlineExam.entities.Subjects;

@Repository
public class SubjectsDAOImpl implements SubjectsDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Subjects> getAll() {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			List list = session.createQuery("FROM Subjects").list();
			session.getTransaction().rollback();
			return list;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return null;
	}

	@Override
	public Subjects getById(Integer id) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Subjects sub = (Subjects) session.createQuery("FROM Subjects WHERE sub_Id = :sub_Id")
					.setParameter("sub_Id", id)
					.uniqueResult();
			session.getTransaction().commit();
			return sub;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return null;
	}

	@Override
	public boolean addEntity(Subjects newEntity) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(newEntity);
			session.getTransaction().commit();
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return false;
	}

	@Override
	public boolean updateEntity(Subjects updateEntity) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.update(updateEntity);
			session.getTransaction().commit();
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return false;
	}

	@Override
	public boolean deleteEntity(Integer id) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.createQuery("UPDATE Subjects s SET s.enable = :status WHERE s.sub_Id = :sub_Id")
			.setParameter("status", false)
			.setParameter("sub_Id", id).executeUpdate();
			session.getTransaction().commit();
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return false;
	}

	@Override
	public Subjects findByName(String name) {
		Session session = sessionFactory.openSession();
		try {
			name="%"+name+"%";
			session.beginTransaction();
			Subjects sub = (Subjects) session.createQuery("FROM Subjects WHERE subName = :name")
					.setParameter("name", name)
					.uniqueResult();
			session.getTransaction().commit();
			return sub;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return null;
	}

	@Override
	public List<Subjects> getAll(String name) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			List list = session.createQuery("FROM Subjects WHERE subName like :subName")
					.setParameter("subName", "%"+name+"%")
					.list();
			session.getTransaction().rollback();
			return list;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return null;
	}

}
