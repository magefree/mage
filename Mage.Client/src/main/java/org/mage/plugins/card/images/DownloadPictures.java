package org.mage.plugins.card.images;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import mage.cards.repository.CardInfo;
import mage.client.constants.Constants;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.sets.ConstructedFormats;
import mage.remote.Connection;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileOutputStream;
import net.java.truevfs.access.TVFS;
import net.java.truevfs.kernel.spec.FsSyncException;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.sources.CardImageSource;
import org.mage.plugins.card.dl.sources.MagicCardsImageSource;
import org.mage.plugins.card.dl.sources.MtgOnlTokensImageSource;
import org.mage.plugins.card.dl.sources.MythicspoilerComSource;
import org.mage.plugins.card.dl.sources.TokensMtgImageSource;
import org.mage.plugins.card.dl.sources.WizardCardsImageSource;
import org.mage.plugins.card.properties.SettingsManager;
import org.mage.plugins.card.utils.CardImageUtils;

public class DownloadPictures extends DefaultBoundedRangeModel implements Runnable {

    private static final Logger logger = Logger.getLogger(DownloadPictures.class);
    private static final Random rnd = new Random();

    private JProgressBar bar;
    private final JOptionPane dlg;
    private boolean cancel;
    private final JButton closeButton;
    private JButton startDownloadButton;
    private int cardIndex;
    private ArrayList<CardDownloadData> cards;
    private ArrayList<CardDownloadData> type2cards;
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
        ArrayList<CardDownloadData> cards = getNeededCards(allCards);

