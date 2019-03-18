
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * Checks if the player has its commander in play and controls it
 *
 * @author LevelX2
 */
public enum CommanderInPlayCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID commanderId : controller.getCommandersIds()) {
                Permanent commander = game.getPermanent(commanderId);
                if (commander != null && commander.isControlledBy(source.getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "As long as you control your commander";
    }

}
