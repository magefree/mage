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
        return oneUrl.replaceFirst("/display/", "/grid/").replaceFirst("format=image", "format=image&version=grid");
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
    public float getAverageSizeKb() {
        // June 2024: MH3 set - 30500 Kb / 332 = 92 Kb (jpg)
        // Nov 2026: webp images are about 62% of the jpg size, so 92 Kb * 0.62 = 57 Kb
        return 57f;
    }

    @Override
    public String getImageQuality() {
        return "grid";
    }
}
