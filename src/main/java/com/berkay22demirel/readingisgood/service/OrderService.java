package com.berkay22demirel.readingisgood.service;

import com.berkay22demirel.readingisgood.dto.BasketItemRequestDto;
import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface OrderService {

    void create(Customer customer, List<BasketItemRequestDto> requestBasketItems);

    OrderDto getById(Long id);

    Page<OrderDto> getByDate(Pageable pageable, Date startDate, Date endDate);

    Page<OrderDto> getAllByCustomer(Pageable pageable, Customer customer);
}
