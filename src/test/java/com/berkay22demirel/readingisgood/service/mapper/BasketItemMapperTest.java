package com.berkay22demirel.readingisgood.service.mapper;

import com.berkay22demirel.readingisgood.dto.BasketItemDto;
import com.berkay22demirel.readingisgood.entity.BasketItem;
import com.berkay22demirel.readingisgood.entity.Book;
import com.berkay22demirel.readingisgood.entity.Order;
import com.berkay22demirel.readingisgood.repoitory.BookRepository;
import com.berkay22demirel.readingisgood.repoitory.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasketItemMapperTest {

    @InjectMocks
    private BasketItemMapper basketItemMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void should_convert_to_entity() {
        //given
        BasketItemDto basketItemDto = new BasketItemDto();
        basketItemDto.setAmount(BigDecimal.ONE);
        basketItemDto.setCount(11L);
        basketItemDto.setBookId(111L);
        basketItemDto.setOrderId(1111L);

        Order order = new Order();
        Book book = new Book();

        when(orderRepository.getReferenceById(1111L)).thenReturn(order);
        when(bookRepository.getReferenceById(111L)).thenReturn(book);

        //when
        BasketItem basketItem = basketItemMapper.convertToEntity(basketItemDto);

        //then
        assertThat(basketItem).isNotNull();
        assertThat(basketItem.getAmount()).isEqualTo(BigDecimal.ONE);
        assertThat(basketItem.getCount()).isEqualTo(11L);
        assertThat(basketItem.getId()).isNull();
        assertThat(basketItem.getBook()).isEqualTo(book);
        assertThat(basketItem.getOrder()).isEqualTo(order);
    }

    @Test
    public void should_convert_to_dto() {
        //given
        Book book = new Book();
        book.setId(111L);
        Order order = new Order();
        order.setId(1111L);

        BasketItem basketItem = new BasketItem();
        basketItem.setAmount(BigDecimal.ONE);
        basketItem.setCount(11L);
        basketItem.setBook(book);
        basketItem.setOrder(order);

        //when
        BasketItemDto basketItemDto = basketItemMapper.convertToDto(basketItem);

        //then
        assertThat(basketItemDto).isNotNull();
        assertThat(basketItemDto.getAmount()).isEqualTo(BigDecimal.ONE);
        assertThat(basketItemDto.getCount()).isEqualTo(11L);
        assertThat(basketItemDto.getBookId()).isEqualTo(book.getId());
        assertThat(basketItemDto.getOrderId()).isEqualTo(order.getId());
    }
}
