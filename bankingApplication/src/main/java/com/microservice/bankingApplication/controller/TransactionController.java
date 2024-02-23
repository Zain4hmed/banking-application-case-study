package com.microservice.bankingApplication.controller;

import com.microservice.bankingApplication.DTO.ResponseDTO;
import com.microservice.bankingApplication.entity.Transaction;
import com.microservice.bankingApplication.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping("/debit")
    public ResponseEntity<ResponseDTO> createTransaction(@RequestBody Transaction transaction){
        String trackingId = UUID.randomUUID().toString();
        logger.info("Tracking ID : {} , Create Transaction request recieved.",trackingId);
        return ResponseEntity.ok().body(transactionService.createTransaction(transaction,trackingId));
    }

}
