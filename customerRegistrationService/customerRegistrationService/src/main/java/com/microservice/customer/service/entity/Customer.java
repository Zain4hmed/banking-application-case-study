package com.microservice.customer.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Customer {

    @Id
    private String customerId;
    private String name;
    private int age;
    private String email;
    private String mobileNumber;
    private double salary;
    private String gender;
    private String username;
    private String password;
    private Boolean accountCreated = false;

    @Transient
    private String confirmPassword;
}