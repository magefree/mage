package mage.target.common;

import mage.filter.FilterOpponent;
import mage.target.TargetPlayer;

/**
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class TargetOpponent extends TargetPlayer {

    private static final FilterOpponent filter = new FilterOpponent();

    public TargetOpponent() {
        this(false);
    }

    public TargetOpponent(boolean notTarget) {
        super(1, 1, notTarget, filter);
    }

    private TargetOpponent(final TargetOpponent target) {
        super(target);
    }

    @Override
    public TargetOpponent copy() {
        return new TargetOpponent(this);
    }
}
