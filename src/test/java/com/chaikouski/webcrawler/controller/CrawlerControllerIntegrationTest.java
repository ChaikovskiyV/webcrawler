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

@SpringBootTest(classes = WebCrawlerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
class CrawlerControllerIntegrationTest {
    public static final String LOCAL_HOST = "http://localhost:";
    public static final String URI = "/api/v1/seeds";
    public static final String SEED_PARAM = "seed=";
    public static final String TERMS_PARAM = "terms=";
    public static final String SEARCH_PARAM = "search=";
    public static final String LIMIT_PARAM = "limit=";
    public String seed;
    public String terms;
    private String url;
    private int limit;
    private String search;
    private List<SeedDataDto> result;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

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

    @BeforeEach
    void setUp() {
        result = List.of();
        limit = 3;
    }

    @AfterEach
    void tearDown() {
    }

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

    @Test
    void getSeedDataTestWhenNoParametersProvided() {
        result = restTemplate.getForObject(url, List.class);

        assertFalse(result.isEmpty());
    }

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