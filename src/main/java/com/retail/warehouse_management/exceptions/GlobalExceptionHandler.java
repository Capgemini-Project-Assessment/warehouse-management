package com.retail.warehouse_management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFoundException(ArticleNotFoundException ex) {
        return ResponseEntity.status(ex.getErrorResponse().httpStatus())
                .body(ex.getErrorResponse());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(ex.getErrorResponse().httpStatus())
                .body(ex.getErrorResponse());
    }

    @ExceptionHandler(FileLoadException.class)
    public ResponseEntity<ErrorResponse> handleFileLoadException(FileLoadException ex) {
        return ResponseEntity.status(ex.getErrorResponse().httpStatus())
                .body(ex.getErrorResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected Server Error");
    }
}
