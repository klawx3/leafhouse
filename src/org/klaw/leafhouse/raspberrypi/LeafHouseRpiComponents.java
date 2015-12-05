/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.raspberrypi;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import org.klaw.leafhouse.ws.service.SecurityService;

import org.klaw.binding.components.LeafHouseComponents.Sensor;
import org.klaw.leafhouse.mail.GMailSender;
/**
 *
 * @author Klaw Strife
 */
public class LeafHouseRpiComponents implements GpioPinListenerDigital {

    private final ComponentsBuilder builder;
    private GMailSender gmailSender;

    @SuppressWarnings("LeakingThisInConstructor")
    public LeafHouseRpiComponents(org.klaw.binding.components.LeafHouseComponents leafHouseComponents,
            GMailSender gmailSender) {
        this.gmailSender = gmailSender;
        builder = new ComponentsBuilder(leafHouseComponents);
        builder.addListener(this);
        builder.buildComponents();

    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        System.out.println(" --> (SENSOR) GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
        if (SecurityService.getLastSecurityState().isSecurityEnabled()) {
            Sensor eventSensor = builder.getLeafHouseComponents().getSensor()
                    .stream()
                    .filter(s-> ComponentsUtil.isGpioPinEquals(s.getAttachedGpioPin().intValue(), event.getPin()))
                    .findFirst()
                    .get();

            if (eventSensor != null) {
                System.out.println("Event capturated asociated to +" + eventSensor.toString());

            } else {
                System.err.println("Event sensor not found");
            }

        }
    }


}
