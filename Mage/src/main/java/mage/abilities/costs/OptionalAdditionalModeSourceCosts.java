
package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public interface OptionalAdditionalModeSourceCosts {

    void addOptionalAdditionalModeCosts(Ability ability, Game game);

    void changeModes(Ability ability, Game game);

    String getCastMessageSuffix();
}
