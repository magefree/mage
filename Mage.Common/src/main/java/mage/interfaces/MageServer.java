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
 * Network: server side commands to process from a client
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public interface MageServer {

    // registers a user to the user DB.
    boolean authRegister(String sessionId, String userName, String password, String email) throws MageException;

    boolean authSendTokenToEmail(String sessionId, String email) throws MageException;

    boolean authResetPassword(String sessionId, String email, String authToken, String password) throws MageException;

    boolean connectUser(String userName, String password, String sessionId, MageVersion version, String userIdStr) throws MageException;

    boolean connectAdmin(String password, String sessionId, MageVersion version) throws MageException;

    boolean connectSetUserData(String userName, String sessionId, UserData userData, String clientVersion, String userIdStr) throws MageException;

    boolean ping(String sessionId, String pingInfo) throws MageException;
    
    void serverAddFeedbackMessage(String sessionId, String username, String title, String type, String message, String email) throws MageException;

    Object serverGetPromotionMessages(String sessionId) throws MageException;

    ServerState getServerState() throws MageException; // TODO: need stable update process, so rename it after few releases

    // TODO: miss session
    UUID serverGetMainRoomId() throws MageException;

    // TODO: miss session
    List<RoomUsersView> roomGetUsers(UUID roomId) throws MageException;

    // TODO: miss session
    List<MatchView> roomGetFinishedMatches(UUID roomId) throws MageException;

    TableView roomCreateTable(String sessionId, UUID roomId, MatchOptions matchOptions) throws MageException;

    TableView roomCreateTournament(String sessionId, UUID roomId, TournamentOptions tournamentOptions) throws MageException;

    boolean roomJoinTable(String sessionId, UUID roomId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws MageException, GameException;

    boolean roomJoinTournament(String sessionId, UUID roomId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws MageException, GameException;

    boolean deckSubmit(String sessionId, UUID tableId, DeckCardLists deckList) throws MageException, GameException;

    void deckSave(String sessionId, UUID tableId, DeckCardLists deckList) throws MageException, GameException;

    boolean roomWatchTable(String sessionId, UUID roomId, UUID tableId) throws MageException;

    boolean roomWatchTournament(String sessionId, UUID tableId) throws MageException;

    boolean roomLeaveTableOrTournament(String sessionId, UUID roomId, UUID tableId) throws MageException;

    void tableSwapSeats(String sessionId, UUID roomId, UUID tableId, int seatNum1, int seatNum2) throws MageException;

    void tableRemove(String sessionId, UUID roomId, UUID tableId) throws MageException;

    boolean tableIsOwner(String sessionId, UUID roomId, UUID tableId) throws MageException;

    // TODO: miss session
    TableView roomGetTableById(UUID roomId, UUID tableId) throws MageException;

    // TODO: miss session
    List<TableView> roomGetAllTables(UUID roomId) throws MageException;

    // TODO: miss session
    void chatSendMessage(UUID chatId, String userName, String message) throws MageException;

    void chatJoin(UUID chatId, String sessionId, String userName) throws MageException;

    void chatLeave(UUID chatId, String sessionId) throws MageException;

    // TODO: miss session
    UUID chatFindByGame(UUID gameId) throws MageException;

    // TODO: miss session
    UUID chatFindByTable(UUID tableId) throws MageException;

    // TODO: miss session
    UUID chatFindByTournament(UUID tournamentId) throws MageException;

    // TODO: miss session
    UUID chatFindByRoom(UUID roomId) throws MageException;

    boolean matchStart(String sessionId, UUID roomId, UUID tableId) throws MageException;

    void matchQuit(UUID gameId, String sessionId) throws MageException;

    void gameJoin(UUID gameId, String sessionId) throws MageException;

    @Deprecated // TODO: implement GameView request on miss client side data, e.g. on reconnect (empty player panels bug)?
    GameView gameGetView(UUID gameId, String sessionId, UUID playerId) throws MageException;

    boolean gameWatchStart(UUID gameId, String sessionId) throws MageException;

    void gameWatchStop(UUID gameId, String sessionId) throws MageException;

    void sendPlayerUUID(UUID gameId, String sessionId, UUID data) throws MageException;

    void sendPlayerString(UUID gameId, String sessionId, String data) throws MageException;

    void sendPlayerBoolean(UUID gameId, String sessionId, Boolean data) throws MageException;

    void sendPlayerInteger(UUID gameId, String sessionId, Integer data) throws MageException;

    void sendPlayerManaType(UUID gameId, UUID playerId, String sessionId, ManaType data) throws MageException;

    // priority, undo, concede, mana pool
    void sendPlayerAction(PlayerAction playerAction, UUID gameId, String sessionId, Object data) throws MageException;

    DraftPickView sendDraftCardPick(UUID draftId, String sessionId, UUID cardId, Set<UUID> hiddenCards) throws MageException;

    void sendDraftCardMark(UUID draftId, String sessionId, UUID cardId) throws MageException;

    boolean tournamentStart(String sessionId, UUID roomId, UUID tableId) throws MageException;

    void tournamentJoin(UUID draftId, String sessionId) throws MageException;

    void tournamentQuit(UUID tournamentId, String sessionId) throws MageException;

    // TODO: miss session
    TournamentView tournamentFindById(UUID tournamentId) throws MageException;

    void draftJoin(UUID draftId, String sessionId) throws MageException;

    void draftQuit(UUID draftId, String sessionId) throws MageException;

    void draftSetBoosterLoaded(UUID draftId, String sessionId) throws MageException;

    void replayInit(UUID gameId, String sessionId) throws MageException;

    void replayStart(UUID gameId, String sessionId) throws MageException;

    void replayStop(UUID gameId, String sessionId) throws MageException;

    void replayNext(UUID gameId, String sessionId) throws MageException;

    void replayPrevious(UUID gameId, String sessionId) throws MageException;

    void replaySkipForward(UUID gameId, String sessionId, int moves) throws MageException;

    void cheatShow(UUID gameId, String sessionId, UUID playerId) throws MageException;

    List<UserView> adminGetUsers(String sessionId) throws MageException;

    void adminDisconnectUser(String sessionId, String userSessionId) throws MageException;

    void adminEndUserSession(String sessionId, String userSessionId) throws MageException;

    void adminMuteUser(String sessionId, String userName, long durationMinutes) throws MageException;

    void adminLockUser(String sessionId, String userName, long durationMinutes) throws MageException;

    void adminActivateUser(String sessionId, String userName, boolean active) throws MageException;
    
    void adminToggleActivateUser(String sessionId, String userName) throws MageException;

    void adminTableRemove(String sessionId, UUID tableId) throws MageException;

    void adminSendBroadcastMessage(String sessionId, String message) throws MageException;
}
