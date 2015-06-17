package org.mage.network.interfaces;

import java.util.List;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import mage.game.match.MatchOptions;
import mage.interfaces.ServerState;
import mage.remote.Connection;
import mage.remote.DisconnectReason;
import mage.utils.MageVersion;
import mage.view.RoomView;
import mage.view.TableView;

/**
 *
 * @author BetaSteward
 */
public interface MageServer {
    
    boolean registerClient(Connection connection, String sessionId, MageVersion version, String host);
    void disconnect(String sessionId, DisconnectReason reason);

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
    UUID joinGame(final UUID gameId, final String sessionId);

    void pingTime(long milliSeconds, String sessionId);

}
