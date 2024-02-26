package com.microservice.customer.service.service;

import java.util.List;
import java.util.UUID;

import com.microservice.customer.service.configurations.BCryptPasswordEncoder;
import com.microservice.customer.service.entity.Account;
import com.microservice.customer.service.entity.Customer;
import com.microservice.customer.service.exceptions.RegistrationUnsuccessfullException;
import com.microservice.customer.service.exceptions.ResourceNotFoundException;
import com.microservice.customer.service.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CustomerServiceImpl implements CustomerService {

    Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRegistrationRepository;

    @Autowired
    private Validations validations;

    @Autowired
    private RestTemplate restTemplate;


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
        return customerRegistrationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException());
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRegistrationRepository.findAll();
    }

    @Override
    public Customer updateCustomerById(String id , Customer customer) {
        Customer updatedCustomer = customerRegistrationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException() );

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

    @Override
    public Customer login(String id, String password) {
        Customer customer = customerRegistrationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException());
        log.info("fromt he db the customer : "+customer);
//		if(customer != null || customer.getPassword().toString() != password) {
//			throw new ResourceNotFoundException(" The id or password is incorrect please retry later");
//		}

        Account account = null;

        try {
            account = restTemplate.getForObject("http://localhost:8097/api/accounts/customer/"+customer.getCustomerId(), Account.class);
        }catch(Exception e) {
            log.info("Log : "+e.getMessage());
        }

        customer.setAccount(account);

        return customer;
    }

}