package mage.filter;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author North
 */
public class FilterStackObject extends FilterObject<StackObject> {

    protected List<ObjectSourcePlayerPredicate<ObjectSourcePlayer<StackObject>>> extraPredicates = new ArrayList<>();

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

        return Predicates.and(extraPredicates).apply(new ObjectSourcePlayer<StackObject>(stackObject, sourceId, playerId), game);
    }

    public final void add(ObjectSourcePlayerPredicate predicate) {
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
