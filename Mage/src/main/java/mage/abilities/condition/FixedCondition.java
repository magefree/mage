

package mage.abilities.condition;

import mage.abilities.Ability;
import mage.game.Game;
import sun.font.CreatedFontTracker;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        return this.conditionMet == ((FixedCondition) obj).conditionMet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditionMet);
    }
}
