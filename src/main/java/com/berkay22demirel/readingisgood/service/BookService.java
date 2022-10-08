package com.berkay22demirel.readingisgood.service;

import com.berkay22demirel.readingisgood.entity.Book;
import com.berkay22demirel.readingisgood.exception.NotFoundException;
import com.berkay22demirel.readingisgood.repoitory.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public void create(String name, String author, Long stockCount) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setStockCount(stockCount);
        bookRepository.save(book);
    }

    public void updateStock(Long id, Long stockCount) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found!"));
        book.setStockCount(stockCount);
        bookRepository.save(book);
    }
}
