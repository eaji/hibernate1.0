package com.exist.dao;

import com.exist.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import java.util.*;
import com.exist.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;


public class RoleDao {

	public List<Role> getAllRoles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
		List<Role> roles = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			roles =  session.createCriteria(Role.class).addOrder( Order.asc("id") ).setCacheable(true).setCacheRegion("Role").list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return roles;
	}

	public List<Role> getRolesByPersonId(int personId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
		List<Role> roles = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Person.class);
			criteria.add(Restrictions.eq("id",personId));
			Person person = (Person)criteria.uniqueResult();
			roles = person.getRoles();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return new ArrayList<>(roles);	
	}

	public void manipulatePersonRoleById(int personId, List<Role> personRoles) {		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Person person = (Person)session.get(Person.class, personId);
			person.setRoles(personRoles);
			session.saveOrUpdate(person);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}