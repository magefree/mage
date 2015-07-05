package org.mage.network.messages.callback;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

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
