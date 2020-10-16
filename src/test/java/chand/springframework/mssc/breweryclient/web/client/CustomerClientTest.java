package chand.springframework.mssc.breweryclient.web.client;

import chand.springframework.mssc.breweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerClientTest {

    @Autowired
    CustomerClient client;

    @Test
    void saveCustomer() {
        CustomerDto customer = CustomerDto.builder()
                .id(UUID.randomUUID())
                .firstName("Chandana")
                .lastName("Ekanayake").age(40)
                .location("Colombo")
                .build();
        URI uri = client.saveCustomer(customer);
        assertNotNull(uri);
    }

    @Test
    void getCustomerById() {
        CustomerDto customer = client.getCustomerById(UUID.randomUUID());
        assertNotNull(customer);
    }

    @Test
    void deleteCustomer() {
        client.deleteCustomer(UUID.randomUUID());
    }

    @Test
    void updateCustomer() {
        CustomerDto customer = CustomerDto.builder()
                .id(UUID.randomUUID())
                .firstName("Chandana")
                .lastName("Ekanayake").age(40)
                .location("Colombo")
                .build();
        client.updateCustomer(UUID.randomUUID(), customer);
    }
}