package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.request.AuthRequest;
import com.berkay22demirel.readingisgood.controller.response.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerIT extends BaseIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void should_authenticate_user() {
        //given
        AuthRequest request = new AuthRequest();
        request.setEmail("test@test.com");
        request.setPassword("12345678");

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/auth", HttpMethod.POST, new HttpEntity<>(request), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Token created successfully.");
    }

    @Test
    public void should_return_unauthorized_error_when_password_is_incorrect() {
        //given
        AuthRequest request = new AuthRequest();
        request.setEmail("test@test.com");
        request.setPassword("123456789");

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/auth", HttpMethod.POST, new HttpEntity<>(request), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Email or password is incorrect!");
    }

    @Test
    public void should_return_unauthorized_error_when_email_is_incorrect() {
        //given
        AuthRequest request = new AuthRequest();
        request.setEmail("test123@test.com");
        request.setPassword("12345678");

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/auth", HttpMethod.POST, new HttpEntity<>(request), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Email or password is incorrect!");
    }

    @Test
    public void should_return_email_validation_errors_when_email_is_not_email_format() {
        //given
        AuthRequest request = new AuthRequest();
        request.setEmail("t12345678");
        request.setPassword("password");

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/auth", HttpMethod.POST, new HttpEntity<>(request), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("email")).isEqualTo("must be a well-formed email address");
    }

    @Test
    public void should_return_email_validation_errors_when_email_is_not_valid() {
        //given
        AuthRequest request = new AuthRequest();
        request.setEmail("t@t.com");
        request.setPassword("password");

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/auth", HttpMethod.POST, new HttpEntity<>(request), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("email")).isEqualTo("length must be between 8 and 50");
    }

    @Test
    public void should_return_password_validation_errors_password_is_not_valid() {
        //given
        AuthRequest request = new AuthRequest();
        request.setEmail("test1@test.com");
        request.setPassword("pass");

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/auth", HttpMethod.POST, new HttpEntity<>(request), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("password")).isEqualTo("length must be between 8 and 64");
    }

}
