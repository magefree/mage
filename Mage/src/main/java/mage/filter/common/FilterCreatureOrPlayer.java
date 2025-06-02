package mage.filter.common;

import mage.filter.FilterPlayer;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCreatureOrPlayer extends FilterPermanentOrPlayer {

    public FilterCreatureOrPlayer() {
        this("creature or player");
    }

    public FilterCreatureOrPlayer(String name) {
        super(name, new FilterCreaturePermanent(), new FilterPlayer());
    }

    protected FilterCreatureOrPlayer(final FilterCreatureOrPlayer filter) {
        super(filter);
    }

    @Override
    public FilterCreatureOrPlayer copy() {
        return new FilterCreatureOrPlayer(this);
    }

}
