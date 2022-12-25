package com.chaikouski.webcrawler.controller;

import com.chaikouski.webcrawler.model.dto.SeedDataDto;
import com.chaikouski.webcrawler.model.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chaikouski.webcrawler.model.service.RequestParams.LIMIT;
import static com.chaikouski.webcrawler.model.service.RequestParams.SEARCH;

/**
 * @author Viktar Chaikouski
 * <p>
 * The type CrawlerController.
 * <p>
 * This class intercepts http requests to the api
 */
@RestController
@RequestMapping("api/v1/seeds")
public class CrawlerController {
    @Autowired
    private SeedService seedService;

    /**
     * Explores web resource with provided URL.
     *
     * @param seed  the URL of a web resource
     * @param terms the terms, those it needs to explore
     * @return the list of data with URLs and information about terms presence, that was added
     */
    @PostMapping
    public List<SeedDataDto> addSeedData(@RequestParam(value = "seed") String seed,
                                         @RequestParam(value = "terms") String terms) {

        return seedService.addSeedData(seed, terms);
    }

    /**
     * Provides data about web resources stored in database according to provided parameters.
     *
     * @param search part of URL or term
     * @param limit  the number of first results those will be provided
     * @return the list of data stored in database, according to the provided parameters.
     */
    @GetMapping
    public List<SeedDataDto> getSeedData(@RequestParam(value = "search", required = false) String search,
                                         @RequestParam(value = "limit", required = false) Integer limit) {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put(SEARCH, search);
        requestParams.put(LIMIT, limit);

        return seedService.getSeedData(requestParams);
    }
}