package com.exist.dao;

import com.exist.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import com.exist.util.HibernateUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

import java.util.*;
import java.io.*;

public class PersonDao {

	public List<Person> getAllPersons() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
		List<Person> persons = new ArrayList<>(); 
		try {
			tx = session.beginTransaction();
			persons = session.createQuery("FROM Person").setCacheable(true).setCacheRegion("Person").list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persons;
	}

	public List<Person> getPersonsSortedBy(int id1, int id2) {
		Session session = HibernateUtil.getSessionFactory().openSession();					
		session = HibernateUtil.getSessionFactory().openSession();
		List<Person> persons = new ArrayList<>();
		Transaction tx = null;
		try {
			if(id1 == 1 && id2 == 1) {
			persons = session.createCriteria(Person.class).addOrder(Order.asc("name")).setCacheable(true).setCacheRegion("Person").list();
			}
			else if(id1 == 1 && id2 == 2) {
			persons = session.createCriteria(Person.class).addOrder(Order.desc("name")).setCacheable(true).setCacheRegion("Person").list();
			}
			else if(id1 == 2 && id2 == 1) {
			persons = session.createCriteria(Person.class).addOrder(Order.asc("dateHired")).setCacheable(true).setCacheRegion("Person").list();
			}
			else if(id1 == 2 && id2 == 2) {
			persons = session.createCriteria(Person.class).addOrder(Order.desc("dateHired")).setCacheable(true).setCacheRegion("Person").list();
			}
		} catch(RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persons;
	}


	public int getPersonsCount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        int personsCount = 0;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Person.class);
			criteria.setProjection(Projections.rowCount());
			personsCount = (int) criteria.uniqueResult();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return personsCount;
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
			session.update(person);
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

	public void updateAddress(Address address) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(address);
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

	public List<Contact> getContactsByPersonId(int personId) {
		List<Contact> contacts = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "FROM Contact WHERE person_id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", personId);
			contacts = query.setCacheable(true).setCacheRegion("Contact").list();
		} catch(RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return contacts;
	}

	public void addPersonContactById(int personId, List<Contact> newContacts) {		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Person person = (Person)session.get(Person.class, personId);
			//person.getContacts().add(contact);
			person.setContacts(newContacts);
			session.save(person);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
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

	public void deletePersonContactById(int id) {
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
