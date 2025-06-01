package mage.filter;

import mage.abilities.Ability;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @param <E>
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public interface Filter<E> extends Serializable, Copyable<Filter<E>> {

    enum ComparisonScope {
        Any, All
    }

    boolean match(E o, Game game);

    boolean match(E object, UUID sourceControllerId, Ability source, Game game);

    Filter<E> add(Predicate<? super E> predicate);

    boolean checkObjectClass(Object object);

    String getMessage();

    void setMessage(String message);

    Filter<E> copy();

    public boolean isLockedFilter();

    public void setLockedFilter(boolean lockedFilter);

    List<Predicate<? super E>> getPredicates();

    default List<ObjectSourcePlayerPredicate<E>> getExtraPredicates() {
        return new ArrayList<>();
    }
}
