package org.mage.plugins.card.images;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.sets.ConstructedFormats;
import mage.remote.Connection;
import static mage.remote.Connection.ProxyType.HTTP;
import static mage.remote.Connection.ProxyType.NONE;
import static mage.remote.Connection.ProxyType.SOCKS;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileOutputStream;
import net.java.truevfs.access.TVFS;
import net.java.truevfs.kernel.spec.FsSyncException;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.sources.*;
import org.mage.plugins.card.properties.SettingsManager;
import org.mage.plugins.card.utils.CardImageUtils;

public class DownloadPictures extends DefaultBoundedRangeModel implements Runnable {

    private static final Logger logger = Logger.getLogger(DownloadPictures.class);

    public static final String ALL_CARDS = "- All cards from that source";
    public static final String ALL_STANDARD_CARDS = "- All cards from standard from that source";
    public static final String ALL_TOKENS = "- All token images from that source";

    private final JProgressBar bar;
    private final JOptionPane dlg;
    private boolean cancel;
    private final JButton closeButton;
    private final JButton startDownloadButton;
    private int cardIndex;
    private List<CardDownloadData> allCardsMissingImage;
    List<CardDownloadData> cardsToDownload = new ArrayList<>();

    private int missingCards = 0;
    private int missingTokens = 0;

    List<String> selectedSetCodes = new ArrayList<>();

    private final JComboBox jComboBoxServer;
    private final JLabel jLabelAllMissing;
    private final JLabel jLabelServer;

    private final JComboBox jComboBoxSet;
    private final JLabel jLabelSet;

    private final Object sync = new Object();

    private static CardImageSource cardImageSource;

    private Proxy p = Proxy.NO_PROXY;

    enum DownloadSources {
        WIZARDS("wizards.com", WizardCardsImageSource.instance),
        MYTHICSPOILER("mythicspoiler.com", MythicspoilerComSource.instance),
        TOKENS("tokens.mtg.onl", TokensMtgImageSource.instance),
        MTG_ONL("mtg.onl", MtgOnlTokensImageSource.instance),
        ALTERNATIVE("alternative.mtg.onl", AltMtgOnlTokensImageSource.instance),
        GRAB_BAG("GrabBag", GrabbagImageSource.instance),
        MAGIDEX("magidex.com", MagidexImageSource.instance),
        SCRYFALL("scryfall.com", ScryfallImageSource.instance),
        MAGICCARDS("magiccards.info", MagicCardsImageSource.instance);

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

    public static void main(String[] args) {
        startDownload();
    }

    public static void startDownload() {

        /*
         * if (cards == null || cards.isEmpty()) {
         * JOptionPane.showMessageDialog(null,
         * "All card pictures have been downloaded."); return; }
         */
        DownloadPictures download = new DownloadPictures();
        JDialog dlg = download.getDlg(null);
        dlg.setVisible(true);
        dlg.dispose();
        download.cancel = true;
    }

    public JDialog getDlg(JFrame frame) {
        String title = "Downloading images";

        final JDialog dialog = this.dlg.createDialog(frame, title);
        closeButton.addActionListener(e -> dialog.setVisible(false));
        return dialog;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public DownloadPictures() {
        JPanel p0 = new JPanel();
        p0.setLayout(new BoxLayout(p0, BoxLayout.Y_AXIS));

        p0.add(Box.createVerticalStrut(5));

        jLabelAllMissing = new JLabel();

        jLabelAllMissing.setAlignmentX(Component.LEFT_ALIGNMENT);
        // jLabelAllMissing.setText("Computing number of missing images...");
        p0.add(jLabelAllMissing);
        p0.add(Box.createVerticalStrut(5));

        jLabelServer = new JLabel();
        jLabelServer.setText("Please select image source:");
        jLabelServer.setAlignmentX(Component.LEFT_ALIGNMENT);
        p0.add(jLabelServer);

        p0.add(Box.createVerticalStrut(5));

        jComboBoxServer = new JComboBox();
        jComboBoxServer.setModel(new DefaultComboBoxModel(DownloadSources.values()));
        jComboBoxServer.setAlignmentX(Component.LEFT_ALIGNMENT);
        jComboBoxServer.addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                comboBoxServerItemSelected(event);
            }
        });
        p0.add(jComboBoxServer);
        // set the first source as default
        cardImageSource = WizardCardsImageSource.instance;

