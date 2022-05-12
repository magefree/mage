package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 * As long as the sourceId permanent is
 * on the battlefield, the condition is true.
 *
 * @author LevelX2
 */
public enum SourceOnBattlefieldCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source.getSourcePermanentIfItStillExists(game) != null;
    }

    @Override
    public String toString() {
        return "if {this} is on the battlefield";
    }
}
