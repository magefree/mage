package mage.filter.common;

import mage.MageItem;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.FilterPermanent;
import mage.filter.FilterPlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author nantuko
 */
public class FilterPermanentOrPlayer extends FilterImpl<MageItem> implements FilterInPlay<MageItem> {

    protected final FilterPermanent permanentFilter;
    protected final FilterPlayer playerFilter;

    public FilterPermanentOrPlayer() {
        this("player or permanent");
    }

    public FilterPermanentOrPlayer(String name) {
        this(name, new FilterPermanent(), new FilterPlayer());
    }

    public FilterPermanentOrPlayer(String name, FilterPermanent permanentFilter, FilterPlayer playerFilter) {
        super(name);
        this.permanentFilter = permanentFilter;
        this.playerFilter = playerFilter;
    }

    public FilterPermanentOrPlayer(final FilterPermanentOrPlayer filter) {
        super(filter);
        this.permanentFilter = filter.permanentFilter.copy();
        this.playerFilter = filter.playerFilter.copy();
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return true;
    }

    public void add(ObjectSourcePlayerPredicate predicate) {
        playerFilter.add((Predicate<? super Player>) predicate);
        permanentFilter.add((Predicate<? super Permanent>) predicate);
    }

    @Override
    public boolean match(MageItem o, Game game) {
        if (super.match(o, game)) {
            if (o instanceof Player) {
                return playerFilter.match((Player) o, game);
            } else if (o instanceof Permanent) {
                return permanentFilter.match((Permanent) o, game);
            }
        }
        return false;
    }

    @Override
    public boolean match(MageItem o, UUID sourceId, UUID playerId, Game game) {
        if (super.match(o, game)) { // process predicates
            if (o instanceof Player) {
                return playerFilter.match((Player) o, sourceId, playerId, game);
            } else if (o instanceof Permanent) {
                return permanentFilter.match((Permanent) o, sourceId, playerId, game);
            }
        }
        return false;
    }

    public FilterPermanent getPermanentFilter() {
        return this.permanentFilter;
    }

    public FilterPlayer getPlayerFilter() {
        return this.playerFilter;
    }

    @Override
    public FilterPermanentOrPlayer copy() {
        return new FilterPermanentOrPlayer(this);
    }

}
