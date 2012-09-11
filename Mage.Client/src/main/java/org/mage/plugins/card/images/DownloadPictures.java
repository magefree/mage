package org.mage.plugins.card.images;

import de.schlichtherle.truezip.file.TArchiveDetector;
import de.schlichtherle.truezip.file.TConfig;
import de.schlichtherle.truezip.file.TFile;
import de.schlichtherle.truezip.file.TFileOutputStream;
import de.schlichtherle.truezip.fs.FsOutputOption;
import mage.cards.Card;
import mage.client.dialog.PreferencesDialog;
import mage.remote.Connection;
import org.apache.log4j.Logger;
import org.mage.plugins.card.constants.Constants;
import org.mage.plugins.card.dl.sources.CardImageSource;
import org.mage.plugins.card.dl.sources.MagicCardsImageSource;
import org.mage.plugins.card.dl.sources.WizardCardsImageSource;
import org.mage.plugins.card.properties.SettingsManager;
import org.mage.plugins.card.utils.CardImageUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class DownloadPictures extends DefaultBoundedRangeModel implements Runnable {

    private static final Pattern basicLandPattern = Pattern.compile("^(Forest|Mountain|Swamp|Island|Plains)$");

    private JProgressBar bar;
    private JOptionPane dlg;
    private boolean cancel;
    private JButton closeButton;
    private JButton startDownloadButton;
    private int cardIndex;
    private ArrayList<CardInfo> cards;
    private JComboBox jComboBox1;
    private JLabel jLabel1;
    private static boolean offlineMode = false;
    private JCheckBox checkBox;
    private final Object sync = new Object();
    private String imagesPath;

    private static CardImageSource cardImageSource;

    private Proxy p = Proxy.NO_PROXY;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public static final Proxy.Type[] types = Proxy.Type.values();

    public static void main(String[] args) {
        startDownload(null, null, null);
    }

    public static void startDownload(JFrame frame, Set<Card> allCards, String imagesPath) {
        ArrayList<CardInfo> cards = getNeededCards(allCards, imagesPath);

        /*
         * if (cards == null || cards.size() == 0) {
         * JOptionPane.showMessageDialog(null,
         * "All card pictures have been downloaded."); return; }
         */

        DownloadPictures download = new DownloadPictures(cards, imagesPath);
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

    public DownloadPictures(ArrayList<CardInfo> cards, String imagesPath) {
        this.cards = cards;
        this.imagesPath = imagesPath;

        //addr = new JTextField("Proxy Address");
        //port = new JTextField("Proxy Port");
        bar = new JProgressBar(this);

        JPanel p0 = new JPanel();
        p0.setLayout(new BoxLayout(p0, BoxLayout.Y_AXIS));

        // Proxy Choice
        /*ButtonGroup bg = new ButtonGroup();
        String[] labels = { "No Proxy", "HTTP Proxy", "SOCKS Proxy" };
        for (int i = 0; i < types.length; i++) {
            JRadioButton rb = new JRadioButton(labels[i]);
            rb.addChangeListener(new ProxyHandler(i));
            bg.add(rb);
            p0.add(rb);
            if (i == 0)
                rb.setSelected(true);
        }*/

        // Proxy config
        //p0.add(addr);
        //p0.add(port);

        p0.add(Box.createVerticalStrut(5));
        jLabel1 = new JLabel();
        jLabel1.setText("Please select server:");

        jLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);

        p0.add(jLabel1);
        p0.add(Box.createVerticalStrut(5));
        ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(new String[] { "magiccards.info", "wizards.com"/*, "mtgathering.ru HQ", "mtgathering.ru MQ", "mtgathering.ru LQ"*/});
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
        checkBox = new JCheckBox("Download for current game only.");
        p0.add(checkBox);
        p0.add(Box.createVerticalStrut(5));
        checkBox.setEnabled(!offlineMode);

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = DownloadPictures.this.cards.size();
                float mb = (count * cardImageSource.getAverageSize()) / 1024;
                bar.setString(String.format(cardIndex == count ? "%d of %d cards finished! Please close!"
                        : "%d of %d cards finished! Please wait! [%.1f Mb]", 0, count, mb));
            }
        });

        // JOptionPane
        Object[] options = { startDownloadButton, closeButton = new JButton("Cancel") };
        dlg = new JOptionPane(p0, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
    }

    public static boolean checkForNewCards(Set<Card> allCards, String imagesPath) {
        TFile file;
        for (Card card : allCards) {
            if (card.getCardNumber() > 0 && !card.getExpansionSetCode().isEmpty()) {
                CardInfo url = new CardInfo(card.getName(), card.getExpansionSetCode(), card.getCardNumber(), 0, false, card.canTransform(), card.isNightCard());
                file = new TFile(CardImageUtils.getImagePath(url, imagesPath));
                if (!file.exists()) {
                    return true;
                }
            }
        }        
        return false;
    }

    private static ArrayList<CardInfo> getNeededCards(Set<Card> allCards, String imagesPath) {

        ArrayList<CardInfo> cardsToDownload = new ArrayList<CardInfo>();

        /**
         * read all card names and urls
         */
        ArrayList<CardInfo> allCardsUrls = new ArrayList<CardInfo>();

        try {
            offlineMode = true;

            for (Card card : allCards) {
                if (card.getCardNumber() > 0 && !card.getExpansionSetCode().isEmpty()) {
                    String cardName = card.getName();
                    CardInfo url = new CardInfo(cardName, card.getExpansionSetCode(), card.getCardNumber(), 0, false, card.canTransform(), card.isNightCard());
                    if (basicLandPattern.matcher(cardName).matches()) {
                        url.setDownloadName(card.getClass().getName().replace(card.getClass().getPackage().getName() + ".", ""));
                    }
                    if (card.isFlipCard()) {
                        url.setFlipCard(true);
                    }
                    allCardsUrls.add(url);
                    if (card.canTransform()) {
                        // add second side for downloading
                        // it has the same expansion set code and card number as original one
                        // second side = true;
                        Card secondSide = card.getSecondCardFace();
                        url = new CardInfo(secondSide.getName(), card.getExpansionSetCode(), card.getCardNumber(), 0, false, card.canTransform(), true);
                        allCardsUrls.add(url);
                    }
                } else {
                    if (card.getCardNumber() < 1) {
                        System.err.println("There was a critical error!");
                        log.error("Card has no collector ID and won't be sent to client: " + card);
                    } else if (card.getExpansionSetCode().isEmpty()) {
                        System.err.println("There was a critical error!");
                        log.error("Card has no set name and won't be sent to client:" + card);
                    }
                }
            }

            allCardsUrls.addAll(getTokenCardUrls());
        } catch (Exception e) {
            log.error(e);
        }

        TFile file;

        /**
         * check to see which cards we already have
         */
        for (CardInfo card : allCardsUrls) {
            file = new TFile(CardImageUtils.getImagePath(card, imagesPath));
            if (!file.exists()) {
                cardsToDownload.add(card);
            }
        }

        for (CardInfo card : cardsToDownload) {
            if (card.isToken()) {
                log.info("Card to download: " + card.getName() + " (Token) ");
            } else {
                try {
                    log.info("Card to download: " + card.getName() + " (" + card.getSet() + ")");
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }

        return cardsToDownload;
    }

    private static ArrayList<CardInfo> getTokenCardUrls() throws RuntimeException {
        ArrayList<CardInfo> list = new ArrayList<CardInfo>();
        InputStream in = DownloadPictures.class.getClassLoader().getResourceAsStream("card-pictures-tok.txt");

        if (in == null) {
            log.error("resources input stream is null");
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
                    String[] params = line.split("\\|");
                    if (params.length >= 4) {
                        if (params[1].toLowerCase().equals("generate") && params[2].startsWith("TOK:")) {
                            String set = params[2].substring(4);
                            CardInfo card = new CardInfo(params[3], set, 0, 0, true);
                            list.add(card);
                        } else if (params[1].toLowerCase().equals("generate") && params[2].startsWith("EMBLEM:")) {
                            String set = params[2].substring(7);
                            CardInfo card = new CardInfo("Emblem " + params[3], set, 0, 0, true);
                            list.add(card);
                        }
                    } else {
                        log.error("wrong format for image urls: " + line);
                    }
                }
                line = reader.readLine();
            }

        } catch (Exception ex) {
            log.error(ex);
            throw new RuntimeException("DownloadPictures : readFile() error");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return list;
    }

    @Override
    public void run() {

        File base = new File(this.imagesPath != null ? imagesPath : Constants.IO.imageBaseDir);
        if (!base.exists()) {
            base.mkdir();
        }

        Connection.ProxyType configProxyType = Connection.ProxyType.valueByText(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_TYPE, "None"));

        Proxy.Type type = Proxy.Type.DIRECT;
        switch (configProxyType) {
            case HTTP: type = Proxy.Type.HTTP; break;
            case SOCKS: type = Proxy.Type.SOCKS; break;
            case NONE:
            default: p = Proxy.NO_PROXY; break;
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

            update(0);
            for (int i = 0; i < cards.size() && !cancel; i++) {
                try {

                    CardInfo card = cards.get(i);

                    log.info("Downloading card: " + card.getName() + " (" + card.getSet() + ")");

                    String url;
                    if (ignoreUrls.contains(card.getSet()) || card.isToken()) {
                        if (card.getCollectorId() != 0) {
                            continue;
                        }
                        url = cardImageSource.generateTokenUrl(card.getName(), card.getSet());
                    } else {
                        url = cardImageSource.generateURL(card.getCollectorId(), card.getDownloadName(), card.getSet(),
                                card.isTwoFacedCard(), card.isSecondSide(), card.isFlipCard());
                    }

                    if (url != null) {
                        Runnable task = new DownloadTask(card, new URL(url));
                        executor.execute(task);
                    } else {
                        synchronized (sync) {
                            update(cardIndex + 1);
                        }
                    }
                } catch (Exception ex) {
                    log.error(ex, ex);
                }
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {}
            }
        }
        closeButton.setText("Close");
    }

    private final class DownloadTask implements Runnable {

        private CardInfo card;
        private URL url;

        public DownloadTask(CardInfo card, URL url) {
            this.card = card;
            this.url = url;
        }

        @Override
        public void run() {
            try {
                File temporaryFile = new File(Constants.IO.imageBaseDir + File.separator + card.hashCode() + "." + card.getName() + ".jpg");

                BufferedInputStream in = new BufferedInputStream(url.openConnection(p).getInputStream());
                BufferedOutputStream out = new BufferedOutputStream(new TFileOutputStream(temporaryFile));

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

                in.close();
                out.flush();
                out.close();

                TFile outputFile = new TFile(CardImageUtils.getImagePath(card, imagesPath));
                if (card.isTwoFacedCard()) {
                    BufferedImage image = ImageIO.read(temporaryFile);
                    temporaryFile.delete();
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
                    }
                } else {
                    new TFile(temporaryFile).cp_rp(outputFile);
                    temporaryFile.delete();
                }

            } catch (Exception e) {
                log.error(e, e);
            }

            synchronized (sync) {
                update(cardIndex + 1);
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

    private void update(int card) {
        this.cardIndex = card;
        int count = DownloadPictures.this.cards.size();

        if (cardIndex < count) {
        float mb = ((count - card) * cardImageSource.getAverageSize()) / 1024;
            bar.setString(String.format("%d of %d cards finished! Please wait! [%.1f Mb]",
                    card, count, mb));
        } else {
            Iterator<CardInfo> cardsIterator = DownloadPictures.this.cards.iterator();
            while (cardsIterator.hasNext()) {
                CardInfo cardInfo = cardsIterator.next();
                TFile file = new TFile(CardImageUtils.getImagePath(cardInfo, imagesPath));
                if (file.exists()) {
                    cardsIterator.remove();
                }
            }
            count = DownloadPictures.this.cards.size();

            if (count == 0) {
                bar.setString(String.format("0 cards remaining! Please close!", count));
            } else {
                bar.setString(String.format("%d cards remaining! Please choose another source!", count));
                executor = Executors.newFixedThreadPool(10);
                startDownloadButton.setEnabled(true);
            }
        }
    }

    private static final Logger log = Logger.getLogger(DownloadPictures.class);

    private static final long serialVersionUID = 1L;
}
