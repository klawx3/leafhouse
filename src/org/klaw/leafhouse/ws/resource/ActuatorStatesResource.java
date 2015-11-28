/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.resource;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.klaw.leafhouse.db.dto.ActuatorState;
import org.klaw.leafhouse.ws.beans.BoundsBean;
import org.klaw.leafhouse.ws.service.ActuatorService;


/**
 *
 * @author Klaw Strife
 */
@Path("/")
public class ActuatorStatesResource {
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<ActuatorState> getActuatorStates(@PathParam("actuatorId") int actuatorId,
            @BeanParam BoundsBean bounds){
        if(bounds.isPaginationSet()){
            return ActuatorService.getActuatorStates(actuatorId,bounds.getStart(),bounds.getSize());
        }
        if(bounds.isDateBoundSet()){
            Date startDate = new Date(bounds.getStartDate());
            Date endDate = new Date(bounds.getEndDate());
            return ActuatorService.getActuatorStates(actuatorId, startDate, endDate);
        }
        //default bounds
        return ActuatorService.getActuatorStates(actuatorId,1,10);
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{actuatorStateId}")
    public ActuatorState getActuatorState(@PathParam("actuatorId") int actuatorId,
            @PathParam("actuatorStateId") int actuatorStateId){        
        return ActuatorService.getActuatorState(actuatorId,actuatorStateId);
        
    }
    
}
