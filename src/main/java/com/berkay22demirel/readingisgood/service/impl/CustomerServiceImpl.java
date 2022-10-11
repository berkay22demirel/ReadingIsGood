package com.berkay22demirel.readingisgood.service.impl;

import com.berkay22demirel.readingisgood.dto.OrderDto;
import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.exception.BusinessException;
import com.berkay22demirel.readingisgood.repoitory.CustomerRepository;
import com.berkay22demirel.readingisgood.service.CustomerService;
import com.berkay22demirel.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;

    @Override
    public void create(String email, String password) {
        if (customerRepository.existsByEmail(email)) {
            throw new BusinessException("Email is already taken!");
        }
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(passwordEncoder.encode(password));
        customerRepository.save(customer);
        log.info("Created customer. Customer Email : {}", email);
    }

    @Override
    public Page<OrderDto> getAllOrders(Pageable pageable, Customer customer) {
        return orderService.getAllByCustomer(pageable, customer);
    }
}
