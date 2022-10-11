package com.berkay22demirel.readingisgood.service;

import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.exception.BusinessException;
import com.berkay22demirel.readingisgood.repoitory.CustomerRepository;
import com.berkay22demirel.readingisgood.service.impl.CustomerServiceImpl;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OrderService orderService;

    @Test
    public void should_create_customer() {
        //given

        //when
        customerService.create("email", "password");

        //then
        InOrder inOrder = Mockito.inOrder(customerRepository);
        inOrder.verify(customerRepository).save(any(Customer.class));
    }

    @Test
    public void should_throw_business_exception_when_exists_email() {
        //given
        when(customerRepository.existsByEmail("email")).thenReturn(true);

        //when
        Throwable throwable = catchThrowable(() -> customerService.create("email", "password"));

        //then
        assertThat(throwable).isInstanceOf(BusinessException.class);
        assertThat(throwable.getMessage()).isEqualTo("Email is already taken!");
    }

    @Test
    public void should_get_orders() {
        //given
        OrderDto order1 = new OrderDto();
        order1.setCustomerId(1L);
        OrderDto order2 = new OrderDto();
        order2.setCustomerId(1L);
        Customer customer = new Customer();
        customer.setId(1L);
        Pageable pageable = PageRequest.of(0, 20);
        Page<OrderDto> orders = new PageImpl<>(Arrays.asList(order1, order2), pageable, 2);
        when(orderService.getAllByCustomer(pageable, customer)).thenReturn(orders);

        //when
        Page<OrderDto> returnObjects = customerService.getAllOrders(pageable, customer);

        //then
        assertThat(returnObjects.getTotalElements()).isEqualTo(2);
        assertThat(returnObjects.getContent().contains(order1)).isTrue();
        assertThat(returnObjects.getContent().contains(order2)).isTrue();
        assertThat(returnObjects.getContent().contains(new OrderDto())).isFalse();
    }
}
