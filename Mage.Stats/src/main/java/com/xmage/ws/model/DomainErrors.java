package com.xmage.ws.model;

/**
 * Domain status error codes.
 *
 * @author noxx
 */
public class DomainErrors {

    public enum Errors {
        STATUS_OK(100, "OK"),
        STATUS_SERVER_ERROR(101, "Server Internal Error"),
        STATUS_AUTH_FAILED(102, "Auth failed"),
        STATUS_ACCESS_DENIED(108, "Access denied"),
        STATUS_NOT_ENOUGH_PARAMETERS(301, "Not enough parameters"),
        STATUS_WRONG_PARAM_FORMAT(302, "Wrong param format"),
        STATUS_NOT_IMPLEMENTED(800, "Not implemented"),
        STATUS_NOT_FOUND(1000, "Resource Not Found");

        private int code;
        private String message;

        Errors(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public void setCustomMessage(String message) {
            this.message = message;
        }
    }

}
