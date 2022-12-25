package com.chaikouski.webcrawler.model.service;

import com.chaikouski.webcrawler.model.dto.SeedDataDto;

import java.util.List;
import java.util.Map;

/**
 * @author Viktar Chaikouski
 * <p>
 * The interface SeedService.
 * <p>
 * Includes methods for processing received data from controller layer,
 * coloborating with repository layer and converting data to a response.
 */
public interface SeedService {
    /**
     * Receives data about web resource with provided url,
     * preparers it to send to the repository layer for saving to database,
     * converts gained data to a special format for a response.
     *
     * @param url   the url
     * @param terms the terms
     * @return the list of SeedDataDto
     */
    List<SeedDataDto> addSeedData(String url, String terms);

    /**
     * Processes provided request parameters,
     * receives data using the repository layer according to gained parameters and
     * converts gained data to a special format for a response.
     *
     * @param requestParams the request params from the controller layer
     * @return the list of SeedDataDto
     */
    List<SeedDataDto> getSeedData(Map<String, Object> requestParams);
}