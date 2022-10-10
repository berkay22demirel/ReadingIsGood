package com.berkay22demirel.readingisgood.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private BigDecimal totalAmount;
    private List<BasketItemDto> basketItems;
    private Long userId;
    private Date date;
}
