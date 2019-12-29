package org.mage.plugins.card.dl.sources;

import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import java.util.List;
import java.util.Locale;

/**
 * Site was shutdown by wizards Feb. 2015
 *
 * @author LevelX2
 */
public enum MtgImageSource implements CardImageSource {

    instance;

    @Override
    public String getSourceName() {
        return "mtgimage.com";
    }

    @Override
    public String getNextHttpImageUrl() {
        return null;
    }

    @Override
    public String getFileForHttpImage(String httpImageUrl) {
        return null;
    }

    @Override
    public boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        return true;
    }

    @Override
    public CardImageUrls generateCardUrl(CardDownloadData card) throws Exception {
        String collectorId = card.getCollectorId();
        String cardSet = card.getSet();
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        StringBuilder url = new StringBuilder("http://mtgimage.com/set/");
        url.append(cardSet.toUpperCase(Locale.ENGLISH)).append('/');

        if (card.isSplitCard()) {
            url.append(card.getDownloadName().replaceAll(" // ", ""));
        } else {
            url.append(card.getDownloadName().replaceAll(" ", "%20"));
        }

        if (card.isTwoFacedCard()) {
            url.append(card.isSecondSide() ? "b" : "a");
        }
        if (card.isFlipCard()) {
            if (card.isFlippedSide()) { // download rotated by 180 degree image
                url.append('b');
            } else {
                url.append('a');
            }
        }
        url.append(".jpg");

        return new CardImageUrls(url.toString());
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) {
        return null;
    }

    @Override
    public float getAverageSize() {
        return 70.0f;
    }

    @Override
    public int getTotalImages() {
        return -1;
    }

    @Override
    public boolean isTokenSource() {
        return false;
    }

    @Override
    public void doPause(String httpImageUrl) {
    }
}
