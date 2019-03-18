package mage.game.mulligan;

import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.*;

public abstract class Mulligan {

    protected final int freeMulligans;
    protected final Map<UUID, Integer> usedFreeMulligans = new HashMap<>();

    public Mulligan(int freeMulligans) {
        this.freeMulligans = freeMulligans;
    }

    public void executeMulliganPhase(Game game, int startingHandSize) {
        List<UUID> keepPlayers = new ArrayList<>();
        List<UUID> mulliganPlayers = new ArrayList<>();
        do {
            mulliganPlayers.clear();
            for (UUID playerId : game.getState().getPlayerList(game.getStartingPlayerId())) {
                if (!keepPlayers.contains(playerId)) {
                    Player player = game.getPlayer(playerId);
                    boolean keep = true;
                    while (true) {
                        if (player.getHand().isEmpty()) {
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

    public int getFreeMulligans() {
        return freeMulligans;
    }

}
