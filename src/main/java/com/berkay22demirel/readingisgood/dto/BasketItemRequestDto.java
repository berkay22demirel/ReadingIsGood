package com.berkay22demirel.readingisgood.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BasketItemRequestDto {

    @Min(1)
    @NotNull
    private Long count;

    @NotNull
    private Long bookId;
}
