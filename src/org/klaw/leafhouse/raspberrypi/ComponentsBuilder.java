/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.raspberrypi;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.klaw.binding.components.LeafHouseComponents;
import org.klaw.binding.components.LeafHouseComponents.Actuator;
import org.klaw.binding.components.LeafHouseComponents.Sensor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Klaw Strife
 */
public class ComponentsBuilder {

    private static final GpioController gpioController = GpioFactory.getInstance();

    private final LeafHouseComponents leafHouseComponents;

    private final List<GpioPinDigitalOutput> digitalOutputs;
    private final List<GpioPinDigitalInput> digitalInputs;
    private final List<GpioPinListenerDigital> listeners;

    public ComponentsBuilder(LeafHouseComponents leafHouseComponents) {
        this.leafHouseComponents = leafHouseComponents;
        digitalOutputs = new ArrayList<>();
        digitalInputs = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public void buildComponents() {
        leafHouseComponents.getSensor().forEach(this::buildSensor);
        leafHouseComponents.getActuator().forEach(this::buildActuator);
    }

    public void addSensorListener(GpioPinListenerDigital listener) {
        listeners.add(listener);
    }

    public void removeSensorListener(GpioPinListenerDigital listener) {
        listeners.remove(listener);
    }

    public List<GpioPinDigitalOutput> getDigitalOutputs() {
        return digitalOutputs;
    }

    public List<GpioPinDigitalInput> getDigitalInputs() {
        return digitalInputs;
    }

    public LeafHouseComponents getLeafHouseComponents() {
        return leafHouseComponents;
    }

    private void buildActuator(Actuator actuator) {
        GpioPinDigitalOutput outputPin = gpioController.provisionDigitalOutputPin(
                RaspiPin.getPinByName(ComponentsUtil.getGpioPinName(actuator.getAttachedGpioPin().intValue())),
                PinState.valueOf(actuator.getDefaultPinState()));
        Actuator.ShutdownOptions shutdown = actuator.getShutdownOptions();
        if (shutdown.isEnabled()) {
            outputPin.setShutdownOptions(shutdown.isUnexport(),
                    PinState.valueOf(shutdown.getPinState()));
        }
        digitalOutputs.add(outputPin);
    }

    private void buildSensor(Sensor sensor) {
        GpioPinDigitalInput inputPin = gpioController.provisionDigitalInputPin(
                RaspiPin.getPinByName(ComponentsUtil.getGpioPinName(sensor.getAttachedGpioPin().intValue())),
                PinPullResistance.valueOf(sensor.getPinPullResistance())
        );
        inputPin.addListener(listeners);
        digitalInputs.add(inputPin);
    }

}
