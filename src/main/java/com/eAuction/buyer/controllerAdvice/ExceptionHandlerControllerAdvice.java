package com.eAuction.buyer.controllerAdvice;

import com.eAuction.buyer.exception.*;
import com.eAuction.buyer.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = InvalidProductDetailException.class)
    public ResponseEntity<ErrorResponse> invalidProductDetailHandler(InvalidProductDetailException exception) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Product Details Invalid " + exception.getMessage()),respHeaders, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = InvalidPersonDetailException.class)
    public ResponseEntity<ErrorResponse> invalidPersonDetailHandler(InvalidPersonDetailException exception) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Person Details Invalid " + exception.getMessage()),respHeaders, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = AlreadyExistingBidException.class)
    public ResponseEntity<ErrorResponse> personNotFoundExceptionHandler(AlreadyExistingBidException exception) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()),respHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AllReadyExistDataException.class)
    public ResponseEntity<ErrorResponse> allReadyExistDataExceptionHandler(AllReadyExistDataException exception) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()),respHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InvalidProductBidDetailException.class)
    public ResponseEntity<ErrorResponse> invalidProductBidDetailExceptionHandler(InvalidProductBidDetailException exception) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), exception.getMessage()),respHeaders, HttpStatus.NOT_ACCEPTABLE);
    }
}
