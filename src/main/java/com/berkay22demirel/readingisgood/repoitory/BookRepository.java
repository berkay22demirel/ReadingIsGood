package com.berkay22demirel.readingisgood.repoitory;

import com.berkay22demirel.readingisgood.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    
}
