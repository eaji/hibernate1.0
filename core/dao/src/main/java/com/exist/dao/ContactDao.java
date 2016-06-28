package com.exist.dao;

import com.exist.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import java.util.*;
import com.exist.util.HibernateUtil;

public class ContactDao {


	public Set<Contact> getAllContacts(){
		List<Contact> contacts = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();		//no use so far
        Transaction tx = null;
		try {
			tx = session.beginTransaction();
			contacts = session.createQuery("FROM Contact").list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return new HashSet<Contact>(contacts);
	}

	public Contact addPersonContact(int id, Contact contact) {		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Person person = (Person)session.get(Person.class, id);
			contact.setPerson(person);
			person.getContacts().add(contact);
			session.update(person);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return contact;
	}

	public void updatePersonContactById(int id, Contact newContact) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "UPDATE Contact SET value = :newValue WHERE id = :contact_id";		
			Query query = session.createQuery(hql);
			query.setParameter("newValue", newContact.toString());
			query.setParameter("contact_id", id);
			query.executeUpdate();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteContactById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "DELETE FROM Contact WHERE id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			query.executeUpdate();
		} catch(RuntimeException e) {
			e.printStackTrace();
		} finally{
			session.close();
		}
	}

	public Set<Contact> getContactsByPersonId(int personId) {
		List<Contact> contacts = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "FROM Contact WHERE person_id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", personId);
			contacts = query.list();
		} catch(RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return new HashSet<Contact>(contacts);
	}

	public Set<Integer> getPersonContactIds(int personId) {
		List<Integer> ids = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "SELECT id FROM Contact WHERE person_id = :id ORDER BY id";
			Query query = session.createQuery(hql);
			query.setParameter("id", personId);
			ids = query.list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return new HashSet<Integer>(ids);
	}

}
