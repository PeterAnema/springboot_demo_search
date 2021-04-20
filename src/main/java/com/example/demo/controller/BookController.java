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
    public ResponseEntity<Object> getBooksByFilter(@RequestParam(name="title", defaultValue="") Optional<String> title,
                                                   @RequestParam(name="author", defaultValue="") Optional<String>  author,
                                                   @RequestParam(name="publisher", defaultValue="") Optional<String>  publisher,
                                                   @RequestParam(name="genre", defaultValue="") Optional<String>  genre) {
            List<Book> books = bookService.getBooksByFilter(title, author, publisher, genre);
        return ResponseEntity.ok(books);
    }

}
