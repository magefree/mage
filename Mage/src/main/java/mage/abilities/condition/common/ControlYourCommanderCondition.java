package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.CommanderCardType;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;

/**
 * Checks if a player controls their commander.
 *
 * @author Alex-Vasile
 */
public enum ControlYourCommanderCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && game
                .getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, true)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .anyMatch(source::isControlledBy);
    }

    @Override
    public String toString() {
        return "you control your commander";
    }
}
