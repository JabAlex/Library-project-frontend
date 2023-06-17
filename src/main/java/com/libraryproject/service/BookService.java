package com.libraryproject.service;

import com.libraryproject.domain.Book;
import com.libraryproject.domain.DetailedBook;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class BookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    private Set<Book> books;
    private static BookService bookService;

    private BookService(){
        this.books = getAllBooks();
    }

    public static BookService getInstance() {
        if (bookService == null) {
            bookService = new BookService();
        }
        return bookService;
    }

    public Set<Book> findByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title))
                .collect(Collectors.toSet());
    }
    public DetailedBook getDetailedBook(Long bookId){
        RestTemplate restTemplate = new RestTemplate();
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/library/books/" + bookId).build().toUri();
        try {
            DetailedBook response = restTemplate.getForObject(url, DetailedBook.class);
            return Optional.ofNullable(response)
                    .orElse(new DetailedBook());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new DetailedBook();
        }
    }

    public Set<Book> getTopBooks(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/library/books/top-month").build().toUri();
        return getDataFromBackend(url);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    private Set<Book> getAllBooks(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/library/books").build().toUri();
        return getDataFromBackend(url);
    }

    private Set<Book> getDataFromBackend(URI url) {
        RestTemplate restTemplate = new RestTemplate();
        try{
            Book[] response = restTemplate.getForObject(url, Book[].class);
            return new HashSet<>(Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e){
            LOGGER.error(e.getMessage(), e);
            return Collections.emptySet();
        }
    }
}
