package com.chaikouski.webcrawler.model.service;

import com.chaikouski.webcrawler.model.dto.SeedDataDto;

import java.util.List;
import java.util.Map;

public interface SeedService {
    List<SeedDataDto> addSeedData(String url, String terms);

    List<SeedDataDto> getSeedData(Map<String, Object> requestParams);
}