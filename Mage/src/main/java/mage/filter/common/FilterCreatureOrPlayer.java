package mage.filter.common;

import mage.MageItem;
import mage.abilities.Ability;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.FilterPlayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCreatureOrPlayer extends FilterImpl<MageItem> implements FilterInPlay<MageItem> {

    protected FilterCreaturePermanent creatureFilter;
    protected final FilterPlayer playerFilter;

    public FilterCreatureOrPlayer() {
        this("creature or player");
    }

    public FilterCreatureOrPlayer(String name) {
        super(name);
        creatureFilter = new FilterCreaturePermanent();
        playerFilter = new FilterPlayer();
    }

    public FilterCreatureOrPlayer(final FilterCreatureOrPlayer filter) {
        super(filter);
        this.creatureFilter = filter.creatureFilter.copy();
        this.playerFilter = filter.playerFilter.copy();
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return true;
    }

    @Override
    public boolean match(MageItem o, Game game) {
        if (super.match(o, game)) {
            if (o instanceof Player) {
                return playerFilter.match((Player) o, game);
            } else if (o instanceof Permanent) {
                return creatureFilter.match((Permanent) o, game);
            }
        }
        return false;
    }

    @Override
    public boolean match(MageItem o, UUID playerId, Ability source, Game game) {
        if (super.match(o, game)) { // process predicates
            if (o instanceof Player) {
                return playerFilter.match((Player) o, playerId, source, game);
            } else if (o instanceof Permanent) {
                return creatureFilter.match((Permanent) o, playerId, source, game);
            }
        }
        return false;
    }

    public FilterCreaturePermanent getCreatureFilter() {
        return this.creatureFilter;
    }

    public FilterPlayer getPlayerFilter() {
        return this.playerFilter;
    }

    public void setCreatureFilter(FilterCreaturePermanent creatureFilter) {
        this.creatureFilter = creatureFilter;
    }

    @Override
    public FilterCreatureOrPlayer copy() {
        return new FilterCreatureOrPlayer(this);
    }

}
