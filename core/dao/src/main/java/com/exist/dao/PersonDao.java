package com.exist.dao;

import com.exist.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import com.exist.util.HibernateUtil;

import java.util.*;
import java.io.*;

public class PersonDao {
	HibernateUtil hutil = new HibernateUtil(); //
	public List<Person> getAllPersons() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
		List<Person> persons = new ArrayList<>(); 
		try {
			tx = session.beginTransaction();
			persons = session.createQuery("FROM Person").list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persons;
	}

	public List<Person> getPersonsSortedByLastNameAsc() {
		Session session = HibernateUtil.getSessionFactory().openSession();					
		session = HibernateUtil.getSessionFactory().openSession();
		List<Person> persons = new ArrayList<>();
		Transaction tx = null;
		try {
            persons = session.createQuery("FROM Person ORDER BY last_name").list();
            //hql = "FROM com.exist.model.Person ORDER BY last_name";
            //query = session.createQuery(hql);
            //list = query.list();
		} catch(RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persons;
	}

	public List<Person> getPersonsSortedByLastNameDesc() {
		Session session = HibernateUtil.getSessionFactory().openSession();					
		session = HibernateUtil.getSessionFactory().openSession();
		List<Person> persons = new ArrayList<>();
		Transaction tx = null;
		try {
            persons = session.createQuery("FROM Person ORDER BY last_name DESC").list();
		} catch(RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persons;
	}

	public List<Person> getPersonsSortedByDateHiredAsc() {
		Session session = HibernateUtil.getSessionFactory().openSession();					
		session = HibernateUtil.getSessionFactory().openSession();
		List<Person> persons = new ArrayList<>();
		Transaction tx = null;
		try {
            persons = session.createQuery("FROM Person ORDER BY date_hired").list();
		} catch(RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persons;
	}

	public List<Person> getPersonsSortedByDateHiredDesc() {
		Session session = HibernateUtil.getSessionFactory().openSession();					
		session = HibernateUtil.getSessionFactory().openSession();
		List<Person> persons = new ArrayList<>();
		Transaction tx = null;
		try {
            persons = session.createQuery("FROM Person ORDER BY date_hired DESC").list();
		} catch(RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persons;
	}

	public Person getPersonById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Person person = null;
		try {
			tx = session.beginTransaction();
			String hql = "FROM Person WHERE id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id",id);
			person = (Person) query.uniqueResult();
		} catch(RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return person;
	}

	public void addPerson(Person person) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(person);
			//session.getTransaction().commit();
			tx.commit();
		} catch(RuntimeException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void updatePerson(Person person) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(person);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deletePersonById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Person person = (Person)session.get(Person.class, id);
			session.delete(person);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Set<Integer> getPersonIds() {
		List<Integer> ids = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ids = session.createQuery("SELECT id FROM Person ORDER BY id").list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return new HashSet<Integer>(ids);
	}

}
