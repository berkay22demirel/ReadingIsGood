package com.berkay22demirel.readingisgood.repoitory;

import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndCustomer(Long id, Customer customer);

    Page<Order> findByDateBetween(Pageable pageable, Date startDate, Date endDate);

    Page<Order> findByCustomer(Pageable pageable, Customer customer);
}
