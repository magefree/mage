package org.mage.plugins.card.dl.sources;

/**
 *
 * @author North
 */
public interface CardImageSource {

    public String generateURL(Integer collectorId, String cardSet) throws Exception;
}
