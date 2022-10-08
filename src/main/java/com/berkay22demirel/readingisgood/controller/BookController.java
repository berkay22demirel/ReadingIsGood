package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.request.CreateBookRequest;
import com.berkay22demirel.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RequestMapping("api/v1/book")
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateBookRequest request) {
        bookService.create(request.getName(), request.getAuthor(), request.getStockCount());
        return new ResponseEntity<>("Book created successfully.", HttpStatus.OK);
    }

    @PutMapping("/{id}/stock")
    ResponseEntity<?> updateStock(@PathVariable @NotNull Long id, @RequestBody Long stockCount) {
        bookService.updateStock(id, stockCount);
        return new ResponseEntity<>("Book stock updated successfully.", HttpStatus.OK);
    }
}
