/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws;

import java.util.logging.Logger;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.klaw.leafhouse.ws.exceptions.GenericException;
import org.klaw.leafhouse.ws.filters.AuthorizationRequestFilter;
import org.klaw.leafhouse.ws.resource.ActuatorResource;
import org.klaw.leafhouse.ws.resource.ApiInfoResource;
import org.klaw.leafhouse.ws.resource.LocationsResource;
import org.klaw.leafhouse.ws.resource.SecurityResource;
import org.klaw.leafhouse.ws.resource.SensorsResource;
import org.klaw.leafhouse.ws.resource.TestResource;
import org.klaw.leafhouse.ws.resource.UsersResource;

/**
 *
 * @author Klaw Strife
 */
public class LeafHouseResourceConfig extends ResourceConfig {
    
    
    public LeafHouseResourceConfig() {        
        super(
                //REST
                SecurityResource.class,
                SecurityResource.class,
                UsersResource.class,
                SensorsResource.class,
                TestResource.class,
                LocationsResource.class,
                ApiInfoResource.class,
                ActuatorResource.class,
                //Filters
                //AuthorizationRequestFilter.class,
                //Exceptions
                //GenericException.class,
                //JSON
                JacksonFeature.class
        );

//        property(ServerProperties.TRACING, "ALL");
//        property(ServerProperties.TRACING_THRESHOLD, "VERBOSE");
    }

    
}
