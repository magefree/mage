package mage.filter.predicate.other;

import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum FaceDownCastablePredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        // is card able to cast as face down
        return input.getAbilities(game).containsClass(MorphAbility.class);
    }

    @Override
    public String toString() {
        return "Face-down";
    }
}
