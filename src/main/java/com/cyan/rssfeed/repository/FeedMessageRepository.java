package com.cyan.rssfeed.repository;

import com.cyan.rssfeed.model.FeedMessage;
import org.springframework.data.repository.CrudRepository;

public interface FeedMessageRepository extends CrudRepository<FeedMessage, Long> {

}