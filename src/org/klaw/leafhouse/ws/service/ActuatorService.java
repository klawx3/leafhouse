/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.service;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.klaw.leafhouse.db.dto.Actuator;
import org.klaw.leafhouse.db.dto.ActuatorState;
import org.klaw.leafhouse.db.utils.HibernateUtil;

/**
 *
 * @author Klaw Strife
 */
public class ActuatorService {

    public static List<Actuator> getAllActuators() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Actuator> actuators = session.createQuery("from Actuator").list();
        session.getTransaction().commit();
        session.close();
        return actuators;
    }

    public static Actuator getActuator(int actuatorId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Actuator actuator = (Actuator) session.get(Actuator.class, actuatorId);
        session.getTransaction().commit();
        session.close();
        return actuator;
    }

    public static List<ActuatorState> getActuatorStates(int actuatorId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from ActuatorState "
                + "where actuatorId = :actuatorId");
        query.setParameter("actuatorId", actuatorId);
        List<ActuatorState> actuatorStates = query.list();
        session.getTransaction().commit();
        session.close();
        return actuatorStates;
    }

    public static ActuatorState getActuatorState(int actuatorId, int actuatorStateId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ActuatorState actuatorState = (ActuatorState) session.get(
                ActuatorState.class,
                actuatorStateId);
        session.getTransaction().commit();
        session.close();
        if (actuatorState != null) {
            if (actuatorState.getActuator().getActuatorId() != actuatorId) {
                return null;
            }
        }
        return actuatorState;
    }

    public static List<ActuatorState> getActuatorStates(int actuatorId, int start, int size) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        final String sqlString = "from ActuatorState "
                + "where actuatorId = :actuatorId";
        Query query = session.createQuery(sqlString);
        query.setParameter("actuatorId", actuatorId);
        query.setFirstResult(start);
        query.setMaxResults(size);
        List<ActuatorState> actuatorStates = query.list();
        session.getTransaction().commit();
        session.close();
        return actuatorStates;
    }

    public static List<ActuatorState> getActuatorStates(int actuatorId, Date startDate, Date endDate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        final String sqlString = "from ActuatorState "
                + "where actuatorId = :actuatorId "
                + "and fireDate between :startDate and :endDate";
        Query query = session.createQuery(sqlString);
        query.setParameter("actuatorId", actuatorId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List<ActuatorState> actuatorStates = query.list();
        session.getTransaction().commit();
        session.close();
        return actuatorStates;
    }

}
