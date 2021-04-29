package com.example.demo.controller;

import com.example.demo.domein.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<Object> getBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/search")
    public ResponseEntity<Object> getBooksByFilter(@RequestParam(name="title") Optional<String> title,
                                                   @RequestParam(name="author") Optional<String>  author,
                                                   @RequestParam(name="publisher") Optional<String>  publisher,
                                                   @RequestParam(name="genre") Optional<String>  genre,
                                                   @RequestParam(name="id") Optional<String>  id) {
            List<Book> books = bookService.getBooksByFilter(title, author, publisher, genre, id);
        return ResponseEntity.ok(books);
    }

}
