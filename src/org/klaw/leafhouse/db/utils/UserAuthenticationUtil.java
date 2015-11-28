/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.db.utils;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.klaw.leafhouse.ws.authentication.HTTPBasicUser;
import org.klaw.leafhouse.db.dto.User;
import org.klaw.leafhouse.db.utils.HibernateUtil;

/**
 *
 * @author Klaw Strife
 */
public class UserAuthenticationUtil {
    
    public static User getValidUser(HTTPBasicUser httpUser) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(
                String.format("from User where userName = '%s' and userPassword = '%s'", httpUser.getUsername(), httpUser.getPassword()));
        User user = (User) query.uniqueResult();
        session.close();
        return user;
    }
    
}
