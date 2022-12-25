package com.chaikouski.webcrawler.model.dto;

import java.util.Objects;

public class SeedDataDto {
    private final String seedData;

    public SeedDataDto(String url, String termRepetitions) {
        seedData = String.join("", url, termRepetitions);
    }

    public String getSeedData() {
        return seedData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeedDataDto that = (SeedDataDto) o;
        return Objects.equals(seedData, that.seedData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seedData);
    }

    @Override
    public String toString() {
        return new StringBuilder("seedData {")
                .append(seedData)
                .append("'}")
                .toString();
    }
}