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
 * @author North
 */
public class WizardCardsImageSource implements CardImageSource {

    private static CardImageSource instance;
    private static Map<String, String> setsAliases;
    private final Map<String, Map<String, String>> sets;

    public static CardImageSource getInstance() {
        if (instance == null) {
            instance = new WizardCardsImageSource();
        }
        return instance;
    }

    @Override
    public String getSourceName() {
        return "WOTC Gatherer";
    }

    public WizardCardsImageSource() {
        sets = new HashMap<>();
        setsAliases = new HashMap<>();
        setsAliases.put("2ED", "Unlimited Edition");
        setsAliases.put("10E", "Tenth Edition");
        setsAliases.put("3ED", "Revised Edition");
        setsAliases.put("4ED", "Fourth Edition");
        setsAliases.put("5DN", "Fifth Dawn");
        setsAliases.put("5ED", "Fifth Edition");
        setsAliases.put("6ED", "Classic Sixth Edition");
        setsAliases.put("7ED", "Seventh Edition");
        setsAliases.put("8ED", "Eighth Edition");
        setsAliases.put("9ED", "Ninth Edition");
        setsAliases.put("ALA", "Shards of Alara");
        setsAliases.put("ALL", "Alliances");
        setsAliases.put("APC", "Apocalypse");
        setsAliases.put("ARB", "Alara Reborn");
        setsAliases.put("ARC", "Archenemy");
        setsAliases.put("ARN", "Arabian Nights");
        setsAliases.put("ATH", "Anthologies");
        setsAliases.put("ATQ", "Antiquities");
        setsAliases.put("AVR", "Avacyn Restored");
        setsAliases.put("BFZ", "Battle for Zendikar");
        setsAliases.put("BNG", "Born of the Gods");
        setsAliases.put("BOK", "Betrayers of Kamigawa");
        setsAliases.put("BRB", "Battle Royale Box Set");
        setsAliases.put("BTD", "Beatdown Box Set");
        setsAliases.put("C13", "Commander 2013 Edition");
        setsAliases.put("C14", "Commander 2014");
        setsAliases.put("CHK", "Champions of Kamigawa");
        setsAliases.put("CHR", "Chronicles");
        setsAliases.put("CMD", "Magic: The Gathering-Commander");
        setsAliases.put("CNS", "Magic: The Gathering—Conspiracy");
        setsAliases.put("CON", "Conflux");
        setsAliases.put("CSP", "Coldsnap");
        setsAliases.put("DD2", "Duel Decks: Jace vs. Chandra");
        setsAliases.put("DD3A", "Duel Decks Anthology, Divine vs. Demonic");
        setsAliases.put("DD3B", "Duel Decks Anthology, Elves vs. Goblins");
        setsAliases.put("DD3C", "Duel Decks Anthology, Garruk vs. Liliana");
        setsAliases.put("DD3D", "Duel Decks Anthology, Jace vs. Chandra");
        setsAliases.put("DDC", "Duel Decks: Divine vs. Demonic");
        setsAliases.put("DDD", "Duel Decks: Garruk vs. Liliana");
        setsAliases.put("DDE", "Duel Decks: Phyrexia vs. the Coalition");
        setsAliases.put("DDF", "Duel Decks: Elspeth vs. Tezzeret");
        setsAliases.put("DDG", "Duel Decks: Knights vs. Dragons");
        setsAliases.put("DDH", "Duel Decks: Ajani vs. Nicol Bolas");
        setsAliases.put("DDI", "Duel Decks: Venser vs. Koth");
        setsAliases.put("DDJ", "Duel Decks: Izzet vs. Golgari");
        setsAliases.put("DDK", "Duel Decks: Sorin vs. Tibalt");
        setsAliases.put("DDL", "Duel Decks: Heroes vs. Monsters");
        setsAliases.put("DDM", "Duel Decks: Jace vs. Vraska");
        setsAliases.put("DDN", "Duel Decks: Speed vs. Cunning");
        setsAliases.put("DDO", "Duel Decks: Elspeth vs. Kiora");
        setsAliases.put("DDP", "Duel Decks: Zendikar vs. Eldrazi");
        setsAliases.put("DGM", "Dragon's Maze");
        setsAliases.put("DIS", "Dissension");
        setsAliases.put("DKA", "Dark Ascension");
        setsAliases.put("DKM", "Deckmasters");
        setsAliases.put("DRK", "The Dark");
        setsAliases.put("DST", "Darksteel");
        setsAliases.put("DTK", "Dragons of Tarkir");
        setsAliases.put("EVE", "Eventide");
        setsAliases.put("EVG", "Duel Decks: Elves vs. Goblins");
        setsAliases.put("EXO", "Exodus");
        setsAliases.put("FEM", "Fallen Empires");
        setsAliases.put("FNMP", "Friday Night Magic");
        setsAliases.put("FRF", "Fate Reforged");
        setsAliases.put("FUT", "Future Sight");
        setsAliases.put("FVD", "From the Vault: Dragons");
        setsAliases.put("FVE", "From the Vault: Exiled");
        setsAliases.put("FVL", "From the Vault: Legends");
        setsAliases.put("FVR", "From the Vault: Relics");
        setsAliases.put("GPT", "Guildpact");
        setsAliases.put("GPX", "Grand Prix");
        setsAliases.put("GRC", "WPN Gateway");
        setsAliases.put("GTC", "Gatecrash");
        setsAliases.put("HML", "Homelands");
        setsAliases.put("HOP", "Planechase");
        setsAliases.put("ICE", "Ice Age");
        setsAliases.put("INV", "Invasion");
        setsAliases.put("ISD", "Innistrad");
        setsAliases.put("JOU", "Journey into Nyx");
        setsAliases.put("JR", "Judge Promo");
        setsAliases.put("JUD", "Judgment");
        setsAliases.put("KTK", "Khans of Tarkir");
        setsAliases.put("LEA", "Limited Edition Alpha");
        setsAliases.put("LEB", "Limited Edition Beta");
        setsAliases.put("LEG", "Legends");
        setsAliases.put("LGN", "Legions");
        setsAliases.put("LRW", "Lorwyn");
        setsAliases.put("M10", "Magic 2010");
        setsAliases.put("M11", "Magic 2011");
        setsAliases.put("M12", "Magic 2012");
        setsAliases.put("M13", "Magic 2013");
        setsAliases.put("M14", "Magic 2014");
        setsAliases.put("M15", "Magic 2015");
        setsAliases.put("MBP", "Media Inserts");
        setsAliases.put("MBS", "Mirrodin Besieged");
        setsAliases.put("ME2", "Masters Edition II");
        setsAliases.put("ME3", "Masters Edition III");
        setsAliases.put("ME4", "Masters Edition IV");
        setsAliases.put("MED", "Masters Edition");
//        setsAliases.put("MGDC", "Game Day");
        setsAliases.put("MIR", "Mirage");
        setsAliases.put("MLP", "Launch Party");
        setsAliases.put("MMA", "Modern Masters");
        setsAliases.put("MM2", "Modern Masters 2015");
        setsAliases.put("MMQ", "Mercadian Masques");
        setsAliases.put("MOR", "Morningtide");
        setsAliases.put("MPRP", "Magic Player Rewards");
        setsAliases.put("MRD", "Mirrodin");
        setsAliases.put("NMS", "Nemesis");
        setsAliases.put("NPH", "New Phyrexia");
        setsAliases.put("ODY", "Odyssey");
        setsAliases.put("ONS", "Onslaught");
        setsAliases.put("ORI", "Magic Origins");
        setsAliases.put("PC2", "Planechase 2012 Edition");
        setsAliases.put("PCY", "Prophecy");
        setsAliases.put("PD2", "Premium Deck Series: Fire and Lightning");
        setsAliases.put("PDS", "Premium Deck Series: Slivers");
        setsAliases.put("PLC", "Planar Chaos");
        setsAliases.put("PLS", "Planeshift");
        setsAliases.put("PO2", "Portal Second Age");
        setsAliases.put("POR", "Portal");
        setsAliases.put("PTC", "Prerelease Events");
        setsAliases.put("PTK", "Portal Three Kingdoms");
        setsAliases.put("RAV", "Ravnica: City of Guilds");
        setsAliases.put("ROE", "Rise of the Eldrazi");
        setsAliases.put("RTR", "Return to Ravnica");
        setsAliases.put("S00", "Starter 2000");
        setsAliases.put("S99", "Starter 1999");
        setsAliases.put("SCG", "Scourge");
        setsAliases.put("SHM", "Shadowmoor");
        setsAliases.put("SOK", "Saviors of Kamigawa");
        setsAliases.put("SOM", "Scars of Mirrodin");
        setsAliases.put("STH", "Stronghold");
        setsAliases.put("THS", "Theros");
        setsAliases.put("TMP", "Tempest");
        setsAliases.put("TOR", "Torment");
        setsAliases.put("TPR", "Tempest Remastered");
        setsAliases.put("TSB", "Time Spiral \"Timeshifted\"");
        setsAliases.put("TSP", "Time Spiral");
        setsAliases.put("UDS", "Urza's Destiny");
        setsAliases.put("UGL", "Unglued");
        setsAliases.put("ULG", "Urza's Legacy");
        setsAliases.put("UNH", "Unhinged");
        setsAliases.put("USG", "Urza's Saga");
        setsAliases.put("V12", "From the Vault: Realms");
        setsAliases.put("V13", "From the Vault: Twenty");
        setsAliases.put("V14", "From the Vault: Annihilation (2014)");
        setsAliases.put("V15", "From the Vault: Angels (2015)");
        setsAliases.put("VG1", "Vanguard Set 1");
        setsAliases.put("VG2", "Vanguard Set 2");
        setsAliases.put("VG3", "Vanguard Set 3");
        setsAliases.put("VG4", "Vanguard Set 4");
        setsAliases.put("VGO", "MTGO Vanguard");
        setsAliases.put("VIS", "Visions");
        setsAliases.put("VMA", "Vintage Masters");
        setsAliases.put("WMCQ", "World Magic Cup Qualifier");
        setsAliases.put("WTH", "Weatherlight");
        setsAliases.put("WWK", "Worldwake");
        setsAliases.put("ZEN", "Zendikar");
    }

