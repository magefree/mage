package org.mage.plugins.card.dl.sources;

import mage.client.util.CardLanguage;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import java.util.*;

/**
 * @author North, JayDi85
 */
public interface CardImageSource {

    CardImageUrls generateCardUrl(CardDownloadData card) throws Exception;

    CardImageUrls generateTokenUrl(CardDownloadData card) throws Exception;

    boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList);

    String getNextHttpImageUrl();

    String getFileForHttpImage(String httpImageUrl);

    String getSourceName();

    float getAverageSizeKb();

    int getTotalImages();

    default int getTokenImages() {
        return 0;
    }

    boolean isCardSource();

    boolean isTokenSource();

    default boolean isLanguagesSupport() {
        return false;
    }

    default void setCurrentLanguage(CardLanguage cardLanguage) {
    }

    default CardLanguage getCurrentLanguage() {
        return CardLanguage.ENGLISH;
    }

    /**
     * Timeout before each http request (must use for services with rate limits)
     */
    default void doPause(String fullUrl) {
        // nothing
    }

    /**
     * Set additional http headers like user agent, referer, cookies, etc
     */
    default Map<String, String> getHttpRequestHeaders(String fullUrl) {
        Map<String, String> headers = new LinkedHashMap<>();
        // TODO: add xmage name and client version here
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        return headers;
    }

    default List<String> getSupportedSets() {
        return new ArrayList<>();
    }


    boolean isCardImageProvided(String setCode, String cardName);

    default boolean isTokenImageProvided(String setCode, String cardName, Integer tokenNumber) {
        return false;
    }
}
