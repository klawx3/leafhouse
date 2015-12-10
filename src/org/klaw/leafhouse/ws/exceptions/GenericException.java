/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.glassfish.grizzly.utils.Exceptions;
import org.klaw.leafhouse.ws.model.ErrorCode;
import org.klaw.leafhouse.ws.model.ErrorMessage;

/**
 *
 * @author Klaw Strife
 */
@Provider
public class GenericException implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        Logger.getGlobal().log(Level.WARNING, "exception response");
        ex.printStackTrace();
        return Response.status(500).entity(Exceptions.getStackTraceAsString(ex)).type("text/plain")
                .build();
    }

}
