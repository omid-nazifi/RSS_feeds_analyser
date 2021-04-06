package com.cyan.rssfeed.service;

import com.cyan.rssfeed.model.AnalysedFeed;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface RssService {

    Long findPotentialHotTopics(List<String> rssUrls) throws IOException;

    Optional<AnalysedFeed> getFeedMatches(long id);
}
