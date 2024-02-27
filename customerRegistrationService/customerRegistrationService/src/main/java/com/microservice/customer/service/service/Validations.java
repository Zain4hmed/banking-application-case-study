package com.microservice.customer.service.service;

import com.microservice.customer.service.entity.Customer;
import com.microservice.customer.service.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Validations {

    @Autowired
    private static CustomerRepository customerRepository;

    private static final Logger log = LoggerFactory.getLogger(Validations.class);

    public static String validateCustomer(Customer customer , String trackingId) {

        log.info("Tracking Id: {} - Validating customer data", trackingId);

        boolean validationCheck = true;
        String exceptionMessage = "";

        if(!isValidName(customer.getName())){
            validationCheck = false;
            exceptionMessage += "name must not be null and should be between 10 and 100 characters long | ";
        }
        if(!isValidAge(customer.getAge())) {
            validationCheck = false;
            exceptionMessage += "age must be grater than 18 | " ;
        }
        if(!isValidEmail(customer.getEmail())) {
            validationCheck = false;
            exceptionMessage += "email should be in valid format | ";
        }
        if(!isValidMobileNumber(customer.getMobileNumber())) {
            validationCheck = false;
            exceptionMessage += "mobile number should be numeric and 10 digits long | ";
        }
        if(!isValidSalary(customer.getSalary())) {
            validationCheck = false;
            exceptionMessage += "salary should be numeric and grater than 0 | ";
        }
        if(!isValidGender(customer.getGender())) {
            validationCheck = false;
            exceptionMessage += "gender should be either Male or Female | ";
        }
        if(!isValidUserNameLength(customer.getUsername())) {
            validationCheck = false;
            exceptionMessage += "username should not be null and should be between 8 and 15 characters long | ";
        }
        if(!isUniqueUsername(customer.getUsername())){
            validationCheck = false;
            exceptionMessage += "username already exists please try with a different username | ";
        }
        if(!isValidPassword(customer.getPassword())) {
            validationCheck = false;
            exceptionMessage += "password should not be null and should be between 8 and 15 characters long | ";
        }
        if(!passwordReEntryMatch(customer.getPassword(),customer.getConfirmPassword())) {
            validationCheck = false;
            exceptionMessage += "password and confirm password must match | ";
        }

        if(validationCheck) {
            log.info("Tracking Id: {} - Validation successfully for customer data", trackingId);
            return "success";
        }else {
            log.error("Tracking Id: {} - Validation failed for customer data , failed validations :{}", trackingId,exceptionMessage);
            return exceptionMessage;
        }
    }

    public static boolean isValidName(String name) {
        return name != null && name.length() >= 10 && name.length() <= 100;
    }

    public static boolean isValidAge(int age) {
        return age > 18;
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public static boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber != null && mobileNumber.matches("\\d{10}");
    }

    public static boolean isValidSalary(double salary) {
        return salary > 0;
    }

    public static boolean isValidGender(String gender) {
        return gender != null && (gender.equals("Male")|| gender.equals("Female"));
    }

    public static boolean isValidUserNameLength(String username) {
        return username != null && username.length() >= 8 && username.length() <= 15;
    }

    public static boolean isUniqueUsername(String username){
      List<String> uniqueUsername = customerRepository.findAll().stream().map(Customer::getUsername).toList();
        return !uniqueUsername.contains(username);
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 && password.length() <= 15;
    }

    public static boolean passwordReEntryMatch(String password , String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }
}