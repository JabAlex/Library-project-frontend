package com.libraryproject.view;

import com.libraryproject.domain.Movie;
import com.libraryproject.layout.MainLayout;
import com.libraryproject.service.MovieService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;


@Route(value = "movies", layout = MainLayout.class)
public class MoviesView extends VerticalLayout {
    private final MovieService movieService = MovieService.getInstance();
    private Grid<Movie> movieGrid = new Grid<>(Movie.class);
    private TextField filter = new TextField();

    public MoviesView(){
        movieGrid.setColumns("id", "title", "director", "releaseYear", "numberOfAvailableCopies");
        movieGrid.addComponentColumn(movie -> {
            Button button = new Button("Show details");
            button.addClickListener(click ->
                    button.getUI().ifPresent(ui ->
                            ui.navigate(DetailedMovieView.class, movie.getId())));
            return button;
        });
        filter.setPlaceholder("Search by title");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(event -> updateSearch());
        add(filter, movieGrid);
        refreshMovies();
    }

    private void refreshMovies() {
        movieGrid.setItems(movieService.getMovies());
    }

    private void updateSearch(){
        movieGrid.setItems(movieService.findByTitle(filter.getValue()));
    }
}
