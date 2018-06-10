
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * 
 * @author noxx
 */
public class TransformedCondition implements Condition {

    protected boolean notCondition;

    public TransformedCondition() {
        this(false);
    }

    /**
     * The condition checks whether a permanent is transformed or not.
     *
     * @param  notCondition if true the condition is true when the permanent is not transformed
     * @return true if the condition is true, false if the condition is false
     */
    public TransformedCondition(boolean notCondition) {
        this.notCondition = notCondition;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if (notCondition) {
                return !permanent.isTransformed();
            } else {
                return permanent.isTransformed();
            }
        }
        return false;
    }
}
