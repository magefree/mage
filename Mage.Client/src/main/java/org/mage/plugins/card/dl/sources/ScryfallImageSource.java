package org.mage.plugins.card.dl.sources;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import mage.MageException;
import mage.client.remote.XmageURLConnection;
import mage.client.util.CardLanguage;
import mage.util.JsonUtil;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;
import net.java.truevfs.access.TFileOutputStream;
import net.java.truevfs.access.TVFS;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.GZIPInputStream;

import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

/**
 * Download: scryfall API support to download images and other data
 *
 * @author JayDi85
 */
public class ScryfallImageSource implements CardImageSource {

    private static final ScryfallImageSource instance = new ScryfallImageSource();

    private static final Logger logger = Logger.getLogger(ScryfallImageSource.class);

    private final Map<CardLanguage, String> languageAliases;
    private CardLanguage currentLanguage = CardLanguage.ENGLISH; // working language
    private final Map<CardDownloadData, String> preparedUrls = new HashMap<>();

    private static final ReentrantLock waitBeforeRequestLock = new ReentrantLock();
    private static final int DOWNLOAD_TIMEOUT_MS = 300;

    // bulk images optimization, how it works
    // - scryfall limit all calls to API endpoints, but allow direct calls to CDN (scryfall.io) without limitation
    // - so download and prepare whole scryfall database
    // - for each downloading card try to find it in bulk database and use direct link from it
    // - only card images supported (without tokens, emblems, etc)
    private static final boolean SCRYFALL_BULK_FILES_ENABLED = true; // use bulk data to find direct links instead API calls
    private static final long SCRYFALL_BULK_FILES_OUTDATE_IN_MILLIS = 7 * 24 * 3600 * 1000; // after 1 week need to download again
    private static final String SCRYFALL_BULK_FILES_DATABASE_SOURCE_API = "https://api.scryfall.com/bulk-data/all-cards"; // 300 MB in zip
    private static final boolean SCRYFALL_BULK_FILES_DEBUG_READ_ONLY_MODE = false; // default: false - for faster debug only, ignore write operations
    // run app with -Dxmage.scryfallEnableBulkData=false to disable bulk data (e.g. for testing api links generation)
    private static final String SCRYFALL_BULK_FILES_PROPERTY = "xmage.scryfallEnableBulkData";

    private static final List<ScryfallApiCard> bulkCardsDatabaseAll = new ArrayList<>(); // 100MB memory footprint, cleaning on download stop
    private static final Map<String, ScryfallApiCard> bulkCardsDatabaseDefault = new HashMap<>(); // card/set/number

