# Beschrijving van mijn project

## Installatie

Wordt initieel opgeleverd met H2 database.

## Application.properties

Pas applications.properties aan voor het gebruik van Postgresql ....
* Pas database server aan
* Pas database user aan
* Pas database password aan

## Endpoints

* GET https://localhost:8080/books<br>
  To get all books.
  

* GET https://localhost:8080/books/search?title=book1&author=A&genre=sf,thriller
  To search for books on title, author, publisher or genre.
  All search terms are optional.
  Searching on genre allows multiple genres seperated by a comma.
  
## Custom Query in BookRepository

```java
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
```

## BookServiceImpl

```java
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
```