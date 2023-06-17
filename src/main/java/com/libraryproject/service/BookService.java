package com.libraryproject.service;

import com.libraryproject.domain.Book;
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
        this.books = getBookData();
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

    public Set<Book> getTopBooks(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/library/books/top-month").build().toUri();
        return getBooksFromBackend(url);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    private Set<Book> getBookData(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/library/books").build().toUri();
        return getBooksFromBackend(url);
    }

    private Set<Book> getBooksFromBackend(URI url) {
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
