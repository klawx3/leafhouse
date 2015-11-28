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
import org.klaw.leafhouse.db.dto.Location;
import org.klaw.leafhouse.ws.service.LocationService;

/**
 *
 * @author Klaw Strife
 */
@Path("/location")
public class LocationsResource {
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Location> getLocations(){
        return LocationService.getAllLocations();
    }
    
    @Path("{locationId}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Location getLocation(@PathParam("locationId") int locationId){
        return LocationService.getLocation( locationId);
    }
    
    
}
