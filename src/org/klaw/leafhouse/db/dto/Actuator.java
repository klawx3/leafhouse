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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.NaturalId;
/**
 *
 * @author Klaw Strife
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Actuator implements Serializable {

    @Id
    @GeneratedValue
    @XmlElement
    private int actuatorId;
    @XmlElement
    private String actuatorName;
    @XmlElement
    private String description;
    @NaturalId
    @XmlElement
    private int actuatorGpioPin;
    @OneToOne
    @XmlElement
    private Location actuatorLocation;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actuator", targetEntity = ActuatorState.class)
    private List<ActuatorState> actuatorStates;

    public int getActuatorId() {
        return actuatorId;
    }

    public void setActuatorId(int actuatorId) {
        this.actuatorId = actuatorId;
    }

    public String getActuatorName() {
        return actuatorName;
    }

    public void setActuatorName(String actuatorName) {
        this.actuatorName = actuatorName;
    }

    public int getActuatorGpioPin() {
        return actuatorGpioPin;
    }

    public void setActuatorGpioPin(int actuatorGpioPin) {
        this.actuatorGpioPin = actuatorGpioPin;
    }

    public Location getActuatorLocation() {
        return actuatorLocation;
    }

    public void setActuatorLocation(Location actuatorLocation) {
        this.actuatorLocation = actuatorLocation;
    }

    public List<ActuatorState> getActuatorStates() {
        return actuatorStates;
    }

    public void setActuatorStates(List<ActuatorState> actuatorStates) {
        this.actuatorStates = actuatorStates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
