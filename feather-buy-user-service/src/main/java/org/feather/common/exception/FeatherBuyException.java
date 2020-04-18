package org.feather.common.exception;


import org.feather.common.constants.Constants;

public class FeatherBuyException extends RuntimeException{

    private int statusCode = Constants.RESP_STATUS_INTERNAL_ERROR;

    public FeatherBuyException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
    public FeatherBuyException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
