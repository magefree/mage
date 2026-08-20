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

    /**
     * Make sure on setting a new Filter that you overwrite this method
     * and call Predicates.makeSurePredicateCompatibleWithFilter
     * to check that the filter is able to process objects
     * of the right kind. Helps with checks the Compiler can't do
     * due to ObjectSourcePlayer casting in the this.match(4 arguments).
     * <p>
     * (method should then call this.addExtra(predicate) after verify checks)
     */
    Filter<E> add(ObjectSourcePlayerPredicate predicate);

    // TODO: if someone can find a way to not have to add this
    //       Compiler was confused between overloads
    void addExtra(ObjectSourcePlayerPredicate predicate);

    boolean checkObjectClass(Object object);

    String getMessage();

    void setMessage(String message);

    Filter<E> copy();

    boolean isLockedFilter();

    void setLockedFilter(boolean lockedFilter);

    List<Predicate<? super E>> getPredicates();

    default List<ObjectSourcePlayerPredicate<E>> getExtraPredicates() {
        return new ArrayList<>();
    }
}
