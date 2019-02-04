package mage.abilities.hint;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

/**
 * @author JayDi85
 */
public interface Hint extends Serializable {

    String getText(Game game, Ability ability);

    Hint copy();
}