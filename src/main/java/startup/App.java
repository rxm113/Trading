package startup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.uk.trading.models.Article;
import com.uk.trading.models.Stock;

public class App {

	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
		EntityManager em = emf.createEntityManager();
		
		
		Article article = new Article();
		article.setContent("hekllo");
		
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
		
		
		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();
		
		Stock stock = (Stock) session.get(Stock.class, 1);
		article.setStock(stock);
		session.save(article);
		tx.commit();
		em.close();
		
	}
}
