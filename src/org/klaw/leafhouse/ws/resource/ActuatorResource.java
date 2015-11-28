/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.resource;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.klaw.leafhouse.db.dto.Actuator;
import org.klaw.leafhouse.ws.service.ActuatorService;

/**
 *
 * @author Klaw Strife
 */
@Path("/actuators")
public class ActuatorResource {
    
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Actuator> getAllActuators(){
        return ActuatorService.getAllActuators();        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{actuatorId}")
    public Actuator getActuator(@PathParam("actuatorId") int actuatorId){
        return ActuatorService.getActuator(actuatorId);        
    }
    
    @Path("{actuatorId}/states")
    public ActuatorStatesResource getActuatorStates(){
        return new ActuatorStatesResource();
    }
    
}
