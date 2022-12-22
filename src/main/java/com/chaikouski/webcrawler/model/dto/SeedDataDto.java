package com.chaikouski.webcrawler.model.dto;

public class SeedDataDto {
    private final String seedData;

    public SeedDataDto(String url, String termRepetitions) {
        seedData = String.join(" ", url, termRepetitions);
    }

    public String getSeedData() {
        return seedData;
    }

    @Override
    public String toString() {
        return new StringBuilder("seedData {")
                .append(seedData)
                .append("'}")
                .toString();
    }
}