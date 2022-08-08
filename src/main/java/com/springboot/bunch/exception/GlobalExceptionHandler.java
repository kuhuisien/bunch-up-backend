package com.springboot.bunch.exception;

import com.springboot.bunch.payload.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    // handle specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFoundException(
            ResourceNotFoundException exception, WebRequest webRequest
    ) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    // handle global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleGlobalException(
            Exception exception, WebRequest webRequest
    ) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
