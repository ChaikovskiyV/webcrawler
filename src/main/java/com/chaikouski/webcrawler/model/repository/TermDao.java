package com.chaikouski.webcrawler.model.repository;

import com.chaikouski.webcrawler.model.entity.Term;

import java.util.List;

/**
 * @author Viktar Chaikouski
 *
 * The interface TermDao.
 *
 * Includes methods to work with database.
 */
public interface TermDao {
    /**
     * Receives all terms with provided term and repetition.
     *
     * @param term       the term
     * @param repetition the number of repetitions
     * @return list of terms
     */
    List<Term> getTermsByNameAndRepetition(String term, long repetition);
}