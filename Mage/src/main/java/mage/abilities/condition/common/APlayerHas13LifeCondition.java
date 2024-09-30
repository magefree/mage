package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;

/**
 * @author TheElk801
 */
public enum APlayerHas13LifeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(Player::getLife)
                .anyMatch(x -> x <= 13);
    }

    @Override
    public String toString() {
        return "a player has 13 or less life";
    }
}
