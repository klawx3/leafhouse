/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.service;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.klaw.leafhouse.db.dto.Location;
import org.klaw.leafhouse.db.dto.SecurityState;
import org.klaw.leafhouse.db.utils.HibernateUtil;

/**
 *
 * @author Klaw Strife
 */
public class LocationService {

    public static List<Location> getAllLocations() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        Query query = ses.createQuery("from Location");
        List<Location> list = query.list();
        ses.getTransaction().commit();
        ses.close();
        return list;
    }

    public static Location getLocation(int locationId) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        Location location= (Location) ses.get(Location.class, locationId);
        ses.getTransaction().commit();
        ses.close();
        return location;
    }

}
