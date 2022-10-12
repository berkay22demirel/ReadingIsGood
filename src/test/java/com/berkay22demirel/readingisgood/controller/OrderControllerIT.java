package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.request.CreateOrderRequest;
import com.berkay22demirel.readingisgood.controller.request.GetOrderByDateRequest;
import com.berkay22demirel.readingisgood.controller.response.Response;
import com.berkay22demirel.readingisgood.dto.BasketItemRequestDto;
import com.berkay22demirel.readingisgood.security.JwtUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderControllerIT extends BaseIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void should_create_order() {
        //given
        BasketItemRequestDto basketItemRequestDto1 = new BasketItemRequestDto();
        basketItemRequestDto1.setCount(1L);
        basketItemRequestDto1.setBookId(100L);
        BasketItemRequestDto basketItemRequestDto2 = new BasketItemRequestDto();
        basketItemRequestDto2.setCount(2L);
        basketItemRequestDto2.setBookId(101L);
        CreateOrderRequest request = new CreateOrderRequest();
        request.setBasketItems(Arrays.asList(basketItemRequestDto1, basketItemRequestDto2));

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/orders", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Order created successfully.");
    }

    @Test
    public void should_return_basket_item_null_validation_error_when_basket_item_list_is_null() {
        //given
        CreateOrderRequest request = new CreateOrderRequest();
        request.setBasketItems(null);

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/orders", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("basketItems")).isEqualTo("must not be empty");
    }

    @Test
    public void should_return_basket_item_empty_validation_error_when_basket_item_list_is_null() {
        //given
        CreateOrderRequest request = new CreateOrderRequest();
        request.setBasketItems(new ArrayList<>());

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/orders", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("basketItems")).isEqualTo("must not be empty");
    }

    @Test
    public void should_return_basket_item_count_negative_validation_error_when_basket_item_count_is_zero_or_negative() {
        //given
        BasketItemRequestDto basketItemRequestDto1 = new BasketItemRequestDto();
        basketItemRequestDto1.setCount(0L);
        basketItemRequestDto1.setBookId(100L);
        BasketItemRequestDto basketItemRequestDto2 = new BasketItemRequestDto();
        basketItemRequestDto2.setCount(2L);
        basketItemRequestDto2.setBookId(101L);
        CreateOrderRequest request = new CreateOrderRequest();
        request.setBasketItems(Arrays.asList(basketItemRequestDto1, basketItemRequestDto2));

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/orders", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("basketItems[0].count")).isEqualTo("must be greater than or equal to 1");
    }

    @Test
    public void should_return_basket_item_book_null_validation_error_when_basket_item_book_id_is_null() {
        //given
        BasketItemRequestDto basketItemRequestDto1 = new BasketItemRequestDto();
        basketItemRequestDto1.setCount(1L);
        basketItemRequestDto1.setBookId(null);
        BasketItemRequestDto basketItemRequestDto2 = new BasketItemRequestDto();
        basketItemRequestDto2.setCount(2L);
        basketItemRequestDto2.setBookId(101L);
        CreateOrderRequest request = new CreateOrderRequest();
        request.setBasketItems(Arrays.asList(basketItemRequestDto1, basketItemRequestDto2));

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/orders", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("basketItems[0].bookId")).isEqualTo("must not be null");
    }

    @Test
    public void should_return_book_not_found_exception_when_book_not_found() {
        //given
        BasketItemRequestDto basketItemRequestDto1 = new BasketItemRequestDto();
        basketItemRequestDto1.setCount(1L);
        basketItemRequestDto1.setBookId(5L);
        BasketItemRequestDto basketItemRequestDto2 = new BasketItemRequestDto();
        basketItemRequestDto2.setCount(2L);
        basketItemRequestDto2.setBookId(101L);
        CreateOrderRequest request = new CreateOrderRequest();
        request.setBasketItems(Arrays.asList(basketItemRequestDto1, basketItemRequestDto2));

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/orders", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Book not found!");
    }

    @Test
    public void should_return_no_stock_exception_when_book_have_not_enough_stock() {
        //given
        BasketItemRequestDto basketItemRequestDto1 = new BasketItemRequestDto();
        basketItemRequestDto1.setCount(11L);
        basketItemRequestDto1.setBookId(100L);
        BasketItemRequestDto basketItemRequestDto2 = new BasketItemRequestDto();
        basketItemRequestDto2.setCount(2L);
        basketItemRequestDto2.setBookId(101L);
        CreateOrderRequest request = new CreateOrderRequest();
        request.setBasketItems(Arrays.asList(basketItemRequestDto1, basketItemRequestDto2));

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/orders", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("The book (book name) is out of stock!");
    }

    @Test
    public void should_get_by_id() {
        //given

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/orders/100", HttpMethod.GET, new HttpEntity<>(createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getData()).isNotNull();
        HashMap<String, Object> order = (HashMap<String, Object>) responseEntity.getBody().getData();
        assertThat(order.get("customerId")).isEqualTo(100);
        assertThat(order.get("totalAmount")).isEqualTo(2.0);
        assertThat(((ArrayList) order.get("basketItems")).size()).isEqualTo(1);
    }

    @Test
    public void should_get_by_date() {
        //given
        GetOrderByDateRequest request = new GetOrderByDateRequest();
        request.setStartDate(new GregorianCalendar(2022, Calendar.JANUARY, 1).getTime());
        request.setEndDate(new GregorianCalendar(2022, Calendar.FEBRUARY, 20).getTime());

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/orders/getbydate?size=1&page=0", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getData()).isNotNull();
        HashMap<String, Object> orderPage = (HashMap<String, Object>) responseEntity.getBody().getData();
        assertThat(orderPage.get("totalElements")).isEqualTo(2);
        assertThat(orderPage.get("totalPages")).isEqualTo(2);
        List<HashMap<String, Object>> orders = (List<HashMap<String, Object>>) orderPage.get("content");
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).get("customerId")).isEqualTo(100);
        assertThat(orders.get(0).get("totalAmount")).isEqualTo(2.0);
    }

    @Test
    public void should_return_start_date_validation_error_when_start_date_is_null() {
        //given
        GetOrderByDateRequest request = new GetOrderByDateRequest();
        request.setStartDate(null);
        request.setEndDate(new GregorianCalendar(2022, Calendar.MARCH, 1).getTime());

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/orders/getbydate?size=1&page=0", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("startDate")).isEqualTo("must not be null");
    }

    @Test
    public void should_return_end_date_validation_error_when_end_date_is_null() {
        //given
        GetOrderByDateRequest request = new GetOrderByDateRequest();
        request.setStartDate(new GregorianCalendar(2022, Calendar.MARCH, 1).getTime());
        request.setEndDate(null);

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/orders/getbydate", HttpMethod.POST, new HttpEntity<>(request, createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getResponseMessage()).isEqualTo("Method argument not valid!");
        assertThat(responseEntity.getBody().getValidationErrors().get("endDate")).isEqualTo("must not be null");
    }
}
