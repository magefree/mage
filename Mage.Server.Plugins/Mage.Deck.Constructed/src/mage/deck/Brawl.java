package mage.deck;

import mage.abilities.Ability;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.keyword.CompanionAbility;
import mage.cards.Card;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidatorErrorType;
import mage.constants.CardType;
import mage.filter.FilterMana;
import mage.util.ManaUtil;

import java.util.*;

/**
 * @author spjspj
 */
public class Brawl extends Constructed {

    protected List<String> bannedCommander = new ArrayList<>();

    public Brawl() {
        super("Brawl");

        // Copy of standard sets
        setCodes.addAll(Standard.makeLegalSets());

        banned.add("Golos, Tireless Pilgrim");
        banned.add("Drannith Magistrate");
        banned.add("Lutri, the Spellchaser");
        banned.add("Oko, Thief of Crowns");
        banned.add("Sorcerous Spyglass");
        banned.add("Teferi, Time Raveler");
        banned.add("Omnath, Locus of Creation");
        banned.add("Winota, Joiner of Forces");
    }

    @Override
    public int getSideboardMinSize() {
        return 1;
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        errorsList.clear();
        Card brawler = null;
        Card companion = null;
        FilterMana colorIdentity = new FilterMana();

        if (deck.getSideboard().size() == 1) {
            for (Card card : deck.getSideboard()) {
                brawler = card;
            }
        } else if (deck.getSideboard().size() == 2) {
            Iterator<Card> iter = deck.getSideboard().iterator();
            Card card1 = iter.next();
            Card card2 = iter.next();
            if (card1.getAbilities().stream().anyMatch(ability -> ability instanceof CompanionAbility)) {
                companion = card1;
                brawler = card2;
            } else if (card2.getAbilities().stream().anyMatch(ability -> ability instanceof CompanionAbility)) {
                companion = card2;
                brawler = card1;
            } else {
                addError(DeckValidatorErrorType.PRIMARY, "Brawl", "Sideboard must contain only the brawler and up to 1 companion");
                valid = false;
            }
        } else {
            addError(DeckValidatorErrorType.PRIMARY, "Brawl", "Sideboard must contain only the brawler and up to 1 companion");
            valid = false;
        }

        if (brawler != null) {
            ManaUtil.collectColorIdentity(colorIdentity, brawler.getColorIdentity());
            if (bannedCommander.contains(brawler.getName())) {
                addError(DeckValidatorErrorType.PRIMARY, brawler.getName(), "Brawler banned (" + brawler.getName() + ')', true);
                valid = false;
            }
            if (!((brawler.hasCardTypeForDeckbuilding(CardType.CREATURE) && brawler.isLegendary())
                    || brawler.hasCardTypeForDeckbuilding(CardType.PLANESWALKER)
                    || brawler.getAbilities().contains(CanBeYourCommanderAbility.getInstance()))) {
                addError(DeckValidatorErrorType.PRIMARY, brawler.getName(), "Brawler Invalid (" + brawler.getName() + ')', true);
                valid = false;
            }
        }

        if (companion != null && deck.getCards().size() + deck.getSideboard().size() != getDeckMinSize() + 1) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain " + (getDeckMinSize() + 1) + " cards (companion doesn't count in deck size requirement): has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        } else if (companion == null && deck.getCards().size() + deck.getSideboard().size() != getDeckMinSize()) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain " + getDeckMinSize() + " cards: has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        valid = checkCounts(1, counts) && valid;

        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                addError(DeckValidatorErrorType.BANNED, bannedCard, "Banned", true);
                valid = false;
            }
        }

        Set<String> basicsInDeck = new HashSet<>();
        if (colorIdentity.isColorless()) {
            for (Card card : deck.getCards()) {
                if (basicLandNames.contains(card.getName())) {
                    basicsInDeck.add(card.getName());
                }
            }
        }
        for (Card card : deck.getCards()) {
            if (!ManaUtil.isColorIdentityCompatible(colorIdentity, card.getColorIdentity())
                    && !(colorIdentity.isColorless()
                    && basicsInDeck.size() == 1
                    && basicsInDeck.contains(card.getName()))) {
                addError(DeckValidatorErrorType.OTHER, card.getName(), "Invalid color (" + colorIdentity.toString() + ')', true);
                valid = false;
            }
        }
        for (Card card : deck.getSideboard()) {
            if (!ManaUtil.isColorIdentityCompatible(colorIdentity, card.getColorIdentity())
                    && !(colorIdentity.isColorless()
                    && basicsInDeck.size() == 1
                    && basicsInDeck.contains(card.getName()))) {
                addError(DeckValidatorErrorType.OTHER, card.getName(), "Invalid color (" + colorIdentity.toString() + ')', true);
                valid = false;
            }
        }
        for (Card card : deck.getCards()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    addError(DeckValidatorErrorType.WRONG_SET, card.getName(), "Not allowed Set: " + card.getExpansionSetCode(), true);
                    valid = false;
                }
            }
        }
        for (Card card : deck.getSideboard()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    addError(DeckValidatorErrorType.WRONG_SET, card.getName(), "Not allowed Set: " + card.getExpansionSetCode(), true);
                    valid = false;
                }
            }
        }
        // Check for companion legality
        if (companion != null) {
            Set<Card> cards = new HashSet<>(deck.getCards());
            cards.add(brawler);
            for (Ability ability : companion.getAbilities()) {
                if (ability instanceof CompanionAbility) {
                    CompanionAbility companionAbility = (CompanionAbility) ability;
                    if (!companionAbility.isLegal(cards, getDeckMinSize())) {
                        addError(DeckValidatorErrorType.PRIMARY, companion.getName(),
                                String.format("Brawl companion illegal: %s", companionAbility.getLegalRule()), true);
                        valid = false;
                    }
                    break;
                }
            }
        }

        return valid;
    }
}
