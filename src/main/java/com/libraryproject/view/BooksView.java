package com.libraryproject.view;

import com.libraryproject.domain.Book;
import com.libraryproject.service.BookService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;


@Route("books")
public class BooksView extends VerticalLayout {

    private final BookService bookService = BookService.getInstance();
    private Grid<Book> bookGrid = new Grid<>(Book.class);
    private TextField filter = new TextField();

    public BooksView(){
        bookGrid.setColumns("title", "author", "releaseYear", "numberOfAvailableCopies");
        filter.setPlaceholder("Search by title");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(event -> updateSearch());
        add(filter, bookGrid);
        refreshBooks();
    }

    private void refreshBooks() {
        bookGrid.setItems(bookService.getBooks());
    }

    private void updateSearch(){
        bookGrid.setItems(bookService.findByTitle(filter.getValue()));
    }
}
