package org.mage.plugins.card.dl.sources;

/**
 *
 * @author North
 */
public interface CardImageSource {

    String generateURL(Integer collectorId, String cardName, String cardSet) throws Exception;
    String generateTokenUrl(String name, String set);
    Float getAverageSize();
}
