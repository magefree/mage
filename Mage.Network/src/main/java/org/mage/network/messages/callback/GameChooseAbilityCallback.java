package org.mage.network.messages.callback;

import java.util.UUID;
import mage.view.AbilityPickerView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GameChooseAbilityCallback extends ClientMessage {
    private final UUID gameId;
    private final AbilityPickerView abilities;

    public GameChooseAbilityCallback(UUID gameId, AbilityPickerView abilities) {
        this.gameId = gameId;
        this.abilities = abilities;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameChooseAbility(gameId, abilities);
    }
    
}
