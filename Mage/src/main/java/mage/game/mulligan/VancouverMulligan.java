package mage.game.mulligan;

import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public class VancouverMulligan extends ParisMulligan {

    public VancouverMulligan(int freeMulligans) {
        super(freeMulligans);
    }

    @Override
    public void executeMulliganPhase(Game game, int startingHandSize) {
        super.executeMulliganPhase(game, startingHandSize);
        // new scry rule
        for (UUID playerId : game.getState().getPlayerList(game.getStartingPlayerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.getHand().size() < startingHandSize) {
                player.scry(1, null, game);
            }
        }
    }

    @Override
    public VancouverMulligan copy() {
        return new VancouverMulligan(getFreeMulligans());
    }

}
