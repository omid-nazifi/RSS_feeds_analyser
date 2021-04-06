package com.cyan.rssfeed.controller;

import com.cyan.rssfeed.service.RssService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RssController.class)
class RssControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RssService service;

    @Test
    void GIVEN_more_than_two_urls_THEN_return_OK() throws Exception {
        String[] strings = {
                "https://news.google.com/rss?cf=all&hl=en-US&pz=1&gl=US&ceid=US:en",
                "https://rss.nytimes.com/services/xml/rss/nyt/World.xml",
                "http://feeds.bbci.co.uk/news/world/rss.xml"
        };
        mockMvc.perform(get("/analyse/new")
                .contentType("application/json")
                .param("urls", strings))
                .andExpect(status().isOk());
    }

    @Test
    void GIVEN_one_urls_THEN_return_NotAcceptable() throws Exception {
        mockMvc.perform(get("/analyse/new")
                .contentType("application/json")
                .param("urls", "https://news.google.com/rss?cf=all&hl=en-US&pz=1&gl=US&ceid=US:en"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    void retrieveResults() throws Exception {
        mockMvc.perform(get("/frequency/1")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}