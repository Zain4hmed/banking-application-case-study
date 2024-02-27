package com.microservice.customer.service.service;

import java.util.List;

import com.microservice.customer.service.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    Customer addCustomer(Customer customer , String trackingId);
    Customer getCustomerById(String id , String trackingId);
    List<Customer> getAllCustomers(String trackingId);
    Customer updateCustomerById(String id,Customer customer);
    void deleteCustomerById(String id);
    Customer getByUsername(String username , String trackingId);
}