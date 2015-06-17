package org.mage.network.interfaces;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.choices.Choice;
import mage.game.Table;
import mage.interfaces.ServerState;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.ChatMessage;
import mage.view.GameEndView;
import mage.view.GameView;
import mage.view.UserRequestMessage;
import org.mage.network.model.MessageType;

/**
 *
 * @author BetaSteward
 */
public interface MageClient {
    
    void connected(String message);
    void disconnected(boolean error);
    
    void inform(String title, String message, MessageType type);
    
    void receiveChatMessage(UUID chatId, ChatMessage message);
    void receiveBroadcastMessage(String message);

    void clientRegistered(ServerState state);
    ServerState getServerState();
    
    void joinedTable(UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament);
    void gameStarted(UUID gameId, UUID playerId);

    void initGame(UUID gameId, GameView gameView);

    void gameAsk(UUID gameId, GameView gameView, String question);
    void gameTarget(UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options);
    void gameChooseAbility(UUID gameId, AbilityPickerView abilities);
    void gameChoosePile(UUID gameId, String message, CardsView pile1, CardsView pile2);
    void gameChooseChoice(UUID gameId, Choice choice);
    void gamePlayMana(UUID gameId, GameView gameView, String message);
    void gamePlayXMana(UUID gameId, GameView gameView, String message);
    void gameSelectAmount(UUID gameId, String message, int min, int max);
    void gameSelect(UUID gameId, GameView gameView, String message, Map<String, Serializable> options);

    public void gameEndInfo(UUID gameId, GameEndView view);

    public void userRequestDialog(UUID gameId, UserRequestMessage userRequestMessage);
    
}
