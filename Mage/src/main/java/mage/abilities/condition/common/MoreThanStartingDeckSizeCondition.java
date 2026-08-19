package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;

/**
 * @author Grath
 */
public enum MoreThanStartingDeckSizeCondition implements Condition {
    TWO_HUNDRED(200);
    private final int amount;

    MoreThanStartingDeckSizeCondition(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getStartingDeckSize)
                .map(deckSize -> deckSize >= amount)
                .orElse(false);
    }

    @Override
    public String toString() {
        return "you have " + amount + " or more cards in your starting deck";
    }
}
