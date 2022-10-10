package com.berkay22demirel.readingisgood.service.mapper;

import com.berkay22demirel.readingisgood.dto.BasketItemDto;
import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.BasketItem;
import com.berkay22demirel.readingisgood.entity.Order;
import com.berkay22demirel.readingisgood.repoitory.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderMapper implements Mappable<Order, OrderDto> {

    private final CustomerRepository customerRepository;
    private final Mappable<BasketItem, BasketItemDto> basketItemMapper;

    @Override
    public Order convertToEntity(OrderDto dto) {
        Order entity = new Order();
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setBasketItems(dto.getBasketItems().stream()
                .map(basketItemMapper::convertToEntity)
                .collect(Collectors.toList()));
        entity.getBasketItems()
                .forEach(basketItem -> basketItem.setOrder(entity));
        entity.setCustomer(customerRepository.getReferenceById(dto.getCustomerId()));
        entity.setDate(dto.getDate());
        return entity;
    }

    @Override
    public OrderDto convertToDto(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setBasketItems(entity.getBasketItems().stream()
                .map(basketItemMapper::convertToDto)
                .collect(Collectors.toList()));
        dto.setCustomerId(entity.getCustomer().getId());
        dto.setDate(entity.getDate());
        return dto;
    }
}
