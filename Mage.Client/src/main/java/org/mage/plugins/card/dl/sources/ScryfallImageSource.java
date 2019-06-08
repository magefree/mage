package org.mage.plugins.card.dl.sources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.client.util.CardLanguage;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author JayDi85
 */
public enum ScryfallImageSource implements CardImageSource {

    instance;

    private static final Logger logger = Logger.getLogger(ScryfallImageSource.class);

    private final Map<CardLanguage, String> languageAliases;
    private CardLanguage currentLanguage = CardLanguage.ENGLISH; // working language
    private Map<CardDownloadData, String> preparedUrls = new HashMap<>();

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
        String prepared = preparedUrls.getOrDefault(card, null);
        if (prepared != null) {
            return new CardImageUrls(prepared, null);
        }

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

        // art variation cards
        // ARN and POR use † notation
        // PLS uses ★ notation
        if (baseUrl == null && card.getUsesVariousArt() && card.getSet().matches("ARN|POR|PLS")) {
            String scryfallCollectorId = card.getCollectorIdAsInt().toString();

            if (card.getCollectorId().endsWith("b")) {
                if (card.getSet().matches("ARN|POR")) {
                    scryfallCollectorId += "†";
                } else if (card.getSet().matches("PLS")) {
                    scryfallCollectorId += "★";
                }
            }

            try {
                scryfallCollectorId = URLEncoder.encode(scryfallCollectorId, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // URL failed to encode, this will cause download to miss in certain environments
            }

            baseUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                    + scryfallCollectorId + "/" + localizedCode + "?format=image";
            alternativeUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                    + scryfallCollectorId + "/" + defaultCode + "?format=image";
        }

        // double faced card
        // the front face can be downloaded normally
        // the back face is prepared beforehand
        if (baseUrl == null && card.isTwoFacedCard() && !card.isSecondSide()) {
            baseUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                    + card.getCollectorIdAsInt() + "/" + localizedCode + "?format=image";
            alternativeUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                    + card.getCollectorIdAsInt() + "/" + defaultCode + "?format=image";
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

    private String getFaceImageUrl(Proxy proxy, CardDownloadData card, boolean isToken, String localizationCode) throws Exception {
        // connect to Scryfall API
        final URL cardUrl = new URL("https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                + (card.getCollectorIdAsInt() % 1000) + "/" + localizationCode);
        URLConnection request = proxy == null ? cardUrl.openConnection() : cardUrl.openConnection(proxy);
        request.connect();

        // parse the response and return the image URI from the correct card face
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonCard = root.getAsJsonObject();
        if (!jsonCard.has("card_faces")) {
            throw new Exception("Couldn't find card_faces in Card JSON.");
        }
        JsonArray jsonCardFaces = jsonCard.getAsJsonArray("card_faces");
        JsonObject jsonCardFace = jsonCardFaces.get(card.isSecondSide() ? 1 : 0).getAsJsonObject();
        JsonObject jsonImageUris = jsonCardFace.getAsJsonObject("image_uris");

        return jsonImageUris.get("large").getAsString();
    }

    @Override
    public boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        // prepare download list example (
        Proxy proxy = downloadServiceInfo.getProxy();

        preparedUrls.clear();

        final List<ExpansionSet.SetCardInfo> sixthEditionCards = Sets.findSet("6ED").getSetCardInfo();

        for (CardDownloadData card : downloadList) {
            // need cancel
            if (downloadServiceInfo.isNeedCancel()) {
                return false;
            }

            // prepare the back face URL
            if (card.isTwoFacedCard() && card.isSecondSide()) {
                final String defaultCode = CardLanguage.ENGLISH.getCode();
                final String localizedCode = languageAliases.getOrDefault(this.getCurrentLanguage(), defaultCode);

                String url = null;

                try {
                    url = getFaceImageUrl(proxy, card, card.isToken(), localizedCode);
                } catch (Exception e) {
                    logger.warn("Failed to prepare image URL for " + card.getName() + " (" + card.getSet() + ") #" + card.getCollectorId());
                    downloadServiceInfo.incErrorCount();
                    continue;
                }

                preparedUrls.put(card, url);
            }

            // if a S00 card is in 6ED, it's actually a 6ED card
            if (card.getSet().equals("S00") && sixthEditionCards.stream().anyMatch(sixthEditionCard -> sixthEditionCard.getName().equals(card.getName()))) {
                // we have direct links for the lands because there are multiple search results
                if (card.getUsesVariousArt()) { // lands are the only defined multiple art cards in S00 in XMage
                    continue;
                }

                String url = null;

                try {
                    url = searchCard(proxy, "6ED", card.getName());
                } catch (Exception e) {
                    logger.warn("Failed to prepare image URL for " + card.getName() + " (" + card.getSet() + ") #" + card.getCollectorId());
                    downloadServiceInfo.incErrorCount();
                    continue;
                }

                preparedUrls.put(card, url);
            }

            // if an E01 card number is above 106, it's actually an AKH card
            if (card.getSet().equals("E01") && card.getCollectorIdAsInt() > 106) {
                String url = null;

                try {
                    url = searchCard(proxy, "AKH", card.getName());
                } catch (Exception e) {
                    logger.warn("Failed to prepare image URL for " + card.getName() + " (" + card.getSet() + ") #" + card.getCollectorId());
                    downloadServiceInfo.incErrorCount();
                    continue;
                }

                preparedUrls.put(card, url);
            }

            // inc error count to stop on too many errors
            // downloadServiceInfo.incErrorCount();
        }

        return true;
    }

    private String searchCard(Proxy proxy, String set, String name) throws Exception {
        final URL searchUrl = new URL("https://api.scryfall.com/cards/search?q=s:" + URLEncoder.encode(set + " " + name, "UTF-8"));
        URLConnection request = proxy == null ? searchUrl.openConnection() : searchUrl.openConnection(proxy);
        request.connect();

        // parse the response and return the image URI from the correct card face
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject searchResult = root.getAsJsonObject();
        if (searchResult.get("total_cards").getAsInt() != 1) {
            throw new Exception("Card not found in Scryfall.");
        }
        JsonObject jsonCard = searchResult.getAsJsonArray("data").get(0).getAsJsonObject();
        JsonObject jsonImageUris = jsonCard.getAsJsonObject("image_uris");

        return jsonImageUris.get("large").getAsString();
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
