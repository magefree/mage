

package mage.abilities.condition;

import mage.abilities.Ability;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */


public class FixedCondition implements Condition{

    protected boolean conditionMet;

    public FixedCondition(boolean conditionMet) {
        this.conditionMet = conditionMet;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return conditionMet;
    }
}
