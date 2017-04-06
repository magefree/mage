package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public enum MoreCardsInHandThanOpponentsCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int cardsInHand = player.getHand().size();
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null && opponent.getHand().size() >= cardsInHand) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "you have more cards in hand than each opponent";
    }

}