        /*
         * if (cards == null || cards.size() == 0) {
         * JOptionPane.showMessageDialog(null,
         * "All card pictures have been downloaded."); return; }
         */
        DownloadPictures download = new DownloadPictures(cards);
        JDialog dlg = download.getDlg(frame);
        dlg.setVisible(true);
        dlg.dispose();
        download.setCancel(true);
    }

    public JDialog getDlg(JFrame frame) {
        String title = "Downloading";

        final JDialog dialog = this.dlg.createDialog(frame, title);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        return dialog;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public DownloadPictures(ArrayList<CardDownloadData> cards) {
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
            "mtg.onl"
        //"mtgathering.ru HQ",
        //"mtgathering.ru MQ",
        //"mtgathering.ru LQ",
        });
        jComboBox1 = new JComboBox();

        cardImageSource = MagicCardsImageSource.getInstance();

        jComboBox1.setModel(jComboBox1Model);
        jComboBox1.setAlignmentX(Component.LEFT_ALIGNMENT);
        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                }
                int count = DownloadPictures.this.cards.size();
                float mb = (count * cardImageSource.getAverageSize()) / 1024;
                bar.setString(String.format(cardIndex == count ? "%d of %d cards finished! Please close!"
                        : "%d of %d cards finished! Please wait! [%.1f Mb]", 0, count, mb));
            }
        });
        p0.add(jComboBox1);
        p0.add(Box.createVerticalStrut(5));

        // Start
        startDownloadButton = new JButton("Start download");
        startDownloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(DownloadPictures.this).start();
                startDownloadButton.setEnabled(false);
                checkBox.setEnabled(false);
            }
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

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<CardDownloadData> cardsToDownload = DownloadPictures.this.cards;
                if (checkBox.isSelected()) {
                    DownloadPictures.this.type2cards = new ArrayList<>();
                    for (CardDownloadData data : DownloadPictures.this.cards) {
                        if (data.isType2() || data.isToken()) {
                            DownloadPictures.this.type2cards.add(data);
                        }
                    }
                    cardsToDownload = DownloadPictures.this.type2cards;
                }
                int count = cardsToDownload.size();
                float mb = (count * cardImageSource.getAverageSize()) / 1024;
                bar.setString(String.format(cardIndex == count ? "%d of %d cards finished! Please close!"
                        : "%d of %d cards finished! Please wait! [%.1f Mb]", 0, count, mb));
            }
        });

        // JOptionPane
        Object[] options = {startDownloadButton, closeButton = new JButton("Cancel")};
        dlg = new JOptionPane(p0, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[1]);
    }

    public static boolean checkForNewCards(List<CardInfo> allCards) {
        TFile file;
        for (CardInfo card : allCards) {
            if (!card.getCardNumber().isEmpty() && !"0".equals(card.getCardNumber()) && !card.getSetCode().isEmpty()) {
                CardDownloadData url = new CardDownloadData(card.getName(), card.getSetCode(), card.getCardNumber(), card.usesVariousArt(),
                        0, "", "", false, card.isDoubleFaced(), card.isNightCard());
                file = new TFile(CardImageUtils.generateImagePath(url));
                if (!file.exists()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String createDownloadName(CardInfo card) {
        String className = card.getClassName();
        return className.substring(className.lastIndexOf('.') + 1);
    }

    private static ArrayList<CardDownloadData> getNeededCards(List<CardInfo> allCards) {

        ArrayList<CardDownloadData> cardsToDownload = new ArrayList<>();

        /**
         * read all card names and urls
         */
        ArrayList<CardDownloadData> allCardsUrls = new ArrayList<>();
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

        try {
            offlineMode = true;

            for (CardInfo card : allCards) {
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
            }
            allCardsUrls.addAll(getTokenCardUrls());

        } catch (Exception e) {
            logger.error(e);
        }
        int numberTokenImages = allCardsUrls.size() - numberCardImages;
        TFile file;

        /**
         * check to see which cards we already have
         */
        for (CardDownloadData card : allCardsUrls) {
            file = new TFile(CardImageUtils.generateImagePath(card));
            if (!file.exists()) {
                cardsToDownload.add(card);
            }
        }

        logger.info("Check download images (total cards: " + numberCardImages + ", total tokens: " + numberTokenImages + ") => Missing images: " + cardsToDownload.size());
        if (logger.isDebugEnabled()) {
            for (CardDownloadData card : cardsToDownload) {
                if (card.isToken()) {
                    logger.debug("Card to download: " + card.getName() + " (Token) ");
                } else {
                    try {
                        logger.debug("Card to download: " + card.getName() + " (" + card.getSet() + ")");
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            }
        }

        return cardsToDownload;
    }

    private static ArrayList<CardDownloadData> getTokenCardUrls() throws RuntimeException {
        ArrayList<CardDownloadData> list = new ArrayList<>();
        InputStream in = DownloadPictures.class.getClassLoader().getResourceAsStream("card-pictures-tok.txt");

        if (in == null) {
            logger.error("resources input stream is null");
            return list;
        }

        BufferedReader reader = null;
        InputStreamReader input = null;
        try {
            input = new InputStreamReader(in);
            reader = new BufferedReader(input);
            String line;

            line = reader.readLine();
            while (line != null) {
                line = line.trim();
                if (line.startsWith("|")) { // new format
                    String[] params = line.split("\\|", -1);
                    if (params.length >= 5) {
                        int type = 0;
                        if (params[4] != null && !params[4].isEmpty()) {
                            type = Integer.parseInt(params[4].trim());
                        }
                        if (params[1].toLowerCase().equals("generate") && params[2].startsWith("TOK:")) {
                            String set = params[2].substring(4);
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type, "", "", true);
                            list.add(card);
                        } else if (params[1].toLowerCase().equals("generate") && params[2].startsWith("EMBLEM:")) {
                            String set = params[2].substring(7);
                            CardDownloadData card = new CardDownloadData("Emblem " + params[3], set, "0", false, type, "", "", true);
                            list.add(card);
                        } else if (params[1].toLowerCase().equals("generate") && params[2].startsWith("EMBLEM-:")) {
                            String set = params[2].substring(8);
                            CardDownloadData card = new CardDownloadData(params[3] + " Emblem", set, "0", false, type, "", "", true);
                            list.add(card);
                        } else if (params[1].toLowerCase().equals("generate") && params[2].startsWith("EMBLEM!:")) {
                            String set = params[2].substring(8);
                            CardDownloadData card = new CardDownloadData(params[3], set, "0", false, type, "", "", true);
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
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    logger.error("Input close failed:", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    logger.error("Reader close failed:", e);
                }
            }

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

            ArrayList<CardDownloadData> cardsToDownload = this.checkBox.isSelected() ? type2cards : cards;

            update(0, cardsToDownload.size());

            int numberOfThreads = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_IMAGES_THREADS, "10"));
            ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
            for (int i = 0; i < cardsToDownload.size() && !cancel; i++) {
                try {

                    CardDownloadData card = cardsToDownload.get(i);

                    logger.debug("Downloading card: " + card.getName() + " (" + card.getSet() + ")");

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
                                
                                Runnable task = new DownloadTask(imageUrl, fileName, 1);
                                executor.execute(task);
                            } catch (Exception ex) {
                            }
                        } else {
                            if (card != null) {
                                logger.info("Card not available on " + cardImageSource.getSourceName() + ": " + card.getName() + " (" + card.getSet() + ")");
                                synchronized (sync) {
                                    update(cardIndex + 1, cardsToDownload.size());
                                }
                            }
                        }
                    } else if (url != null) {
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
        private boolean useSpecifiedPaths;

        public DownloadTask(CardDownloadData card, URL url, int count) {
            this.card = card;
            this.url = url;
            this.count = count;
            this.actualFilename = "";
            useSpecifiedPaths = false;
        }

        public DownloadTask(URL url, String actualFilename, int count) {
            this.card = null;
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
                    filePath.append(card.hashCode()).append(".").append(card.getName().replace(":", "").replace("//", "-")).append(".jpg");
                    temporaryFile = new File(filePath.toString());
                }
                String imagePath;
                if (useSpecifiedPaths) {
                    imagePath = CardImageUtils.getTokenBasePath();    // temporaryFile = plugins/images\NUM.jace, telepath unbound.jpg
                    imagePath += actualFilename;                      // imagePath = d:\xmage_images\ORI.zip\ORI\Jace,telepathunbound.jpg
                    String tmpFile = filePath + actualFilename + ".2";
                    temporaryFile = new File(tmpFile.toString());
                } else {
                    imagePath = CardImageUtils.generateImagePath(card);
                }

                outputFile = new TFile(imagePath);
                if (!outputFile.exists()) {
                    outputFile.getParentFile().mkdirs();
                }
                File existingFile = new File(imagePath.replaceFirst("\\w{3}.zip", ""));
                if (existingFile.exists()) {
                    new TFile(existingFile).cp_rp(outputFile);
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
                URLConnection httpConn = url.openConnection(p);
                setUpConnection(httpConn);

                httpConn.connect();
                int responseCode = ((HttpURLConnection) httpConn).getResponseCode();
                if (responseCode == 200) {
                    try (BufferedInputStream in = new BufferedInputStream(((HttpURLConnection) httpConn).getInputStream())) {
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
                            new TFile(temporaryFile).cp_rp(outputFile);
                        }
                        temporaryFile.delete();
                    } else {
                        new TFile(temporaryFile).cp_rp(outputFile);
                    }
                } else {
                    if (card != null) {
                        logger.warn("Image download for " + card.getName()
                                + (!card.getDownloadName().equals(card.getName()) ? " downloadname: " + card.getDownloadName() : "")
                                + "(" + card.getSet() + ") failed - responseCode: " + responseCode + " url: " + url.toString());
                    }
                    if (logger.isDebugEnabled()) { // Shows the returned html from the request to the web server
                        logger.debug("Returned HTML ERROR:\n" + convertStreamToString(((HttpURLConnection) httpConn).getErrorStream()));
                    }
                }

            } catch (AccessDeniedException e) {
                logger.error("The file " + (outputFile != null ? outputFile.toString() : "to add the image of " + card.getName() + "(" + card.getSet() + ")") + " can't be accessed. Try rebooting your system to remove the file lock.");
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
            switch (rnd.nextInt(3)) {
                // chrome
                case 0:
                    httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                    httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
                    httpConn.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
                    httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
                    break;
                // ff
                case 1:
                    httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                    httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
                    httpConn.setRequestProperty("Accept-Language", "en-US;q=0.5,en;q=0.3");
                    httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
                    break;
                // ie
                case 2:
                    httpConn.setRequestProperty("Accept", "text/html, application/xhtml+xml, */*");
                    httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
                    httpConn.setRequestProperty("Accept-Language", "en-US");
                    httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
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
            Iterator<CardDownloadData> cardsIterator = DownloadPictures.this.cards.iterator();
            while (cardsIterator.hasNext()) {
                CardDownloadData cardDownloadData = cardsIterator.next();
                TFile file = new TFile(CardImageUtils.generateImagePath(cardDownloadData));
                if (file.exists()) {
                    cardsIterator.remove();
                }
            }
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
