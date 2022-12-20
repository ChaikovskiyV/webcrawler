package com.chaikouski.webcrawler.model.service.impl;

import com.chaikouski.webcrawler.model.entity.Seed;
import com.chaikouski.webcrawler.model.entity.Term;
import com.chaikouski.webcrawler.model.repository.SeedDao;
import com.chaikouski.webcrawler.model.repository.TermDao;
import com.chaikouski.webcrawler.model.service.SeedService;
import com.chaikouski.webcrawler.model.util.Crawler;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.chaikouski.webcrawler.model.service.RequestParams.LIMIT;
import static com.chaikouski.webcrawler.model.service.RequestParams.SEARCH;

@Service
public class SeedServiceImpl implements SeedService {
    private final SeedDao seedDao;
    private final TermDao termDao;
    private final Crawler crawler;

    @Autowired
    public SeedServiceImpl(SeedDao seedDao, TermDao termDao, Crawler crawler) {
        this.seedDao = seedDao;
        this.termDao = termDao;
        this.crawler = crawler;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Seed> addSeedData(String url, String terms) {
        List<Seed> seeds = new ArrayList<>();

        if (url != null && terms != null) {
            getSeedList(url, terms).forEach(seed -> {
                Seed existed = findExistedSeed(seed);

                if (existed != null) {
                    seeds.add(existed);
                } else {
                    seeds.add(seedDao.addSeed(seed));
                }
            });
        }

        return seeds;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Seed> getSeedData(Map<String, Object> requestParams) {
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

        return seeds;
    }

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

    private Term getTerm(String term, long repetitions) {
        List<Term> existed = termDao.getTermsByNameAndRepetition(term, repetitions);

        return existed != null ? existed.get(0) : createTerm(term, repetitions);
    }

    private Seed createSeed(String url, List<Term> terms) {
        return new Seed(url, terms);
    }

    private Term createTerm(String term, long repetitions) {
        return new Term(term, repetitions);
    }

    private Seed findExistedSeed(Seed seed) {
        List<Seed> seeds = seedDao.getSeedsByUrl(seed.getUrl());

        return seeds.stream()
                .filter(s -> s.getTerms().equals(seed.getTerms()))
                .findAny()
                .orElse(null);
    }
}