package com.chaikouski.webcrawler.controller;

import com.chaikouski.webcrawler.WebCrawlerApplication;
import com.chaikouski.webcrawler.config.TestConfig;
import com.chaikouski.webcrawler.model.dto.SeedDataDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Viktar Chaikouski
 *
 * The type CrawlerControllerIntegrationTest.
 *
 * Includes integration tests for testing the WebCrawlerApplication.
 */
@SpringBootTest(classes = WebCrawlerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
class CrawlerControllerIntegrationTest {
    /**
     * The constant LOCAL_HOST.
     */
    public static final String LOCAL_HOST = "http://localhost:";
    /**
     * The constant URI.
     */
    public static final String URI = "/api/v1/seeds";
    /**
     * The constant SEED_PARAM.
     */
    public static final String SEED_PARAM = "seed=";
    /**
     * The constant TERMS_PARAM.
     */
    public static final String TERMS_PARAM = "terms=";
    /**
     * The constant SEARCH_PARAM.
     */
    public static final String SEARCH_PARAM = "search=";
    /**
     * The constant LIMIT_PARAM.
     */
    public static final String LIMIT_PARAM = "limit=";
    /**
     * The Seed.
     */
    public String seed;
    /**
     * The Terms.
     */
    public String terms;
    private String url;
    private int limit;
    private String search;
    private List<SeedDataDto> result;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Init.
     */
    @BeforeAll
    void init() {
        url = new StringBuilder().append(LOCAL_HOST)
                .append(port)
                .append(URI)
                .toString();
        seed = "https://habr.com/";
        terms = "junior, trainee, middle";
        search = "firstTerm";
    }

    /**
     * SetsUp.
     */
    @BeforeEach
    void setUp() {
        result = List.of();
        limit = 3;
    }

    /**
     * TearDown.
     */
    @AfterEach
    void tearDown() {
    }

    /**
     * Tests the addSeedData method when no parameters are provided..
     */
    @Test
    void addSeedDataTestWhenNoParametersProvided() {
        String parameterizedUrl = new StringBuilder(url)
                .append("?")
                .append(SEED_PARAM)
                .append("&")
                .append(TERMS_PARAM)
                .toString();

        result = restTemplate.postForObject(parameterizedUrl, null, List.class);


        assertTrue(result.isEmpty());
    }

    /**
     * Tests the addSeedData method when seed parameter is not provided.
     */
    @Test
    void addSeedDataTestWhenSeedParameterNotProvided() {
        String parameterizedUrl = new StringBuilder(url)
                .append("?")
                .append(SEED_PARAM)
                .append("&")
                .append(TERMS_PARAM)
                .append(terms)
                .toString();

        result = restTemplate.postForObject(parameterizedUrl, null, List.class);

        assertTrue(result.isEmpty());
    }

    /**
     * Tests the addSeedData method when terms parameter is not provided.
     */
    @Test
    void addSeedDataTestWhenTermsParameterNotProvided() {
        String parameterizedUrl = new StringBuilder(url)
                .append("?")
                .append(TERMS_PARAM)
                .append("&")
                .append(SEED_PARAM)
                .append(seed)
                .toString();

        result = restTemplate.postForObject(parameterizedUrl, null, List.class);

        assertTrue(result.isEmpty());
    }

    /**
     * Tests the addSeedData method when seed and terms are provided.
     */
    @Test
    void addSeedDataTestWhenSeedAndTermsProvided() {
        String parameterizedUrl = new StringBuilder(url)
                .append("?")
                .append(SEED_PARAM)
                .append(seed)
                .append("&")
                .append(TERMS_PARAM)
                .append(terms)
                .toString();

        result = restTemplate.postForObject(parameterizedUrl, null, List.class);

        assertFalse(result.isEmpty());
    }

    /**
     * Tests the getSeedData method when no parameters are provided.
     */
    @Test
    void getSeedDataTestWhenNoParametersProvided() {
        result = restTemplate.getForObject(url, List.class);

        assertFalse(result.isEmpty());
    }

    /**
     * Tests the getSeedData method when search parameter is provided.
     */
    @Test
    void getSeedDataTestWhenSearchParameterProvided() {
        String parameterizedUrl = new StringBuilder(url)
                .append("?")
                .append(SEARCH_PARAM)
                .append(search)
                .toString();
        result = restTemplate.getForObject(parameterizedUrl, List.class);

        assertFalse(result.isEmpty());
    }

    /**
     * Tests the getSeedData method when limit parameter is provided.
     */
    @Test
    void getSeedDataTestWhenLimitParameterProvided() {
        String parameterizedUrl = new StringBuilder(url)
                .append("?")
                .append(LIMIT_PARAM)
                .append(limit)
                .toString();
        result = restTemplate.getForObject(parameterizedUrl, List.class);

        assertEquals(limit, result.size());
    }

    /**
     * Tests the getSeedData method when search and limit parameters are provided.
     */
    @Test
    void getSeedDataTestWhenSearchAndLimitParametersProvided() {
        limit = 4;
        String parameterizedUrl = new StringBuilder(url)
                .append("?")
                .append(SEARCH_PARAM)
                .append(search)
                .append("&")
                .append(LIMIT_PARAM)
                .append(limit)
                .toString();
        result = restTemplate.getForObject(parameterizedUrl, List.class);

        System.out.println(result);

        assertEquals(limit, result.size());
    }
}