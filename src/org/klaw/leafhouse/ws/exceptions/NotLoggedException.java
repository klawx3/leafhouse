/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.klaw.leafhouse.ws.model.ErrorCode;
import org.klaw.leafhouse.ws.model.ErrorMessage;

/**
 *
 * @author Klaw Strife
 */

public class NotLoggedException extends WebApplicationException{

    public NotLoggedException() {
        super(Response.status(401).
                entity(new ErrorMessage("No autorizado", ErrorCode.NO_AUTHORIZATED.getErrorCode(), "klaw.doc.cl")).build());
    }


}
