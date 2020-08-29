package org.mage.plugins.card.dl.sources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mage.client.util.CardLanguage;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author JayDi85
 */
public enum ScryfallImageSource implements CardImageSource {

    instance;

    private static final Logger logger = Logger.getLogger(ScryfallImageSource.class);

    private final Map<CardLanguage, String> languageAliases;
    private CardLanguage currentLanguage = CardLanguage.ENGLISH; // working language
    private final Map<CardDownloadData, String> preparedUrls = new HashMap<>();

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
        if (isToken) {
            baseUrl = ScryfallImageSupportTokens.findTokenLink(card.getSet(), card.getName(), card.getType());
            alternativeUrl = null;
        }

        // CARDS TRY

        // direct links to images via hardcoded API path. Used for cards with non-ASCII collector numbers
        if (baseUrl == null) {
            String apiUrl = ScryfallImageSupportCards.findDirectDownloadLink(card.getSet(), card.getName(), card.getCollectorId());
            if (apiUrl != null) {
                baseUrl = apiUrl + localizedCode + "?format=image";
                alternativeUrl = apiUrl + defaultCode + "?format=image";

                // workaround to use cards without english images (some promos or special cards)
                if (Objects.equals(baseUrl, alternativeUrl) && baseUrl.endsWith("/en?format=image")) {
                    alternativeUrl = alternativeUrl.replace("/en?format=image", "/?format=image");
                }
            }
        }

        // double faced card
        // the front face can be downloaded normally
        // the back face is prepared beforehand
        if (baseUrl == null && card.isTwoFacedCard() && !card.isSecondSide()) {
            baseUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                    + card.getCollectorId() + "/" + localizedCode + "?format=image";
            alternativeUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                    + card.getCollectorId() + "/" + defaultCode + "?format=image";
        }

        // basic cards by api call (redirect to img link)
        // example: https://api.scryfall.com/cards/xln/121/en?format=image
        if (baseUrl == null) {
            baseUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                    + card.getCollectorId() + "/" + localizedCode + "?format=image";
            alternativeUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                    + card.getCollectorId() + "/" + defaultCode + "?format=image";

            // workaround to use cards without english images (some promos or special cards)
            // bug: https://github.com/magefree/mage/issues/6829
            // example: Mysterious Egg from IKO https://api.scryfall.com/cards/iko/385/?format=image
            if (Objects.equals(baseUrl, alternativeUrl)) {
                // without loc code scryfall must return first available image
                alternativeUrl = "https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                        + card.getCollectorId() + "/?format=image";
            }
        }

        return new CardImageUrls(baseUrl, alternativeUrl);
    }

    private String getFaceImageUrl(Proxy proxy, CardDownloadData card, boolean isToken, String localizationCode) throws Exception {
        // connect to Scryfall API
        final URL cardUrl = new URL("https://api.scryfall.com/cards/" + formatSetName(card.getSet(), isToken) + "/"
                + card.getCollectorId() + "/" + localizationCode);
        URLConnection request = proxy == null ? cardUrl.openConnection() : cardUrl.openConnection(proxy);
        request.connect();

        // parse the response and return the image URI from the correct card face
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonCard = root.getAsJsonObject();
        if (!jsonCard.has("card_faces")) {
            throw new Exception("Couldn't find card_faces in Card JSON.: " + cardUrl.toString());
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
                    logger.warn("Failed to prepare image URL (back face) for " + card.getName() + " (" + card.getSet() + ") #"
                            + card.getCollectorId() + ", Error Message: " + e.getMessage());
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
        // March 2020: 46_354 image files with total size 9_545_168 KiB
        return 206;
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
    public boolean isCardSource() {
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
    public List<String> getSupportedSets() {

        // cards
        List<String> supportedSetsCopy = new ArrayList<>(ScryfallImageSupportCards.getSupportedSets());

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
