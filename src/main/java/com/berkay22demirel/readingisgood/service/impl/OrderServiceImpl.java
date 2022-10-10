package com.berkay22demirel.readingisgood.service.impl;

import com.berkay22demirel.readingisgood.dto.BasketItemDto;
import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.Book;
import com.berkay22demirel.readingisgood.entity.Order;
import com.berkay22demirel.readingisgood.entity.User;
import com.berkay22demirel.readingisgood.exception.NotFoundException;
import com.berkay22demirel.readingisgood.repoitory.OrderRepository;
import com.berkay22demirel.readingisgood.service.BookService;
import com.berkay22demirel.readingisgood.service.OrderService;
import com.berkay22demirel.readingisgood.service.mapper.Mappable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final Mappable<Order, OrderDto> orderMapper;
    
    @Override
    public void create(User user, List<Long> bookIdList) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(user.getId());
        orderDto.setDate(new Date());
        orderDto.setBasketItems(bookIdList.stream().map(bookId -> {
            Book book = bookService.getById(bookId);
            BasketItemDto basketItemDto = new BasketItemDto();
            basketItemDto.setBookId(bookId);
            basketItemDto.setAmount(book.getAmount());
            return basketItemDto;
        }).collect(Collectors.toList()));
        orderDto.setTotalAmount(orderDto.getBasketItems().stream().map(BasketItemDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        Order order = orderMapper.convertToEntity(orderDto);
        orderRepository.save(order);
    }

    @Override
    public OrderDto get(Long id, User user) {
        return orderMapper.convertToDto(orderRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new NotFoundException("Order not found!")));
    }

    @Override
    public Page<OrderDto> getByDate(Pageable pageable, Date startDate, Date endDate, User user) {
        return orderRepository.findByDateBetweenAndUser(pageable, startDate, endDate, user)
                .map(orderMapper::convertToDto);
    }
}
