package com.example.demo.service;

import com.example.demo.domein.Book;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

public class BookSpecifications {

    public static Specification<Book> withTitle(String title) {
        if (title == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.like(root.get("title"), title);
        }
    }

    public static Specification<Book> withAuthor(String author) {
        if (author == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.equal(root.get("author"), author);
        }
    }

    public static Specification<Book> withPublisher(String publisher) {
        if (publisher == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.equal(root.get("publisher"), publisher);
        }
    }

    public static Specification<Book> withGenres(Collection<String> genres) {
        if (genres == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.in(root.get("genre")).value(genres);
        }
    }

    public static Specification<Book> withIds(Collection<Long> ids) {
        if (ids == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.in(root.get("id")).value(ids);
        }
    }

}
