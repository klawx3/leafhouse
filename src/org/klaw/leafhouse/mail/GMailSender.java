/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.mail;

import java.util.List;
import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.klaw.binding.config.LeafHouseConfiguration;
import org.klaw.leafhouse.db.dto.SensorState;
import org.klaw.leafhouse.db.dto.User;
import org.klaw.leafhouse.db.utils.HibernateUtil;
import org.klaw.leafhouse.security.SecurityThreshhold;
import org.klaw.leafhouse.security.SecurityThreshhold.SecurityRisk;

/**
 *
 * @author Klaw Strife
 */
public class GMailSender {

    private final Session session;

    public GMailSender(LeafHousePrimaryAuthenticator auth) {
        Properties props = new Properties();
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        session = Session.getInstance(props,auth);
    }

    public void send(SensorState sensorState) throws MessagingException {
        MimeMessage msg = new MimeMessage(session);
        msg.setSubject("#leafhouse alert");
        SecurityRisk risk = SecurityThreshhold.getInstance()
                .getRiskByEstimulation();
        String message = sensorState.getFireDate().toString() + " on "
                + sensorState.getSensor().getSensorName() + " in "
                + sensorState.getSensor().getSensorLocation().getLocationName()
                + " has been fired, security risk " + risk.toString();
        System.out.println("message -> " + message);
        msg.setText(message);
        msg.addRecipients(RecipientType.TO,
                InternetAddress.parse(getUsersEmails(), false));
        Transport.send(msg);
    }

    public String getUsersEmails() {
        return getUsers().stream()
                .map(u -> u.getUserEmail())
                .reduce("", (a, b) -> a.concat(" ,").concat(b));
    }
    
    private List<User> getUsers(){
        org.hibernate.Session ses = HibernateUtil.getSessionFactory().openSession();
        ses.beginTransaction();
        List<User> list = ses.createQuery("from User where userLeafHouse = false").list();
        ses.getTransaction().commit();
        ses.close();
        return list;        
    }


}
