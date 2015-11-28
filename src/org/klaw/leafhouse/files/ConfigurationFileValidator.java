/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.files;

import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.klaw.binding.LeafHouseComponents;

/**
 *
 * @author Klaw Strife
 */
public class ConfigurationFileValidator {
    
    private final LeafHouseComponents leafHouseComponents;
    private final List<String> invalidEntries;

    public ConfigurationFileValidator(LeafHouseComponents leafHouseComponents) {
        this.leafHouseComponents = leafHouseComponents;
        invalidEntries = new ArrayList<>();
        validate();
    }

    public boolean isValid(){
        return invalidEntries.isEmpty();
    }
    
    public List<String> getInvalidEntries() {
        return invalidEntries;
    }

    private void validate() {

        int totalElements = leafHouseComponents.getActuator().size()
                + leafHouseComponents.getSensor().size();
        int n = 0;
        int[] pins = new int[totalElements];
        for (LeafHouseComponents.Actuator actuator : leafHouseComponents.getActuator()) {
            //DEFAULT PIN STATE
            try {
                PinState.valueOf(actuator.getDefaultPinState());                
            } catch (IllegalArgumentException e) {
                invalidEntries.add(e.getMessage());
            }
            //SHUTDOWN PIN STATE
            try {
                PinState.valueOf(actuator.getShutdownOptions().getPinState());                
            } catch (IllegalArgumentException e) {
                invalidEntries.add(e.getMessage());
            }
            int gpioPin = actuator.getAttachedGpioPin().intValue();
            pins[n++] = gpioPin;
        }
        for (LeafHouseComponents.Sensor sensor : leafHouseComponents.getSensor()) {
            //PIN PULL RESISTANCE
            try {
                PinPullResistance.valueOf(sensor.getPinPullResistance());
            } catch (IllegalArgumentException e) {
                invalidEntries.add(e.getMessage());
            }
            //PIN STATE
            try {
                PinState.valueOf(sensor.getShutdownOptions().getPinState());
            } catch (IllegalArgumentException e) {
                invalidEntries.add(e.getMessage());
            }
            int gpioPin = sensor.getAttachedGpioPin().intValue();
            pins[n++] = gpioPin;
        }
        Arrays.sort(pins);
        for (int i = 0; i < totalElements; i++) {
            if (i + 1 < totalElements) {
                if (pins[i] == pins[i + 1]) {
                    invalidEntries.add("Gpio pin (" + pins[i] + ") must be unique");
                }
            }
        }

    }


}
