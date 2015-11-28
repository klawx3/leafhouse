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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;



/**
 *
 * @author Klaw Strife
 */
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class SecurityState implements Serializable {

    @Id @GeneratedValue @XmlElement
    private int securityStateId;
    @XmlElement
    private boolean securityEnabled;
    @Temporal(TemporalType.TIMESTAMP)@XmlElement
    private Calendar modifiedDate;
    @ManyToOne @JoinColumn(name = "userId") 
    private User user;
    @Transient @XmlElement(name = "user")
    private String rawUserName;

    public int getSecurityStateId() {
        return securityStateId;
    }

    public void setSecurityStateId(int securityStateId) {
        this.securityStateId = securityStateId;
    }

    public String getRawUserName() {
        return rawUserName;
    }

    public void setRawUserName(String rawUserName) {
        this.rawUserName = rawUserName;
    }

    public int getsecurityStateId() {
        return securityStateId;
    }

    public void setsecurityStateId(int securityStateId) {
        this.securityStateId = securityStateId;
    }

    public boolean isSecurityEnabled() {
        return securityEnabled;
    }

    public void setSecurityEnabled(boolean securityEnabled) {
        this.securityEnabled = securityEnabled;
    }

    public Calendar getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
