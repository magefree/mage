package org.mage.plugins.card.dl.sources;

import java.util.ArrayList;
import org.mage.plugins.card.images.CardDownloadData;

/**
 *
 * @author North
 */
public interface CardImageSource {

    String generateURL(CardDownloadData card) throws Exception;

    String generateTokenUrl(CardDownloadData card) throws Exception;

    String getNextHttpImageUrl();

    String getFileForHttpImage(String httpImageUrl);

    String getSourceName();

    float getAverageSize();

    int getTotalImages();

    boolean isTokenSource();

    void doPause(String httpImageUrl);

    default ArrayList<String> getSupportedSets() {
        return null;
    }

    default boolean providesTokenImages() {
        return false;
    }
;
}
