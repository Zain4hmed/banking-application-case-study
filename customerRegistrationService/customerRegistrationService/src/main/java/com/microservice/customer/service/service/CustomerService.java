package com.microservice.customer.service.service;

import java.util.List;

import com.microservice.customer.service.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    Customer addCustomer(Customer customer);

    Customer getCustomerById(String id);

    List<Customer> getAllCustomers();

    Customer updateCustomerById(String id,Customer customer);

    void deleteCustomerById(String id);

    String toggleAccountCreationBoolean(String customerId);

    Customer getByUsername(String username);

    Customer login(String id , String password);

}