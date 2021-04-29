# Beschrijving van mijn project

## Installatie

Wordt initieel opgeleverd met H2 database.

Pas applications.properties aan voor het gebruik van Postgresql ....
* Pas database server aan
* Pas database user aan
* Pas database password aan

## Endpoints

* GET https://localhost:8080/books
  * To get all books.
  

* GET https://localhost:8080/books/search?title=book1&author=A&genre=sf,thriller&id=1,2
  * To search for books on title, author, publisher, genre or id.
  * All search terms are optional.
  * Searching on genre or id allows multiple genres seperated by a comma.
  
## Custom Query in BookRepository

```java
    @Query( value = "SELECT * FROM books b " +
            "WHERE (:title = '' OR lower(b.title) LIKE %:title%) " +
            "AND (:author = '' OR lower(b.author) = :author) " +
            "AND (:publisher = '' OR lower(b.publisher) = :publisher) " +
            "AND (COALESCE(:genres, NULL) IS NULL OR lower(b.genre) in :genres)" +
            "AND (COALESCE(:ids, NULL) IS NULL OR id in :ids)",
            nativeQuery = true
    )
    List<Book> findBookByFilterValues(@Param("title") String title,
                                      @Param("author") String author,
                                      @Param("publisher") String publisher,
                                      @Param("genres") List<String> genres,
                                      @Param("ids") List<Long> ids);
```

## BookServiceImpl

```java
    public List<Book> getBooksByFilter(Optional<String> title,
                                       Optional<String> author,
                                       Optional<String> publisher,
                                       Optional<String> genre,
                                       Optional<String> id) {

        List<String> genres = new ArrayList<>();
        if (genre.isPresent() && !genre.get().isEmpty()) {
            for (String s: genre.get().split(",")) {
                genres.add(s.trim().toLowerCase());
            }
        }

        List<Long> ids = new ArrayList<>();
        if (id.isPresent() && !id.get().isEmpty()) {
            for (String s: id.get().split(",")) {
                ids.add(Long.parseLong(s.trim()));
            }
        }

        return bookRepository.findBookByFilterValues(
                title.orElse("").toLowerCase().trim(),
                author.orElse("").toLowerCase().trim(),
                publisher.orElse("").toLowerCase().trim(),
                genres,
                ids);
    }
```