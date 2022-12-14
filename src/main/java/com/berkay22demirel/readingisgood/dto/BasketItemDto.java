package com.berkay22demirel.readingisgood.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class BasketItemDto {

    @NotNull
    @DecimalMin(value = "1.0")
    private BigDecimal amount;

    @Min(1)
    @NotNull
    private Long count;

    @NotNull
    private Long bookId;

    private Long orderId;
}
