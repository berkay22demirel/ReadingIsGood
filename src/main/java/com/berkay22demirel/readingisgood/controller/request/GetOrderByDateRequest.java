package com.berkay22demirel.readingisgood.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class GetOrderByDateRequest {

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;
}
