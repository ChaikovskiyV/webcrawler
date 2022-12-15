package com.chaikouski.webcrawler.model.repository;

public final class QueriesStorage {
    public static final String GET_ALL_SEEDS_QUERY = "FROM Seed";
    public static final String BY_SEARCH_PARAM = " s JOIN Term t WHERE s.url LIKE ?1 OR t.termName LIKE ?1";


    private QueriesStorage() {
    }
}