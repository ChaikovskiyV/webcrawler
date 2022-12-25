package com.chaikouski.webcrawler.model.dto;

import com.chaikouski.webcrawler.model.entity.Seed;
import com.chaikouski.webcrawler.model.entity.Term;

import java.util.List;

/**
 * @author Viktar Chaikouski
 * <p>
 * The type SeedDataDtoFactory.
 * <p>
 * This class creates a SeedDataDto object from a provided Seed object
 */
public class SeedDataDtoFactory {
    private SeedDataDtoFactory() {
    }

    /**
     * Creates SeedDataDto from Seed.
     *
     * @param seed the seed
     * @return the seedDataDto
     */
    public static SeedDataDto createSeedDataDto(Seed seed) {
        return new SeedDataDto(seed.getUrl(),
                buildTermRepetitionString(seed.getTerms()) + " " + getTotalRepetition(seed.getTerms()));
    }

    /*Convert list of terms to String with their number of repetitions.*/
    private static String buildTermRepetitionString(List<Term> terms) {
        return terms.stream()
                .map(term -> String.valueOf(term.getRepetitionsNumber()))
                .reduce("", (s1, s2) -> String.join(" ", s1, s2));
    }

    /*Return a total number of repetitions of all terms in the list*/
    private static long getTotalRepetition(List<Term> terms) {
        return terms.stream()
                .map(Term::getRepetitionsNumber)
                .reduce(0L, Long::sum);
    }
}