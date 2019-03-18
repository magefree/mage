package mage.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterPlayer extends FilterImpl<Player> {

    protected List<ObjectPlayerPredicate<ObjectPlayer<Player>>> extraPredicates = new ArrayList<>();

    public FilterPlayer() {
        this("player");
    }

    public FilterPlayer(String name) {
        super(name);
    }

    public FilterPlayer(final FilterPlayer filter) {
        super(filter);
        this.extraPredicates = new ArrayList<>(filter.extraPredicates);
    }

    public void add(ObjectPlayerPredicate predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        extraPredicates.add(predicate);
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Player;
    }

    public boolean match(Player checkPlayer, UUID sourceId, UUID sourceControllerId, Game game) {
        if (!this.match(checkPlayer, game)) {
            return false;
        }

        return Predicates.and(extraPredicates).apply(new ObjectSourcePlayer(checkPlayer, sourceId, sourceControllerId), game);
    }

    @Override
    public FilterPlayer copy() {
        return new FilterPlayer(this);
    }

}
