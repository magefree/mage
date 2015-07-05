package org.mage.network.messages.responses;

import mage.view.TournamentView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GetTournamentResponse extends ClientMessage {
    private final TournamentView tournament;

    public GetTournamentResponse(TournamentView tournament) {
        this.tournament = tournament;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveTournamentView(tournament);
    }
    
}
