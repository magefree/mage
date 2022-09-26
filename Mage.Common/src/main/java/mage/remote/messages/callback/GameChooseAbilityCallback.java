package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.AbilityPickerView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;
import mage.view.GameView;

/**
 *
 * @author BetaSteward
 */
public class GameChooseAbilityCallback extends ClientMessage {
    private final UUID gameId;
    private final GameView gameView;
    private final AbilityPickerView abilities;

    public GameChooseAbilityCallback(UUID gameId, GameView gameView, AbilityPickerView abilities) {
        this.gameId = gameId;
        this.gameView = gameView;
        this.abilities = abilities;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameChooseAbility(gameId, gameView, abilities);
    }
    
}
