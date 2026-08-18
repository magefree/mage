package mage.deck;

import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidatorErrorType;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SubType;
import mage.util.CardUtil;

import java.util.Map;
import java.util.Set;

/**
 * @author ArcadesSaboth
 */
public class PauperCommander extends AbstractCommander {
    public PauperCommander() {
        this("Pauper Commander");

        // Allowed rarities
        rarities.add(Rarity.COMMON);
        rarities.add(Rarity.LAND);

        // Banned cards
        banned.add("Rhystic Study");
        banned.add("Mystic Remora");
    }

    protected PauperCommander(String name) {
        super(name);
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }
    }

    @Override
    protected boolean checkBanned(Map<String, Integer> counts) {
        boolean valid = true;
        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                addError(DeckValidatorErrorType.BANNED, bannedCard, "Banned", true);
                valid = false;
            }
        }
        return valid;
    }

    @Override
    public boolean validate(Deck deck) {
        for (Card card : deck.getCards()) {
            if (!legalRarity(card))
            {
                addError(DeckValidatorErrorType.OTHER, card.getName(), "Card must be have any printing in Common rarity.",true);
                return false;
            }
        }

        return super.validate(deck);
    }

    protected boolean checkCommander(Card commander, Set<Card> commanders) {
        if (commander.getRarity() != Rarity.UNCOMMON) {
            return false;
        }

        // There are no Uncommon planeswalker with this ability, at the time of writing, but this should ensure
        // future changes
        if (commander.getAbilities().contains(CanBeYourCommanderAbility.getInstance())) {
            return true;
        }

        // We don't check the Legendary supertype in Pauper EDH, non-legendary creature can be your commander.
        if  (
                commander.hasCardTypeForDeckbuilding(CardType.CREATURE)
                        || commander.hasSubTypeForDeckbuilding(SubType.VEHICLE)
                        || commander.hasSubTypeForDeckbuilding(SubType.SPACECRAFT)
                        && CardUtil
                        .castStream(commander.getAbilities(), StationLevelAbility.class)
                        .anyMatch(StationLevelAbility::hasPT)
        ) {
            return true;
        }

        return commanders.size() == 2 && validators.stream().anyMatch(validator -> validator.specialCheck(commander));
    }

}
