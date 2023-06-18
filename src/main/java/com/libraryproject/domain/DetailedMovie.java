package com.libraryproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedMovie {
    private Long id;
    private String title;
    private String director;
    private int releaseYear;
    private int numberOfAvailableCopies;
    private String synopsis;
}
