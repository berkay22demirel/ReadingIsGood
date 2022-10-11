package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.request.CreateCustomerRequest;
import com.berkay22demirel.readingisgood.controller.response.Response;
import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.security.JwtManager;
import com.berkay22demirel.readingisgood.service.CustomerService;
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
@RequestMapping("api/v1/customers")
@RestController
public class CustomerController {

    private final JwtManager jwtManager;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody @Valid CreateCustomerRequest request) {
        customerService.create(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(new Response("Customer created successfully."), HttpStatus.OK);
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<Response> getAllOrders(@PathVariable @NotNull Long customerId, @NotNull final Pageable pageable, HttpServletRequest httpServletRequest) {
        Customer customer = jwtManager.validateTokenByCustomerId(httpServletRequest, customerId);
        Page<OrderDto> orders = customerService.getAllOrders(pageable, customer);
        return new ResponseEntity<>(new Response(orders), HttpStatus.OK);

    }
}
