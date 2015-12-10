/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.db.dto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import org.klaw.binding.components.LeafHouseComponents;
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
    @Column(unique = true)
    @XmlElement
    private int actuatorGpioPin;
    @XmlElement
    private String actuatorName;
    @XmlElement
    private String actuatorDescription;
    @OneToOne
    @XmlElement
    private Location actuatorLocation;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actuator", targetEntity = ActuatorState.class)
    private List<ActuatorState> actuatorStates;

    public Actuator() {
    }

    public Actuator(String actuatorName, String actuatorDescription, int actuatorGpioPin, Location actuatorLocation) {
        this.actuatorName = actuatorName;
        this.actuatorDescription = actuatorDescription;
        this.actuatorGpioPin = actuatorGpioPin;
        this.actuatorLocation = actuatorLocation;
    }

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

    public String getActuatorDescription() {
        return actuatorDescription;
    }

    public void setActuatorDescription(String actuatorDescription) {
        this.actuatorDescription = actuatorDescription;
    }

    public boolean isEqualsToFileActuator(LeafHouseComponents.Actuator fileActuator) {
        return fileActuator.getAttachedGpioPin().intValue() == actuatorGpioPin
                && fileActuator.getName().equals(actuatorName)
                && fileActuator.getDescription().equals(actuatorDescription)
                && fileActuator.getLocation().equals(actuatorLocation.getLocationName());
    }

    public static Actuator convertFileActuator(LeafHouseComponents.Actuator fileActuator, Location location) {
        return new Actuator(fileActuator.getName(), fileActuator.getDescription(),
                fileActuator.getAttachedGpioPin().intValue(), location);
    }

    @Override
    public String toString() {
        return "Actuator{" + "actuatorId=" + actuatorId + ", actuatorGpioPin="
                + actuatorGpioPin + ", actuatorName=" + actuatorName + ", actuatorDescription=" + actuatorDescription
                + ", actuatorLocation=" + actuatorLocation + ", actuatorStates=" + actuatorStates + '}';
    }

}
