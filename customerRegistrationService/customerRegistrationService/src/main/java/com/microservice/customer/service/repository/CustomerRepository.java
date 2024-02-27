package com.microservice.customer.service.repository;

import com.microservice.customer.service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>{
    Customer findByUsername(String userName);
}