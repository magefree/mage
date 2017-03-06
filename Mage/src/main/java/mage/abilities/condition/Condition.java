package mage.abilities.condition;

import java.io.Serializable;
import mage.abilities.Ability;
import mage.game.Game;


/**
 * Interface describing condition occurrence.
 *
 * @author nantuko, noxx
 */
@FunctionalInterface
public interface Condition extends Serializable {

    enum ComparisonType {

        GreaterThan(">"),
        Equal("=="),
        LessThan("<");

        private final String text;

        ComparisonType(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    /**
     * Checks the game to see if this condition applies for the given ability.
     * 
     * @param game
     * @param source
     * @return
     */
    boolean apply(Game game, Ability source);
}