    private Map<String, String> getSetLinks(String cardSet) {
        Map<String, String> setLinks = new HashMap<>();
        try {
            String setNames = setsAliases.get(cardSet);
            Preferences prefs = MageFrame.getPreferences();
            Connection.ProxyType proxyType = Connection.ProxyType.valueByText(prefs.get("proxyType", "None"));
            for (String setName : setNames.split("\\^")) {
                String URLSetName = URLEncoder.encode(setName, "UTF-8");
                String urlDocument;
                int page = 0;
                int firstMultiverseIdLastPage = 0;
                Pages:
                while (page < 999) {
                    Document doc;
                    if (proxyType.equals(ProxyType.NONE)) {
                        urlDocument = "http://gatherer.wizards.com/Pages/Search/Default.aspx?page=" + page +"&output=spoiler&method=visual&action=advanced&set=+[%22" + URLSetName + "%22]";
                        doc = Jsoup.connect(urlDocument).get();
                    } else {
                        String proxyServer = prefs.get("proxyAddress", "");
                        int proxyPort = Integer.parseInt(prefs.get("proxyPort", "0"));
                        URL url = new URL("http://gatherer.wizards.com/Pages/Search/Default.aspx?page=" + page +"&output=spoiler&method=visual&action=advanced&set=+[%22" + URLSetName + "%22]");
                        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServer, proxyPort)); 
                        HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);

                        uc.connect();

                        String line;
                        StringBuffer tmp = new StringBuffer();
                        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                        while ((line = in.readLine()) != null) {
                          tmp.append(line);
                        }
                        doc = Jsoup.parse(String.valueOf(tmp));
                    }
                    
                    Elements cardsImages = doc.select("img[src^=../../Handlers/]");
                    if (cardsImages.isEmpty()) {
                        break;
                    }
                        
                    for (int i = 0; i < cardsImages.size(); i++) {
                        if (i == 0) {
                            Integer multiverseId = Integer.parseInt(cardsImages.get(i).attr("src").replaceAll("[^\\d]", ""));
                            if (multiverseId == firstMultiverseIdLastPage) {
                                break Pages;
                            }
                            firstMultiverseIdLastPage = multiverseId;
                        }
                        String cardName = normalizeName(cardsImages.get(i).attr("alt"));
                        if (cardName != null && !cardName.isEmpty()) {
                            if (cardName.equals("Forest") || cardName.equals("Swamp") || cardName.equals("Mountain") || cardName.equals("Island") || cardName.equals("Plains")) {
                            	Integer multiverseId = Integer.parseInt(cardsImages.get(i).attr("src").replaceAll("[^\\d]", ""));
                            	String urlLandDocument = "http://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=" + multiverseId;
                            	Document landDoc = Jsoup.connect(urlLandDocument).get();
                            	Elements variations = landDoc.select("a.variationlink");
                            	if(!variations.isEmpty()) {
                            		int landNumber = 1;
                            		for (Element variation : variations) {
                            			Integer landMultiverseId = Integer.parseInt(variation.attr("onclick").replaceAll("[^\\d]", ""));
                            			// ""
                            			setLinks.put((cardName + landNumber).toLowerCase(), "/Handlers/Image.ashx?multiverseid=" +landMultiverseId + "&type=card");
                            			landNumber++;
                            		}
                            	} else {
                            		setLinks.put(cardName.toLowerCase(), cardsImages.get(i).attr("src").substring(5));
                            	}
                            } else {
                                setLinks.put(cardName.toLowerCase(), cardsImages.get(i).attr("src").substring(5));
                            }
                        }
                    }
                    page++;
                }
            }
        } catch (IOException ex) {
            System.out.println("Exception when parsing the wizards page: " + ex.getMessage());
        }
        return setLinks;
    }

    private String normalizeName(String name) {
    	//Split card
    	if(name.contains("//")) {
    		name = name.substring(0, name.indexOf("(") - 1);
    	}
    	//Special timeshifted name
    	if(name.startsWith("XX")) {
    		name = name.substring(name.indexOf("(") + 1, name.length() - 1);
    	}
        return name.replace("\u2014", "-").replace("\u2019", "'")
                .replace("\u00C6", "AE").replace("\u00E6", "ae")
                .replace("\u00C3\u2020", "AE")                
                .replace("\u00C1", "A").replace("\u00E1", "a")
                .replace("\u00C2", "A").replace("\u00E2", "a")
                .replace("\u00D6", "O").replace("\u00F6", "o")
                .replace("\u00DB", "U").replace("\u00FB", "u")
                .replace("\u00DC", "U").replace("\u00FC", "u")
                .replace("\u00E9", "e").replace("&", "//")
                .replace("Hintreland Scourge", "Hinterland Scourge");
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
        String setNames = setsAliases.get(cardSet);
        if (setNames != null) {
            Map<String, String> setLinks = sets.get(cardSet);
            if (setLinks == null) {
                setLinks = getSetLinks(cardSet);
                sets.put(cardSet, setLinks);
            }
            String link = setLinks.get(card.getDownloadName().toLowerCase());
            if (link == null) {
                if (setLinks.size() >= collectorId) {
                    link = setLinks.get(Integer.toString(collectorId - 1));
                } else {
                    link = setLinks.get(Integer.toString(collectorId - 21));
                    if (link != null) {
                        link = link.replace(Integer.toString(collectorId - 20), (Integer.toString(collectorId - 20) + "a"));
                    }
                }
            }
            if (link != null && !link.startsWith("http://")) {
                link = "http://gatherer.wizards.com" + link;
            }
            return link;
        }
        return null;
    }

    @Override
    public String generateTokenUrl(CardDownloadData card) {
        return null;
    }

    @Override
    public Float getAverageSize() {
        return 60.0f;
    }
}
