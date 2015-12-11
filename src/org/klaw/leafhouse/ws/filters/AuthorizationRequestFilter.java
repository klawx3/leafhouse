/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.filters;

//import com.sun.jersey.spi.container.ContainerRequest;
import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.klaw.leafhouse.db.dto.User;
import org.klaw.leafhouse.ws.HTTPServer;
import org.klaw.leafhouse.ws.authentication.HTTPBasicAuthUtil;
import org.klaw.leafhouse.ws.authentication.HTTPBasicUser;
import org.klaw.leafhouse.ws.service.UserService;


//import com.sun.jersey.spi.container.ContainerRequestFilter;
/**
 *
 * @author Klaw Strife
 */
public class AuthorizationRequestFilter implements ContainerRequestFilter {

    private static final Logger logger = LogManager.getLogger(AuthorizationRequestFilter.class.getSimpleName());
    private static final String BASIC = "Basic";
    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        String auth = crc.getHeaders().getFirst("Authorization");

        if (auth == null) {
            logger.warn("Unauthorized request, null authorization");
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        HTTPBasicUser user = HTTPBasicAuthUtil.decodeBasicAuthWithBasicString(auth);

        if (user == null) {
            logger.warn("Unauthorized request, null user");
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        if (!isValid(user)) {
            logger.warn("Unauthorized request, bad user/password");
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
    }

    private boolean isValid(HTTPBasicUser httpUser) {
        User validUser = null;
        for (User dtoUser : UserService.getAllUsers()) {
            if (dtoUser.getUserName().equals(httpUser.getUsername())
                    && dtoUser.getUserPassword().equals(httpUser.getPassword())) {
                validUser = dtoUser;
                break;
            }
        }
        return validUser != null;

    }

}
