package mage.client;

import mage.cards.decks.DeckCardLists;
import static mage.cards.decks.DeckFormats.XMAGE;
import mage.client.chat.LocalCommands;
import mage.client.constants.Constants.DeckEditorMode;
import mage.client.dialog.PreferencesDialog;
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
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Network: client side session
 *
 * Only one session/server per GUI's client supports
 *
 * @author IGOUDT
 */
public final class SessionHandler {

    private static final Logger logger = Logger.getLogger(SessionHandler.class);

    private static Session session;
    private static String lastConnectError = "";

    private SessionHandler(){
    }

    public static void startSession(MageFrame mageFrame) {

        session = new SessionImpl(mageFrame);
        session.setJsonLogActive("true".equals(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_JSON_GAME_LOG_AUTO_SAVE, "true")));
    }

    public static void ping() {
        session.ping();
    }

    public static Session getSession() {
        return session;
    }

    public static boolean isConnected() {
        return session.isConnected();
    }

    public static String getVersionInfo() {
        return session.getVersionInfo();
    }

    public static boolean connect(Connection connection) {
        lastConnectError = "";
        if (session.connectStart(connection)) {
            return true;
        } else {
            lastConnectError = session.getLastError();
            return false;
        }
    }

    public static String getLastConnectError() {
        return lastConnectError;
    }

    public static boolean stopConnecting() {
        return session.connectAbort();
    }

    public static void disconnect(boolean showmessage) {
        session.connectStop(showmessage);
    }

    public static void sendPlayerAction(PlayerAction playerAction, UUID gameId, Object relatedUserId) {
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

    public static void removeTable(UUID roomId, UUID tableId) {
        session.removeTable(roomId, tableId);
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
        return session.getPlayerTypes();
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

    public static void updatePreferencesForServer(UserData userData) {
        session.updatePreferencesForServer(userData);
    }

    public static boolean isTableOwner(UUID roomId, UUID tableId) {
        return session.isTableOwner(roomId, tableId);
    }

    public static Optional<UUID> getTableChatId(UUID tableId) {
        return session.getTableChatId(tableId);
    }

    public static boolean startTournament(UUID roomId, UUID tableId) {
        return session.startTournament(roomId, tableId);
    }

    public static boolean startMatch(UUID roomId, UUID tableId) {
        return session.startMatch(roomId, tableId);
    }

    public static Optional<UUID> getGameChatId(UUID gameId) {
        return session.getGameChatId(gameId);
    }

    public static boolean joinGame(UUID gameId) {
        return session.joinGame(gameId);
    }

    public static boolean startReplay(UUID gameId) {
        return session.startReplay(gameId);
    }

    public static void watchTournamentTable(UUID tableId) {
        session.watchTournamentTable(tableId);
    }

    public static boolean joinTournament(UUID tournamentId) {
        return session.joinTournament(tournamentId);
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
        return session.isTestMode();
    }

    public static void cheatShow(UUID gameId, UUID playerId) {
        session.cheatShow(gameId, playerId);
    }

    public static String getSessionId() {
        return session.getSessionId();
    }

    public static List<TournamentTypeView> getTournamentTypes() {
        return session.getTournamentTypes();
    }

    private static void autoSaveLimitedDeck(DeckCardLists deckList) {
        String autoSave = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_LIMITED_DECK_AUTO_SAVE, "true");
        if(autoSave.equals("true")){
            // Log the submitted deck in the log folder.
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String logFilename = sdf.format(new Date()) + "_limited" + ".dck";
            try {
                XMAGE.getExporter().writeDeck(new File("gamelogs"), logFilename, deckList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean submitDeck(DeckEditorMode mode, UUID tableId, DeckCardLists deckCardLists) {
        boolean success = session.submitDeck(tableId, deckCardLists);
        if(DeckEditorMode.LIMITED_BUILDING.equals(mode)) {
            // AutoSaving is done after submitting, to not let the server wait.
            autoSaveLimitedDeck(deckCardLists);
        }
        return success;
    }

    public static String[] getDeckTypes() {
        return session.getDeckTypes();
    }

    public static String[] getDraftCubes() {
        return session.getDraftCubes();
    }

    public static List<GameTypeView> getTournamentGameTypes() {
        return session.getTournamentGameTypes();
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
        return session.getGameTypes();
    }

    public static boolean joinDraft(UUID draftId) {
        return session.joinDraft(draftId);
    }

    public static DraftPickView sendCardPick(UUID draftId, UUID id, Set<UUID> cardsHidden) {
        return session.sendCardPick(draftId, id, cardsHidden);
    }

    public static void sendCardMark(UUID draftId, UUID id) {
        session.sendCardMark(draftId, id);
    }

    public static void setBoosterLoaded(UUID draftId) {
        session.setBoosterLoaded(draftId);
    }

    public static Optional<UUID> getRoomChatId(UUID roomId) {
        return session.getRoomChatId(roomId);
    }

    public static Collection<RoomUsersView> getRoomUsers(UUID roomId) {
        try {
            return session.getRoomUsers(roomId);
        } catch (MageRemoteException e) {
            logger.info(e);
            return Collections.emptyList();
        }
    }

    public static Collection<MatchView> getFinishedMatches(UUID roomId) {
        try {
            return session.getFinishedMatches(roomId);
        } catch (MageRemoteException e) {
            logger.info(e);
            return Collections.emptyList();
        }
    }

    public static void replayGame(UUID id) {
        session.replayGame(id);
    }

    public static void watchTable(UUID roomId, UUID tableId) {
        session.watchTable(roomId, tableId);
    }

    public static Collection<TableView> getTables(UUID roomId) {
        try {
            return session.getTables(roomId);
        } catch (MageRemoteException e) {
            logger.info(e);
            return Collections.emptyList();
        }
    }

    public static List<String> getServerMessages() {
        return session.getServerMessages();
    }

    public static boolean joinChat(UUID chatId) {
        return session.joinChat(chatId);
    }

    public static boolean leaveChat(UUID chatId) {
        return session.leaveChat(chatId);
    }

    public static boolean sendChatMessage(UUID chatId, String text) {
        if (!LocalCommands.handleLocalCommands(chatId, text)) {
            return session.sendChatMessage(chatId, text);
        } else {
            return false;
        }
    }

    public static boolean sendPlayerManaType(UUID gameId, UUID playerId, ManaType data) {
        return session.sendPlayerManaType(gameId, playerId, data);
    }

    public static Optional<TableView> getTable(UUID roomId, UUID tableId) {
        return session.getTable(roomId, tableId);
    }

    public static void updateDeck(UUID tableId, DeckCardLists deckCardLists) {
        session.updateDeck(tableId, deckCardLists);
    }

    public static boolean emailAuthToken(Connection connection) {
        return session.sendAuthSendTokenToEmail(connection);
    }

    public static boolean resetPassword(Connection connection) {
        return session.sendAuthResetPassword(connection);
    }

    public static boolean register(Connection connection) {
        return session.sendAuthRegister(connection);
    }
}
