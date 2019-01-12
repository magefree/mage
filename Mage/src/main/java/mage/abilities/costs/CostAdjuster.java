package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * @author TheElk801
 */
public interface CostAdjuster {

    void adjustCosts(Ability ability, Game game);
}
