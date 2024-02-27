package com.microservice.customer.service.service;

import com.microservice.customer.service.exceptions.RegistrationUnsuccessfullException;
import com.microservice.customer.service.configurations.BCryptPasswordEncoder;
import com.microservice.customer.service.exceptions.ResourceNotFoundException;
import com.microservice.customer.service.repository.CustomerRepository;
import com.microservice.customer.service.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRegistrationRepository;

    @Autowired
    private Validations validations;

    @Autowired
    private RestTemplate restTemplate;

    Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public Customer addCustomer(Customer customer , String trackingId) {

        String result = Validations.validateCustomer(customer,trackingId);

        if(!result.equals("success")) {
            throw new RegistrationUnsuccessfullException(result);
        }
        // encrypting password.
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setPassword(bcrypt.encode(customer.getPassword()));

        log.info("Tracking Id: {} - encrypting password & setting cust Id and saving the customer in DB", trackingId);

        return customerRegistrationRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(String id , String trackingId) {
        log.info("Tracking Id: {} - fetching customer through customer Id :{}", trackingId,id);
        return customerRegistrationRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRegistrationRepository.findAll();
    }

    @Override
    public Customer updateCustomerById(String id , Customer customer) {
        Customer updatedCustomer = customerRegistrationRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        updatedCustomer.setName(customer.getName());
        updatedCustomer.setAge(customer.getAge());
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setMobileNumber(customer.getMobileNumber());
        updatedCustomer.setSalary(customer.getSalary());
        updatedCustomer.setGender(customer.getGender());
        updatedCustomer.setPassword(customer.getPassword());

        return customerRegistrationRepository.save(updatedCustomer);
    }

    @Override
    public void deleteCustomerById(String id) {
        customerRegistrationRepository.deleteById(id);
    }

    @Override
    public Customer getByUsername(String username) {
        return customerRegistrationRepository.findByUsername(username);
    }
}