package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * Interface for abilities that impose a mandatory additional cost on the source.
 */
public interface MandatoryAdditionalSourceCosts extends Ability {

    void addMandatoryAdditionalCosts(Ability ability, Game game);
}
