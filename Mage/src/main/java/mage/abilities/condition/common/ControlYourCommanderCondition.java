package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.CommanderCardType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collection;
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
        return game.getPlayerList()
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> game.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, true)) // must search all card parts (example: mdf commander on battlefield)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(Permanent::getOwnerId)
                .anyMatch(source.getControllerId()::equals);
    }

    @Override
    public String toString() {
        return "If you control your commander";
    }
}
