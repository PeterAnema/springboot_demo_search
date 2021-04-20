package com.example.demo.repository;

import com.example.demo.domein.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query( value = "SELECT * FROM books b " +
            "WHERE (:title = '' OR lower(b.title) LIKE %:title%) " +
            "AND (:author = '' OR lower(b.author) = :author) " +
            "AND (:publisher = '' OR lower(b.publisher) = :publisher) " +
            "AND (COALESCE(:genres, NULL) IS NULL OR lower(b.genre) in :genres)",
            nativeQuery = true
    )
    List<Book> findBookByFilterValues(@Param("title") String title,
                                      @Param("author") String author,
                                      @Param("publisher") String publisher,
                                      @Param("genres") List<String> genres);

}
