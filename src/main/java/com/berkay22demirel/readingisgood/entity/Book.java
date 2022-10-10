package com.berkay22demirel.readingisgood.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "book")
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

    @Min(0)
    @Column(nullable = false)
    private Long stockCount;

    @Version
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long version;
}
