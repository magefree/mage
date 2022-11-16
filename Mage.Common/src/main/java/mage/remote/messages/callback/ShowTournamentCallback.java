package mage.remote.messages.callback;

import java.util.UUID;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class ShowTournamentCallback extends ClientMessage {
    private final UUID tournamentId;

    public ShowTournamentCallback(UUID tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().showTournament(tournamentId);
    }
    
}
