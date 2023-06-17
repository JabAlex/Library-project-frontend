package com.libraryproject.view;

import com.libraryproject.domain.Book;
import com.libraryproject.domain.Movie;
import com.libraryproject.service.BookService;
import com.libraryproject.service.MovieService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;

@Route("top")
public class TopListsView extends HorizontalLayout {

    private final BookService bookService = BookService.getInstance();
    private final MovieService movieService = MovieService.getInstance();
    private Grid<Book> topBooksGrid = new Grid<>(Book.class, false);
    private Grid<Movie> topMoviesGrid = new Grid<>(Movie.class, false);


    public TopListsView(){
        topBooksGrid.addColumn(Book::getTitle).setHeader("Title").setSortable(false);
        topBooksGrid.addColumn(Book::getAuthor).setHeader("Author").setSortable(false);
        topBooksGrid.addColumn(Book::getReleaseYear).setHeader("Release Year").setSortable(false);
        topMoviesGrid.addColumn(Movie::getTitle).setHeader("Title").setSortable(false);
        topMoviesGrid.addColumn(Movie::getDirector).setHeader("Director").setSortable(false);
        topMoviesGrid.addColumn(Movie::getReleaseYear).setHeader("Release Year").setSortable(false);
        add(topBooksGrid, topMoviesGrid);
        setSizeFull();
        refreshBooks();
    }
    public void refreshBooks() {
        topBooksGrid.setItems(bookService.getTopBooks());
        topMoviesGrid.setItems(movieService.getTopMovies());
    }
}
