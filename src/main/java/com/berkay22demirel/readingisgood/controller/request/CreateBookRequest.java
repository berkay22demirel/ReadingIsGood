package com.berkay22demirel.readingisgood.controller.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateBookRequest {

    @NotNull
    @Length(min = 8, max = 64)
    private String name;

    @NotNull
    @Length(min = 8, max = 64)
    private String author;

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Long stockCount;

    @NotNull
    @DecimalMin(value = "1.0")
    private BigDecimal amount;
}
