package com.berkay22demirel.readingisgood.service.mapper;

import com.berkay22demirel.readingisgood.dto.BasketItemDto;
import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.BasketItem;
import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.entity.Order;
import com.berkay22demirel.readingisgood.repoitory.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderMapperTest {

    @InjectMocks
    private OrderMapper orderMapper;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Mappable<BasketItem, BasketItemDto> basketItemMapper;

    @Test
    public void should_convert_to_entity() {
        //given
        Date date = new Date();
        Long customerId = 1L;
        OrderDto orderDto = new OrderDto();
        orderDto.setBasketItems(Collections.singletonList(new BasketItemDto()));
        orderDto.setDate(date);
        orderDto.setTotalAmount(BigDecimal.TEN);
        orderDto.setCustomerId(customerId);
        BasketItem basketItem = new BasketItem();
        Customer customer = new Customer();
        when(basketItemMapper.convertToEntity(any(BasketItemDto.class))).thenReturn(basketItem);
        when(customerRepository.getReferenceById(customerId)).thenReturn(customer);

        //when
        Order order = orderMapper.convertToEntity(orderDto);

        //then
        assertThat(order).isNotNull();
        assertThat(order.getDate()).isEqualTo(date);
        assertThat(order.getCustomer()).isEqualTo(customer);
        assertThat(order.getId()).isNull();
        assertThat(order.getBasketItems()).isEqualTo(Collections.singletonList(basketItem));
        assertThat(order.getTotalAmount()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void should_convert_to_dto() {
        //given
        Date date = new Date();
        Customer customer = new Customer();
        customer.setId(11L);
        Order order = new Order();
        order.setBasketItems(Collections.singletonList(new BasketItem()));
        order.setDate(date);
        order.setTotalAmount(BigDecimal.TEN);
        order.setCustomer(customer);
        BasketItemDto basketItemDto = new BasketItemDto();

        when(basketItemMapper.convertToDto(any(BasketItem.class))).thenReturn(basketItemDto);

        //when
        OrderDto orderDto = orderMapper.convertToDto(order);

        //then
        assertThat(orderDto).isNotNull();
        assertThat(orderDto.getDate()).isEqualTo(date);
        assertThat(orderDto.getCustomerId()).isEqualTo(11L);
        assertThat(orderDto.getBasketItems()).isEqualTo(Collections.singletonList(basketItemDto));
        assertThat(orderDto.getTotalAmount()).isEqualTo(BigDecimal.TEN);
    }
}
