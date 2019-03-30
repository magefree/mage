
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 * @author Ketsuban
 */
public enum MorphCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            return card.getAbilities().stream()
                    .filter(a -> a instanceof MorphAbility)
                    .anyMatch(Ability::isActivated);
        }
        return false;
    }
}
