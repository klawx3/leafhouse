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
import org.klaw.leafhouse.db.dto.Sensor;
import org.klaw.leafhouse.db.dto.SensorState;
import org.klaw.leafhouse.ws.service.SensorService;

/**
 *
 * @author Klaw Strife
 */
@Path("/sensors")
public class SensorsResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getSensors(){
        return SensorService.getSensors();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{sensorId}")
    public Sensor getSensor(@PathParam("sensorId") int sensorId){
        return SensorService.getSensor(sensorId);
    }
    

    @Path("{sensorId}/states")
    public SensorStatesResource getSensorsStates(){
        return new SensorStatesResource();
    }
    

    
    
}
