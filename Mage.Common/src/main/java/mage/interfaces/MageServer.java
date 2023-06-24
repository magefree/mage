
package mage.interfaces;

import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.ExpansionInfo;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.GameException;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.players.PlayerType;
import mage.players.net.UserData;
import mage.utils.MageVersion;
import mage.view.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface MageServer {

    // registers a user to the user DB.
    boolean registerUser(String sessionId, String userName, String password, String email) throws MageException;

    boolean emailAuthToken(String sessionId, String email) throws MageException;

    boolean resetPassword(String sessionId, String email, String authToken, String password) throws MageException;

    boolean connectUser(String userName, String password, String sessionId, MageVersion version, String userIdStr) throws MageException;

    boolean connectAdmin(String password, String sessionId, MageVersion version) throws MageException;

    // update methods
    List<ExpansionInfo> getMissingExpansionData(List<String> codes);

    List<CardInfo> getMissingCardsData(List<String> classNames);

    // user methods
    boolean setUserData(String userName, String sessionId, UserData userData, String clientVersion, String userIdStr) throws MageException;

    void sendFeedbackMessage(String sessionId, String username, String title, String type, String message, String email) throws MageException;

    // server state methods
    ServerState getServerState() throws MageException;

    List<RoomUsersView> getRoomUsers(UUID roomId) throws MageException;

    List<MatchView> getFinishedMatches(UUID roomId) throws MageException;

    Object getServerMessagesCompressed(String sessionId) throws MageException;     // messages of the day

    // ping - extends session
    boolean ping(String sessionId, String pingInfo) throws MageException;

    //table methods
    TableView createTable(String sessionId, UUID roomId, MatchOptions matchOptions) throws MageException;

    TableView createTournamentTable(String sessionId, UUID roomId, TournamentOptions tournamentOptions) throws MageException;

    boolean joinTable(String sessionId, UUID roomId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws MageException, GameException;

    boolean joinTournamentTable(String sessionId, UUID roomId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws MageException, GameException;

    boolean submitDeck(String sessionId, UUID tableId, DeckCardLists deckList) throws MageException, GameException;

    void updateDeck(String sessionId, UUID tableId, DeckCardLists deckList) throws MageException, GameException;

    boolean watchTable(String sessionId, UUID roomId, UUID tableId) throws MageException;

    boolean watchTournamentTable(String sessionId, UUID tableId) throws MageException;

    boolean leaveTable(String sessionId, UUID roomId, UUID tableId) throws MageException;

    void swapSeats(String sessionId, UUID roomId, UUID tableId, int seatNum1, int seatNum2) throws MageException;

    void removeTable(String sessionId, UUID roomId, UUID tableId) throws MageException;

    boolean isTableOwner(String sessionId, UUID roomId, UUID tableId) throws MageException;

    TableView getTable(UUID roomId, UUID tableId) throws MageException;

    List<TableView> getTables(UUID roomId) throws MageException;

    //chat methods
    void sendChatMessage(UUID chatId, String userName, String message) throws MageException;

    void joinChat(UUID chatId, String sessionId, String userName) throws MageException;

    void leaveChat(UUID chatId, String sessionId) throws MageException;

    UUID getTableChatId(UUID tableId) throws MageException;

    UUID getGameChatId(UUID gameId) throws MageException;

    UUID getRoomChatId(UUID roomId) throws MageException;

    UUID getTournamentChatId(UUID tournamentId) throws MageException;

    //room methods
    UUID getMainRoomId() throws MageException;

    //game methods
    boolean startMatch(String sessionId, UUID roomId, UUID tableId) throws MageException;

    void joinGame(UUID gameId, String sessionId) throws MageException;

    boolean watchGame(UUID gameId, String sessionId) throws MageException;

    void stopWatching(UUID gameId, String sessionId) throws MageException;

    void sendPlayerUUID(UUID gameId, String sessionId, UUID data) throws MageException;

    void sendPlayerString(UUID gameId, String sessionId, String data) throws MageException;

    void sendPlayerBoolean(UUID gameId, String sessionId, Boolean data) throws MageException;

    void sendPlayerInteger(UUID gameId, String sessionId, Integer data) throws MageException;

    void sendPlayerManaType(UUID gameId, UUID playerId, String sessionId, ManaType data) throws MageException;

    void quitMatch(UUID gameId, String sessionId) throws MageException;

    GameView getGameView(UUID gameId, String sessionId, UUID playerId) throws MageException;

    // priority, undo, concede, mana pool
    void sendPlayerAction(PlayerAction playerAction, UUID gameId, String sessionId, Object data) throws MageException;

    //tournament methods
    boolean startTournament(String sessionId, UUID roomId, UUID tableId) throws MageException;

    void joinTournament(UUID draftId, String sessionId) throws MageException;

    void quitTournament(UUID tournamentId, String sessionId) throws MageException;

    TournamentView getTournament(UUID tournamentId) throws MageException;

    //draft methods
    void joinDraft(UUID draftId, String sessionId) throws MageException;

    void quitDraft(UUID draftId, String sessionId) throws MageException;

    DraftPickView sendCardPick(UUID draftId, String sessionId, UUID cardId, Set<UUID> hiddenCards) throws MageException;

    void sendCardMark(UUID draftId, String sessionId, UUID cardId) throws MageException;

    void setBoosterLoaded(UUID draftId, String sessionId) throws MageException;

    //challenge methods
    // void startChallenge(String sessionId, UUID roomId, UUID tableId, UUID challengeId) throws MageException;
    //replay methods
    void replayGame(UUID gameId, String sessionId) throws MageException;

    void startReplay(UUID gameId, String sessionId) throws MageException;

    void stopReplay(UUID gameId, String sessionId) throws MageException;

    void nextPlay(UUID gameId, String sessionId) throws MageException;

    void previousPlay(UUID gameId, String sessionId) throws MageException;

    void skipForward(UUID gameId, String sessionId, int moves) throws MageException;

    //test methods
    void cheat(UUID gameId, String sessionId, UUID playerId, DeckCardLists deckList) throws MageException;

    boolean cheat(UUID gameId, String sessionId, UUID playerId, String cardName) throws MageException;

    //admin methods
    List<UserView> getUsers(String sessionId) throws MageException;

    void disconnectUser(String sessionId, String userSessionId) throws MageException;

    void endUserSession(String sessionId, String userSessionId) throws MageException;

    void muteUser(String sessionId, String userName, long durationMinutes) throws MageException;

    void lockUser(String sessionId, String userName, long durationMinutes) throws MageException;

    void setActivation(String sessionId, String userName, boolean active) throws MageException;
    
    void toggleActivation(String sessionId, String userName) throws MageException;

    void removeTable(String sessionId, UUID tableId) throws MageException;

    void sendBroadcastMessage(String sessionId, String message) throws MageException;
}
