package mage.filter;

import mage.abilities.Ability;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @param <E>
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public abstract class FilterImpl<E> implements Filter<E> {

    private List<Predicate<? super E>> predicates = new ArrayList<>();
    private List<ObjectSourcePlayerPredicate<E>> extraPredicates = new ArrayList<>();
    protected String message;
    protected boolean lockedFilter; // Helps to prevent "accidentally" modifying the StaticFilters objects

    @Override
    public abstract FilterImpl<E> copy();

    public FilterImpl(String name) {
        this.message = name;
        this.lockedFilter = false;
    }

    protected FilterImpl(final FilterImpl<E> filter) {
        this.message = filter.message;
        this.predicates = new ArrayList<>(filter.predicates);
        this.extraPredicates.addAll(filter.extraPredicates);
        this.lockedFilter = false;// After copying a filter it's allowed to modify
    }

    @Override
    public boolean match(E e, Game game) {
        if (checkObjectClass(e)) {
            return Predicates.and(predicates).apply(e, game);
        }
        return false;
    }

    public boolean match(E object, UUID sourceControllerId, Ability source, Game game) {
        if (!this.match(object, game)) {
            return false;
        }
        ObjectSourcePlayer<E> osp = new ObjectSourcePlayer<>(object, sourceControllerId, source);
        return extraPredicates.stream().allMatch(p -> p.apply(osp, game));
    }

    @Override
    public Filter<E> add(Predicate<? super E> predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        predicates.add(predicate);
        return this;
    }

    @Override
    public final void addExtra(ObjectSourcePlayerPredicate predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        extraPredicates.add(predicate);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public final void setMessage(String message) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public boolean isLockedFilter() {
        return lockedFilter;
    }

    @Override
    public void setLockedFilter(boolean lockedFilter) {
        this.lockedFilter = lockedFilter;
    }

    public List<Predicate<? super E>> getPredicates() {
        return predicates;
    }

    public List<ObjectSourcePlayerPredicate<E>> getExtraPredicates() {
        return new ArrayList<>(extraPredicates);
    }

    public boolean hasPredicates() {
        return !predicates.isEmpty() || !extraPredicates.isEmpty();
    }
}
