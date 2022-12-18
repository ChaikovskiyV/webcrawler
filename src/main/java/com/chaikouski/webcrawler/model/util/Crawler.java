package com.chaikouski.webcrawler.model.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Crawler {
    private static final int MAX_DEEP_LEVEL = 8;
    private static final int MAX_NUM_VISITED_PAGES = 10000;

    public List<Document> crawl(String url) {
        List<Document> documents = new ArrayList<>();

        return scan(1, url, documents);
    }

    private List<Document> scan(int level, String url, List<Document> documents) {
        if (documents.size() < MAX_NUM_VISITED_PAGES && level <= MAX_DEEP_LEVEL) {
            Document document = request(url, documents);

            if (document != null) {
                for (Element link : document.select("a[href]")) {
                    String nextLink = link.absUrl("href");
                    if (documents.stream().noneMatch(d -> d.baseUri().equals(nextLink))) {
                        scan(++level, nextLink, documents);
                    }
                }
            }
        }
        return documents;
    }

    private Document request(String url, List<Document> documents) {
        try {
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            if (connection.response().statusCode() == 200) {
                documents.add(document);
            }

            return document;

        } catch (IOException e) {
            return null;
        }
    }
}