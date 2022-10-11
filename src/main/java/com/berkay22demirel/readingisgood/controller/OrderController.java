package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.request.CreateOrderRequest;
import com.berkay22demirel.readingisgood.controller.request.GetOrderByDateRequest;
import com.berkay22demirel.readingisgood.controller.response.Response;
import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.security.JwtManager;
import com.berkay22demirel.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
@RestController
public class OrderController {

    private final JwtManager jwtManager;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody @Valid CreateOrderRequest request, HttpServletRequest httpServletRequest) {
        Customer customer = jwtManager.getCustomer(httpServletRequest);
        orderService.create(customer, request.getBasketItems());
        return new ResponseEntity<>(new Response("Order created successfully."), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable @NotNull Long id) {
        OrderDto order = orderService.getById(id);
        return new ResponseEntity<>(new Response(order), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Response> getByDate(@NotNull final Pageable pageable, @RequestBody @Valid GetOrderByDateRequest request, HttpServletRequest httpServletRequest) {
        Page<OrderDto> orders = orderService.getByDate(pageable, request.getStartDate(), request.getEndDate());
        return new ResponseEntity<>(new Response(orders), HttpStatus.OK);
    }
}
