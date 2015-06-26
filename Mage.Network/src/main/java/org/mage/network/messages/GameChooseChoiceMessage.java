package org.mage.network.messages;

import java.util.UUID;
import mage.choices.Choice;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GameChooseChoiceMessage extends ClientMessage {
    private final UUID gameId;
    private final Choice choice;

    public GameChooseChoiceMessage(UUID gameId, Choice choice) {
        this.gameId = gameId;
        this.choice = choice;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameChooseChoice(gameId, choice);
    }
    
}
