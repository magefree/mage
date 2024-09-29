package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.HashSet;
import java.util.Optional;

/**
 * @author TheElk801
 */
public enum ThresholdCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getGraveyard)
                .map(HashSet::size)
                .orElse(0) >= 7;
    }

    @Override
    public String toString() {
        return "seven or more cards are in your graveyard";
    }
}
