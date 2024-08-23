package com.prasad.ordersapi.service;

import com.prasad.ordersapi.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GetCustomerService {

    @Value("${customerapi.url}")
    private String customerAPIBaseURL;

    public Customer getCustomer(long id) {
        log.info("Inside GetCustomerService --> getCustomer");
        RestTemplate restTemplateCustomer = new RestTemplate();
        String customerAPIURL = customerAPIBaseURL+id;
        log.info("Invoking URL : "+customerAPIURL);
        ResponseEntity<Customer> responseCustomer = restTemplateCustomer.getForEntity(customerAPIURL, Customer.class);
        Customer cust1 = new Customer(
                responseCustomer.getBody().getId(),
                responseCustomer.getBody().getCustName(),
                responseCustomer.getBody().getCustEmail()
        );
        log.info("Customer --> " + cust1.toString());
        return cust1;
    }
}
