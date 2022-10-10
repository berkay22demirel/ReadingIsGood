package com.berkay22demirel.readingisgood.service;

import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    void create(String email, String password);

    Page<OrderDto> getAllOrders(Pageable pageable, Customer customer);
}
