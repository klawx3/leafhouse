/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.klaw.leafhouse.ws.model.ApiInfo;
import org.klaw.leafhouse.ws.service.ApiInfoService;

/**
 *
 * @author Klaw Strife
 */
@Path("/")
public class ApiInfoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ApiInfo getApiInfo(@Context UriInfo uriInfo) {

        return ApiInfoService.getApiInfo(uriInfo);
    }

}
