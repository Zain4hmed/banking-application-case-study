package com.microservice.customer.service.controller;

import java.util.List;
import java.util.UUID;

import com.microservice.customer.service.entity.Customer;
import com.microservice.customer.service.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerRegistrationController {

    @Autowired
    private CustomerService customerService;

    Logger log = LoggerFactory.getLogger(CustomerService.class);

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer){
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to create new customer",trackingId);
        return ResponseEntity.ok().body(customerService.addCustomer(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id){
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to create new customer",trackingId);
        return ResponseEntity.ok().body(customerService.getCustomerById(id));
    }

    @GetMapping("/byusername/{username}")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username) {
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to create new customer",trackingId);
        return ResponseEntity.ok().body(customerService.getByUsername(username));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to create new customer",trackingId);
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable String id , @RequestBody Customer customer){
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to create new customer",trackingId);
        return ResponseEntity.ok().body(customerService.updateCustomerById(id, customer));
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id){
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to create new customer",trackingId);
        customerService.deleteCustomerById(id);
    }

    @GetMapping("/createaccount/{customerId}")
    public String toggleAccountCreatedBoolean(@PathVariable String customerId) {
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to create new customer",trackingId);
        return customerService.toggleAccountCreationBoolean(customerId);
    }
}