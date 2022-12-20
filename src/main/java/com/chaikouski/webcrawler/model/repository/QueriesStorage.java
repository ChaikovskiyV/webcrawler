package com.chaikouski.webcrawler.model.repository;

public final class QueriesStorage {
    public static final String GET_ALL_SEEDS_QUERY = "FROM Seed";
    public static final String BY_SEARCH_PARAM = " s JOIN FETCH s.terms t WHERE CONCAT_WS(' ', s.url, t.termName) LIKE ?1";
    public static final String BY_URL = " s WHERE s.url=?1";
    public static final String GET_TERM_BY_NAME_AND_REPETITION = "FROM Term t WHERE t.termName=?1 AND t.repetitionsNumber=?2";

    private QueriesStorage() {
    }
}