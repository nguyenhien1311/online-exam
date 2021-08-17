package com.example.onlineExam.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onlineExam.dao.PapersDAO;
import com.example.onlineExam.entities.Papers;

@Repository
public class PapersDAOImpl implements PapersDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Papers> getAll() {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			List list =session.createQuery("FROM Papers").list();
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
	public Papers getById(Integer id) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Papers pp = (Papers) session.createQuery("FROM Papers WHERE paper_Id = :paper_Id")
					.setParameter("paper_Id", id)
					.uniqueResult();
			session.getTransaction().commit();
			return pp;
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
	public boolean addEntity(Papers newEntity) {
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
	public boolean updateEntity(Papers updateEntity) {
		Session session = sessionFactory.openSession();
		try {
			session.getTransaction();
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
			Papers optional = getById(id);
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
	public List<Papers> getByUserId(Integer id) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			List list = session.createQuery("FROM Papers WHERE U_Id = :U_Id").setParameter("U_Id", id).list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}

}
