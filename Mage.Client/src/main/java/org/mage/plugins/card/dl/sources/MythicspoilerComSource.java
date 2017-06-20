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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
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
public enum MythicspoilerComSource implements CardImageSource {

    instance;
    private Map<String, String> setsAliases;
    private Map<String, String> cardNameAliases;
    private Map<String, Set<String>> cardNameAliasesStart;
    private final Map<String, Map<String, String>> sets;


    @Override
    public String getSourceName() {
        return "mythicspoiler.com";
    }

    MythicspoilerComSource() {
        sets = new LinkedHashMap<>();
        setsAliases = new HashMap<>();
        setsAliases.put("exp", "bfz");
        cardNameAliases = new HashMap<>();
        // set+wrong name from web side => correct card name
        cardNameAliases.put("MM2-otherwordlyjourney", "otherworldlyjourney");
        cardNameAliases.put("THS-fellhideminotaur", "felhideminotaur");
        cardNameAliases.put("THS-purphorosemissary", "purphorossemissary");
        cardNameAliases.put("THS-soldierofpantheon", "soldierofthepantheon");
        cardNameAliases.put("THS-vulpinegolaith", "vulpinegoliath");
        cardNameAliases.put("ORI-kothopedhoarderofsouls", "kothophedsoulhoarder");
        cardNameAliases.put("BFZ-kozliekschanneler", "kozilekschanneler");
        cardNameAliases.put("OGW-wastes", "wastes1");
        cardNameAliases.put("OGW-wastes2", "wastes2");
        cardNameAliases.put("AER-aegisautomation", "aegisautomaton");
        cardNameAliases.put("AKH-illusorywrappins", "illusorywrappings");
        cardNameAliases.put("AKH-reducerumble", "reducerubble");
        cardNameAliases.put("AKH-forsakethewordly", "forsaketheworldly");
        cardNameAliases.put("AKH-kefnatsmonument", "kefnetsmonument");

        cardNameAliasesStart = new HashMap<>();
        HashSet<String> names = new HashSet<>();
        names.add("eldrazidevastator.jpg");
        cardNameAliasesStart.put("BFZ", names);
    }

    private Map<String, String> getSetLinks(String cardSet) {
        Map<String, String> setLinks = new HashMap<>();
        try {
            String setNames = setsAliases.get(cardSet.toLowerCase());
            Set<String> aliasesStart = new HashSet<>();
            if (cardNameAliasesStart.containsKey(cardSet)) {
                aliasesStart.addAll(cardNameAliasesStart.get(cardSet));
            }
            if (setNames == null) {
                setNames = cardSet.toLowerCase();
            }
            Preferences prefs = MageFrame.getPreferences();
            Connection.ProxyType proxyType = Connection.ProxyType.valueByText(prefs.get("proxyType", "None"));
            for (String setName : setNames.split("\\^")) {
                String URLSetName = URLEncoder.encode(setName, "UTF-8");
                String baseUrl = "http://mythicspoiler.com/" + URLSetName + '/';

                Map<String, String> pageLinks = getSetLinksFromPage(cardSet, aliasesStart, prefs, proxyType, baseUrl, baseUrl);
                setLinks.putAll(pageLinks);

                // try to download images for double-faced cards
                try {
                    String doubleFacedUrl = baseUrl + "dfc.html";
                    pageLinks = getSetLinksFromPage(cardSet, aliasesStart, prefs, proxyType, baseUrl, doubleFacedUrl);
                    setLinks.putAll(pageLinks);
                } catch (Exception ex) {
                    // that's ok if we cannot download double-faced cards for some sets
                }
            }

        } catch (IOException ex) {
            System.out.println("Exception when parsing the mythicspoiler page: " + ex.getMessage());
        }
        return setLinks;
    }

    private Map<String, String> getSetLinksFromPage(String cardSet, Set<String> aliasesStart, Preferences prefs,
            ProxyType proxyType, String baseUrl, String pageUrl) throws IOException {
        Map<String, String> pageLinks = new HashMap<>();

        String urlDocument;
        Document doc;
        if (proxyType == ProxyType.NONE) {
            urlDocument = pageUrl;
            doc = Jsoup.connect(urlDocument).get();
        } else {
            String proxyServer = prefs.get("proxyAddress", "");
            int proxyPort = Integer.parseInt(prefs.get("proxyPort", "0"));
            URL url = new URL(pageUrl);
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
        if (!aliasesStart.isEmpty()) {
            for (String text : aliasesStart) {
                cardsImages.addAll(doc.select("img[src^=" + text + ']'));
            }
        }

        for (Element cardsImage : cardsImages) {
            String cardLink = cardsImage.attr("src");
            String cardName = null;
            if (cardLink.startsWith("cards/") && cardLink.endsWith(".jpg")) {
                cardName = cardLink.substring(6, cardLink.length() - 4);
            } else if (aliasesStart.contains(cardLink)) {
                cardName = cardLink.substring(0, cardLink.length() - 4);
            }
            if (cardName != null && !cardName.isEmpty()) {
                if (cardNameAliases.containsKey(cardSet + '-' + cardName)) {
                    cardName = cardNameAliases.get(cardSet + '-' + cardName);
                } else if (cardName.endsWith("1") || cardName.endsWith("2") || cardName.endsWith("3") || cardName.endsWith("4") || cardName.endsWith("5")) {
                    cardName = cardName.substring(0, cardName.length() - 1);
                } else if (cardName.endsWith("promo")) {
                    cardName = cardName.substring(0, cardName.length() - 5);
                }
                pageLinks.put(cardName, baseUrl + cardLink);
            }
        }

        return pageLinks;
    }

    @Override
    public String generateURL(CardDownloadData card) throws Exception {
        String collectorId = card.getCollectorId();
        String cardSet = card.getSet();
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        if (card.isFlippedSide()) { //doesn't support rotated images
            return null;
        }
        Map<String, String> setLinks = sets.computeIfAbsent(cardSet, k -> getSetLinks(cardSet));
        String searchName = card.getDownloadName().toLowerCase()
                .replaceAll(" ", "")
                .replaceAll("-", "")
                .replaceAll("'", "")
                .replaceAll(",", "")
                .replaceAll("/", "");
        String link = setLinks.get(searchName);
        return link;
    }

    @Override
    public String generateTokenUrl(CardDownloadData card
    ) {
        return null;
    }

    @Override
    public float getAverageSize() {
        return 50.0f;
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
    public int getTotalImages() {
        return -1;
    }

    @Override
    public boolean isTokenSource() {
        return false;
    }

    @Override
    public void doPause(String httpImageUrl) {
    }
}
