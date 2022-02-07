package org.example.customer.service;

import com.example.clients.fraud.FraudCheckResponse;
import com.example.clients.fraud.FraudClient;
import com.example.clients.notification.NotificationClient;
import com.example.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.example.customer.CustomerRegistrationRequest;
import org.example.customer.dao.CustomerRepository;
import org.example.customer.entity.Customer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //todo: check if email void
        //todo: check if email not take
        customerRepository.saveAndFlush(customer);
        //todo: check if fraudster
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
        //todo: make it async
        notificationClient.sendNotification(new NotificationRequest(customer.getId(), customer.getEmail()
        , String.format("Hi %s, welcome to my server", customer.getFirstName())));

    }
}
