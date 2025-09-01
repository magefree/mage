package org.mage.plugins.card.images;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.TokenRepository;
import mage.client.MageFrame;
import mage.client.dialog.DownloadImagesDialog;
import mage.client.dialog.PreferencesDialog;
import mage.client.remote.XmageURLConnection;
import mage.client.util.CardLanguage;
import mage.client.util.GUISizeHelper;
import mage.client.util.sets.ConstructedFormats;
import mage.util.ThreadUtils;
import mage.util.XmageThreadFactory;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;
import net.java.truevfs.access.TFileOutputStream;
import net.java.truevfs.access.TVFS;
import net.java.truevfs.kernel.spec.FsSyncException;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.dl.sources.*;
import org.mage.plugins.card.utils.CardImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

/**
 * App GUI: card images downloader service (for GUI control see DownloadImagesDialog)
 *
 * @author JayDi85
 */
public class DownloadPicturesService extends DefaultBoundedRangeModel implements DownloadServiceInfo, Runnable {

    private static DownloadPicturesService instance = null;
    private static Thread loadMissingDataThread = null;

    private static final Logger logger = Logger.getLogger(DownloadPicturesService.class);

    private static final String ALL_IMAGES = "- ALL images from selected source (can be slow)";
    private static final String ALL_MODERN_IMAGES = "- MODERN images (can be slow)";
    private static final String ALL_STANDARD_IMAGES = "- STANDARD images";
    private static final String ALL_TOKENS = "- TOKEN images";
    private static final String ALL_BASICS = "- BASIC LAND images";

    private static final List<String> basicList = Arrays.asList("Plains", "Island", "Swamp", "Mountain", "Forest");

    private static final int MAX_ERRORS_COUNT_BEFORE_CANCEL = 50;
    private static final int DEFAULT_DOWNLOAD_THREADS = 5;

    // protect from wrong data save
    // there are possible land images with small sizes, so must research content in check
    private static final int MIN_FILE_SIZE_OF_GOOD_IMAGE = 1024 * 6; // smaller files will be mark as broken
    private static final int MIN_FILE_SIZE_OF_POSSIBLE_BAD_IMAGE = 1024 * 8; // smaller files will be checked for possible broken mark (slow)

    private final DownloadImagesDialog uiDialog;
    private boolean needCancel;
    private int errorCount;
    private int cardIndex;

    private List<CardInfo> cardsAll;
    private List<CardDownloadData> cardsMissing;
    private List<CardDownloadData> cardsDownloadQueue;

    private final List<String> selectedSets = new ArrayList<>();
    private CardImageSource selectedSource;

    private final Object sync = new Object();

    enum DownloadSources {
        WIZARDS("1. wizards.com - low quality, cards only", WizardCardsImageSource.instance),
        SCRYFALL_BIG("2a. scryfall.com - BIG: high quality (~15 GB)", ScryfallImageSource.getInstance()),
        SCRYFALL_NORM("2b. scryfall.com - normal: good quality (~10 GB)", ScryfallImageSourceNormal.getInstance()),
        SCRYFALL_SMALL("2c. scryfall.com - small: low quality, unreadable text (~1.5 GB)", ScryfallImageSourceSmall.getInstance()),
        GRAB_BAG("3. GrabBag - unofficial STAR WARS + Arena Tutorial cards", GrabbagImageSource.instance),
        COPYPASTE("4. Experimental - copy and paste image URLs", CopyPasteImageSource.instance); // TODO: need rework for user friendly GUI

        private final String text;
        private final CardImageSource source;

        DownloadSources(String text, CardImageSource sourceInstance) {
            this.text = text;
            this.source = sourceInstance;
        }

        public CardImageSource getSource() {
            return source;
        }

        @Override
        public String toString() {
            return text;
        }

    }

    public static DownloadPicturesService getInstance() {
        return instance;
    }

    public static void startDownload() {
        // workaround to keep first db connection in main thread (not images thread) - so it will keep connection after close
        // TODO: no needs?
        CardRepository.instance.getNonLandAndNonCreatureNames();

        // load images info in background task
        if (instance == null) {
            instance = new DownloadPicturesService();
        }

        if (loadMissingDataThread != null) {
            // stop old thread
            //loadMissingDataThread.interrupt();
        }

        // refresh ui
        instance.uiDialog.setGlobalInfo("Initializing image download...");
        instance.uiDialog.getProgressBar().setValue(0);

        // start new thread
        loadMissingDataThread = new Thread(new LoadMissingCardDataNew(instance));
        loadMissingDataThread.setDaemon(true);
        loadMissingDataThread.start();

        // show control dialog
        instance.setNeedCancel(false);
        instance.resetErrorCount();
        instance.uiDialog.showDialog(() -> {
            // on finish/close/cancel
            doStopAndClose();
        });
    }

