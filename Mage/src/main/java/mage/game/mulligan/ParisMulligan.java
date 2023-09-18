package mage.game.mulligan;

import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public class ParisMulligan extends Mulligan {

    public ParisMulligan(int freeMulligans) {
        super(freeMulligans);
    }

    ParisMulligan(final ParisMulligan mulligan) {
        super(mulligan);
    }

    @Override
    public int mulliganDownTo(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        int deduction = 1;
        if (freeMulligans > 0) {
            if (usedFreeMulligans != null && usedFreeMulligans.containsKey(player.getId())) {
                int used = usedFreeMulligans.get(player.getId());
                if (used < freeMulligans) {
                    deduction = 0;
                }
            } else {
                deduction = 0;
            }
        }
        return player.getHand().size() - deduction;
    }

    @Override
    public void mulligan(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        int numCards = player.getHand().size();
        player.getLibrary().addAll(player.getHand().getCards(game), game);
        player.getHand().clear();
        player.shuffleLibrary(null, game);
        int deduction = 1;
        if (freeMulligans > 0) {
            if (usedFreeMulligans.containsKey(player.getId())) {
                int used = usedFreeMulligans.get(player.getId());
                if (used < freeMulligans) {
                    deduction = 0;
                    usedFreeMulligans.put(player.getId(), used + 1);
                }
            } else {
                deduction = 0;
                usedFreeMulligans.put(player.getId(), 1);
            }
        }
        game.fireInformEvent(new StringBuilder(player.getLogName())
                .append(" mulligans")
                .append(deduction == 0 ? " for free and draws " : " down to ")
                .append((numCards - deduction))
                .append(numCards - deduction == 1 ? " card" : " cards").toString());
        drawHand(numCards - deduction, player, game);
    }

    @Override
    public void endMulligan(Game game, UUID playerId) {
    }

    @Override
    public ParisMulligan copy() {
        return new ParisMulligan(this);
    }
}
