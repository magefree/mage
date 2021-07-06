package mage.game.mulligan;

import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public class VancouverMulligan extends ParisMulligan {

    public VancouverMulligan(int freeMulligans) {
        super(freeMulligans);
    }

    VancouverMulligan(final VancouverMulligan mulligan) {
        super(mulligan);
    }

    @Override
    public void executeMulliganPhase(Game game, int startingHandSize) {
        super.executeMulliganPhase(game, startingHandSize);
        /*
         * 103.4 (scry rule) - After all players have kept an opening hand, each player in
         * turn order whose hand contains fewer cards than that player’s starting hand size
         * may look at the top card of their library. If a player does, that player may put
         * that card on the bottom of their library.
         */
        for (UUID playerId : game.getState().getPlayerList(game.getStartingPlayerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.getHand().size() < startingHandSize) {
                player.scry(1, null, game);
            }
        }
    }

    @Override
    public VancouverMulligan copy() {
        return new VancouverMulligan(this);
    }
}
