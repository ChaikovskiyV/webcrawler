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
    private static final String LINK_SELECTOR = "a[href]";
    private static final String HREF_KEY = "href";
    private static final String HTTP = "http";

    public List<Document> crawl(String url) {
        List<Document> documents = new ArrayList<>();

        return scan(1, url, documents);
    }

    private List<Document> scan(int level, String url, List<Document> documents) {
        if (documents.size() < MAX_NUM_VISITED_PAGES && level <= MAX_DEEP_LEVEL) {
            Document document = request(url, documents);

            if (document != null) {
                for (Element link : document.select(LINK_SELECTOR)) {
                    String nextLink = link.absUrl(HREF_KEY);
                    scan(++level, nextLink, documents);
                }
            }
        }
        return documents;
    }

    private Document request(String url, List<Document> documents) {
        Document document = null;
        if (url.startsWith(HTTP) && documents.stream().noneMatch(doc -> doc.baseUri().equals(url))) {
            try {
                Connection connection = Jsoup.connect(url);
                document = connection.get();

                if (connection.response().statusCode() == 200) {
                    documents.add(document);
                }

            } catch (IOException e) {
                return null;
            }
        }
        return document;
    }
}