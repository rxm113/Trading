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

import com.uk.trading.models.Price;
import com.uk.trading.models.Stock;
import com.uk.trading.utils.EntityManagerFactoryUtil;

public class PriceManager {

	private EntityManager em;

	public PriceManager() {
		em = EntityManagerFactoryUtil.getEntityManagerFactory().createEntityManager();
	}

	public Price addPrice(Price price) {

		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();
		Criteria criteriaPrice = session.createCriteria(Price.class);
		Price currentPrice = (Price) criteriaPrice.add(Restrictions.eq("date", price.getDate())).uniqueResult();

		if (currentPrice != null) {
			throw new EntityExistsException("Price already Exsists for the current date");
		}

		Stock stock = (Stock) session.get(Stock.class, price.getStock());

		if (stock == null) {
			throw new EntityExistsException("No Stock by given ID");
		}
		session.save(price);
		tx.commit();
		session.close();
		em.close();

		return price;
	}

	public List<Price> getAllPrices() {

		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(Price.class);
		@SuppressWarnings("unchecked")
		List<Price> prices = criteria.list();

		tx.commit();
		session.close();
		em.close();

		return prices;

	}

	public Price getPriceByID(int id) {

		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();

		Price price = (Price) session.get(Price.class, id);
		if (price == null) {
			throw new EntityExistsException("No Price exists with the given ID");
		}

		tx.commit();
		session.close();
		em.close();

		return price;
	}

	public Price getPriceByDate(String dateString) {

		try {
			DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
			Date date;

			date = format.parse(dateString);

			Session session = em.unwrap(Session.class);
			Transaction tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Price.class);
			criteria.add(Restrictions.eqOrIsNull("date", date));

			Price price = (Price) criteria.uniqueResult();

			if (price == null) {
				throw new EntityExistsException("No Price exists for the given date");
			}

			tx.commit();
			session.close();
			em.close();

			return price;
		} catch (ParseException e) {
			return null;
		}
	}

}
