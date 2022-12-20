package com.chaikouski.webcrawler.model.repository;

import com.chaikouski.webcrawler.model.entity.Seed;

import java.util.List;

public interface SeedDao {
    Seed addSeed(Seed seed);

    List<Seed> getAllSeeds();

    List<Seed> getSeedsBySearchParam(String search);

    List<Seed> getSeedsWithLimitedResult(int limit);

    List<Seed> getSeedsBySearchParamWithLimitedResult(String search, int limit);

    List<Seed> getSeedsByUrl(String url);
}