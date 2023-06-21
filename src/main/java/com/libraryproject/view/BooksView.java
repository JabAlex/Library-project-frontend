package com.libraryproject.view;

import com.libraryproject.domain.Book;
import com.libraryproject.layout.MainLayout;
import com.libraryproject.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;


@Route(value = "books", layout = MainLayout.class)
public class BooksView extends VerticalLayout {

    private final BookService bookService = BookService.getInstance();
    private Grid<Book> bookGrid = new Grid<>(Book.class);
    private TextField filter = new TextField();

    public BooksView(){
        bookGrid.setColumns("id","title", "author", "publicationYear", "numberOfAvailableCopies");
        bookGrid.addComponentColumn(book -> {
                Button button = new Button("Show details");
                button.addClickListener(click ->
                        button.getUI().ifPresent(ui ->
                                ui.navigate(DetailedBookView.class, book.getId())));
                return button;
                });
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
