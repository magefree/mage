
package mage.filter.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.filter.FilterImpl;
import mage.filter.FilterPlayer;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterPlaneswalkerOrPlayer extends FilterImpl<Object> {

    protected final FilterPlaneswalkerPermanent planeswalkerFilter;
    protected final FilterPlayer playerFilter;

    public FilterPlaneswalkerOrPlayer(Set<UUID> defenders) {
        super("planeswalker or player");

        List<Predicate<Permanent>> permanentPredicates = new ArrayList<>();
        for (UUID defenderId : defenders) {
            permanentPredicates.add(new ControllerIdPredicate(defenderId));
        }
        planeswalkerFilter = new FilterPlaneswalkerPermanent();
        planeswalkerFilter.add(Predicates.or(permanentPredicates));

        List<Predicate<Player>> playerPredicates = new ArrayList<>();
        for (UUID defenderId : defenders) {
            playerPredicates.add(new PlayerIdPredicate(defenderId));
        }
        playerFilter = new FilterPlayer();
        playerFilter.add(Predicates.or(playerPredicates));
    }

    public FilterPlaneswalkerOrPlayer(final FilterPlaneswalkerOrPlayer filter) {
        super(filter);
        this.planeswalkerFilter = filter.planeswalkerFilter.copy();
        this.playerFilter = filter.playerFilter.copy();
    }

    public FilterPlaneswalkerPermanent getFilterPermanent() {
        return this.planeswalkerFilter;
    }

    public FilterPlayer getFilterPlayer() {
        return this.playerFilter;
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return true;
    }

    @Override
    public boolean match(Object o, Game game) {
        if (o instanceof Player) {
            return playerFilter.match((Player) o, game);
        } else if (o instanceof Permanent) {
            return planeswalkerFilter.match((Permanent) o, game);
        }
        return false;
    }

    @Override
    public FilterPlaneswalkerOrPlayer copy() {
        return new FilterPlaneswalkerOrPlayer(this);
    }
}
