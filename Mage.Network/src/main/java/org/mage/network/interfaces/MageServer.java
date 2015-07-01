package org.mage.network.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.match.MatchOptions;
import mage.interfaces.ServerState;
import mage.remote.Connection;
import mage.remote.DisconnectReason;
import mage.utils.MageVersion;
import mage.view.RoomView;
import mage.view.TableView;
import mage.view.UserDataView;

/**
 *
 * @author BetaSteward
 */
public interface MageServer {
    
    boolean registerClient(Connection connection, String sessionId, MageVersion version, String host);
    void disconnect(String sessionId, DisconnectReason reason);
    void setPreferences(String sessionId, UserDataView userDataView);
    void sendFeedbackMessage(String sessionId, String title, String type, String message, String email);

    void receiveChatMessage(UUID chatId, String sessionId, String message);
    void joinChat(UUID chatId, String sessionId);
    void leaveChat(UUID chatId, String sessionId);
    UUID getRoomChatId(UUID roomId);
    void receiveBroadcastMessage(String title, String message, String sessionId);
    
    ServerState getServerState();

    List<String> getServerMessages();
    RoomView getRoom(UUID roomId);
    TableView createTable(String sessionId, UUID roomId, MatchOptions options);
    boolean joinTable(String sessionId, UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password);
    TableView getTable(UUID roomId, UUID tableId);
    boolean leaveTable(String sessionId, UUID roomId, UUID tableId);
    void removeTable(String sessionId, UUID roomId, UUID tableId);
    void swapSeats(String sessionId, UUID roomId, UUID tableId, int seatNum1, int seatNum2);

    boolean startMatch(String sessionId, UUID roomId, UUID tableId);
    UUID joinGame(UUID gameId, String sessionId);
    void sendPlayerUUID(UUID gameId, String sessionId, UUID data);
    void sendPlayerString(UUID gameId, String sessionId, String data);
    void sendPlayerManaType(UUID gameId, UUID playerId, String sessionId, ManaType data);
    void sendPlayerBoolean(UUID gameId, String sessionId, Boolean data);
    void sendPlayerInteger(UUID gameId, String sessionId, Integer data);
    void sendPlayerAction(PlayerAction playerAction, UUID gameId, String sessionId, Serializable data);
    
    boolean submitDeck(String sessionId, UUID tableId, DeckCardLists deckList);
    void updateDeck(String sessionId, UUID tableId, DeckCardLists deckList);

    void pingTime(long milliSeconds, String sessionId);

}
