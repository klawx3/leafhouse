/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.db.utils;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Klaw Strife
 */
public class HibernateUtil {
    private static final Logger logger = LogManager.getLogger(HibernateUtil.class.getSimpleName());
    private static final SessionFactory sessionFactory;
    
    static {
        try {
            logger.trace("Trying to connect to hibernate");
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            logger.info("Connected to DB through Hibernate");
        } catch (Throwable ex) {

            logger.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
