package mage.filter;

import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <E>
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public abstract class FilterImpl<E> implements Filter<E> {

    protected List<Predicate<? super E>> predicates = new ArrayList<>();
    protected String message;
    protected boolean lockedFilter; // Helps to prevent "accidentally" modifying the StaticFilters objects

    @Override
    public abstract FilterImpl<E> copy();

    public FilterImpl(String name) {
        this.message = name;
        this.lockedFilter = false;
    }

    public FilterImpl(final FilterImpl<E> filter) {
        this.message = filter.message;
        this.predicates = new ArrayList<>(filter.predicates);
        this.lockedFilter = false;// After copying a filter it's allowed to modify
    }

    @Override
    public boolean match(E e, Game game) {
        if (checkObjectClass(e)) {
            return Predicates.and(predicates).apply(e, game);
        }
        return false;
    }

    @Override
    public final Filter<E> add(Predicate<? super E> predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        predicates.add(predicate);
        return this;
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
}
