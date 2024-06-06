package org.mage.plugins.card.dl.sources;

import org.mage.plugins.card.images.CardDownloadData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tiera3
 */
public class ScryfallImageSourceSmall extends ScryfallImageSource {

    private static final ScryfallImageSourceSmall instanceSmall = new ScryfallImageSourceSmall();
    
    public static ScryfallImageSource getInstance() {
        return instanceSmall;
    }

    private static String innerModifyUrlString(String oneUrl) {
        return oneUrl.replaceFirst("/large/","/small/").replaceFirst("format=image","format=image&version=small");
    }

    private static CardImageUrls innerModifyUrl(CardImageUrls cardUrls) {
        List<String> downloadUrls = cardUrls.getDownloadList().stream() 
            .map(ScryfallImageSourceSmall::innerModifyUrlString)
            .collect(Collectors.toList());
        return new CardImageUrls(downloadUrls);
    }

    @Override
    public CardImageUrls generateCardUrl(CardDownloadData card) throws Exception {
        return innerModifyUrl(super.generateCardUrl(card));
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) throws Exception {
        return innerModifyUrl(super.generateTokenUrl(card));
    }

    @Override
    public float getAverageSize() {
        return 13; // initial estimate - TODO calculate a more accurate number
    }

}
