/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.service;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.klaw.leafhouse.db.dto.Sensor;
import org.klaw.leafhouse.db.dto.SensorState;
import org.klaw.leafhouse.db.utils.HibernateUtil;

/**
 *
 * @author Klaw Strife
 */
public class SensorService {

    public static List<Sensor> getSensors() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List<Sensor> list = s.createQuery("from Sensor").list();
        list.forEach(e -> {
            System.out.println("Imprimiendo ubicaciones");
            System.out.println(e.getSensorLocation().getLocationId());
        });
        s.getTransaction().commit();
        s.close();

        return list;
    }

    public static Sensor getSensor(int idSensor) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Sensor sensor = (Sensor) s.get(Sensor.class, idSensor);
        s.getTransaction().commit();
        s.close();
        return sensor;
    }

    public static List<SensorState> getSensorStates(int sensorId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List<SensorState> list = s.createQuery("from SensorState where sensorId = :sensorId")
                .setParameter("sensorId", sensorId).list();

        s.getTransaction().commit();
        s.close();
        return list;
    }

    public static SensorState getSensorState(int sensorId, int sensorStateId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        SensorState sensor = (SensorState) s.get(SensorState.class, sensorStateId);
        s.getTransaction().commit();
        s.close();
        if (sensor != null) {
            if (sensor.getSensor().getSensorId() != sensorId) {
                System.err.println("Error el estado de el sensor no es igual a su id de sensor por recurso");
                return null;
            }
        }
        return sensor;
    }

    public static List<SensorState> getSensorStates(int sensorId, int start, int size) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List<SensorState> list = s.createQuery("from SensorState where sensorId = :sensorId")
                .setParameter("sensorId", sensorId)
                .setFirstResult(start)
                .setMaxResults(size).list();
        s.getTransaction().commit();
        s.close();
        return list;
    }

    public static List<SensorState> getSensorStates(int sensorId, Date startDate, Date endDate) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        String sql = "from SensorState "
                + "where sensorId = :sensorId "
                + "and fireDate between :startDate and :endDate";
        List<SensorState> list = s.createQuery(sql)
                .setParameter("sensorId", sensorId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).list();
        s.getTransaction().commit();
        s.close();
        return list;
    }

}
