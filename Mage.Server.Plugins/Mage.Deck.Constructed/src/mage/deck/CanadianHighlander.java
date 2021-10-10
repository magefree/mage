package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidatorErrorType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spjspj
 */
public class CanadianHighlander extends Constructed {

    private static final Map<String, Integer> pointMap = new HashMap<>();

    static {
        pointMap.put("Ancestral Recall", 7);
        pointMap.put("Balance", 1);
        pointMap.put("Birthing Pod", 2);
        pointMap.put("Black Lotus", 7);
        pointMap.put("Crop Rotation", 1);
        pointMap.put("Demonic Tutor", 4);
        pointMap.put("Dig Through Time", 1);
        pointMap.put("Flash", 5);
        pointMap.put("Gifts Ungiven", 2);
        pointMap.put("Imperial Seal", 1);
        pointMap.put("Intuition", 1);
        pointMap.put("Library of Alexandria", 1);
        pointMap.put("Mana Crypt", 4);
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
        pointMap.put("Natural Order", 3);
        pointMap.put("Price of Progress", 1);
        pointMap.put("Protean Hulk", 2);
        pointMap.put("Sol Ring", 4);
        pointMap.put("Spellseeker", 2);
        pointMap.put("Strip Mine", 3);
        pointMap.put("Summoner's Pact", 1);
        pointMap.put("Survival of the Fittest", 2);
        pointMap.put("Tainted Pact", 1);
        pointMap.put("Thassa's Oracle", 2);
        pointMap.put("Time Vault", 7);
        pointMap.put("Time Walk", 7);
        pointMap.put("Tinker", 3);
        pointMap.put("Tolarian Academy", 1);
        pointMap.put("Transmute Artifact", 1);
        pointMap.put("Treasure Cruise", 1);
        pointMap.put("True-Name Nemesis", 1);
        pointMap.put("Umezawa's Jitte", 1);
        pointMap.put("Underworld Breach", 2);
        pointMap.put("Vampiric Tutor", 2);
        pointMap.put("Wishclaw Talisman", 1);
        pointMap.put("Yawgmoth's Will", 2);
    }

    public CanadianHighlander() {
        super("Canadian Highlander");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        errorsList.clear();

        if (deck.getCards().size() < 100) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain 100 or more singleton cards: has " + (deck.getCards().size()) + " cards");
            valid = false;
        }

        if (!deck.getSideboard().isEmpty()) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Sideboard can't contain any cards: has " + (deck.getSideboard().size()) + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        valid = checkCounts(1, counts) && valid;

        int allowedPoints = 10;
        int totalPoints = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String cn = entry.getKey();
            if (pointMap.containsKey(cn)) {
                totalPoints += pointMap.get(cn);
                addError(DeckValidatorErrorType.OTHER, entry.getKey(), " " + pointMap.get(cn) + " point " + cn, true);
            }
        }
        if (totalPoints > allowedPoints) {
            addError(DeckValidatorErrorType.PRIMARY, "Total points too high", "Your calculated point total was " + totalPoints);
            valid = false;
        }
        return valid;
    }
}
