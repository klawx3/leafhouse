/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.args;

import com.beust.jcommander.Parameter;

/**
 *
 * @author Klaw Strife
 */
public class ArgNewUser {
    
    @Parameter(names = "-user", description = "User name")
    private String userName;
    @Parameter(names = "-pass", description = "User password", password = true)
    private String userPassword;
    @Parameter(names = "-mail", description = "User e-mail")
    private String userMail;
    
}
