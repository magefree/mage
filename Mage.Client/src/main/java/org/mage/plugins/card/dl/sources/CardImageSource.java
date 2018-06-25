package org.mage.plugins.card.dl.sources;

import java.util.ArrayList;
import org.mage.plugins.card.images.CardDownloadData;

/**
 *
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

    void doPause(String httpImageUrl);

    default ArrayList<String> getSupportedSets() {
        return null;
    }

    default boolean isSetSupportedComplete(String setCode) {
        return true;
    }

    default boolean isImageProvided(String setCode, String cardName) {
        return false;
    }
}
