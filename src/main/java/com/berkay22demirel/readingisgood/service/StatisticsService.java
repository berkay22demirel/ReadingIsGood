package com.berkay22demirel.readingisgood.service;

import com.berkay22demirel.readingisgood.dto.MonthlyStatisticsDto;
import com.berkay22demirel.readingisgood.entity.Customer;

import java.util.List;

public interface StatisticsService {

    List<MonthlyStatisticsDto> getMonthlyStatistics(Customer user);
}
