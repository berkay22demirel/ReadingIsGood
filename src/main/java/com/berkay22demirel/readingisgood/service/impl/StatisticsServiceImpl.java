package com.berkay22demirel.readingisgood.service.impl;

import com.berkay22demirel.readingisgood.dto.MonthlyStatisticsDto;
import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.entity.Order;
import com.berkay22demirel.readingisgood.repoitory.OrderRepository;
import com.berkay22demirel.readingisgood.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderRepository orderRepository;

    @Override
    public List<MonthlyStatisticsDto> getMonthlyStatistics(Customer user) {
        return orderRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(order -> new SimpleDateFormat("yyyy-MM")
                        .format(order.getDate())))
                .entrySet()
                .stream()
                .map(entry -> {
                    MonthlyStatisticsDto dto = new MonthlyStatisticsDto();
                    dto.setMonth(entry.getKey());
                    dto.setTotalPurchasedAmount(entry.getValue().stream().map(Order::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
                    dto.setTotalBookCount(entry.getValue().stream().mapToLong(order -> order.getBasketItems().size()).sum());
                    dto.setTotalOrderCount((long) entry.getValue().size());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
