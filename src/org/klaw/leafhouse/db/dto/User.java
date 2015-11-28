/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.db.dto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Klaw Strife
 */

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class User implements Serializable {
    @Id @Column(length = 25) @XmlElement
    private String userName;
    @Column(nullable = false) @XmlElement
    private String userPassword;
    @Column(nullable = false) @XmlElement
    private String userEmail;
    @Column(nullable = false) @XmlElement
    private boolean userAdmin;
    @Column(nullable = false) @XmlElement
    private boolean userLeafHouse;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SecurityState.class, mappedBy = "user")
    private List<SecurityState> securityStates;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SensorState.class, mappedBy = "user")
    private List<SensorState> sensorStates;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = ActuatorState.class,mappedBy = "user")
    private List<ActuatorState> actuatorStates;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<SecurityState> getSecurityStates() {
        return securityStates;
    }

    public void setSecurityStates(List<SecurityState> securityStates) {
        this.securityStates = securityStates;
    }

    public List<SensorState> getSensorStates() {
        return sensorStates;
    }

    public void setSensorStates(List<SensorState> sensorStates) {
        this.sensorStates = sensorStates;
    }

    public List<ActuatorState> getActuatorStates() {
        return actuatorStates;
    }

    public void setActuatorStates(List<ActuatorState> actuatorStates) {
        this.actuatorStates = actuatorStates;
    }

    public boolean isUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(boolean userAdmin) {
        this.userAdmin = userAdmin;
    }

    public boolean isUserLeafHouse() {
        return userLeafHouse;
    }

    public void setUserLeafHouse(boolean userLeafHouse) {
        this.userLeafHouse = userLeafHouse;
    }
}