    public static ScryfallImageSource getInstance() {
        return instance;
    }

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
            return new CardImageUrls(prepared);
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
            baseUrl = ScryfallImageSupportTokens.findTokenLink(card.getSet(), card.getName(), card.getImageNumber());
            alternativeUrl = null;
        }

        // CARDS TRY

        // direct links to images via hardcoded API path
        // used for cards with non-ASCII collector numbers or another use cases
        if (baseUrl == null) {
            String link = ScryfallImageSupportCards.findDirectDownloadLink(card.getSet(), card.getName(), card.getCollectorId());
            if (link != null) {
                if (ScryfallImageSupportCards.isApiLink(link)) {
                    // api link - must prepare direct link
                    baseUrl = link + localizedCode + "?format=image";
                    // workaround to use cards without english images (some promos or special cards)
                    if (link.endsWith("/")) {
                        alternativeUrl = link.substring(0, link.length() - 1) + "?format=image";
                    }
                } else {
                    // direct link to image
                    baseUrl = link;
                    // workaround to use localization in direct links
                    if (link.contains("/?format=image")) {
                        baseUrl = link.replaceFirst("\\?format=image", localizedCode + "?format=image");
                        alternativeUrl = link.replaceFirst("/\\?format=image", "?format=image");
                    }
                }
            }
        }

        // double faced cards (modal double faces cards too)
        // meld cards are excluded.
        if (card.isSecondSide()) {
            // back face - must be prepared before
            logger.warn("Can't find back face info in prepared list "
                    + card.getName() + " (" + card.getSet() + ") #" + card.getCollectorId());
            return new CardImageUrls(null, null);
        } else {
            // front face - can be downloaded normally as basic card
        }

        // basic cards by api call (redirect to img link)
        // example: https://api.scryfall.com/cards/xln/121/en?format=image
        if (baseUrl == null) {
            String cn = ScryfallApiCard.transformCardNumberFromXmageToScryfall(card.getCollectorId());
            baseUrl = String.format("https://api.scryfall.com/cards/%s/%s/%s?format=image",
                    formatSetName(card.getSet(), isToken),
                    cn,
                    localizedCode);
            alternativeUrl = String.format("https://api.scryfall.com/cards/%s/%s?format=image&include_variations=true",
                    formatSetName(card.getSet(), isToken),
                    cn);
            // with no localisation code, scryfall defaults to first available image - usually english, but may not be for some special cards
            // workaround to use cards without english images (some promos or special cards)
            // bug: https://github.com/magefree/mage/issues/6829
            // example: Mysterious Egg from IKO https://api.scryfall.com/cards/iko/385/?format=image
            // include_variations=true added to deal with the cards that scryfall has marked as variations that seem to sometimes fail
            // eg https://api.scryfall.com/cards/4ed/134†?format=image fails
            // eg https://api.scryfall.com/cards/4ed/134†?format=image&include_variations=true succeeds
        }

        return new CardImageUrls(baseUrl, alternativeUrl);
    }

    // TODO: delete face code after bulk data implemented?
    private String getFaceImageUrl(CardDownloadData card, boolean isToken) throws Exception {
        final String defaultCode = CardLanguage.ENGLISH.getCode();
        final String localizedCode = languageAliases.getOrDefault(this.getCurrentLanguage(), defaultCode);

        List<String> needUrls = new ArrayList<>();
        int needFaceIndex = card.isSecondSide() ? 1 : 0;

        String apiUrl = ScryfallImageSupportCards.findDirectDownloadLink(card.getSet(), card.getName(), card.getCollectorId());
        if (apiUrl != null) {
            // BY DIRECT URL
            // direct url already contains scryfall compatible card numbers (with unicode chars)
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
            String cn = ScryfallApiCard.transformCardNumberFromXmageToScryfall(card.getCollectorId());
            needUrls.add(String.format("https://api.scryfall.com/cards/%s/%s/%s",
                    formatSetName(card.getSet(), isToken),
                    cn,
                    localizedCode));
            if (!localizedCode.equals(defaultCode)) {
                needUrls.add(String.format("https://api.scryfall.com/cards/%s/%s",
                        formatSetName(card.getSet(), isToken),
                        cn));
            }
        }

        InputStream jsonStream = null;
        String jsonUrl = null;
        for (String currentUrl : needUrls) {
            // find first workable api endpoint
            waitBeforeRequest();

            jsonStream = XmageURLConnection.downloadBinary(currentUrl);
            if (jsonStream != null) {
                // found good url, can stop
                jsonUrl = currentUrl;
                break;
            } else {
                // localized image doesn't exist, try next url
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
        if (jsonFaces.size() < needFaceIndex + 1) {
            throw new MageException("card_faces doesn't contains face index in card's JSON data: " + jsonUrl);
        }

        JsonObject jsonFace = jsonFaces.get(needFaceIndex).getAsJsonObject();
        JsonObject jsonImages = JsonUtil.getAsObject(jsonFace, "image_uris");
        if (jsonImages == null) {
            throw new MageException("Couldn't find image_uris in card's JSON data: " + jsonUrl);
        }

        return JsonUtil.getAsString(jsonImages, "large");
    }

    @Override
    public boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        preparedUrls.clear();

        // direct images without api calls
        if (!prepareBulkData(downloadServiceInfo, downloadList)) {
            return false;
        }
        analyseBulkData(downloadServiceInfo, downloadList);

        // second side images need lookup for
        if (!prepareSecondSideImages(downloadServiceInfo, downloadList)) {
            return false;
        }

        return true;
    }

    private boolean prepareSecondSideImages(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        // prepare stats
        int needPrepareCount = 0;
        int currentPrepareCount = 0;
        for (CardDownloadData card : downloadList) {
            if (card.isSecondSide()) {
                needPrepareCount++;
            }
        }
        updatePrepareStats(downloadServiceInfo, needPrepareCount, currentPrepareCount);

        for (CardDownloadData card : downloadList) {
            // fast cancel
            if (downloadServiceInfo.isNeedCancel()) {
                return false;
            }

            // already prepare, e.g. on bulk data
            if (preparedUrls.containsKey(card)) {
                continue;
            }

            // prepare the back face URL
            if (card.isSecondSide()) {
                currentPrepareCount++;
                try {
                    String url = getFaceImageUrl(card, card.isToken());
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

    private boolean prepareBulkData(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        boolean isEnabled = SCRYFALL_BULK_FILES_ENABLED;
        if (System.getProperty(SCRYFALL_BULK_FILES_PROPERTY) != null) {
            isEnabled = Boolean.parseBoolean(System.getProperty(SCRYFALL_BULK_FILES_PROPERTY));
        }
        if (!isEnabled) {
            return true;
        }

        // if up to date
        if (isBulkDataPrepared(downloadServiceInfo)) {
            return true;
        }

        // NEED TO DOWNLOAD
        if (downloadServiceInfo.isNeedCancel()) {
            return false;
        }

        // clean
        TFile bulkTempFile = prepareTempFileForBulkData();
        if (bulkTempFile.exists()) {
            try {
                if (!SCRYFALL_BULK_FILES_DEBUG_READ_ONLY_MODE) {
                    bulkTempFile.rm();
                    TVFS.umount();
                }
            } catch (IOException e) {
                return false;
            }
        }

        // find actual download link
        String s = XmageURLConnection.downloadText(SCRYFALL_BULK_FILES_DATABASE_SOURCE_API);
        if (s.isEmpty()) {
            logger.error("Can't get bulk info from scryfall api " + SCRYFALL_BULK_FILES_DATABASE_SOURCE_API);
            return false;
        }
        ScryfallApiBulkData bulkData = new Gson().fromJson(s, ScryfallApiBulkData.class);
        if (bulkData == null
                || bulkData.download_uri == null
                || bulkData.download_uri.isEmpty()
                || bulkData.size == 0) {
            logger.error("Unknown bulk info format from scryfall api " + SCRYFALL_BULK_FILES_DATABASE_SOURCE_API);
            return false;
        }

        // download
        if (!SCRYFALL_BULK_FILES_DEBUG_READ_ONLY_MODE) {
            logger.info("Scryfall: downloading additional data files, size " + bulkData.size / (1024 * 1024) + " MB");
            String url = bulkData.download_uri;
            InputStream inputStream = XmageURLConnection.downloadBinary(url, true);
            OutputStream outputStream = null;
            try {
                try {
                    if (inputStream == null) {
                        logger.error("Can't get bulk data from scryfall api " + url);
                        return false;
                    }
                    outputStream = new TFileOutputStream(bulkTempFile);

                    byte[] buf = new byte[5 * 1024 * 1024]; // 5 MB buffer
                    int len;
                    long needDownload = bulkData.size / 7; // text -> zip size multiplier
                    long doneDownload = 0;
                    while ((len = inputStream.read(buf)) != -1) {
                        // fast cancel
                        if (downloadServiceInfo.isNeedCancel()) {
                            // stop download and delete current file
                            inputStream.close();
                            outputStream.close();
                            if (bulkTempFile.exists()) {
                                bulkTempFile.rm();
                            }
                            return false;
                        }

                        // all fine, can save data part
                        outputStream.write(buf, 0, len);

                        doneDownload += len;
                        updateBulkDownloadStats(downloadServiceInfo, doneDownload, needDownload);
                    }
                    outputStream.flush();

                    // unpack
                    logger.info("Scryfall: unpacking files...");
                    Path zippedBulkPath = Paths.get(getBulkTempFileName());
                    Path textBulkPath = Paths.get(getBulkStaticFileName());
                    Files.deleteIfExists(textBulkPath);
                    try (GZIPInputStream in = new GZIPInputStream(Files.newInputStream(zippedBulkPath));
                         FileOutputStream out = new FileOutputStream(textBulkPath.toString())) {
                        byte[] buffer = new byte[5 * 1024 * 1024];
                        long needUnpack = bulkData.size; // zip -> text
                        long doneUnpack = 0;
                        while ((len = in.read(buffer)) > 0) {
                            // fast cancel
                            if (downloadServiceInfo.isNeedCancel()) {
                                out.flush();
                                Files.deleteIfExists(textBulkPath);
                                return false;
                            }

                            out.write(buffer, 0, len);

                            doneUnpack += len;
                            updateBulkUnpackingStats(downloadServiceInfo, doneUnpack, needUnpack);
                        }
                    }
                    logger.info("Scryfall: unpacking done");

                    // all fine
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            } catch (Exception e) {
                logger.error("Catch unknown error while download bulk data from scryfall api " + url, e);
                return false;
            }
        }

        return isBulkDataPrepared(downloadServiceInfo);
    }

    private boolean isBulkDataPrepared(DownloadServiceInfo downloadServiceInfo) {

        // already loaded
        if (bulkCardsDatabaseAll.size() > 0) {
            return true;
        }

        if (downloadServiceInfo.isNeedCancel()) {
            return false;
        }

        // file not exists
        Path textBulkPath = Paths.get(getBulkStaticFileName());
        if (!Files.exists(textBulkPath)) {
            return false;
        }

        // file outdated
        try {
            if (System.currentTimeMillis() - Files.getLastModifiedTime(textBulkPath).toMillis() > SCRYFALL_BULK_FILES_OUTDATE_IN_MILLIS) {
                logger.info("Scryfall: bulk files outdated, need to download new");
                return false;
            }
        } catch (IOException ignore) {
        }

        // bulk files are too big, so must read and parse it in stream mode only
        // 500k reprints in diff languages
        Set<String> usedCards = new HashSet<>();
        Gson gson = new Gson();
        try (TFileInputStream inputStream = new TFileInputStream(textBulkPath.toFile());
             InputStreamReader inputReader = new InputStreamReader(inputStream);
             JsonReader jsonReader = new JsonReader(inputReader)) {

            bulkCardsDatabaseAll.clear();
            bulkCardsDatabaseDefault.clear();

            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                if (downloadServiceInfo.isNeedCancel()) {
                    return false;
                }

                ScryfallApiCard card = gson.fromJson(jsonReader, ScryfallApiCard.class);

                // prepare data
                // memory optimization: fewer data, from 1145 MB to 470 MB
                card.prepareCompatibleData();

                // keep only usefully languages
                // memory optimization: fewer items, from 470 MB to 96 MB
                // make sure final list will have one version
                // example: japan only card must be in final list for any selected languages https://scryfall.com/card/war/180★/
                String key = String.format("%s/%s/%s", card.name, card.set, card.collector_number);
                if (!card.lang.equals(this.currentLanguage.getCode())
                        && !card.lang.equals(CardLanguage.ENGLISH.getCode())
                        && bulkCardsDatabaseDefault.containsKey(key)) {
                    continue;
                }

                // workaround for duplicated lines in bulk data, see https://github.com/magefree/mage/issues/12817
                if (usedCards.contains(card.id)) {
                    logger.warn("WARNING, must report to scryfall about duplicated lines for bulk data: " + key + ", id " + card.id);
                    continue;
                }
                usedCards.add(card.id);

                // default versions list depends on scryfall bulk data order (en first)
                bulkCardsDatabaseDefault.put(key, card);
                bulkCardsDatabaseAll.add(card);
            }
            jsonReader.close();
            return bulkCardsDatabaseAll.size() > 0;
        } catch (Exception e) {
            logger.error("Can't read bulk file (possible reason: broken scryfall format), details: " + e, e);
            try {
                // clean up
                if (!SCRYFALL_BULK_FILES_DEBUG_READ_ONLY_MODE) {
                    Files.deleteIfExists(textBulkPath);
                }
            } catch (IOException ignore) {
            }
        }
        return false;
    }

    private void analyseBulkData(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        // prepare card indexes
        // name/set/number/lang - normal cards
        Map<String, String> bulkImagesIndexAll = createBulkImagesIndex(downloadServiceInfo, bulkCardsDatabaseAll, true);
        // name/set/number - default cards
        Map<String, String> bulkImagesIndexDefault = createBulkImagesIndex(downloadServiceInfo, bulkCardsDatabaseDefault.values(), false);

        // find good images
        AtomicInteger statsOldPrepared = new AtomicInteger();
        AtomicInteger statsDirectLinks = new AtomicInteger();
        AtomicInteger statsNewPrepared = new AtomicInteger();
        AtomicInteger statsNotFound = new AtomicInteger();
        for (CardDownloadData card : downloadList) {
            // fast cancel
            if (downloadServiceInfo.isNeedCancel()) {
                return;
            }

            // already prepared, e.g. from direct download links or other sources
            if (preparedUrls.containsKey(card)) {
                statsOldPrepared.incrementAndGet();
                continue;
            }

            // only cards supported
            if (card.isToken()) {
                continue;
            }

            // ignore direct links
            String directLink = ScryfallImageSupportCards.findDirectDownloadLink(card.getSet(), card.getName(), card.getCollectorId());
            if (directLink != null) {
                statsDirectLinks.incrementAndGet();
                continue;
            }

            String searchingName = card.getName();
            String searchingSet = card.getSet().toLowerCase(Locale.ENGLISH);
            String searchingNumber = card.getCollectorId();

            // current language
            String key = String.format("%s/%s/%s/%s",
                    searchingName,
                    searchingSet,
                    searchingNumber,
                    this.currentLanguage
            );
            String link = bulkImagesIndexAll.getOrDefault(key, "");

            // default en language
            if (link.isEmpty()) {
                key = String.format("%s/%s/%s/%s",
                        searchingName,
                        searchingSet,
                        searchingNumber,
                        CardLanguage.ENGLISH.getCode()
                );
                link = bulkImagesIndexAll.getOrDefault(key, "");
            }

            // default non en-language
            if (link.isEmpty()) {
                key = String.format("%s/%s/%s/",
                        searchingName,
                        searchingSet,
                        searchingNumber);
                link = bulkImagesIndexDefault.getOrDefault(key, "");
            }

            if (link.isEmpty()) {
                // how-to fix:
                // - make sure name notation compatible, see workarounds in ScryfallApiCard.prepareCompatibleData
                // - makue sure it's not fake card (xmage can use fake cards instead doubled-images) -- add it to ScryfallImageSupportCards.directDownloadLinks
                logger.info("Scryfall: bulk image not found (outdated cards data in xmage?): " + key + " ");
                statsNotFound.incrementAndGet();
            } else {
                preparedUrls.put(card, link);
                statsNewPrepared.incrementAndGet();
            }
        }

        logger.info(String.format("Scryfall: bulk optimization result - need cards: %d, already prepared: %d, direct links: %d, new prepared: %d, NOT FOUND: %d",
                downloadList.size(),
                statsOldPrepared.get(),
                statsDirectLinks.get(),
                statsNewPrepared.get(),
                statsNotFound.get() // all not founded cards must be fixed
        ));
    }

    private Map<String, String> createBulkImagesIndex(DownloadServiceInfo downloadServiceInfo, Collection<ScryfallApiCard> sourceList, boolean useLocalization) {
        Map<String, String> res = new HashMap<>();
        for (ScryfallApiCard card : sourceList) {
            // fast cancel
            if (downloadServiceInfo.isNeedCancel()) {
                return res;
            }

            // main card
            String image = card.findImage(getImageQuality());
            if (!image.isEmpty()) {
                String mainKey = String.format("%s/%s/%s/%s",
                        card.name,
                        card.set,
                        card.collector_number,
                        useLocalization ? card.lang : ""
                );
                if (res.containsKey(mainKey)) {
                    // how-to fix: rework whole card number logic in xmage and scryfall
                    throw new IllegalArgumentException("Wrong code usage: scryfall used unique card numbers, but found duplicated: " + mainKey);
                }
                res.put(mainKey, image);
            }

            // faces
            if (card.card_faces != null) {
                // hints:
                // 1. Not all faces has images - example: adventure cards
                // 2. Some faces contain fake data in second face, search it on scryfall by "set_type:memorabilia"
                //   example: https://scryfall.com/card/aznr/25/clearwater-pathway-clearwater-pathway
                // 3. Some faces contain diff images of the same card, layout = reversible_card
                //   example: https://scryfall.com/card/rex/26/command-tower-command-tower

                Set<String> usedNames = new HashSet<>();
                card.card_faces.forEach(face -> {
                    // workaround to ignore fake data, see above about memorabilia
                    if (usedNames.contains(face.name)) {
                        return;
                    }
                    usedNames.add(face.name);

                    String faceImage = face.findImage(getImageQuality());
                    if (!faceImage.isEmpty()) {
                        String faceKey = String.format("%s/%s/%s/%s",
                                face.name,
                                card.set,
                                card.collector_number,
                                useLocalization ? card.lang : ""
                        );

                        // workaround for flip cards - ignore first face - it already in the index
                        // example: https://scryfall.com/card/sok/103/homura-human-ascendant-homuras-essence
                        if (card.layout.equals("flip")
                                && card.name.equals(face.name)
                                && res.containsKey(faceKey)) {
                            return;
                        }

                        if (res.containsKey(faceKey)) {
                            // how-to fix: rework whole card number logic in xmage and scryfall
                            throw new IllegalArgumentException("Wrong code usage: scryfall used unique card numbers, but found duplicated: " + faceKey);
                        }
                        res.put(faceKey, faceImage);
                    }
                });
            }
        }

        return res;
    }

    private String getBulkTempFileName() {
        return getImagesDir() + File.separator + "downloading" + File.separator + "scryfall_bulk_cards.json.gz";
    }

    private String getBulkStaticFileName() {
        return getImagesDir() + File.separator + "downloading" + File.separator + "scryfall_bulk_cards.json";
    }

    private TFile prepareTempFileForBulkData() {
        TFile file = new TFile(getBulkTempFileName());
        TFile parent = file.getParentFile();
        if (parent != null) {
            if (!parent.exists()) {
                parent.mkdirs();
            }
        }
        return file;
    }

    private void updateBulkDownloadStats(DownloadServiceInfo service, long done, long need) {
        int doneMb = (int) (done / (1024 * 1024));
        int needMb = (int) (need / (1024 * 1024));
        synchronized (service.getSync()) {
            service.updateProgressMessage(
                    String.format("Step 1 of 3. Downloading additional data... %d of %d MB", doneMb, needMb),
                    doneMb,
                    needMb
            );
        }
    }

    private void updateBulkUnpackingStats(DownloadServiceInfo service, long done, long need) {
        int doneMb = (int) (done / (1024 * 1024));
        int needMb = (int) (need / (1024 * 1024));
        synchronized (service.getSync()) {
            service.updateProgressMessage(
                    String.format("Step 2 of 3. Unpacking additional files... %d of %d MB", doneMb, needMb),
                    doneMb,
                    needMb
            );
        }
    }

    private void updatePrepareStats(DownloadServiceInfo service, int need, int current) {
        synchronized (service.getSync()) {
            service.updateProgressMessage(
                    String.format("Step 3 of 3. Preparing download list... %d of %d", current, need),
                    current,
                    need
            );
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
        return "scryfall.com - big";
    }

    @Override
    public float getAverageSizeKb() {
        // June 2024: MH3 set - 46450 Kb / 332 = 140 Kb
        return 140f;
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
    public void doPause(String fullUrl) {
        // scryfall recommends 300 ms timeout per each request to API to work under a rate limit
        // possible error: 429 Too Many Requests

        // cdn source can be safe and called without pause
        if (fullUrl.contains("scryfall.io")) {
            return;
        }

        waitBeforeRequest();
    }

    private void waitBeforeRequest() {
        try {
            // single wait queue for all threads - it's guarantee min timeout before each api call
            waitBeforeRequestLock.lock();
            try {
                Thread.sleep(DOWNLOAD_TIMEOUT_MS);
            } finally {
                waitBeforeRequestLock.unlock();
            }
        } catch (InterruptedException ignore) {
        }
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

    /**
     * Image quality
     */
    public String getImageQuality() {
        return "large";
    }

    @Override
    public void onFinished() {
        // cleanup resources
        bulkCardsDatabaseAll.clear();
        bulkCardsDatabaseDefault.clear();
    }
}
