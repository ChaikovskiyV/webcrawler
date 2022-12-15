package com.chaikouski.webcrawler.model.service;

import com.chaikouski.webcrawler.model.entity.Seed;

import java.util.List;
import java.util.Map;

public interface SeedService {
    List<Seed> addSeedData(String url, String terms);

    List<Seed> getSeedData(Map<String, Object> requestParams);
}