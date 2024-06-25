package com.retail.warehouse_management.exceptions;

public class ArticleNotFoundException extends RuntimeException{
    private final ErrorResponse errorResponse;
    public ArticleNotFoundException(ErrorResponse errorResponse) {
        super(errorResponse.message());
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse(){
        return errorResponse;
    }
}
