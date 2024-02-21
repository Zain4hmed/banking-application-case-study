package com.microservice.bankingApplication.repository;

import com.microservice.bankingApplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,String> {
    List<Transaction> findBySenderAccountId(String accountId);
}
