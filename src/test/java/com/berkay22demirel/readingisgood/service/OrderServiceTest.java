package com.berkay22demirel.readingisgood.service;

import com.berkay22demirel.readingisgood.dto.BasketItemDto;
import com.berkay22demirel.readingisgood.dto.BasketItemRequestDto;
import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.Book;
import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.entity.Order;
import com.berkay22demirel.readingisgood.exception.NotFoundException;
import com.berkay22demirel.readingisgood.repoitory.OrderRepository;
import com.berkay22demirel.readingisgood.service.impl.OrderServiceImpl;
import com.berkay22demirel.readingisgood.service.mapper.Mappable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookService bookService;

    @Mock
    private Mappable<Order, OrderDto> orderMapper;

    @Test
    public void should_create_order() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);

        BasketItemRequestDto basketItemRequestDto1 = new BasketItemRequestDto();
        basketItemRequestDto1.setBookId(1L);
        basketItemRequestDto1.setCount(1L);
        BasketItemRequestDto basketItemRequestDto2 = new BasketItemRequestDto();
        basketItemRequestDto2.setBookId(2L);
        basketItemRequestDto2.setCount(2L);
        List<BasketItemRequestDto> requestBasketItems = Arrays.asList(basketItemRequestDto1, basketItemRequestDto2);

        Book book1 = new Book();
        book1.setId(1L);
        book1.setAmount(BigDecimal.TEN);
        Book book2 = new Book();
        book2.setId(2L);
        book2.setAmount(BigDecimal.ONE);

        when(bookService.getById(1L)).thenReturn(book1);
        when(bookService.getById(2L)).thenReturn(book2);
        when(orderMapper.convertToEntity(any())).thenReturn(new Order());

        //when
        orderService.create(customer, requestBasketItems);

        //then
        InOrder inOrder = Mockito.inOrder(bookService, orderMapper, orderRepository, bookService);
        inOrder.verify(bookService).getById(1L);
        inOrder.verify(bookService).getById(2L);
        inOrder.verify(orderMapper).convertToEntity(any());
        inOrder.verify(orderRepository).save(any());
        inOrder.verify(bookService).decreaseStock(1L, 1L);
        inOrder.verify(bookService).decreaseStock(2L, 2L);
    }

    @Test
    public void should_get_order_by_id() {
        //given
        Date date = new Date();
        Order order = new Order();
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(1L);
        orderDto.setBasketItems(Arrays.asList(new BasketItemDto(), new BasketItemDto()));
        orderDto.setDate(date);
        orderDto.setTotalAmount(BigDecimal.ONE);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.convertToDto(order)).thenReturn(orderDto);

        //when
        OrderDto returnedOrder = orderService.getById(1L);

        //then
        assertThat(returnedOrder.getCustomerId()).isEqualTo(1L);
        assertThat(returnedOrder.getBasketItems().size()).isEqualTo(2L);
        assertThat(returnedOrder.getDate()).isEqualTo(date);
        assertThat(returnedOrder.getTotalAmount()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void should_throw_not_found_exception_when_get_null_order() {
        //given
        when(orderRepository.findById(1L)).thenThrow(new NotFoundException("Order not found!"));

        //when
        Throwable throwable = catchThrowable(() -> orderService.getById(1L));

        //then
        assertThat(throwable).isInstanceOf(NotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Order not found!");
    }

    @Test
    public void should_get_orders_by_date() {
        //given
        Pageable pageable = PageRequest.of(0, 20);
        Date startDate = new Date();
        Date endDate = new Date();
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);
        OrderDto orderDto1 = new OrderDto();
        orderDto1.setDate(startDate);
        orderDto1.setCustomerId(1L);
        orderDto1.setTotalAmount(BigDecimal.TEN);
        BasketItemDto b1 = new BasketItemDto();
        b1.setAmount(new BigDecimal(5));
        orderDto1.setBasketItems(Arrays.asList(b1, b1));
        OrderDto orderDto2 = new OrderDto();
        orderDto2.setDate(endDate);
        orderDto2.setCustomerId(2L);
        orderDto2.setTotalAmount(BigDecimal.ONE);
        BasketItemDto b2 = new BasketItemDto();
        b2.setAmount(BigDecimal.ONE);
        orderDto2.setBasketItems(Collections.singletonList(b2));

        when(orderRepository.findByDateBetween(pageable, startDate, endDate)).thenReturn(new PageImpl<>(Arrays.asList(order1, order2), pageable, 2));
        when(orderMapper.convertToDto(order1)).thenReturn(orderDto1);
        when(orderMapper.convertToDto(order2)).thenReturn(orderDto2);

        //when
        Page<OrderDto> returnedOrders = orderService.getByDate(pageable, startDate, endDate);

        //then
        assertThat(returnedOrders.getTotalElements()).isEqualTo(2L);
        assertThat(returnedOrders.getContent().get(0).getTotalAmount()).isEqualTo(BigDecimal.TEN);
        assertThat(returnedOrders.getContent().get(0).getCustomerId()).isEqualTo(1L);
        assertThat(returnedOrders.getContent().get(0).getDate()).isEqualTo(startDate);
        assertThat(returnedOrders.getContent().get(0).getBasketItems().size()).isEqualTo(2L);
        assertThat(returnedOrders.getContent().get(1).getTotalAmount()).isEqualTo(BigDecimal.ONE);
        assertThat(returnedOrders.getContent().get(1).getCustomerId()).isEqualTo(2L);
        assertThat(returnedOrders.getContent().get(1).getDate()).isEqualTo(endDate);
        assertThat(returnedOrders.getContent().get(1).getBasketItems().size()).isEqualTo(1L);
    }

    @Test
    public void should_get_all_orders_by_customer() {
        //given
        Customer customer = new Customer();
        Pageable pageable = PageRequest.of(0, 20);
        Date dateForOrder1 = new Date();
        Date dateForOrder2 = new Date();
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);

        OrderDto orderDto1 = new OrderDto();
        orderDto1.setDate(dateForOrder1);
        orderDto1.setCustomerId(1L);
        orderDto1.setTotalAmount(BigDecimal.TEN);
        BasketItemDto b1 = new BasketItemDto();
        b1.setAmount(new BigDecimal(5));
        orderDto1.setBasketItems(Arrays.asList(b1, b1));

        OrderDto orderDto2 = new OrderDto();
        orderDto2.setDate(dateForOrder2);
        orderDto2.setCustomerId(1L);
        orderDto2.setTotalAmount(BigDecimal.ONE);
        BasketItemDto b2 = new BasketItemDto();
        b2.setAmount(BigDecimal.ONE);
        orderDto2.setBasketItems(Collections.singletonList(b2));

        when(orderRepository.findByCustomer(pageable, customer)).thenReturn(new PageImpl<>(Arrays.asList(order1, order2), pageable, 2));
        when(orderMapper.convertToDto(order1)).thenReturn(orderDto1);
        when(orderMapper.convertToDto(order2)).thenReturn(orderDto2);

        //when
        Page<OrderDto> returnedOrders = orderService.getAllByCustomer(pageable, customer);

        //then
        assertThat(returnedOrders.getTotalElements()).isEqualTo(2L);
        assertThat(returnedOrders.getContent().get(0).getTotalAmount()).isEqualTo(BigDecimal.TEN);
        assertThat(returnedOrders.getContent().get(0).getCustomerId()).isEqualTo(1L);
        assertThat(returnedOrders.getContent().get(0).getDate()).isEqualTo(dateForOrder1);
        assertThat(returnedOrders.getContent().get(0).getBasketItems().size()).isEqualTo(2L);
        assertThat(returnedOrders.getContent().get(1).getTotalAmount()).isEqualTo(BigDecimal.ONE);
        assertThat(returnedOrders.getContent().get(1).getCustomerId()).isEqualTo(1L);
        assertThat(returnedOrders.getContent().get(1).getDate()).isEqualTo(dateForOrder2);
        assertThat(returnedOrders.getContent().get(1).getBasketItems().size()).isEqualTo(1L);
    }

}
