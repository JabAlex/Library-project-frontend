package com.libraryproject.view;

import com.libraryproject.domain.DetailedMovie;
import com.libraryproject.layout.MainLayout;
import com.libraryproject.service.MovieService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "movies", layout = MainLayout.class)
public class DetailedMovieView extends VerticalLayout implements HasUrlParameter<Long> {

    MovieService movieService = MovieService.getInstance();
    Grid<DetailedMovie> grid = new Grid<>(DetailedMovie.class);

    @Override
    public void setParameter(BeforeEvent event, Long receivedMovieId){
        grid.setItems(movieService.getDetailedMovie(receivedMovieId));
    }

    public DetailedMovieView() {
        grid.setColumns("title", "director", "releaseYear","numberOfAvailableCopies", "synopsis");
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        add(grid);
        setSizeFull();
    }

}