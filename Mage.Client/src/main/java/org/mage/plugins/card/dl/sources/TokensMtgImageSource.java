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
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.mage.plugins.card.images.CardDownloadData;

/**
 *
 * @author Quercitron
 */
public class TokensMtgImageSource implements CardImageSource {

    private static final Logger logger = Logger.getLogger(TokensMtgImageSource.class);

    private static CardImageSource instance = new TokensMtgImageSource();

    public static CardImageSource getInstance() {
        if (instance == null) {
            instance = new TokensMtgImageSource();
        }
        return instance;
    }

    @Override
    public String getSourceName() {
        return "tokens.mtg.onl";
    }

    @Override
    public Float getAverageSize() {
        return 26.7f;
    }

    @Override
    public String generateURL(CardDownloadData card) throws Exception {
        return null;
    }

    private static final String[] EMBLEMS = {
            "Ajani",
            "Chandra",
            "Dack",
            "Daretti",
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
                if (name.toLowerCase().contains(emblem.toLowerCase())){
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
        List<TokenData> tokensData = getTokensData();

        if (tokensData.isEmpty()) {
            return null;
        }

        List<TokenData> matchedTokens = new ArrayList<TokenData>();
        for (TokenData token : tokensData) {
            if (name.equalsIgnoreCase(token.getName()) && set.equalsIgnoreCase(token.getExpansionSetCode())) {
                matchedTokens.add(token);
            }
        }

        if (matchedTokens.isEmpty()) {
            logger.info("Could not find data for token " + name + ", set " + set + ".");
            return null;
        }

        TokenData tokenData;
        if (type == 0) {
            if (matchedTokens.size() > 1) {
                logger.info("Multiple images were found for token " + name + ", set " + set + ".");
            }
            tokenData = matchedTokens.get(0);
        } else {
            if (type > matchedTokens.size()) {
                logger.warn("Not enough images for token with type " + type + ", name " + name + ", set " + set + ".");
                return null;
            }
            tokenData = matchedTokens.get(card.getType() - 1);
        }

        String url =  "http://tokens.mtg.onl/tokens/" + tokenData.getExpansionSetCode().trim() + "_"
                + tokenData.getNumber().trim() + "-" + tokenData.getName().trim()+ ".jpg";
        url = url.replace(' ', '-');
        return url;
    }

    private List<TokenData> tokensData;

    private final Object tokensDataSync = new Object();

    private List<TokenData> getTokensData() throws IOException {
        if (tokensData == null) {
            synchronized (tokensDataSync) {
                if (tokensData == null) {
                    tokensData = new ArrayList<TokenData>();

                    // get tokens data from resource file
                    InputStream inputStream = null;
                    try {
                        inputStream = this.getClass().getResourceAsStream("/tokens-mtg-onl-list.csv");
                        List<TokenData> fileTokensData = parseTokensData(inputStream);
                        tokensData.addAll(fileTokensData);
                    } catch (Exception exception) {
                        logger.warn("Failed to get tokens description from resource file tokens-mtg-onl-list.csv", exception);
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Exception e) {
                                logger.error("Input stream close failed:", e);
                            }
                        }
                    }

                    // description on site may contain new information
                    // try to add it
                    try {
                        URL url = new URL("http://tokens.mtg.onl/data/SetsWithTokens.csv");
                        inputStream = url.openStream();
                        List<TokenData> siteTokensData = parseTokensData(inputStream);

                        List<TokenData> newTokensData = new ArrayList<TokenData>();
                        for (TokenData siteData : siteTokensData) {
                            boolean isNew = true;
                            for (TokenData fileData : tokensData) {
                                if (siteData.getName().equalsIgnoreCase(fileData.getName())
                                        && siteData.getNumber().equalsIgnoreCase(fileData.getNumber())
                                        && siteData.getExpansionSetCode().equalsIgnoreCase(fileData.getExpansionSetCode())) {
                                    isNew = false;
                                    break;
                                }
                            }
                            if (isNew) {
                                newTokensData.add(siteData);
                            }
                        }

                        tokensData.addAll(newTokensData);
                    } catch (Exception exception) {
                        logger.warn("Failed to get tokens description from tokens.mtg.onl", exception);
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Exception e) {
                                logger.error("Input stream close failed:", e);
                            }
                        }
                    }
                }
            }
        }

        return tokensData;
    }

    private List<TokenData> parseTokensData(InputStream inputStream) throws IOException {
        List<TokenData> tokensData = new ArrayList<TokenData>();

        InputStreamReader inputReader = null;
        BufferedReader reader = null;
        try {
            // we have to specify encoding to read special comma
            inputReader = new InputStreamReader(inputStream, "Cp1252");
            reader = new BufferedReader(inputReader);

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
                } else {
                    if (state == 0) {
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
                        tokensData.add(token);
                    }
                }

                line = reader.readLine();
            }
        } finally {
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (Exception e) {
                    logger.error("Input reader close failed:", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    logger.error("Buffered reader close failed:", e);
                }
            }
        }

        return tokensData;
    }

    final class TokenData {
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
