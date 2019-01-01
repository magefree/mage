package org.mage.plugins.card.dl.sources;

import mage.client.util.CardLanguage;
import org.mage.plugins.card.images.CardDownloadData;

import java.util.ArrayList;

/**
 * @author North
 */
public interface CardImageSource {

    CardImageUrls generateURL(CardDownloadData card) throws Exception;

    CardImageUrls generateTokenUrl(CardDownloadData card) throws Exception;

    String getNextHttpImageUrl();

    String getFileForHttpImage(String httpImageUrl);

    String getSourceName();

    float getAverageSize();

    int getTotalImages();

    default int getTokenImages() {
        return 0;
    }

    default boolean isTokenSource() {
        return false;
    }

    default boolean isLanguagesSupport() {
        return false;
    }

    default void setCurrentLanguage(CardLanguage cardLanguage) {
    }

    default CardLanguage getCurrentLanguage() {
        return CardLanguage.ENGLISH;
    }

    void doPause(String httpImageUrl);

    default ArrayList<String> getSupportedSets() {
        return new ArrayList<>();
    }

    default boolean isSetSupportedComplete(String setCode) {
        return true;
    }

    default boolean isImageProvided(String setCode, String cardName) {
        return false;
    }
}
