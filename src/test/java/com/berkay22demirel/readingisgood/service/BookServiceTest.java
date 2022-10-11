package com.berkay22demirel.readingisgood.service;

import com.berkay22demirel.readingisgood.entity.Book;
import com.berkay22demirel.readingisgood.exception.NoStockException;
import com.berkay22demirel.readingisgood.exception.NotFoundException;
import com.berkay22demirel.readingisgood.repoitory.BookRepository;
import com.berkay22demirel.readingisgood.service.impl.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void should_create_book() {
        //given

        //when
        bookService.create("book_name", "book_author", 1L, BigDecimal.TEN);

        //then
        InOrder inOrder = Mockito.inOrder(bookRepository);
        inOrder.verify(bookRepository).save(any(Book.class));
    }

    @Test
    public void should_get_book_by_id() {
        //given
        Book book = getDummyBook();
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        //when
        Book returnedBook = bookService.getById(book.getId());

        //then
        assertThat(returnedBook.getId()).isEqualTo(book.getId());
        assertThat(returnedBook.getName()).isEqualTo(book.getName());
        assertThat(returnedBook.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(returnedBook.getStockCount()).isEqualTo(book.getStockCount());
        assertThat(returnedBook.getAmount()).isEqualTo(book.getAmount());
    }

    @Test
    public void should_throw_not_found_exception_when_get_null_book() {
        //given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> bookService.getById(1L));

        //then
        assertThat(throwable).isInstanceOf(NotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Book not found!");
    }

    @Test
    public void should_update_stock() {
        //given
        Book book = getDummyBook();
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        //when
        bookService.updateStock(book.getId(), 8L);

        //then
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository, times(1)).save(captor.capture());
        Book updatedBook = captor.getValue();
        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getId()).isEqualTo(1L);
        assertThat(updatedBook.getStockCount()).isEqualTo(8L);
    }

    @Test
    public void should_throw_not_found_exception_when_update_null_book() {
        //given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> bookService.updateStock(1L, 1L));

        //then
        assertThat(throwable).isInstanceOf(NotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Book not found!");
    }

    @Test
    public void should_decrease_stock() {
        //given
        Book book = getDummyBook();
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        //when
        bookService.decreaseStock(book.getId(), 2L);

        //then
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository, times(1)).save(captor.capture());
        Book updatedBook = captor.getValue();
        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getId()).isEqualTo(1L);
        assertThat(updatedBook.getStockCount()).isEqualTo(8L);
    }

    @Test
    public void should_throw_not_found_exception_when_decrease_null_book_stock() {
        //given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> bookService.decreaseStock(1L, 1L));

        //then
        assertThat(throwable).isInstanceOf(NotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Book not found!");
    }

    @Test
    public void should_throw_no_stock_exception_when_negative_stock() {
        //given
        Book book = getDummyBook();
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        //when
        Throwable throwable = catchThrowable(() -> bookService.decreaseStock(1L, 11L));

        //then
        assertThat(throwable).isInstanceOf(NoStockException.class);
        assertThat(throwable.getMessage()).isEqualTo("The book (" + book.getName() + ") is out of stock!");
    }

    @Test
    public void should_get_orders() {
        //given
        Book book1 = getDummyBook();
        Book book2 = getDummyBook();
        book2.setId(2L);
        when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));
        when(bookRepository.findById(book2.getId())).thenReturn(Optional.of(book2));

        //when
        Set<Book> books = bookService.getByIdsForOrder(new HashSet<>(Arrays.asList(1L, 2L)));

        //then
        assertThat(books.size()).isEqualTo(2);
        assertThat(books.contains(book1)).isTrue();
        assertThat(books.contains(book2)).isTrue();
        assertThat(books.contains(new Book())).isFalse();
    }

    @Test
    public void should_throw_not_found_exception_when_get_null_books() {
        //given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> bookService.getByIdsForOrder(new HashSet<>(Arrays.asList(1L, 2L))));

        //then
        assertThat(throwable).isInstanceOf(NotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Book not found!");
    }

    @Test
    public void should_throw_no_stock_exception_when_get_negative_stock() {
        //given
        Book book1 = getDummyBook();
        Book book2 = getDummyBook();
        book2.setStockCount(0L);
        when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));
        when(bookRepository.findById(book2.getId())).thenReturn(Optional.of(book2));

        //when
        Throwable throwable = catchThrowable(() -> bookService.getByIdsForOrder(new HashSet<>(Arrays.asList(1L, 2L))));

        //then
        assertThat(throwable).isInstanceOf(NoStockException.class);
        assertThat(throwable.getMessage()).isEqualTo("The book (" + book2.getName() + ") is out of stock!");
    }

    private Book getDummyBook() {
        Book book = new Book();
        book.setId(1L);
        book.setName("book_name");
        book.setName("book_author");
        book.setStockCount(10L);
        book.setAmount(BigDecimal.TEN);
        return book;
    }
}
