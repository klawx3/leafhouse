/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.service;

import java.util.List;
import org.hibernate.Session;
import org.klaw.leafhouse.db.dto.User;
import org.klaw.leafhouse.db.utils.HibernateUtil;

/**
 *
 * @author Klaw Strife
 */
public class UserService {
    
    
    public static List<User> getAllUsers(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    public static User getUser(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, userName);
        session.getTransaction().commit();
        session.close();
        return user;
    }
    
    public static User deleteUser(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, userName);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public static User createUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

}
