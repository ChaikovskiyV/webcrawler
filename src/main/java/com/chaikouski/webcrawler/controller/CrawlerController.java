package com.chaikouski.webcrawler.controller;

import com.chaikouski.webcrawler.model.entity.Seed;
import com.chaikouski.webcrawler.model.service.RequestParams;
import com.chaikouski.webcrawler.model.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chaikouski.webcrawler.model.service.RequestParams.*;

@RestController
@RequestMapping("api/v1/seeds")
public class CrawlerController {
    @Autowired
    private SeedService seedService;

    @PostMapping
    public List<Seed> addSeedAnalyticData(@RequestParam(value = "seed") String seed,
                                          @RequestParam(value = "terms") String terms) {

        return null;

    }

    @GetMapping
    public List<Seed> getSeedAnalyticData(@RequestParam(value = "search", required = false) String search,
                                          @RequestParam(value = "limit", required = false) Integer limit) {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put(SEARCH, search);
        requestParams.put(LIMIT, limit);

        return seedService.getSeedData(requestParams);
    }
}