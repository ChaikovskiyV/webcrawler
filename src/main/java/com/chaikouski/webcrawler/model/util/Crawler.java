package com.chaikouski.webcrawler.model.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Viktar Chaikouski
 * <p>
 * The type Crawler.
 * <p>
 * This class is destined to crawl web resources and gather documents.
 */
@Component
public class Crawler {
    private static final int MAX_DEEP_LEVEL = 8;
    private static final int MAX_NUM_VISITED_PAGES = 10000;
    private static final String LINK_SELECTOR = "a[href]";
    private static final String HREF_KEY = "href";
    private static final String HTTP = "http";

    /**
     * Crawls the web resource with provided url and gathers documents.
     *
     * @param url the url of web resource
     * @return the list of Document
     */
    public List<Document> crawl(String url) {
        List<Document> documents = new ArrayList<>();

        return scan(1, url, documents);
    }

    /*Scans document to find urls of other web resources*/
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

    /*Returns Document of the web resource with provided url and null
    if the response from this resource differ from 200 or such a document is already exist in the document list.*/
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