/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.resource;

import java.util.Date;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.klaw.leafhouse.db.dto.SensorState;
import org.klaw.leafhouse.ws.beans.BoundsBean;
import org.klaw.leafhouse.ws.service.SensorService;

/**
 *
 * @author Klaw Strife
 */
@Path("/")
public class SensorStatesResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorState> getAllSensorStates(@PathParam("sensorId") int sensorId,
            @BeanParam BoundsBean bounds) {
        if(bounds.isPaginationSet()){
            return SensorService.getSensorStates(sensorId,bounds.getStart(),bounds.getSize());
        }
        if(bounds.isDateBoundSet()){
            Date startDate = new Date(bounds.getStartDate());
            Date endDate =new Date(bounds.getEndDate());
            return SensorService.getSensorStates(sensorId,startDate,endDate);            
        }
        return SensorService.getSensorStates(sensorId,0,9); 
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{sensorStateId}")
    public SensorState getSensorState(@PathParam("sensorId") int sensorId,
            @PathParam("sensorStateId") int sensorStateId){
        return SensorService.getSensorState(sensorId,sensorStateId);
    }

}
