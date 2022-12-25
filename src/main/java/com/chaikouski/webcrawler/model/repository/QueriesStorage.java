package com.chaikouski.webcrawler.model.repository;

/**
 * @author Viktar Chaikouski
 *
 * The type QueriesStorage.
 *
 * This class includes constants with queries to database
 */
public final class QueriesStorage {
    /**
     * The constant GET_ALL_SEEDS_QUERY.
     */
    public static final String GET_ALL_SEEDS_QUERY = "FROM Seed";
    /**
     * The constant BY_SEARCH_PARAM.
     */
    public static final String BY_SEARCH_PARAM = " s JOIN FETCH s.terms t WHERE CONCAT_WS(' ', s.url, t.termName) LIKE ?1";
    /**
     * The constant BY_URL.
     */
    public static final String BY_URL = " s WHERE s.url=?1";
    /**
     * The constant GET_TERM_BY_NAME_AND_REPETITION.
     */
    public static final String GET_TERM_BY_NAME_AND_REPETITION = "FROM Term t WHERE t.termName=?1 AND t.repetitionsNumber=?2";

    private QueriesStorage() {
    }
}