package mage.game.mulligan;

import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.io.Serializable;
import java.util.*;

public abstract class Mulligan implements Serializable {

    protected final int freeMulligans;
    protected final Map<UUID, Integer> usedFreeMulligans = new HashMap<>();

    public Mulligan(int freeMulligans) {
        this.freeMulligans = freeMulligans;
    }

    Mulligan(final Mulligan mulligan) {
        super();
        this.freeMulligans = mulligan.freeMulligans;
        this.usedFreeMulligans.putAll(mulligan.usedFreeMulligans);
    }

    public void executeMulliganPhase(Game game, int startingHandSize) {
        /*
         * 103.4. Each player draws a number of cards equal to their starting hand size,
         * which is normally seven. (Some effects can modify a player’s starting hand size.)
         * A player who is dissatisfied with their initial hand may take a mulligan. First
         * the starting player declares whether they will take a mulligan. Then each other
         * player in turn order does the same. Once each player has made a declaration, all
         * players who decided to take mulligans do so at the same time. To take a mulligan,
         * a player shuffles their hand back into their library, then draws a new hand of one
         * fewer cards than they had before. If a player kept their hand of cards, those cards
         * become the player’s opening hand, and that player may not take any further mulligans.
         * This process is then repeated until no player takes a mulligan. (Note that if a
         * player’s hand size reaches zero cards, that player must keep that hand.)
         */
        List<UUID> keepPlayers = new ArrayList<>();
        List<UUID> mulliganPlayers = new ArrayList<>();
        do {
            mulliganPlayers.clear();
            for (UUID playerId : game.getState().getPlayerList(game.getStartingPlayerId())) {
                if (!keepPlayers.contains(playerId)) {
                    Player player = game.getPlayer(playerId);
                    boolean keep = true;
                    while (true) {
                        if (!canTakeMulligan(game, player)) {
                            break;
                        }
                        GameEvent event = new GameEvent(GameEvent.EventType.CAN_TAKE_MULLIGAN, null, null, playerId);
                        if (!game.replaceEvent(event)) {
                            game.fireEvent(event);
                            game.getState().setChoosingPlayerId(playerId);
                            if (player.chooseMulligan(game)) {
                                keep = false;
                            }
                            break;
                        }
                    }
                    if (keep) {
                        game.endMulligan(player.getId());
                        keepPlayers.add(playerId);
                        game.fireInformEvent(player.getLogName() + " keeps hand");
                    } else {
                        mulliganPlayers.add(playerId);
                        game.fireInformEvent(player.getLogName() + " decides to take mulligan");
                    }
                }
            }
            for (UUID mulliganPlayerId : mulliganPlayers) {
                mulligan(game, mulliganPlayerId);
            }
            game.saveState(false);
        } while (!mulliganPlayers.isEmpty());
    }

    public abstract int mulliganDownTo(Game game, UUID playerId);

    public abstract void mulligan(Game game, UUID playerId);

    public abstract void endMulligan(Game game, UUID playerId);

    public abstract Mulligan copy();

    public boolean canTakeMulligan(Game game, Player player) {
        return !player.getHand().isEmpty();
    }

    public int getFreeMulligans() {
        return freeMulligans;
    }

    public void drawHand(int numCards, Player player, Game game){
        player.drawCards(numCards, null, game);
    }
}
