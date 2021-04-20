package com.example.demo.service;

import com.example.demo.domein.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByFilter(Optional<String> title,
                                       Optional<String> author,
                                       Optional<String> publisher,
                                       Optional<String> genre) {

        List<String> genres = new ArrayList<>();
        if (genre.isPresent() && !genre.get().isEmpty()) {
            for (String g: genre.get().split(",")) {
                genres.add(g.toLowerCase().trim());
            }
        }
        else {
            genres = new ArrayList<>();
        }

        return bookRepository.findBookByFilterValues(
                title.orElse("").toLowerCase().trim(),
                author.orElse("").toLowerCase().trim(),
                publisher.orElse("").toLowerCase().trim(),
                genres);
    }



}
