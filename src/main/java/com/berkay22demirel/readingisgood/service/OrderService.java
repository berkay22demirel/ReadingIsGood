package com.berkay22demirel.readingisgood.service;

import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface OrderService {

    void create(User user, List<Long> bookIdList);

    OrderDto get(Long id, User user);

    Page<OrderDto> getByDate(Pageable pageable, Date startDate, Date endDate, User User);
}
