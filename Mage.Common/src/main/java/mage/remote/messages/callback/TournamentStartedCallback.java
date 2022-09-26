package mage.remote.messages.callback;

import java.util.UUID;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class TournamentStartedCallback extends ClientMessage {
    private final UUID tournamentId;

    public TournamentStartedCallback(UUID tournamentId, UUID tournamentId0) {
        this.tournamentId = tournamentId;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().tournamentStarted(tournamentId);
    }
    
}
