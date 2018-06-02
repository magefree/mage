
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

        int allowedPoints = 10 * (int) Math.floor(deck.getCards().size() / 100.0);
        int totalPoints = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String cn = entry.getKey();
            if (cn.equals("Balance")
                    || cn.equals("Dig Through Time")
                    || cn.equals("Doomsday")
                    || cn.equals("Enlightened Tutor")
                    || cn.equals("Fastbond")
                    || cn.equals("Intuition")
                    || cn.equals("Library of Alexandria")
                    || cn.equals("Mana Vault")
                    || cn.equals("Merchant Scroll")
                    || cn.equals("Mind Twist")
                    || cn.equals("Personal Tutor")
                    || cn.equals("Stoneforge Mystic")
                    || cn.equals("Tainted Pact")
                    || cn.equals("Tolarian Academy")
                    || cn.equals("Transmute Artifact")
                    || cn.equals("Treasure Cruise")
                    || cn.equals("True-Name Nemesis")
                    || cn.equals("Worldly Tutor")) {
                totalPoints += 1;
                invalid.put(entry.getKey(), " 1 point " + cn);
            }
            if (cn.equals("Gifts Ungiven")
                    || cn.equals("Hermit Druid")
                    || cn.equals("Imperial Seal")
                    || cn.equals("Mana Crypt")
                    || cn.equals("Mystical Tutor")
                    || cn.equals("Strip Mine")
                    || cn.equals("Summoner's Pact")
                    || cn.equals("Survival of the Fittest")
                    || cn.equals("Umezawa's Jitte")) {
                totalPoints += 2;
                invalid.put(entry.getKey(), " 2 points " + cn);
            }
            if (cn.equals("Birthing Pod")
                    || cn.equals("Mox Emerald")
                    || cn.equals("Mox Jet")
                    || cn.equals("Mox Pearl")
                    || cn.equals("Mox Ruby")
                    || cn.equals("Mox Sapphire")
                    || cn.equals("Protean Hulk")
                    || cn.equals("Sol Ring")
                    || cn.equals("Vampiric Tutor")) {
                totalPoints += 3;
                invalid.put(entry.getKey(), " 3 points " + cn);
            }
            if (cn.equals("Demonic Tutor")
                    || cn.equals("Tinker")) {
                totalPoints += 4;
                invalid.put(entry.getKey(), " 4 points " + cn);
            }
            if (cn.equals("Natural Order")
                    || cn.equals("Time Walk")) {
                totalPoints += 5;
                invalid.put(entry.getKey(), " 5 points " + cn);
            }
            if (cn.equals("Ancestral Recall")
                    || cn.equals("Time Vault")) {
                totalPoints += 6;
                invalid.put(entry.getKey(), " 6 points " + cn);
            }
            if (cn.equals("Black Lotus")
                    || cn.equals("Flash")) {
                totalPoints += 7;
                invalid.put(entry.getKey(), " 7 points " + cn);
            }
        }
        if (totalPoints > allowedPoints) {
            invalid.put("Total points too high", "Your calculated point total was " + totalPoints);
            valid = false;
        }
        return valid;
    }
}
