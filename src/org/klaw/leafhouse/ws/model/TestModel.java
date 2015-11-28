/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Klaw Strife
 */
@XmlRootElement
public class TestModel {

    private String testString;

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

}
