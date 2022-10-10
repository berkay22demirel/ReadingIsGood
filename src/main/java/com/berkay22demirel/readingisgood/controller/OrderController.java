package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.request.CreateOrderRequest;
import com.berkay22demirel.readingisgood.controller.request.GetOrderByDateRequest;
import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.User;
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
@RequestMapping("api/v1/users/{userId}/orders")
@RestController
public class OrderController {

    private final JwtManager jwtManager;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateOrderRequest request, @PathVariable Long userId, HttpServletRequest httpServletRequest) {
        User user = jwtManager.validateTokenByUserId(httpServletRequest, userId);
        orderService.create(user, request.getBooks());
        return new ResponseEntity<>("Order created successfully.", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> get(@PathVariable @NotNull Long id, @PathVariable Long userId, HttpServletRequest httpServletRequest) {
        User user = jwtManager.validateTokenByUserId(httpServletRequest, userId);
        return new ResponseEntity<>(orderService.get(id, user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<OrderDto>> getByDate(@PathVariable Long userId, @NotNull final Pageable pageable, @RequestBody @Valid GetOrderByDateRequest request, HttpServletRequest httpServletRequest) {
        User user = jwtManager.validateTokenByUserId(httpServletRequest, userId);
        return new ResponseEntity<>(orderService.getByDate(pageable, request.getStartDate(), request.getEndDate(), user), HttpStatus.OK);
    }
}
