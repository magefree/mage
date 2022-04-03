
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.DashAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 * @author LevelX2
 */

public enum DashedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            return card.getAbilities(game).stream()
                    .filter(a -> a instanceof DashAbility)
                    .anyMatch(d -> ((DashAbility) d).isActivated(source, game));

        }
        return false;
    }
}
