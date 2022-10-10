package com.berkay22demirel.readingisgood.service.impl;

import com.berkay22demirel.readingisgood.entity.Book;
import com.berkay22demirel.readingisgood.exception.NoStockException;
import com.berkay22demirel.readingisgood.exception.NotFoundException;
import com.berkay22demirel.readingisgood.repoitory.BookRepository;
import com.berkay22demirel.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Retryable(value = OptimisticLockException.class)
    @Override
    public void decreaseStock(Long id, Long purchasedCount) {
        Book book = getById(id);
        long lastStock = book.getStockCount() - purchasedCount;
        if (lastStock < 0) {
            throw new NoStockException("The book (" + book.getName() + ") is out of stock!");
        }
        book.setStockCount(lastStock);
        bookRepository.save(book);
    }

    @Override
    public Set<Book> getByIdsForOrder(Set<Long> ids) {
        return ids.stream().map(bookRepository::findById)
                .map(optionalBook -> optionalBook.orElseThrow(() -> new NotFoundException("Book not found!")))
                .peek(book -> {
                    if (book.getStockCount() <= 0) {
                        throw new NoStockException("The book (" + book.getName() + ") is out of stock!");
                    }
                })
                .collect(Collectors.toSet());
    }

}
