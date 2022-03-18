package mage.cards.decks;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Constructed extends DeckValidator {

    private static final Logger logger = Logger.getLogger(Constructed.class);

    private static final List<String> anyNumberCardsAllowed = new ArrayList<>(Arrays.asList(
            "Relentless Rats", "Shadowborn Apostle", "Rat Colony",
            "Persistent Petitioners", "Seven Dwarves", "Dragon's Approach"
    ));
    protected static final List<String> basicLandNames = new ArrayList<>(Arrays.asList(
            "Forest", "Island", "Mountain", "Swamp", "Plains", "Wastes", "Snow-Covered Forest",
            "Snow-Covered Island", "Snow-Covered Mountain", "Snow-Covered Swamp", "Snow-Covered Plains"
    ));
    protected List<String> banned = new ArrayList<>();
    protected List<String> restricted = new ArrayList<>();
    protected List<String> setCodes = new ArrayList<>();
    protected List<Rarity> rarities = new ArrayList<>();
    protected Set<String> singleCards = new HashSet<>();

    protected Constructed(String name) {
        this(name, null);
    }

    protected Constructed(String name, String shortName) {
        super(name, shortName);
    }

    public List<String> getSetCodes() {
        return setCodes;
    }

    @Override
    public int getDeckMinSize() {
        return 60;
    }

    @Override
    public int getSideboardMinSize() {
        return 0;
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        errorsList.clear();
        //20091005 - 100.2a
        if (deck.getCards().size() < getDeckMinSize()) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain at least " + getDeckMinSize() + " cards: has only " + deck.getCards().size() + " cards");
            valid = false;
        }
        //20130713 - 100.4a
        if (deck.getSideboard().size() > 15) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Sideboard", "Must contain no more than 15 cards : has " + deck.getSideboard().size() + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        valid = checkCounts(4, counts) && valid;

        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                addError(DeckValidatorErrorType.BANNED, bannedCard, "Banned", true);
                valid = false;
            }
        }

        for (String restrictedCard : restricted) {
            if (counts.containsKey(restrictedCard)) {
                int count = counts.get(restrictedCard);
                if (count > 1) {
                    addError(DeckValidatorErrorType.OTHER, restrictedCard, "Restricted amount: " + count, true);
                    valid = false;
                }
            }
        }

        if (!rarities.isEmpty()) {
            for (Card card : deck.getCards()) {
                if (!rarities.contains(card.getRarity())) {
                    if (!legalRarity(card)) {
                        valid = false;
                    }
                }
            }
            for (Card card : deck.getSideboard()) {
                if (!rarities.contains(card.getRarity())) {
                    if (!legalRarity(card)) {
                        valid = false;
                    }
                }
            }
        }

        for (Card card : deck.getCards()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    valid = false;
                }
            }
        }
        for (Card card : deck.getSideboard()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Checks if the given card is legal in any of the given rarities
     *
     * @param card - the card to check
     * @return Whether the card was printed at any of the given rarities.
     */
    protected boolean legalRarity(Card card) {
        // check if card is legal if taken from other set
        boolean legal = false;
        List<CardInfo> cardInfos = CardRepository.instance.findCards(card.getName());
        for (CardInfo cardInfo : cardInfos) {
            if (rarities.contains(cardInfo.getRarity())) {
                legal = true;
                break;
            }
        }
        if (!legal && !errorsListContainsGroup(card.getName())) {
            addError(DeckValidatorErrorType.OTHER, card.getName(), "Invalid rarity: " + card.getRarity(), true);
        }
        return legal;
    }

    /**
     * Checks if a given set is legal in this format.
     *
     * @param code - the set code to check
     * @return Whether the set is legal in this format.
     */
    protected boolean isSetAllowed(String code) {
        return setCodes.isEmpty() || setCodes.contains(code);
    }

    /**
     * Checks if the given card is legal in any of the given sets or as single card
     *
     * @param card - the card to check
     * @return Whether the card was printed in any of this format's sets.
     */
    protected boolean legalSets(Card card) {
        // check if card is legal if taken from other set
        boolean legal = false;
        List<CardInfo> cardInfos = CardRepository.instance.findCards(card.getName());
        for (CardInfo cardInfo : cardInfos) {
            if (isSetAllowed(cardInfo.getSetCode())) {
                legal = true;
                break;
            }
        }

        // check if single card allows
        if (singleCards.contains(card.getName())) {
            legal = true;
        }

        if (!legal && !errorsListContainsGroup(card.getName())) {
            addError(DeckValidatorErrorType.WRONG_SET, card.getName(), "Invalid set: " + card.getExpansionSetCode(), true);
        }
        return legal;
    }

    protected boolean checkCounts(int maxCopies, Map<String, Integer> counts) {
        boolean valid = true;
        for (Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCopies
                    && !basicLandNames.contains(entry.getKey())
                    && !anyNumberCardsAllowed.contains(entry.getKey())) {
                addError(DeckValidatorErrorType.OTHER, entry.getKey(), "Too many: " + entry.getValue(), true);
                valid = false;
            }
            if (entry.getValue() > 7 && entry.getKey().equals("Seven Dwarves")) {
                addError(DeckValidatorErrorType.OTHER, entry.getKey(), "Too many: " + entry.getValue(), true);
                valid = false;
            }
        }
        return valid;
    }
}