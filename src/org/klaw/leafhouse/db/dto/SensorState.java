/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.db.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Klaw Strife
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class SensorState implements Serializable {
    @Id @GeneratedValue  @XmlElement
    private int sensorStateId;
    @XmlElement
    private boolean securityBreach;
    @XmlElement
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fireDate;    
    @ManyToOne @JoinColumn(name = "sensorId") 
    private Sensor sensor;
    @ManyToOne
    private User user;

    public int getSensorStateId() {
        return sensorStateId;
    }

    public void setSensorStateId(int sensorStateId) {
        this.sensorStateId = sensorStateId;
    }

    public boolean isSecurityBreach() {
        return securityBreach;
    }

    public void setSecurityBreach(boolean securityBreach) {
        this.securityBreach = securityBreach;
    }

    public Calendar getFireDate() {
        return fireDate;
    }

    public void setFireDate(Calendar fireDate) {
        this.fireDate = fireDate;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "SensorState{" + "sensorStateId=" + sensorStateId + ", securityBreach=" + securityBreach + ", fireDate=" + fireDate + ", sensor=" + sensor + ", user=" + user + '}';
    }


    
}
