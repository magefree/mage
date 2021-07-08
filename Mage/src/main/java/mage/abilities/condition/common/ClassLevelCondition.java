package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public enum ClassLevelCondition implements Condition {
    ONE(1),
    TWO(2),
    THREE(3);

    private final int level;

    ClassLevelCondition(int level) {
        this.level = level;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.getClassLevel() == level;
    }
}
