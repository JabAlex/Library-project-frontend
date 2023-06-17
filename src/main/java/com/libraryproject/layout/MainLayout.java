package com.libraryproject.layout;

import com.libraryproject.view.BooksView;
import com.libraryproject.view.MainView;
import com.libraryproject.view.MoviesView;
import com.libraryproject.view.NewsView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        H1 title = new H1("Library App");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "0")
                .set("position", "absolute");

        Tabs tabs = getTabs();

        addToNavbar(title, tabs);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");
        tabs.add(createTab("Home", MainView.class), createTab("Books", BooksView.class),
                createTab("Movies", MoviesView.class), createTab("News", NewsView.class));
        return tabs;
    }

    private Tab createTab(String viewName, Class destinationView) {
        RouterLink link = new RouterLink();
        link.add(viewName);
        link.setRoute(destinationView);
        link.setTabIndex(-1);

        return new Tab(link);
    }
}
