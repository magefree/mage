
package org.mage.plugins.card.dl.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import mage.constants.SubType;
import org.apache.log4j.Logger;
import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.images.DownloadPictures;
import org.mage.plugins.card.utils.CardImageUtils;

/**
 * @author Quercitron
 */
public enum TokensMtgImageSource implements CardImageSource {

    instance;
    private static final Logger logger = Logger.getLogger(TokensMtgImageSource.class);

    // [[EXP/Name, TokenData>
    private HashMap<String, ArrayList<TokenData>> tokensData;
    private static final Set<String> supportedSets = new LinkedHashSet<String>();

    private final Object tokensDataSync = new Object();

    @Override
    public String getSourceName() {
        return "tokens.mtg.onl";
    }

    @Override
    public float getAverageSize() {
        return 26.7f;
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
    public CardImageUrls generateURL(CardDownloadData card) throws Exception {
        return null;
    }

    private static final Map<String, String> SET_NAMES_REPLACEMENT = new HashMap<String, String>() {
        {
            put("con", "CFX");
            put("fnmp", "FNM");
        }
    };

    private String getEmblemName(String originalName) {

        for (SubType subType : SubType.getPlaneswalkerTypes(true)) {
            if (originalName.toLowerCase(Locale.ENGLISH).contains(subType.toString().toLowerCase(Locale.ENGLISH))) {
                return subType.getDescription() + " Emblem";
            }
        }
        return null;
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) throws IOException {
        String name = card.getName();
        String set = card.getSet();
        int type = card.getType();

        // handle emblems
        if (name.toLowerCase(Locale.ENGLISH).contains("emblem")) {
            name = getEmblemName(name);
        }

        // we should replace some set names
        if (SET_NAMES_REPLACEMENT.containsKey(set.toLowerCase(Locale.ENGLISH))) {
            set = SET_NAMES_REPLACEMENT.get(set.toLowerCase(Locale.ENGLISH));
        }

        // Image URL contains token number
        // e.g. http://tokens.mtg.onl/tokens/ORI_010-Thopter.jpg -- token number 010
        // We don't know these numbers, but we can take them from a file
        // with tokens information that can be downloaded from the site.
        if (tokensData.isEmpty()) {
            logger.info("Source " + getSourceName() + " provides no token data.");
            return null;
        }

        String key = set + "/" + name;
        List<TokenData> list = tokensData.get(key);
        if (list == null) {
            logger.warn("Could not find data for token " + name + ", set " + set + ".");
            return null;
        }

        TokenData tokenData;
        if (type == 0) {
            tokenData = list.get(0);
        } else {
            if (type > list.size()) {
                logger.warn("Not enough images variants for token with type number " + type + ", name " + name + ", set " + set + '.');
                return null;
            }
            tokenData = list.get(card.getType() - 1);
        }

        String url = "http://tokens.mtg.onl/tokens/" + tokenData.getExpansionSetCode().trim() + '_'
                + tokenData.getNumber().trim() + '-' + tokenData.getName().trim() + ".jpg";
        url = url.replace(' ', '-');
        return new CardImageUrls(url);
    }

    @Override
    public int getTotalImages() {
        return getTokenImages();
    }

    @Override
    public int getTokenImages() {
        try {
            getTokensData();
        } catch (IOException ex) {
            logger.error(getSourceName() + ": Loading available data failed. " + ex.getMessage());
        }
        return tokensData.size();
    }

    @Override
    public boolean isTokenSource() {
        return true;
    }

    @Override
    public void doPause(String httpImageUrl) {
    }

    @Override
    public ArrayList<String> getSupportedSets() {
        ArrayList<String> supportedSetsCopy = new ArrayList<>();
        supportedSetsCopy.addAll(supportedSets);
        return supportedSetsCopy;
    }

    @Override
    public boolean isImageProvided(String setCode, String cardName) {
        String searchName = cardName;
        if (cardName.toLowerCase(Locale.ENGLISH).contains("emblem")) {
            searchName = getEmblemName(cardName);
        }
        try {
            getTokensData();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(TokensMtgImageSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        String key = setCode + "/" + searchName;
        return (tokensData.containsKey(key));
    }

    @Override
    public boolean isSetSupportedComplete(String setCode) {
        return false;
    }

    private HashMap<String, ArrayList<TokenData>> getTokensData() throws IOException {
        synchronized (tokensDataSync) {
            if (tokensData == null) {
                DownloadPictures.getInstance().updateAndViewMessage("Creating token data...");
                tokensData = new HashMap<>();

                // get tokens data from resource file
                try (InputStream inputStream = this.getClass().getResourceAsStream("/tokens-mtg-onl-list.csv")) {
                    List<TokenData> fileTokensData = parseTokensData(inputStream);
                    for (TokenData tokenData : fileTokensData) {
                        String key = tokenData.getExpansionSetCode() + "/" + tokenData.getName();
                        ArrayList<TokenData> list = tokensData.get(key);
                        if (list == null) {
                            list = new ArrayList<>();
                            tokensData.put(key, list);
                            supportedSets.add(tokenData.getExpansionSetCode());
                            logger.debug("Added key: " + key);
                        }
                        list.add(tokenData);
                    }
                } catch (Exception exception) {
                    logger.warn("Failed to get tokens description from resource file tokens-mtg-onl-list.csv", exception);
                }

                String urlString = "http://tokens.mtg.onl/data/SetsWithTokens.csv";
                Proxy proxy = CardImageUtils.getProxyFromPreferences();
                URLConnection conn = proxy == null ? new URL(urlString).openConnection() : new URL(urlString).openConnection(proxy);

                // description on site may contain new information
                // try to add it
                try (InputStream inputStream = conn.getURL().openStream()) {
                    List<TokenData> siteTokensData = parseTokensData(inputStream);
                    for (TokenData siteData : siteTokensData) {
                        // logger.info("TOK: " + siteData.getExpansionSetCode() + "/" + siteData.getName());
                        String key = siteData.getExpansionSetCode() + "/" + siteData.getName();
                        supportedSets.add(siteData.getExpansionSetCode());
                        ArrayList<TokenData> list = tokensData.get(key);
                        if (list == null) {
                            list = new ArrayList<>();
                            tokensData.put(key, list);
                            list.add(siteData);
                        } else {
                            boolean newToken = true;
                            for (TokenData tokenData : list) {
                                if (siteData.getNumber().equals(tokenData.number)) {
                                    newToken = false;
                                    break;
                                }
                            }
                            if (newToken) {
                                list.add(siteData);
                            }
                        }
                    }
                    DownloadPictures.getInstance().updateAndViewMessage("");
                } catch (Exception ex) {
                    logger.warn("Failed to get tokens description from tokens.mtg.onl", ex);
                    DownloadPictures.getInstance().updateAndViewMessage(ex.getMessage());
                }
            }
        }

        return tokensData;
    }

    private List<TokenData> parseTokensData(InputStream inputStream) throws IOException {
        List<TokenData> newTokensData = new ArrayList<>();

        try (InputStreamReader inputReader = new InputStreamReader(inputStream, "Cp1252");
             BufferedReader reader = new BufferedReader(inputReader)) {
            // we have to specify encoding to read special comma

            reader.readLine(); // skip header
            String line = reader.readLine();
            // states
            // 0 - wait set name
            // 1 - wait cards
            // 2 - process cards
            int state = 0;
            String set = null;
            while (line != null) {
                if (line.trim().isEmpty()) {
                    if (state == 2) {
                        state = 0;
                    }
                } else if (state == 0) {
                    set = line.substring(0, 3);
                    state = 1;
                } else {
                    if (state == 1) {
                        state = 2;
                    }
                    String[] split = line.split(",");
                    // replace special comma for cards like 'Ashaya‚ the Awoken World'
                    String name = split[0].replace('‚', ',').replace("â€š", ",");
                    String number = split[1];
                    TokenData token = new TokenData(name, number, set);
                    newTokensData.add(token);
                }

                line = reader.readLine();
            }
        }

        return newTokensData;
    }

    static final class TokenData {

        final private String name;
        final private String number;
        final private String expansionSetCode;

        public TokenData(String name, String number, String expansionSetCode) {
            this.name = name;
            this.number = number;
            this.expansionSetCode = expansionSetCode;
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }

        public String getExpansionSetCode() {
            return expansionSetCode;
        }
    }

}
