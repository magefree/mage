
package mage.filter;

import java.io.Serializable;
import java.util.List;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @param <E>
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public interface Filter<E> extends Serializable {

    enum ComparisonScope {
        Any, All
    }

    boolean match(E o, Game game);

    Filter<E> add(Predicate<? super E> predicate);

    boolean checkObjectClass(Object object);

    String getMessage();

    void setMessage(String message);

    Filter<E> copy();

    List<Predicate<? super E>> getPredicates();
}
