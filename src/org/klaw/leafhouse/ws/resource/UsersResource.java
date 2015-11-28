/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.resource;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.klaw.leafhouse.db.dto.User;
import org.klaw.leafhouse.ws.service.UserService;

/**
 *
 * @author Klaw Strife
 */
@Path("/users")
public class UsersResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<User> getUsers(@QueryParam("start") int start,
             @QueryParam("end") int end) {
        System.out.println("start" + start);
        System.out.println("end" + end);
        return UserService.getAllUsers();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public User createUser(User user) {
        return UserService.createUser(user);
    }

    @Path("/{userName}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public User getUser(@PathParam("userName") String userName) {
        return UserService.getUser(userName);
    }
    
    @Path("/{userName}")
    @DELETE
    @Produces(MediaType.APPLICATION_XML)
    public User delUser(@PathParam("userName") String userName) {
        return UserService.deleteUser(userName);
    }


    
}
