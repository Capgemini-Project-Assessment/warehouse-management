package com.retail.warehouse_management.exceptions;

public class ProductNotFoundException extends RuntimeException{
    private final ErrorResponse errorResponse;
    public ProductNotFoundException(ErrorResponse errorResponse) {
        super(errorResponse.message());
        this.errorResponse = errorResponse;
    }
    public ErrorResponse getErrorResponse(){
        return errorResponse;
    }
}
