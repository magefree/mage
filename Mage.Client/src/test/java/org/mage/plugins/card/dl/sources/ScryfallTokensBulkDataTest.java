package org.mage.plugins.card.dl.sources;

import com.google.gson.Gson;
import mage.client.dialog.PreferencesDialog;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Images download: scryfall tokens must use direct links from bulk data instead api calls
 * <p>
 * Each api call needs a pause (rate limits), so full tokens download took 2.7k api calls and 14+ minutes.
 * Bulk data contains the same token cards (set, number, language, faces), so api links can be replaced by direct links.
 * <p>
 * Uses fake bulk data file and app's preferences cache only (no network, user's settings untouched)
 *
 * @author JayDi85
 */
public class ScryfallTokensBulkDataTest {

    private static final String CDN = "https://cards.scryfall.io/";

    private File imagesDir;
    private final Map<String, String> oldSettings = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        imagesDir = Files.createTempDirectory("xmage-scryfall-test").toFile();
        setCachedSetting(PreferencesDialog.KEY_CARD_IMAGES_USE_DEFAULT, "false");
        setCachedSetting(PreferencesDialog.KEY_CARD_IMAGES_PATH, imagesDir.getAbsolutePath());
        ScryfallImageSource.getInstance().onFinished(); // clear static bulk data from other tests
    }

    @After
    public void tearDown() throws Exception {
        ScryfallImageSource.getInstance().onFinished();
        for (Map.Entry<String, String> entry : oldSettings.entrySet()) {
            setCachedSetting(entry.getKey(), entry.getValue());
        }
        deleteFolder(imagesDir);
    }

    @Test
    public void test_TokenLinks_MustBeSupportedByBulkData() {
        // all api links must be parsed (wrong links can't use bulk data and can't be downloaded by api too)
        List<String> wrongLinks = ScryfallImageSupportTokens.getTokenLinks().entrySet().stream()
                .filter(e -> e.getValue().startsWith("https://api.scryfall.com/"))
                .filter(e -> ScryfallImageSource.parseApiImageLink(e.getValue()) == null)
                .map(e -> e.getKey() + " - " + e.getValue())
                .sorted()
                .collect(Collectors.toList());
        Assert.assertEquals("found wrong token links: " + wrongLinks, 0, wrongLinks.size());
    }

    @Test
    public void test_ParseApiImageLink() {
        assertLink("https://api.scryfall.com/cards/trix/1/en?format=image", "trix", "1", "en", "");
        assertLink("https://api.scryfall.com/cards/trix/6?format=image", "trix", "6", "", "");
        assertLink("https://api.scryfall.com/cards/tclb/19/?format=image", "tclb", "19", "", "");
        assertLink("https://api.scryfall.com/cards/tvow/21/en?format=image&face=back", "tvow", "21", "en", "back");
        assertLink("https://api.scryfall.com/cards/tmid/1/en?format=image&face=front", "tmid", "1", "en", "front");
        assertLink("https://api.scryfall.com/cards/tm21/1a/en?format=image", "tm21", "1a", "en", "");
        // xmage's number format (same as bulk data after prepareCompatibleData)
        assertLink("https://api.scryfall.com/cards/twar/2★/en?format=image", "twar", "2*", "en", "");
        assertLink("https://api.scryfall.com/cards/twar/2%E2%98%85/en?format=image", "twar", "2*", "en", "");

        // unsupported
        Assert.assertNull(ScryfallImageSource.parseApiImageLink(null));
        Assert.assertNull(ScryfallImageSource.parseApiImageLink("https://api.scryfall.com/cards/tmsh/1?5format=image"));
        Assert.assertNull(ScryfallImageSource.parseApiImageLink("https://api.scryfall.com/cards/trix/1/en?format=json"));
        Assert.assertNull(ScryfallImageSource.parseApiImageLink("https://api.scryfall.com/cards/trix/1/en?format=image&face=middle"));
        Assert.assertNull(ScryfallImageSource.parseApiImageLink("https://cards.scryfall.io/large/front/d/c/dc26e13b.jpg"));
        Assert.assertNull(ScryfallImageSource.parseApiImageLink("https://upload.wikimedia.org/wikipedia/en/a/aa/card_back.jpg"));
    }

    @Test
    public void test_Tokens_UseDirectLinksFromBulkData() throws Exception {
        // real tokens from xmage's list, one per link type
        Map<String, String> links = new TreeMap<>(ScryfallImageSupportTokens.getTokenLinks());
        String tokenEn = findToken(links, "/en?format=image", null);
        String tokenNoLang = findToken(links, "?format=image", "/en?");
        String tokenBack = findToken(links, "&face=back", null);
        String tokenFront = findToken(links, "&face=front", null);
        String tokenStar = links.keySet().stream().filter(k -> links.get(k).contains("★")).findFirst().orElse(null);
        String tokenJapanOnly = findToken(links, "?format=image", "/en?", tokenNoLang);
        String tokenMissing = findToken(links, "/en?format=image", null, tokenEn);
        String tokenNonApi = links.keySet().stream().filter(k -> !links.get(k).contains("api.scryfall.com")).findFirst().orElse(null);
        Assert.assertNotNull(tokenEn);
        Assert.assertNotNull(tokenNoLang);
        Assert.assertNotNull(tokenBack);
        Assert.assertNotNull(tokenFront);
        Assert.assertNotNull(tokenStar);
        Assert.assertNotNull(tokenJapanOnly);
        Assert.assertNotNull(tokenMissing);

        // fake bulk data (all-cards: all languages, en first, tokens with faces)
        List<String> bulk = new ArrayList<>();
        bulk.add(bulkCard("Grizzly Bears", "10e", "268", "en", "normal", null));
        bulk.add(bulkCard("Test En", links.get(tokenEn), "ru", false)); // other language must be ignored
        bulk.add(bulkCard("Test En", links.get(tokenEn), "en", false));
        bulk.add(bulkCard("Test No Lang", links.get(tokenNoLang), "en", false));
        bulk.add(bulkCard("Test No Lang", links.get(tokenNoLang), "de", false));
        bulk.add(bulkCard("Test Back", links.get(tokenBack), "en", true));
        bulk.add(bulkCard("Test Front", links.get(tokenFront), "en", true));
        bulk.add(bulkCard("Test Star", links.get(tokenStar), "en", false));
        bulk.add(bulkCard("Test Japan", links.get(tokenJapanOnly), "ja", false)); // default version for a link without lang
        writeBulkData(bulk);

        List<CardDownloadData> downloadList = new ArrayList<>();
        Map<String, CardDownloadData> tokens = new HashMap<>();
        for (String key : Arrays.asList(tokenEn, tokenNoLang, tokenBack, tokenFront, tokenStar, tokenJapanOnly, tokenMissing, tokenNonApi)) {
            if (key == null) {
                continue;
            }
            CardDownloadData card = createToken(key);
            tokens.put(key, card);
            downloadList.add(card);
        }

        // large images
        ScryfallImageSource source = ScryfallImageSource.getInstance();
        Assert.assertTrue(source.prepareDownloadList(new TestServiceInfo(), downloadList));
        Assert.assertEquals(expectedImage(links.get(tokenEn), "en", "large", "front"), source.generateTokenUrl(tokens.get(tokenEn)).getBaseUrl());
        Assert.assertEquals(expectedImage(links.get(tokenNoLang), "en", "large", "front"), source.generateTokenUrl(tokens.get(tokenNoLang)).getBaseUrl());
        Assert.assertEquals(expectedImage(links.get(tokenBack), "en", "large", "back"), source.generateTokenUrl(tokens.get(tokenBack)).getBaseUrl());
        Assert.assertEquals(expectedImage(links.get(tokenFront), "en", "large", "front"), source.generateTokenUrl(tokens.get(tokenFront)).getBaseUrl());
        Assert.assertEquals(expectedImage(links.get(tokenStar), "en", "large", "front"), source.generateTokenUrl(tokens.get(tokenStar)).getBaseUrl());
        Assert.assertEquals(expectedImage(links.get(tokenJapanOnly), "ja", "large", "front"), source.generateTokenUrl(tokens.get(tokenJapanOnly)).getBaseUrl());
        // not found in bulk data - api link as before
        Assert.assertEquals(links.get(tokenMissing), source.generateTokenUrl(tokens.get(tokenMissing)).getBaseUrl());
        if (tokenNonApi != null) {
            Assert.assertEquals(links.get(tokenNonApi), source.generateTokenUrl(tokens.get(tokenNonApi)).getBaseUrl());
        }

        // small images
        ScryfallImageSource sourceSmall = ScryfallImageSourceSmall.getInstance();
        Assert.assertTrue(sourceSmall.prepareDownloadList(new TestServiceInfo(), downloadList));
        Assert.assertEquals(expectedImage(links.get(tokenEn), "en", "small", "front"), sourceSmall.generateTokenUrl(tokens.get(tokenEn)).getBaseUrl());
        Assert.assertEquals(expectedImage(links.get(tokenBack), "en", "small", "back"), sourceSmall.generateTokenUrl(tokens.get(tokenBack)).getBaseUrl());
        Assert.assertEquals(links.get(tokenMissing).replace("format=image", "format=image&version=small"),
                sourceSmall.generateTokenUrl(tokens.get(tokenMissing)).getBaseUrl());
    }

    private static void assertLink(String apiLink, String set, String number, String lang, String face) {
        ScryfallImageSource.ApiImageLink link = ScryfallImageSource.parseApiImageLink(apiLink);
        Assert.assertNotNull("must parse " + apiLink, link);
        Assert.assertEquals(apiLink, set, link.set);
        Assert.assertEquals(apiLink, number, link.number);
        Assert.assertEquals(apiLink, lang, link.lang);
        Assert.assertEquals(apiLink, face, link.face);
    }

    private static String findToken(Map<String, String> links, String linkPart, String excludePart, String... excludeKeys) {
        List<String> excluded = Arrays.asList(excludeKeys);
        return links.entrySet().stream()
                .filter(e -> e.getValue().startsWith("https://api.scryfall.com/"))
                .filter(e -> e.getValue().contains(linkPart))
                .filter(e -> excludePart == null || !e.getValue().contains(excludePart))
                .filter(e -> !e.getValue().contains("&face=") || linkPart.contains("&face="))
                .filter(e -> !e.getValue().contains("★") && !e.getValue().contains("/?format"))
                .map(Map.Entry::getKey)
                .filter(k -> !excluded.contains(k))
                .findFirst()
                .orElse(null);
    }

    private static CardDownloadData createToken(String key) {
        // set/token_name or set/token_name/N
        String[] parts = key.split("/");
        int imageNumber = 0;
        String name;
        if (parts.length > 2 && parts[parts.length - 1].matches("\\d+")) {
            imageNumber = Integer.parseInt(parts[parts.length - 1]);
            name = String.join("/", Arrays.copyOfRange(parts, 1, parts.length - 1));
        } else {
            name = String.join("/", Arrays.copyOfRange(parts, 1, parts.length));
        }
        CardDownloadData card = new CardDownloadData(name, parts[0], "0", false, imageNumber);
        card.setToken(true);
        Assert.assertEquals("wrong test: can't find token by key " + key,
                ScryfallImageSupportTokens.getTokenLinks().get(key),
                ScryfallImageSupportTokens.findTokenLink(card.getSet(), card.getName(), card.getImageNumber()));
        return card;
    }

    private static String expectedImage(String apiLink, String lang, String quality, String face) {
        ScryfallImageSource.ApiImageLink link = ScryfallImageSource.parseApiImageLink(apiLink);
        return imageLink(link.set, link.number, lang, quality, face);
    }

    private static String imageLink(String set, String number, String lang, String quality, String face) {
        return CDN + quality + "/" + face + "/" + set + "-" + number + "-" + lang + ".jpg";
    }

    private static String bulkCard(String name, String apiLink, String lang, boolean doubleFaced) {
        ScryfallImageSource.ApiImageLink link = ScryfallImageSource.parseApiImageLink(apiLink);
        Assert.assertNotNull("wrong test: unsupported link " + apiLink, link);
        // scryfall's number format
        String number = ScryfallApiCard.transformCardNumberFromXmageToScryfall(link.number);
        if (!doubleFaced) {
            return bulkCard(name, link.set, number, lang, "token", null);
        }
        List<Map<String, Object>> faces = new ArrayList<>();
        for (String face : Arrays.asList("front", "back")) {
            Map<String, Object> jsonFace = new LinkedHashMap<>();
            jsonFace.put("name", name + " " + face);
            jsonFace.put("image_uris", imageUris(link.set, link.number, lang, face));
            faces.add(jsonFace);
        }
        return bulkCard(name + " front // " + name + " back", link.set, number, lang, "double_faced_token", faces);
    }

    private static String bulkCard(String name, String set, String number, String lang, String layout, List<Map<String, Object>> faces) {
        Map<String, Object> json = new LinkedHashMap<>();
        json.put("id", UUID.randomUUID().toString());
        json.put("name", name);
        json.put("set", set);
        json.put("collector_number", number);
        json.put("lang", lang);
        json.put("layout", layout);
        if (faces == null) {
            json.put("image_uris", imageUris(set, ScryfallApiCard.transformCardNumberFromScryfallToXmage(number), lang, "front"));
        } else {
            json.put("card_faces", faces);
        }
        return new Gson().toJson(json);
    }

    private static Map<String, String> imageUris(String set, String number, String lang, String face) {
        Map<String, String> res = new LinkedHashMap<>();
        for (String quality : Arrays.asList("small", "normal", "large")) {
            res.put(quality, imageLink(set, number, lang, quality, face));
        }
        return res;
    }

    private void writeBulkData(List<String> lines) throws IOException {
        // fresh file - no download
        File file = new File(imagesDir, "downloading" + File.separator + "scryfall_bulk_cards.jsonl");
        Assert.assertTrue(file.getParentFile().mkdirs());
        Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
    }

    private void setCachedSetting(String key, String value) throws Exception {
        Field cacheField = PreferencesDialog.class.getDeclaredField("CACHE");
        cacheField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, String> cache = (Map<String, String>) cacheField.get(null);
        if (!oldSettings.containsKey(key)) {
            oldSettings.put(key, cache.get(key));
        }
        if (value == null) {
            cache.remove(key);
        } else {
            cache.put(key, value);
        }
    }

    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }

    private static class TestServiceInfo implements DownloadServiceInfo {

        private final Object sync = new Object();

        @Override
        public boolean isNeedCancel() {
            return false;
        }

        @Override
        public void incErrorCount() {
            Assert.fail("unexpected error on prepare");
        }

        @Override
        public void stopOnRateLimit(String url, String retryAfter) {
            Assert.fail("unexpected api call on prepare: " + url);
        }

        @Override
        public void updateGlobalMessage(String text) {
        }

        @Override
        public void updateProgressMessage(String text) {
        }

        @Override
        public void updateProgressMessage(String text, int progressCurrent, int progressNeed) {
        }

        @Override
        public Object getSync() {
            return sync;
        }
    }
}
