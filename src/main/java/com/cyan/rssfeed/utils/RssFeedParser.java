package com.cyan.rssfeed.utils;

import com.cyan.rssfeed.model.FeedMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;


public class RssFeedParser {

    private static final String ITEM = "item";
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String GUID = "guid";
    private static final String PUBLICATION_DATE = "pubDate";
    private static final String DESCRIPTION = "description";

    public static List<FeedMessage> readFeeds(final String url) {
        List<FeedMessage> feedMessages = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(url);

            NodeList items = doc.getElementsByTagName(ITEM);

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                FeedMessage feed = new FeedMessage();
                feed.setTitle(getValue(item, TITLE));
                feed.setLink(getValue(item, LINK));
                feed.setGuid(getValue(item, GUID));
                feed.setPublicationDate(getValue(item, PUBLICATION_DATE));
                feed.setDescription(getValue(item, DESCRIPTION));
                feedMessages.add(feed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedMessages;
    }

    private static String getValue(Element parent, String nodeName) {
        Node node = parent.getElementsByTagName(nodeName).item(0);
        if (node.hasChildNodes()) {
            return node.getFirstChild().getNodeValue();
        }
        return "";
    }
}
