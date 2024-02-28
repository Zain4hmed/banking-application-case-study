package com.microservice.customer.service.service;

import com.microservice.customer.service.DTO.ResponseDTO;
import com.microservice.customer.service.exceptions.RegistrationUnsuccessfullException;
import com.microservice.customer.service.configurations.BCryptPasswordEncoder;
import com.microservice.customer.service.exceptions.ResourceNotFoundException;
import com.microservice.customer.service.repository.CustomerRepository;
import com.microservice.customer.service.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRegistrationRepository;

    @Autowired
    private Validations validations;

//    @Autowired
//    private RestTemplate restTemplate;

    Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public ResponseDTO addCustomer(Customer customer , String trackingId) {
        String result = Validations.validateCustomer(customer,trackingId);
        if(!result.equals("success")) {
            throw new RegistrationUnsuccessfullException(result);
        }
        // encrypting password.
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setPassword(bcrypt.encode(customer.getPassword()));
        log.info("Tracking Id: {} - encrypting password & setting cust Id and saving the customer in DB", trackingId);
        customerRegistrationRepository.save(customer);
        return new ResponseDTO(customer,trackingId);
    }

    @Override
    public ResponseDTO getCustomerById(String id , String trackingId) {
        log.info("Tracking Id: {} - fetching customer through customer Id :{}", trackingId,id);
        Customer customer =  customerRegistrationRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return new ResponseDTO(customer,trackingId);
    }

    @Override
    public List<ResponseDTO> getAllCustomers(String trackingId) {
        log.info("Tracking Id: {} - fetching all the customer from DB", trackingId);
        return customerRegistrationRepository.findAll().stream()
                .map(customer -> new ResponseDTO(customer, "[ * * * * ]")).collect(Collectors.toList());
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
    public Customer getByUsername(String username , String trackingId) {
        log.info("Tracking Id: {} - fetching customer for it's username :{}", trackingId,username);
        return customerRegistrationRepository.findByUsername(username);
    }
}