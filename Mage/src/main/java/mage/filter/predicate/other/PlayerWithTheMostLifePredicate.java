package mage.filter.predicate.other;

import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public enum PlayerWithTheMostLifePredicate implements ObjectSourcePlayerPredicate<Player> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player inputPlayer = input.getObject();
        if (inputPlayer == null) {
            return false;
        }

        int mostLife = Integer.MIN_VALUE;
        for (UUID playerId : game.getState().getPlayersInRange(input.getPlayerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.getLife() > mostLife) {
                mostLife = player.getLife();
            }
        }
        return inputPlayer.getLife() == mostLife;
    }
}
