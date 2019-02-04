package mage.abilities.hint;

import mage.game.Game;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author JayDi85
 */
public interface Hint extends Serializable {

    String getText(Game game, UUID sourceId);

    Hint copy();
}