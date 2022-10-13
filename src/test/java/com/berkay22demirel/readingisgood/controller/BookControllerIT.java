package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.request.CreateBookRequest;
import com.berkay22demirel.readingisgood.controller.request.UpdateBookStockRequest;
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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIT extends BaseIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void should_create_book() {
        //given
        CreateBookRequest request = new CreateBookRequest();
        request.setAmount(BigDecimal.ONE);
        request.setName("book name");
        request.setAuthor("author name");
        request.setStockCount(1L);

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/books", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Book created successfully.");
    }

    @Test
    public void should_return_book_name_validation_errors_when_book_name_is_not_valid() {
        //given
        CreateBookRequest request = new CreateBookRequest();
        request.setAmount(BigDecimal.ONE);
        request.setName("book");
        request.setAuthor("author name");
        request.setStockCount(1L);

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/books", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("name")).isEqualTo("length must be between 8 and 64");
    }

    @Test
    public void should_return_book_author_validation_errors_when_book_author_is_not_valid() {
        //given
        CreateBookRequest request = new CreateBookRequest();
        request.setAmount(BigDecimal.ONE);
        request.setName("book name");
        request.setAuthor("author");
        request.setStockCount(1L);

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/books", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("author")).isEqualTo("length must be between 8 and 64");
    }

    @Test
    public void should_return_stock_count_validation_errors_when_stock_is_null() {
        //given
        CreateBookRequest request = new CreateBookRequest();
        request.setAmount(BigDecimal.ONE);
        request.setName("book name");
        request.setAuthor("author name");
        request.setStockCount(null);

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/books", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("stockCount")).isEqualTo("must not be null");
    }

    @Test
    public void should_return_stock_count_validation_errors_when_stock_is_negative() {
        //given
        CreateBookRequest request = new CreateBookRequest();
        request.setAmount(BigDecimal.ONE);
        request.setName("book name");
        request.setAuthor("author name");
        request.setStockCount(-1L);

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/books", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("stockCount")).isEqualTo("must be greater than or equal to 0");
    }

    @Test
    public void should_return_amount_validation_errors_when_amount_is_null() {
        //given
        CreateBookRequest request = new CreateBookRequest();
        request.setAmount(null);
        request.setName("book name");
        request.setAuthor("author name");
        request.setStockCount(1L);

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/books", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("amount")).isEqualTo("must not be null");
    }

    @Test
    public void should_return_amount_validation_errors_when_amount_less_than_one() {
        //given
        CreateBookRequest request = new CreateBookRequest();
        request.setAmount(new BigDecimal("0.5"));
        request.setName("book name");
        request.setAuthor("author name");
        request.setStockCount(1L);

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/books", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("amount")).isEqualTo("must be greater than or equal to 1.0");
    }

    @Test
    public void should_update_stock() {
        //given
        UpdateBookStockRequest request = new UpdateBookStockRequest();
        request.setStockCount(5L);

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/books/100/stock", HttpMethod.PUT, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Book stock updated successfully.");
    }

    @Test
    public void should_return_stock_validation_errors_when_stock_count_is_negative() {
        //given
        CreateBookRequest request = new CreateBookRequest();
        request.setAmount(new BigDecimal("0.5"));
        request.setName("book name");
        request.setAuthor("author name");
        request.setStockCount(1L);

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/books", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("amount")).isEqualTo("must be greater than or equal to 1.0");
    }
}
