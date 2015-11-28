/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.db.dto;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Klaw Strife
 */
@Entity
public class ActuatorState implements Serializable {
    @Id @GeneratedValue
    private int actuatorStateid;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fireDate;
    @ManyToOne @JoinColumn(name = "actuatorId")
    private Actuator actuator;
    @ManyToOne
    private User user;

    public int getActuatorStateid() {
        return actuatorStateid;
    }

    public void setActuatorStateid(int actuatorStateid) {
        this.actuatorStateid = actuatorStateid;
    }

    public Calendar getFireDate() {
        return fireDate;
    }

    public void setFireDate(Calendar fireDate) {
        this.fireDate = fireDate;
    }

    public Actuator getActuator() {
        return actuator;
    }

    public void setActuator(Actuator actuator) {
        this.actuator = actuator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
