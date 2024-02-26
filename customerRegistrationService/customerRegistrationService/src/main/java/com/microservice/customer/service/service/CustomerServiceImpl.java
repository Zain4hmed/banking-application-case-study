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
    public Customer addCustomer(Customer customer) {
        customer.setCustomerId(UUID.randomUUID().toString());
        String result = Validations.validateCustomer(customer);
        if(!result.equals("success")) {
            throw new RegistrationUnsuccessfullException(result);
        }
        // encrypting password.
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        customer.setPassword(bcrypt.encode(customer.getPassword()));
        return customerRegistrationRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(String id) {
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
    public String toggleAccountCreationBoolean(String customerId) {
        Customer customer = getCustomerById(customerId);
        customer.setAccountCreated(!customer.getAccountCreated());
        customerRegistrationRepository.save(customer);
        return "success";
    }

    @Override
    public Customer getByUsername(String username) {
        if(!(customerRegistrationRepository.findByUsername(username).isEmpty())) {
            return customerRegistrationRepository.findByUsername(username).get(0);
        }
        else {
            return null;
        }
    }
}