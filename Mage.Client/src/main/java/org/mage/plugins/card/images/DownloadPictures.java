package org.mage.plugins.card.images;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.*;
import java.net.*;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.sets.ConstructedFormats;
import mage.remote.Connection;
import mage.util.StreamUtils;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileOutputStream;
import net.java.truevfs.access.TVFS;
import net.java.truevfs.kernel.spec.FsSyncException;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.sources.*;
import org.mage.plugins.card.properties.SettingsManager;
import org.mage.plugins.card.utils.CardImageUtils;

import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

public class DownloadPictures extends DefaultBoundedRangeModel implements Runnable {

    // don't forget to remove new sets from ignore.urls to download (propeties file in resources)
    private static DownloadPictures instance;

    private static final Logger logger = Logger.getLogger(DownloadPictures.class);

    public static final String ALL_IMAGES = "- ALL images from selected source (CAN BE VERY SLOW)";
    public static final String ALL_STANDARD_IMAGES = "- Only images from STANDARD sets";
    public static final String ALL_TOKENS = "- Only token images from selected source";

    private JDialog dialog;
    private final JProgressBar bar;
    private final JOptionPane dlg;
    private boolean cancel;
    private final JButton closeButton;
    private final JButton startDownloadButton;
    private int cardIndex;
    private List<CardDownloadData> allCardsMissingImage;
    private List<CardDownloadData> cardsToDownload;

    private int missingCards = 0;
    private int missingTokens = 0;

    List<String> selectedSetCodes = new ArrayList<>();

    private final JComboBox jComboBoxServer;
    private final JLabel jLabelMessage;
    private final JLabel jLabelAllMissing;
    private final JLabel jLabelServer;

    private final JComboBox jComboBoxSet;
    private final JLabel jLabelSet;

    private final Object sync = new Object();

    private static CardImageSource cardImageSource;

    private Proxy p = Proxy.NO_PROXY;

    enum DownloadSources {
        WIZARDS("1. wizards.com - low quality CARDS, multi-language, can be SLOW", WizardCardsImageSource.instance),
        TOKENS("2. tokens.mtg.onl - high quality TOKENS", TokensMtgImageSource.instance),
        SCRYFALL("3. scryfall.com - high quality CARDS, multi-language", ScryfallImageSource.instance),
        MAGIDEX("4. magidex.com - high quality CARDS", MagidexImageSource.instance),
        GRAB_BAG("5. GrabBag - STAR WARS cards and tokens", GrabbagImageSource.instance),
        MYTHICSPOILER("6. mythicspoiler.com", MythicspoilerComSource.instance),
        ALTERNATIVE("7. alternative.mtg.onl", AltMtgOnlTokensImageSource.instance),
        COPYPASTE("8. Copy and Paste Image URLs", CopyPasteImageSource.instance);
        // MTG_ONL("mtg.onl", MtgOnlTokensImageSource.instance), Not working correctly yet
        // MAGICCARDS("magiccards.info", MagicCardsImageSource.instance)

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

    public static DownloadPictures getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        startDownload();
    }

    public static void startDownload() {

        /*
         * if (cards == null || cards.isEmpty()) {
         * JOptionPane.showMessageDialog(null,
         * "All card pictures have been downloaded."); return; }
         */
        instance = new DownloadPictures(MageFrame.getInstance());
        Thread t1 = new Thread(new LoadMissingCardData(instance));
        t1.start();
        instance.getDlg().setVisible(true);
        instance.getDlg().dispose();
        instance.cancel = true;
    }

