package mage.client;

import mage.cards.decks.DeckCardLists;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.players.PlayerType;
import mage.players.net.UserData;
import mage.remote.Connection;
import mage.remote.MageRemoteException;
import mage.remote.Session;
import mage.remote.SessionImpl;
import mage.view.*;
import mage.cards.repository.CardInfo;
import mage.cards.repository.ExpansionInfo;
import mage.utils.CompressUtil;
import mage.utils.MageVersion;
import org.apache.log4j.Logger;

import java.util.*;
import java.io.Serializable;

/**
 * Created by IGOUDT on 15-9-2016.
 */
public final class SessionHandler {


    private static final Logger logger = Logger.getLogger(SessionHandler.class);
    
    private static final MageVersion VERSION = new MageVersion(SessionHandler.class);

    private static Session session;
    private static String lastConnectError = "";

    private SessionHandler(){
    }

    public static void startSession(MageFrame mageFrame) {

        session = new SessionImpl(mageFrame);
    }

    public static void ping() {
        session.ping(new Connection());
    }

    public static Session getSession() {
        return session;
    }

    public static boolean isConnected() {
        if(session == null){
            return false;
        }
        return session.isConnected();
    }

    public static boolean connect(Connection connection) {
        return session.connect(connection, VERSION);
    }

    public static String getLastConnectError() {
        return lastConnectError;
    }

    public static void disconnect(boolean showmessage) {
        session.disconnect(showmessage);
    }

    public static void sendPlayerAction(PlayerAction playerAction, UUID gameId, Serializable relatedUserId) {
        session.sendPlayerAction(playerAction, gameId, relatedUserId);
    }

    public static void quitTournament(UUID tournamentId) {
        session.quitTournament(tournamentId);
    }

    public static void quitDraft(UUID tournamentId) {
        session.quitDraft(tournamentId);
    }

    public static void quitMatch(UUID gameId) {
        session.quitMatch(gameId);
    }

    public static void stopWatching(UUID gameId) {
        session.stopWatching(gameId);
    }

    public static void removeTable(UUID roomId) {
        session.removeTable(roomId);
    }

    public static void stopReplay(UUID gameId) {
        session.stopReplay(gameId);
    }

    public static void sendPlayerUUID(UUID gameId, UUID id) {
        session.sendPlayerUUID(gameId, id);
    }

    public static void sendPlayerBoolean(UUID gameId, boolean b) {
        session.sendPlayerBoolean(gameId, b);
    }

    public static PlayerType[] getPlayerTypes() {
        return session.getServerState().getPlayerTypes();
    }

    public static boolean joinTournamentTable(UUID roomId, UUID tableId, String text, PlayerType selectedItem, Integer integer, DeckCardLists deckCardLists, String s) {
        return session.joinTournamentTable(roomId, tableId, text, selectedItem, integer, deckCardLists, s);
    }

    public static void sendPlayerInteger(UUID gameId, int data) {
        session.sendPlayerInteger(gameId, data);
    }

    public static void sendPlayerString(UUID gameId, String data) {
        session.sendPlayerString(gameId, data);
    }

    public static boolean sendFeedback(String title, String type, String message, String email) {
        return session.sendFeedback(title, type, message, email);
    }

    public static void swapSeats(UUID roomId, UUID tableId, int row, int targetrow) {
        session.swapSeats(roomId, tableId, row, targetrow);
    }

    public static boolean leaveTable(UUID roomId, UUID tableId) {
        return session.leaveTable(roomId, tableId);
    }
    
    public static void setPreferences(UserData userData) {
        if(isConnected()){
            session.setPreferences(userData);
        }
    }

    public static boolean startTournament(UUID roomId, UUID tableId) {
        return session.startTournament(roomId, tableId);
    }

    public static boolean startMatch(UUID roomId, UUID tableId) {
        return session.startMatch(roomId, tableId);
    }

    public static Optional<UUID> joinGame(UUID gameId) {
        return session.joinGame(gameId);
    }

    public static boolean startReplay(UUID gameId) {
        return session.startReplay(gameId);
    }

    public static void watchTournamentTable(UUID tableId) {
        session.watchTournamentTable(tableId);
    }

    public static void joinTournament(UUID tournamentId) {
        session.joinTournament(tournamentId);
    }

    public static Optional<UUID> getTournamentChatId(UUID tournamentId) {
        return session.getTournamentChatId(tournamentId);
    }

    public static TournamentView getTournament(UUID tournamentId) {
        try {
            return session.getTournament(tournamentId);
        } catch (MageRemoteException e) {
            logger.info(e);
            return null;
        }

    }

