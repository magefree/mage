package org.mage.plugins.card.dl.sources;

import mage.client.MageFrame;
import mage.remote.Connection;
import mage.remote.Connection.ProxyType;
import mage.util.CardUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.*;
import java.util.prefs.Preferences;

/**
 * @author LevelX2
 */
public enum MythicspoilerComSource implements CardImageSource {

    instance;
    private final Map<String, String> setsAliases;
    private final Map<String, String> cardNameAliases;
    private final Map<String, Set<String>> cardNameAliasesStart;
    private final Map<String, Map<String, String>> sets;
    private final Set<String> supportedSets;
    private final Map<String, Map<String, String>> manualLinks;

    @Override
    public String getSourceName() {
        return "mythicspoiler.com";
    }

    MythicspoilerComSource() {
        supportedSets = new LinkedHashSet<>();
//        supportedSets.add("LEA");
        supportedSets.add("LEB");
//        supportedSets.add("2ED");
        supportedSets.add("ARN");
        supportedSets.add("ATQ");
        supportedSets.add("3ED");
        supportedSets.add("LEG");
        supportedSets.add("DRK");
        supportedSets.add("FEM");
//        supportedSets.add("4ED");
        supportedSets.add("ICE");
        supportedSets.add("CHR");
        supportedSets.add("HML");
        supportedSets.add("ALL");
        supportedSets.add("MIR");
        supportedSets.add("VIS");
//        supportedSets.add("5ED");
//        supportedSets.add("POR");
        supportedSets.add("WTH");
        supportedSets.add("TMP");
        supportedSets.add("STH");
        supportedSets.add("EXO");
//        supportedSets.add("P02");
//        supportedSets.add("UGL");
        supportedSets.add("USG");
//        supportedSets.add("DD3DVD");
//        supportedSets.add("DD3EVG");
//        supportedSets.add("DD3GVL");
//        supportedSets.add("DD3JVC");

//        supportedSets.add("ULG");
//        supportedSets.add("6ED");
        supportedSets.add("UDS");
        supportedSets.add("PTK");
//        supportedSets.add("S99");
        supportedSets.add("MMQ");
        // supportedSets.add("BRB");Battle Royale Box Set
        supportedSets.add("NEM");
//        supportedSets.add("S00");
        supportedSets.add("PCY");
        supportedSets.add("INV");
        // supportedSets.add("BTD"); // Beatdown Boxset
        supportedSets.add("PLS");
        supportedSets.add("7ED");
        supportedSets.add("APC");
        supportedSets.add("ODY");
        // supportedSets.add("DKM"); // Deckmasters 2001
        supportedSets.add("TOR");
        supportedSets.add("JUD");
        supportedSets.add("ONS");
        supportedSets.add("LGN");
        supportedSets.add("SCG");
        supportedSets.add("8ED");
        supportedSets.add("MRD");
        supportedSets.add("DST");
        supportedSets.add("5DN");
        supportedSets.add("CHK");
        supportedSets.add("UNH");
        supportedSets.add("BOK");
        supportedSets.add("SOK");
        supportedSets.add("9ED");
        supportedSets.add("RAV");
        supportedSets.add("GPT");
        supportedSets.add("DIS");
        supportedSets.add("CSP");
        supportedSets.add("TSP");
        supportedSets.add("TSB");
        supportedSets.add("PLC");
        supportedSets.add("FUT");
        supportedSets.add("10E");
        supportedSets.add("MED");
        supportedSets.add("LRW");
        supportedSets.add("EVG");
        supportedSets.add("MOR");
        supportedSets.add("SHM");
        supportedSets.add("EVE");
        supportedSets.add("DRB");
//        supportedSets.add("ME2");
        supportedSets.add("ALA");
//        supportedSets.add("DD2");
        supportedSets.add("CON");
//        supportedSets.add("DDC");
        supportedSets.add("ARB");
        supportedSets.add("M10");
        // supportedSets.add("TD0"); // Magic Online Deck Series
//        supportedSets.add("V09");
        supportedSets.add("HOP");
//        supportedSets.add("ME3");
        supportedSets.add("ZEN");
//        supportedSets.add("DDD");
        supportedSets.add("H09");
        supportedSets.add("WWK");
//        supportedSets.add("DDE");
        supportedSets.add("ROE");
        supportedSets.add("DPA");
        supportedSets.add("ARC");
        supportedSets.add("M11");
//        supportedSets.add("V10");
//        supportedSets.add("DDF");
        supportedSets.add("SOM");
//        supportedSets.add("TD0"); // Commander Theme Decks
//        supportedSets.add("PD2");
//        supportedSets.add("ME4");
        supportedSets.add("MBS");
//        supportedSets.add("DDG");
        supportedSets.add("NPH");
        supportedSets.add("CMD");
        supportedSets.add("M12");
        supportedSets.add("V11");
//        supportedSets.add("DDH");
        supportedSets.add("ISD");
        supportedSets.add("PD3");
        supportedSets.add("DKA");
//        supportedSets.add("DDI");
        supportedSets.add("AVR");
//        supportedSets.add("PC2");
        supportedSets.add("M13");
//        supportedSets.add("V12");
//        supportedSets.add("DDJ");
        supportedSets.add("RTR");
//        supportedSets.add("CM1");
        // supportedSets.add("TD2"); // Duel Decks: Mirrodin Pure vs. New Phyrexia
        supportedSets.add("GTC");
//        supportedSets.add("DDK");
        supportedSets.add("DGM");
//        supportedSets.add("MMA");
        supportedSets.add("M14");
        supportedSets.add("V13");
//        supportedSets.add("DDL");
        supportedSets.add("THS");
        supportedSets.add("C13");
        supportedSets.add("BNG");
//        supportedSets.add("DDM");
        supportedSets.add("JOU");
        // supportedSets.add("MD1"); // Modern Event Deck
//        supportedSets.add("CNS");
//        supportedSets.add("VMA");
        supportedSets.add("M15");
        supportedSets.add("V14");
        supportedSets.add("DDN");
        supportedSets.add("KTK");
        supportedSets.add("C14");
        // supportedSets.add("DD3"); // Duel Decks Anthology
        supportedSets.add("FRF");
//        supportedSets.add("DDO");
//        supportedSets.add("DTK");
//        supportedSets.add("TPR");
        supportedSets.add("MM2");
        supportedSets.add("ORI");
//        supportedSets.add("V15");
//        supportedSets.add("DDP");
        supportedSets.add("BFZ");
//        supportedSets.add("EXP");
        supportedSets.add("C15");
        // supportedSets.add("PZ1"); // Legendary Cube
        supportedSets.add("OGW");
//        supportedSets.add("DDQ");
//        supportedSets.add("W16");
        supportedSets.add("SOI");
        supportedSets.add("EMA");
        supportedSets.add("EMN");
//        supportedSets.add("V16");
        supportedSets.add("CN2");
//        supportedSets.add("DDR");
        supportedSets.add("KLD");
//        supportedSets.add("MPS");
        // supportedSets.add("PZ2");
        supportedSets.add("C16");
//        supportedSets.add("PCA");
        supportedSets.add("AER");
        supportedSets.add("MM3");
//        supportedSets.add("W17");
        supportedSets.add("AKH");
        supportedSets.add("MPS");
        supportedSets.add("CMA");
        supportedSets.add("E01");
        supportedSets.add("HOU");
        supportedSets.add("C17");
        supportedSets.add("IMA");
        supportedSets.add("XLN");
        supportedSets.add("UST");
        supportedSets.add("RIX");
        supportedSets.add("DOM");
        supportedSets.add("BBD");
        supportedSets.add("M19");
        supportedSets.add("C18");
        supportedSets.add("CM2");
        supportedSets.add("GRN");

        sets = new LinkedHashMap<>();
        setsAliases = new HashMap<>();
        setsAliases.put("exp", "bfz");
        setsAliases.put("xln", "ixa");
        setsAliases.put("nem", "nms");
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
        cardNameAliases.put("XLN-kinjaliscaller", "kinjalliscaller");
        cardNameAliases.put("XLN-lookoutsdecision", "lookoutsdispersal");
        cardNameAliases.put("XLN-infuriatedgladiodon", "ragingswordtooth");
        cardNameAliases.put("XLN-redoubledvolley", "repeatingbarrage");
        cardNameAliases.put("UST-captialoffense", "capitaloffense");
        cardNameAliases.put("RIX-tetzimocdeathprimordial", "tetzimocprimaldeath");
        // <card name, card link>
        manualLinks = new HashMap<>();
        Map<String, String> links = new HashMap<>();
        links.put("templeofaclazotz", "templeofaclazotz");
        links.put("conquerorsfoothold", "conquerorsfoothold");
        links.put("primalwellspring", "primalwellspring");
        links.put("azcantathesunkenruin", "azcantathesunkenruin");
        links.put("spiresoforazca", "spiresoforazca");
        links.put("treasurecove", "treasurecove");
        links.put("itlimoccradleofthesun", "itlimoccradleofthesun");
        links.put("lostvale", "lostvale");
        links.put("adantothefirstfort", "adantothefirstport");
        links.put("spitfirebastion", "spitfirebastion");
        manualLinks.put("XLN", links);

        Map<String, String> linksRix = new HashMap<>();
        linksRix.put("vaultofcatlacan", "vaultofcatlacan");
        linksRix.put("atzalcaveofeternity", "atzalcaveofeternity");
        linksRix.put("wingedtempleoforazca", "wingedtempleoforazca");
        linksRix.put("metzalitoweroftriumph", "metzalitoweroftriumph");
        linksRix.put("tomboftheduskrose", "tomboftheduskrose");
        linksRix.put("sanctumofthesun", "sanctumofthesun");
        linksRix.put("goldforgegarrison", "goldforgegarrison");
        manualLinks.put("RIX", linksRix);

        cardNameAliasesStart = new HashMap<>();
        Set<String> names = new HashSet<>();
        names.add("eldrazidevastator.jpg");
        cardNameAliasesStart.put("BFZ", names);
    }

