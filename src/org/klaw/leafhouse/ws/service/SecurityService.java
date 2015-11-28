/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.UriInfo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.klaw.leafhouse.db.utils.HibernateUtil;
import org.klaw.leafhouse.db.dto.*;
import org.klaw.leafhouse.ws.model.Link;
import org.klaw.leafhouse.ws.model.SecurityInformation;
import org.klaw.leafhouse.security.SecurityThreshhold;
import org.klaw.leafhouse.ws.resource.SecurityResource;

/**
 *
 * @author Klaw Strife
 */
public class SecurityService {

    public static SecurityInformation getSecurityInformation(UriInfo uriInfo) {
        SecurityState lastSecurityState = SecurityService.getLastSecurityState();
        SecurityThreshhold securityThreshhold = SecurityThreshhold.getInstance();
        List<Link> links = new ArrayList<>();
        links.add(new Link(uriInfo.getBaseUriBuilder()
                .path(SecurityResource.class)
                .build()
                .toString(), "self"));
        links.add(new Link(uriInfo.getBaseUriBuilder()
                .path(SecurityResource.class)
                //.path(SecurityResource.class,"getSecurityStates")
                .path(Integer.toString(lastSecurityState.getsecurityStateId()))
                .toString(), "self"));
        
        return new SecurityInformation(securityThreshhold.getRiskByEstimulation(),
                lastSecurityState,
                links);
    }

    public static SecurityState getLastSecurityState() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();        
        Query query = ses.createQuery("from SecurityState");
        query.setMaxResults(1);
        List<SecurityState> list = query.list();
        SecurityState last = list.get(0);
        last.setRawUserName(last.getUser().getUserName());
        ses.getTransaction().commit();
        ses.close();
        return last;
    }

    public  static List<SecurityState> getAllSecurityStates() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        Query query = ses.createQuery("from SecurityState");
        List<SecurityState> list = query.list();

        ses.getTransaction().commit();
        ses.close();
        return list;
    }

    public static List<SecurityState> getAllSecurityStates(int start, int size) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        List<SecurityState> list = ses.createQuery("from SecurityState")
                .setFirstResult(start)
                .setMaxResults(size).list();
        ses.getTransaction().commit();
        ses.close();
        return list;
    }
    
    public static List<SecurityState> getAllSecurityStates(Date startDate,Date endDate) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        String sql = "from SecurityState "
                + "where modifiedDate between :startDate and :endDate";
        List<SecurityState> list = ses.createQuery(sql)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).list();
        ses.getTransaction().commit();
        ses.close();
        return list;
    }

    public static SecurityState getSecurityState(int stateId) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        SecurityState securityState = (SecurityState) ses.get(SecurityState.class, stateId);
        ses.getTransaction().commit();
        ses.close();
        return securityState;
    }

    public static List<SensorState> getAllSecurityBreach() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from SensorState "
                + "where securityBreach = :security ");
        query.setParameter("security", true);
        List<SensorState> list = query.list();    
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static List<SensorState> getAllSecurityBreach(int start, int size) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<SensorState> list = session.createQuery("from SensorState "
                + "where securityBreach = :security ")
                .setParameter("security", true)
                .setFirstResult(start)
                .setMaxResults(size)
                .list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static List<SensorState> getAllSecurityBreach(Date startDate, Date endDate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "from SensorState "
                + "where securityBreach = :security and "
                + "fireDate between :startDate and :endDate";
        List<SensorState> list = session.createQuery(sql)
                .setParameter("security", true)
                .setParameter("startDate", startDate)
                .setParameter("startDate", endDate)
                .list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

}
