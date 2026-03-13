package mage.filter;

import mage.abilities.Ability;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Make a Filter out of multiple inner filters.
 * If making a multi filter out of filterA and filterB,
 * any object match the multi filter if it either match
 * filterA or match filterB.
 *
 * @author Susucr
 */
public abstract class MultiFilterImpl<E> implements Filter<E> {

    protected List<Filter<? extends E>> innerFilters = new ArrayList<>();
    private String message;

    @Override
    public abstract MultiFilterImpl<E> copy();

    protected MultiFilterImpl(String name, Filter<? extends E>... filters) {
        this.message = name;
        if (filters.length < 2) {
            throw new IllegalArgumentException("Wrong code usage: MultiFilterImpl should have at least 2 inner filters");
        }
        this.innerFilters.addAll(
                Arrays.stream(filters)
                        .map(f -> f.copy())
                        .collect(Collectors.toList()));
    }

    protected MultiFilterImpl(final MultiFilterImpl<E> filter) {
        this.message = filter.message;
        for (Filter<? extends E> innerFilter : filter.innerFilters) {
            this.innerFilters.add(innerFilter.copy());
        }
    }

    @Override
    public boolean match(E object, Game game) {
        return innerFilters
                .stream()
                .anyMatch((Filter filter) -> filter.checkObjectClass(object) && filter.match(object, game));
    }

    @Override
    public boolean match(E object, UUID sourceControllerId, Ability source, Game game) {
        return innerFilters
                .stream()
                .anyMatch((Filter filter) -> filter.checkObjectClass(object) && filter.match(object, sourceControllerId, source, game));
    }

    @Override
    public MultiFilterImpl<E> add(Predicate<? super E> predicate) {
        innerFilters.forEach((Filter filter) -> filter.add(predicate));
        return this;
    }

    @Override
    public MultiFilterImpl<E> add(ObjectSourcePlayerPredicate predicate) {
        innerFilters.forEach((Filter filter) -> filter.add(predicate));
        return this;
    }

    @Override
    public void addExtra(ObjectSourcePlayerPredicate predicate) {
        innerFilters.forEach((Filter filter) -> filter.addExtra(predicate));
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return innerFilters
                .stream()
                .anyMatch((Filter filter) -> filter.checkObjectClass(object));
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
        return innerFilters.stream().anyMatch((Filter filter) -> filter.isLockedFilter());
    }

    @Override
    public void setLockedFilter(boolean lockedFilter) {
        innerFilters.forEach((Filter filter) -> filter.setLockedFilter(lockedFilter));
    }

    public List<Predicate<? super E>> getPredicates() {
        List<Predicate<? super E>> predicates = new ArrayList<>();
        for (Filter<? extends E> filter : innerFilters) {
            for (Predicate predicate : filter.getPredicates()) {
                predicates.add(predicate);
            }
        }
        return predicates;
    }

    public List<ObjectSourcePlayerPredicate<E>> getExtraPredicates() {
        List<ObjectSourcePlayerPredicate<E>> predicates = new ArrayList<>();
        for (Filter<? extends E> filter : innerFilters) {
            for (ObjectSourcePlayerPredicate predicate : filter.getExtraPredicates()) {
                predicates.add(predicate);
            }
        }
        return predicates;
    }
}
