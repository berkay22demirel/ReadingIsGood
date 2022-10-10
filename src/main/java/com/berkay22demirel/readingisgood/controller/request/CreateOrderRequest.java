package com.berkay22demirel.readingisgood.controller.request;

import com.berkay22demirel.readingisgood.dto.BasketItemRequestDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateOrderRequest {

    @NotNull
    @NotEmpty
    private List<BasketItemRequestDto> basketItems;
}