    private Map<String, String> getSetLinks(String cardSet) {
        Map<String, String> setLinks = new HashMap<>();
        try {
            String setNames = setsAliases.get(cardSet.toLowerCase(Locale.ENGLISH));
            Set<String> aliasesStart = new HashSet<>();
            if (cardNameAliasesStart.containsKey(cardSet)) {
                aliasesStart.addAll(cardNameAliasesStart.get(cardSet));
            }
            if (setNames == null) {
                setNames = cardSet.toLowerCase(Locale.ENGLISH);
            }
            Preferences prefs = MageFrame.getPreferences();
            Connection.ProxyType proxyType = Connection.ProxyType.valueByText(prefs.get("proxyType", "None"));
            for (String setName : setNames.split("\\^")) {
                String URLSetName = CardUtil.urlEncode(setName);
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
        Map<String, String> linksToAdd = manualLinks.get(cardSet);
        if (linksToAdd != null) {
            for (Map.Entry<String, String> link : linksToAdd.entrySet()) {
                pageLinks.put(link.getKey(), baseUrl + "cards/" + link.getValue() + ".jpg");
            }
        }
        return pageLinks;
    }

    @Override
    public boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        return true;
    }

    @Override
    public CardImageUrls generateCardUrl(CardDownloadData card) throws Exception {
        String collectorId = card.getCollectorId();
        String cardSet = card.getSet();
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ", card set: " + cardSet);
        }
        if (card.isFlippedSide()) { //doesn't support rotated images
            return null;
        }
        Map<String, String> setLinks = sets.computeIfAbsent(cardSet, k -> getSetLinks(cardSet));
        String searchName = card.getDownloadName().toLowerCase(Locale.ENGLISH)
                .replaceAll(" ", "")
                .replaceAll("\\.", "")
                .replaceAll("&", "and")
                .replaceAll("-", "")
                .replaceAll("'", "")
                .replaceAll(",", "")
                .replaceAll("/", "");
        String link = setLinks.get(searchName);
        return new CardImageUrls(link);
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card
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
    public boolean isCardSource() {
        return true;
    }

    @Override
    public void doPause(String httpImageUrl) {
    }

    @Override
    public List<String> getSupportedSets() {
        return new ArrayList<>(supportedSets);
    }

    @Override
    public boolean isCardImageProvided(String setCode, String cardName) {
        return supportedSets.contains(setCode);
    }
}
