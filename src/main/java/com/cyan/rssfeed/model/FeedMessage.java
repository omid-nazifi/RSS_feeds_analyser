package com.cyan.rssfeed.model;

import javax.persistence.*;

@Entity
public class FeedMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String title;

    @Lob
    @Column(length = 500)
    String description;

    @Column(length = 500)
    String link;

    String guid;

    String publicationDate;

    int frequency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "FeedMessage{" +
                "id='" + id + '\'' +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", guid='" + guid + '\'' +
                ", publicationDate=" + publicationDate +
                ", frequency=" + frequency +
                '}';
    }
}
