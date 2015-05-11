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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import mage.client.MageFrame;
import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mage.plugins.card.images.CardDownloadData;

/**
 *
 * @author LevelX2
 */
public class MythicspoilerComSource implements CardImageSource {

    private static CardImageSource instance;
    private static Map<String, String> setsAliases;
    private static Map<String, String> cardNameAliases;
    private final Map<String, Map<String, String>> sets;

    public static CardImageSource getInstance() {
        if (instance == null) {
            instance = new MythicspoilerComSource();
        }
        return instance;
    }

    @Override
    public String getSourceName() {
        return "mythicspoiler.com";
    }

    public MythicspoilerComSource() {
        sets = new HashMap<>();
        setsAliases = new HashMap<>();
        cardNameAliases = new HashMap<>();
        // set+wrong name from web side => correct card name
        cardNameAliases.put("MM2-otherwordlyjourney", "otherworldlyjourney");
        cardNameAliases.put("THS-fellhideminotaur", "felhideminotaur");
        cardNameAliases.put("THS-purphorosemissary", "purphorossemissary");
        cardNameAliases.put("THS-soldierofpantheon", "soldierofthepantheon");
        cardNameAliases.put("THS-vulpinegolaith", "vulpinegoliath");
    }

    private Map<String, String> getSetLinks(String cardSet) {
        Map<String, String> setLinks = new HashMap<>();
        try {
            String setNames = setsAliases.get(cardSet.toLowerCase());
            if (setNames == null) {
                setNames = cardSet.toLowerCase();
            }
            Preferences prefs = MageFrame.getPreferences();
            Connection.ProxyType proxyType = Connection.ProxyType.valueByText(prefs.get("proxyType", "None"));
            for (String setName : setNames.split("\\^")) {
                String URLSetName = URLEncoder.encode(setName, "UTF-8");
                String baseUrl = "http://mythicspoiler.com/" + URLSetName + "/";
                String urlDocument;
                Document doc;
                if (proxyType.equals(ProxyType.NONE)) {
                    urlDocument = baseUrl;
                    doc = Jsoup.connect(urlDocument).get();
                } else {
                    String proxyServer = prefs.get("proxyAddress", "");
                    int proxyPort = Integer.parseInt(prefs.get("proxyPort", "0"));
                    URL url = new URL(baseUrl);
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServer, proxyPort));
                    HttpURLConnection uc = (HttpURLConnection) url.openConnection(proxy);

                    uc.connect();

                    String line;
                    StringBuffer tmp = new StringBuffer();
                    BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    while ((line = in.readLine()) != null) {
                        tmp.append(line);
                    }
                    doc = Jsoup.parse(String.valueOf(tmp));
                }

                Elements cardsImages = doc.select("img[src^=cards/]"); // starts with cards/
                if (cardsImages.isEmpty()) {
                    break;
                }

                for (Element cardsImage : cardsImages) {
                    String cardLink = cardsImage.attr("src");
                    if (cardLink.startsWith("cards/") && cardLink.endsWith(".jpg")) {
                        String cardName = cardLink.substring(6, cardLink.length() - 4);
                        if (cardName != null && !cardName.isEmpty()) {
                            if (cardNameAliases.containsKey(cardSet + "-" + cardName)) {
                                cardName = cardNameAliases.get(cardSet + "-" + cardName);
                            }
                            if (cardName.endsWith("1") || cardName.endsWith("2") || cardName.endsWith("3")|| cardName.endsWith("4")|| cardName.endsWith("5")) {
                                if (!cardName.startsWith("forest") && 
                                        !cardName.startsWith("swamp") && 
                                        !cardName.startsWith("mountain") && 
                                        !cardName.startsWith("island") && 
                                        !cardName.startsWith("plains")) {
                                    cardName = cardName.substring(0, cardName.length() - 1);
                                }                                
                            }
                            setLinks.put(cardName, baseUrl + cardLink);
                        }                        
                    }

                }
            }
        } catch (IOException ex) {
            System.out.println("Exception when parsing the mythicspoiler page: " + ex.getMessage());
        }
        return setLinks;
    }   

    @Override
    public String generateURL(CardDownloadData card) throws Exception {
        Integer collectorId = card.getCollectorId();
        String cardSet = card.getSet();
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        if (card.isFlippedSide()) { //doesn't support rotated images
            return null;
        }
        Map<String, String> setLinks = sets.get(cardSet);
        if (setLinks == null) {
            setLinks = getSetLinks(cardSet);
            sets.put(cardSet, setLinks);
        }
        String searchName = card.getDownloadName().toLowerCase()
                .replaceAll(" ", "")
                .replaceAll("-", "")
                .replaceAll("'", "")
                .replaceAll(",", "");
        String link = setLinks.get(searchName);
        return link;
    }

    @Override
    public String generateTokenUrl(CardDownloadData card) {
        return null;
    }

    @Override
    public Float getAverageSize() {
        return 50.0f;
    }
}
