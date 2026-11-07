package org.mage.plugins.card.images;

import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardInfo;
import mage.client.dialog.PreferencesDialog;
import mage.client.remote.XmageURLConnection;
import mage.client.util.GUISizeHelper;
import mage.constants.CardType;
import mage.constants.Rarity;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;
import net.java.truevfs.access.TFileOutputStream;
import net.java.truevfs.access.TVFS;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.dl.sources.CardImageSource;
import org.mage.plugins.card.dl.sources.CardImageUrls;
import org.mage.plugins.card.utils.CardImageUtils;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Images download: images download, save and GUI refresh tests without real network usage
 * <p>
 * all downloaded images must be saved and GUI must be refreshed one time after it
 * <p>
 * Features:
 * - use fake network and isolated app's preferences (settings cache only, user's settings untouched) to simulate real downloads and saves;
 * - tests whole images lifecycle (check -> queue -> download -> save -> finish and refresh)
 * - tests multithreads, thread safe downloads and saves, and data consistence;
 * - tests GUI update after download (one time and after the last image; EDT usage is a separate task, see PR #16497);
 * - tests different storages:
 * ---- jpg inside zip files;
 * ---- jpg inside folders;
 * ---- TODO: webp inside folders;
 * - tests different problems:
 * ---- broken archives (on check and on save);
 * ---- broken or zero size files;
 * ---- broken url, broken website and non-image results, blocked network (as a fake network fail, see TestDownloadService);
 * - tests different images:
 * ----- cards;
 * ----- tokens;
 * - tests cancel actions;
 * - tests finish and cleanup files;
 * - tests re-downloads and forced re-download;
 * - tests download and miss stats;
 * - tests max errors stops;
 * - tests http errors: failed images (404, 5xx, 403, no response) stop the download after max fails in a row only
 * ---- (any good image resets it, so a broken set or many missing images can't stop the whole download);
 * - tests rate limit (HTTP 429): the download stops at once, no more requests (alternative urls too), clear message, next start works;
 * - tests read only storage (e.g. Program Files on Windows);
 * - tests archive changed by another program while download runs;
 * - tests images reading by a game while download runs (no errors, no locks, periodic archives save);
 * - TODO: tests double faced cards (second side added two times, need cards database);
 * - tests dialog re-open while a cancelled download is finishing (lists reload waits the finish, cancel is not reset);
 * - tests errors on download finish (dialog's controls must be enabled after any errors);
 *
 * @author JayDi85
 */
public class DownloadPicturesServiceTest {

    private static final Logger logger = Logger.getLogger(DownloadPicturesServiceTest.class);

    private static final int IMAGE_SIZE_BYTES = 10 * 1024; // must be bigger than a broken image size limit
    private static final String[] SET_CODES = {"TS1", "TS2", "TS3", "TS4", "TS5"};
    private static final int DOWNLOAD_THREADS = 10;

    private File imagesDir;
    private final Map<String, String> oldSettings = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        imagesDir = Files.createTempDirectory("xmage-images-test").toFile();

        // use own images folder (settings cache only, real user's settings are untouched)
        setCachedSetting(PreferencesDialog.KEY_CARD_IMAGES_USE_DEFAULT, "false");
        setCachedSetting(PreferencesDialog.KEY_CARD_IMAGES_PATH, imagesDir.getAbsolutePath());
        setCachedSetting(PreferencesDialog.KEY_CARD_IMAGES_SAVE_TO_ZIP, "true");
    }

    @After
    public void tearDown() throws Exception {
        TVFS.umount();
        for (Map.Entry<String, String> entry : oldSettings.entrySet()) {
            setCachedSetting(entry.getKey(), entry.getValue());
        }
        GUISizeHelper.calculateGUISizes(); // restore fonts
        deleteFolder(imagesDir);
    }

    @Test
    public void test_ZipMode_AllImagesSavedAfterLongDownload() throws Exception {
        // old code closes zip files 30 secs after download start, so a long download must lose nothing
        int imagesPerSet = 70; // 350 images with 1 sec each on 10 threads = 35 secs

        // a game reads images while download runs (old image from a previous download, new images, missing images):
        // must work without errors and must find saved images
        writeImageFile("OLD", "Old card", IMAGE_SIZE_BYTES);
        File zipFile = new File(imagesDir, "TS1.zip");
        long zipSizeBefore = zipFile.length();
        List<String> readErrors = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger foundNewImages = new AtomicInteger();
        AtomicInteger zipSaves = new AtomicInteger();
        Thread reader = new Thread(() -> {
            long lastSize = zipSizeBefore;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Assert.assertEquals("old image must be readable", IMAGE_SIZE_BYTES, readImageFile("OLD", "Old card"));
                    Assert.assertEquals("missing image must be missing", -1, readImageFile("TS1", "Unknown card"));
                    if (readImageFile("TS1", "Card 5") == IMAGE_SIZE_BYTES) {
                        foundNewImages.incrementAndGet();
                    }
                    if (zipFile.length() != lastSize) {
                        lastSize = zipFile.length();
                        zipSaves.incrementAndGet();
                    }
                    sleep(200);
                } catch (Throwable e) {
                    readErrors.add(e.toString());
                }
            }
        }, "test-images-reader");
        reader.start();
        TestDownloadService service;
        try {
            service = runDownload(cards(imagesPerSet), 1000);
        } finally {
            reader.interrupt();
            reader.join();
        }

        assertSavedImages(imagesPerSet);
        assertNoTempFiles();
        assertRefreshedOnceAfterDownload(service);
        assertMissing(service, 0);
        Assert.assertEquals("images reading while download must work: " + readErrors, 0, readErrors.size());
        Assert.assertTrue("new image must be readable while download", foundNewImages.get() > 0);
        Assert.assertTrue("archives must be saved periodically while download (30 secs), saves: " + zipSaves.get(), zipSaves.get() >= 2);
        assertLogContains(service, "archives saved (periodic)");
    }

    @Test
    public void test_ReadOnlyStorage_NoDownloadAndClearError() throws Exception {
        // images folder is a file, so nothing can be created there (like a Program Files folder without write access)
        File fileInsteadOfFolder = new File(imagesDir, "read-only-images");
        Assert.assertTrue(fileInsteadOfFolder.createNewFile());
        setCachedSetting(PreferencesDialog.KEY_CARD_IMAGES_PATH, fileInsteadOfFolder.getAbsolutePath());

        TestDownloadService service = new TestDownloadService(10);
        service.prepareDownloadForTests(new TestImageSource(), cards(10), DOWNLOAD_THREADS);
        service.run();

        Assert.assertEquals("nothing must be downloaded", 0, service.finishedDownloads.get() + service.failedDownloads.get());
        Assert.assertTrue("user must see the storage error: " + service.getGlobalMessageForTests(),
                service.getGlobalMessageForTests().startsWith("Images folder is not writable"));
        Assert.assertTrue("storage is untouched", fileInsteadOfFolder.isFile());
    }

    @Test
    public void test_ArchiveReplacedByAnotherProgramWhileDownload() throws Exception {
        // another program saved a new file with the same name (new inode) while download runs:
        // download must finish fine, the archive must be readable (the other program's changes are lost)
        int imagesPerSet = 10;
        runDownload(cards(imagesPerSet), 10);
        File zipFile = new File(imagesDir, "TS1.zip");
        List<CardDownloadData> moreCards = cards("TS1", imagesPerSet, imagesPerSet * 2);

        TestDownloadService service = new TestDownloadService(10);
        service.onDownload = card -> {
            if (card.getCollectorId().equals("15")) {
                sleep(500); // other download threads must save their images into the mounted archive first
                try {
                    File other = writeOtherZip();
                    Files.move(other.toPath(), zipFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
        service.prepareDownloadForTests(new TestImageSource(), moreCards, DOWNLOAD_THREADS);
        service.run();

        Assert.assertEquals(moreCards.size(), service.finishedDownloads.get());
        Assert.assertTrue("archive must be readable", countZipEntries(zipFile, "TS1/") >= imagesPerSet);
        assertNoTempFiles();
    }

    @Test
    public void test_ArchiveRewrittenByAnotherProgramWhileDownload() throws Exception {
        // another program rewrote the file in place (same file, other bytes) while download runs:
        // archive can't be saved (broken), so it must be deleted and the next download restores the set
        int imagesPerSet = 10;
        runDownload(cards(imagesPerSet), 10);
        File zipFile = new File(imagesDir, "TS1.zip");
        List<CardDownloadData> moreCards = cards("TS1", imagesPerSet, imagesPerSet * 2);

        TestDownloadService service = new TestDownloadService(10);
        service.onDownload = card -> {
            if (card.getCollectorId().equals("15")) {
                sleep(500); // other download threads must save their images into the mounted archive first
                try {
                    File other = writeOtherZip();
                    try (OutputStream out = new java.io.FileOutputStream(zipFile, false)) { // same file, new bytes
                        Files.copy(other.toPath(), out);
                    }
                    Files.delete(other.toPath());
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
        service.prepareDownloadForTests(new TestImageSource(), moreCards, DOWNLOAD_THREADS);
        service.run();

        Assert.assertEquals(moreCards.size(), service.finishedDownloads.get());
        Assert.assertFalse("broken archive must be deleted", zipFile.exists());
        assertLogContains(service, "PROBLEM: deleted broken archive");
        assertNoTempFiles();

        TestDownloadService restore = runDownload(cards("TS1", 0, imagesPerSet * 2), 10);
        Assert.assertEquals(imagesPerSet * 2, countZipEntries(zipFile, "TS1/"));
        assertMissing(restore, 0);
    }

    @Test
    public void test_GuiRefresh_RunsOnceInSwingThreadAfterDownload() throws Exception {
        // the app runs the service in a separate thread, but GUI changes are allowed in the Swing thread only
        // (random NPE in layouts like BasicScrollBarUI.layoutHScrollbar, see #13503)
        int imagesPerSet = 10;

        TestDownloadService service = runDownload(cards(imagesPerSet), 10, true);

        assertRefreshedOnceAfterDownload(service);
        SwingUtilities.invokeAndWait(() -> Assert.assertTrue("dialog's start button must be enabled after download",
                service.isStartButtonEnabledForTests()));
        assertSavedImages(imagesPerSet);
        assertMissing(service, 0);
    }

    @Test
    public void test_FolderMode_AllImagesSaved() throws Exception {
        setCachedSetting(PreferencesDialog.KEY_CARD_IMAGES_SAVE_TO_ZIP, "false");
        int imagesPerSet = 20;

        TestDownloadService service = runDownload(cards(imagesPerSet), 10);

        for (String setCode : SET_CODES) {
            File setDir = new File(imagesDir, setCode);
            String[] files = setDir.list();
            Assert.assertNotNull("set folder must exist: " + setDir, files);
            Assert.assertEquals("all images must be saved in " + setCode, imagesPerSet, files.length);
        }
        assertNoTempFiles();
        assertRefreshedOnceAfterDownload(service);
        assertMissing(service, 0);
    }

    @Test
    public void test_FinishProgress_ArchivesSaveAndStatsShown() throws Exception {
        // finish steps must show a progress instead of a single "please wait" (see issue #14014)
        int imagesPerSet = 10;
        List<CardDownloadData> allCards = cards(imagesPerSet);

        TestDownloadService service = runDownload(allCards, 10);

        // archives save: one archive per set, saved one by one
        int archives = SET_CODES.length;
        assertProgressMessage(service, "saving archives... 0 of " + archives, 0, archives);
        assertProgressMessage(service, "saving archives... " + (archives - 1) + " of " + archives, archives - 1, archives);
        // stats: from 0 to all missing images
        assertProgressMessage(service, "refreshing stats... 0 of " + allCards.size(), 0, allCards.size());
        assertProgressMessage(service, "refreshing stats... " + allCards.size() + " of " + allCards.size(), allCards.size(), allCards.size());
        assertSavedImages(imagesPerSet);
        assertMissing(service, 0);
    }

    @Test
    public void test_Tokens_SavedInTokensArchive() throws Exception {
        int imagesPerSet = 10;
        List<CardDownloadData> tokens = new ArrayList<>();
        for (String setCode : SET_CODES) {
            for (int i = 0; i < imagesPerSet; i++) {
                CardDownloadData token = new CardDownloadData("Token " + i, setCode, "0", false, i + 1);
                token.setToken(true);
                tokens.add(token);
            }
        }

        TestDownloadService service = runDownload(tokens, 10);

        File tokensZip = new File(imagesDir, "TOK.zip");
        Assert.assertTrue("tokens zip must exist", tokensZip.exists());
        for (String setCode : SET_CODES) {
            Assert.assertEquals("all tokens must be saved in TOK.zip/" + setCode, imagesPerSet, countZipEntries(tokensZip, setCode + "/"));
        }
        assertNoTempFiles();
        assertMissing(service, 0);
    }

    @Test
    public void test_Cancel_SavedImagesKeptAndThreadsFinished() throws Exception {
        int imagesPerSet = 20; // 100 images with 500 ms each on 10 threads = 5 secs
        TestDownloadService service = new TestDownloadService(500);
        service.prepareDownloadForTests(new TestImageSource(), cards(imagesPerSet), DOWNLOAD_THREADS);

        // cancel in the middle of the download (like a user's stop button)
        Thread canceller = new Thread(() -> {
            sleep(1500);
            service.setNeedCancel(true);
        });
        canceller.start();
        service.run();
        canceller.join();

        Assert.assertTrue("download must be stopped before the end", service.finishedDownloads.get() < imagesPerSet * SET_CODES.length);
        Assert.assertEquals("download threads must be finished before run() ends", 0, service.activeDownloads.get());
        int saved = 0;
        for (String setCode : SET_CODES) {
            File zipFile = new File(imagesDir, setCode + ".zip");
            if (zipFile.exists()) {
                saved += countZipEntries(zipFile, setCode + "/");
            }
        }
        Assert.assertTrue("downloaded images must be saved on cancel", saved > 0);
        Assert.assertEquals("missing stats must be refreshed on cancel", imagesPerSet * SET_CODES.length - saved, service.getCardsMissingForTests().size());
        assertNoTempFiles();
    }

    @Test
    public void test_DialogReopen_WhileCancelledDownloadFinishing() throws Exception {
        // cancel/close doesn't wait the download threads, so a user can open the dialog again while the
        // cancelled download is finishing (in-flight images, archives save, stats); the dialog must not
        // reset the cancel and must not reload the lists in that time (NPE in stats and disabled dialog's controls)
        int imagesPerSet = 20; // 100 images with 500 ms each on 10 threads = 5 secs
        TestDownloadService service = new TestDownloadService(500);
        service.prepareDownloadForTests(new TestImageSource(), cards(imagesPerSet), DOWNLOAD_THREADS);

        Thread downloadThread = new Thread(service, "test-images-download");
        downloadThread.start();
        sleep(1500);

        // close and open the dialog again (cancel, then open while the in-flight images are still downloading)
        service.setNeedCancel(true);
        SwingUtilities.invokeAndWait(() -> {
            Assert.assertFalse("dialog open must not load lists while the previous download is finishing", service.prepareDialogOpen());
            Assert.assertTrue("dialog open must not reset the cancel of the previous download", service.isNeedCancel());
            Assert.assertTrue("dialog must show a wait message, but found: " + service.getGlobalMessageForTests(),
                    service.getGlobalMessageForTests().contains("Stopping the previous download"));
        });
        Assert.assertEquals("missing cards must not be loaded while the download is finishing", 0, service.loadMissingCalls.get());

        downloadThread.join(TimeUnit.MINUTES.toMillis(1));
        Assert.assertFalse("download thread must be finished", downloadThread.isAlive());
        SwingUtilities.invokeAndWait(() -> {
        }); // workaround to wait GUI refresh

        Assert.assertTrue("cancelled download must be stopped before the end", service.finishedDownloads.get() < imagesPerSet * SET_CODES.length);
        Assert.assertEquals("missing cards must be loaded one time after the download finished", 1, service.loadMissingCalls.get());
        Assert.assertFalse("new dialog must be ready for a new download", service.isNeedCancel());
        SwingUtilities.invokeAndWait(() -> {
            Assert.assertTrue("dialog's start button must be enabled after download", service.isStartButtonEnabledForTests());
            Assert.assertTrue("next dialog open must load lists at once", service.prepareDialogOpen());
        });
    }

    @Test
    public void test_FinishErrors_DialogControlsEnabled() throws Exception {
        // any errors on download finish (stats, GUI refresh) must not keep the dialog in a disabled state
        int imagesPerSet = 2;
        TestDownloadService service = new TestDownloadService(10);
        service.prepareDownloadForTests(new TestImageSource(), cards(imagesPerSet), DOWNLOAD_THREADS);
        service.getCardsMissingForTests().add(null); // broken data, raise NPE in stats and GUI refresh

        service.run(); // must not fail
        SwingUtilities.invokeAndWait(() -> {
        }); // workaround to wait GUI refresh

        Assert.assertEquals("all images must be downloaded", imagesPerSet * SET_CODES.length, service.finishedDownloads.get());
        assertSavedImages(imagesPerSet);
        assertLogContains(service, "can't finish download");
        SwingUtilities.invokeAndWait(() -> {
            Assert.assertTrue("dialog's start button must be enabled after finish errors", service.isStartButtonEnabledForTests());
            Assert.assertTrue("next dialog open must load lists at once", service.prepareDialogOpen());
        });
    }

    @Test
    public void test_RateLimit_StopsDownloadAtOnce() throws Exception {
        // server's rate limit (HTTP 429, example: scryfall api) blocks all next requests for some time,
        // so download must stop on first 429 - new requests are useless and can lead to a ban
        int imagesPerSet = 100; // 500 images
        String limitedUrl = "http://test.local/TS2/10"; // first 429 in the middle of the queue
        TestDownloadService service = new TestDownloadService(0);
        service.httpCodes = url -> {
            // like a real server: after the first 429 all requests are blocked
            if (url.equals(limitedUrl) || service.httpRequests.contains(limitedUrl)) {
                return XmageURLConnection.HTTP_TOO_MANY_REQUESTS;
            }
            return 200;
        };
        service.prepareDownloadForTests(new TestImageSource(true), cards(imagesPerSet), DOWNLOAD_THREADS);

        service.run();

        List<String> requests = new ArrayList<>(service.httpRequests);
        int limitedIndex = requests.indexOf(limitedUrl);
        Assert.assertTrue("rate limited url must be requested", limitedIndex >= 0);
        int requestsAfterLimit = requests.size() - limitedIndex - 1;
        Assert.assertTrue("download must stop at once on rate limit, but found " + requestsAfterLimit + " requests after it",
                requestsAfterLimit <= DOWNLOAD_THREADS);
        Assert.assertFalse("alternative url must not be requested after rate limit",
                requests.contains(limitedUrl + TestImageSource.ALTERNATIVE_URL_SUFFIX));
        Assert.assertEquals("download threads must be finished before run() ends", 0, service.activeDownloads.get());
        Assert.assertTrue("clear message about rate limit must be shown: " + service.getGlobalMessageForTests(),
                service.getGlobalMessageForTests().contains("HTTP 429"));
        assertLogContains(service, "rate limit (HTTP 429)");
        // in-flight images can be cancelled while saving, so stats must be the same as saved files
        int savedImages = 0;
        for (String setCode : SET_CODES) {
            File zipFile = new File(imagesDir, setCode + ".zip");
            savedImages += zipFile.exists() ? countZipEntries(zipFile, setCode + "/") : 0;
        }
        Assert.assertTrue("images before rate limit must be saved", savedImages > 0);
        assertMissing(service, imagesPerSet * SET_CODES.length - savedImages);
        assertNoTempFiles();

        // next start (same dialog) must work and download all missing images
        int missingBefore = service.getCardsMissingForTests().size();
        service.httpRequests.clear();
        service.httpCodes = url -> 200;
        service.setNeedCancel(false); // start button
        service.prepareDownloadForTests(new TestImageSource(true), new ArrayList<>(service.getCardsMissingForTests()), DOWNLOAD_THREADS);
        service.run();
        Assert.assertEquals("next start must download all missing images", missingBefore, service.httpRequests.size());
        Assert.assertEquals("rate limit message must be cleared on next start", "", service.getGlobalMessageForTests());
        assertMissing(service, 0);
        assertSavedImages(imagesPerSet);
    }

    @Test
    public void test_HttpErrors_TooManyFailsInRowStopDownload() throws Exception {
        // dead or blocked server: all images fail, download must stop after max fails in a row (not run through the whole queue)
        int imagesPerSet = 100; // 500 images
        for (int code : new int[]{HttpURLConnection.HTTP_NOT_FOUND, HttpURLConnection.HTTP_UNAVAILABLE, HttpURLConnection.HTTP_FORBIDDEN, -1}) {
            TestDownloadService service = new TestDownloadService(0);
            service.httpCodes = url -> code;
            service.prepareDownloadForTests(new TestImageSource(true), cards(imagesPerSet), DOWNLOAD_THREADS);

            service.run();

            int triedImages = service.httpRequests.size() / 2; // main and alternative urls
            Assert.assertTrue("download must stop on too many fails in a row " + code + ", tried images: " + triedImages,
                    triedImages > 300 && triedImages < imagesPerSet * SET_CODES.length);
            Assert.assertTrue("must stop by max fails", service.isNeedCancel());
            Assert.assertEquals("download threads must be finished before run() ends", 0, service.activeDownloads.get());
            assertMissing(service, imagesPerSet * SET_CODES.length);
            assertLogContains(service, "http code: " + code);
        }
    }

    @Test
    public void test_HttpErrors_BrokenSetAndManyMissingImagesDoNotStopDownload() throws Exception {
        // a whole broken set (all images missing) and many missing images in other sets, but with good images between
        // them - download must try the whole list
        int imagesPerSet = 100; // 500 images
        TestDownloadService service = new TestDownloadService(0);
        service.httpCodes = url -> {
            if (url.contains("/TS2/")) {
                return HttpURLConnection.HTTP_NOT_FOUND; // broken set
            }
            int number = Integer.parseInt(url.replaceAll(".*/(\\d+)(\\?.*)?$", "$1"));
            return number % 5 == 0 ? 200 : HttpURLConnection.HTTP_UNAVAILABLE; // 80% of images fail
        };
        service.prepareDownloadForTests(new TestImageSource(true), cards(imagesPerSet), DOWNLOAD_THREADS);

        service.run();

        Assert.assertFalse("fails with good images between them must not stop the download", service.isNeedCancel());
        Assert.assertEquals("all images and alternative urls must be tried",
                imagesPerSet * SET_CODES.length * 2 - 80, service.httpRequests.size()); // good images use main url only
        assertMissing(service, imagesPerSet + 4 * 80); // broken set + 80% of other sets
    }

    @Test
    public void test_MaxErrors_DownloadStops() throws Exception {
        int imagesPerSet = 100; // 500 images, download must stop after 50 errors
        TestDownloadService service = new TestDownloadService(10);
        service.failAll = true;
        service.prepareDownloadForTests(new TestImageSource(), cards(imagesPerSet), DOWNLOAD_THREADS);

        service.run();

        Assert.assertTrue("download must stop on too many errors (fails: " + service.failedDownloads.get() + ")",
                service.failedDownloads.get() < imagesPerSet * SET_CODES.length);
        Assert.assertEquals("download threads must be finished before run() ends", 0, service.activeDownloads.get());
        assertMissing(service, imagesPerSet * SET_CODES.length);
        assertNoTempFiles();
        assertLogContains(service, "FAILED: ");
    }

    @Test
    public void test_Stats_FailedImagesStayMissing() throws Exception {
        int imagesPerSet = 10;
        TestDownloadService service = new TestDownloadService(10);
        service.failFilter = card -> card.getCollectorId().equals("3"); // one fail per set
        service.prepareDownloadForTests(new TestImageSource(), cards(imagesPerSet), DOWNLOAD_THREADS);

        service.run();

        assertSavedImages(imagesPerSet - 1);
        assertMissing(service, SET_CODES.length);
        Assert.assertTrue("missing list must keep failed images only",
                service.getCardsMissingForTests().stream().allMatch(card -> card.getCollectorId().equals("3")));
        assertLogContains(service, "FAILED: TS1 - Card 2");
    }

    @Test
    public void test_ReDownload_NewImagesAddedToExistingArchives() throws Exception {
        int imagesPerSet = 10;
        runDownload(cards(imagesPerSet), 10);
        assertSavedImages(imagesPerSet);

        // second download of other images must keep the old ones
        List<CardDownloadData> moreCards = new ArrayList<>();
        for (String setCode : SET_CODES) {
            for (int i = imagesPerSet; i < imagesPerSet * 2; i++) {
                moreCards.add(new CardDownloadData("Card " + i, setCode, String.valueOf(i + 1), false, 0));
            }
        }
        TestDownloadService service = runDownload(moreCards, 10);

        assertSavedImages(imagesPerSet * 2);
        assertNoTempFiles();
        assertMissing(service, 0);
    }

    @Test
    public void test_Check_MissingAndBrokenFilesFoundOnly() throws Exception {
        // check step: existing good images are skipped, missing and broken images (small files) are queued
        List<CardInfo> cards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            cards.add(new CardInfo(new TestCard("Card " + i, "TS1", String.valueOf(i + 1))));
        }
        writeImageFile("TS1", "Card 0", IMAGE_SIZE_BYTES); // good
        writeImageFile("TS1", "Card 1", 100); // broken (too small)
        // Card 2 and Card 3 are missing

        // (all tokens from the tokens repository are always in the list, so check cards only)
        List<CardDownloadData> missing = DownloadPicturesService.prepareMissingCards(cards, false);
        List<String> missingNames = missing.stream().filter(card -> !card.isToken()).map(CardDownloadData::getName).sorted().collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("Card 1", "Card 2", "Card 3"), missingNames);

        // forced re-download: all cards
        List<CardDownloadData> all = DownloadPicturesService.prepareMissingCards(cards, true);
        Assert.assertEquals(4, all.stream().filter(card -> !card.isToken()).count());
    }

    @Test
    public void test_BrokenArchive_DeletedOnCheck() throws Exception {
        // not a zip file at all
        File brokenZip = new File(imagesDir, "TS1.zip");
        Files.write(brokenZip.toPath(), "garbage".getBytes(StandardCharsets.UTF_8));
        // zero size archive
        File emptyZip = new File(imagesDir, "TS2.zip");
        Assert.assertTrue(emptyZip.createNewFile());

        List<String> problems = CardImageUtils.checkAndFixImageFiles();

        Assert.assertFalse("broken archive must be deleted", brokenZip.exists());
        Assert.assertFalse("empty archive must be deleted", emptyZip.exists());
        Assert.assertEquals("must report 2 problems: " + problems, 2, problems.size());
    }

    @Test
    public void test_BrokenArchive_DeletedOnSaveFail() throws Exception {
        // archive with a broken image entry: zip structure is readable, but any save of new images fails
        int imagesPerSet = 10;
        runDownload(cards(imagesPerSet), 10);
        File zipFile = new File(imagesDir, "TS1.zip");
        damageZipEntry(zipFile, "TS1/Card 5.full.jpg");
        Assert.assertEquals("damaged archive must be readable", imagesPerSet, countZipEntries(zipFile, "TS1/"));

        // new download to the damaged archive
        List<CardDownloadData> moreCards = new ArrayList<>();
        for (int i = imagesPerSet; i < imagesPerSet * 2; i++) {
            moreCards.add(new CardDownloadData("Card " + i, "TS1", String.valueOf(i + 1), false, 0));
        }
        TestDownloadService service = runDownload(moreCards, 10);

        Assert.assertFalse("broken archive must be deleted on save fail", zipFile.exists());
        assertNoTempFiles();
        assertLogContains(service, "PROBLEM: deleted broken archive");

        // next download restores the whole set to a new archive
        List<CardDownloadData> allCards = cards(imagesPerSet * 2).stream().filter(card -> card.getSet().equals("TS1")).collect(Collectors.toList());
        runDownload(allCards, 10);
        Assert.assertEquals(imagesPerSet * 2, countZipEntries(zipFile, "TS1/"));
    }

    private List<CardDownloadData> cards(String setCode, int from, int to) {
        List<CardDownloadData> missingCards = new ArrayList<>();
        for (int i = from; i < to; i++) {
            missingCards.add(new CardDownloadData("Card " + i, setCode, String.valueOf(i + 1), false, 0));
        }
        return missingCards;
    }

    private List<CardDownloadData> cards(int imagesPerSet) {
        List<CardDownloadData> missingCards = new ArrayList<>();
        for (String setCode : SET_CODES) {
            for (int i = 0; i < imagesPerSet; i++) {
                missingCards.add(new CardDownloadData("Card " + i, setCode, String.valueOf(i + 1), false, 0));
            }
        }
        return missingCards;
    }

    private TestDownloadService runDownload(List<CardDownloadData> missingCards, int imageDownloadMillis) throws Exception {
        return runDownload(missingCards, imageDownloadMillis, false);
    }

    /**
     * @param inDownloadThread true - run the service in a separate thread like the app's start button does
     *                         (false - run in the current thread, same result)
     */
    private TestDownloadService runDownload(List<CardDownloadData> missingCards, int imageDownloadMillis, boolean inDownloadThread) throws Exception {
        TestDownloadService service = new TestDownloadService(imageDownloadMillis);
        service.prepareDownloadForTests(new TestImageSource(), missingCards, DOWNLOAD_THREADS);

        // GUI refresh (reset fonts) must be one time, after the last image only and in the Swing thread
        // (new font size, so a refresh will change the fonts and raise an event)
        setCachedSetting(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(GUISizeHelper.dialogFont.getSize() + 1));
        PropertyChangeListener refreshListener = event -> {
            if ("InternalFrame.titleFont".equals(event.getPropertyName())) {
                service.refreshCalls.incrementAndGet();
                service.downloadsOnRefresh.set(service.finishedDownloads.get());
                if (!SwingUtilities.isEventDispatchThread()) {
                    service.refreshOutsideSwingThread.set(true);
                }
            }
        };
        UIManager.getDefaults().addPropertyChangeListener(refreshListener);
        try {
            if (inDownloadThread) {
                Thread downloadThread = new Thread(service, "test-images-download");
                downloadThread.start();
                downloadThread.join(TimeUnit.MINUTES.toMillis(5));
                Assert.assertFalse("download thread must be finished", downloadThread.isAlive());
            } else {
                service.run(); // same thread, returns after a full download process
            }
            // workaround to wait GUI refresh
            SwingUtilities.invokeAndWait(() -> {
            });
        } finally {
            UIManager.getDefaults().removePropertyChangeListener(refreshListener);
        }

        Assert.assertEquals("all images must be downloaded", missingCards.size(), service.finishedDownloads.get());
        Assert.assertEquals("download threads must be finished before run() ends", 0, service.activeDownloads.get());
        return service;
    }

    private void assertSavedImages(int imagesPerSet) throws IOException {
        for (String setCode : SET_CODES) {
            File zipFile = new File(imagesDir, setCode + ".zip");
            Assert.assertTrue("zip file must exist: " + zipFile, zipFile.exists());
            Assert.assertEquals("all images must be saved in " + zipFile.getName(), imagesPerSet, countZipEntries(zipFile, setCode + "/"));
        }
    }

    private void assertRefreshedOnceAfterDownload(TestDownloadService service) {
        Assert.assertEquals("GUI must be refreshed one time", 1, service.refreshCalls.get());
        Assert.assertEquals("GUI must be refreshed after the last image", service.finishedDownloads.get(), service.downloadsOnRefresh.get());
        Assert.assertFalse("GUI must be refreshed in the Swing thread only (see #13503)", service.refreshOutsideSwingThread.get());
    }

    private void assertProgressMessage(TestDownloadService service, String text, int progressCurrent, int progressNeed) {
        String need = String.format("%s [%d/%d]", text, progressCurrent, progressNeed);
        Assert.assertTrue("progress message must be shown: " + need + ", but found: " + service.progressMessages,
                service.progressMessages.stream().anyMatch(m -> m.contains(need)));
    }

    private void assertMissing(TestDownloadService service, int expectedMissing) {
        Assert.assertEquals("missing images stats after download", expectedMissing, service.getCardsMissingForTests().size());
    }

    private void assertNoTempFiles() {
        List<String> leftovers = new ArrayList<>();
        for (File file : Objects.requireNonNull(imagesDir.listFiles())) {
            if (file.isDirectory()) {
                if (file.getName().equals("downloading")) {
                    for (String inner : Objects.requireNonNull(file.list())) {
                        if (!inner.equals(DownloadLog.LOG_FILE_NAME)) {
                            leftovers.add(file.getName() + "/" + inner);
                        }
                    }
                }
            } else if (!file.getName().endsWith(".zip")) {
                leftovers.add(file.getName()); // e.g. SET.zip.NNN.tmp (TrueVFS's buffer of an unsaved zip)
            }
        }
        Assert.assertEquals("must be no temp files after download: " + leftovers, 0, leftovers.size());
    }

    private void assertLogContains(TestDownloadService service, String text) throws IOException {
        File logFile = service.getDownloadLogFileForTests();
        Assert.assertNotNull("download log must exist", logFile);
        String log = new String(Files.readAllBytes(logFile.toPath()), StandardCharsets.UTF_8);
        Assert.assertTrue("download log must contain [" + text + "]:\n" + log, log.contains(text));
    }

    private static int countZipEntries(File zipFile, String prefix) throws IOException {
        int count = 0;
        try (ZipFile zip = new ZipFile(zipFile)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().startsWith(prefix)) {
                    count++;
                }
            }
        }
        return count;
    }

    private void writeImageFile(String setCode, String cardName, int size) throws IOException {
        CardDownloadData card = new CardDownloadData(cardName, setCode, "1", false, 0);
        TFile file = new TFile(CardImageUtils.buildImagePathToCardOrToken(card));
        file.getParentFile().mkdirs();
        try (OutputStream out = new TFileOutputStream(file)) {
            out.write(new byte[size]);
        }
        TVFS.umount();
    }

    /**
     * Zip file made by "another program" (without TrueVFS, so the download's archives stay mounted)
     */
    private File writeOtherZip() throws IOException {
        File other = new File(imagesDir, "other-program.tmp");
        try (java.util.zip.ZipOutputStream zip = new java.util.zip.ZipOutputStream(Files.newOutputStream(other.toPath()))) {
            for (int i = 0; i < 5; i++) {
                zip.putNextEntry(new ZipEntry("OTHER/Other card " + i + ".full.jpg"));
                byte[] data = new byte[IMAGE_SIZE_BYTES];
                ThreadLocalRandom.current().nextBytes(data);
                zip.write(data);
                zip.closeEntry();
            }
        }
        return other;
    }

    /**
     * @return image size or -1 on missing
     */
    private int readImageFile(String setCode, String cardName) throws IOException {
        CardDownloadData card = new CardDownloadData(cardName, setCode, "1", false, 0);
        TFile file = new TFile(CardImageUtils.buildImagePathToCardOrToken(card));
        if (!file.exists()) {
            return -1;
        }
        int total = 0;
        try (InputStream in = new TFileInputStream(file)) {
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) != -1) {
                total += len;
            }
        }
        return total;
    }

    /**
     * Break a local header of the zip entry (zip's list is fine, but the entry can't be read or copied)
     */
    private static void damageZipEntry(File zipFile, String entryName) throws IOException {
        byte[] all = Files.readAllBytes(zipFile.toPath());
        byte[] name = entryName.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < all.length - 30 - name.length; i++) {
            if (all[i] == 0x50 && all[i + 1] == 0x4b && all[i + 2] == 3 && all[i + 3] == 4
                    && Arrays.equals(Arrays.copyOfRange(all, i + 30, i + 30 + name.length), name)) {
                try (FileChannel channel = FileChannel.open(zipFile.toPath(), StandardOpenOption.WRITE)) {
                    channel.position(i).write(ByteBuffer.wrap(new byte[4]));
                }
                return;
            }
        }
        throw new IOException("entry not found: " + entryName);
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

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void deleteFolder(File folder) throws IOException {
        if (folder == null || !folder.exists()) {
            return;
        }
        Files.walk(folder.toPath())
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    if (!path.toFile().delete()) {
                        logger.warn("Can't delete test file " + path);
                    }
                });
    }

    /**
     * Download service with a fake network: each image is a slow stream of random bytes, or a fail
     */
    private static class TestDownloadService extends DownloadPicturesService {

        final int imageDownloadMillis;
        final AtomicInteger activeDownloads = new AtomicInteger();
        final AtomicInteger finishedDownloads = new AtomicInteger();
        final AtomicInteger failedDownloads = new AtomicInteger();
        final AtomicInteger refreshCalls = new AtomicInteger();
        final AtomicInteger downloadsOnRefresh = new AtomicInteger(-1);
        final AtomicBoolean refreshOutsideSwingThread = new AtomicBoolean(false);
        final List<String> progressMessages = Collections.synchronizedList(new ArrayList<>()); // text [current/need]
        boolean failAll = false;
        Function<CardDownloadData, Boolean> failFilter = card -> false;
        java.util.function.Consumer<CardDownloadData> onDownload = card -> {
        }; // e.g. a storage change by another program
        Function<String, Integer> httpCodes = null; // fake http mode: response code by url (null - fake images stream only)
        final List<String> httpRequests = Collections.synchronizedList(new ArrayList<>()); // urls in requests order
        final AtomicInteger loadMissingCalls = new AtomicInteger(); // dialog's lists reload (no cards database in tests)

        TestDownloadService(int imageDownloadMillis) {
            this.imageDownloadMillis = imageDownloadMillis;
        }

        @Override
        public void updateProgressMessage(String text, int progressCurrent, int progressNeed) {
            progressMessages.add(String.format("%s [%d/%d]", text, progressCurrent, progressNeed));
            super.updateProgressMessage(text, progressCurrent, progressNeed);
        }

        @Override
        InputStream openImageStream(CardDownloadData card, List<String> downloadUrls, List<String> errorsList) throws IOException {
            if (httpCodes != null) {
                // real urls processing with fake http connections
                return super.openImageStream(card, downloadUrls, errorsList);
            }
            if (failAll || failFilter.apply(card)) {
                // like a broken url, a blocked network or a non-image result
                incErrorCount();
                failedDownloads.incrementAndGet();
                errorsList.add("test fail of " + card);
                return null;
            }

            onDownload.accept(card);
            return createImageStream();
        }

        @Override
        void loadMissingCardsInBackground() {
            loadMissingCalls.incrementAndGet();
        }

        @Override
        XmageURLConnection createConnection(String url) {
            if (httpCodes == null) {
                throw new IllegalStateException("Wrong test usage: http requests are not allowed in fake images stream mode");
            }
            return new XmageURLConnection(url) {
                int responseCode = -1;

                @Override
                public void startConnection() {
                    // no network
                }

                @Override
                public boolean isConnected() {
                    return true;
                }

                @Override
                public void setRequestHeaders(Map<String, String> additionalHeaders) {
                }

                @Override
                public void connect() {
                    httpRequests.add(url);
                    responseCode = httpCodes.apply(url);
                }

                @Override
                public int getResponseCode() {
                    return responseCode;
                }

                @Override
                public String getHeaderField(String name) {
                    return responseCode == HTTP_TOO_MANY_REQUESTS && "Retry-After".equals(name) ? "30" : null;
                }

                @Override
                public String getErrorResponseAsString() {
                    return "";
                }

                @Override
                public InputStream getGoodResponseAsStream() {
                    return createImageStream();
                }
            };
        }

        private InputStream createImageStream() {
            byte[] data = new byte[IMAGE_SIZE_BYTES];
            ThreadLocalRandom.current().nextBytes(data);
            activeDownloads.incrementAndGet();
            return new ByteArrayInputStream(data) {
                boolean started = false;

                @Override
                public synchronized int read(byte[] b, int off, int len) {
                    if (!started) {
                        started = true;
                        sleep(imageDownloadMillis);
                    }
                    return super.read(b, off, len);
                }

                @Override
                public void close() {
                    activeDownloads.decrementAndGet();
                    finishedDownloads.incrementAndGet();
                }
            };
        }
    }

    private static class TestImageSource implements CardImageSource {

        static final String ALTERNATIVE_URL_SUFFIX = "?alternative";

        final boolean useAlternativeUrls; // like scryfall: localized url + default url

        TestImageSource() {
            this(false);
        }

        TestImageSource(boolean useAlternativeUrls) {
            this.useAlternativeUrls = useAlternativeUrls;
        }

        @Override
        public CardImageUrls generateCardUrl(CardDownloadData card) {
            String url = "http://test.local/" + card.getSet() + "/" + card.getCollectorId();
            if (useAlternativeUrls) {
                return new CardImageUrls(url, url + ALTERNATIVE_URL_SUFFIX);
            }
            return new CardImageUrls(url);
        }

        @Override
        public CardImageUrls generateTokenUrl(CardDownloadData card) {
            return new CardImageUrls("http://test.local/tokens/" + card.getSet() + "/" + card.getImageNumber());
        }

        @Override
        public boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
            return true;
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
            return "test source";
        }

        @Override
        public float getAverageSizeKb() {
            return IMAGE_SIZE_BYTES / 1024f;
        }

        @Override
        public int getTotalImages() {
            return -1;
        }

        @Override
        public boolean isCardSource() {
            return true;
        }

        @Override
        public boolean isTokenSource() {
            return true;
        }

        @Override
        public boolean isCardImageProvided(String setCode, String cardName) {
            return true;
        }

        @Override
        public boolean isTokenImageProvided(String setCode, String cardName, Integer tokenNumber) {
            return true;
        }

        @Override
        public List<String> getSupportedSets() {
            return Arrays.asList(SET_CODES);
        }
    }

    private static class TestCard extends CardImpl {

        TestCard(String name, String setCode, String cardNumber) {
            super(UUID.randomUUID(), new CardSetInfo(name, setCode, cardNumber, Rarity.COMMON), new CardType[]{CardType.ARTIFACT}, "{1}");
        }

        private TestCard(final TestCard card) {
            super(card);
        }

        @Override
        public Card copy() {
            return new TestCard(this);
        }
    }
}