package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.response.Response;
import com.berkay22demirel.readingisgood.security.JwtUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StatisticsControllerIT extends BaseIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void should_get_all_orders() {
        //given

        //when
        ResponseEntity<Response> responseEntity = testRestTemplate.exchange("/api/v1/statistics/monthly", HttpMethod.GET, new HttpEntity<>(createHttpHeaders(jwtUtil)), Response.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getData()).isNotNull();
        List<HashMap<String, Object>> monthlyStatisticsDtoList = (List<HashMap<String, Object>>) responseEntity.getBody().getData();
        assertThat(monthlyStatisticsDtoList.size()).isEqualTo(2);
        assertThat(monthlyStatisticsDtoList.get(0).get("month")).isEqualTo("2022-01");
        assertThat(monthlyStatisticsDtoList.get(0).get("totalPurchasedAmount")).isEqualTo(2.0);
        assertThat(monthlyStatisticsDtoList.get(0).get("totalBookCount")).isEqualTo(2);
        assertThat(monthlyStatisticsDtoList.get(0).get("totalOrderCount")).isEqualTo(1);
        assertThat(monthlyStatisticsDtoList.get(1).get("month")).isEqualTo("2022-02");
        assertThat(monthlyStatisticsDtoList.get(1).get("totalPurchasedAmount")).isEqualTo(22.0);
        assertThat(monthlyStatisticsDtoList.get(1).get("totalBookCount")).isEqualTo(12);
        assertThat(monthlyStatisticsDtoList.get(1).get("totalOrderCount")).isEqualTo(2);
    }
}
