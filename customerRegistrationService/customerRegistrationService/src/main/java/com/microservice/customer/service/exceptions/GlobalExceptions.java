package com.microservice.customer.service.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions extends RuntimeException {

    @ExceptionHandler(RegistrationUnsuccessfullException.class)
    public ResponseEntity<Map<String,Object>> handlerResourceNotFoundException(RegistrationUnsuccessfullException e){
        Map<String , Object> map = new HashMap<>();
        map.put("message",e.getMessage());
        map.put("success",false);
        map.put("status",HttpStatus.BAD_REQUEST);

        return ResponseEntity.badRequest().body(map);
    }

}