package mage.remote.messages.callback;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.view.GameView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

public class GameMultiAmountCallback extends ClientMessage {
    
    private final UUID gameId;
    private final GameView gameView;
    private final Map<String, Serializable> option;
    private final List<String> messages;
    private final int min;
    private final int max;
    
    public GameMultiAmountCallback(UUID gameId, GameView gameView, Map<String, Serializable> option, List<String> messages, int min, int max){
        this.gameId = gameId;
        this.gameView = gameView;
        this.option = option;
        this.messages = messages;
        this.min = min;
        this.max = max;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().gameMultiAmount(gameId, gameView, option, messages, min, max);
    }
}
