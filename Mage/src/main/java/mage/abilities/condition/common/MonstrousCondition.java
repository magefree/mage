package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Checks if a Permanent is monstrous
 *
 * @author LevelX2
 */
public enum MonstrousCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null && permanent.isMonstrous();
    }

    @Override
    public String toString() {
        return "{this} is monstrous";
    }
}
