package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.request.CreateCustomerRequest;
import com.berkay22demirel.readingisgood.controller.response.Response;
import com.berkay22demirel.readingisgood.security.JwtUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerControllerIT extends BaseIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void should_create_customer() {
        //given
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setEmail("test1@test.com");
        request.setPassword("password");

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/customers", HttpMethod.POST, new HttpEntity<>(request), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Customer created successfully.");
    }

    @Test
    public void should_return_email_validation_errors_when_email_is_not_email_format() {
        //given
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setEmail("t12345678");
        request.setPassword("password");

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/customers", HttpMethod.POST, new HttpEntity<>(request), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("email")).isEqualTo("must be a well-formed email address");
    }

    @Test
    public void should_return_email_validation_errors_when_email_is_not_valid() {
        //given
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setEmail("t@t.com");
        request.setPassword("password");

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/customers", HttpMethod.POST, new HttpEntity<>(request), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("email")).isEqualTo("length must be between 8 and 50");
    }

    @Test
    public void should_return_password_validation_errors_password_is_not_valid() {
        //given
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setEmail("test1@test.com");
        request.setPassword("pass");

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/customers", HttpMethod.POST, new HttpEntity<>(request), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("password")).isEqualTo("length must be between 8 and 64");
    }

    @Test
    public void should_get_all_orders() {
        //given

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/customers/100/orders?size=2&page=0", HttpMethod.GET, new HttpEntity<>(createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getData()).isNotNull();
        HashMap<String, Object> orderPage = (HashMap<String, Object>) responseEntity.getBody().getData();
        assertThat(orderPage.get("totalElements")).isEqualTo(3);
        assertThat(orderPage.get("totalPages")).isEqualTo(2);
        List<HashMap<String, Object>> orders = (List<HashMap<String, Object>>) orderPage.get("content");
        assertThat(orders.size()).isEqualTo(2);
        assertThat(orders.get(0).get("customerId")).isEqualTo(100);
        assertThat(orders.get(1).get("customerId")).isEqualTo(100);
    }
}
