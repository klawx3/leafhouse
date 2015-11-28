/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.filters;

//import com.sun.jersey.spi.container.ContainerRequest;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

//import com.sun.jersey.spi.container.ContainerRequestFilter;

/**
 *
 * @author Klaw Strife
 */
public class AuthorizationRequestFilter implements ContainerRequestFilter {

//    @Override
//    public ContainerRequest filter(ContainerRequest cr) {
//
//        //MultivaluedMap<String, String> requestHeaders = cr.getRequestHeaders();
//        return cr;
//    }

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        System.out.println("Pasando por filter <3");
    }

}
