package com.exist.dao;

import com.exist.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import com.exist.util.HibernateUtil;

public class AddressDao {

	public Address addAddress(Address address) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(address);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return address;
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

	public Address getAddressById(int id) {															
		Address address = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "FROM Address WHERE id = :id";
			Query query = session.createQuery(hql);
			query.setInteger("id",id);
			address = (Address) query.uniqueResult();
			tx.commit();
		} catch(RuntimeException e){
			e.printStackTrace();
		} finally{
			session.close();
		}
		return address;
	}//

}
