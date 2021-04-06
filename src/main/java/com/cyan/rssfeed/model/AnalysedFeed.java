package com.cyan.rssfeed.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class AnalysedFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(length = 500)
    private String mostRepetitionWords;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column()
    private List<FeedMessage> topFeeds;

    public AnalysedFeed() {

    }

    public AnalysedFeed(String mostRepetitionWords, List<FeedMessage> topFeeds, Long id) {
        this.mostRepetitionWords = mostRepetitionWords;
        this.topFeeds = topFeeds;
        this.id = id;
    }

    public AnalysedFeed(String mostRepetitionWords, List<FeedMessage> topFeeds) {
        this.mostRepetitionWords = mostRepetitionWords;
        this.topFeeds = topFeeds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMostRepetitionWords() {
        return mostRepetitionWords;
    }

    public void setMostRepetitionWords(String hotTopics) {
        this.mostRepetitionWords = hotTopics;
    }

    public List<FeedMessage> getTopFeeds() {
        return topFeeds;
    }

    public void setTopFeeds(List<FeedMessage> topFeeds) {
        this.topFeeds = topFeeds;
    }

    @Override
    public String toString() {
        return "AnalysedFeed{" +
                "id=" + id +
                ", mostRepetitionWords='" + mostRepetitionWords + '\'' +
                ", topFeeds='" + topFeeds + '\'' +
                '}';
    }
}
