package com.cyan.rssfeed.service;

import com.cyan.rssfeed.model.AnalysedFeed;
import com.cyan.rssfeed.repository.AnalysedFeedRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RssServiceTest {

    private static final List<String> URLS = Arrays.asList(
            "https://news.google.com/rss?cf=all&hl=en-US&pz=1&gl=US&ceid=US:en",
            "https://rss.nytimes.com/services/xml/rss/nyt/World.xml",
            "http://feeds.bbci.co.uk/news/world/rss.xml");
    private static final long ID = 1;

    @InjectMocks
    RssServiceImpl service;

    @Mock
    AnalysedFeedRepository feedRepository;

    @Test
    public void findPotentialHotTopicsTest() throws IOException {
        when(feedRepository.save(any(AnalysedFeed.class))).thenReturn(new AnalysedFeed("param1", Collections.emptyList(), ID));
        Long frequencyId = service.findPotentialHotTopics(URLS);
        assertNotNull(frequencyId);
        assertThat(frequencyId, is(ID));
    }

    @Test
    public void getFeedMatchesTest() {
        when(feedRepository.findById(any(Long.class))).thenReturn(Optional.of(new AnalysedFeed("param1", Collections.emptyList(), ID)));
        Optional<AnalysedFeed> optional = service.getFeedMatches(ID);
        assertTrue(optional.isPresent());
        assertNotNull(optional.get());
    }
}