    public static String getUserName() {
        return session.getUserName();
    }

    public static boolean watchGame(UUID gameId) {
        return session.watchGame(gameId);
    }

    public static void nextPlay(UUID gameId) {
        session.nextPlay(gameId);
    }

    public static void previousPlay(UUID gameId) {
        session.previousPlay(gameId);
    }

    public static void skipForward(UUID gameId, int i) {
        session.skipForward(gameId, i);
    }

    public static boolean isTestMode() {
        return session.getServerState().isTestMode();
    }

    public static void cheat(UUID gameId, UUID playerId, DeckCardLists deckCardLists) {
        session.cheat(gameId, playerId, deckCardLists);
    }

    public static String getSessionId() {
        return session.getSessionId();
    }

    public static List<TournamentTypeView> getTournamentTypes() {
        return session.getServerState().getTournamentTypes();
    }

    public static boolean submitDeck(UUID tableId, DeckCardLists deckCardLists) {
        return session.submitDeck(tableId, deckCardLists);
    }

    public static String[] getDeckTypes() {
        return session.getServerState().getDeckTypes();
    }

    public static String[] getDraftCubes() {
        return session.getServerState().getDraftCubes();
    }

    public static List<GameTypeView> getTournamentGameTypes() {
        return session.getServerState().getTournamentGameTypes();
    }
    
    public static TableView createTournamentTable(UUID roomId, TournamentOptions tOptions) {
        return session.createTournamentTable(roomId, tOptions);
    }

    public static TableView createTable(UUID roomId, MatchOptions options) {
        return session.createTable(roomId, options);
    }

    public static boolean joinTable(UUID roomId, UUID tableId, String playerName, PlayerType human, int skill, DeckCardLists deckCardLists, String text) {
        return session.joinTable(roomId, tableId, playerName, human, skill, deckCardLists, text);
    }

    public static List<GameTypeView> getGameTypes() {
        return session.getServerState().getGameTypes();
    }
    
    public static void joinDraft(UUID draftId) {
        session.joinDraft(draftId);
    }
    
    public static DraftPickView pickCard(UUID draftId, UUID cardId, Set<UUID> cardsHidden){
        return session.pickCard(draftId, cardId, cardsHidden);
    }
    
    public static void markCard(UUID draftId, UUID cardId) {
        session.markCard(draftId, cardId);
    }
    
    public static void setBoosterLoaded(UUID draftId) {
        session.setBoosterLoaded(draftId);
    }
    
    public static Optional<UUID> getRoomChatId(UUID roomId) {
        return session.getRoomChatId(roomId);
    }

    public static void replayGame(UUID id) {
        session.replayGame(id);
    }

    public static void watchTable(UUID roomId, UUID tableId) {
        session.watchTable(roomId, tableId);
    }

    public static Collection<TableView> getTables(UUID roomId) {
        return session.getTables(roomId);
    }

    public static List<String> getServerMessages() {
        return (List<String>) CompressUtil.decompress(session.getServerMessages());
    }

    public static void joinChat(UUID chatId) {
        session.joinChat(chatId);
    }

    public static void leaveChat(UUID chatId) {
        session.leaveChat(chatId);
    }

    public static void sendChatMessage(UUID chatId, String text) {
        session.sendChatMessage(chatId, text);
    }

    public static void sendPlayerManaType(UUID gameId, UUID playerId, ManaType data) {
        session.sendPlayerManaType(gameId, playerId, data);
    }

    public static Optional<TableView> getTable(UUID roomId, UUID tableId) {
        return session.getTable(roomId, tableId);
    }

    public static void updateDeck(UUID tableId, DeckCardLists deckCardLists) {
        session.updateDeck(tableId, deckCardLists);
    }

    public static boolean emailAuthToken(Connection connection) {
        return session.emailAuthToken(connection);
    }

    public static boolean resetPassword(Connection connection) {
        return session.resetPassword(connection);
    }

    public static boolean register(Connection connection) {
        return session.register(connection,VERSION);
    }
    
    public static List<ExpansionInfo> getMissingExpansionsData(List<String> setCodes) {
        return session.getMissingExpansionsData(setCodes);
    }
    
    public static List<CardInfo> getMissingCardsData(List<String> cards) {
        return session.getMissingCardsData(cards);
    }
    
    public static Optional<String> getServerHostname() {
        return session.getServerHostname();
    }
    
    public static UUID getMainRoomId(){
        return session.getMainRoomId();
    }
    
    public static RoomView getRoom(UUID roomId) {
        return session.getRoom(roomId);
    }
}
