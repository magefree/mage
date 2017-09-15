/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.plugins.card.dl.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.images.DownloadPictures;

/**
 *
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
    public String generateURL(CardDownloadData card) throws Exception {
        return null;
    }

    private static final String[] EMBLEMS = {
        "Ajani",
        "Arlinn",
        "Chandra",
        "Dack",
        "Daretti",
        "Dovin",
        "Domri",
        "Elspeth",
        "Garruk",
        "Gideon",
        "Jace",
        "Kiora",
        "Koth",
        "Liliana",
        "Narset",
        "Nixilis",
        "Sarkhan",
        "Sorin",
        "Tamiyo",
        "Teferi",
        "Venser",
        // Custom Emblems
        "Yoda",
        "Obi-Wan Kenobi",
        "Aurra Sing"
    };

    private static final Map<String, String> SET_NAMES_REPLACEMENT = new HashMap<String, String>() {
        {
            put("con", "CFX");
            put("fnmp", "FNM");
        }
    };

    @Override
    public String generateTokenUrl(CardDownloadData card) throws IOException {
        String name = card.getName();
        String set = card.getSet();
        int type = card.getType();

        // handle emblems
        if (name.toLowerCase().contains("emblem")) {
            for (String emblem : EMBLEMS) {
                if (name.toLowerCase().contains(emblem.toLowerCase())) {
                    name = emblem + " Emblem";
                    break;
                }
            }
        }

        // we should replace some set names
        if (SET_NAMES_REPLACEMENT.containsKey(set.toLowerCase())) {
            set = SET_NAMES_REPLACEMENT.get(set.toLowerCase());
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
            logger.info("Could not find data for token " + name + ", set " + set + ".");
            return null;
        }

        TokenData tokenData;
        if (type == 0) {
            if (list.size() > 1) {
                logger.info("Multiple images were found for token " + name + ", set " + set + '.');
            }
            logger.info("Token found: " + name + ", set " + set + '.');
            tokenData = list.get(0);
        } else {
            if (type > list.size()) {
                logger.warn("Not enough images for token with type " + type + ", name " + name + ", set " + set + '.');
                return null;
            }
            tokenData = list.get(card.getType() - 1);
        }

        String url = "http://tokens.mtg.onl/tokens/" + tokenData.getExpansionSetCode().trim() + '_'
                + tokenData.getNumber().trim() + '-' + tokenData.getName().trim() + ".jpg";
        url = url.replace(' ', '-');
        return url;
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
                            logger.info("Added key: " + key);
                        }
                        list.add(tokenData);
                    }
                } catch (Exception exception) {
                    logger.warn("Failed to get tokens description from resource file tokens-mtg-onl-list.csv", exception);
                }

                // description on site may contain new information
                // try to add it
                URL url = new URL("http://tokens.mtg.onl/data/SetsWithTokens.csv");
                try (InputStream inputStream = url.openStream()) {
                    List<TokenData> siteTokensData = parseTokensData(inputStream);
                    for (TokenData siteData : siteTokensData) {
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
                    String name = split[0].replace('‚', ',');
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
        try {
            getTokensData();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(TokensMtgImageSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        String key = setCode + "/" + cardName;
        return (tokensData.containsKey(key));
    }

    @Override
    public boolean isSetSupportedComplete(String setCode) {
        return false;
    }

}