    static private void doStopAndClose() {
        instance.setNeedCancel(true);
        instance.uiDialog.hideDialog();
    }

    @Override
    public boolean isNeedCancel() {
        return this.needCancel || (this.errorCount > MAX_ERRORS_COUNT_BEFORE_CANCEL) || Thread.currentThread().isInterrupted();
    }

    private void setNeedCancel(boolean needCancel) {
        this.needCancel = needCancel;
    }

    @Override
    public void incErrorCount() {
        this.errorCount = this.errorCount + 1;

        if (this.errorCount == MAX_ERRORS_COUNT_BEFORE_CANCEL + 1) {
            logger.warn("Too many errors (> " + MAX_ERRORS_COUNT_BEFORE_CANCEL + ") in images download. Stop.");
        }
    }

    private void resetErrorCount() {
        this.errorCount = 0;
    }

    public DownloadPicturesService() {
        // init service and dialog
        cardsAll = Collections.synchronizedList(new ArrayList<>());
        cardsMissing = Collections.synchronizedList(new ArrayList<>());
        cardsDownloadQueue = Collections.synchronizedList(new ArrayList<>());
        uiDialog = new DownloadImagesDialog();

        // SOURCES - scryfall is default source
        uiDialog.getSourcesCombo().setModel(new DefaultComboBoxModel(DownloadSources.values()));
        uiDialog.getSourcesCombo().setSelectedItem(DownloadSources.SCRYFALL_NORM);
        selectedSource = ScryfallImageSource.getInstance();
        uiDialog.getSourcesCombo().addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                comboboxSourceSelected(event);
            }
        });

        // LANGUAGES
        uiDialog.getLaunguagesCombo().setModel(new DefaultComboBoxModel(CardLanguage.values()));
        uiDialog.getLaunguagesCombo().setSelectedItem(PreferencesDialog.getPrefImagesLanguage());
        reloadLanguagesForSelectedSource();

        // DOWNLOAD THREADS
        uiDialog.getDownloadThreadsCombo().setModel(new DefaultComboBoxModel<>(new String[]{"10", "9", "8", "7", "6", "5", "4", "3", "2", "1"}));
        uiDialog.getDownloadThreadsCombo().setSelectedItem(String.valueOf(DEFAULT_DOWNLOAD_THREADS));

        // REDOWNLOAD
        uiDialog.getRedownloadCheckbox().setSelected(false);
        uiDialog.getRedownloadCheckbox().addItemListener(this::checkboxRedowloadChanged);

        // SETS (fills after source and language select)
        //uiDialog.getSetsCombo().setModel(new DefaultComboBoxModel(DownloadSources.values()));
        uiDialog.getSetsCombo().addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                comboboxSetSelected(event);
            }
        });

        // BUTTON START
        uiDialog.getStartButton().addActionListener(e -> {
            // selected language setup
            if (selectedSource != null) {
                if (selectedSource.isLanguagesSupport()) {
                    selectedSource.setCurrentLanguage((CardLanguage) uiDialog.getLaunguagesCombo().getSelectedItem());
                }
            }

            // run
            uiDialog.enableActionControls(false);
            uiDialog.getStartButton().setEnabled(false);
            new Thread(DownloadPicturesService.this).start();
        });

        // BUTTON CANCEL (dialog and loading)
        uiDialog.getCancelButton().addActionListener(e -> doStopAndClose());
        uiDialog.getStopButton().addActionListener(e -> doStopAndClose());
    }

    public void findMissingCards() {
        updateGlobalMessage("Loading...");
        this.cardsAll.clear();
        this.cardsMissing.clear();
        this.cardsDownloadQueue.clear();

        try {
            updateGlobalMessage("Loading cards list...");
            this.cardsAll.addAll(CardRepository.instance.findCards(
                    new CardCriteria().nightCard(null) // meld cards need to be in the target cards, so we allow for night cards
            ));
            if (isNeedCancel()) {
                // fast stop on cancel
                return;
            }

            updateGlobalMessage("Finding missing images...");
            this.cardsMissing.addAll(prepareMissingCards(this.cardsAll, uiDialog.getRedownloadCheckbox().isSelected()));
            if (isNeedCancel()) {
                // fast stop on cancel
                return;
            }

            updateGlobalMessage("Finding available sets from selected source...");
            this.uiDialog.getSetsCombo().setModel(new DefaultComboBoxModel<>(getSetsForCurrentImageSource()));
            reloadCardsToDownload(this.uiDialog.getSetsCombo().getSelectedItem().toString());
        } finally {
            updateGlobalMessage("");
            this.uiDialog.showDownloadControls(true);
        }
    }

    private void reloadLanguagesForSelectedSource() {
        this.uiDialog.showLanguagesSupport(selectedSource != null && selectedSource.isLanguagesSupport());
    }

    private void reloadSetsForSelectedSource() {
        // update the available sets / token combobox
        Object oldSelection = this.uiDialog.getSetsCombo().getSelectedItem();
        this.uiDialog.getSetsCombo().setModel(new DefaultComboBoxModel<>(getSetsForCurrentImageSource()));
        if (oldSelection != null) {
            this.uiDialog.getSetsCombo().setSelectedItem(oldSelection);
        }
        reloadCardsToDownload(this.uiDialog.getSetsCombo().getSelectedItem().toString());
    }

    private void comboboxSourceSelected(ItemEvent evt) {
        if (this.uiDialog.getSourcesCombo().isEnabled()) {
            selectedSource = ((DownloadSources) evt.getItem()).getSource();
            reloadSetsForSelectedSource();
            reloadLanguagesForSelectedSource();
        }
    }

    @Override
    public void updateGlobalMessage(String text) {
        this.uiDialog.setGlobalInfo(text);
    }

    @Override
    public void updateProgressMessage(String text) {
        updateProgressMessage(text, 0, 0);
    }

    @Override
    public void updateProgressMessage(String text, int progressCurrent, int progressNeed) {
        this.uiDialog.getProgressBar().setString(text);

        // set values to 0 to disable progress bar
        this.uiDialog.getProgressBar().setMaximum(progressNeed);
        this.uiDialog.getProgressBar().setValue(progressCurrent);
    }

    private String getSetNameWithYear(ExpansionSet exp) {
        return exp.getName() + " (" + exp.getCode() + ", " + exp.getReleaseYear() + ")";
    }

    private ExpansionSet findSetByNameWithYear(String name) {
        return Sets.getInstance().values().stream()
                .filter(exp -> getSetNameWithYear(exp).equals(name))
                .findFirst()
                .orElse(null);
    }

    private Object[] getSetsForCurrentImageSource() {
        // Set the available sets to the combo box
        List<String> supportedSets = selectedSource.getSupportedSets();
        List<String> setNames = new ArrayList<>();

        // multiple sets selection
        if (selectedSource.isCardSource()) {
            setNames.add(ALL_IMAGES);
            setNames.add(ALL_MODERN_IMAGES);
            setNames.add(ALL_STANDARD_IMAGES);
            setNames.add(ALL_BASICS);
        }
        if (selectedSource.isTokenSource()) {
            setNames.add(ALL_TOKENS);
        }

        // single set selection
        Collection<ExpansionSet> dbSets = Sets.getInstance().values();
        Collection<String> comboSets = dbSets.stream()
                .filter(exp -> supportedSets.contains(exp.getCode()))
                .sorted(Comparator.comparing(ExpansionSet::getReleaseDate).reversed())
                .map(this::getSetNameWithYear)
                .collect(Collectors.toList());
        setNames.addAll(comboSets);

        if (setNames.isEmpty()) {
            logger.error("Source " + selectedSource.getSourceName() + " creates no selectable items.");
            setNames.add("not available");
        }
        return setNames.toArray(new String[0]);
    }

    private void reloadCardsToDownload(String selectedItem) {
        // find selected sets
        selectedSets.clear();
        boolean onlyTokens = false;
        boolean onlyBasics = false;
        List<String> formatSets;
        List<String> sourceSets = selectedSource.getSupportedSets();
        switch (selectedItem) {

            case ALL_IMAGES:
                selectedSets.addAll(selectedSource.getSupportedSets());
                break;

            case ALL_STANDARD_IMAGES:
                formatSets = ConstructedFormats.getSetsByFormat(ConstructedFormats.STANDARD);
                formatSets.stream()
                        .filter(sourceSets::contains)
                        .forEachOrdered(selectedSets::add);
                break;

            case ALL_MODERN_IMAGES:
                formatSets = ConstructedFormats.getSetsByFormat(ConstructedFormats.MODERN);
                formatSets.stream()
                        .filter(sourceSets::contains)
                        .forEachOrdered(selectedSets::add);
                break;

            case ALL_BASICS:
                selectedSets.addAll(selectedSource.getSupportedSets());
                onlyBasics = true;
                break;

            case ALL_TOKENS:
                selectedSets.addAll(selectedSource.getSupportedSets());
                onlyTokens = true;
                break;

            default:
                // selects one set
                ExpansionSet selectedExp = findSetByNameWithYear(selectedItem);
                if (selectedExp != null) {
                    selectedSets.add(selectedExp.getCode());
                }
                break;
        }

        // find missing cards to download
        cardsDownloadQueue.clear();
        int numberTokenImagesAvailable = 0;
        int numberCardImagesAvailable = 0;
        for (CardDownloadData data : cardsMissing) {
            if (data.isToken()) {
                if (!onlyBasics
                        && selectedSource.isTokenSource()
                        && selectedSource.isTokenImageProvided(data.getSet(), data.getName(), data.getImageNumber())
                        && selectedSets.contains(data.getSet())) {
                    numberTokenImagesAvailable++;
                    cardsDownloadQueue.add(data);
                }
            } else {
                if (!onlyTokens
                        && selectedSource.isCardSource()
                        && selectedSource.isCardImageProvided(data.getSet(), data.getName())
                        && selectedSets.contains(data.getSet())) {
                    if (!onlyBasics
                            || basicList.contains(data.getName())) {
                        numberCardImagesAvailable++;
                        cardsDownloadQueue.add(data);
                    }
                }
            }
        }
        updateProgressText(numberCardImagesAvailable, numberTokenImagesAvailable);
    }

    private void comboboxSetSelected(ItemEvent event) {
        // Update the cards to download related to the selected set
        reloadCardsToDownload(event.getItem().toString());
    }

    private void checkboxRedowloadChanged(ItemEvent event) {
        MageFrame.getDesktop().setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            this.cardsMissing.clear();
            this.cardsMissing.addAll(prepareMissingCards(this.cardsAll, uiDialog.getRedownloadCheckbox().isSelected()));
            reloadCardsToDownload(uiDialog.getSetsCombo().getSelectedItem().toString());
        } finally {
            MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void updateProgressText(int cardCount, int tokenCount) {
        int missingTokensCount = 0;
        for (CardDownloadData card : cardsMissing) {
            if (card.isToken()) {
                missingTokensCount++;
            }
        }
        int missingCardsCount = cardsMissing.size() - missingTokensCount;

        uiDialog.setCurrentInfo("Missing: " + missingCardsCount + " card images / " + missingTokensCount + " token images");
        int imageSum = cardCount + tokenCount;
        float mb = (imageSum * selectedSource.getAverageSizeKb()) / 1024;
        String statusEnd;
        if (imageSum == 0) {
            statusEnd = "you have all images. Please close.";
        } else if (cardIndex == 0) {
            statusEnd = "image download NOT STARTED. Please start.";
        } else {
            statusEnd = String.format("image downloading... Please wait [%.1f MB left].", mb);
        }
        updateProgressMessage(String.format("%d of %d (%d cards and %d tokens) %s",
                0, imageSum, cardCount, tokenCount, statusEnd
        ), 0, imageSum);
    }

    private static String createDownloadName(CardInfo card) {
        String className = card.getClassName();
        return className.substring(className.lastIndexOf('.') + 1);
    }

    private static List<CardDownloadData> prepareMissingCards(List<CardInfo> allCards, boolean redownloadMode) {
        // prepare checking list
        List<CardDownloadData> allCardsUrls = Collections.synchronizedList(new ArrayList<>());
        try {
            allCards.parallelStream().forEach(card -> {
                if (!card.getCardNumber().isEmpty()
                        && !"0".equals(card.getCardNumber())
                        && !card.getSetCode().isEmpty()) {

                    // main side for non-night cards.
                    // At the exception of Meld Card, as they are not the back of an image for download
                    if (!card.isNightCard() || card.isMeldCard()) {
                        String cardName = card.getName();
                        CardDownloadData url = new CardDownloadData(
                                cardName,
                                card.getSetCode(),
                                card.getCardNumber(),
                                card.usesVariousArt(),
                                0);

                        // variations must have diff file names with additional postfix
                        if (url.getUsesVariousArt()) {
                            url.setDownloadName(createDownloadName(card));
                        }

                        url.setSplitCard(card.isSplitCard());
                        allCardsUrls.add(url);
                    }
                    // second side
                    // xmage doesn't search night cards by default, so add it and other types manually
                    if (card.isDoubleFaced()) {
                        if (card.getSecondSideName() == null || card.getSecondSideName().trim().isEmpty()) {
                            throw new IllegalStateException("Second side card can't have empty name.");
                        }

                        CardInfo secondSideCard = CardRepository.instance.findCardWithPreferredSetAndNumber(card.getSecondSideName(), card.getSetCode(), card.getCardNumber());
                        if (secondSideCard == null) {
                            throw new IllegalStateException("Can't find second side card in database: " + card.getSecondSideName());
                        }

                        CardDownloadData url = new CardDownloadData(
                                card.getSecondSideName(),
                                card.getSetCode(),
                                secondSideCard.getCardNumber(),
                                card.usesVariousArt(),
                                0
                        );
                        url.setSecondSide(true);
                        allCardsUrls.add(url);
                    }
                    if (card.isFlipCard()) {
                        if (card.getFlipCardName() == null || card.getFlipCardName().trim().isEmpty()) {
                            throw new IllegalStateException("Flipped card can't have empty name.");
                        }
                        CardDownloadData cardDownloadData = new CardDownloadData(
                                card.getFlipCardName(),
                                card.getSetCode(),
                                card.getCardNumber(),
                                card.usesVariousArt(),
                                0
                        );
                        cardDownloadData.setFlippedSide(true);
                        // meld cards urls are on their own
                        cardDownloadData.setSecondSide(card.isNightCard() && !card.isMeldCard());
                        allCardsUrls.add(cardDownloadData);
                    }
                    if (card.getMeldsToCardName() != null) {
                        if (card.getMeldsToCardName().trim().isEmpty()) {
                            throw new IllegalStateException("MeldsToCardName can't be empty in " + card.getName());
                        }

                        CardInfo meldsToCard = CardRepository.instance.findCardWithPreferredSetAndNumber(card.getMeldsToCardName(), card.getSetCode(), card.getCardNumber());
                        if (meldsToCard == null) {
                            throw new IllegalStateException("Can't find meldsToCard in database: " + card.getMeldsToCardName());
                        }

                        // meld cards are normal cards from the set, so no needs to set two faces/sides here
                        CardDownloadData url = new CardDownloadData(
                                card.getMeldsToCardName(),
                                card.getSetCode(),
                                meldsToCard.getCardNumber(),
                                card.usesVariousArt(),
                                0
                        );
                        allCardsUrls.add(url);
                    }
                    if (card.isModalDoubleFacedCard()) {
                        if (card.getModalDoubleFacedSecondSideName() == null || card.getModalDoubleFacedSecondSideName().trim().isEmpty()) {
                            throw new IllegalStateException("MDF card can't have empty name.");
                        }
                        CardDownloadData cardDownloadData = new CardDownloadData(
                                card.getModalDoubleFacedSecondSideName(),
                                card.getSetCode(),
                                card.getCardNumber(),
                                card.usesVariousArt(),
                                0
                        );
                        cardDownloadData.setSecondSide(true);
                        allCardsUrls.add(cardDownloadData);
                    }
                } else if (card.getCardNumber().isEmpty() || "0".equals(card.getCardNumber())) {
                    logger.error("Card has no collector ID and won't be sent to client: " + card.getName());
                } else if (card.getSetCode().isEmpty()) {
                    logger.error("Card has no set name and won't be sent to client:" + card.getName());
                } else {
                    logger.info("Card was not selected: " + card.getName());
                }
            });

            // tokens
            TokenRepository.instance.getAll().forEach(token -> {
                CardDownloadData card = new CardDownloadData(
                        token.getName(),
                        token.getSetCode(),
                        "0",
                        false,
                        token.getImageNumber());
                card.setToken(true);
                allCardsUrls.add(card);
            });
        } catch (Exception e) {
            logger.error("Error on prepare images list: " + e, e);
        }

        // find missing files
        List<CardDownloadData> cardsToDownload = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger badContentChecks = new AtomicInteger();
        allCardsUrls.parallelStream().forEach(card -> {
            if (redownloadMode) {
                // need all cards
                cardsToDownload.add(card);
            } else {
                // need missing cards
                String imagePath = CardImageUtils.buildImagePathToCardOrToken(card);
                File file = new TFile(imagePath);
                if (!file.exists()) {
                    cardsToDownload.add(card);
                } else if (file.length() < MIN_FILE_SIZE_OF_GOOD_IMAGE) {
                    // too small, e.g. contains http error page instead image data
                    // how-to fix: if it really downloads image data then set lower file size
                    logger.error("Found broken file (small size): " + imagePath);
                    cardsToDownload.add(card);
                } else if (file.length() < MIN_FILE_SIZE_OF_POSSIBLE_BAD_IMAGE) {
                    // bad image format, e.g. contains redirected site page
                    badContentChecks.incrementAndGet();
                    try {
                        try (TFileInputStream inputStream = new TFileInputStream(file)) {
                            BufferedImage image = ImageIO.read(inputStream);
                            if (image.getWidth() <= 0) {
                                throw new IOException("bad format");
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Found broken file (bad format): " + imagePath);
                        cardsToDownload.add(card);
                    }
                }
            }
        });

        if (badContentChecks.get() > 1000) {
            // how-to fix: download full small images and run checks -- make sure it finds fewer files to check
            logger.warn("Wrong code usage: too many file content checks (" + badContentChecks.get() + ") - try to decrease min file size");
        }

        return Collections.synchronizedList(new ArrayList<>(cardsToDownload));
    }

    @Override
    public void run() {
        this.cardIndex = 0;
        this.resetErrorCount();

        try {
            File base = new File(getImagesDir());
            if (!base.exists()) {
                base.mkdir();
            }

            int downloadThreadsAmount = Math.max(1, Integer.parseInt((String) uiDialog.getDownloadThreadsCombo().getSelectedItem()));

            logger.info("Started download of " + cardsDownloadQueue.size() + " images"
                    + " from source: " + selectedSource.getSourceName()
                    + ", language: " + selectedSource.getCurrentLanguage().getCode()
                    + ", threads: " + downloadThreadsAmount);
            updateProgressMessage("Preparing download list...");
            if (selectedSource.prepareDownloadList(this, cardsDownloadQueue)) {
                update(0, cardsDownloadQueue.size());
                ExecutorService executor = Executors.newFixedThreadPool(
                        downloadThreadsAmount,
                        new XmageThreadFactory(ThreadUtils.THREAD_PREFIX_CLIENT_IMAGES_DOWNLOADER, false)
                );
                for (int i = 0; i < cardsDownloadQueue.size() && !this.isNeedCancel(); i++) {
                    try {
                        CardDownloadData card = cardsDownloadQueue.get(i);

                        logger.debug("Downloading image: " + card.getName() + " (" + card.getSet() + ')');

                        CardImageUrls urls;
                        if (card.isToken()) {
                            if (!"0".equals(card.getCollectorId())) {
                                continue;
                            }
                            urls = selectedSource.generateTokenUrl(card);
                        } else {
                            urls = selectedSource.generateCardUrl(card);
                        }

                        if (urls == null) {
                            String imageRef = selectedSource.getNextHttpImageUrl();
                            String fileName = selectedSource.getFileForHttpImage(imageRef);
                            if (imageRef != null && fileName != null) {
                                imageRef = selectedSource.getSourceName() + imageRef;
                                try {
                                    card.setToken(selectedSource.isTokenSource());
                                    Runnable task = new DownloadTask(card, imageRef, fileName, selectedSource.getTotalImages());
                                    executor.execute(task);
                                } catch (Exception ex) {
                                }
                            } else if (selectedSource.getTotalImages() == -1) {
                                logger.info("Image not available on " + selectedSource.getSourceName() + ": " + card.getName() + " (" + card.getSet() + ')');
                                synchronized (sync) {
                                    update(cardIndex + 1, cardsDownloadQueue.size());
                                }
                            }
                        } else {
                            Runnable task = new DownloadTask(card, urls, cardsDownloadQueue.size());
                            executor.execute(task);
                        }
                    } catch (Exception ex) {
                        logger.error(ex, ex);
                    }
                }

                executor.shutdown();
                try {
                    executor.awaitTermination(30, TimeUnit.SECONDS);
                } catch (InterruptedException ignore) {
                }

                // IMAGES CHECK (download process can break some files, so fix it here too)
                // code executes on finish/cancel download (but not executes on app's close -- it's ok)
                logger.info("Images: search broken files...");
                CardImageUtils.checkAndFixImageFiles();
            }
        } catch (Throwable e) {
            logger.error("Catch unknown error while downloading: " + e, e);
            MageFrame.getInstance().showErrorDialog("Catch unknown error while downloading " + e, e);
        } finally {
            // must close all active archives anyway
            try {
                TVFS.umount();
            } catch (FsSyncException e) {
                logger.error("Couldn't unmount zip files " + e, e);
                // this is not a critical error - just need to run it again - see issue #12833
            }
        }

        // cleanup resources
        Arrays.stream(DownloadSources.values()).forEach(resource -> {
            resource.source.onFinished();
        });

        // stop
        reloadCardsToDownload(uiDialog.getSetsCombo().getSelectedItem().toString());
        enableDialogButtons();

        // reset GUI and cards to use new images
        GUISizeHelper.refreshGUIAndCards(false);
    }

    private final class DownloadTask implements Runnable {

        private final CardDownloadData card;
        private final CardImageUrls urls;
        private final int count;
        private final String actualFilename;
        private final boolean useSpecifiedPaths;

        DownloadTask(CardDownloadData card, CardImageUrls urls, int count) {
            this.card = card;
            this.urls = urls;
            this.count = count;
            this.actualFilename = "";
            this.useSpecifiedPaths = false;
        }

        DownloadTask(CardDownloadData card, String baseUrl, String actualFilename, int count) {
            this.card = card;
            this.urls = new CardImageUrls(baseUrl);
            this.count = count;
            this.actualFilename = actualFilename;
            this.useSpecifiedPaths = true;
        }

        @Override
        public void run() {
            if (DownloadPicturesService.getInstance().isNeedCancel()) {
                synchronized (sync) {
                    update(cardIndex + 1, count);
                }
                return;
            }

            TFile fileTempImage;
            TFile destFile;
            try {

                if (card == null) {
                    synchronized (sync) {
                        update(cardIndex + 1, count);
                    }
                    return;
                }

                // gen temp file (download to images folder)
                String tempPath = getImagesDir() + File.separator + "downloading" + File.separator;
                if (useSpecifiedPaths) {
                    fileTempImage = new TFile(tempPath + actualFilename + "-" + card.hashCode() + ".jpg");
                } else {
                    fileTempImage = new TFile(tempPath + CardImageUtils.prepareCardNameForFile(card.getName()) + "-" + card.hashCode() + ".jpg");
                }
                TFile parentFile = fileTempImage.getParentFile();
                if (parentFile != null) {
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                }

                // gen dest file name
                if (useSpecifiedPaths) {
                    if (card.isToken()) {
                        destFile = new TFile(CardImageUtils.buildImagePathToSet(card) + actualFilename + ".jpg");
                    } else {
                        destFile = new TFile(CardImageUtils.buildImagePathToTokens() + actualFilename + ".jpg");
                    }
                } else {
                    destFile = new TFile(CardImageUtils.buildImagePathToCardOrToken(card));
                }

                // check zip access
                TFile testArchive = destFile.getTopLevelArchive();
                if (testArchive != null && testArchive.exists()) {
                    try {
                        testArchive.list();
                    } catch (Exception e) {
                        logger.error("Error reading archive, it's can be corrupted. Try to delete it: " + testArchive.toString());

                        synchronized (sync) {
                            update(cardIndex + 1, count);
                        }
                        return;
                    }
                }

                // can download images from many alternative urls
                List<String> downloadUrls;
                XmageURLConnection connection = null;
                if (this.urls != null) {
                    downloadUrls = this.urls.getDownloadList();
                } else {
                    downloadUrls = new ArrayList<>();
                }

                // try to find first workable link
                boolean isDownloadOK = false;
                List<String> errorsList = new ArrayList<>();
                for (String currentUrl : downloadUrls) {

                    // fast stop on cancel
                    if (DownloadPicturesService.getInstance().isNeedCancel()) {
                        return;
                    }

                    // timeout before each request
                    selectedSource.doPause(currentUrl);

                    connection = new XmageURLConnection(currentUrl);
                    connection.startConnection();
                    if (connection.isConnected()) {

                        // custom headers
                        connection.setRequestHeaders(selectedSource.getHttpRequestHeaders(currentUrl));

                        try {
                            connection.connect();
                        } catch (SocketException e) {
                            incErrorCount();
                            errorsList.add("Wrong image URL or java app is not allowed to use network. Check your firewall or proxy settings. Error: " + e.getMessage() + ". Image URL: " + currentUrl);
                            break;
                        } catch (UnknownHostException e) {
                            incErrorCount();
                            errorsList.add("Unknown site. Check your DNS settings. Error: " + e.getMessage() + ". Image URL: " + currentUrl);
                            break;
                        }
                        int responseCode = connection.getResponseCode();

                        // check result
                        if (responseCode != 200) {
                            // show errors only on full fail (all urls were not work)
                            String info = String.format("Image download failed for %s - %s, http code: %d, url: %s",
                                    card.getSet(),
                                    card.getName(),
                                    responseCode,
                                    currentUrl
                            );
                            errorsList.add(info);

                            if (logger.isDebugEnabled()) {
                                // Shows the returned html from the request to the web server
                                logger.debug("Returned HTML ERROR:\n" + connection.getErrorResponseAsString());
                            }

                            // go to next try
                            continue;
                        } else {
                            // all fine
                            isDownloadOK = true;
                            break;
                        }
                    }
                }

                // if workable link found then save result
                if (isDownloadOK && connection.isConnected()) {
                    // save data to temp
                    try (InputStream in = new BufferedInputStream(connection.getGoodResponseAsStream());
                         OutputStream tempFileStream = new TFileOutputStream(fileTempImage);
                         OutputStream out = new BufferedOutputStream(tempFileStream)) {
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) != -1) {
                            // user cancelled
                            if (DownloadPicturesService.getInstance().isNeedCancel()) {
                                // stop download, save current state and exit
                                // real archives save will be done on download service finish
                                try {
                                    TFile.rm(fileTempImage);
                                } catch (Exception e) {
                                    logger.error("Can't delete temp file: " + e.getMessage(), e);
                                }
                                return;
                            }

                            // all fine, can save data part
                            out.write(buf, 0, len);
                        }
                    }

                    // SAVE final data
                    // TODO: add image data check here instead use it on download dialog?
                    if (fileTempImage.exists()) {
                        if (!destFile.getParentFile().exists()) {
                            destFile.getParentFile().mkdirs();
                        }
                        new TFile(fileTempImage).cp_rp(destFile);
                        try {
                            TFile.rm(fileTempImage);
                        } catch (Exception e) {
                            logger.error("Can't delete temp file: " + e.getMessage(), e);
                        }
                    }
                } else {
                    // download errors
                    for (String err : errorsList) {
                        logger.warn(err);
                    }
                }
            } catch (AccessDeniedException e) {
                incErrorCount();
                logger.error("Can't access to files: " + card.getName() + "(" + card.getSet() + "). Try rebooting your system to remove the file lock.");
            } catch (Exception e) {
                incErrorCount();
                String sampleUrl = (urls == null ? "null" : urls.getDownloadList().stream().findFirst().orElse(null));
                logger.error("Unknown error: " + e.getMessage() + ", sample url: " + sampleUrl, e);
            }

            synchronized (sync) {
                update(cardIndex + 1, count);
            }
        }
    }

    private void update(int lastCardIndex, int needDownloadCount) {
        this.cardIndex = lastCardIndex;

        if (cardIndex < needDownloadCount) {
            // downloading
            float mb = ((needDownloadCount - lastCardIndex) * selectedSource.getAverageSizeKb()) / 1024;
            updateProgressMessage(String.format("%d of %d image downloading... Please wait [%.1f MB left].",
                    lastCardIndex, needDownloadCount, mb), lastCardIndex, needDownloadCount);
        } else {
            // finished
            updateProgressMessage("Image download DONE, saving last files and refreshing stats... Please wait.");
            List<CardDownloadData> downloadedCards = Collections.synchronizedList(new ArrayList<>());
            DownloadPicturesService.this.cardsMissing.parallelStream().forEach(cardDownloadData -> {
                TFile file = new TFile(CardImageUtils.buildImagePathToCardOrToken(cardDownloadData));
                if (file.exists() && file.length() > MIN_FILE_SIZE_OF_GOOD_IMAGE) {
                    downloadedCards.add(cardDownloadData);
                }
            });

            // remove all downloaded cards, missing must be remains
            // workaround for fast remove
            Set<CardDownloadData> finished = new HashSet<>(downloadedCards);
            this.cardsDownloadQueue = Collections.synchronizedList(this.cardsDownloadQueue.stream()
                    .filter(c -> !finished.contains(c))
                    .collect(Collectors.toList())
            );
            this.cardsMissing = Collections.synchronizedList(this.cardsMissing.stream()
                    .filter(c -> !finished.contains(c))
                    .collect(Collectors.toList())
            );

            if (this.cardsDownloadQueue.isEmpty()) {
                // stop download
                updateProgressMessage("Nothing to download. Please close.");
            } else {
                // try download again
            }

            enableDialogButtons();
        }
    }

    private static final long serialVersionUID = 1L;

    private void enableDialogButtons() {
        uiDialog.getRedownloadCheckbox().setSelected(false); // reset re-download button after finished
        uiDialog.enableActionControls(true);
        uiDialog.getStartButton().setEnabled(true);
    }

    @Override
    public Object getSync() {
        return sync;
    }
}

class LoadMissingCardDataNew implements Runnable {

    private static DownloadPicturesService downloadPicturesService;

    public LoadMissingCardDataNew(DownloadPicturesService downloadPicturesService) {
        LoadMissingCardDataNew.downloadPicturesService = downloadPicturesService;
    }

    @Override
    public void run() {
        downloadPicturesService.findMissingCards();
    }
}
