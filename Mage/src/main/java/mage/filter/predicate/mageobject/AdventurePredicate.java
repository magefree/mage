package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author TheElk801
 */
public enum AdventurePredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        if (input instanceof Spell) {
            return ((Spell) input).getCard() instanceof AdventureCard;
        } else if (input instanceof Card) {
            return input instanceof AdventureCard;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Adventure";
    }
}
