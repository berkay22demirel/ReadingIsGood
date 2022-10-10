package com.berkay22demirel.readingisgood.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 64)
    private String author;

    @DecimalMin(value = "1.0")
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Long stockCount;
}
