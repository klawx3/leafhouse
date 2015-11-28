/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.ws.model;

/**
 *
 * @author Klaw Strife
 */
public enum ErrorCode {

    GENERIC_ERROR(0),
    NO_AUTHORIZATED(402);

    private final int errorCode;

    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
    
}
