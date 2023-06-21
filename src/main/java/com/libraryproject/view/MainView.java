package com.libraryproject.view;

import com.libraryproject.domain.Article;
import com.libraryproject.domain.Book;
import com.libraryproject.domain.Movie;
import com.libraryproject.layout.MainLayout;
import com.libraryproject.service.BookService;
import com.libraryproject.service.MovieService;
import com.libraryproject.service.NewsService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(layout = MainLayout.class)
public class MainView extends VerticalLayout {

    private final BookService bookService = BookService.getInstance();
    private final MovieService movieService = MovieService.getInstance();
    private final NewsService newsService = NewsService.getInstance();
    private Grid<Book> topBooksGrid = new Grid<>(Book.class, false);
    private Grid<Movie> topMoviesGrid = new Grid<>(Movie.class, false);
    private Grid<Article> newsGrid = new Grid<>(Article.class, false);

    public MainView(){
        gridSetUp();
        add(newsGrid, topBooksGrid, topMoviesGrid);
        setSizeFull();
        refresh();
    }


    private void gridSetUp(){
        newsGrid.addColumn(Article::getAuthor).setHeader("Author").setSortable(false);
        newsGrid.addColumn(Article::getContent).setHeader("Content").setSortable(false);
        newsGrid.addColumn(Article::getPublicationDate).setHeader("Publication Date").setSortable(false);

        topBooksGrid.addColumn(Book::getTitle).setHeader("Title").setSortable(false);
        topBooksGrid.addColumn(Book::getAuthor).setHeader("Author").setSortable(false);
        topBooksGrid.addColumn(Book::getPublicationYear).setHeader("Release Year").setSortable(false);

        topMoviesGrid.addColumn(Movie::getTitle).setHeader("Title").setSortable(false);
        topMoviesGrid.addColumn(Movie::getDirector).setHeader("Director").setSortable(false);
        topMoviesGrid.addColumn(Movie::getReleaseYear).setHeader("Release Year").setSortable(false);
    }
    public void refresh() {
        topBooksGrid.setItems(bookService.getTopBooks());
        topMoviesGrid.setItems(movieService.getTopMovies());
        newsGrid.setItems(newsService.getNews());
    }
}
