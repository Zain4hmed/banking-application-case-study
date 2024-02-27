package com.microservice.customer.service.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import com.microservice.customer.service.service.CustomerService;
import com.microservice.customer.service.entity.Customer;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerRegistrationController {

    @Autowired
    private CustomerService customerService;

    Logger log = LoggerFactory.getLogger(CustomerService.class);

    private static final String GITHUB_URL = "https://github.com/ZainAhmed08/banking-application-case-study";

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer){
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to create new customer",trackingId);
        return ResponseEntity.ok().body(customerService.addCustomer(customer , trackingId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id){
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to get customer by customer Id :{}",trackingId,id);
        return ResponseEntity.ok().body(customerService.getCustomerById(id,trackingId));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username) {
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to get customer by username :{}",trackingId,username);
        return ResponseEntity.ok().body(customerService.getByUsername(username,trackingId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to get all customers from DB",trackingId);
        return ResponseEntity.ok().body(customerService.getAllCustomers(trackingId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable String id , @RequestBody Customer customer){
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to update customer by customer Id :{}",trackingId,id);
        return ResponseEntity.ok().body(customerService.updateCustomerById(id, customer));
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id){
        String trackingId = UUID.randomUUID().toString();
        log.info("Tracking Id :{} request to delete customer by customer Id :{}",trackingId,id);
        customerService.deleteCustomerById(id);
    }

    @GetMapping(value = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode() {
        try {
            // Generate QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(GITHUB_URL, BarcodeFormat.QR_CODE, 200, 200);

            // Convert QR code to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bitMatrixToImage(bitMatrix), "png", byteArrayOutputStream);
            byte[] imageData = byteArrayOutputStream.toByteArray();

            // Return QR code image
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageData);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    private static BufferedImage bitMatrixToImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    @GetMapping()
    public ResponseEntity<List<String>> exposeAllEndpoints(){
        List<String> customerServiceEndpoints = Arrays.asList(
                "GET    http://localhost:8080/api/customers/qrcode",
                "POST   http://localhost:8080/api/customers/register",
                "GET    http://localhost:8080/api/customers/{id}",
                "GET    http://localhost:8080/api/customers/username/{username}",
                "GET    http://localhost:8080/api/customers/all",
                "PUT    http://localhost:8080/api/customers/{id}",
                "DELETE http://localhost:8080/api/customers/{id}"
        );
        return ResponseEntity.ok().body(customerServiceEndpoints);
    }
}