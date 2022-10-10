package com.berkay22demirel.readingisgood.service.impl;

import com.berkay22demirel.readingisgood.dto.BasketItemDto;
import com.berkay22demirel.readingisgood.dto.BasketItemRequestDto;
import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.Book;
import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.entity.Order;
import com.berkay22demirel.readingisgood.exception.NotFoundException;
import com.berkay22demirel.readingisgood.repoitory.OrderRepository;
import com.berkay22demirel.readingisgood.service.BookService;
import com.berkay22demirel.readingisgood.service.OrderService;
import com.berkay22demirel.readingisgood.service.mapper.Mappable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void create(Customer customer, List<BasketItemRequestDto> requestBasketItems) {
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(customer.getId());
        orderDto.setDate(new Date());
        orderDto.setBasketItems(requestBasketItems.stream().map(requestBasketItem -> {
            Book book = bookService.getById(requestBasketItem.getBookId());
            BasketItemDto basketItemDto = new BasketItemDto();
            basketItemDto.setBookId(book.getId());
            basketItemDto.setAmount(book.getAmount().multiply(new BigDecimal(requestBasketItem.getCount())));
            basketItemDto.setCount(requestBasketItem.getCount());
            return basketItemDto;
        }).collect(Collectors.toList()));
        orderDto.setTotalAmount(orderDto.getBasketItems().stream().map(BasketItemDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        Order order = orderMapper.convertToEntity(orderDto);
        orderRepository.save(order);
        requestBasketItems.forEach(requestBasketItem -> {
            bookService.decreaseStock(requestBasketItem.getBookId(), requestBasketItem.getCount());
        });
    }

    @Override
    public OrderDto getById(Long id) {
        return orderMapper.convertToDto(orderRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found!")));
    }

    @Override
    public Page<OrderDto> getByDate(Pageable pageable, Date startDate, Date endDate) {
        return orderRepository.findByDateBetween(pageable, startDate, endDate)
                .map(orderMapper::convertToDto);
    }

    @Override
    public Page<OrderDto> getAllByCustomer(Pageable pageable, Customer customer) {
        return orderRepository.findByCustomer(pageable, customer)
                .map(orderMapper::convertToDto);
    }

}
