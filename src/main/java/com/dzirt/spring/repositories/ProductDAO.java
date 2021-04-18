package com.dzirt.spring.repositories;

import com.dzirt.spring.entities.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductDAO {

	private static AtomicLong identity = new AtomicLong(0);
	private static final SessionFactory factory = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(Product.class)
			.buildSessionFactory();;
	private Session session;

	private final Map<Long, Product> identityMap = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() {
		add(new Product("MacBook", "Ultra low and Great Power", new BigDecimal(3000)));
		add(new Product("iPhone", "The most expensive phone by credit", new BigDecimal(1000)));
		add(new Product("iPad", "More size - more cost", new BigDecimal(2000)));
	}

	public void add(Product product) {
		session = factory.getCurrentSession();
		session.beginTransaction();
		session.save(product);
		session.getTransaction().commit();
	}

	public void update(Product product) {
		session = factory.getCurrentSession();
		session.beginTransaction();
		Product dbProduct = session.get(Product.class, product.getId());
		dbProduct = product;
		session.getTransaction().commit();
	}

	public void remove(long id) {
		session = factory.getCurrentSession();
		session.beginTransaction();
		Product dbProduct = session.get(Product.class, id);
		session.delete(dbProduct);
		session.getTransaction().commit();
	}

	public Product findById(long id) {
		session = factory.getCurrentSession();
		session.beginTransaction();
		Product product = session.createQuery("from Product p WHERE p.id = :id", Product.class)
				.setParameter("id", id)
				.getSingleResult();
		session.getTransaction().commit();

		return product;
	}

	public List<Product> findAll() {
		session = factory.getCurrentSession();
		session.beginTransaction();
		List<Product> productList = session.createQuery("from Product").getResultList();
		session.getTransaction().commit();

		return productList;
	}

	@PreDestroy
	public void cleanUP() {
		session.close();
		factory.close();
	}

}
