package com.berkay22demirel.readingisgood.repoitory;

import com.berkay22demirel.readingisgood.entity.Order;
import com.berkay22demirel.readingisgood.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndUser(Long id, User user);

    Page<Order> findByDateBetweenAndUser(Pageable pageable, Date startDate, Date endDate, User user);
}
