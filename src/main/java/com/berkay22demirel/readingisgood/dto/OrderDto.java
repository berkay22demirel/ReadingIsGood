package com.berkay22demirel.readingisgood.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    @NotNull
    @DecimalMin(value = "1.0")
    private BigDecimal totalAmount;

    @NotNull
    @NotEmpty
    private List<BasketItemDto> basketItems;

    @NotNull
    private Long customerId;

    @NotNull
    private Date date;
}
