
package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * Interface for abilities that add additional costs to the source.
 *
 * Example of such additional source costs:
 * {@link mage.abilities.keyword.KickerAbility}
 *
 * @author LevelX2
 */
public interface OptionalAdditionalSourceCosts {

    void addOptionalAdditionalCosts(Ability ability, Game game);

    String getCastMessageSuffix();
}