    public JDialog getDlg() {
        return dialog;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    static int WIDTH = 400;

    public DownloadPictures(JFrame frame) {

        cardsToDownload = new ArrayList<>();

        JPanel p0 = new JPanel();
        p0.setLayout(new BoxLayout(p0, BoxLayout.Y_AXIS));

        p0.add(Box.createVerticalStrut(5));

        jLabelMessage = new JLabel();
        jLabelMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabelMessage.setText("Initializing image download...");
        p0.add(jLabelMessage);
        p0.add(Box.createVerticalStrut(5));

        jLabelAllMissing = new JLabel();

        jLabelAllMissing.setAlignmentX(Component.LEFT_ALIGNMENT);
        // jLabelAllMissing.setText("Computing number of missing images...");
        p0.add(jLabelAllMissing);
        p0.add(Box.createVerticalStrut(5));

        jLabelServer = new JLabel();
        jLabelServer.setText("Please select image source:");
        jLabelServer.setAlignmentX(Component.LEFT_ALIGNMENT);
        jLabelServer.setVisible(false);
        p0.add(jLabelServer);

        p0.add(Box.createVerticalStrut(5));

        jComboBoxServer = new JComboBox();
        jComboBoxServer.setModel(new DefaultComboBoxModel(DownloadSources.values()));
        jComboBoxServer.setAlignmentX(Component.LEFT_ALIGNMENT);
        jComboBoxServer.setAlignmentY(Component.LEFT_ALIGNMENT);
        jComboBoxServer.addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                comboBoxServerItemSelected(event);
            }
        });
        Dimension d = jComboBoxServer.getPreferredSize();
        d.width = WIDTH;
        jComboBoxServer.setPreferredSize(d);
        p0.add(jComboBoxServer);
        jComboBoxServer.setVisible(false);

        // set the first source as default
        cardImageSource = WizardCardsImageSource.instance;

        p0.add(Box.createVerticalStrut(5));

        // Set selection ---------------------------------
        jLabelSet = new JLabel();
        jLabelSet.setText("Please select sets to download the images for:");
        jLabelSet.setAlignmentX(Component.LEFT_ALIGNMENT);
        jLabelSet.setVisible(false);
        p0.add(jLabelSet);

        jComboBoxSet = new JComboBox();
