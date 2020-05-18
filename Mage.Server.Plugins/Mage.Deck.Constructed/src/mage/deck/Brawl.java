package mage.deck;

import mage.abilities.Ability;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.keyword.CompanionAbility;
import mage.cards.Card;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
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
        banned.add("Winota, Joiner of Forces");
    }

    public Brawl(String name) {
        super(name);
    }

    @Override
    public int getSideboardMinSize() {
        return 1;
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
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
                invalid.put("Brawl", "Sideboard must contain only the brawler and up to 1 companion");
                valid = false;
            }
        } else {
            invalid.put("Brawl", "Sideboard must contain only the brawler and up to 1 companion");
            valid = false;
        }

        if (brawler != null) {
            ManaUtil.collectColorIdentity(colorIdentity, brawler.getColorIdentity());
            if (bannedCommander.contains(brawler.getName())) {
                invalid.put("Brawl", "Brawler banned (" + brawler.getName() + ')');
                valid = false;
            }
            if (!((brawler.isCreature() && brawler.isLegendary())
                    || brawler.isPlaneswalker() || brawler.getAbilities().contains(CanBeYourCommanderAbility.getInstance()))) {
                invalid.put("Brawl", "Invalid Brawler (" + brawler.getName() + ')');
                valid = false;
            }
        }

        if (companion != null && deck.getCards().size() + deck.getSideboard().size() != getDeckMinSize() + 1) {
            invalid.put("Deck", "Must contain " + (getDeckMinSize() + 1) + " cards (companion doesn't count in deck size requirement): has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        } else if (companion == null && deck.getCards().size() + deck.getSideboard().size() != getDeckMinSize()) {
            invalid.put("Deck", "Must contain " + getDeckMinSize() + " cards: has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        valid = checkCounts(1, counts) && valid;

        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                invalid.put(bannedCard, "Banned");
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
                invalid.put(card.getName(), "Invalid color (" + colorIdentity.toString() + ')');
                valid = false;
            }
        }
        for (Card card : deck.getSideboard()) {
            if (!ManaUtil.isColorIdentityCompatible(colorIdentity, card.getColorIdentity())
                    && !(colorIdentity.isColorless()
                    && basicsInDeck.size() == 1
                    && basicsInDeck.contains(card.getName()))) {
                invalid.put(card.getName(), "Invalid color (" + colorIdentity.toString() + ')');
                valid = false;
            }
        }
        for (Card card : deck.getCards()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    invalid.put(card.getName(), "Not allowed Set: " + card.getExpansionSetCode());
                    valid = false;
                }
            }
        }
        for (Card card : deck.getSideboard()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    invalid.put(card.getName(), "Not allowed Set: " + card.getExpansionSetCode());
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
                        invalid.put(companion.getName(), "Deck invalid for companion");
                        valid = false;
                    }
                    break;
                }
            }
        }

        return valid;
    }
}
