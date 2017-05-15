package com.uk.trading.managers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.uk.trading.models.Document;
import com.uk.trading.models.Stock;
import com.uk.trading.utils.EntityManagerFactoryUtil;

public class DocumentManager {

	private EntityManager em;

	public DocumentManager() {
		em = EntityManagerFactoryUtil.getEntityManagerFactory().createEntityManager();
	}

	public Document addDocument(Document document) {

		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Document.class);
		Document currentDocument = (Document) criteria.add(Restrictions.eq("date", document.getTitle())).uniqueResult();

		if (currentDocument != null) {
			throw new EntityExistsException("Document already Exsists for the current date");
		}
		Stock stock = (Stock) session.get(Stock.class, document.getStock());

		if (stock == null) {
			throw new EntityExistsException("No Stock by given ID");
		}
		session.save(document);
		tx.commit();
		session.close();
		em.close();

		return document;
	}

	public List<Document> getAllDocuments() {

		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(Document.class);
		@SuppressWarnings("unchecked")
		List<Document> documents = criteria.list();

		tx.commit();
		session.close();
		em.close();

		return documents;
	}

	public Document getDocumentByID(int id) {

		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();

		Document document = (Document) session.get(Document.class, id);
		if (document == null) {
			throw new EntityExistsException("No Document exists with the given ID");
		}

		tx.commit();
		session.close();
		em.close();

		return document;
	}
	
	public Document getDocumentByDate(String dateString) {

		try {
			DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
			Date date;

			date = format.parse(dateString);

			Session session = em.unwrap(Session.class);
			Transaction tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Document.class);
			criteria.add(Restrictions.eqOrIsNull("date", date));

			Document document = (Document) criteria.uniqueResult();

			if (document == null) {
				throw new EntityExistsException("No Document exists for the given date");
			}

			tx.commit();
			session.close();
			em.close();

			return document;
		} catch (ParseException e) {
			return null;
		}
	}



}
