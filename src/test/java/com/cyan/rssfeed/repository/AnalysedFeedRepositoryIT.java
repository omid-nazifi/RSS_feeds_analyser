package com.cyan.rssfeed.repository;

import com.cyan.rssfeed.model.AnalysedFeed;
import com.cyan.rssfeed.model.FeedMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
public class AnalysedFeedRepositoryIT {

    @Autowired
    AnalysedFeedRepository repository;

    private static final long ID = 1;
    private static final String MOST_REPETITION_WORDS = "word1, word2";

    @Test
    public void givenAnalysedData_whenSave_thenOk() {
        FeedMessage feedMessage = new FeedMessage();
        feedMessage.setTitle("title");
        feedMessage.setDescription("des");
        feedMessage.setPublicationDate("20.01.2021");
        feedMessage.setFrequency(5);
        AnalysedFeed analysedFeed = new AnalysedFeed(MOST_REPETITION_WORDS, Collections.singletonList(feedMessage));
        AnalysedFeed saved = repository.save(analysedFeed);

        Optional<AnalysedFeed> retrieved = repository.findById(ID);
        assertTrue(retrieved.isPresent());
        assertEquals(retrieved.get().getId(), saved.getId());
        assertEquals(retrieved.get().getMostRepetitionWords(), MOST_REPETITION_WORDS);
    }
}