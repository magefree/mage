package mage.remote.messages.responses;

import mage.view.TournamentView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

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
