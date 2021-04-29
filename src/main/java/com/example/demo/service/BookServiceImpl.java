package com.example.demo.service;

import com.example.demo.domein.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.service.BookSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

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
                                       Optional<String> genre,
                                       Optional<String> id) {

        List<String> genres = null;
        if (genre.isPresent() && !genre.get().isEmpty()) {
            genres = new ArrayList<>();
            for (String s : genre.get().split(",")) {
                genres.add(s.trim().toLowerCase());
            }
        }

        List<Long> ids = null;
        if (id.isPresent() && !id.get().isEmpty()) {
            ids = new ArrayList<>();
            for (String s : id.get().split(",")) {
                ids.add(Long.parseLong(s.trim()));
            }
        }

        return bookRepository.findAll(
                where(withTitle(title.orElse(null)))
                .and(withAuthor(author.orElse(null)))
                .and(withPublisher(publisher.orElse(null)))
                .and(withGenres(genres))
                .and(withIds(ids))
        );

    }

}