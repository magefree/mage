

package mage.filter.common;

import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;
import mage.MageItem;

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
        if (o instanceof Player) {
            if (((Player)o).getCounters().isEmpty()) {
                return false;
            }
        } else if (o instanceof Permanent) {
            if (((Permanent)o).getCounters(game).isEmpty()) {
                return false;
            }
        }
        return super.match(o, game);
    }

    @Override
    public boolean match(MageItem o, UUID sourceId, UUID playerId, Game game) {
        if (o instanceof Player) {
            return playerFilter.match((Player) o, sourceId, playerId, game);
        } else if (o instanceof Permanent) {
            return permanentFilter.match((Permanent) o, sourceId, playerId, game);
        }
        return false;
    }

    @Override
    public FilterPermanentOrPlayerWithCounter copy() {
        return new FilterPermanentOrPlayerWithCounter(this);
    }

}
