package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.response.Response;
import com.berkay22demirel.readingisgood.dto.MonthlyStatisticsDto;
import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.security.JwtManager;
import com.berkay22demirel.readingisgood.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/statistics")
@RestController
public class StatisticsController {

    private final JwtManager jwtManager;
    private final StatisticsService statisticsService;

    @GetMapping("/monthly")
    public ResponseEntity<Response> getMonthlyStatistics(HttpServletRequest httpServletRequest) {
        Customer user = jwtManager.getCustomer(httpServletRequest);
        List<MonthlyStatisticsDto> monthlyStatisticsList = statisticsService.getMonthlyStatistics(user);
        return new ResponseEntity<>(new Response(monthlyStatisticsList), HttpStatus.OK);
    }
}
