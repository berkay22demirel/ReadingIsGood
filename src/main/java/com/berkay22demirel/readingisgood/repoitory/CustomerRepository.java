package com.berkay22demirel.readingisgood.repoitory;

import com.berkay22demirel.readingisgood.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Boolean existsByEmail(String email);
}
