/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws;

import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class HTTPServer {

    private static final Logger logger = LogManager.getLogger(HTTPServer.class.getSimpleName());
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
            logger.trace("Trying to start Http Server");
            httpServer = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                httpServer.shutdownNow();
            }));
            logger.info("Http Server running on: " + BASE_URI);
        } catch (IllegalArgumentException ex) {
            logger.fatal(ex.toString());
        } catch(javax.ws.rs.ProcessingException ex){
            logger.fatal(ex.getMessage());
            System.exit(1);
        }
    }

    public void httpServerShutdown() {
        httpServer.shutdownNow();
    }
    
    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0").port(PORT).build();
    }

}
