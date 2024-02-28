package com.microservice.customer.service.DTO;

import com.microservice.customer.service.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO {
    private String TrackingId;
    private String CustomerId;
    private String Name;
    private String Email;
    private String username;

    public ResponseDTO(Customer customer , String trackingId){
        this.TrackingId = trackingId;
        this.CustomerId = customer.getCustomerId();
        this.Name       = customer.getName();
        this.Email      = customer.getEmail();
        this.username   = customer.getUsername();
    }

}
