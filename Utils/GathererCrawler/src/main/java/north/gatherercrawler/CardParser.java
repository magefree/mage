package north.gatherercrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author robert.biter
 */
public class CardParser extends Thread {

    private boolean parseCard(Integer multiverseId) {
        String url = "http://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=" + multiverseId;
        Card card = new Card(multiverseId);
        Document doc = null;
        int retries = 30;
        boolean done = false;
        while (retries > 0 && !done) {
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
                card.setName(select.get(0).text().trim());
            }

            select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_manaRow .value img");
            List<String> manaCost = new ArrayList<String>();
            if (!select.isEmpty()) {
                for (Element element : select) {
                    manaCost.add(element.attr("src").replace("/Handlers/Image.ashx?size=medium&name=", "").replace("&type=symbol", "").replaceAll("\" alt=\"[\\d\\w\\s]+?\" align=\"absbottom\" />", ""));
                }
            }
            card.setManaCost(manaCost);

            select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_cmcRow .value");
            if (!select.isEmpty()) {
                card.setConvertedManaCost(Integer.parseInt(select.get(0).text().trim()));
            }

            select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_typeRow .value");
            if (!select.isEmpty()) {
                card.setTypes(select.get(0).text().trim());
            }

            select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_textRow .value .cardtextbox");
            List<String> cardText = new ArrayList<String>();
            if (!select.isEmpty()) {
                for (Element element : select) {
                    cardText.add(element.html().trim().replace("<img src=\"/Handlers/Image.ashx?size=small&amp;name=", "{").replace("&amp;type=symbol", "}").replaceAll("\" alt=\"[\\d\\w\\s]+?\" align=\"absbottom\" />", "").replace("\n", ""));
                }
            }
            card.setCardText(cardText);

            select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_FlavorText .cardtextbox i");
            if (!select.isEmpty()) {
                card.setFlavorText(select.get(0).text().trim());
            }

            select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_ptRow .value");
            if (!select.isEmpty()) {
                card.setPowerToughness(select.get(0).text().trim());
            }

            select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_currentSetSymbol a");
            if (!select.isEmpty()) {
                card.setExpansion(select.get(1).text().trim());
            }

            select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_rarityRow .value span");
            if (!select.isEmpty()) {
                card.setRarity(select.get(0).text().trim());
            }

            select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_otherSetsValue a");
            List<Integer> otherSets = new ArrayList<Integer>();
            if (!select.isEmpty()) {
                for (Element element : select) {
                    otherSets.add(Integer.parseInt(element.attr("href").replace("Details.aspx?multiverseid=", "")));
                }
            }
//            card.setOtherSets(otherSets);
            for (Integer otherSet : otherSets) {
                if (!ParsedList.contains(otherSet)) {
                    ParseQueue.add(otherSet);
                }
            }

            select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_numberRow .value");
            if (!select.isEmpty()) {
                card.setCardNumber(select.get(0).text().trim());
            }

            select = doc.select("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_ArtistCredit a");
            if (!select.isEmpty()) {
                card.setArtist(select.get(0).text().trim());
            }
        } catch (Exception e) {
            return false;
        }

        CardsList.add(card);
        return true;
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
