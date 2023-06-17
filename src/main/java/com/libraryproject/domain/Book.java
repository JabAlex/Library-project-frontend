package com.libraryproject.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Long bookId;
    private String title;
    private String author;
    private int releaseYear;
    private int numberOfAvailableCopies;
}
