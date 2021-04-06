package com.cyan.rssfeed.repository;

import com.cyan.rssfeed.model.FeedMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
public class FeedMessageRepositoryIT {

    private static final String TITLE = "title";
    public static final int FREQUENCY = 2;

    @Autowired
    FeedMessageRepository repository;

    @Test
    public void givenAnalysedData_whenSave_thenOk() {
        FeedMessage feedMessage = new FeedMessage();
        feedMessage.setTitle(TITLE);
        feedMessage.setFrequency(FREQUENCY);
        FeedMessage saved = repository.save(feedMessage);

        Optional<FeedMessage> retrieved = repository.findById(saved.getId());
        assertTrue(retrieved.isPresent());
        assertEquals(retrieved.get().getTitle(), TITLE);
        assertEquals(retrieved.get().getFrequency(), FREQUENCY);
    }
}