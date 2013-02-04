package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * Condition for -
 *  Any opponent has X or more cards in his or her graveyard
 *  @author Loki
 */
public class CardsInOpponentGraveCondition implements Condition {
    private int value;

    public CardsInOpponentGraveCondition(int value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null && opponent.getGraveyard().size() >= value)
                    return true;
            }
        }
        return false;
    }
}
