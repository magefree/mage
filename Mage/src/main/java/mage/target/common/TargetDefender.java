package mage.target.common;

import mage.filter.common.FilterDefender;

import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetDefender extends TargetPermanentOrPlayer {

    public TargetDefender(Set<UUID> defenders) {
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
