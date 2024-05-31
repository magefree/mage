package org.mage.plugins.card.dl.sources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class CardImageUrls {

    private final List<String> urls = new ArrayList<>();

    public CardImageUrls(String baseUrl) {
        addUrl(baseUrl);
    }

    public CardImageUrls(String... urls) {
        for (String url : urls) {
            addUrl(url);
        }
    }

    public CardImageUrls(Collection<String> urls) {
        for (String url : urls) {
            addUrl(url);
        }
    }

    public List<String> getDownloadList() {
        return urls.stream().distinct().collect(Collectors.toList());
    }

    // for tests
    public String getBaseUrl() {
        return urls.stream().findFirst().orElse(null);
    }

    public void addUrl(String url) {
        if (url != null && !url.isEmpty()) {
            this.urls.add(url);
        } else {
            throw new IllegalArgumentException("Null or empty URL");
        }
    }
}
