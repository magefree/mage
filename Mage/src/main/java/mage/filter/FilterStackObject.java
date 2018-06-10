package mage.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 *
 * @author North
 */
public class FilterStackObject extends FilterObject<StackObject> {

    protected List<ObjectPlayerPredicate<ObjectPlayer<Permanent>>> extraPredicates = new ArrayList<>();

    public FilterStackObject() {
        this("spell or ability");
    }

    public FilterStackObject(String name) {
        super(name);
    }

    public FilterStackObject(final FilterStackObject filter) {
        super(filter);
        this.extraPredicates = new ArrayList<>(filter.extraPredicates);
    }

    public boolean match(StackObject stackObject, UUID sourceId, UUID playerId, Game game) {
        if (!this.match(stackObject, game)) {
            return false;
        }

        return Predicates.and(extraPredicates).apply(new ObjectSourcePlayer(stackObject, sourceId, playerId), game);
    }

    public final void add(ObjectPlayerPredicate predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        extraPredicates.add(predicate);
    }

    @Override
    public FilterStackObject copy() {
        return new FilterStackObject(this);
    }

}
