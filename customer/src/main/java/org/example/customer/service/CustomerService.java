package org.example.customer.service;

import org.example.customer.entity.Customer;
import org.example.customer.CustomerRegistrationRequest;
import org.example.customer.dao.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //todo: check if email void
        //todo: check if email not take
        customerRepository.save(customer);
    }
}
