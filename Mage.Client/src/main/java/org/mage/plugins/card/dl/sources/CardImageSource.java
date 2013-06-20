package org.mage.plugins.card.dl.sources;

import org.mage.plugins.card.images.CardDownloadData;

/**
 *
 * @author North
 */
public interface CardImageSource {

    String generateURL(CardDownloadData card) throws Exception;
    String generateTokenUrl(CardDownloadData card);
    Float getAverageSize();
}
