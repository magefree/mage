package org.mage.network.messages.callback;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

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
