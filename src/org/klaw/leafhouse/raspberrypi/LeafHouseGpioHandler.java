/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.raspberrypi;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.hibernate.Session;
import org.klaw.binding.components.LeafHouseComponents;
import org.klaw.leafhouse.db.dto.Sensor;
import org.klaw.leafhouse.db.dto.SensorState;
import org.klaw.leafhouse.db.dto.User;

import org.klaw.leafhouse.ws.service.SecurityService;
import org.klaw.leafhouse.db.utils.HibernateUtil;
import org.klaw.leafhouse.mail.GMailSender;
import org.klaw.leafhouse.security.SecurityThreshhold;
import org.klaw.leafhouse.types.SensorOnEvent;
import org.klaw.leafhouse.types.SensorType;

/**
 *
 * @author Klaw Strife
 */
public class LeafHouseGpioHandler implements GpioPinListenerDigital {

    private final ComponentsBuilder builder;
    private final GMailSender gmailSender;
    private final User defaultUser;

    @SuppressWarnings("LeakingThisInConstructor")
    public LeafHouseGpioHandler(org.klaw.binding.components.LeafHouseComponents leafHouseComponents,
            GMailSender gmailSender) {
        this.gmailSender = gmailSender;
        builder = new ComponentsBuilder(leafHouseComponents);
        builder.addSensorListener(this);
        builder.buildComponents();
        defaultUser = getDefaultUser();

    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        System.out.println(" --> (SENSOR) GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
        if (SecurityService.getLastSecurityState().isSecurityEnabled()) {
            LeafHouseComponents.Sensor eventFileSensor = builder.getLeafHouseComponents().getSensor()
                    .stream()
                    .filter(s -> ComponentsUtil.isGpioPinEquals(s.getAttachedGpioPin().intValue(), event.getPin()))
                    .findFirst()
                    .get();
            if (eventFileSensor != null) {
                handleEvent(eventFileSensor, event);
            }
        }
    }

    private void handleEvent(LeafHouseComponents.Sensor eventFileSensor,
            GpioPinDigitalStateChangeEvent event) {
        System.out.println("Event capturated asociated to +" + eventFileSensor.toString());
        boolean sensorSate = event.getState().isHigh();
        switch (SensorType.valueOf(eventFileSensor.getType())) {
            case EVENT_FIRED:
                boolean securityBreach = isSecurityBreach();
                SensorState sensorState = saveSensorState(eventFileSensor, sensorSate, securityBreach);
                if (securityBreach) {
                    SecurityThreshhold.getInstance().estimulate();
                    try {
                        gmailSender.send(sensorState);
                    } catch (MessagingException ex) {
                        Logger.getLogger(LeafHouseGpioHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                SensorOnEvent onEvent = SensorOnEvent.valueOf(eventFileSensor.getOnEvent());
                if (onEvent != null) {
                    if (onEvent.equals(SensorOnEvent.CAM_SHOOT)) {
                        // FALTARIA CODIGO ACA
                    }
                }
                break;
            case CONCURRENT:

                break;
        }
    }

    private boolean isSecurityBreach() {
        return SecurityService.getLastSecurityState().isSecurityEnabled();
    }

    private SensorState saveSensorState(LeafHouseComponents.Sensor eventFileSensor,
            boolean sensorSate, boolean securityBreach) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        SensorState sensorState = new SensorState();
        sensorState.setSensor(getSensorByGpioPin(eventFileSensor));
        sensorState.setFireDate(Calendar.getInstance());
        sensorState.setSecurityBreach(securityBreach);
        sensorState.setUser(defaultUser);
        session.getTransaction().commit();
        session.close();
        return sensorState;
    }

    private Sensor getSensorByGpioPin(LeafHouseComponents.Sensor eventFileSensor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "from Sensor where sensorGpioPin = :gpioPin";
        Sensor sensor = (Sensor) session.createQuery(query)
                .setParameter("gpioPin", eventFileSensor.getAttachedGpioPin()
                        .intValue())
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return sensor;
    }

    private User getDefaultUser() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "from User where userLeafHouse = :u";
        User defaultUserAux = (User) session.createQuery(query)
                .setParameter("u", Boolean.TRUE)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return defaultUserAux;
    }

}
