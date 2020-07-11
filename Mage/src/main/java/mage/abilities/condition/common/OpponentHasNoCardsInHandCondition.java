package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author JayDi85
 */

public enum OpponentHasNoCardsInHandCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null && opponent.getHand().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "an opponent has no cards in hand";
    }
}
