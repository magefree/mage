package org.mage.network.messages;

import java.util.UUID;
import mage.view.CardsView;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GameChoosePileMessage extends ClientMessage {
    private final UUID gameId;
    private final String message;
    private final CardsView pile1;
    private final CardsView pile2;

    public GameChoosePileMessage(UUID gameId, String message, CardsView pile1, CardsView pile2) {
        this.gameId = gameId;
        this.message = message;
        this.pile1 = pile1;
        this.pile2 = pile2;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameChoosePile(gameId, message, pile1, pile2);
    }
    
}
