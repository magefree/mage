package mage.target.common;

import mage.filter.common.FilterCreatureOrPlayer;
import mage.filter.common.FilterPermanentOrPlayer;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCreatureOrPlayer extends TargetPermanentOrPlayer {

    private static final FilterPermanentOrPlayer filter = new FilterCreatureOrPlayer();

    public TargetCreatureOrPlayer() {
        super(1, 1, filter, false);
    }

    protected TargetCreatureOrPlayer(final TargetCreatureOrPlayer target) {
        super(target);
    }

    @Override
    public TargetCreatureOrPlayer copy() {
        return new TargetCreatureOrPlayer(this);
    }
}
