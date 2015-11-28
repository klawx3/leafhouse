/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.exceptions;

import java.util.List;

/**
 *
 * @author Klaw Strife
 */
public class LeafHouseComponentsFileException extends Exception{

    public LeafHouseComponentsFileException(String message) {
        super(message);
    }

    public LeafHouseComponentsFileException(List<String> messages) {
        super(messages.stream().reduce("",
                (a,b) -> a.concat(", ").concat(b)));
    }

}
