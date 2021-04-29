package com.example.demo.service;

import com.example.demo.domein.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAllBooks();
    List<Book> getBooksByFilter(Optional<String> title,
                                Optional<String> author,
                                Optional<String> publisher,
                                Optional<String> genre,
                                Optional<String> id);
}
