package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Checks if a Permanent is suspected
 *
 * @author DominionSpy
 */
public enum SuspectedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.isSuspected();
        }
        return false;
    }
}
