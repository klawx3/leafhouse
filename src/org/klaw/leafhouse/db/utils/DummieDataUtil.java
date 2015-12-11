/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.db.utils;

import java.util.Calendar;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.klaw.leafhouse.db.dto.*;
import org.klaw.leafhouse.types.SensorType;

/**
 *
 * @author Klaw Strife
 */
public class DummieDataUtil {
    
    
    public static void genDummieData(){

        Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        //du data
        Location l1 = new Location();
        l1.setLocationName("Ubicacion principal");
        Location l2 = new Location();
        l2.setLocationName("Cochera");
        Location l3 = new Location();
        l3.setLocationName("Patio");
        ses.save(l1);
        ses.save(l2);
        ses.save(l3);
        
        User u1 = new User();
        u1.setUserName("_Leafhouse");
        u1.setUserLeafHouse(true);
        u1.setUserPassword("weaadasdad");
        u1.setUserEmail("klawx3@gmail.com");
        u1.setUserAdmin(true);
        
        ses.save(u1);
        
        Sensor s1 = new Sensor();
        s1.setSensorLocation(l3);
        s1.setSensorName("Sensor puerta");
        s1.setSensorGpioPin(1);
        s1.setSensorType(SensorType.EVENT_FIRED);
        ses.save(s1);
        
        for (int i = 0; i < 20; i++) {
            SensorState ss = new SensorState();
            ss.setFireDate(Calendar.getInstance());
            ss.setSecurityBreach(false);
            ss.setSensor(s1);
            ss.setUser(u1);
            ses.save(ss);
        }
        
        SecurityState sss1 = new SecurityState();
        sss1.setSecurityEnabled(true);
        sss1.setUser(u1);
        sss1.setModifiedDate(Calendar.getInstance());
        ses.save(sss1);
        
        SecurityState sss2 = new SecurityState();
        sss2.setSecurityEnabled(false);
        sss2.setUser(u1);
        sss2.setModifiedDate(Calendar.getInstance());
        ses.save(sss2);
        
        //end
        ses.getTransaction().commit();
        ses.close();

        
    }
}
