/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.raspberrypi;

import com.pi4j.io.gpio.GpioPin;

/**
 *
 * @author Klaw Strife
 */
public class ComponentsUtil {

    public static boolean isGpioPinEquals(int sensor, GpioPin gpioPin) {
        return ComponentsUtil.getGpioPinName(sensor).equals(gpioPin.getName());
    }

    public static String getGpioPinName(int pin) {
        return "GPIO " + pin;
    }
}
