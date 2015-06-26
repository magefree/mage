package org.mage.network.messages.callback;

import java.util.UUID;
import mage.choices.Choice;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameChooseChoiceCallback extends ClientMessage {
    private final UUID gameId;
    private final Choice choice;

    public GameChooseChoiceCallback(UUID gameId, Choice choice) {
        this.gameId = gameId;
        this.choice = choice;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameChooseChoice(gameId, choice);
    }
    
}
