/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.UriInfo;
import org.klaw.leafhouse.ws.model.ApiInfo;
import org.klaw.leafhouse.ws.model.Link;
import org.klaw.leafhouse.ws.resource.*;

/**
 *
 * @author Klaw Strife
 */
public class ApiInfoService {
    
    private static final String VERSION = "v1.0";
    private static final String HELP_URL = "http://www.leafhouse.com/api";
    private static final String DEVELOPER = "klawx3@gmail.com";
    private static final String WELCOME_MESSAGE = "Hello leafhouse now on json";

    public static ApiInfo getApiInfo(UriInfo uriInfo) {
        List<Link> rootResourcesPath = new ArrayList<>();
        rootResourcesPath.add(new Link(uriInfo.getAbsolutePathBuilder()
                .path(ApiInfoResource.class).build().toString(),
                "Api-App information"));
        rootResourcesPath.add(new Link(uriInfo.getBaseUriBuilder()
                .path(SecurityResource.class).build().toString(),
                "Show security information"));
        rootResourcesPath.add(new Link(uriInfo.getBaseUriBuilder()
                .path(LocationsResource.class).build().toString(),
                "Show all leafhouse locations"));
        rootResourcesPath.add(new Link(uriInfo.getBaseUriBuilder()
                .path(SensorsResource.class).build().toString(),
                "Show all leafhouse sensors"));
        rootResourcesPath.add(new Link(uriInfo.getBaseUriBuilder()
                .path(UsersResource.class).build().toString(),
                "Show all users"));
        rootResourcesPath.add(new Link(uriInfo.getBaseUriBuilder()
                .path("application.wadl").build().toString(),
                "WADL"));
        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setDeveloper(DEVELOPER);
        apiInfo.setHelpUrl(HELP_URL);
        apiInfo.setVersion(VERSION);
        apiInfo.setWelcomeMessage(WELCOME_MESSAGE);
        apiInfo.setRootResources(rootResourcesPath);
        return apiInfo;
    }

}
