package mage.filter.predicate.other;

import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public enum FaceDownPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        if (game.inCheckPlayableState()) {
            // Check for cost reduction of possible face down spell to cast
            if (input != null && !(input instanceof Permanent)) {
                return input.getAbilities().containsClass(MorphAbility.class);
            }
            return false;
        } else {
            return input.isFaceDown(game);
        }
    }

    @Override
    public String toString() {
        return "Face-down";
    }
}
