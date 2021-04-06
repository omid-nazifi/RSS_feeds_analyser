package com.cyan.rssfeed.utils;

import com.cyan.rssfeed.model.FeedMessage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RssFeedParserTest {

    public static final String RSS_SOURCE_URL = "https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss";

    @Test
    public void readFeedsTest() {
        List<FeedMessage> feedMessages = RssFeedParser.readFeeds(RSS_SOURCE_URL);
        assertThat(feedMessages.size() > 0, is(true));
        System.out.println(feedMessages.get(0));
    }
}
