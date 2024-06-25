package com.retail.warehouse_management.exceptions;

public class FileLoadException extends RuntimeException{
    private final ErrorResponse errorResponse;
    public FileLoadException(ErrorResponse errorResponse) {
        super(errorResponse.message());
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse(){
        return errorResponse;
    }
}
