package mage.remote.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
import mage.remote.Connection;
import mage.remote.DisconnectReason;
import mage.utils.MageVersion;
import mage.view.DraftPickView;
import mage.view.RoomView;
import mage.view.TableView;
import mage.view.TournamentView;
import mage.view.UserView;

/**
 *
 * @author BetaSteward
 */
public interface MageServer {

    String registerUser(Connection connection, String sessionId, MageVersion version, String host);

    void disconnect(String sessionId, DisconnectReason reason);

    void setPreferences(String sessionId, UserData userData, String clientVersion, String userIdStr);

    void sendFeedbackMessage(String sessionId, String title, String type, String message, String email);

    void receiveChatMessage(UUID chatId, String sessionId, String message);

    void joinChat(UUID chatId, String sessionId);

    void leaveChat(UUID chatId, String sessionId);

    UUID getRoomChatId(String sessionId, UUID roomId);

    void receiveBroadcastMessage(String title, String message, String sessionId);

    ServerState getServerState();

    Object getServerMessages(String sessionId);

    List<String> getCards();

    List<CardInfo> getMissingCardData(List<String> cards);

    List<ExpansionInfo> getMissingExpansionData(List<String> setCodes);

    RoomView getRoom(UUID roomId);

    TableView createTable(String sessionId, UUID roomId, MatchOptions options);

    boolean joinTable(String sessionId, UUID roomId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password);

    TableView getTable(String sessionId, UUID roomId, UUID tableId);

    boolean leaveTable(String sessionId, UUID roomId, UUID tableId);

    void swapSeats(String sessionId, UUID roomId, UUID tableId, int seatNum1, int seatNum2);

    boolean joinTournamentTable(String sessionId, UUID roomId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password);

    boolean startMatch(String sessionId, UUID roomId, UUID tableId);

    void quitMatch(UUID gameId, String sessionId);

    UUID joinGame(UUID gameId, String sessionId);

    void sendPlayerUUID(UUID gameId, String sessionId, UUID data);

    void sendPlayerString(UUID gameId, String sessionId, String data);

    void sendPlayerManaType(UUID gameId, UUID playerId, String sessionId, ManaType data);

    void sendPlayerBoolean(UUID gameId, String sessionId, Boolean data);

    void sendPlayerInteger(UUID gameId, String sessionId, Integer data);

    void sendPlayerAction(PlayerAction playerAction, UUID gameId, String sessionId, Serializable data);

    boolean submitDeck(String sessionId, UUID tableId, DeckCardLists deckList);

    void updateDeck(String sessionId, UUID tableId, DeckCardLists deckList);

    void joinDraft(UUID draftId, String sessionId);

    void quitDraft(UUID draftId, String sessionId);
    
    void sendBroadcastMessage(String sessionId, String message);
    
    void disconnectUser(String sessionId, String userSessionId);
    
    void removeTable(String sessionId, UUID tableId);
    
    void endUserSession(String sessionId, String userSessionId);
    
    List<UserView> getUsers(String sessionId);

    void markCard(UUID draftId, String sessionId, UUID cardPick);
    
    void setBoosterLoaded(UUID draftId, String sessionId);

    DraftPickView pickCard(UUID draftId, String sessionId, UUID cardPick, Set<UUID> hiddenCards);

    TableView createTournamentTable(String sessionId, UUID roomId, TournamentOptions options);

    boolean startTournament(String sessionId, UUID roomId, UUID tableId);

    UUID getTournamentChatId(String sessionId, UUID tournamentId);

    TournamentView getTournament(String sessionId, UUID tournamentId);

    void joinTournament(UUID tournamentId, String sessionId);

    void quitTournament(UUID tournamentId, String sessionId);

    void watchTable(String sessionId, UUID roomId, UUID tableId);

    void watchTournamentTable(String sessionId, UUID tableId);

    void startReplay(UUID gameId, String sessionId);
    
    void stopWatching(UUID gameId, String sessionId);
    
    void stopReplay(UUID gameId, String sessionId);
    
    void nextPlay(UUID gameId, String sessionId);
    
    void previousPlay(UUID gameId, String sessionId);
    
    void skipForward(UUID gameId, String sessionId, int moves);

    void pingTime(long milliSeconds, String sessionId);

    void cheat(UUID gameId, String sessionId, UUID playerId, DeckCardLists deckList);
    
    String connectUser(final Connection connection, final String sessionId, MageVersion version, String host);
    
    boolean emailAuthToken(Connection connection);
    
    boolean resetPassword(Connection connection);
    
    UUID getMainRoomId(String sessionId);
    
    void muteUser(String sessionId, String userName, long durationMinutes) ;
    
    void setActivation(String sessionId, String userName, boolean active);
    
    void toggleActivation(String sessionId, String userName);
    
    void lockUser(String sessionId, String userName, long durationMinutes);
    
    List<TableView> getTables(String sessionId, UUID roomId);
    
    boolean ping(String sessionId, Connection connection);
    
    boolean watchGame(UUID gameId, String sessionId);
    
    void replayGame(UUID gameId, String sessionId);

}
