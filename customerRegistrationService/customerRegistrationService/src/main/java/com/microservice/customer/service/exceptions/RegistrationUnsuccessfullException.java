package com.microservice.customer.service.exceptions;

public class RegistrationUnsuccessfullException extends RuntimeException{

    public RegistrationUnsuccessfullException(String message) {
        super(message);
    }

    public RegistrationUnsuccessfullException() {
        super("Registration Unsuccessfull");
    }
}