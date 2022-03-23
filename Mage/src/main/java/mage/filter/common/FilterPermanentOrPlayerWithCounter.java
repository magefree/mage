package mage.filter.common;

import mage.MageItem;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author nantuko
 */
public class FilterPermanentOrPlayerWithCounter extends FilterPermanentOrPlayer {

    public FilterPermanentOrPlayerWithCounter() {
        this("player or permanent with counters on them");
    }

    public FilterPermanentOrPlayerWithCounter(String name) {
        super(name);
    }

    public FilterPermanentOrPlayerWithCounter(final FilterPermanentOrPlayerWithCounter filter) {
        super(filter);
    }

    @Override
    public boolean match(MageItem o, Game game) {
        if (super.match(o, game)) {
            if (o instanceof Player) {
                return !((Player) o).getCounters().isEmpty();
            } else if (o instanceof Permanent) {
                return !((Permanent) o).getCounters(game).isEmpty();
            }
        }
        return false;
    }

    @Override
    public boolean match(MageItem o, UUID playerId, Ability source, Game game) {
        if (super.match(o, playerId, source, game)) { // same as parent class, so can call with full params
            if (o instanceof Player) {
                return !((Player) o).getCounters().isEmpty();
            } else if (o instanceof Permanent) {
                return !((Permanent) o).getCounters(game).isEmpty();
            }
        }
        return false;
    }

    @Override
    public FilterPermanentOrPlayerWithCounter copy() {
        return new FilterPermanentOrPlayerWithCounter(this);
    }

}
