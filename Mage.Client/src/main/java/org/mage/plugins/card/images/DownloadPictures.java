package org.mage.plugins.card.images;

import mage.cards.repository.CardInfo;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.sets.ConstructedFormats;
import mage.remote.Connection;
import mage.util.RandomUtil;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileOutputStream;
import net.java.truevfs.access.TVFS;
import net.java.truevfs.kernel.spec.FsSyncException;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.sources.*;
import org.mage.plugins.card.properties.SettingsManager;
import org.mage.plugins.card.utils.CardImageUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class DownloadPictures extends DefaultBoundedRangeModel implements Runnable {

    private static final Logger logger = Logger.getLogger(DownloadPictures.class);

    private final JProgressBar bar;
    private final JOptionPane dlg;
    private boolean cancel;
    private final JButton closeButton;
    private final JButton startDownloadButton;
    private int cardIndex;
    private List<CardDownloadData> cards;
    private List<CardDownloadData> type2cards;
    private final JComboBox jComboBox1;
    private final JLabel jLabel1;
    private static boolean offlineMode = false;
    private JCheckBox checkBox;
    private final Object sync = new Object();

    private static CardImageSource cardImageSource;

    private Proxy p = Proxy.NO_PROXY;

    // private ExecutorService executor = Executors.newFixedThreadPool(10);
    public static void main(String[] args) {
        startDownload(null, null);
    }

    public static void startDownload(JFrame frame, List<CardInfo> allCards) {
        List<CardDownloadData> cards = getNeededCards(allCards);

        /*
         * if (cards == null || cards.isEmpty()) {
         * JOptionPane.showMessageDialog(null,
         * "All card pictures have been downloaded."); return; }
         */
        DownloadPictures download = new DownloadPictures(cards);
        JDialog dlg = download.getDlg(frame);
        dlg.setVisible(true);
        dlg.dispose();
        download.cancel = true;
    }

    public JDialog getDlg(JFrame frame) {
        String title = "Downloading";

        final JDialog dialog = this.dlg.createDialog(frame, title);
        closeButton.addActionListener(e -> dialog.setVisible(false));
        return dialog;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public DownloadPictures(List<CardDownloadData> cards) {
        this.cards = cards;

        bar = new JProgressBar(this);

        JPanel p0 = new JPanel();
        p0.setLayout(new BoxLayout(p0, BoxLayout.Y_AXIS));

        p0.add(Box.createVerticalStrut(5));
        jLabel1 = new JLabel();
        jLabel1.setText("Please select server:");

        jLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);

        p0.add(jLabel1);
        p0.add(Box.createVerticalStrut(5));
        ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(new String[]{
            "magiccards.info",
            "wizards.com",
            "mythicspoiler.com",
            "tokens.mtg.onl", //"mtgimage.com (HQ)",
            "mtg.onl",
            "alternative.mtg.onl",
            "GrabBag",
            "magidex.com"
        //"mtgathering.ru HQ",
        //"mtgathering.ru MQ",
        //"mtgathering.ru LQ",
        });
        jComboBox1 = new JComboBox();

        cardImageSource = MagicCardsImageSource.getInstance();

        jComboBox1.setModel(jComboBox1Model);
        jComboBox1.setAlignmentX(Component.LEFT_ALIGNMENT);
        jComboBox1.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            switch (cb.getSelectedIndex()) {
                case 0:
                    cardImageSource = MagicCardsImageSource.getInstance();
                    break;
                case 1:
                    cardImageSource = WizardCardsImageSource.getInstance();
                    break;
                case 2:
                    cardImageSource = MythicspoilerComSource.getInstance();
                    break;
                case 3:
                    cardImageSource = TokensMtgImageSource.getInstance();
                    break;
                case 4:
                    cardImageSource = MtgOnlTokensImageSource.getInstance();
                    break;
                case 5:
                    cardImageSource = AltMtgOnlTokensImageSource.getInstance();
                    break;
                case 6:
                    cardImageSource = GrabbagImageSource.getInstance();
                    break;
                case 7:
                    cardImageSource = MagidexImageSource.getInstance();
                    break;
            }
            updateCardsToDownload();
        });
        p0.add(jComboBox1);
        p0.add(Box.createVerticalStrut(5));

        // Start
        startDownloadButton = new JButton("Start download");
        startDownloadButton.addActionListener(e -> {
            new Thread(DownloadPictures.this).start();
            startDownloadButton.setEnabled(false);
            checkBox.setEnabled(false);
        });
        p0.add(Box.createVerticalStrut(5));

        // Progress
        p0.add(bar);
        bar.setStringPainted(true);
        int count = cards.size();
        float mb = (count * cardImageSource.getAverageSize()) / 1024;
        bar.setString(String.format(cardIndex == cards.size() ? "%d of %d cards finished! Please close!"
                : "%d of %d cards finished! Please wait! [%.1f Mb]", 0, cards.size(), mb));
        Dimension d = bar.getPreferredSize();
        d.width = 300;
        bar.setPreferredSize(d);

        p0.add(Box.createVerticalStrut(5));
        checkBox = new JCheckBox("Download images for Standard (Type2) only");
        p0.add(checkBox);
        p0.add(Box.createVerticalStrut(5));

        checkBox.addActionListener(e -> updateCardsToDownload());

        // JOptionPane
        Object[] options = {startDownloadButton, closeButton = new JButton("Cancel")};
        dlg = new JOptionPane(p0, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[1]);
    }

    public static boolean checkForMissingCardImages(List<CardInfo> allCards) {
        AtomicBoolean missedCardTFiles = new AtomicBoolean();
        allCards.parallelStream().forEach(card -> {
            if (!missedCardTFiles.get()) {
                if (!card.getCardNumber().isEmpty() && !"0".equals(card.getCardNumber()) && !card.getSetCode().isEmpty()) {
                    CardDownloadData url = new CardDownloadData(card.getName(), card.getSetCode(), card.getCardNumber(), card.usesVariousArt(),
                            0, "", "", false, card.isDoubleFaced(), card.isNightCard());
                    TFile file = new TFile(CardImageUtils.generateImagePath(url));
                    if (!file.exists()) {
                        missedCardTFiles.set(true);
                    }
                }
            }
        });
        return missedCardTFiles.get();
    }

    private void updateCardsToDownload() {
        List<CardDownloadData> cardsToDownload = cards;
        if (type2cardsOnly()) {
            selectType2andTokenCardsIfNotYetDone();
            cardsToDownload = type2cards;
        }
        updateProgressText(cardsToDownload.size());
    }

    private boolean type2cardsOnly() {
        return checkBox.isSelected();
    }

    private void selectType2andTokenCardsIfNotYetDone() {
        if (type2cards == null) {
            type2cards = new ArrayList<>();
            for (CardDownloadData data : cards) {
                if (data.isType2() || data.isToken()) {
                    type2cards.add(data);
                }
            }
        }
    }

    private void updateProgressText(int cardCount) {
        float mb = (cardCount * cardImageSource.getAverageSize()) / 1024;
        bar.setString(String.format(cardIndex == cardCount ? "%d of %d cards finished! Please close!"
                : "%d of %d cards finished! Please wait! [%.1f Mb]", 0, cardCount, mb));
    }

    private static String createDownloadName(CardInfo card) {
        String className = card.getClassName();
        return className.substring(className.lastIndexOf('.') + 1);
    }

    private static List<CardDownloadData> getNeededCards(List<CardInfo> allCards) {

        List<CardDownloadData> cardsToDownload = Collections.synchronizedList(new ArrayList<>());

        /**
         * read all card names and urls
         */
        List<CardDownloadData> allCardsUrls = Collections.synchronizedList(new ArrayList<>());
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

        int numberCardImages = allCards.size();
        int numberWithoutTokens = 0;
        try {
            offlineMode = true;
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
                        url = new CardDownloadData(card.getFlipCardName(), card.getSetCode(), card.getCardNumber(), card.usesVariousArt(), 0, "", "", false, card.isDoubleFaced(), card.isNightCard());
                        url.setFlipCard(true);
                        url.setFlippedSide(true);
                        url.setType2(isType2);
                        allCardsUrls.add(url);
                    }
                } else if (card.getCardNumber().isEmpty() || "0".equals(card.getCardNumber())) {
                    System.err.println("There was a critical error!");
                    logger.error("Card has no collector ID and won't be sent to client: " + card);
                } else if (card.getSetCode().isEmpty()) {
                    System.err.println("There was a critical error!");
                    logger.error("Card has no set name and won't be sent to client:" + card);
                }
            });
            numberWithoutTokens = allCards.size();
            allCardsUrls.addAll(getTokenCardUrls());
        } catch (Exception e) {
            logger.error(e);
        }

        int numberAllTokenImages = allCardsUrls.size() - numberWithoutTokens;

        /**
         * check to see which cards we already have
         */
        allCardsUrls.parallelStream().forEach(card -> {
            TFile file = new TFile(CardImageUtils.generateImagePath(card));
            if (!file.exists()) {
                logger.debug("Missing: " + file.getAbsolutePath());
                cardsToDownload.add(card);
            }
        });

        int tokenImages = 0;
        for (CardDownloadData card : cardsToDownload) {
            logger.debug((card.isToken() ? "Token" : "Card") + " image to download: " + card.getName() + " (" + card.getSet() + ')');
            if (card.isToken()) {
                tokenImages++;
            }
        }
        logger.info("Check download images (total card images: " + numberCardImages + ", total token images: " + numberAllTokenImages + ')');
        logger.info("   => Missing card images: " + (cardsToDownload.size() - tokenImages));
        logger.info("   => Missing token images: " + tokenImages);
        return new ArrayList<>(cardsToDownload);
    }

    private static ArrayList<CardDownloadData> getTokenCardUrls() throws RuntimeException {
        ArrayList<CardDownloadData> list = new ArrayList<>();
        InputStream in = DownloadPictures.class.getClassLoader().getResourceAsStream("card-pictures-tok.txt");

        if (in == null) {
            logger.error("resources input stream is null");
            return list;
        }


        try(InputStreamReader input = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(input)) {
            String line;

            line = reader.readLine();
            while (line != null) {
                line = line.trim();
                if (line.startsWith("|")) { // new format
                    String[] params = line.split("\\|", -1);
                    if (params.length >= 5) {
                        int type = 0;
                        String fileName = "";
                        if (params[4] != null && !params[4].isEmpty()) {
                            type = Integer.parseInt(params[4].trim());
                        }
                        if (params.length > 5 && params[5] != null && !params[5].isEmpty()) {
                            fileName = params[5].trim();
                        }

                        if (params[1].toLowerCase().equals("generate") && params[2].startsWith("TOK:")) {
                            String set = params[2].substring(4);
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type, "", "", true);
                            list.add(card);
                        } else if (params[1].toLowerCase().equals("generate") && params[2].startsWith("EMBLEM:")) {
                            String set = params[2].substring(7);
                            CardDownloadData card = new CardDownloadData("Emblem " + params[3], set, "0", false, type, "", "", true, fileName);
                            list.add(card);
                        } else if (params[1].toLowerCase().equals("generate") && params[2].startsWith("EMBLEM-:")) {
                            String set = params[2].substring(8);
                            CardDownloadData card = new CardDownloadData(params[3] + " Emblem", set, "0", false, type, "", "", true, fileName);
                            list.add(card);
                        } else if (params[1].toLowerCase().equals("generate") && params[2].startsWith("EMBLEM!:")) {
                            String set = params[2].substring(8);
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type, "", "", true, fileName);
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

            List<CardDownloadData> cardsToDownload = this.checkBox.isSelected() ? type2cards : cards;

            update(0, cardsToDownload.size());

            int numberOfThreads = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_THREADS, "10"));
            ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
            for (int i = 0; i < cardsToDownload.size() && !cancel; i++) {
                try {

                    CardDownloadData card = cardsToDownload.get(i);

                    logger.debug("Downloading card: " + card.getName() + " (" + card.getSet() + ')');

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
                            logger.info("Card not available on " + cardImageSource.getSourceName() + ": " + card.getName() + " (" + card.getSet() + ')');
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
                    Thread.sleep(1000);
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

        public DownloadTask(CardDownloadData card, URL url, int count) {
            this.card = card;
            this.url = url;
            this.count = count;
            this.actualFilename = "";
            useSpecifiedPaths = false;
        }

        public DownloadTask(CardDownloadData card, URL url, String actualFilename, int count) {
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
                BufferedOutputStream out;

                // Logger.getLogger(this.getClass()).info(url.toString());
                boolean useTempFile = false;
                int responseCode = 0;
                URLConnection httpConn = null;

                if (temporaryFile != null && temporaryFile.length() > 100) {
                    useTempFile = true;
                } else {
                    cardImageSource.doPause(url.getPath());
                    httpConn = url.openConnection(p);
                    setUpConnection(httpConn);
                    httpConn.connect();
                    responseCode = ((HttpURLConnection) httpConn).getResponseCode();
                }

                if (responseCode == 200 || useTempFile) {
                    if (!useTempFile) {
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

        private void setUpConnection(URLConnection httpConn) {
            // images download from magiccards.info may not work with default 'User-Agent: Java/1.x.x' request header
            switch (RandomUtil.nextInt(3)) {
                // chrome
                case 0:
                    httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                    httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
                    httpConn.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
                    httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                    break;
                // ff
                case 1:
                    httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                    httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
                    httpConn.setRequestProperty("Accept-Language", "en-US;q=0.5,en;q=0.3");
                    httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
                    break;
                // ie
                case 2:
                    httpConn.setRequestProperty("Accept", "text/html, application/xhtml+xml, */*");
                    httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
                    httpConn.setRequestProperty("Accept-Language", "en-US");
                    httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
                    break;
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
            bar.setString(String.format("%d of %d cards finished! Please wait! [%.1f Mb]",
                    card, count, mb));
        } else {
            List<CardDownloadData> remainingCards = Collections.synchronizedList(new ArrayList<>());
            DownloadPictures.this.cards.parallelStream().forEach(cardDownloadData -> {
                TFile file = new TFile(CardImageUtils.generateImagePath(cardDownloadData));
                if (!file.exists()) {
                    remainingCards.add(cardDownloadData);
                }
            });

            DownloadPictures.this.cards = new ArrayList<>(remainingCards);

            count = DownloadPictures.this.cards.size();

            if (count == 0) {
                bar.setString("0 cards remaining! Please close!");
            } else {
                bar.setString(String.format("%d cards remaining! Please choose another source!", count));
                startDownloadButton.setEnabled(true);
            }
        }
    }

    private static final long serialVersionUID = 1L;
}
