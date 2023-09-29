package mage.target.common;

import mage.MageObjectReference;
import mage.filter.common.FilterDefender;

import java.util.Set;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetDefender extends TargetPermanentOrPlayer {

    public TargetDefender(Set<MageObjectReference> defenders) {
        super(1, 1, new FilterDefender(defenders), true);
    }

    private TargetDefender(final TargetDefender target) {
        super(target);
    }

    @Override
    public TargetDefender copy() {
        return new TargetDefender(this);
    }
}
