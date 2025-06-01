package mage.filter;

import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterPlayer extends FilterImpl<Player> {

    public FilterPlayer() {
        this("player");
    }

    public FilterPlayer(String name) {
        super(name);
    }

    protected FilterPlayer(final FilterPlayer filter) {
        super(filter);
    }

    @Override
    public FilterPlayer copy() {
        return new FilterPlayer(this);
    }

    @Override
    public void add(ObjectSourcePlayerPredicate predicate) {
        // verify checks
        Predicates.makeSurePredicateCompatibleWithFilter(predicate, Player.class);
        this.addExtra(predicate);
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Player;
    }
}
