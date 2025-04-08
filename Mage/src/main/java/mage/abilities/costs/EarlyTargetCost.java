package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Grath
 * <p>
 * Support 601.2b rules for ealry target choice before X announce and other actions
 */
public interface EarlyTargetCost {

    void chooseTarget(Game game, Ability source, Player controller);

}
