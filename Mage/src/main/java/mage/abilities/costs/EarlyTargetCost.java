package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Grath
 * <p>
 * Supporting rule 601.5 for early target choice before choosing how to divide or distribute something.
 */
public interface EarlyTargetCost {

    void chooseTarget(Game game, Ability source, Player controller);

}
