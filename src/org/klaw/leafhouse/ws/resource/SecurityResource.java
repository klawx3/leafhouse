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
import javax.ws.rs.core.Context;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.klaw.leafhouse.db.dto.SecurityState;
import org.klaw.leafhouse.db.dto.SensorState;
import org.klaw.leafhouse.ws.model.SecurityInformation;
import org.klaw.leafhouse.ws.beans.BoundsBean;

import org.klaw.leafhouse.ws.service.*;

/**
 *
 * @author Klaw Strife
 */
@Path("/security")
public class SecurityResource {
    @Context UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON )
    public SecurityInformation getSecurity() {        
        return SecurityService.getSecurityInformation(uriInfo);
    }

    @Path("/states")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SecurityState> getSecurityStates(@BeanParam BoundsBean bounds) {
        List<SecurityState> allSecurityState = null;
        if (bounds.isPaginationSet()) {
            allSecurityState = SecurityService.getAllSecurityStates(bounds.getStart(), bounds.getSize());
        } else if (bounds.isDateBoundSet()) {
            Date startDate = new Date(bounds.getStartDate());
            Date endDate = new Date(bounds.getEndDate());
            allSecurityState = SecurityService.getAllSecurityStates(startDate, endDate);
        } else {
            allSecurityState = SecurityService.getAllSecurityStates(0,9); // standar
        }
        //for -> solo muestra informacion necesaria para el cliente
        allSecurityState.forEach(securityState -> securityState.setRawUserName(securityState.getUser().getUserName()));
        return allSecurityState;
    }

    @Path("/breach")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorState> getSecurityBreach(@BeanParam BoundsBean bounds) {
        if (bounds.isPaginationSet()) {
            return SecurityService.getAllSecurityBreach(bounds.getStart(),bounds.getSize());
        }
        if (bounds.isDateBoundSet()) {
            Date startDate = new Date(bounds.getStartDate());
            Date endDate = new Date(bounds.getEndDate());
            return SecurityService.getAllSecurityBreach(startDate,endDate);
        }
        return SecurityService.getAllSecurityBreach(0,9);

    }

    @Path("/states/{stateId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SecurityState getSecurityStates(@PathParam("stateId") int stateId) {        
        SecurityState securityState = SecurityService.getSecurityState(stateId);
        securityState.setRawUserName(securityState.getUser().getUserName());
        return securityState;        
    }

}
