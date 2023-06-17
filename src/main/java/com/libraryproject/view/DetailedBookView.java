package com.libraryproject.view;

import com.libraryproject.domain.DetailedBook;
import com.libraryproject.layout.MainLayout;
import com.libraryproject.service.BookService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "books", layout = MainLayout.class)
public class DetailedBookView extends VerticalLayout implements HasUrlParameter<Long> {

    BookService bookService = BookService.getInstance();
    Grid<DetailedBook> grid = new Grid<>(DetailedBook.class);

    @Override
    public void setParameter(BeforeEvent event, Long receivedBookId){
        grid.setItems(bookService.getDetailedBook(receivedBookId));
    }

    public DetailedBookView() {
        grid.setColumns("title", "author", "releaseYear","numberOfAvailableCopies", "synopsis");
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        add(grid);
        setSizeFull();
    }

}
