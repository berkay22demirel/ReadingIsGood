package com.berkay22demirel.readingisgood.service.impl;

import com.berkay22demirel.readingisgood.entity.Book;
import com.berkay22demirel.readingisgood.exception.NotFoundException;
import com.berkay22demirel.readingisgood.repoitory.BookRepository;
import com.berkay22demirel.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public void create(String name, String author, Long stockCount, BigDecimal amount) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setStockCount(stockCount);
        book.setAmount(amount);
        bookRepository.save(book);
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found!"));
    }

    @Override
    public void updateStock(Long id, Long stockCount) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found!"));
        book.setStockCount(stockCount);
        bookRepository.save(book);
    }

}
