package org.mage.plugins.card.dl.sources;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author JayDi85
 */
public class CardImageUrls {

    public String baseUrl;
    public List<String> alternativeUrls;

    public CardImageUrls() {
        this.baseUrl = null;
        this.alternativeUrls = new ArrayList<>();
    }

    public CardImageUrls(String baseUrl) {
        this(baseUrl, null);
    }

    public CardImageUrls(String baseUrl, String alternativeUrl) {
        this();

        this.baseUrl = baseUrl;

        if (alternativeUrl != null
                && !alternativeUrl.isEmpty()
                && !Objects.equals(baseUrl, alternativeUrl)) {
            this.alternativeUrls.add(alternativeUrl);
        }
    }

    public List<String> getDownloadList() {
        List<String> downloadUrls = new ArrayList<>();

        if (this.baseUrl != null && !this.baseUrl.isEmpty()) {
            downloadUrls.add(this.baseUrl);
        }

        // no needs in base url duplicate
        if (this.alternativeUrls != null) {
            for (String url : this.alternativeUrls) {
                if (!url.equals(this.baseUrl)) {
                    downloadUrls.add(url);
                }
            }
        }

        return downloadUrls;
    }

    public void addAlternativeUrl(String url) {
        if (url != null && !url.isEmpty()) {
            this.alternativeUrls.add(url);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
