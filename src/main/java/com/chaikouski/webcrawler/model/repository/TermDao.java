package com.chaikouski.webcrawler.model.repository;

import com.chaikouski.webcrawler.model.entity.Term;

import java.util.List;

public interface TermDao {
    List<Term> getTermsByNameAndRepetition(String term, long repetition);
}