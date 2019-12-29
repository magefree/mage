package mage.filter.common;

import java.util.UUID;

import mage.MageItem;
import mage.filter.FilterPlayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author JRHerlehy Created on 4/8/18.
 */
public class FilterCreaturePlayerOrPlaneswalker extends FilterPermanentOrPlayer {

    protected FilterCreaturePermanent creatureFilter;
    protected FilterPlaneswalkerPermanent planeswalkerFilter;

    public FilterCreaturePlayerOrPlaneswalker() {
        this("any target");
    }

    public FilterCreaturePlayerOrPlaneswalker(String name) {
        super(name);
        creatureFilter = new FilterCreaturePermanent();
        planeswalkerFilter = new FilterPlaneswalkerPermanent();
    }

    public FilterCreaturePlayerOrPlaneswalker(final FilterCreaturePlayerOrPlaneswalker filter) {
        super(filter);
        this.creatureFilter = filter.creatureFilter.copy();
        this.planeswalkerFilter = filter.planeswalkerFilter.copy();
    }

    @Override
    public boolean match(MageItem o, Game game) {
        if (o instanceof Player) {
            return playerFilter.match((Player) o, game);
        } else if (o instanceof Permanent) {
            return creatureFilter.match((Permanent) o, game)
                    || planeswalkerFilter.match((Permanent) o, game);
        }
        return false;
    }

    @Override
    public boolean match(MageItem o, UUID sourceId, UUID playerId, Game game) {
        if (o instanceof Player) {
            return playerFilter.match((Player) o, sourceId, playerId, game);
        } else if (o instanceof Permanent) {
            return creatureFilter.match((Permanent) o, sourceId, playerId, game)
                    || planeswalkerFilter.match((Permanent) o, sourceId, playerId, game);
        }
        return false;
    }

    public FilterCreaturePermanent getCreatureFilter() {
        return this.creatureFilter;
    }

    public FilterPlaneswalkerPermanent getPlaneswalkerFilter() {
        return this.planeswalkerFilter;
    }

    public void setCreatureFilter(FilterCreaturePermanent creatureFilter) {
        this.creatureFilter = creatureFilter;
    }

    @Override
    public FilterCreaturePlayerOrPlaneswalker copy() {
        return new FilterCreaturePlayerOrPlaneswalker(this);
    }
}
