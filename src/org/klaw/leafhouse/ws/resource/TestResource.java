/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.resource;


import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.klaw.leafhouse.ws.model.ApiInfo;
import org.klaw.leafhouse.ws.beans.BoundsBean;
import org.klaw.leafhouse.ws.model.TestModel;
import org.klaw.leafhouse.ws.service.ApiInfoService;


/**
 *
 * @author Klaw Strife
 */
@Path("/test")
public class TestResource {
    
    @Context UriInfo uriInfo;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test(@BeanParam BoundsBean b) {
        StringBuilder sb = new StringBuilder();
        if (b.isDateBoundSet()) {
            sb.append("startDate:").append(b.getStartDate()).append("\n")
                    .append("endDate").append(b.getEndDate());

        } else {
            sb.append("Date not set");
        }
        sb.append("\n");
        if (b.isPaginationSet()) {
            sb.append("PaginationStart:").append(b.getStart()).append("\n")
                    .append("PaginationSize:").append(b.getSize());
        } else {
            sb.append("Pagination not set");
        }
        sb.append("\n");
        return sb.toString();
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("/wea")
    public Response getWea() {
        TestModel test = new TestModel();
        test.setTestString("weatestifinirina");
        return Response.ok()
                .entity(ApiInfoService.getApiInfo(uriInfo))
                .entity(test)
                .build();
    }

}
