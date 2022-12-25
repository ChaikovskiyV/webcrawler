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

/**
 * @author Viktar Chaikouski
 * <p>
 * The type CrawlerTest.
 * <p>
 * Includes tests for testing the methods of Crawler class.
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CrawlerTest {
    @Autowired
    private Crawler crawler;
    /**
     * The Documents.
     */
    List<Document> documents;

    /**
     * SetsUp.
     */
    @BeforeAll
    void setUp() {
        String url = "https://habr.com/";
        documents = crawler.crawl(url);
    }

    /**
     * Tests the crawl method to check whether it returns not empty result.
     */
    @Test
    void crawlTestWhetherReturnNotEmptyResult() {
        assertFalse(documents.isEmpty());
    }

    /**
     * Tests the crawl method to check whether returned result contains null item.
     */
    @Test
    void crawlTestWhetherContainsNullItem() {
        boolean isContainsNull = documents.stream().anyMatch(Objects::isNull);

        assertFalse(isContainsNull);
    }

    /**
     * Tests the crawl method to check whether it returns empty result if provided url is not correct.
     */
    @Test
    void crawlTestWhetherReturnEmptyResultIfUrlNotExist() {
        List<Document> documents = crawler.crawl("https://haaaabr.com/");

        assertTrue(documents.isEmpty());
    }
}