package com.example.onlineExam.dao.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onlineExam.dao.ExamsDAO;
import com.example.onlineExam.entities.Exams;

@Repository
public class ExamsDAOImpl implements ExamsDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Exams> getAll() {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			List list = session.createQuery("FROM Exams").list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
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
	public Exams getById(Integer id) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Exams e = (Exams) session.createQuery("FROM Exams WHERE exam_Id = :exam_Id")
					.setParameter("exam_Id", id)
					.uniqueResult();
			session.getTransaction().commit();
			return e;
		} catch (Exception e) {
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
	public boolean addEntity(Exams newEntity) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(newEntity);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
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
	public boolean updateEntity(Exams updateEntity) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.update(updateEntity);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
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
			session.createQuery("UPDATE Exams e SET e.e_status = :status, e.enable = :enable WHERE e.exam_Id = :exam_Id")
			.setParameter("status", false)
			.setParameter("enable", false)
			.setParameter("exam_Id", id).executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return false;
	}

}
