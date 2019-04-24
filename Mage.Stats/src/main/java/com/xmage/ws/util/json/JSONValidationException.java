package com.xmage.ws.util.json;

/**
 *
 * @author noxx
 */
public class JSONValidationException extends Exception {

    public JSONValidationException(String message) {
        super(message);
    }

    public JSONValidationException(String message, Exception e) {
        super(message, e);
    }
}
