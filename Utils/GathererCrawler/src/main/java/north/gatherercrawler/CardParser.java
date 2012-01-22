package north.gatherercrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import north.gatherercrawler.util.CardsList;
import north.gatherercrawler.util.ParseQueue;
import north.gatherercrawler.util.ParsedList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author North
 */
public class CardParser extends Thread {

    private static final Pattern patternPrint = Pattern.compile("(?<=#)[\\w\\d]+?(?= )");
    private static final Pattern patternUrl = Pattern.compile("(?<=/)[\\w\\d]+?(?=\\.html)");

    private boolean parseCard(Integer multiverseId) {
        String url = "http://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=" + multiverseId;
        Card card;
        Document doc = null;
        boolean done = false;
        while (!done) {
            try {
                Connection connection = Jsoup.connect(url);
                connection.timeout(20000);
                doc = connection.get();
            } catch (IOException ex) {
            }
            done = true;
        }
        if (!done) {
            System.out.println("Card get exception: " + multiverseId);
            return false;
        }

        try {
            Elements select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_nameRow .value");
            if (!select.isEmpty()) {
                card = extractCardData(doc, "", multiverseId);
                select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_rightCol ul li a");

                // for multi-part cards
                if (!select.isEmpty()) {
                    String href = select.attr("href");
                    url = "http://gatherer.wizards.com/Pages/Card/Details.aspx" + href.substring(href.indexOf("?"));

                    done = false;
                    while (!done) {
                        try {
                            Connection connection = Jsoup.connect(url);
                            connection.timeout(20000);
                            doc = connection.get();
                        } catch (IOException ex) {
                        }
                        done = true;
                    }
                    if (!done) {
                        System.out.println("Card get exception: " + multiverseId);
                    } else {
                        card.setCardNumber(card.getCardNumber() + "b");
                        Card cardSide = extractCardData(doc, "", multiverseId);
                        cardSide.setCardNumber(cardSide.getCardNumber() + "a");
                        cardSide.setOtherSide(card);
                        card = cardSide;
                    }
                }
            } else {
                // for flip / double sided cards
                card = extractCardData(doc, "_ctl05", multiverseId);
                if (card == null) {
                    return false;
                }
                card.setOtherSide(extractCardData(doc, "_ctl06", multiverseId));
                if (card.getOtherSide() == null) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }


        if (card == null) {
            return false;
        }
        CardsList.add(card);
        return true;
    }

    private Card extractCardData(Document doc, String selectorModifier, Integer id) throws NumberFormatException {
        Elements select;

        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_cardImage");
        Integer multiverseId = null;
        if (!select.isEmpty()) {
            Pattern pattern = Pattern.compile("(?<=multiverseid=)\\d+");
            Matcher matcher = pattern.matcher(select.get(0).attr("src"));
            if (matcher.find()) {
                multiverseId = Integer.parseInt(matcher.group());
            }
        }
        if (multiverseId == null) {
            return null;
        }

        Card card = new Card(multiverseId);

        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_nameRow .value");
        if (!select.isEmpty()) {
            card.setName(select.get(0).text().trim());
        }
        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_manaRow .value img");
        List<String> manaCost = new ArrayList<String>();
        if (!select.isEmpty()) {
            for (Element element : select) {
                manaCost.add(element.attr("src").replace("/Handlers/Image.ashx?size=medium&name=", "").replace("&type=symbol", "").replaceAll("\" alt=\"[\\d\\w\\s]+?\" align=\"absbottom\" />", ""));
            }
        }
        card.setManaCost(manaCost);
        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_cmcRow .value");
        if (!select.isEmpty()) {
            card.setConvertedManaCost(Integer.parseInt(select.get(0).text().trim()));
        }
        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_typeRow .value");
        if (!select.isEmpty()) {
            card.setTypes(select.get(0).text().trim());
        }
        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_textRow .value .cardtextbox");
        List<String> cardText = new ArrayList<String>();
        if (!select.isEmpty()) {
            for (Element element : select) {
                cardText.add(element.html().trim().replace("<img src=\"/Handlers/Image.ashx?size=small&amp;name=", "{").replace("&amp;type=symbol", "}").replaceAll("\" alt=\"[\\d\\w\\s]+?\" align=\"absbottom\" />", "").replace("\n", "").replace("&quot;", "\""));
            }
        }
        card.setCardText(cardText);
        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_FlavorText .cardtextbox");
        List<String> flavorText = new ArrayList<String>();
        if (!select.isEmpty()) {
            for (Element element : select) {
                flavorText.add(element.html().trim().replace("&quot;", "\"").replace("<i>", "").replace("</i>", ""));
            }
        }
        card.setFlavorText(flavorText);
        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_ptRow .value");
        if (!select.isEmpty()) {
            card.setPowerToughness(select.get(0).text().trim());
        }
        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_currentSetSymbol a");
        if (!select.isEmpty()) {
            card.setExpansion(select.get(1).text().trim());
        }
        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_rarityRow .value span");
        if (!select.isEmpty()) {
            card.setRarity(select.get(0).text().trim());
        }
        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_otherSetsValue a");
        List<Integer> otherSets = new ArrayList<Integer>();
        if (!select.isEmpty()) {
            for (Element element : select) {
                otherSets.add(Integer.parseInt(element.attr("href").replace("Details.aspx?multiverseid=", "")));
            }
        }
        // card.setOtherSets(otherSets);
        for (Integer otherSet : otherSets) {
            if (!ParsedList.contains(otherSet)) {
                ParseQueue.add(otherSet);
            }
        }
        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_numberRow .value");
        if (!select.isEmpty()) {
            card.setCardNumber(select.get(0).text().trim());
        }
        select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent" + selectorModifier + "_ArtistCredit a");
        if (!select.isEmpty()) {
            card.setArtist(select.get(0).text().trim());
        }

        if (card.getCardNumber() == null) {
            String url = "http://magiccards.info/query?q=" + card.getName().replace(' ', '+');
            try {
                Connection connection = Jsoup.connect(url);
                connection.timeout(20000);
                doc = connection.get();

                select = doc.select("small a:contains(" + card.getExpansion() + ")");
                if (!select.isEmpty()) {
                    Matcher matcher = patternUrl.matcher(select.get(0).attr("href"));
                    matcher.find();
                    card.setCardNumber(matcher.group());
                } else {
                    select = doc.select("small b:contains(#)");
                    if (!select.isEmpty()) {
                        Matcher matcher = patternPrint.matcher(select.get(0).html());
                        matcher.find();
                        card.setCardNumber(matcher.group());
                    }
                }

            } catch (IOException ex) {
            }
        }

        if (card.getCardNumber() == null) {
            select = doc.select("p a:contains(" + card.getExpansion() + ")");
            if (!select.isEmpty()) {
                Matcher matcher = patternUrl.matcher(select.get(0).attr("href"));
                matcher.find();
                card.setCardNumber(matcher.group());
            } else {
                select = doc.select("p b:contains(#)");
                if (!select.isEmpty()) {
                    Matcher matcher = patternPrint.matcher(select.get(0).html());
                    matcher.find();
                    card.setCardNumber(matcher.group());
                }
            }
            if (card.getCardNumber() == null) {
                System.out.println("Card number missing: " + card.getName());
            }
        }

        return card;
    }

    @Override
    public void run() {
        while (!ParseQueue.isEmpty()) {
            Integer multiverseId = ParseQueue.remove();
            if (!ParsedList.contains(multiverseId)) {
                ParsedList.add(multiverseId);
                parseCard(multiverseId);
            }
        }

        ThreadStarter.threadDone();
    }
}
