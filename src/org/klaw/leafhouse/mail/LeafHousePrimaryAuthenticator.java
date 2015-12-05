/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author Klaw Strife
 */
public class LeafHousePrimaryAuthenticator extends Authenticator {
    
    private final String username;
    private final String password;

    public LeafHousePrimaryAuthenticator(String username,String password) {
        super();
        this.username = username;
        this.password = password;

    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

}
