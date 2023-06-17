package com.libraryproject.service;

import com.libraryproject.domain.Article;
import com.libraryproject.domain.Book;
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
public class NewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsService.class);

    private Set<Article> news;

    private static NewsService newsService;

    public NewsService() {
        this.news = getArticleData();
    }

    public static NewsService getInstance(){
        if(newsService == null){
            newsService = new NewsService();
        }
        return newsService;
    }

    public Set<Article> getRecentNews(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/library/news/recent").build().toUri();
        return getDataFromBackend(url);
    }

    public Set<Article> findByAuthor(String author) {
        return news.stream()
                .filter(news -> news.getAuthor().toLowerCase().contains(author))
                .collect(Collectors.toSet());
    }

    private Set<Article> getArticleData(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/v1/library/news").build().toUri();
        return getDataFromBackend(url);
    }

    private Set<Article> getDataFromBackend(URI url) {
        RestTemplate restTemplate = new RestTemplate();
        try{
            Article[] response = restTemplate.getForObject(url, Article[].class);
            return new HashSet<>(Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e){
            LOGGER.error(e.getMessage(), e);
            return Collections.emptySet();
        }
    }
}
