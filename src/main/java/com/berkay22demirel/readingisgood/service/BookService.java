package com.berkay22demirel.readingisgood.service;

import com.berkay22demirel.readingisgood.entity.Book;

import java.math.BigDecimal;

public interface BookService {

    void create(String name, String author, Long stockCount, BigDecimal amount);

    Book getById(Long id);

    void updateStock(Long id, Long stockCount);
}
