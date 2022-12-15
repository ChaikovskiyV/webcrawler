package com.chaikouski.webcrawler.model.service.impl;

import com.chaikouski.webcrawler.model.entity.Seed;
import com.chaikouski.webcrawler.model.repository.SeedDao;
import com.chaikouski.webcrawler.model.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.chaikouski.webcrawler.model.service.RequestParams.LIMIT;
import static com.chaikouski.webcrawler.model.service.RequestParams.SEARCH;

@Service
public class SeedServiceImpl implements SeedService {
    private final SeedDao seedDao;

    @Autowired
    public SeedServiceImpl(SeedDao seedDao) {
        this.seedDao = seedDao;
    }

    @Override
    public List<Seed> addSeedData(String url, String terms) {
        List<Seed> seeds = new ArrayList<>();

        getSeedList(url, terms).forEach(seed -> seeds.add(seedDao.addSeed(seed)));

        return seeds;
    }

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
        return List.of();
    }
}