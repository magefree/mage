package mage.deck;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.DeckValidatorErrorType;
import mage.constants.CardType;

import java.util.Map;
import java.util.Set;

/**
 * @author spjspj
 */
public class FreeformCommander extends AbstractCommander {

    public FreeformCommander() {
        super("Freeform Commander");
        for (ExpansionSet set : Sets.getInstance().values()) {
            setCodes.add(set.getCode());
        }

        // no banned cards
        this.banned.clear();
    }

    public FreeformCommander(String name) {
        super(name);
    }

    @Override
    protected boolean checkBanned(Map<String, Integer> counts) {
        return true;
    }

    @Override
    protected boolean checkCommander(Card commander, Set<Card> commanders) {
        if (!commander.hasCardTypeForDeckbuilding(CardType.CREATURE) && !commander.isLegendary()) {
            addError(DeckValidatorErrorType.PRIMARY, commander.getName(), "For Freeform Commander, the commander must be a creature or be legendary. Yours was: " + commander.getName(), true);
            return false;
        }
        return true;
    }
}
