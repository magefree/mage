package mage.abilities.condition;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

/**
 * Interface describing condition occurrence.
 *
 * @author nantuko
 */
public interface Condition extends Serializable {
    boolean apply(Game game, Ability source);
}
