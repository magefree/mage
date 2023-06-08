package mage.filter;

import mage.abilities.Ability;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterPlayer extends FilterImpl<Player> {

    protected final List<ObjectSourcePlayerPredicate<Player>> extraPredicates = new ArrayList<>();

    public FilterPlayer() {
        this("player");
    }

    public FilterPlayer(String name) {
        super(name);
    }

    public FilterPlayer(final FilterPlayer filter) {
        super(filter);
        this.extraPredicates.addAll(filter.extraPredicates);
    }

    public FilterPlayer add(ObjectSourcePlayerPredicate predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        extraPredicates.add(predicate);
        return this;
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Player;
    }

    public boolean match(Player checkPlayer, UUID sourceControllerId, Ability source, Game game) {
        if (!this.match(checkPlayer, game)) {
            return false;
        }
        ObjectSourcePlayer<Player> osp = new ObjectSourcePlayer<>(checkPlayer, sourceControllerId, source);
        return extraPredicates.stream().allMatch(p -> p.apply(osp, game));
    }

    @Override
    public FilterPlayer copy() {
        return new FilterPlayer(this);
    }
}
