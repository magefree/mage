
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author xenohedron
 */

public enum SourceEnteredThisTurnCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null && permanent.getTurnsOnBattlefield() == 0;
    }

    @Override
    public String toString() {
        return "{this} entered the battlefield this turn";
    }

}
