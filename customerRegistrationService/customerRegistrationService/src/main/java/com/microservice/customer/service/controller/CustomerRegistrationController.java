package com.microservice.customer.service.controller;

import java.util.List;

import com.microservice.customer.service.entity.Customer;
import com.microservice.customer.service.service.CustomerService;
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

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer){
        return ResponseEntity.ok().body(customerService.addCustomer(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id){
        return ResponseEntity.ok().body(customerService.getCustomerById(id));
    }

    @GetMapping("/byusername/{username}")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(customerService.getByUsername(username));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable String id , @RequestBody Customer customer){
        return ResponseEntity.ok().body(customerService.updateCustomerById(id, customer));
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id){
        customerService.deleteCustomerById(id);
    }

    @GetMapping("/createaccount/{customerId}")
    public String toggleAccountCreatedBoolean(@PathVariable String customerId) {
        return customerService.toggleAccountCreationBoolean(customerId);
    }

    @GetMapping("/login")
    public ResponseEntity<Customer> login(@RequestParam String id ,@RequestParam String password ) {
        return ResponseEntity.ok().body(customerService.login(id, password));
    }

}