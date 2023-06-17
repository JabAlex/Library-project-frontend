package com.libraryproject.view;

import com.libraryproject.domain.Article;
import com.libraryproject.layout.MainLayout;
import com.libraryproject.service.NewsService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "news", layout = MainLayout.class)
public class NewsView extends VerticalLayout {

    private final NewsService newsService = NewsService.getInstance();

    private Grid<Article> newsGrid = new Grid<>(Article.class);
    private TextField filter = new TextField();

    public NewsView(){
        newsGrid.setColumns("author", "content", "publicationDate");
        filter.setPlaceholder("Search by author");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(event -> updateSearch());
        add(filter, newsGrid);
        refreshNews();
    }

    private void refreshNews() {
        newsGrid.setItems(newsService.getNews());
    }

    private void updateSearch(){
        newsGrid.setItems(newsService.findByAuthor(filter.getValue()));
    }
}
