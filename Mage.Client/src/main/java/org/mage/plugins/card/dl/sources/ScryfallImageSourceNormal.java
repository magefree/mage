package org.mage.plugins.card.dl.sources;

import org.mage.plugins.card.images.CardDownloadData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tiera3
 */
public class ScryfallImageSourceNormal extends ScryfallImageSource {

    private static final ScryfallImageSourceNormal instanceNormal = new ScryfallImageSourceNormal();
    
    public static ScryfallImageSource getInstance() {
        return instanceNormal;
    }

    private static String innerModifyUrlString(String oneUrl) {
        return oneUrl.replaceFirst("/large/","/normal/").replaceFirst("format=image","format=image&version=normal");
    }

    private static CardImageUrls innerModifyUrl(CardImageUrls cardUrls) {
        List<String> downloadUrls = cardUrls.getDownloadList().stream() 
            .map(ScryfallImageSourceNormal::innerModifyUrlString)
            .collect(Collectors.toList());
        return new CardImageUrls(downloadUrls);
    }

    @Override
    public String getSourceName() {
        return "scryfall.com - normal";
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
        return 100; // initial estimate - TODO calculate a more accurate number
    }

}
