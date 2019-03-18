
package mage.filter.predicate;

import java.io.Serializable;
import mage.game.Game;

/**
 * Determines a true or false value for a given input.
 *
 * @author North
 * @param <T>
 */
@FunctionalInterface
public interface Predicate <T> extends Serializable {
    /**
     * Returns the result of applying this predicate to {@code input}. This method is <i>generally
     * expected</i>, but not absolutely required, to have the following properties:
     *
     * <ul>
     * <li>Its execution does not cause any observable side effects.</li>
     * <li>The computation is <i>consistent with equals</i>; that is, {@link Objects#equal
     *     Objects.equal}{@code (a, b)} implies that {@code predicate.apply(a) ==
     *     predicate.apply(b)}.</li>
     * </ul>
     *
     * @param input
     * @param game
     * @return 
     * @throws NullPointerException if {@code input} is null and this predicate does not accept null
     *     arguments
     */
    boolean apply(T input, Game game);
}
