package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spjspj
 */
public class AusHighlander extends Constructed {

    public static final Map<String, Integer> pointMap = new HashMap();

    static {
        pointMap.put("Ancestral Recall", 4);
        pointMap.put("Black Lotus", 4);
        pointMap.put("Time Vault", 4);
        pointMap.put("Demonic Tutor", 3);
        pointMap.put("Imperial Seal", 3);
        pointMap.put("Mox Emerald", 3);
        pointMap.put("Mox Jet", 3);
        pointMap.put("Mox Pearl", 3);
        pointMap.put("Mox Ruby", 3);
        pointMap.put("Mox Sapphire", 3);
        pointMap.put("Sol Ring", 3);
        pointMap.put("Time Walk", 3);
        pointMap.put("Tinker", 3);
        pointMap.put("Vampiric Tutor", 3);
        pointMap.put("Yawgmoth’s Will", 3);
        pointMap.put("Channel", 2);
        pointMap.put("Dig Through Time", 2);
        pointMap.put("Library of Alexandria", 2);
        pointMap.put("Mana Crypt", 2);
        pointMap.put("Mind Twist", 2);
        pointMap.put("Mystical Tutor", 2);
        pointMap.put("Protean Hulk", 2);
        pointMap.put("Strip Mine", 2);
        pointMap.put("Tolarian Academy", 2);
        pointMap.put("Treasure Cruise", 2);
        pointMap.put("Back to Basics", 1);
        pointMap.put("Balance", 1);
        pointMap.put("Birthing Pod", 1);
        pointMap.put("Crop Rotation", 1);
        pointMap.put("Dark Petition", 1);
        pointMap.put("Enlightened Tutor", 1);
        pointMap.put("Fastbond", 1);
        pointMap.put("Force of Will", 1);
        pointMap.put("Gifts Ungiven", 1);
        pointMap.put("Green Sun’s Zenith", 1);
        pointMap.put("Hermit Druid", 1);
        pointMap.put("Intuition", 1);
        pointMap.put("Jace, the Mind Sculptor", 1);
        pointMap.put("Karakas", 1);
        pointMap.put("Lim-Dul’s Vault", 1);
        pointMap.put("Mana Drain", 1);
        pointMap.put("Mana Vault", 1);
        pointMap.put("Memory Jar", 1);
        pointMap.put("Merchant Scroll", 1);
        pointMap.put("Mishra’s Workshop", 1);
        pointMap.put("Natural Order", 1);
        pointMap.put("Oath of Druids", 1);
        pointMap.put("Personal Tutor", 1);
        pointMap.put("Sensei’s Divining Top", 1);
        pointMap.put("Skullclamp", 1);
        pointMap.put("Snapcaster Mage", 1);
        pointMap.put("Stoneforge Mystic", 1);
        pointMap.put("Survival of the Fittest", 1);
        pointMap.put("Tainted Pact", 1);
        pointMap.put("Time Spiral", 1);
        pointMap.put("Timetwister", 1);
        pointMap.put("True-Name Nemesis", 1);
        pointMap.put("Umezawa’s Jitte", 1);
        pointMap.put("Wasteland", 1);
        pointMap.put("Yawgmoth’s Bargain", 1);
    }

    public AusHighlander() {
        this("Australian Highlander");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }
    }

    public AusHighlander(String name) {
        super(name);
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;

        if (deck.getCards().size() != 60) {
            invalid.put("Deck", "Must contain 60 singleton cards: has " + (deck.getCards().size()) + " cards");
            valid = false;
        }
        if (deck.getSideboard().size() > 15) {
            invalid.put("Sideboard", "Must contain at most 15 singleton cards: has " + (deck.getSideboard().size()) + " cards");
            valid = false;
        }

        banned.add("Advantageous Proclamation");
        banned.add("Amulet of Quoz");
        banned.add("Backup Plan");
        banned.add("Brago's Favor");
        banned.add("Bronze Tablet");
        banned.add("Chaos Orb");
        banned.add("Contract from Below");
        banned.add("Darkpact");
        banned.add("Demonic Attorney");
        banned.add("Double Stroke");
        banned.add("Falling Star");
        banned.add("Immediate Action");
        banned.add("Iterative Analysis");
        banned.add("Jeweled Bird");
        banned.add("Muzzio's Preparations");
        banned.add("Power Play");
        banned.add("Rebirth");
        banned.add("Secret Summoning");
        banned.add("Secrets of Paradise");
        banned.add("Sentinel Dispatch");
        banned.add("Shahrazad");
        banned.add("Tempest Efreet");
        banned.add("Timmerian Fiends");
        banned.add("Unexpected Potential");
        banned.add("Worldknit");

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                if (!basicLandNames.contains(entry.getKey()) && !anyNumberCardsAllowed.contains(entry.getKey())) {
                    invalid.put(entry.getKey(), "Too many: " + entry.getValue());
                    valid = false;
                }
            }
        }

        int totalPoints = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String cn = entry.getKey();
            if (pointMap.containsKey(cn)) {
                totalPoints += pointMap.get(cn);
                invalid.put(entry.getKey(), " " + pointMap.get(cn) + " point " + cn);
            }
        }
        if (totalPoints > 7) {
            invalid.put("Total points too high", "Your calculated point total was " + totalPoints);
            invalid.put("Only you can see this!", "Your opponents will not be able to see this message or what cards are in your deck!");
            valid = false;
        }
        return valid;
    }
}
