package com.chaikouski.webcrawler.model.repository.impl;

import com.chaikouski.webcrawler.model.entity.Term;
import com.chaikouski.webcrawler.model.repository.QueriesStorage;
import com.chaikouski.webcrawler.model.repository.TermDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TermDaoImpl implements TermDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Term> getTermsByNameAndRepetition(String term, long repetition) {
        return entityManager.createQuery(QueriesStorage.GET_TERM_BY_NAME_AND_REPETITION, Term.class)
                .setParameter(1, term)
                .setParameter(2, repetition)
                .getResultList();
    }
}