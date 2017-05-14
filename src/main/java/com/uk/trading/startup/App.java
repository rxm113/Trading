package com.uk.trading.startup;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.uk.trading.data_fetch_modules.DiffbotModule;
import com.uk.trading.data_fetch_modules.StockPriceFetch;
import com.uk.trading.data_fetch_modules.TwitterFetch;
import com.uk.trading.models.Document;
import com.uk.trading.models.Price;
import com.uk.trading.models.Stock;

public class App {

	
	public static void main(String[] args) throws ParseException, IOException {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
		EntityManager em = emf.createEntityManager();
		
	
//		
//		Stock stock = new Stock();
//		stock.setName("Tesla");
		
//		
		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();

		DiffbotModule d = new DiffbotModule();
		List<Document> documents = d.diffbotCrawlCSVReader("/Users/robertminford/Downloads/Tesla_stock_documents/yahhoo.csv");
		
//		
		
//	
//		session.save(stock);
//		tx.commit();
//		em.close();
//		Session session = em.unwrap(Session.class);
//		Transaction tx = session.beginTransaction();
//		
//		Stock st = new Stock();
//		st.setName("sdsdsd");
//		session.save(st);
//		
//		StockPriceFetch s = new StockPriceFetch();
//		List<Price> prices = s.getStockPriceFromCSV("/Users/robertminford/Downloads/tsla.csv");
//		System.out.println(prices.size());
//		System.out.println();
		for(Document ds : documents){
			session.save(ds);
		}
//		System.out.println(prices.size());
//		System.out.println("finished");
		
		tx.commit();
		
//		TwitterFetch tf = new TwitterFetch();
//		tf.fetchTweets("@TeslaMotors", "2016-05-05","2017-05-05");
		
		em.close();
		
	}
}
