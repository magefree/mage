package com.xmage.ws.model;


/**
 * Some services may return simple response that is not related to domain or contain minor information.
 * Example: return OK or FALSE only for checking server state.
 *
 * @author noxx
 */
public class SimpleResponse {

    private int code;

    private String message;

    public SimpleResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public SimpleResponse(DomainErrors.Errors error) {
        this(error.getCode(), error.getMessage());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
