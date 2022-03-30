
package mage.remote;

import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.ExpansionInfo;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.ServerState;
import mage.players.PlayerType;
import mage.players.net.UserData;
import mage.utils.MageVersion;
import mage.view.DraftPickView;
import mage.view.RoomView;
import mage.view.TableView;
import mage.view.TournamentView;
import mage.view.UserView;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Extracted interface for SessionImpl class.
 *
 * @author noxx
 */
public interface Session {
    
    Optional<UUID> getRoomChatId(UUID roomId);

    Optional<UUID> getTournamentChatId(UUID tournamentId);

    void joinChat(UUID chatId);

    void leaveChat(UUID chatId);

    void sendChatMessage(UUID chatId, String message);

    void sendBroadcastMessage(String message);
    
    RoomView getRoom(UUID roomId);
    
    String getUserName();
    
    void setPreferences(UserData userData);
    
    boolean register(Connection connection, MageVersion version);

    boolean emailAuthToken(Connection connection);

    boolean resetPassword(Connection connection);

    boolean connect(Connection connection, MageVersion version);

    void disconnect(boolean showMessage);

    boolean ping(Connection connection);

    boolean isConnected();

    Optional<String> getServerHostname();

    void disconnectUser(String userSessionId);

    void endUserSession(String userSessionId);

    void muteUser(String userName, long durationMinute);

    void setActivation(String userName, boolean active);

    void toggleActivation(String userName);

    void lockUser(String userName, long durationMinute);

    String getSessionId();
    
    ServerState getServerState();
    
    Object getServerMessages();
    
    boolean sendFeedback(String title, String type, String message, String email);
    
    boolean startMatch(UUID roomId, UUID tableId);

    boolean watchGame(UUID gameId);

    void stopWatching(UUID gameId);

    void sendPlayerUUID(UUID gameId, UUID data);

    void sendPlayerBoolean(UUID gameId, boolean data);

    void sendPlayerInteger(UUID gameId, int data);

    void sendPlayerString(UUID gameId, String data);

    void sendPlayerManaType(UUID gameId, UUID playerId, ManaType data);

    void quitMatch(UUID gameId);

    void quitTournament(UUID tournamentId);

    void quitDraft(UUID draftId);

    boolean submitDeck(UUID tableId, DeckCardLists deck);

    void updateDeck(UUID tableId, DeckCardLists deck);

    DraftPickView pickCard(UUID draftId, UUID cardId, Set<UUID> hiddenCards);
    void markCard(UUID draftId, UUID cardId);

    /**
     * magenoxx:
     *   it should be done separately as sendPlayer* methods calls are injected into the game flow
     *   - this is similar to concedeGame method
     * 
     * This method sends player actions for a game
     * priority handling, undo
     *
     * @param passPriorityAction
     * @param gameId
     * @param Data
     */
    void sendPlayerAction(PlayerAction passPriorityAction, UUID gameId, Serializable Data);
    
    UUID getMainRoomId();
    
    List<ExpansionInfo> getMissingExpansionsData(List<String> setCodes);
    
    List<CardInfo> getMissingCardsData(List<String> cards);
    
    List<String> getCards();
    
    TableView createTable(UUID roomId, MatchOptions matchOptions);

    TableView createTournamentTable(UUID roomId, TournamentOptions tournamentOptions);

    void removeTable(UUID tableId);

    Optional<UUID> joinGame(UUID gameId);

    void joinDraft(UUID draftId);

    void joinTournament(UUID tournamentId);

    boolean leaveTable(UUID roomId, UUID tableId);

    void swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2);

    boolean startTournament(UUID roomId, UUID tableId);

    boolean joinTournamentTable(UUID roomId, UUID tableId, String playerName, PlayerType playerType, int skill, DeckCardLists deckList, String password);

    void watchTable(UUID roomId, UUID tableId);

    void watchTournamentTable(UUID tableId);

    boolean joinTable(UUID roomId, UUID tableId, String playerName, PlayerType playerType, int skill, DeckCardLists deckList, String password);

    Optional<TableView> getTable(UUID roomId, UUID tableId);
    
    List<TableView> getTables(UUID roomId);

    TournamentView getTournament(UUID tournamentId) throws MageRemoteException;
    
    List<UserView> getUsers();
    
    void replayGame(UUID gameId);

    boolean startReplay(UUID gameId);

    void stopReplay(UUID gameId);

    void nextPlay(UUID gameId);

    void previousPlay(UUID gameId);

    void skipForward(UUID gameId, int moves);
    
    void cheat(UUID gameId, UUID playerId, DeckCardLists deckList);

}
