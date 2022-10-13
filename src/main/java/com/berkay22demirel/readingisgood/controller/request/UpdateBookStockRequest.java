package com.berkay22demirel.readingisgood.controller.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateBookStockRequest {

    @NotNull
    @Min(0)
    private Long stockCount;
}
