package org.mage.plugins.card.dl.sources;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mage.util.StreamUtils;
import org.mage.plugins.card.dl.DownloadJob;

import static org.mage.card.arcane.ManaSymbols.getSymbolFileNameAsSVG;
import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

// TODO: add force to download symbols (rewrite exist files)

/**
 *
 * @author jaydi85@gmail.com
 *
 */
public class ScryfallSymbolsSource implements Iterable<DownloadJob> {


    static final String SOURCE_URL = "https://assets.scryfall.com/assets/scryfall.css"; // search css-file on https://scryfall.com/docs/api/colors
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
            "2/W", "2/U", "2/B", "2/R", "2/G",
            "WP", "UP", "BP", "RP", "GP",
            "X", "S", "T", "Q", "C", "E"};

    @Override
    public Iterator<DownloadJob> iterator() {
        ArrayList<DownloadJob> jobs = new ArrayList<>();

        // all symbols on one page
        jobs.add(generateDownloadJob());

        return jobs.iterator();
    }

    private void parseData(String sourcePath){

        String sourceData = "";
        try {
            sourceData = new String(Files.readAllBytes(Paths.get(sourcePath)));
        }catch (IOException e) {
            LOGGER.error("Can't open file to parse data: " + sourcePath + " , reason: " + e.getMessage());
        }

        // gen symbols list
        ArrayList<String> allMageSymbols = new ArrayList<>();
        for(int i = 0; i < SYMBOLS_LIST.length; i++){
            allMageSymbols.add(SYMBOLS_LIST[i]);
        }
        for(Integer i = SYMBOLS_NUMBER_START; i <= SYMBOLS_NUMBER_END; i++){
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
        if(!dir.exists()){
            dir.mkdirs();
        }

        // decode and save data (only if not exist)
        for(String needCode: allMageSymbols){

            String searchCode = needCode.replace("/", "");

            if(!foundedData.containsKey(searchCode))
            {
                LOGGER.warn("Can't found symbol code from scryfall: " + searchCode);
                continue;
            }

            File destFile = getSymbolFileNameAsSVG(searchCode);
            if (destFile.exists() && (destFile.length() > 0)){
                continue;
            }
            FileOutputStream stream = null;
            try {
                // base64 transform
                String data64 = foundedData.get(searchCode);
                Base64.Decoder dec = Base64.getDecoder();
                byte[] fileData = dec.decode(data64);

                stream = new FileOutputStream(destFile);
                stream.write(fileData);

                LOGGER.info("New svg symbol downloaded: " + needCode);
            } catch  (Exception e) {
                LOGGER.error("Can't decode svg icon and save to file: " + destFile.getPath() + ", reason: " + e.getMessage());
            } finally {
                StreamUtils.closeQuietly(stream);
            }
        }
    }


    private class ScryfallSymbolsDownloadJob extends DownloadJob{

        // listener for data parse after download complete
        private class ScryDownloadOnFinishedListener implements PropertyChangeListener {
            private String downloadedFile;

            public ScryDownloadOnFinishedListener(String ADestFile){
                this.downloadedFile = ADestFile;
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (!evt.getPropertyName().equals(STATE_PROP_NAME)){
                    throw new IllegalArgumentException("Unknown download property " + evt.getPropertyName());
                }

                if (evt.getNewValue() != State.FINISHED){
                    return;
                }

                // parse data and save to dest
                parseData(this.downloadedFile);
            }
        }

        private String destFile = "";

        public ScryfallSymbolsDownloadJob() {
            super("Scryfall symbols source", fromURL(SOURCE_URL), toFile(DOWNLOAD_TEMP_FILE));
            this.destFile = DOWNLOAD_TEMP_FILE;
            this.addPropertyChangeListener(STATE_PROP_NAME, new ScryDownloadOnFinishedListener(this.destFile));

            // clear dest file (always download new data)
            File file = new File(this.destFile);
            if (file.exists()){
                file.delete();
            }
        }
    }

    private DownloadJob generateDownloadJob() {
        return new ScryfallSymbolsDownloadJob();
    }
}
