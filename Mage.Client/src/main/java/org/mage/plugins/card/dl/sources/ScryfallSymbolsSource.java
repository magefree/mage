package org.mage.plugins.card.dl.sources;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mage.plugins.card.dl.DownloadJob;
import org.mage.plugins.card.utils.CardImageUtils;


import static org.mage.card.arcane.ManaSymbols.getSymbolFileNameAsSVG;
import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

// TODO: add force to download symbols (rewrite exist files)

/**
 * @author JayDi85
 */
public class ScryfallSymbolsSource implements Iterable<DownloadJob> {


    //static final String SOURCE_URL = "https://assets.scryfall.com/assets/scryfall.css"; // old version with direct css-file on https://scryfall.com/docs/api/colors
    static final String CSS_SOURCE_URL = "https://scryfall.com/docs/api/colors";
    static final String CSS_SOURCE_SELECTOR = "link[rel=stylesheet]"; // <link rel="stylesheet" media="all" href="https://assets.scryfall.com/assets/scryfall-8cdaa786c4c86d5c49317e3e0fed72a0e409666753d3d3e8c906f33a188e19ed.css">
    static final String STATE_PROP_NAME = "state";
    static final String DOWNLOAD_TEMP_FILE = getImagesDir() + File.separator + "temp" + File.separator + "scryfall-symbols-source.txt";

    // card-symbol-(.{1,10}){background-image.+base64,(.+)("\)})
    // see https://regex101.com/
    static final String REGEXP_MANA_PATTERN = "card-symbol-(.{1,10})\\{background-image.+base64,(.+)(\"\\)\\})";


    protected static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ScryfallSymbolsSource.class);

    private static final int SYMBOLS_NUMBER_START = 0;
    private static final int SYMBOLS_NUMBER_END = 20;
    // copy-past symbols list from gatherer download
    private static final String[] SYMBOLS_LIST = {"W", "U", "B", "R", "G",
            "W/U", "U/B", "B/R", "R/G", "G/W", "W/B", "U/R", "B/G", "R/W", "G/U",
            "W/U/P", "U/B/P", "B/R/P", "R/G/P", "G/W/P", "W/B/P", "U/R/P", "B/G/P", "R/W/P", "G/U/P",
            "2/W", "2/U", "2/B", "2/R", "2/G",
            "WP", "UP", "BP", "RP", "GP",
            "X", "S", "T", "Q", "C", "E"};

    @Override
    public Iterator<DownloadJob> iterator() {
        List<DownloadJob> jobs = new ArrayList<>();

        // all symbols on one page
        jobs.add(generateDownloadJob());

        return jobs.iterator();
    }

    private void parseData(String sourcePath) {

        String sourceData = "";
        try {
            sourceData = new String(Files.readAllBytes(Paths.get(sourcePath)));
        } catch (IOException e) {
            LOGGER.error("Can't open file to parse data: " + sourcePath + " , reason: " + e.getMessage());
        }

        // gen symbols list
        List<String> allMageSymbols = new ArrayList<>(Arrays.asList(SYMBOLS_LIST));
        for (Integer i = SYMBOLS_NUMBER_START; i <= SYMBOLS_NUMBER_END; i++) {
            allMageSymbols.add(String.valueOf(SYMBOLS_NUMBER_START + i));
        }

        Map<String, String> foundedData = new HashMap<>();

        // search raw data
        sourceData = sourceData.replaceAll(".card-symbol", "\n.card-symbol"); // css as one line, but need multiline
        Pattern regex = Pattern.compile(REGEXP_MANA_PATTERN);
        Matcher regexMatcher = regex.matcher(sourceData);
        while (regexMatcher.find()) {
            String symbolCode = regexMatcher.group(1).trim();
            String symbolData = regexMatcher.group(2).trim().replace(" ", "").replaceAll("\n", ""); // decoder need only wrapped text as one line

            foundedData.put(symbolCode, symbolData);
        }

        // dirs maker
        File dir = getSymbolFileNameAsSVG("W").getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // decode and save data (only if not exist)
        for (String needCode : allMageSymbols) {

            String searchCode = needCode.replace("/", "");

            if (!foundedData.containsKey(searchCode)) {
                LOGGER.warn("Can't found symbol code from scryfall: " + searchCode);
                continue;
            }

            File destFile = getSymbolFileNameAsSVG(searchCode);
            if (destFile.exists() && (destFile.length() > 0)) {
                continue;
            }
            try(FileOutputStream stream  = new FileOutputStream(destFile)) {
                // base64 transform
                String data64 = foundedData.get(searchCode);
                Base64.Decoder dec = Base64.getDecoder();
                byte[] fileData = dec.decode(data64);

                stream.write(fileData);

                LOGGER.info("New svg symbol downloaded: " + needCode);
            } catch (Exception e) {
                LOGGER.error("Can't decode svg icon and save to file: " + destFile.getPath() + ", reason: " + e.getMessage());
            }
        }
    }


    private class ScryfallSymbolsDownloadJob extends DownloadJob {

        private String cssUrl = ""; // need to find url from colors page https://scryfall.com/docs/api/colors

        // listener for data parse after download complete
        private class ScryfallDownloadOnFinishedListener implements PropertyChangeListener {
            private String downloadedFile;

            public ScryfallDownloadOnFinishedListener(String ADestFile) {
                this.downloadedFile = ADestFile;
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {

                if (!evt.getPropertyName().equals(STATE_PROP_NAME)) {
                    throw new IllegalArgumentException("Unknown download property " + evt.getPropertyName());
                }

                if (evt.getNewValue() != State.FINISHED) {
                    return;
                }

                // parse data and save to dest
                parseData(this.downloadedFile);
            }
        }

        @Override
        public void onPreparing() throws Exception {
            this.cssUrl = "";

            org.jsoup.nodes.Document doc = CardImageUtils.downloadHtmlDocument(CSS_SOURCE_URL);
            org.jsoup.select.Elements cssList = doc.select(CSS_SOURCE_SELECTOR);
            if (cssList.size() == 1) {
                this.cssUrl = cssList.first().attr("href");
            }

            if (this.cssUrl.isEmpty()) {
                throw new IllegalStateException("Can't find stylesheet url from scryfall colors page.");
            } else {
                this.setSource(fromURL(this.cssUrl));
            }
        }

        private String destFile = "";

        public ScryfallSymbolsDownloadJob() {
            // download init
            super("Scryfall symbols source", fromURL(""), toFile(DOWNLOAD_TEMP_FILE)); // url setup on preparing stage
            this.destFile = DOWNLOAD_TEMP_FILE;
            this.addPropertyChangeListener(STATE_PROP_NAME, new ScryfallDownloadOnFinishedListener(this.destFile));

            // clear dest file (always download new data)
            File file = new File(this.destFile);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    private DownloadJob generateDownloadJob() {
        return new ScryfallSymbolsDownloadJob();
    }
}
