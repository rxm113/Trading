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

import com.uk.trading.models.Stock;
import com.uk.trading.models.Trade;
import com.uk.trading.utils.EntityManagerFactoryUtil;

public class TradeManager {
	private EntityManager em;

	public TradeManager() {
		em = EntityManagerFactoryUtil.getEntityManagerFactory().createEntityManager();
	}

	public Trade addTrade(Trade trade) {

		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();
		
		Stock stock = (Stock) session.get(Stock.class,trade.getStock());
		
		if(stock==null){
			throw new EntityExistsException("No Stock by given ID");
		}
		session.save(trade);

		tx.commit();
		session.close();
		em.close();

		return trade;
	}
	
	public List<Trade> getAllTrades(){
		
		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Trade.class);
		@SuppressWarnings("unchecked")
		List<Trade> trades = criteria.list();
	
		tx.commit();
		session.close();
		em.close();
		
		return trades;
		
	}
	
	public Trade getTradeByID(int id) {

		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();

		Trade trade = (Trade) session.get(Trade.class, id);
		if (trade == null) {
			throw new EntityExistsException("No Trade exists with the given ID");
		}

		tx.commit();
		session.close();
		em.close();

		return trade;
	}

	public Trade getTradeByDate(String dateString) {

		try {
			DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
			Date date;

			date = format.parse(dateString);

			Session session = em.unwrap(Session.class);
			Transaction tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Trade.class);
			criteria.add(Restrictions.eqOrIsNull("date", date));

			Trade trade = (Trade) criteria.uniqueResult();

			if (trade == null) {
				throw new EntityExistsException("No Trade exists for the given date");
			}

			tx.commit();
			session.close();
			em.close();

			return trade;
		} catch (ParseException e) {
			return null;
		}
	}

	
	
	
}
