package com.berkay22demirel.readingisgood.service;

import com.berkay22demirel.readingisgood.dto.MonthlyStatisticsDto;
import com.berkay22demirel.readingisgood.entity.BasketItem;
import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.entity.Order;
import com.berkay22demirel.readingisgood.repoitory.OrderRepository;
import com.berkay22demirel.readingisgood.service.impl.StatisticsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void should_get_monthly_statistics() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        Order order1 = createOrder(1L, customer, new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new BigDecimal(8), Collections.singletonList(createBasketItem(new BigDecimal(8), 2L)));
        Order order2 = createOrder(2L, customer, new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new BigDecimal(6), Collections.singletonList(createBasketItem(new BigDecimal(6), 3L)));
        Order order3 = createOrder(3L, customer, new GregorianCalendar(2022, Calendar.APRIL, 1).getTime(), new BigDecimal(5), Collections.singletonList(createBasketItem(new BigDecimal(5), 1L)));
        Order order4 = createOrder(4L, customer, new GregorianCalendar(2022, Calendar.APRIL, 1).getTime(), new BigDecimal(10), Collections.singletonList(createBasketItem(new BigDecimal(10), 2L)));
        Order order5 = createOrder(5L, customer, new GregorianCalendar(2022, Calendar.APRIL, 1).getTime(), new BigDecimal(12), Collections.singletonList(createBasketItem(new BigDecimal(12), 4L)));
        when(orderRepository.findByCustomer(customer)).thenReturn(Arrays.asList(order1, order2, order3, order4, order5));

        //when
        List<MonthlyStatisticsDto> monthlyStatistics = statisticsService.getMonthlyStatistics(customer);

        //then
        assertThat(monthlyStatistics.size()).isEqualTo(2L);
        assertThat(monthlyStatistics.get(0).getMonth()).isEqualTo("2022-04");
        assertThat(monthlyStatistics.get(0).getTotalBookCount()).isEqualTo(7L);
        assertThat(monthlyStatistics.get(0).getTotalOrderCount()).isEqualTo(3L);
        assertThat(monthlyStatistics.get(0).getTotalPurchasedAmount()).isEqualTo(new BigDecimal(27));
        assertThat(monthlyStatistics.get(1).getMonth()).isEqualTo("2022-05");
        assertThat(monthlyStatistics.get(1).getTotalBookCount()).isEqualTo(5L);
        assertThat(monthlyStatistics.get(1).getTotalOrderCount()).isEqualTo(2L);
        assertThat(monthlyStatistics.get(1).getTotalPurchasedAmount()).isEqualTo(new BigDecimal(14));

    }

    private BasketItem createBasketItem(BigDecimal amount, Long count) {
        BasketItem basketItem = new BasketItem();
        basketItem.setCount(count);
        basketItem.setAmount(amount);
        return basketItem;
    }

    private Order createOrder(Long id, Customer customer, Date date, BigDecimal totalAmount, List<BasketItem> basketItems) {
        Order order = new Order();
        order.setId(id);
        order.setCustomer(customer);
        order.setDate(date);
        order.setTotalAmount(totalAmount);
        order.setBasketItems(basketItems);
        return order;
    }
}
