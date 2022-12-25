package com.chaikouski.webcrawler.model.repository;

import com.chaikouski.webcrawler.model.entity.Seed;

import java.util.List;

/**
 * @author Viktar Chaikouski
 *
 * The interface SeedDao.
 *
 * Includes methods to work with database.
 */
public interface SeedDao {
    /**
     * Save seed to database.
     *
     * @param seed the seed
     * @return the seed, that was saved
     */
    Seed addSeed(Seed seed);

    /**
     * Receives all seeds those stored in database.
     *
     * @return list of seeds
     */
    List<Seed> getAllSeeds();

    /**
     * Receives all seeds with terms or URL that includes the search parameter.
     *
     * @param search the search
     * @return list of seeds
     */
    List<Seed> getSeedsBySearchParam(String search);

    /**
     * Receives limit first seeds those stored in database.
     *
     * @param limit the limit
     * @return list of seeds
     */
    List<Seed> getSeedsWithLimitedResult(int limit);

    /**
     * Receives limit first seeds with terms or URL that includes the search parameter.
     *
     * @param search the search
     * @param limit  the limit
     * @return list of seeds
     */
    List<Seed> getSeedsBySearchParamWithLimitedResult(String search, int limit);

    /**
     * Receives all seeds with provided url.
     *
     * @param url the url
     * @return list of seeds
     */
    List<Seed> getSeedsByUrl(String url);
}