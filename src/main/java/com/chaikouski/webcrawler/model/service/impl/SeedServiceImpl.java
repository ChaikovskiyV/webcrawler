package com.chaikouski.webcrawler.model.service.impl;

import com.chaikouski.webcrawler.model.dto.SeedDataDto;
import com.chaikouski.webcrawler.model.dto.SeedDataDtoFactory;
import com.chaikouski.webcrawler.model.entity.Seed;
import com.chaikouski.webcrawler.model.entity.Term;
import com.chaikouski.webcrawler.model.repository.SeedDao;
import com.chaikouski.webcrawler.model.repository.TermDao;
import com.chaikouski.webcrawler.model.service.SeedService;
import com.chaikouski.webcrawler.model.util.Crawler;
import com.chaikouski.webcrawler.model.util.CsvWriter;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.chaikouski.webcrawler.model.service.RequestParams.LIMIT;
import static com.chaikouski.webcrawler.model.service.RequestParams.SEARCH;

/**
 * @author Viktar Chaikouski
 * <p>
 * The type SeedServiceImpl.
 * <p>
 * Implements the SeedService interface
 */
@Service
public class SeedServiceImpl implements SeedService {
    private final SeedDao seedDao;
    private final TermDao termDao;
    private final Crawler crawler;
    private final CsvWriter csvWriter;

    /**
     * Instantiates a new Seed service.
     *
     * @param seedDao   the seedDao
     * @param termDao   the termDao
     * @param crawler   the crawler
     * @param csvWriter the csvWriter
     */
    @Autowired
    public SeedServiceImpl(SeedDao seedDao, TermDao termDao, Crawler crawler, CsvWriter csvWriter) {
        this.seedDao = seedDao;
        this.termDao = termDao;
        this.crawler = crawler;
        this.csvWriter = csvWriter;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<SeedDataDto> addSeedData(String url, String terms) {
        List<Seed> seeds = new ArrayList<>();

        if (url != null && !url.isBlank() && terms != null && !terms.isBlank()) {
            getSeedList(url, terms).forEach(seed -> {
                Seed existed = findExistedSeed(seed);

                if (existed != null) {
                    seeds.add(existed);
                } else {
                    seeds.add(seedDao.addSeed(seed));
                }
            });
        }

        List<SeedDataDto> seedData = convertToSeedDataDtoList(seeds);
        writeDataToFile(seedData);

        return seedData;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<SeedDataDto> getSeedData(Map<String, Object> requestParams) {
        List<Seed> seeds;

        String search = (String) requestParams.get(SEARCH);
        Integer limit = (Integer) requestParams.get(LIMIT);

        if (search != null && limit != null) {
            seeds = seedDao.getSeedsBySearchParamWithLimitedResult(search, limit);
        } else if (search != null) {
            seeds = seedDao.getSeedsBySearchParam(search);
        } else if (limit != null) {
            seeds = seedDao.getSeedsWithLimitedResult(limit);
        } else {
            seeds = seedDao.getAllSeeds();
        }

        return convertToSeedDataDtoList(seeds);
    }

    /*Gains list of documents from crawler,
    extracts required data to create a seed object and return list of Seed objects*/
    private List<Seed> getSeedList(String url, String terms) {
        List<Seed> seeds = new ArrayList<>();
        String delimiter = ", ";

        String[] termArr = terms.toLowerCase().split(delimiter);
        List<Document> documents = crawler.crawl(url);

        documents.forEach(doc -> {
            String text = doc.text().toLowerCase();
            List<Term> termList = new ArrayList<>();

            for (String term : termArr) {
                Pattern pattern = Pattern.compile(term);
                Matcher matcher = pattern.matcher(text);
                long termRepetition = matcher.results().count();

                termList.add(getTerm(term, termRepetition));
            }

            seeds.add(createSeed(doc.baseUri(), termList));
        });

        return seeds;
    }

    /*Calls the method of the repository layer to receive terms those suit provided parameters.*/
    private Term getTerm(String term, long repetitions) {
        List<Term> existed = termDao.getTermsByNameAndRepetition(term, repetitions);

        return existed.isEmpty() ? createTerm(term, repetitions) : existed.get(0);
    }

    private Seed createSeed(String url, List<Term> terms) {
        return new Seed(url, terms);
    }

    private Term createTerm(String term, long repetitions) {
        return new Term(term, repetitions);
    }

    /*Calls the method of the repository layer to find the same seed in database and returns it.
     * Returns null if nothing was found*/
    private Seed findExistedSeed(Seed seed) {
        List<Seed> seeds = seedDao.getSeedsByUrl(seed.getUrl());

        return seeds.stream()
                .filter(s -> s.getTerms().equals(seed.getTerms()))
                .findAny()
                .orElse(null);
    }

    /*Converts list of Seed objects to the list of SeedDataDto objects*/
    private List<SeedDataDto> convertToSeedDataDtoList(List<Seed> seeds) {
        return seeds.stream()
                .map(SeedDataDtoFactory::createSeedDataDto)
                .collect(Collectors.toList());
    }

    /*Prepare data to save in file and calls the method of cswWriter*/
    private void writeDataToFile(List<SeedDataDto> seedDataDtos) {
        List<String> seedData = seedDataDtos.stream()
                .map(SeedDataDto::getSeedData)
                .collect(Collectors.toList());

        csvWriter.writeDataToCsvFile(seedData);
    }
}