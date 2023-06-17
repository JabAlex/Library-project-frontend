package com.libraryproject.service;

import com.libraryproject.domain.Movie;
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
public class MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

    private Set<Movie> movies;
    private static MovieService movieService;

    public MovieService() {
        this.movies = getMovieData();
    }
    public static MovieService getInstance(){
        if(movieService == null){
            movieService = new MovieService();
        }
        return movieService;
    }

    public Set<Movie> findByTitle(String title){
        return movies.stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(title))
                .collect(Collectors.toSet());
    }

    public Set<Movie> getTopMovies(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/library/movies/top-month").build().toUri();
        return getMoviesFromBackend(url);
    }

    private Set<Movie> getMovieData(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/library/movies").build().toUri();
        return getMoviesFromBackend(url);
    }

    private Set<Movie> getMoviesFromBackend(URI url) {
        RestTemplate restTemplate = new RestTemplate();
        try{
            Movie[] response = restTemplate.getForObject(url, Movie[].class);
            return new HashSet<>(Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e){
            LOGGER.error(e.getMessage(), e);
            return Collections.emptySet();
        }
    }
}
