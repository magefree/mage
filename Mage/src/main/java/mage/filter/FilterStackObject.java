package mage.filter;

import mage.abilities.Ability;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author North
 */
public class FilterStackObject extends FilterObject<StackObject> {

    protected final List<ObjectSourcePlayerPredicate<StackObject>> extraPredicates = new ArrayList<>();

    public FilterStackObject() {
        this("spell or ability");
    }

    public FilterStackObject(String name) {
        super(name);
    }

    public FilterStackObject(final FilterStackObject filter) {
        super(filter);
        this.extraPredicates.addAll(filter.extraPredicates);
    }

    public boolean match(StackObject stackObject, UUID playerId, Ability source, Game game) {
        if (!this.match(stackObject, game)) {
            return false;
        }
        ObjectSourcePlayer<StackObject> osp = new ObjectSourcePlayer<>(stackObject, playerId, source);
        return extraPredicates.stream().allMatch(p -> p.apply(osp, game));
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
