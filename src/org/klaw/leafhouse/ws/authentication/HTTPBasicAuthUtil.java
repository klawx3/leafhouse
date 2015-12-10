/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.authentication;

import java.util.Base64;


/**
 *
 * @author Klaw Strife
 */
public class HTTPBasicAuthUtil {

    private static final String BASIC = "Basic";
    
    public static String encodeBasicAuthWithBasicString(HTTPBasicUser user) {
        return "Basic " + HTTPBasicAuthUtil.encodeBasicAuth(user);
    }
    
    public static HTTPBasicUser decodeBasicAuthWithBasicString(String encoded){
        String[] split = encoded.split(" ");
        if (split.length == 2) {
            if (split[0].equalsIgnoreCase(BASIC)) {
                return decodeBasicAuth(split[1]);
            }
        }
        return null;
    }
    

    public static String encodeBasicAuth(HTTPBasicUser user) {
        Base64.Encoder encoder = Base64.getEncoder();
        StringBuilder sb = new StringBuilder();
        sb.append(user.getUsername()).append(":").append(user.getPassword());
        String encodeToString = encoder.encodeToString(sb.toString().getBytes());
        return encodeToString;
    }

    public static HTTPBasicUser decodeBasicAuth(String encoded) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(encoded);
        String aux = new String(decode);
        String[] split = aux.split(":");
        return new HTTPBasicUser(split[0], split[1]);
    }

}
