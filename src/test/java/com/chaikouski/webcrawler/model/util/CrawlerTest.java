package com.chaikouski.webcrawler.model.util;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CrawlerTest {
    @Autowired
    private Crawler crawler;
    List<Document> documents;

    @BeforeAll
    void setUp() {
        String url = "https://habr.com/";
        documents = crawler.crawl(url);
    }

    @Test
    void crawlTestWhetherReturnNotEmptyResult() {
        assertFalse(documents.isEmpty());
    }

    @Test
    void crawlTestWhetherContainsNullItem() {
        boolean isContainsNull = documents.stream().anyMatch(Objects::isNull);

        assertFalse(isContainsNull);
    }

    @Test
    void crawlTestWhetherReturnEmptyResultIfUrlNotExist() {
        List<Document> documents = crawler.crawl("https://haaaabr.com/");

        assertTrue(documents.isEmpty());
    }
}