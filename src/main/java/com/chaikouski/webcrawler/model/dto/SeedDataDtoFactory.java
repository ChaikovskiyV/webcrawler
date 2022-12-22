package com.chaikouski.webcrawler.model.dto;

import com.chaikouski.webcrawler.model.entity.Seed;
import com.chaikouski.webcrawler.model.entity.Term;

import java.util.List;

public class SeedDataDtoFactory {
    private SeedDataDtoFactory() {
    }

    public static SeedDataDto createSeedDataDto(Seed seed) {
        return new SeedDataDto(seed.getUrl(),
                buildTermRepetitionString(seed.getTerms()) + " " + getTotalRepetition(seed.getTerms()));
    }

    private static String buildTermRepetitionString(List<Term> terms) {
        return terms.stream()
                .map(term -> String.valueOf(term.getRepetitionsNumber()))
                .reduce("", (s1, s2) -> String.join(" ", s1, s2));

    }

    private static long getTotalRepetition(List<Term> terms) {
        return terms.stream()
                .map(Term::getRepetitionsNumber)
                .reduce(0L, Long::sum);
    }
}