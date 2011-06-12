package org.mage.plugins.card.dl.sources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author North
 */
public class WizardCardsImageSource implements CardImageSource {

    private static CardImageSource instance;
    private static Map setsAliases;
    private Map sets;

    public static CardImageSource getInstance() {
        if (instance == null) {
            instance = new WizardCardsImageSource();
        }
        return instance;
    }

    public WizardCardsImageSource() {
        sets = new HashMap();
        setsAliases = new HashMap();
        setsAliases.put("NPH", "newphyrexia");
        setsAliases.put("MBS", "mirrodinbesieged");
        setsAliases.put("SOM", "scarsofmirrodin");
        setsAliases.put("M11", "magic2011");
        setsAliases.put("ROE", "riseoftheeldrazi");
        setsAliases.put("WWK", "worldwake");
        setsAliases.put("ZEN", "zendikar");
        setsAliases.put("M10", "magic2010");
        setsAliases.put("ARB", "alarareborn");
        setsAliases.put("CON", "conflux");
        setsAliases.put("ALA", "shardsofalara");
    }

    private List<String> getSetLinks(String cardSet) {
        List<String> setLinks = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect("http://www.wizards.com/magic/tcg/article.aspx?x=mtg/tcg/" + cardSet + "/spoiler").get();
            Elements cardsImages = doc.select("img[height$=370]");
            for (int i = 0; i < cardsImages.size(); i++) {
                setLinks.add(cardsImages.get(i).attr("src"));
            }
        } catch (IOException ex) {
            System.out.println("Exception when parsing the wizards page: " + ex.getMessage());
        }
        return setLinks;
    }

    @Override
    public String generateURL(Integer collectorId, String cardSet) throws Exception {
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        if (setsAliases.get(cardSet) != null) {
            List<String> setLinks = (List<String>) sets.get(cardSet);
            if (setLinks == null) {
                setLinks = getSetLinks((String) setsAliases.get(cardSet));
                sets.put(cardSet, setLinks);
            }
            String link;
            if (setLinks.size() >= collectorId) {
                link = setLinks.get(collectorId - 1);
            } else {
                link = setLinks.get(collectorId - 21);
                link = link.replace(Integer.toString(collectorId - 20), (Integer.toString(collectorId - 20) + "a"));
            }
            if (!link.startsWith("http://")) {
                link = "http://www.wizards.com" + link;
            }
            return link;
        }
        return null;
    }

    @Override
    public String generateTokenUrl(String name, String set) {
        return null;
    }

    @Override
    public Float getAverageSize() {
        return 60.0f;
    }
}