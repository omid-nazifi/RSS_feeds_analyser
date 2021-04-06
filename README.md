# Exercise

Implement a hot topic analysis for RSS feeds.

## Specification
Application has two HTTP endpoints:

### API Definition: 

```
/analyse/new?urls={url1, url2, ...}
```

### API Input:

This API endpoint should take at least two RSS URLs as a parameter

### API Response:

For each request executed against the API endpoint you should return an unique identifier, which will be the input for the second API endpoint.

### Some RSS Feeds URLs:
1. https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss
2. https://rss.nytimes.com/services/xml/rss/nyt/World.xml
3. http://feeds.bbci.co.uk/news/world/rss.xml

### API Definition: 

```
/frequency/{id}
```

### API Input:

This API endpoint takes an id as input

### API Output:

Returns the three elements with the most matches, additinally the orignal news header and the link to the whole news text should be displayed.

### Workflow:

When this API is being called, you will read the analysis data stored in the database by using the given id parameter
Return the top three results as a json object ordered by their frequency of occurrence

## Additional Information
You can Run the project with maven commands:
1. rebuild:
```mvn clean install```

2. run:
```mvn spring-boot:run```
