package com.berkay22demirel.readingisgood.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateOrderRequest {

    @NotNull
    @NotEmpty
    private List<Long> books;
}
