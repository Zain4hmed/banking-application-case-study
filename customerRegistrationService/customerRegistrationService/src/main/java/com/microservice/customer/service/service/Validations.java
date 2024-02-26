package com.microservice.customer.service.service;

import com.microservice.customer.service.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class Validations {

    public static String validateCustomer(Customer customer) {
        boolean validationCheck = true;
        String exceptionMessage = "";
        if(!isValidName(customer.getName())){
            validationCheck = false;
            exceptionMessage += "name must not be null and should be between 10 and 100 characters long \r\n ";
        }
        if(!isValidAge(customer.getAge())) {
            validationCheck = false;
            exceptionMessage += "age must be grater than 18 \r\n " ;
        }
        if(!isValidEmail(customer.getEmail())) {
            validationCheck = false;
            exceptionMessage += "email should be in valid format \r\n ";
        }
        if(!isValidMobileNumber(customer.getMobileNumber())) {
            validationCheck = false;
            exceptionMessage += "mobile number should be numeric and 10 digits long \r\n ";
        }
        if(!isValidSalary(customer.getSalary())) {
            validationCheck = false;
            exceptionMessage += "salary should be numeric and grater than 0 \r\n ";
        }
        if(!isValidGender(customer.getGender())) {
            validationCheck = false;
            exceptionMessage += "gender should be either Male or Female \r\n ";
        }
        if(!isValidUserName(customer.getUsername())) {
            validationCheck = false;
            exceptionMessage += "username should not be null and should be between 8 and 15 characters long \r\n ";
        }
        if(!isValidPassword(customer.getPassword())) {
            validationCheck = false;
            exceptionMessage += "password should not be null and should be between 8 and 15 characters long \r\n ";
        }
        if(!passwordReEntryMatch(customer.getPassword(),customer.getConfirmPassword())) {
            validationCheck = false;
            exceptionMessage += "pasword and confirm password must match \r\n ";
        }

        if(validationCheck) {
            return "success";
        }else {
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

    public static boolean isValidUserName(String username) {
        return username != null && username.length() >= 8 && username.length() <= 15;
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 && password.length() <= 15;
    }

    public static boolean passwordReEntryMatch(String password , String confirmPassword) {
        return password != null && confirmPassword != null && password.equals(confirmPassword);
    }
}