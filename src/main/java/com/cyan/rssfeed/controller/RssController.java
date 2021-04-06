package com.cyan.rssfeed.controller;

import com.cyan.rssfeed.model.AnalysedFeed;
import com.cyan.rssfeed.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController()
public class RssController {


    private final RssService rssService;

    @Autowired
    public RssController(RssService rssService) {
        this.rssService = rssService;
    }

    @GetMapping(value = "/")
    public String home() {
        return "Hello to hiring_exercise-software_developer project!";
    }

    @GetMapping(value = "/analyse/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> analyseNewFeeds(@RequestParam List<String> urls) throws IOException {
        if (urls.size() < 2) {
            return new ResponseEntity<>("Minimum two RSS Urls must be sent!", HttpStatus.NOT_ACCEPTABLE);
        }
        Long frequencyId = rssService.findPotentialHotTopics(urls);
        return new ResponseEntity<>("Analysis was successful. You can see the result with: /frequency/" + frequencyId, HttpStatus.OK);
    }

    @GetMapping(value = "/frequency/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> analyseNewFeeds(@PathVariable long id) {
        Optional<AnalysedFeed> optional = rssService.getFeedMatches(id);
        return optional
                .<ResponseEntity<Object>>map(analysedFeed -> new ResponseEntity<>(analysedFeed, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("There is not any Analysed Feed with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<String> handleIOException() {
        return new ResponseEntity<>("An exception occurred while reading a file!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<String> handleDataException() {
        return new ResponseEntity<>("An error occurred while accessing the data!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