        p0.add(Box.createVerticalStrut(5));

        // Set selection ---------------------------------
        jLabelSet = new JLabel();
        jLabelSet.setText("Please select sets to download the images for:");
        jLabelSet.setAlignmentX(Component.LEFT_ALIGNMENT);
        p0.add(jLabelSet);

        jComboBoxSet = new JComboBox();
        jComboBoxSet.setModel(new DefaultComboBoxModel<>(getSetsForCurrentImageSource()));
        jComboBoxSet.setAlignmentX(Component.LEFT_ALIGNMENT);
        jComboBoxSet.addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                comboBoxSetItemSelected(event);
            }
        });

        p0.add(jComboBoxSet);

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

        Dimension d = bar.getPreferredSize();
        d.width = 300;
        bar.setPreferredSize(d);

        // JOptionPane
        Object[] options = {startDownloadButton, closeButton = new JButton("Cancel")};
        dlg = new JOptionPane(p0, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[1]);

        setAllMissingCards();
    }

    public void setAllMissingCards() {
        List<CardInfo> cards = CardRepository.instance.findCards(new CardCriteria());
        this.allCardsMissingImage = getNeededCards(cards);
        updateCardsToDownload(jComboBoxSet.getSelectedItem().toString());
    }

    private void comboBoxServerItemSelected(ItemEvent evt) {
        cardImageSource = ((DownloadSources) evt.getItem()).getSource();
        // update the available sets / token comboBox
        jComboBoxSet.setModel(new DefaultComboBoxModel<>(getSetsForCurrentImageSource()));
        updateCardsToDownload(jComboBoxSet.getSelectedItem().toString());
    }

    private Object[] getSetsForCurrentImageSource() {
        // Set the available sets to the combo box
        ArrayList<String> supportedSets = cardImageSource.getSupportedSets();
        List<String> setNames = new ArrayList<>();
        if (supportedSets != null) {
            setNames.add(ALL_CARDS);
            setNames.add(ALL_STANDARD_CARDS);
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
                    logger.error(cardImageSource.getSourceName() + ": Expansion set for code " + setCode + " not found!");
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
        boolean tokens = false;
        switch (itemText) {
            case ALL_CARDS:
                if (cardImageSource.getSupportedSets() == null) {
                    selectedSetCodes = cardImageSource.getSupportedSets();
                } else {
                    selectedSetCodes.addAll(cardImageSource.getSupportedSets());
                }
                break;
            case ALL_STANDARD_CARDS:
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
                tokens = true;
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
        for (CardDownloadData data : allCardsMissingImage) {
            if ((data.isToken() && tokens)
                    || (!data.isToken() && selectedSetCodes != null && selectedSetCodes.contains(data.getSet()))) {
                cardsToDownload.add(data);
            }
        }
        int numberTokenImagesAvailable = 0;
        if (tokens) {
            cardImageSource.getTokenImages();
        }
        updateProgressText(cardsToDownload.size() + numberTokenImagesAvailable);
    }

    private void comboBoxSetItemSelected(ItemEvent event) {
        // Update the cards to download related to the selected set
        updateCardsToDownload(event.getItem().toString());
    }

    private void updateProgressText(int cardCount) {
        missingTokens = 0;
        for (CardDownloadData card : allCardsMissingImage) {
            if (card.isToken()) {
                missingTokens++;
            }
        }
        missingCards = allCardsMissingImage.size() - missingTokens;
        jLabelAllMissing.setText("Missing: " + missingCards + " card images / " + missingTokens + " token images");

        float mb = (cardCount * cardImageSource.getAverageSize()) / 1024;
        bar.setString(String.format(cardIndex == cardCount ? "%d of %d image downloads finished! Please close!"
                : "%d of %d image downloads finished! Please wait! [%.1f Mb]", 0, cardCount, mb));
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
                        url = new CardDownloadData(card.getSecondSideName(), card.getSetCode(), card.getCardNumber(), card.usesVariousArt(), 0, "", "", false, card.isDoubleFaced(), true);
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
                    System.err.println("There was a critical error!");
                    logger.error("Card has no collector ID and won't be sent to client: " + card.getName());
                } else if (card.getSetCode().isEmpty()) {
                    System.err.println("There was a critical error!");
                    logger.error("Card has no set name and won't be sent to client:" + card.getName());
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
            TFile file = new TFile(CardImageUtils.generateImagePath(card));
            logger.debug(card.getName() + " (is_token=" + card.isToken() + "). Image is here:" + file.getAbsolutePath() + " (exists=" + file.exists() + ')');
            if (!file.exists()) {
                logger.debug("Missing: " + file.getAbsolutePath());
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

                        if (params[1].toLowerCase().equals("generate") && params[2].startsWith("TOK:")) {
                            String set = params[2].substring(4);
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type, "", "", true);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else if (params[1].toLowerCase().equals("generate") && params[2].startsWith("EMBLEM:")) {
                            String set = params[2].substring(7);
                            CardDownloadData card = new CardDownloadData("Emblem " + params[3], set, "0", false, type, "", "", true, fileName);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else if (params[1].toLowerCase().equals("generate") && params[2].startsWith("EMBLEM-:")) {
                            String set = params[2].substring(8);
                            CardDownloadData card = new CardDownloadData(params[3] + " Emblem", set, "0", false, type, "", "", true, fileName);
                            card.setTokenClassName(tokenClassName);
                            list.add(card);
                        } else if (params[1].toLowerCase().equals("generate") && params[2].startsWith("EMBLEM!:")) {
                            String set = params[2].substring(8);
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

        File base = new File(Constants.IO.imageBaseDir);
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

                    String url;
                    if (ignoreUrls.contains(card.getSet()) || card.isToken()) {
                        if (!"0".equals(card.getCollectorId())) {
                            continue;
                        }
                        url = cardImageSource.generateTokenUrl(card);
                    } else {
                        url = cardImageSource.generateURL(card);
                    }

                    if (url == null) {
                        String imageRef = cardImageSource.getNextHttpImageUrl();
                        String fileName = cardImageSource.getFileForHttpImage(imageRef);
                        if (imageRef != null && fileName != null) {
                            imageRef = cardImageSource.getSourceName() + imageRef;
                            try {
                                URL imageUrl = new URL(imageRef);
                                card.setToken(cardImageSource.isTokenSource());
                                Runnable task = new DownloadTask(card, imageUrl, fileName, cardImageSource.getTotalImages());
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
                        Runnable task = new DownloadTask(card, new URL(url), cardsToDownload.size());
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
            System.gc();
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
        private final URL url;
        private final int count;
        private final String actualFilename;
        private final boolean useSpecifiedPaths;

        DownloadTask(CardDownloadData card, URL url, int count) {
            this.card = card;
            this.url = url;
            this.count = count;
            this.actualFilename = "";
            useSpecifiedPaths = false;
        }

        DownloadTask(CardDownloadData card, URL url, String actualFilename, int count) {
            this.card = card;
            this.url = url;
            this.count = count;
            this.actualFilename = actualFilename;
            useSpecifiedPaths = true;
        }

        @Override
        public void run() {
            StringBuilder filePath = new StringBuilder();
            File temporaryFile = null;
            TFile outputFile = null;
            try {
                filePath.append(Constants.IO.imageBaseDir);
                if (!useSpecifiedPaths && card != null) {
                    filePath.append(card.hashCode()).append('.').append(card.getName().replace(":", "").replace("//", "-")).append(".jpg");
                    temporaryFile = new File(filePath.toString());
                }
                String imagePath;
                if (useSpecifiedPaths) {
                    if (card != null && card.isToken()) {
                        imagePath = CardImageUtils.getTokenBasePath() + actualFilename;
                    } else if (card != null) {
                        imagePath = CardImageUtils.getImageBasePath() + actualFilename;
                    } else {
                        imagePath = Constants.IO.imageBaseDir;
                    }

                    String tmpFile = filePath + "temporary" + actualFilename;
                    temporaryFile = new File(tmpFile);
                    if (!temporaryFile.exists()) {
                        temporaryFile.getParentFile().mkdirs();
                    }
                } else {
                    imagePath = CardImageUtils.generateImagePath(card);
                }

                outputFile = new TFile(imagePath);
                if (!outputFile.exists()) {
                    outputFile.getParentFile().mkdirs();
                }
                File existingFile = new File(imagePath.replaceFirst("\\w{3}.zip", ""));
                if (existingFile.exists()) {
                    try {
                        new TFile(existingFile).cp_rp(outputFile);
                    } catch (IOException e) {
                        logger.error("Error while copying file " + card.getName(), e);
                    }
                    synchronized (sync) {
                        update(cardIndex + 1, count);
                    }
                    existingFile.delete();
                    File parent = existingFile.getParentFile();
                    if (parent != null && parent.isDirectory() && parent.list().length == 0) {
                        parent.delete();
                    }
                    return;
                }

                // Logger.getLogger(this.getClass()).info(url.toString());
                boolean useTempFile = false;
                int responseCode = 0;
                URLConnection httpConn = null;

                if (temporaryFile != null && temporaryFile.length() > 100) {
                    useTempFile = true;
                } else {
                    cardImageSource.doPause(url.getPath());
                    httpConn = url.openConnection(p);
                    httpConn.connect();
                    responseCode = ((HttpURLConnection) httpConn).getResponseCode();
                }

                if (responseCode == 200 || useTempFile) {
                    if (!useTempFile) {
                        BufferedOutputStream out;
                        try (BufferedInputStream in = new BufferedInputStream(httpConn.getInputStream())) {
                            //try (BufferedInputStream in = new BufferedInputStream(url.openConnection(p).getInputStream())) {
                            out = new BufferedOutputStream(new TFileOutputStream(temporaryFile));
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) != -1) {
                                // user cancelled
                                if (cancel) {
                                    in.close();
                                    out.flush();
                                    out.close();
                                    temporaryFile.delete();
                                    return;
                                }
                                out.write(buf, 0, len);
                            }

                        }
                        out.flush();
                        out.close();
                    }

                    if (card != null && card.isTwoFacedCard()) {
                        BufferedImage image = ImageIO.read(temporaryFile);
                        if (image.getHeight() == 470) {
                            BufferedImage renderedImage = new BufferedImage(265, 370, BufferedImage.TYPE_INT_RGB);
                            renderedImage.getGraphics();
                            Graphics2D graphics2D = renderedImage.createGraphics();
                            if (card.isTwoFacedCard() && card.isSecondSide()) {
                                graphics2D.drawImage(image, 0, 0, 265, 370, 313, 62, 578, 432, null);
                            } else {
                                graphics2D.drawImage(image, 0, 0, 265, 370, 41, 62, 306, 432, null);
                            }
                            graphics2D.dispose();
                            writeImageToFile(renderedImage, outputFile);
                        } else {
                            outputFile.getParentFile().mkdirs();
                            new TFile(temporaryFile).cp_rp(outputFile);
                        }
                        //temporaryFile.delete();
                    } else {
                        outputFile.getParentFile().mkdirs();
                        new TFile(temporaryFile).cp_rp(outputFile);
                    }
                } else {
                    if (card != null && !useSpecifiedPaths) {
                        logger.warn("Image download for " + card.getName()
                                + (!card.getDownloadName().equals(card.getName()) ? " downloadname: " + card.getDownloadName() : "")
                                + '(' + card.getSet() + ") failed - responseCode: " + responseCode + " url: " + url.toString());
                    }
                    if (logger.isDebugEnabled()) { // Shows the returned html from the request to the web server
                        logger.debug("Returned HTML ERROR:\n" + convertStreamToString(((HttpURLConnection) httpConn).getErrorStream()));
                    }
                }

            } catch (AccessDeniedException e) {
                logger.error("The file " + (outputFile != null ? outputFile.toString() : "to add the image of " + card.getName() + '(' + card.getSet() + ')') + " can't be accessed. Try rebooting your system to remove the file lock.");
            } catch (Exception e) {
                logger.error(e, e);
            } finally {
                if (temporaryFile != null) {
                    temporaryFile.delete();
                }
            }
            synchronized (sync) {
                update(cardIndex + 1, count);
            }
        }

        private void writeImageToFile(BufferedImage image, TFile file) throws IOException {
            Iterator iter = ImageIO.getImageWritersByFormatName("jpg");

            ImageWriter writer = (ImageWriter) iter.next();
            ImageWriteParam iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(0.96f);

            File tempFile = new File(Constants.IO.imageBaseDir + File.separator + image.hashCode() + file.getName());
            FileImageOutputStream output = new FileImageOutputStream(tempFile);
            writer.setOutput(output);
            IIOImage image2 = new IIOImage(image, null, null);
            writer.write(null, image2, iwp);
            writer.dispose();
            output.close();

            new TFile(tempFile).cp_rp(file);
            tempFile.delete();
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
                TFile file = new TFile(CardImageUtils.generateImagePath(cardDownloadData));
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
