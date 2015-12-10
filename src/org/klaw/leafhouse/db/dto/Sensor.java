/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.db.dto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import org.hibernate.annotations.NaturalId;
import org.klaw.binding.components.LeafHouseComponents;
import org.klaw.leafhouse.types.SensorType;

/**
 *
 * @author Klaw Strife
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Sensor implements Serializable {

    @Id
    @GeneratedValue
    @XmlElement
    private int sensorId;
    @XmlElement
    private String sensorName;
    @XmlElement
    private String description;
    @NaturalId
    @XmlElement
    private int sensorGpioPin;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Location.class)
    @JoinColumn(name = "fkSensorToLocation")
    @XmlElement
    private Location sensorLocation;
    @Enumerated
    @XmlElement
    private SensorType sensorType;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = SensorState.class, fetch = FetchType.LAZY, mappedBy = "sensor")
    private List<SensorState> sensorStates;

    public Sensor() {
    }

    public Sensor(String sensorName, String description,int sensorGpioPin, Location sensorLocation, SensorType sensorType) {
        this.sensorName = sensorName;
        this.description = description;
        this.sensorGpioPin = sensorGpioPin;
        this.sensorLocation = sensorLocation;
        this.sensorType = sensorType;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public int getSensorGpioPin() {
        return sensorGpioPin;
    }

    public void setSensorGpioPin(int sensorGpioPin) {
        this.sensorGpioPin = sensorGpioPin;
    }

    public Location getSensorLocation() {
        return sensorLocation;
    }

    public void setSensorLocation(Location sensorLocation) {
        this.sensorLocation = sensorLocation;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public List<SensorState> getSensorStates() {
        return sensorStates;
    }

    public void setSensorStates(List<SensorState> sensorStates) {
        this.sensorStates = sensorStates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEqualsToFileSensor(LeafHouseComponents.Sensor sensor) {
        return sensor.getAttachedGpioPin().intValue() == sensorGpioPin
                && sensor.getName().equals(sensorName)
                && sensor.getDescription().equals(description)
                && sensor.getLocation().equals(sensorLocation.getLocationName())
                && sensor.getType().equals(sensorType.toString());
    }

    public static Sensor convertFileSensor(LeafHouseComponents.Sensor sensor, Location location) {
        return new Sensor(sensor.getName(), sensor.getDescription(),sensor.getAttachedGpioPin().intValue(),
                location, SensorType.valueOf(sensor.getType()));
    }

    @Override
    public String toString() {
        return "Sensor{" + "sensorId=" + sensorId + ", sensorName=" + sensorName
                + ", description=" + description + ", sensorGpioPin=" + sensorGpioPin
                + ", sensorLocation=" + sensorLocation + ", sensorType=" + sensorType + ", sensorStates=" + sensorStates + '}';
    }

}
