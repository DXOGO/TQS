package com.dxogo.lab07_3testcontainers.repository;

import com.dxogo.lab07_3testcontainers.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
