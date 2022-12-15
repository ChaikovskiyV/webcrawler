package com.chaikouski.webcrawler.model.repository.impl;

import com.chaikouski.webcrawler.model.entity.Seed;
import com.chaikouski.webcrawler.model.repository.SeedDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.chaikouski.webcrawler.model.repository.QueriesStorage.*;

@Repository
public class SeedDaoImpl implements SeedDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Seed addSeed(Seed seed) {
        return entityManager.merge(seed);
    }

    @Override
    public List<Seed> getAllSeeds() {
        return entityManager.createQuery(GET_ALL_SEEDS_QUERY, Seed.class).getResultList();
    }

    @Override
    public List<Seed> getSeedsBySearchParam(String search) {
        return entityManager.createQuery(GET_ALL_SEEDS_QUERY + BY_SEARCH_PARAM, Seed.class)
                .setParameter(1, buildLikeParam(search))
                .getResultList();
    }

    @Override
    public List<Seed> getSeedsWithLimitedResult(int limit) {
        return entityManager.createQuery(GET_ALL_SEEDS_QUERY, Seed.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Seed> getSeedsBySearchParamWithLimitedResult(String search, int limit) {
        return entityManager.createQuery(GET_ALL_SEEDS_QUERY + BY_SEARCH_PARAM, Seed.class)
                .setParameter(1, buildLikeParam(search))
                .setMaxResults(limit)
                .getResultList();
    }

    private String buildLikeParam(String param) {
        String delimiter = "%";
        return new StringBuilder(delimiter)
                .append(param)
                .append(delimiter)
                .toString();
    }
}