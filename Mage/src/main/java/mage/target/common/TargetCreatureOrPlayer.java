package mage.target.common;

import mage.filter.common.FilterCreatureOrPlayer;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCreatureOrPlayer extends TargetPermanentOrPlayer {

    public TargetCreatureOrPlayer() {
        this(1, 1, new FilterCreatureOrPlayer());
    }

    public TargetCreatureOrPlayer(FilterCreatureOrPlayer filter) {
        this(1, 1, filter);
    }

    public TargetCreatureOrPlayer(int minNumTargets, int maxNumTargets, FilterCreatureOrPlayer filter) {
        super(minNumTargets, maxNumTargets, filter, false);
    }

    protected TargetCreatureOrPlayer(final TargetCreatureOrPlayer target) {
        super(target);
    }

    @Override
    public TargetCreatureOrPlayer copy() {
        return new TargetCreatureOrPlayer(this);
    }

}
