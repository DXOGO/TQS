package com.dxogo.lab07_3testcontainers;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.dxogo.lab07_3testcontainers.model.Book;
import com.dxogo.lab07_3testcontainers.repository.BookRepository;

@Testcontainers
@SpringBootTest
@SuppressWarnings("rawtypes")
public class BookTest {
    
    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
        .withUsername("duke")
        .withPassword("password")
        .withDatabaseName("test");

  @Autowired private BookRepository bookRepository;

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  @Order(1)
  public void testCreateBook() {
      Book book = new Book();
      book.setName("Dune");
      bookRepository.saveAndFlush(book);
  }

  @Test
  @Order(2)
  public void testListBooks() {
      List<Book> books = bookRepository.findAll();
      assertThat(books).hasSize(1).extracting(Book::getName).contains("Dune");
  }

  @Test
  @Order(3)
  public void testUpdateBook() {
    List<Book> books = bookRepository.findAll();

    Book newbook = new Book();
    newbook.setName("Joker");
    bookRepository.saveAndFlush(newbook);

    for (Book book : books){
        if (book.getName().equals("Dune")){
            book.setName("Batman");
        }
        assertThat(book.getName()).isEqualTo("Batman");
        assertThat(books).hasSize(2).extracting(Book::getName).contains("Batman");
    }
  }

  @Test
  @Order(4)
  public void testDeleteBook() {
      bookRepository.deleteAll();
      assertThat(bookRepository.count()).isEqualTo(0);
  }

}
