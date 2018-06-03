

package mage.filter;

import java.util.UUID;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @param <E>
 */
public interface FilterInPlay<E> extends Filter<E> {

    boolean match(E o, UUID sourceId, UUID playerId, Game game);
    @Override
    FilterInPlay<E> copy();

}
