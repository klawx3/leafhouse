/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.exceptions;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.klaw.leafhouse.ws.model.ErrorCode;
import org.klaw.leafhouse.ws.model.ErrorMessage;

/**
 *
 * @author Klaw Strife
 */
@Provider
public class GenericException implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        return Response.serverError()
                .entity(new ErrorMessage("General error", ErrorCode.GENERIC_ERROR.getErrorCode(), "api.leafhouse.cl/doc"))
                .build();
    }

}
