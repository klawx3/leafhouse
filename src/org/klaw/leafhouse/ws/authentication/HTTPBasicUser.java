/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.authentication;

/**
 *
 * @author Klaw Strife
 */
public class HTTPBasicUser {

    private final String username;
    private final String password;

    public HTTPBasicUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "BasicAuthUser{" + "username=" + username + ", password=" + password + '}';
    }

}
