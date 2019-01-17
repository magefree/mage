package org.mage.plugins.card.dl.sources;

import mage.client.util.CardLanguage;
import org.mage.plugins.card.images.CardDownloadData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author JayDi85
 */
public enum ScryfallImageSource implements CardImageSource {

    instance;

    private final Map<CardLanguage, String> languageAliases;
    private CardLanguage currentLanguage = CardLanguage.ENGLISH; // working language

    ScryfallImageSource() {
        // LANGUAGES
        // https://scryfall.com/docs/api/languages
        languageAliases = new HashMap<>();
        languageAliases.put(CardLanguage.ENGLISH, "en");
        languageAliases.put(CardLanguage.SPANISH, "es");
        languageAliases.put(CardLanguage.FRENCH, "fr");
        languageAliases.put(CardLanguage.GERMAN, "de");
        languageAliases.put(CardLanguage.ITALIAN, "it");
        languageAliases.put(CardLanguage.PORTUGUESE, "pt");
        languageAliases.put(CardLanguage.JAPANESE, "ja");
        languageAliases.put(CardLanguage.KOREAN, "ko");
        languageAliases.put(CardLanguage.RUSSIAN, "ru");
        languageAliases.put(CardLanguage.CHINES_SIMPLE, "zhs");
        languageAliases.put(CardLanguage.CHINES_TRADITION, "zht");
    }

    private CardImageUrls innerGenerateURL(CardDownloadData card, boolean isToken) {
        String defaultCode = CardLanguage.ENGLISH.getCode();
        String localizedCode = languageAliases.getOrDefault(this.getCurrentLanguage(), defaultCode);
        // loc example: https://api.scryfall.com/cards/xln/121/ru?format=image

        // WARNING, some cards haven't direct images and uses random GUID:
        // As example: Raging Ravine - https://scryfall.com/card/uma/249/raging-ravine
        // https://img.scryfall.com/cards/large/front/5/4/54f41726-e0bb-4154-a2db-4b68b50f5032.jpg
        String baseUrl = null;
        String alternativeUrl = null;

        // TOKENS TRY

        // tokens support only direct links
        if (baseUrl == null && isToken) {
            baseUrl = ScryfallImageSupportTokens.findTokenLink(card.getSet(), card.getName(), card.getType());
            alternativeUrl = null;
        }

        // CARDS TRY

        // direct links to images (non localization)
        if (baseUrl == null) {
            baseUrl = ScryfallImageSupportCards.findDirectDownloadLink(card.getSet(), card.getName(), card.getCollectorId());
            alternativeUrl = null;
        }

        // special card number like "103a" and "U123" already compatible
        if (baseUrl == null && card.isCollectorIdWithStr()) {
            // WARNING, after 2018 it's not compatible and some new sets have GUID files instead card numbers
            // TODO: replace card number links to API calls (need test with lands, alternative images and double faces), replace not working images by direct links
            if (card.getCollectorId().startsWith("U")) {
                // fix for Ultimate Box Topper (PUMA) -- need to use API
                // ignored and go to API call at the end
            } else {
                baseUrl = "https://img.scryfall.com/cards/large/" + localizedCode + "/" + formatSetName(card.getSet(), isToken) + "/"
                        + card.getCollectorId() + ".jpg";
                alternativeUrl = "https://img.scryfall.com/cards/large/" + defaultCode + "/" + formatSetName(card.getSet(), isToken) + "/"
                        + card.getCollectorId() + ".jpg";
            }
        }

        // double faced cards do not supports by API (need direct link for img)
        // example: https://img.scryfall.com/cards/large/en/xln/173b.jpg
        if (baseUrl == null && card.isTwoFacedCard()) {
            baseUrl = "https://img.scryfall.com/cards/large/" + localizedCode + "/" + formatSetName(card.getSet(), isToken) + "/"
                    + card.getCollectorId() + (card.isSecondSide() ? "b" : "a") + ".jpg";
            alternativeUrl = "https://img.scryfall.com/cards/large/" + defaultCode + "/" + formatSetName(card.getSet(), isToken) + "/"
                    + card.getCollectorId() + (card.isSecondSide() ? "b" : "a") + ".jpg";
        }

        // basic cards by api call (redirect to img link)
        // example: https://api.scryfall.com/cards/xln/121/en?format=image
        if (baseUrl == null) {
            baseUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                    + card.getCollectorId() + "/" + localizedCode + "?format=image";
            alternativeUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                    + card.getCollectorId() + "/" + defaultCode + "?format=image";
        }

        return new CardImageUrls(baseUrl, alternativeUrl);
    }

    @Override
    public CardImageUrls generateCardUrl(CardDownloadData card) throws Exception {
        return innerGenerateURL(card, false);

    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) throws Exception {
        return innerGenerateURL(card, true);
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
    public String getSourceName() {
        return "scryfall.com";
    }

    @Override
    public float getAverageSize() {
        return 90;
    }

    @Override
    public int getTotalImages() {
        return -1;
    }

    @Override
    public boolean isTokenSource() {
        return true;
    }

    @Override
    public boolean isLanguagesSupport() {
        return true;
    }

    @Override
    public void setCurrentLanguage(CardLanguage cardLanguage) {
        this.currentLanguage = cardLanguage;
    }

    @Override
    public CardLanguage getCurrentLanguage() {
        return currentLanguage;
    }

    @Override
    public void doPause(String httpImageUrl) {

    }

    private String formatSetName(String setName, boolean isToken) {
        if (isToken) {
            // token uses direct link download, not set
            return setName.toLowerCase(Locale.ENGLISH);
        } else {
            return ScryfallImageSupportCards.findScryfallSetCode(setName);
        }
    }

    @Override
    public ArrayList<String> getSupportedSets() {
        ArrayList<String> supportedSetsCopy = new ArrayList<>();

        // cards
        supportedSetsCopy.addAll(ScryfallImageSupportCards.getSupportedSets());

        // tokens
        for (String code : ScryfallImageSupportTokens.getSupportedSets().keySet()) {
            if (!supportedSetsCopy.contains(code)) {
                supportedSetsCopy.add(code);
            }
        }

        return supportedSetsCopy;
    }

    @Override
    public boolean isCardImageProvided(String setCode, String cardName) {
        // all cards from set
        return ScryfallImageSupportCards.getSupportedSets().contains(setCode);
    }

    @Override
    public boolean isTokenImageProvided(String setCode, String cardName, Integer tokenNumber) {
        // only direct tokens from set
        return ScryfallImageSupportTokens.findTokenLink(setCode, cardName, tokenNumber) != null;
    }
}
