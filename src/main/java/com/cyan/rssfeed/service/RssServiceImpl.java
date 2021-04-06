package com.cyan.rssfeed.service;

import com.cyan.rssfeed.model.AnalysedFeed;
import com.cyan.rssfeed.model.FeedMessage;
import com.cyan.rssfeed.repository.AnalysedFeedRepository;
import com.cyan.rssfeed.utils.RssFeedParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RssServiceImpl implements RssService {

    public static final int MAX_TOP_FEEDS = 3;
    public static final String STOPWORDS_FILENAME = "english_stopwords.txt";

    @Autowired
    private AnalysedFeedRepository repository;

    @Override
    public Long findPotentialHotTopics(final List<String> rssUrls) throws IOException {
        Map<String, List<FeedMessage>> feedMap = new HashMap<>();
        rssUrls.forEach(url -> feedMap.put(url, RssFeedParser.readFeeds(url)));

        // find common words
        Set<String> commonWords = findFeedCommonWords(feedMap);

        // remove stopwords
        reomveStopWords(commonWords);

        // find three high topics
        TreeMap<Integer, FeedMessage> topFeeds = findTopFeedsWithPotentialHotTopics(feedMap, commonWords);
        AnalysedFeed analysedFeed = new AnalysedFeed(String.join(", ", commonWords), new ArrayList<>(topFeeds.values()));
        AnalysedFeed saved = repository.save(analysedFeed);

        return saved.getId();
    }

    private Set<String> findFeedCommonWords(Map<String, List<FeedMessage>> feedMap) {
        List<Set<String>> feedWords = new ArrayList<>();
        Set<String> commonWords = new HashSet<>();

        feedMap.values().forEach(feed -> feedWords.add(getWords(feed)));

        // filter feeds
        for (int i = 0; i < feedWords.size(); i++) {
            for (String word : feedWords.get(i)) {
                int counter = 0;
                for (int j = 0; j < feedWords.size(); j++) {
                    if (i != j) {
                        if (feedWords.get(j).contains(word)) {
                            counter++;
                        }
                    }
                }
                if (counter >= 1) {
                    commonWords.add(word);
                }
            }
        }

        return commonWords;
    }

    /**
     * Retrieve all the words from a feed
     *
     * @return list of words without repetition
     */
    private Set<String> getWords(List<FeedMessage> feedMessages) {
        Set<String> feedWords = new HashSet<>();

        feedMessages.forEach(feedMessage -> feedWords.addAll(Arrays.asList(feedMessage.getTitle().toLowerCase().split(" "))));

        return feedWords;
    }

    @Override
    public Optional<AnalysedFeed> getFeedMatches(final long id) {
        return repository.findById(id);
    }

    private void reomveStopWords(Set<String> commonWords) throws IOException {
        List<String> stopwords = Files.readAllLines(new ClassPathResource(STOPWORDS_FILENAME).getFile().toPath());
        commonWords.removeAll(stopwords);
    }

    private TreeMap<Integer, FeedMessage> findTopFeedsWithPotentialHotTopics(Map<String, List<FeedMessage>> feedMap, Set<String> commonWords) {
        int count;
        TreeMap<Integer, FeedMessage> topFeeds = new TreeMap<>(Collections.reverseOrder());

        for (List<FeedMessage> feedList : feedMap.values()) {
            for (FeedMessage feed : feedList) {
                count = 0;

                for (String word : commonWords) {
                    if (isContain(feed.getTitle().toLowerCase(), word)) {
                        count++;
                    }
                }

                if (topFeeds.size() < MAX_TOP_FEEDS && count > 0) {
                    feed.setFrequency(count);
                    topFeeds.put(count, feed);
                } else if (topFeeds.size() == MAX_TOP_FEEDS && count > 0 && topFeeds.get(count) == null) {
                    final OptionalInt optionalMin = topFeeds.keySet().stream()
                            .mapToInt(Integer::intValue)
                            .min();

                    if (optionalMin.isPresent()) {
                        int min = optionalMin.getAsInt();
                        if (min < count) {
                            topFeeds.remove(min);
                            feed.setFrequency(count);
                            topFeeds.put(count, feed);
                        }
                    }
                }
            }
        }
        System.out.println("Biggest: " + topFeeds);
        return topFeeds;
    }

    public static boolean isContain(String source, String subItem) {
        String pattern = "\\b" + subItem + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find();
    }
}
