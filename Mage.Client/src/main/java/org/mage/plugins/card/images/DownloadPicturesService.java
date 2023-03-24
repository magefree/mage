package org.mage.plugins.card.images;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.MageFrame;
import mage.client.dialog.DownloadImagesDialog;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.CardLanguage;
import mage.client.util.sets.ConstructedFormats;
import mage.remote.Connection;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileOutputStream;
import net.java.truevfs.access.TVFS;
import net.java.truevfs.kernel.spec.FsSyncException;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.dl.sources.*;
import org.mage.plugins.card.utils.CardImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.*;
import java.net.*;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

/**
 * @author JayDi85
 */
public class DownloadPicturesService extends DefaultBoundedRangeModel implements DownloadServiceInfo, Runnable {

    // don't forget to remove new sets from ignore.urls to download (properties file in resources)
    private static DownloadPicturesService instance;
    private static final Logger logger = Logger.getLogger(DownloadPicturesService.class);

    private static final String ALL_IMAGES = "- ALL images from selected source (can be slow)";
    private static final String ALL_MODERN_IMAGES = "- MODERN images (can be slow)";
    private static final String ALL_STANDARD_IMAGES = "- STANDARD images";
    private static final String ALL_TOKENS = "- TOKEN images";

    private static final int MAX_ERRORS_COUNT_BEFORE_CANCEL = 50;

    private final DownloadImagesDialog uiDialog;
    private boolean needCancel;
    private int errorCount;
    private int cardIndex;

    private List<CardInfo> cardsAll;
    private List<CardDownloadData> cardsMissing;
    private final List<CardDownloadData> cardsDownloadQueue;
    private int missingCardsCount = 0;
    private int missingTokensCount = 0;

    private final List<String> selectedSets = new ArrayList<>();
    private static CardImageSource selectedSource;

    private final Object sync = new Object();
    private Proxy proxy = Proxy.NO_PROXY;

    enum DownloadSources {
        WIZARDS("1. wizards.com - low quality CARDS, multi-language, slow download", WizardCardsImageSource.instance),
        TOKENS("2. tokens.mtg.onl - high quality TOKENS", TokensMtgImageSource.instance),
        SCRYFALL("3. scryfall.com - high quality CARDS and TOKENS, multi-language", ScryfallImageSource.instance),
        MAGIDEX("4. magidex.com - high quality CARDS", MagidexImageSource.instance),
        GRAB_BAG("5. GrabBag - STAR WARS cards and tokens", GrabbagImageSource.instance),
        MYTHICSPOILER("6. mythicspoiler.com", MythicspoilerComSource.instance),
        ALTERNATIVE("7. alternative.mtg.onl", AltMtgOnlTokensImageSource.instance),
        COPYPASTE("8. Copy and Paste Image URLs", CopyPasteImageSource.instance);
        // MTG_ONL("mtg.onl", MtgOnlTokensImageSource.instance), Not working correctly yet

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

    public static void main(String[] args) {
        startDownload();
    }

    public static void startDownload() {
        // load images info in background task
        instance = new DownloadPicturesService(MageFrame.getInstance());
        new Thread(new LoadMissingCardDataNew(instance)).start();

        // show dialog
        instance.setNeedCancel(false);
        instance.resetErrorCount();
        instance.uiDialog.showDialog();
        instance.uiDialog.dispose();
        instance.setNeedCancel(true);

        // IMAGES CHECK (download process can broke some files, so fix it here too)
        // code executes on cancel/close download dialog (but not executes on app's close -- it's ok)
        logger.info("Images: search broken files...");
        CardImageUtils.checkAndFixImageFiles();
    }

