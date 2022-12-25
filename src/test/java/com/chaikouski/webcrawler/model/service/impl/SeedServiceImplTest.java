package com.chaikouski.webcrawler.model.service.impl;

import com.chaikouski.webcrawler.model.dto.SeedDataDto;
import com.chaikouski.webcrawler.model.entity.Seed;
import com.chaikouski.webcrawler.model.entity.Term;
import com.chaikouski.webcrawler.model.repository.SeedDao;
import com.chaikouski.webcrawler.model.repository.TermDao;
import com.chaikouski.webcrawler.model.util.Crawler;
import com.chaikouski.webcrawler.model.util.CsvWriter;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Viktar Chaikouski
 * <p>
 * The type SeedServiceImplTest.
 * <p>
 * Includes tests for testing the methods of SeedServiceImpl class
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeedServiceImplTest {
    private static final String FIRST_URL = "firstUrl";
    private static final String SECOND_URL = "secondUrl";
    private static final String THIRD_URL = "thirdUrl";
    private static final String FIRST_TERM = "firstTerm";
    private static final String SECOND_TERM = "secondTerm";
    private static final String THIRD_TERM = "thirdTerm";
    private static final String TERM_REPETITION = " 1 2 3 6";
    private AutoCloseable closeable;
    private List<Document> documents;
    private List<Term> terms;
    private List<Seed> seeds;
    private List<SeedDataDto> seedData;
    private List<SeedDataDto> expectedSeedData;
    private Map<String, Object> requestParams;

    @Spy
    private SeedDao seedDaoMock;
    @Spy
    private TermDao termDaoMock;
    @Spy
    private Crawler crawlerMock;
    @Spy
    private CsvWriter csvWriterMock;

    @InjectMocks
    private SeedServiceImpl seedService;

    /**
     * Init.
     */
    @BeforeAll
    void init() {
        documents = List.of(
                new Document(FIRST_URL),
                new Document(SECOND_URL),
                new Document(THIRD_URL));
        terms = List.of(
                new Term(FIRST_TERM, 1),
                new Term(SECOND_TERM, 2),
                new Term(THIRD_TERM, 3));
        seeds = List.of(
                new Seed(FIRST_URL, terms),
                new Seed(SECOND_URL, terms),
                new Seed(THIRD_URL, terms));
        seedData = List.of(
                new SeedDataDto(FIRST_URL, TERM_REPETITION),
                new SeedDataDto(SECOND_URL, TERM_REPETITION),
                new SeedDataDto(THIRD_URL, TERM_REPETITION));
    }

    /**
     * SetsUp.
     */
    @BeforeEach
    void setUp() {
        requestParams = new HashMap<>();
        expectedSeedData = List.of();
        closeable = MockitoAnnotations.openMocks(this);

        Mockito.doNothing().when(csvWriterMock).writeDataToCsvFile(Mockito.anyList());
        Mockito.doReturn(documents).when(crawlerMock).crawl(Mockito.anyString());
        Mockito.doReturn(terms).when(termDaoMock).getTermsByNameAndRepetition(Mockito.anyString(), Mockito.anyLong());
        Mockito.doReturn(List.of()).when(seedDaoMock).getSeedsByUrl(Mockito.anyString());
        Mockito.doReturn(seeds).when(seedDaoMock).getAllSeeds();
    }

    /**
     * TearDown.
     *
     * @throws Exception the exception
     */
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    /**
     * Tests the addSeedData method when url and terms parameters are provided.
     */
    @Test
    void addSeedDataTestWhenUrlAndTermsParametersProvided() {
        Mockito.when(seedDaoMock.addSeed(Mockito.any(Seed.class))).thenReturn(seeds.get(0), seeds.get(1), seeds.get(2));
        List<SeedDataDto> result = seedService.addSeedData(FIRST_URL, "firstTerm, secondTerm, thirdTerm");

        assertEquals(seedData, result);
    }

    /**
     * Tests the addSeedData method when no parameters are provided.
     */
    @Test
    void addSeedDataTestWhenNoParametersProvided() {
        List<SeedDataDto> result = seedService.addSeedData(null, null);

        assertEquals(expectedSeedData, result);
    }

    /**
     * Tests the addSeedData method when url parameter is not provided.
     */
    @Test
    void addSeedDataTestWhenUrlParameterNotProvided() {
        List<SeedDataDto> result = seedService.addSeedData(null, "terms");

        assertEquals(expectedSeedData, result);
    }

    /**
     * Tests the addSeedData method when terms parameter is not provided.
     */
    @Test
    void addSeedDataTestWhenTermsParameterNotProvided() {
        List<SeedDataDto> result = seedService.addSeedData("any url", null);

        assertEquals(expectedSeedData, result);
    }

    /**
     * Tests the getSeedData method when no request parameters are provided.
     */
    @Test
    void getSeedDataTestWhenNoRequestParametersProvided() {
        List<SeedDataDto> result = seedService.getSeedData(requestParams);

        assertEquals(seedData, result);
    }

    /**
     * Tests the getSeedData method when search and limit parameters are provided.
     */
    @Test
    void getSeedDataTestWhenSearchAndLimitParametersProvided() {
        Mockito.doReturn(List.of(seeds.get(0), seeds.get(1))).when(seedDaoMock)
                .getSeedsBySearchParamWithLimitedResult(Mockito.anyString(), Mockito.anyInt());
        requestParams = Map.of("search", "term", "limit", 2);
        List<SeedDataDto> result = seedService.getSeedData(requestParams);
        expectedSeedData = List.of(seedData.get(0), seedData.get(1));

        assertEquals(expectedSeedData, result);
    }

    /**
     * Tests the getSeedData method when only search parameter is provided.
     */
    @Test
    void getSeedDataTestWhenOnlySearchParameterProvided() {
        Mockito.doReturn(List.of(seeds.get(1))).when(seedDaoMock).getSeedsBySearchParam(Mockito.anyString());
        requestParams = Map.of("search", SECOND_TERM);
        List<SeedDataDto> result = seedService.getSeedData(requestParams);
        expectedSeedData = List.of(seedData.get(1));

        assertEquals(expectedSeedData, result);
    }

    /**
     * Tests the getSeedData method when only limit parameter is provided.
     */
    @Test
    void getSeedDataTestWhenOnlyLimitParameterProvided() {
        Mockito.doReturn(seeds).when(seedDaoMock).getSeedsWithLimitedResult(Mockito.anyInt());
        requestParams = Map.of("limit", 10);
        List<SeedDataDto> result = seedService.getSeedData(requestParams);

        assertEquals(seedData, result);
    }
}