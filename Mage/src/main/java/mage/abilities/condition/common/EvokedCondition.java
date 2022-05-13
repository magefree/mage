

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 *  Checks if a the spell was cast with the alternate evoke costs
 *
 * @author LevelX2
 */

public enum EvokedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            return card.getAbilities(game).stream()
                    .filter(EvokeAbility.class::isInstance)
                    .anyMatch(evoke -> ((EvokeAbility) evoke).isActivated(source, game));
        }
        return false;
    }
}
