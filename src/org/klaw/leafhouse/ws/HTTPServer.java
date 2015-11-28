/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class HTTPServer {

    private static final Logger LOGGER = Logger.getLogger(HTTPServer.class.getName());
    public static final URI BASE_URI = getBaseURI();
    public static final int PORT = 8081 ;
    protected ResourceConfig rc;
    protected HttpServer httpServer;

    public HTTPServer(boolean startServer) {
        rc = new LeafHouseResourceConfig();
        if(startServer)
            httpServerStart();
    }

    public final void httpServerStart() {
        try {
            httpServer = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                httpServer.shutdownNow();
            }));
            printInfo();
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void httpServerShutdown() {
        httpServer.shutdownNow();
    }
    
    private void printInfo(){
        System.out.printf("Server running on: %s\n",BASE_URI);
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost").port(PORT).build();
    }

}