//         jComboBoxSet.setModel(new DefaultComboBoxModel<>(getSetsForCurrentImageSource()));
        jComboBoxSet.setAlignmentX(Component.LEFT_ALIGNMENT);
        jComboBoxSet.addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                comboBoxSetItemSelected(event);
            }
        });

        p0.add(jComboBoxSet);
        jComboBoxSet.setVisible(false);

        p0.add(Box.createVerticalStrut(5));

        // Start
        startDownloadButton = new JButton("Start download");
        startDownloadButton.addActionListener(e -> {
            new Thread(DownloadPictures.this).start();
            startDownloadButton.setEnabled(false);
        });
        p0.add(Box.createVerticalStrut(5));

        // Progress
        bar = new JProgressBar(this);
        p0.add(bar);
        bar.setStringPainted(true);

        d = bar.getPreferredSize();
        d.width = WIDTH;
        bar.setPreferredSize(d);
        bar.setVisible(false);

        // JOptionPane
        Object[] options = {startDownloadButton, closeButton = new JButton("Cancel")};
        startDownloadButton.setVisible(false);
        closeButton.addActionListener(e -> dialog.setVisible(false));
        closeButton.setVisible(false);

        dlg = new JOptionPane(p0, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[1]);
        dialog = this.dlg.createDialog(frame, "Downloading images");
    }

    public void setAllMissingCards() {
        updateAndViewMessage("Get all available cards from the repository...");
        List<CardInfo> cards = CardRepository.instance.findCards(new CardCriteria());
        updateAndViewMessage("Check which images are missing ...");
        this.allCardsMissingImage = getNeededCards(cards);
        updateAndViewMessage("Check which images the current source is providing ...");
        jComboBoxSet.setModel(new DefaultComboBoxModel<>(getSetsForCurrentImageSource()));

        updateCardsToDownload(jComboBoxSet.getSelectedItem().toString());

        jComboBoxServer.setVisible(true);
        jLabelServer.setVisible(true);
        jComboBoxSet.setVisible(true);
        jLabelSet.setVisible(true);
        bar.setVisible(true);
        startDownloadButton.setVisible(true);
        closeButton.setVisible(true);

        updateAndViewMessage("");
    }

    private void comboBoxServerItemSelected(ItemEvent evt) {
        if (jComboBoxServer.isEnabled()) {
            cardImageSource = ((DownloadSources) evt.getItem()).getSource();
            // update the available sets / token comboBox
            jComboBoxSet.setModel(new DefaultComboBoxModel<>(getSetsForCurrentImageSource()));
            updateCardsToDownload(jComboBoxSet.getSelectedItem().toString());
        }
    }

    public void updateAndViewMessage(String text) {
        jLabelMessage.setText(text);
        if (dialog != null) {
            dialog.pack();
            dialog.validate();
            dialog.repaint();
        }
    }

    private Object[] getSetsForCurrentImageSource() {
        // Set the available sets to the combo box
        ArrayList<String> supportedSets = cardImageSource.getSupportedSets();
        List<String> setNames = new ArrayList<>();
        if (supportedSets != null) {
            setNames.add(ALL_IMAGES);
            setNames.add(ALL_STANDARD_IMAGES);
        }
        if (cardImageSource.isTokenSource()) {
            setNames.add(ALL_TOKENS);
        }
        if (supportedSets != null) {
            for (String setCode : supportedSets) {
                ExpansionSet expansionSet = Sets.findSet(setCode);
                if (expansionSet != null) {
                    setNames.add(expansionSet.getName());
                } else {
                    logger.warn("Source: " + cardImageSource.getSourceName() + ": Expansion set for code " + setCode + " not found in xmage sets!");
                }
            }

        }
        if (setNames.isEmpty()) {
            logger.error("Source " + cardImageSource.getSourceName() + " creates no selectable items.");
            setNames.add("not avalable");
        }
        return setNames.toArray(new String[0]);
    }

    private void updateCardsToDownload(String itemText) {
        selectedSetCodes.clear();
        switch (itemText) {
            case ALL_IMAGES:
                if (cardImageSource.getSupportedSets() == null) {
                    selectedSetCodes = cardImageSource.getSupportedSets();
                } else {
                    selectedSetCodes.addAll(cardImageSource.getSupportedSets());
                }
                break;
            case ALL_STANDARD_IMAGES:
                List<String> standardSets = ConstructedFormats.getSetsByFormat(ConstructedFormats.STANDARD);
                for (String setCode : cardImageSource.getSupportedSets()) {
                    if (standardSets.contains(setCode)) {
                        selectedSetCodes.add(setCode);
                    } else {
                        logger.debug("Set code " + setCode + " from download source " + cardImageSource.getSourceName());
                    }
                }
                break;
            case ALL_TOKENS:
                break;
            default:
                int nonSetEntries = 0;
                if (cardImageSource.getSupportedSets() != null) {
                    nonSetEntries = 2;
                }
                if (cardImageSource.isTokenSource()) {
                    nonSetEntries++;
                }
                selectedSetCodes.add(cardImageSource.getSupportedSets().get(jComboBoxSet.getSelectedIndex() - nonSetEntries));
        }
        cardsToDownload.clear();
        int numberTokenImagesAvailable = 0;
        int numberCardImagesAvailable = 0;
        for (CardDownloadData data : allCardsMissingImage) {
            if (data.isToken()) {
                if (cardImageSource.isTokenSource() && cardImageSource.isImageProvided(data.getSet(), data.getName())) {
                    numberTokenImagesAvailable++;
                    cardsToDownload.add(data);
                } else {
                    //logger.warn("Source do not support token (set " + data.getSet() + ", token " + data.getName() + ")");
                }
            } else {
                if (selectedSetCodes != null && selectedSetCodes.contains(data.getSet())) {
                    if (cardImageSource.isSetSupportedComplete(data.getSet()) || cardImageSource.isImageProvided(data.getSet(), data.getName())) {
                        numberCardImagesAvailable++;
                        cardsToDownload.add(data);
                    }
                }
            }
        }
        updateProgressText(numberCardImagesAvailable, numberTokenImagesAvailable);
    }

    private void comboBoxSetItemSelected(ItemEvent event) {
        // Update the cards to download related to the selected set
        updateCardsToDownload(event.getItem().toString());
    }

    private void updateProgressText(int cardCount, int tokenCount) {
        missingTokens = 0;
        for (CardDownloadData card : allCardsMissingImage) {
            if (card.isToken()) {
                missingTokens++;
            }
        }
        missingCards = allCardsMissingImage.size() - missingTokens;
        jLabelAllMissing.setText("Missing: " + missingCards + " card images / " + missingTokens + " token images");
        int imageSum = cardCount + tokenCount;
        float mb = (imageSum * cardImageSource.getAverageSize()) / 1024;
        bar.setString(String.format(cardIndex == imageSum ? "%d of %d (%d cards/%d tokens) image downloads finished! Please close!"
                : "%d of %d (%d cards/%d tokens) image downloads finished! Please wait! [%.1f Mb]", 0, imageSum, cardCount, tokenCount, mb));
    }

    private static String createDownloadName(CardInfo card) {
        String className = card.getClassName();
        return className.substring(className.lastIndexOf('.') + 1);
    }

    private static List<CardDownloadData> getNeededCards(List<CardInfo> allCards) {

        /**
         * read all card names and urls
         */
        HashSet<String> ignoreUrls = SettingsManager.getIntance().getIgnoreUrls();

        /**
         * get filter for Standard Type 2 cards
         */
        Set<String> type2SetsFilter = new HashSet<>();
        List<String> constructedFormats = ConstructedFormats.getSetsByFormat(ConstructedFormats.STANDARD);
        if (constructedFormats != null && !constructedFormats.isEmpty()) {
            type2SetsFilter.addAll(constructedFormats);
        } else {
            logger.warn("No formats defined. Try connecting to a server first!");
        }

        List<CardDownloadData> allCardsUrls = Collections.synchronizedList(new ArrayList<>());
        try {
            allCards.parallelStream().forEach(card -> {
                if (!card.getCardNumber().isEmpty() && !"0".equals(card.getCardNumber()) && !card.getSetCode().isEmpty()
                        && !ignoreUrls.contains(card.getSetCode())) {
                    String cardName = card.getName();
                    boolean isType2 = type2SetsFilter.contains(card.getSetCode());
                    CardDownloadData url = new CardDownloadData(cardName, card.getSetCode(), card.getCardNumber(), card.usesVariousArt(), 0, "", "", false, card.isDoubleFaced(), card.isNightCard());
                    if (url.getUsesVariousArt()) {
                        url.setDownloadName(createDownloadName(card));
                    }

                    url.setFlipCard(card.isFlipCard());
                    url.setSplitCard(card.isSplitCard());
                    url.setType2(isType2);

                    allCardsUrls.add(url);
                    if (card.isDoubleFaced()) {
                        if (card.getSecondSideName() == null || card.getSecondSideName().trim().isEmpty()) {
                            throw new IllegalStateException("Second side card can't have empty name.");
                        }

                        CardInfo secondSideCard = CardRepository.instance.findCard(card.getSecondSideName());
                        if (secondSideCard == null) {
                            throw new IllegalStateException("Can''t find second side card in database: " + card.getSecondSideName());
                        }

                        url = new CardDownloadData(card.getSecondSideName(), card.getSetCode(), secondSideCard.getCardNumber(), card.usesVariousArt(), 0, "", "", false, card.isDoubleFaced(), true);
                        url.setType2(isType2);
                        allCardsUrls.add(url);
                    }
                    if (card.isFlipCard()) {
                        if (card.getFlipCardName() == null || card.getFlipCardName().trim().isEmpty()) {
                            throw new IllegalStateException("Flipped card can't have empty name.");
                        }
                        CardDownloadData cardDownloadData = new CardDownloadData(card.getFlipCardName(), card.getSetCode(), card.getCardNumber(), card.usesVariousArt(), 0, "", "", false, card.isDoubleFaced(), card.isNightCard());
                        cardDownloadData.setFlipCard(true);
                        cardDownloadData.setFlippedSide(true);
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

        /**
         * check to see which cards we already have
         */
        List<CardDownloadData> cardsToDownload = Collections.synchronizedList(new ArrayList<>());
        allCardsUrls.parallelStream().forEach(card -> {
            File file = new TFile(CardImageUtils.buildImagePathToCard(card));
            logger.debug(card.getName() + " (is_token=" + card.isToken() + "). Image is here:" + file.getAbsolutePath() + " (exists=" + file.exists() + ')');
            if (!file.exists()) {
                logger.debug("Missing: " + file.getAbsolutePath());
                // logger.info("Missing image: " + (card.isToken() ? "TOKEN " : "CARD  ") + card.getSet() + "/" + card.getName() + "               type: " + card.getType());
                cardsToDownload.add(card);
            }
        });

        return new ArrayList<>(cardsToDownload);
    }

    public static ArrayList<CardDownloadData> getTokenCardUrls() throws RuntimeException {
        ArrayList<CardDownloadData> list = new ArrayList<>();
        InputStream in = DownloadPictures.class
                .getClassLoader().getResourceAsStream("card-pictures-tok.txt");

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
                            type = Integer.parseInt(params[4].trim());
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
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type, "", "", true);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                            // logger.debug("Token: " + set + "/" + card.getName() + " type: " + type);
                        } else if (params[1].toLowerCase(Locale.ENGLISH).equals("generate") && params[2].startsWith("EMBLEM:")) {
                            String set = params[2].substring(7);
                            CardDownloadData card = new CardDownloadData("Emblem " + params[3], set, "0", false, type, "", "", true, fileName);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else if (params[1].toLowerCase(Locale.ENGLISH).equals("generate") && params[2].startsWith("EMBLEM-:")) {
                            String set = params[2].substring(8);
                            CardDownloadData card = new CardDownloadData(params[3] + " Emblem", set, "0", false, type, "", "", true, fileName);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else if (params[1].toLowerCase(Locale.ENGLISH).equals("generate") && params[2].startsWith("EMBLEM!:")) {
                            String set = params[2].substring(8);
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type, "", "", true, fileName);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else if (params[1].toLowerCase(Locale.ENGLISH).equals("generate") && params[2].startsWith("PLANE:")) {
                            String set = params[2].substring(6);
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type, "", "", true, fileName);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        }
                    } else {
                        logger.error("wrong format for image urls: " + line);
                    }
                }
                line = reader.readLine();
            }

        } catch (Exception ex) {
            logger.error(ex);
            throw new RuntimeException("DownloadPictures : readFile() error");
        }
        return list;
    }

    @Override
    public void run() {
        this.cardIndex = 0;

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
                p = Proxy.NO_PROXY;
                break;
        }

        if (type != Proxy.Type.DIRECT) {
            try {
                String address = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_ADDRESS, "");
                Integer port = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_PORT, "80"));
                p = new Proxy(type, new InetSocketAddress(address, port));
            } catch (Exception ex) {
                throw new RuntimeException("Gui_DownloadPictures : error 1 - " + ex);
            }
        }

        if (p != null) {
            HashSet<String> ignoreUrls = SettingsManager.getIntance().getIgnoreUrls();

            update(0, cardsToDownload.size());
            logger.info("Started download of " + cardsToDownload.size() + " images from source: " + cardImageSource.getSourceName());

            int numberOfThreads = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_THREADS, "10"));
            ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
            for (int i = 0; i < cardsToDownload.size() && !cancel; i++) {
                try {

                    CardDownloadData card = cardsToDownload.get(i);

                    logger.debug("Downloading image: " + card.getName() + " (" + card.getSet() + ')');

                    CardImageUrls urls;

                    if (ignoreUrls.contains(card.getSet()) || card.isToken()) {
                        if (!"0".equals(card.getCollectorId())) {
                            continue;
                        }
                        urls = cardImageSource.generateTokenUrl(card);
                    } else {
                        urls = cardImageSource.generateURL(card);
                    }

                    if (urls == null) {
                        String imageRef = cardImageSource.getNextHttpImageUrl();
                        String fileName = cardImageSource.getFileForHttpImage(imageRef);
                        if (imageRef != null && fileName != null) {
                            imageRef = cardImageSource.getSourceName() + imageRef;
                            try {
                                card.setToken(cardImageSource.isTokenSource());
                                Runnable task = new DownloadTask(card, imageRef, fileName, cardImageSource.getTotalImages());
                                executor.execute(task);
                            } catch (Exception ex) {
                            }
                        } else if (cardImageSource.getTotalImages() == -1) {
                            logger.info("Image not available on " + cardImageSource.getSourceName() + ": " + card.getName() + " (" + card.getSet() + ')');
                            synchronized (sync) {
                                update(cardIndex + 1, cardsToDownload.size());
                            }
                        }
                    } else {
                        Runnable task = new DownloadTask(card, urls, cardsToDownload.size());
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
        try {
            TVFS.umount();
        } catch (FsSyncException e) {
            logger.fatal("Couldn't unmount zip files", e);
            JOptionPane.showMessageDialog(null, "Couldn't unmount zip files", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            //
        }
        closeButton.setText("Close");
        updateCardsToDownload(jComboBoxSet.getSelectedItem().toString());
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private final class DownloadTask implements Runnable {

        private final CardDownloadData card;
        private final CardImageUrls urls;
        private final int count;
        private final String actualFilename;
        private final boolean useSpecifiedPaths;

        DownloadTask(CardDownloadData card, String baseUrl, int count) {
            this(card, new CardImageUrls(baseUrl, null), count);
        }

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
            if (cancel) {
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
                    destFile = new TFile(CardImageUtils.buildImagePathToCard(card));
                }

                // FILE already exists (in zip or in dir)
                if (destFile.exists()) {
                    synchronized (sync) {
                        update(cardIndex + 1, count);
                    }
                    return;
                }

                // zip can't be read
                TFile testArchive = destFile.getTopLevelArchive();
                if (testArchive != null && testArchive.exists()) {
                    try {
                        testArchive.list();
                    } catch (Exception e) {
                        logger.error("Error reading archive, may be it was corrapted. Try to delete it: " + testArchive.toString());

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
                    if (cancel) {
                        return;
                    }

                    // download
                    cardImageSource.doPause(url.getPath());
                    httpConn = url.openConnection(p);
                    httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                    httpConn.connect();
                    int responseCode = ((HttpURLConnection) httpConn).getResponseCode();

                    // check result
                    if (responseCode != 200) {
                        // show errors only on full fail (all urls were not work)
                        errorsList.add("Image download for " + card.getName()
                                + (!card.getDownloadName().equals(card.getName()) ? " downloadname: " + card.getDownloadName() : "")
                                + " (" + card.getSet() + ") failed - responseCode: " + responseCode + " url: " + url.toString());

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

                // can save result
                if (isDownloadOK & httpConn != null) {
                    // save data to temp
                    OutputStream out = null;
                    OutputStream tfileout = null;
                    InputStream in = null;
                    try {
                        in = new BufferedInputStream(httpConn.getInputStream());
                        tfileout = new TFileOutputStream(fileTempImage);
                        out = new BufferedOutputStream(tfileout);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) != -1) {
                            // user cancelled
                            if (cancel) {
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
                    } finally {
                        StreamUtils.closeQuietly(in);
                        StreamUtils.closeQuietly(out);
                        StreamUtils.closeQuietly(tfileout);
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
                logger.error("Can't access to files: " + card.getName() + "(" + card.getSet() + "). Try rebooting your system to remove the file lock.");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
            }

            synchronized (sync) {
                update(cardIndex + 1, count);
            }
        }
    }

    private void update(int card, int count) {
        this.cardIndex = card;

        if (cardIndex < count) {
            float mb = ((count - card) * cardImageSource.getAverageSize()) / 1024;
            bar.setString(String.format("%d of %d image downloads finished! Please wait! [%.1f Mb]",
                    card, count, mb));
        } else {
            List<CardDownloadData> remainingCards = Collections.synchronizedList(new ArrayList<>());
            DownloadPictures.this.allCardsMissingImage.parallelStream().forEach(cardDownloadData -> {
                TFile file = new TFile(CardImageUtils.buildImagePathToCard(cardDownloadData));
                if (!file.exists()) {
                    remainingCards.add(cardDownloadData);
                }
            });

            // remove the cards not downloaded to get the siccessfull downloaded cards
            DownloadPictures.this.cardsToDownload.removeAll(remainingCards);
            DownloadPictures.this.allCardsMissingImage.removeAll(DownloadPictures.this.cardsToDownload);

            count = remainingCards.size();

            if (count == 0) {
                bar.setString("0 images remaining! Please close!");
            } else {
//                bar.setString(String.format("%d cards remaining! Please choose another source!", count));
                startDownloadButton.setEnabled(true);
            }
        }
    }

    private static final long serialVersionUID = 1L;

}

class LoadMissingCardData implements Runnable {

    private static DownloadPictures downloadPictures;

    public LoadMissingCardData(DownloadPictures downloadPictures) {
        LoadMissingCardData.downloadPictures = downloadPictures;
    }

    @Override
    public void run() {
        downloadPictures.setAllMissingCards();
    }

    public static void main() {

        (new Thread(new LoadMissingCardData(downloadPictures))).start();
    }

}
