package com.retail.warehouse_management.exceptions;

import org.springframework.http.HttpStatus;


public record ErrorResponse (
    String message,
    HttpStatus httpStatus
) {};