    @Override
    public boolean isNeedCancel() {
        return this.needCancel || (this.errorCount > MAX_ERRORS_COUNT_BEFORE_CANCEL);
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

    public DownloadPicturesService(JFrame frame) {
        // init service and dialog
        cardsAll = Collections.synchronizedList(new ArrayList<>());
        cardsMissing = Collections.synchronizedList(new ArrayList<>());
        cardsDownloadQueue = Collections.synchronizedList(new ArrayList<>());
        uiDialog = new DownloadImagesDialog();

        // MESSAGE
        uiDialog.setGlobalInfo("Initializing image download...");

        // SOURCES - scryfall is default source
        uiDialog.getSourcesCombo().setModel(new DefaultComboBoxModel(DownloadSources.values()));
        uiDialog.getSourcesCombo().setSelectedItem(DownloadSources.SCRYFALL);
        selectedSource = ScryfallImageSource.instance;
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
        uiDialog.getDownloadThreadsCombo().setSelectedItem(PreferencesDialog.getPrefDownloadThreads().toString());

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
        uiDialog.getCancelButton().addActionListener(e -> uiDialog.setVisible(false));
        uiDialog.getStopButton().addActionListener(e -> uiDialog.setVisible(false));

        // PROGRESS BAR
        uiDialog.getProgressBar().setValue(0);
    }

    public void findMissingCards() {
        updateGlobalMessage("Loading...");
        this.cardsAll.clear();
        this.cardsMissing.clear();
        this.cardsDownloadQueue.clear();

        updateGlobalMessage("Loading cards list...");
        this.cardsAll = Collections.synchronizedList(CardRepository.instance.findCards(new CardCriteria()));

        updateGlobalMessage("Finding missing images...");
        this.cardsMissing = prepareMissingCards(this.cardsAll, uiDialog.getRedownloadCheckbox().isSelected());

        updateGlobalMessage("Finding available sets from selected source...");
        this.uiDialog.getSetsCombo().setModel(new DefaultComboBoxModel<>(getSetsForCurrentImageSource()));
        reloadCardsToDownload(this.uiDialog.getSetsCombo().getSelectedItem().toString());

        this.uiDialog.showDownloadControls(true);
        updateGlobalMessage("");
        showDownloadControls(true);
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

    @Override
    public void showDownloadControls(boolean needToShow) {
        // auto-size form on show
        this.uiDialog.showDownloadControls(needToShow);
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
                if (selectedSource.isTokenSource()
                        && selectedSource.isTokenImageProvided(data.getSet(), data.getName(), data.getType())
                        && selectedSets.contains(data.getSet())) {
                    numberTokenImagesAvailable++;
                    cardsDownloadQueue.add(data);
                }
            } else {
                if (!onlyTokens
                        && selectedSource.isCardSource()
                        && selectedSource.isCardImageProvided(data.getSet(), data.getName())
                        && selectedSets.contains(data.getSet())) {
                    numberCardImagesAvailable++;
                    cardsDownloadQueue.add(data);
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
            this.cardsMissing = prepareMissingCards(this.cardsAll, uiDialog.getRedownloadCheckbox().isSelected());
            reloadCardsToDownload(uiDialog.getSetsCombo().getSelectedItem().toString());
        } finally {
            MageFrame.getDesktop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void updateProgressText(int cardCount, int tokenCount) {
        missingTokensCount = 0;
        for (CardDownloadData card : cardsMissing) {
            if (card.isToken()) {
                missingTokensCount++;
            }
        }
        missingCardsCount = cardsMissing.size() - missingTokensCount;

        uiDialog.setCurrentInfo("Missing: " + missingCardsCount + " card images / " + missingTokensCount + " token images");
        int imageSum = cardCount + tokenCount;
        float mb = (imageSum * selectedSource.getAverageSize()) / 1024;
        updateProgressMessage(String.format(
                cardIndex == imageSum
                        ? "%d of %d (%d cards/%d tokens) image downloads finished! Please close!"
                        : "%d of %d (%d cards/%d tokens) image downloads finished! Please wait! [%.1f Mb]",
                0, imageSum, cardCount, tokenCount, mb
        ), 0, imageSum);
    }

    private static String createDownloadName(CardInfo card) {
        String className = card.getClassName();
        return className.substring(className.lastIndexOf('.') + 1);
    }

    private static List<CardDownloadData> prepareMissingCards(List<CardInfo> allCards, boolean redownloadMode) {

        // get filter for Standard Type 2 cards
        Set<String> type2SetsFilter = new HashSet<>();
        List<String> constructedFormats = ConstructedFormats.getSetsByFormat(ConstructedFormats.STANDARD);
        if (constructedFormats != null && !constructedFormats.isEmpty()) {
            type2SetsFilter.addAll(constructedFormats);
        } else {
            logger.warn("No formats defined. Try connecting to a server first!");
        }

        // prepare checking list
        List<CardDownloadData> allCardsUrls = Collections.synchronizedList(new ArrayList<>());
        try {
            allCards.parallelStream().forEach(card -> {
                if (!card.getCardNumber().isEmpty() && !"0".equals(card.getCardNumber()) && !card.getSetCode().isEmpty()) {
                    String cardName = card.getName();
                    boolean isType2 = type2SetsFilter.contains(card.getSetCode());
                    CardDownloadData url = new CardDownloadData(cardName, card.getSetCode(), card.getCardNumber(), card.usesVariousArt(), 0, false, card.isDoubleFaced(), card.isNightCard());

                    // variations must have diff file names with additional postfix
                    if (url.getUsesVariousArt()) {
                        url.setDownloadName(createDownloadName(card));
                    }

                    url.setFlipCard(card.isFlipCard());
                    url.setSplitCard(card.isSplitCard());
                    url.setType2(isType2);

                    // main side
                    allCardsUrls.add(url);

                    // second side
                    // xmage doesn't search night cards by default, so add it and other types manually
                    if (card.isDoubleFaced()) {
                        if (card.getSecondSideName() == null || card.getSecondSideName().trim().isEmpty()) {
                            throw new IllegalStateException("Second side card can't have empty name.");
                        }

                        CardInfo secondSideCard = CardRepository.instance.findCardWithPreferredSetAndNumber(card.getSecondSideName(), card.getSetCode(), card.getCardNumber());
                        if (secondSideCard == null) {
                            throw new IllegalStateException("Can''t find second side card in database: " + card.getSecondSideName());
                        }

                        url = new CardDownloadData(
                                card.getSecondSideName(),
                                card.getSetCode(),
                                secondSideCard.getCardNumber(),
                                card.usesVariousArt(),
                                0, false, card.isDoubleFaced(), true);
                        url.setType2(isType2);
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
                                0, false, card.isDoubleFaced(), card.isNightCard());
                        cardDownloadData.setFlipCard(true);
                        cardDownloadData.setFlippedSide(true);
                        cardDownloadData.setType2(isType2);
                        allCardsUrls.add(cardDownloadData);
                    }
                    if (card.getMeldsToCardName() != null) {
                        if (card.getMeldsToCardName().trim().isEmpty()) {
                            throw new IllegalStateException("MeldsToCardName can't be empty in " + card.getName());
                        }

                        CardInfo meldsToCard = CardRepository.instance.findCardWithPreferredSetAndNumber(card.getMeldsToCardName(), card.getSetCode(), card.getCardNumber());
                        if (meldsToCard == null) {
                            throw new IllegalStateException("Can''t find meldsToCard in database: " + card.getMeldsToCardName());
                        }

                        // meld cards are normal cards from the set, so no needs to set two faces/sides here
                        url = new CardDownloadData(
                                card.getMeldsToCardName(),
                                card.getSetCode(),
                                meldsToCard.getCardNumber(),
                                card.usesVariousArt(),
                                0, false, false, false);
                        url.setType2(isType2);
                        allCardsUrls.add(url);
                    }
                    if (card.isModalDoubleFacesCard()) {
                        if (card.getModalDoubleFacesSecondSideName() == null || card.getModalDoubleFacesSecondSideName().trim().isEmpty()) {
                            throw new IllegalStateException("MDF card can't have empty name.");
                        }
                        CardDownloadData cardDownloadData = new CardDownloadData(
                                card.getModalDoubleFacesSecondSideName(),
                                card.getSetCode(),
                                card.getCardNumber(),
                                card.usesVariousArt(),
                                0, false, true, true);
                        cardDownloadData.setType2(isType2);
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

            allCardsUrls.addAll(getTokenCardUrls());
        } catch (Exception e) {
            logger.error(e);
        }

        // find missing files
        List<CardDownloadData> cardsToDownload = Collections.synchronizedList(new ArrayList<>());
        allCardsUrls.parallelStream().forEach(card -> {
            if (redownloadMode) {
                // need all cards
                cardsToDownload.add(card);
            } else {
                // need missing cards
                File file = new TFile(CardImageUtils.buildImagePathToCardOrToken(card));
                if (!file.exists()) {
                    cardsToDownload.add(card);
                }
            }
        });

        return Collections.synchronizedList(new ArrayList<>(cardsToDownload));
    }

    public static List<CardDownloadData> getTokenCardUrls() throws RuntimeException {
        List<CardDownloadData> list = new ArrayList<>();
        InputStream in = DownloadPicturesService.class.getClassLoader().getResourceAsStream("card-pictures-tok.txt");

        if (in == null) {
            logger.error("resources input stream is null");
            return list;
        }

        try (InputStreamReader input = new InputStreamReader(in);
             BufferedReader reader = new BufferedReader(input)) {

            String line = reader.readLine();
            while (line != null) {
                line = line.trim();
                if (line.startsWith("|")) { // new format
                    String[] params = line.split("\\|", -1);
                    if (params.length >= 5) {
                        int type = 0;
                        if (params[4] != null && !params[4].isEmpty()) {
                            type = Integer.parseInt(params[4].trim()); // token number for same names
                        }
                        String fileName = "";
                        if (params.length > 5 && params[5] != null && !params[5].isEmpty()) {
                            fileName = params[5].trim();
                        }
                        String tokenClassName = "";
                        if (params.length > 7 && params[6] != null && !params[6].isEmpty()) {
                            tokenClassName = params[6].trim();
                        }

                        if (params[1].toLowerCase(Locale.ENGLISH).equals("generate") && params[2].startsWith("TOK:")) {
                            String set = params[2].substring(4);
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type,true);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else if (params[1].toLowerCase(Locale.ENGLISH).equals("generate") && params[2].startsWith("EMBLEM:")) {
                            String set = params[2].substring(7);
                            CardDownloadData card = new CardDownloadData("Emblem " + params[3], set, "0", false, type, true, fileName);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else if (params[1].toLowerCase(Locale.ENGLISH).equals("generate") && params[2].startsWith("EMBLEM-:")) {
                            String set = params[2].substring(8);
                            CardDownloadData card = new CardDownloadData(params[3] + " Emblem", set, "0", false, type,true, fileName);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else if (params[1].toLowerCase(Locale.ENGLISH).equals("generate") && params[2].startsWith("EMBLEM!:")) {
                            String set = params[2].substring(8);
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type, true, fileName);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else if (params[1].toLowerCase(Locale.ENGLISH).equals("generate") && params[2].startsWith("PLANE:")) {
                            String set = params[2].substring(6);
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type, true, fileName);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else if (params[1].toLowerCase(Locale.ENGLISH).equals("generate") && params[2].startsWith("DUNGEON:")) {
                            String set = params[2].substring(8);
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type, true, fileName);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else {
                            logger.error("wrong line format in tokens file: " + line);
                        }
                    } else {
                        logger.error("wrong line data in tokens file: " + line);
                    }
                }
                line = reader.readLine();
            }

        } catch (Exception ex) {
            logger.error(ex);
            throw new RuntimeException("DownloadPicturesService : readFile() error");
        }

        // TODO: delete and move to copy-pate images download mode
        /*
        for (CardDownloadData card : list) {
            if (card.isToken()) {
                System.out.println(card.getSet() + "/" + card.getName() + (!card.getType().equals(0) ? "/" + card.getType() : ""));
            }
        }
        */

        return list;
    }

    @Override
    public void run() {
        this.cardIndex = 0;
        this.resetErrorCount();

        File base = new File(getImagesDir());
        if (!base.exists()) {
            base.mkdir();
        }

        Connection.ProxyType configProxyType = Connection.ProxyType.valueByText(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_TYPE, "None"));
        Proxy.Type type = Proxy.Type.DIRECT;
        switch (configProxyType) {
            case HTTP:
                type = Proxy.Type.HTTP;
                break;
            case SOCKS:
                type = Proxy.Type.SOCKS;
                break;
            case NONE:
            default:
                proxy = Proxy.NO_PROXY;
                break;
        }

        if (type != Proxy.Type.DIRECT) {
            try {
                String address = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_ADDRESS, "");
                Integer port = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_PORT, "80"));
                proxy = new Proxy(type, new InetSocketAddress(address, port));
            } catch (Exception ex) {
                throw new RuntimeException("Gui_DownloadPicturesService : error 1 - " + ex);
            }
        }

        int downloadThreadsAmount = Integer.parseInt((String) uiDialog.getDownloadThreadsCombo().getSelectedItem());

        if (proxy != null) {
            logger.info("Started download of " + cardsDownloadQueue.size() + " images"
                    + " from source: " + selectedSource.getSourceName()
                    + ", language: " + selectedSource.getCurrentLanguage().getCode()
                    + ", threads: " + downloadThreadsAmount);
            updateProgressMessage("Preparing download list...");
            if (selectedSource.prepareDownloadList(this, cardsDownloadQueue)) {
                update(0, cardsDownloadQueue.size());
                ExecutorService executor = Executors.newFixedThreadPool(downloadThreadsAmount);
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
                while (!executor.isTerminated()) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ie) {
                    }
                }
            }
        }

        try {
            TVFS.umount();
        } catch (FsSyncException e) {
            logger.fatal("Couldn't unmount zip files", e);
            JOptionPane.showMessageDialog(null, "Couldn't unmount zip files", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            //
        }

        // stop
        reloadCardsToDownload(uiDialog.getSetsCombo().getSelectedItem().toString());
        enableDialogButtons();

        // reset images cache
        ImageCache.clearCache();
    }

    static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
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
            useSpecifiedPaths = false;
        }

        DownloadTask(CardDownloadData card, String baseUrl, String actualFilename, int count) {
            this.card = card;
            this.urls = new CardImageUrls(baseUrl, null);
            this.count = count;
            this.actualFilename = actualFilename;
            useSpecifiedPaths = true;
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

                // FILE already exists (in zip or in dir)
                // don't use, images can be re-downloaded
                /*
                if (destFile.exists()) {
                    synchronized (sync) {
                        update(cardIndex + 1, count);
                    }
                    return;
                }
                */

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
                if (this.urls != null) {
                    downloadUrls = this.urls.getDownloadList();
                } else {
                    downloadUrls = new ArrayList<>();
                }

                boolean isDownloadOK = false;
                URLConnection httpConn = null;
                List<String> errorsList = new ArrayList<>();
                for (String currentUrl : downloadUrls) {
                    URL url = new URL(currentUrl);

                    // on download cancel need to stop
                    if (DownloadPicturesService.getInstance().isNeedCancel()) {
                        return;
                    }

                    // download
                    selectedSource.doPause(url.getPath());

                    httpConn = url.openConnection(proxy);
                    if (httpConn != null) {

                        httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                        try {
                            httpConn.connect();
                        } catch (SocketException e) {
                            incErrorCount();
                            errorsList.add("Wrong image URL or java app is not allowed to use network. Check your firewall or proxy settings. Error: " + e.getMessage() + ". Image URL: " + url.toString());
                            break;
                        } catch (UnknownHostException e) {
                            incErrorCount();
                            errorsList.add("Unknown site. Check your DNS settings. Error: " + e.getMessage() + ". Image URL: " + url.toString());
                            break;
                        }
                        int responseCode = ((HttpURLConnection) httpConn).getResponseCode();

                        // check result
                        if (responseCode != 200) {
                            // show errors only on full fail (all urls were not work)
                            String info = String.format("Image download failed for %s - %s%s, http code: %d, url: %s",
                                    card.getSet(),
                                    card.getName(),
                                    (card.getDownloadName().equals(card.getName()) ? "" : ", cardDownloadName: " + card.getDownloadName()),
                                    responseCode,
                                    url
                            );
                            errorsList.add(info);

                            if (logger.isDebugEnabled()) {
                                // Shows the returned html from the request to the web server
                                logger.debug("Returned HTML ERROR:\n" + convertStreamToString(((HttpURLConnection) httpConn).getErrorStream()));
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

                // can save result
                if (isDownloadOK && httpConn != null) {
                    // save data to temp
                    try (InputStream in = new BufferedInputStream(httpConn.getInputStream());
                         OutputStream tfileout = new TFileOutputStream(fileTempImage);
                         OutputStream out = new BufferedOutputStream(tfileout)) {
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) != -1) {
                            // user cancelled
                            if (DownloadPicturesService.getInstance().isNeedCancel()) {
                                // stop download, save current state and exit
                                TFile archive = destFile.getTopLevelArchive();
                                ///* not need to unmout/close - it's auto action
                                if (archive != null && archive.exists()) {
                                    logger.info("User canceled download. Closing archive file: " + destFile.toString());
                                    try {
                                        TVFS.umount(archive);
                                    } catch (Exception e) {
                                        logger.error("Can't close archive file: " + e.getMessage(), e);
                                    }
                                }
                                try {
                                    TFile.rm(fileTempImage);
                                } catch (Exception e) {
                                    logger.error("Can't delete temp file: " + e.getMessage(), e);
                                }
                                return;
                            }
                            out.write(buf, 0, len);
                        }
                    }
                    // TODO: add two faces card correction? (WTF)
                    // SAVE final data
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
                logger.error("Unknown error: " + e.getMessage(), e);
            } finally {
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
            float mb = ((needDownloadCount - lastCardIndex) * selectedSource.getAverageSize()) / 1024;
            updateProgressMessage(String.format("%d of %d image downloads finished! Please wait! [%.1f Mb]",
                    lastCardIndex, needDownloadCount, mb), lastCardIndex, needDownloadCount);
        } else {
            // finished
            List<CardDownloadData> downloadedCards = Collections.synchronizedList(new ArrayList<>());
            DownloadPicturesService.this.cardsMissing.parallelStream().forEach(cardDownloadData -> {
                TFile file = new TFile(CardImageUtils.buildImagePathToCardOrToken(cardDownloadData));
                if (file.exists()) {
                    downloadedCards.add(cardDownloadData);
                }
            });

            // remove all downloaded cards, missing must be remains
            this.cardsDownloadQueue.removeAll(downloadedCards);
            this.cardsMissing.removeAll(downloadedCards);

            if (this.cardsDownloadQueue.isEmpty()) {
                // stop download
                updateProgressMessage("0 images remaining. Please close.");
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
    public Proxy getProxy() {
        return proxy;
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

    public static void main() {
        (new Thread(new LoadMissingCardDataNew(downloadPicturesService))).start();
    }
}
