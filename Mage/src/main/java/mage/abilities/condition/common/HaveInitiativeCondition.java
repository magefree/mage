package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum HaveInitiativeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source.isControlledBy(game.getInitiativeId());
    }

    @Override
    public String toString() {
        return "if you have the initiative";
    }
}
