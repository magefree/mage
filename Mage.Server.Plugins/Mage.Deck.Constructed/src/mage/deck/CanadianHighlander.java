package mage.deck;

import java.util.HashMap;
import java.util.Map;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.constants.SetType;

/**
 *
 * @author spjspj
 */
public class CanadianHighlander extends Constructed {

    public static final Map<String, Integer> pointMap = new HashMap();

    static {
        pointMap.put("Ancestral Recall", 7);
        pointMap.put("Balance", 1);
        pointMap.put("Birthing Pod", 3);
        pointMap.put("Black Lotus", 7);
        pointMap.put("Demonic Tutor", 3);
        pointMap.put("Dig Through Time", 1);
        pointMap.put("Enlightened Tutor", 1);
        pointMap.put("Fastbond", 1);
        pointMap.put("Flash", 7);
        pointMap.put("Gifts Ungiven", 2);
        pointMap.put("Hermit Druid", 1);
        pointMap.put("Imperial Seal", 1);
        pointMap.put("Intuition", 1);
        pointMap.put("Library of Alexandria", 1);
        pointMap.put("Mana Crypt", 2);
        pointMap.put("Mana Drain", 1);
        pointMap.put("Mana Vault", 1);
        pointMap.put("Merchant Scroll", 1);
        pointMap.put("Mind Twist", 1);
        pointMap.put("Mox Emerald", 3);
        pointMap.put("Mox Jet", 3);
        pointMap.put("Mox Pearl", 3);
        pointMap.put("Mox Ruby", 3);
        pointMap.put("Mox Sapphire", 3);
        pointMap.put("Mystical Tutor", 2);
        pointMap.put("Natural Order", 4);
        pointMap.put("Personal Tutor", 1);
        pointMap.put("Protean Hulk", 3);
        pointMap.put("Sol Ring", 3);
        pointMap.put("Stoneforge Mystic", 1);
        pointMap.put("Strip Mine", 2);
        pointMap.put("Summoner's Pact", 2);
        pointMap.put("Survival of the Fittest", 2);
        pointMap.put("Time Vault", 6);
        pointMap.put("Time Walk", 6);
        pointMap.put("Tinker", 4);
        pointMap.put("Tolarian Academy", 1);
        pointMap.put("Transmute Artifact", 1);
        pointMap.put("Treasure Cruise", 1);
        pointMap.put("True-Name Nemesis", 1);
        pointMap.put("Umezawa's Jitte", 2);
        pointMap.put("Vampiric Tutor", 3);
    }

    public CanadianHighlander() {
        this("Canadian Highlander");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType() != SetType.CUSTOM_SET) {
                setCodes.add(set.getCode());
            }
        }
    }

    public CanadianHighlander(String name) {
        super(name);
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;

        if (deck.getCards().size() < 100) {
            invalid.put("Deck", "Must contain 100 or more singleton cards: has " + (deck.getCards().size()) + " cards");
            valid = false;
        }

        if (!deck.getSideboard().isEmpty()) {
            invalid.put("Deck", "Sideboard can't contain any cards: has " + (deck.getSideboard().size()) + " cards");
            valid = false;
        }

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

        int allowedPoints = 10;
        int totalPoints = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String cn = entry.getKey();
            if (pointMap.containsKey(cn)) {
                totalPoints += pointMap.get(cn);
                invalid.put(entry.getKey(), " " + pointMap.get(cn) + " point " + cn);
            }
        }
        if (totalPoints > allowedPoints) {
            invalid.put("Total points too high", "Your calculated point total was " + totalPoints);
            valid = false;
        }
        return valid;
    }
}
