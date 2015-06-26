package org.mage.network.messages;

import java.util.UUID;
import mage.view.AbilityPickerView;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class GameChooseAbilityMessage extends ClientMessage {
    private final UUID gameId;
    private final AbilityPickerView abilities;

    public GameChooseAbilityMessage(UUID gameId, AbilityPickerView abilities) {
        this.gameId = gameId;
        this.abilities = abilities;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameChooseAbility(gameId, abilities);
    }
    
}
