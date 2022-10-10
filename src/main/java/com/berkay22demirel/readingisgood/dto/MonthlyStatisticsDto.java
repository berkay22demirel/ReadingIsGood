package com.berkay22demirel.readingisgood.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonthlyStatisticsDto {

    private String month;
    private Long totalOrderCount;
    private Long totalBookCount;
    private BigDecimal totalPurchasedAmount;
}
