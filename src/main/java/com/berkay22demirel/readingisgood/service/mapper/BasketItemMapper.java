package com.berkay22demirel.readingisgood.service.mapper;

import com.berkay22demirel.readingisgood.dto.BasketItemDto;
import com.berkay22demirel.readingisgood.entity.BasketItem;
import com.berkay22demirel.readingisgood.repoitory.BookRepository;
import com.berkay22demirel.readingisgood.repoitory.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BasketItemMapper implements Mappable<BasketItem, BasketItemDto> {

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    @Override
    public BasketItem convertToEntity(BasketItemDto dto) {
        BasketItem entity = new BasketItem();
        entity.setOrder(dto.getOrderId() != null ? orderRepository.getReferenceById(dto.getOrderId()) : null);
        entity.setAmount(dto.getAmount());
        entity.setBook(bookRepository.getReferenceById(dto.getBookId()));
        entity.setCount(dto.getCount());
        return entity;
    }

    @Override
    public BasketItemDto convertToDto(BasketItem entity) {
        BasketItemDto dto = new BasketItemDto();
        dto.setOrderId(entity.getOrder().getId());
        dto.setAmount(entity.getAmount());
        dto.setBookId(entity.getBook().getId());
        dto.setCount(entity.getCount());
        return dto;
    }
}
