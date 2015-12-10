/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.resource;

import java.net.URI;
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

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SecurityInformation getSecurityInformation() {
        SecurityInformation securityInformation = SecurityService.getSecurityInformation(uriInfo);
        securityInformation.setLink(getUriFromSelf(), "self");
        securityInformation.setLink(getUriFromStates(), "security changes");
        securityInformation.setLink(getUriFromBreach(), "security breach");
        return securityInformation;
    }

    @Path("/states")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SecurityState> getSecurityStates(@BeanParam BoundsBean bounds) {
        List<SecurityState> allSecurityState;
        if (bounds.isPaginationSet()) {
            allSecurityState = SecurityService.getAllSecurityStates(bounds.getStart(), bounds.getSize());
        } else if (bounds.isDateBoundSet()) {
            Date startDate = new Date(bounds.getStartDate());
            Date endDate = new Date(bounds.getEndDate());
            allSecurityState = SecurityService.getAllSecurityStates(startDate, endDate);
        } else {
            allSecurityState = SecurityService.getAllSecurityStates(0, 9); // standar
        }
        if (allSecurityState != null) { // precaucion
            if (allSecurityState.isEmpty()) {// esto arregla el contenido vacio
                return null;
            }
            allSecurityState.forEach(securityState
                    -> securityState.setRawUserName(securityState.getUser()
                            .getUserName()));  //for -> solo muestra informacion necesaria para el cliente
        }
        return allSecurityState;
    }

    @Path("/breach")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorState> getSecurityBreach(@BeanParam BoundsBean bounds) {
        List<SensorState> securityBreach;
        if (bounds.isPaginationSet()) {
            securityBreach = SecurityService.getAllSecurityBreach(bounds.getStart(), bounds.getSize());
        } else if (bounds.isDateBoundSet()) {
            Date startDate = new Date(bounds.getStartDate());
            Date endDate = new Date(bounds.getEndDate());
            securityBreach = SecurityService.getAllSecurityBreach(startDate, endDate);
        } else {
            securityBreach = SecurityService.getAllSecurityBreach(0, 9);
        }
        if (securityBreach != null) {
            if (!securityBreach.isEmpty()) {
                return securityBreach;
            }
        }
        return null;
    }

    @Path("/states/{stateId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SecurityState getSecurityStates(@PathParam("stateId") int stateId) {

        return SecurityService.getSecurityState(stateId);
    }

    private String getUriFromSelf() {
        URI build = uriInfo.getBaseUriBuilder()
                .path(SecurityResource.class)
                .build();
        return build.toString();
    }

    private String getUriFromStates() {
        URI build = uriInfo.getBaseUriBuilder()
                .path(SecurityResource.class)
                .path("states")
                .build();
        return build.toString();
    }

    private String getUriFromBreach() {
        URI build = uriInfo.getBaseUriBuilder()
                .path(SecurityResource.class)
                .path("breach")
                .build();
        return build.toString();
    }
}
