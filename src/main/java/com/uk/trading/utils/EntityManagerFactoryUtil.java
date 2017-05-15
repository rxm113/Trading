package com.uk.trading.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryUtil {

	private static final EntityManagerFactory emf;
	static {
		try {

			emf = Persistence.createEntityManagerFactory("persistence-unit");

		} catch (Throwable ex) {
			System.err.println("Initial EntityManagerFailed creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
}
