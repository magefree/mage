
package mage.target.common;

import mage.filter.FilterOpponent;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class TargetOpponent extends TargetPlayer {

    public TargetOpponent() {
        this(false);
    }
    
    public TargetOpponent(boolean notTarget) {
        this(new FilterOpponent(), notTarget);        
    }
    
    public TargetOpponent(FilterOpponent filter, boolean notTarget) {
        super(1, 1, notTarget, filter);
    }

    public TargetOpponent(final TargetOpponent target) {
        super(target);
    }

    @Override
    public TargetOpponent copy() {
        return new TargetOpponent(this);
    }
}
