package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.request.CreateBookRequest;
import com.berkay22demirel.readingisgood.controller.response.Response;
import com.berkay22demirel.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RequestMapping("api/v1/books")
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody @Valid CreateBookRequest request) {
        bookService.create(request.getName(), request.getAuthor(), request.getStockCount(), request.getAmount());
        return new ResponseEntity<>(new Response("Book created successfully."), HttpStatus.OK);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Response> updateStock(@PathVariable @NotNull Long id, @RequestBody @NotNull @Min(0) Long stockCount) {
        bookService.updateStock(id, stockCount);
        return new ResponseEntity<>(new Response("Book stock updated successfully."), HttpStatus.OK);
    }
}
