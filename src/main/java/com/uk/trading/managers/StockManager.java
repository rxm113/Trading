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
import com.uk.trading.models.Price;
import com.uk.trading.models.Stock;
import com.uk.trading.models.Trade;
import com.uk.trading.utils.EntityManagerFactoryUtil;

public class StockManager {

	private EntityManager em;

	public StockManager() {
		em = EntityManagerFactoryUtil.getEntityManagerFactory().createEntityManager();
	}

	public Stock addStock(Stock stock) {

		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Stock.class);
		Stock currentStock = (Stock) criteria.add(Restrictions.eq("name", stock.getName())).uniqueResult();

		if (currentStock != null) {
			throw new EntityExistsException("Stock already Exsists with the current name");
		}
		session.save(stock);
		tx.commit();
		session.close();
		em.close();

		return stock;
	}

	public List<Stock> getAllStocks() {

		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(Stock.class);
		@SuppressWarnings("unchecked")
		List<Stock> stocks = criteria.list();

		tx.commit();
		session.close();
		em.close();

		return stocks;

	}

	public List<Price> getAllPricesForStock(int id, String dateFromString, String dateToString) {
		try {
			Session session = em.unwrap(Session.class);
			Transaction tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Price.class);
			criteria.add(Restrictions.eq("stock_id", id));
			if ((dateFromString != null) && (dateToString == null)) {
				DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
				Date dateFrom = format.parse(dateFromString);
				Date dateTo = format.parse(dateToString);
				criteria.add(Restrictions.between("date", dateFrom, dateTo));
			}

			@SuppressWarnings("unchecked")
			List<Price> prices = criteria.list();

			tx.commit();
			session.close();
			em.close();

			return prices;
		} catch (ParseException e) {

			e.printStackTrace();
			return null;
		}
	}

	public List<Document> getAllDocumentsForStock(int id, String dateFromString, String dateToString) {
		try {
			Session session = em.unwrap(Session.class);
			Transaction tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Document.class);
			criteria.add(Restrictions.eq("stock_id", id));
			if ((dateFromString != null) && (dateToString == null)) {
				DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
				Date dateFrom;

				dateFrom = format.parse(dateFromString);

				Date dateTo = format.parse(dateToString);
				criteria.add(Restrictions.between("date", dateFrom, dateTo));
			}

			@SuppressWarnings("unchecked")
			List<Document> documents = criteria.list();

			tx.commit();
			session.close();
			em.close();

			return documents;
		} catch (ParseException e) {

			e.printStackTrace();
			return null;
		}
	}

	public List<Trade> getAllTradesForStock(int id, String dateFromString, String dateToString) {
		try {
			Session session = em.unwrap(Session.class);
			Transaction tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Trade.class);
			criteria.add(Restrictions.eq("stock_id", id));
			if ((dateFromString != null) && (dateToString == null)) {
				DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
				Date dateFrom;

				dateFrom = format.parse(dateFromString);

				Date dateTo = format.parse(dateToString);
				criteria.add(Restrictions.between("date", dateFrom, dateTo));
			}

			@SuppressWarnings("unchecked")
			List<Trade> trades = criteria.list();

			tx.commit();
			session.close();
			em.close();

			return trades;
		} catch (ParseException e) {

			e.printStackTrace();
			return null;
		}
	}
}
