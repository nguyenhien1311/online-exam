package com.example.onlineExam.dao.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onlineExam.dao.QuestionsDAO;
import com.example.onlineExam.entities.Questions;

@Repository
public class QuestionsDAOImpl implements QuestionsDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Questions> getAll() {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			List list = session.createQuery("FROM Questions").list();
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
	public Questions getById(Integer id) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Questions quest = (Questions) session.createQuery("FROM Questions WHERE quest_Id = :quest_Id")
					.setParameter("quest_Id", id)
					.uniqueResult();
			session.getTransaction().commit();
			return quest;
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
	public boolean addEntity(Questions newEntity) {
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
	public boolean updateEntity(Questions updateEntity) {
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
			Questions optional = getById(id);
			session.beginTransaction();
			session.delete(optional);
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
	public List<Questions> getAllByExamId(Integer id) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			List list = session.createQuery("FROM Questions WHERE Exam_Id = :Exam_Id")
					.setParameter("Exam_Id", id)
					.list();
			session.getTransaction().commit();
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
