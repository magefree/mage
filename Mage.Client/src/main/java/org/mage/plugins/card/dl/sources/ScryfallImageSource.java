package org.mage.plugins.card.dl.sources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mage.MageException;
import mage.client.util.CardLanguage;
import mage.util.JsonUtil;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.FileNotFoundException;
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

        // double faced cards (modal double faces cards too)
        if (card.isTwoFacedCard()) {
            if (card.isSecondSide()) {
                // back face - must be prepared before
                logger.warn("Can't find back face info in prepared list "
                        + card.getName() + " (" + card.getSet() + ") #" + card.getCollectorId());
                return new CardImageUrls(null, null);
            } else {
                // front face - can be downloaded normally as basic card
            }
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

    private String getFaceImageUrl(Proxy proxy, CardDownloadData card, boolean isToken) throws Exception {
        final String defaultCode = CardLanguage.ENGLISH.getCode();
        final String localizedCode = languageAliases.getOrDefault(this.getCurrentLanguage(), defaultCode);

        List<String> needUrls = new ArrayList<>();

        String apiUrl = ScryfallImageSupportCards.findDirectDownloadLink(card.getSet(), card.getName(), card.getCollectorId());
        if (apiUrl != null) {
            // BY DIRECT URL
            // direct links via hardcoded API path. Used for cards with non-ASCII collector numbers
            if (localizedCode.equals(defaultCode)) {
                // english only, so can use workaround without loc param (scryfall download first available card)
                // workaround to use cards without english images (some promos or special cards)
                needUrls.add(apiUrl);
            } else {
                // localize, must use loc params
                needUrls.add(apiUrl + localizedCode);
                needUrls.add(apiUrl + defaultCode);
            }
        } else {
            // BY CARD NUMBER
            // localized and default
            needUrls.add("https://api.scryfall.com/cards/"
                    + formatSetName(card.getSet(), isToken) + "/" + card.getCollectorId() + "/" + localizedCode);
            if (!localizedCode.equals(defaultCode)) {
                needUrls.add("https://api.scryfall.com/cards/"
                        + formatSetName(card.getSet(), isToken) + "/" + card.getCollectorId() + "/" + defaultCode);
            }
        }

        InputStream jsonStream = null;
        String jsonUrl = null;
        for (String currentUrl : needUrls) {
            // connect to Scryfall API
            URL cardUrl = new URL(currentUrl);
            URLConnection request = (proxy == null ? cardUrl.openConnection() : cardUrl.openConnection(proxy));
            request.connect();
            try {
                jsonStream = (InputStream) request.getContent();
                jsonUrl = currentUrl;
                // found good url, can stop
                break;
            } catch (FileNotFoundException e) {
                // localized image doesn't exists, try next url
            }
        }

        if (jsonStream == null) {
            throw new MageException("Couldn't find card's JSON data on the server");
        }

        // OK, found card data, parse it
        JsonObject jsonCard = JsonParser.parseReader(new InputStreamReader(jsonStream)).getAsJsonObject();
        JsonArray jsonFaces = JsonUtil.getAsArray(jsonCard, "card_faces");
        if (jsonFaces == null) {
            throw new MageException("Couldn't find card_faces in card's JSON data: " + jsonUrl);
        }

        JsonObject jsonFace = jsonFaces.get(card.isSecondSide() ? 1 : 0).getAsJsonObject();
        JsonObject jsonImages = JsonUtil.getAsObject(jsonFace, "image_uris");
        if (jsonImages == null) {
            throw new MageException("Couldn't find image_uris in card's JSON data: " + jsonUrl);
        }

        return JsonUtil.getAsString(jsonImages, "large");
    }

    @Override
    public boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        // prepare download list example (
        Proxy proxy = downloadServiceInfo.getProxy();

        preparedUrls.clear();

        // prepare stats
        int needPrepareCount = 0;
        int currentPrepareCount = 0;
        for (CardDownloadData card : downloadList) {
            if (card.isTwoFacedCard() && card.isSecondSide()) {
                needPrepareCount++;
            }
        }
        updatePrepareStats(downloadServiceInfo, needPrepareCount, currentPrepareCount);

        for (CardDownloadData card : downloadList) {
            // need cancel
            if (downloadServiceInfo.isNeedCancel()) {
                return false;
            }

            // prepare the back face URL
            if (card.isTwoFacedCard() && card.isSecondSide()) {
                currentPrepareCount++;
                try {
                    String url = getFaceImageUrl(proxy, card, card.isToken());
                    preparedUrls.put(card, url);
                } catch (Exception e) {
                    logger.warn("Failed to prepare image URL (back face) for " + card.getName() + " (" + card.getSet() + ") #"
                            + card.getCollectorId() + ", Error Message: " + e.getMessage());
                    downloadServiceInfo.incErrorCount();
                }

                updatePrepareStats(downloadServiceInfo, needPrepareCount, currentPrepareCount);
            }
        }

        return true;
    }

    private void updatePrepareStats(DownloadServiceInfo service, int need, int current) {
        synchronized (service.getSync()) {
            service.updateProgressMessage(String.format("Preparing download list... %d of %d", current, need));
        }
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
            // tokens uses xmage codes to find direct link download
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
