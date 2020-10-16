package chand.springframework.mssc.breweryclient.web.client;

import chand.springframework.mssc.breweryclient.web.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)
public class CustomerClient {

    private String apiHost;
    private final String CUSTOMER_V1_PATH = "/api/v1/customer/";

    private final RestTemplate restTemplate;


    public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public URI saveCustomer(CustomerDto customer) {
        return restTemplate.postForLocation(apiHost + CUSTOMER_V1_PATH, customer);
    }

    public CustomerDto getCustomerById(UUID customerId) {
        return restTemplate.getForObject(apiHost+CUSTOMER_V1_PATH + customerId.toString(), CustomerDto.class);
    }

    public void deleteCustomer(UUID customerId) {
        restTemplate.delete(apiHost + CUSTOMER_V1_PATH + customerId.toString());
    }

    public  void updateCustomer(UUID customerId, CustomerDto customer) {
        restTemplate.put(apiHost + CUSTOMER_V1_PATH + customerId.toString(), customer);
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

}
