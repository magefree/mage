package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * Cost that can change ability's modes (example: Kicker or Entwine can make all modes selectable).
 *
 * @author LevelX2
 */
public interface OptionalAdditionalModeSourceCosts extends OptionalAdditionalSourceCosts {

    void changeModes(Ability ability, Game game);

}
