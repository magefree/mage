package mage.abilities.condition;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

/**
 * Interface describing condition occurrence.
 *
 * @author nantuko, noxx
 */
@FunctionalInterface
public interface Condition extends Serializable {

    /**
     * Checks the game to see if this condition applies for the given ability.
     *
     * @param game
     * @param source
     * @return
     */
    boolean apply(Game game, Ability source);

    default String getManaText() {
        return this.getClass().getSimpleName();
    }

    default boolean caresAboutManaColor() {
        return false;
    }
}
