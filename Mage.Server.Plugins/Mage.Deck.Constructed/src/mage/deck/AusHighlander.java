package mage.deck;

import java.util.HashMap;
import java.util.Map;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidatorErrorType;

/**
 * @author spjspj
 */
public class AusHighlander extends Constructed {
    private static final Map<String, Integer> pointMap = new HashMap<>();
    private static final Map<String, Integer> companionPointMap = new HashMap<>();

    static {
        // pointed as companions only
        companionPointMap.put("Lurrus of the Dream-Den", 3);
        companionPointMap.put("Lutri, the Spellchaser", 2);

        // cards in deck
        pointMap.put("Ancestral Recall", 5);
        pointMap.put("Time Walk", 5);

        pointMap.put("Black Lotus", 4);
        pointMap.put("Mana Crypt", 4);
        pointMap.put("Thassa's Oracle", 4);
        pointMap.put("Time Vault", 4);

        pointMap.put("Demonic Tutor", 3);
        pointMap.put("Flash", 3);
        pointMap.put("Mox Emerald", 3);
        pointMap.put("Mox Jet", 3);
        pointMap.put("Mox Pearl", 3);
        pointMap.put("Mox Ruby", 3);
        pointMap.put("Mox Sapphire", 3);
        pointMap.put("Sol Ring", 3);
        pointMap.put("Underworld Breach", 3);
        pointMap.put("Vampiric Tutor", 3);

        pointMap.put("Channel", 2);
        pointMap.put("Dig Through Time", 2);
        pointMap.put("Imperial Seal", 2);
        pointMap.put("Minsc & Boo, Timeless Heroes", 2);
        pointMap.put("Oko, Thief of Crowns", 2);
        pointMap.put("Ragavan, Nimble Pilferer", 2);
        pointMap.put("Strip Mine", 2);
        pointMap.put("Tinker", 2);
        pointMap.put("Treasure Cruise", 2);
        pointMap.put("True-Name Nemesis", 2);
        pointMap.put("Uro, Titan of Nature's Wrath", 2);

        pointMap.put("Ancient Tomb", 1);
        pointMap.put("Balance", 1);
        pointMap.put("Comet, Stellar Pup", 1);
        pointMap.put("Crop Rotation", 1);
        pointMap.put("Deathrite Shaman", 1);
        pointMap.put("Dreadhorde Arcanist", 1);
        pointMap.put("Enlightened Tutor", 1);
        pointMap.put("Fastbond", 1);
        pointMap.put("Force of Will", 1);
        pointMap.put("Gamble", 1);
        pointMap.put("Gifts Ungiven", 1);
        pointMap.put("Gush", 1);
        pointMap.put("Intuition", 1);
        pointMap.put("Karakas", 1);
        pointMap.put("Library of Alexandria", 1);
        pointMap.put("Mana Drain", 1);
        pointMap.put("Mana Vault", 1);
        pointMap.put("Merchant Scroll", 1);
        pointMap.put("Mind Twist", 1);
        pointMap.put("Mishra's Workshop", 1);
        pointMap.put("Murktide Regent", 1);
        pointMap.put("Mystical Tutor", 1);
        pointMap.put("Natural Order", 1);
        pointMap.put("Oath of Druids", 1);
        pointMap.put("Personal Tutor", 1);
        pointMap.put("Profane Tutor", 1);
        pointMap.put("Seasoned Dungeoneer", 1);
        pointMap.put("Sensei's Divining Top", 1);
        pointMap.put("Skullclamp", 1);
        pointMap.put("Snapcaster Mage", 1);
        pointMap.put("Tainted Pact", 1);
        pointMap.put("The One Ring", 1);
        pointMap.put("Timetwister", 1);
        pointMap.put("Tolarian Academy", 1);
        pointMap.put("Umezawa's Jitte", 1);
        pointMap.put("Urza's Saga", 1);
        pointMap.put("Wasteland", 1);
        pointMap.put("White Plume Adventurer", 1);
        pointMap.put("Wishclaw Talisman", 1);
        pointMap.put("Wrenn and Six", 1);
    }

    public AusHighlander() {
        super("Australian Highlander", "AU Highlander");
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

        if (deck.getMaindeckCards().size() != getDeckMinSize()) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain " + getDeckMinSize() + " singleton cards: has " + (deck.getMaindeckCards().size()) + " cards");
            valid = false;
        }
        if (deck.getSideboard().size() > 15) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Sideboard", "Must contain at most 15 singleton cards: has " + (deck.getSideboard().size()) + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        valid = checkCounts(1, counts) && valid;

        int totalPoints = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String cn = entry.getKey();
            if (pointMap.containsKey(cn)) {
                totalPoints += pointMap.get(cn);
                addError(DeckValidatorErrorType.OTHER, entry.getKey(), " " + pointMap.get(cn) + " point " + cn, true);
            }
        }
        for (Card card : deck.getSideboard()) {
            if (companionPointMap.containsKey(card.getName())) {
                totalPoints += companionPointMap.get(card.getName());
                addError(DeckValidatorErrorType.OTHER, card.getName(), " " + companionPointMap.get(card.getName()) + " point " + card.getName(), true);
            }
        }
        if (totalPoints > 7) {
            addError(DeckValidatorErrorType.PRIMARY, "Total points too high", "Your calculated point total was " + totalPoints);
            addError(DeckValidatorErrorType.PRIMARY, "Only you can see this!", "Your opponents will not be able to see this message or what cards are in your deck!");
            valid = false;
        }
        return valid;
    }
}
