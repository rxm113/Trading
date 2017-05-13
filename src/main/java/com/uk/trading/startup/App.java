package com.uk.trading.startup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.uk.trading.data_fetch_modules.StockPriceFetch;

public class App {

	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
		EntityManager em = emf.createEntityManager();
		
	
//		
//		Stock stock = new Stock();
//		stock.setName("Tesla");
		
//		
//		Session session = em.unwrap(Session.class);
//		Transaction tx = session.beginTransaction();
//		
		
//	
//		session.save(stock);
//		tx.commit();
//		em.close();
		StockPriceFetch s = new StockPriceFetch();
		s.getStockPriceFromCSV("/Users/robertminford/Downloads/tsla.csv");
		
		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		em.close();
		
	}
}
