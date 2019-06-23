
package mage.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.constants.TableState;
import mage.game.Game;
import mage.game.GameException;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentPlayer;
import mage.players.PlayerType;
import mage.server.game.GameController;
import mage.server.game.GameManager;
import mage.server.game.GamesRoomManager;
import mage.server.util.ThreadExecutor;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum TableManager {
    instance;
    protected final ScheduledExecutorService expireExecutor = Executors.newSingleThreadScheduledExecutor();

    // protected static ScheduledExecutorService expireExecutor = ThreadExecutor.getInstance().getExpireExecutor();
    private final Logger logger = Logger.getLogger(TableManager.class);
    private final DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    private final ConcurrentHashMap<UUID, TableController> controllers = new ConcurrentHashMap<>();
    private final ReadWriteLock controllersLock = new ReentrantReadWriteLock();

    private final ConcurrentHashMap<UUID, Table> tables = new ConcurrentHashMap<>();
    private final ReadWriteLock tablesLock = new ReentrantReadWriteLock();

    /**
     * Defines how often checking process should be run on server.
     * <p>
     * In minutes.
     */
    private static final int EXPIRE_CHECK_PERIOD = 10;

    TableManager() {
        expireExecutor.scheduleAtFixedRate(() -> {
            try {
                ChatManager.instance.clearUserMessageStorage();
                checkTableHealthState();
            } catch (Exception ex) {
                logger.fatal("Check table health state job error:", ex);
            }
        }, EXPIRE_CHECK_PERIOD, EXPIRE_CHECK_PERIOD, TimeUnit.MINUTES);
    }

    public Table createTable(UUID roomId, UUID userId, MatchOptions options) {
        TableController tableController = new TableController(roomId, userId, options);
        putControllers(tableController.getTable().getId(), tableController);
        putTables(tableController.getTable().getId(), tableController.getTable());
        return tableController.getTable();
    }

    public Table createTable(UUID roomId, MatchOptions options) {
        TableController tableController = new TableController(roomId, null, options);
        putControllers(tableController.getTable().getId(), tableController);
        putTables(tableController.getTable().getId(), tableController.getTable());
        return tableController.getTable();
    }

    public Table createTournamentTable(UUID roomId, UUID userId, TournamentOptions options) {
        TableController tableController = new TableController(roomId, userId, options);
        putControllers(tableController.getTable().getId(), tableController);
        putTables(tableController.getTable().getId(), tableController.getTable());
        return tableController.getTable();
    }

    private void putTables(UUID tableId, Table table) {
        final Lock w = tablesLock.writeLock();
        w.lock();
        try {
            tables.put(tableId, table);
        } finally {
            w.unlock();
        }
    }

    private void putControllers(UUID controllerId, TableController tableController) {
        final Lock w = controllersLock.writeLock();
        w.lock();
        try {
            controllers.put(controllerId, tableController);
        } finally {
            w.unlock();
        }
    }

    public Table getTable(UUID tableId) {
        return tables.get(tableId);
    }

    public Optional<Match> getMatch(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            return Optional.of(controllers.get(tableId).getMatch());
        }
        return Optional.empty();
    }

    public Collection<Table> getTables() {
        Collection<Table> newTables = new ArrayList<>();
        final Lock r = tablesLock.readLock();
        r.lock();
        try {
            newTables.addAll(tables.values());
        } finally {
            r.unlock();
        }
        return newTables;
    }

    public Collection<TableController> getControllers() {
        Collection<TableController> newControllers = new ArrayList<>();
        final Lock r = controllersLock.readLock();
        r.lock();
        try {
            newControllers.addAll(controllers.values());
        } finally {
            r.unlock();
        }
        return newControllers;
    }

    public Optional<TableController> getController(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            return Optional.of(controllers.get(tableId));
        }
        return Optional.empty();
    }

    public boolean joinTable(UUID userId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws MageException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).joinTable(userId, name, playerType, skill, deckList, password);
        }
        return false;
    }

    public boolean joinTournament(UUID userId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws GameException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).joinTournament(userId, name, playerType, skill, deckList, password);
        }
        return false;
    }

    public boolean submitDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).submitDeck(userId, deckList);
        }
        UserManager.instance.getUser(userId).ifPresent(user -> {
            user.removeSideboarding(tableId);
            user.showUserMessage("Submit deck", "Table no longer active");

        });
        // return true so the panel closes
        return true;
    }

    public void updateDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).updateDeck(userId, deckList);
        }
    }

    // removeUserFromAllTablesAndChat user from all tournament sub tables
    public void userQuitTournamentSubTables(UUID userId) {
        for (TableController controller : getControllers()) {
            if (controller.getTable() != null) {
                if (controller.getTable().isTournamentSubTable()) {
                    controller.leaveTable(userId);
                }
            } else {
                logger.error("TableManager.userQuitTournamentSubTables table == null - userId " + userId);
            }
        }
    }

    // removeUserFromAllTablesAndChat user from all sub tables of a tournament
    public void userQuitTournamentSubTables(UUID tournamentId, UUID userId) {
        for (TableController controller : getControllers()) {
            if (controller.getTable().isTournamentSubTable() && controller.getTable().getTournament().getId().equals(tournamentId)) {
                if (controller.hasPlayer(userId)) {
                    controller.leaveTable(userId);
                }
            }
        }
    }

    public boolean isTableOwner(UUID tableId, UUID userId) {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).isOwner(userId);
        }
        return false;
    }

    public boolean removeTable(UUID userId, UUID tableId) {
        if (isTableOwner(tableId, userId) || UserManager.instance.isAdmin(userId)) {
            logger.debug("Table remove request - userId: " + userId + " tableId: " + tableId);
            TableController tableController = controllers.get(tableId);
            if (tableController != null) {
                tableController.leaveTableAll();
                ChatManager.instance.destroyChatSession(tableController.getChatId());
                removeTable(tableId);
            }
            return true;
        }
        return false;
    }

    public void leaveTable(UUID userId, UUID tableId) {
        TableController tableController = controllers.get(tableId);
        if (tableController != null) {
            tableController.leaveTable(userId);
        }
    }

    public Optional<UUID> getChatId(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            return Optional.of(controllers.get(tableId).getChatId());
        }
        return Optional.empty();
    }

    /**
     * Starts the Match from a non tournament table
     *
     * @param userId table owner
     * @param roomId
     * @param tableId
     */
    public void startMatch(UUID userId, UUID roomId, UUID tableId) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).startMatch(userId);
            // chat of start dialog can be killed
            ChatManager.instance.destroyChatSession(controllers.get(tableId).getChatId());
        }
    }

    /**
     * Used from tournament to start the sub matches from tournament
     *
     * @param roomId
     * @param tableId
     */
    public void startTournamentSubMatch(UUID roomId, UUID tableId) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).startMatch();
        }
    }

    public void startTournament(UUID userId, UUID roomId, UUID tableId) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).startTournament(userId);
            ChatManager.instance.destroyChatSession(controllers.get(tableId).getChatId());
        }
    }

    public void startDraft(UUID tableId, Draft draft) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).startDraft(draft);
        }
    }

    public boolean watchTable(UUID userId, UUID tableId) {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).watchTable(userId);
        }
        return false;
    }

    public void endGame(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            if (controllers.get(tableId).endGameAndStartNextGame()) {
                removeTable(tableId);
            }
        }
    }

    public void endDraft(UUID tableId, Draft draft) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).endDraft(draft);
        }
    }

    public void endTournament(UUID tableId, Tournament tournament) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).endTournament(tournament);
        }
    }

    public void swapSeats(UUID tableId, UUID userId, int seatNum1, int seatNum2) {
        if (controllers.containsKey(tableId) && isTableOwner(tableId, userId)) {
            controllers.get(tableId).swapSeats(seatNum1, seatNum2);
        }
    }

    public void construct(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).construct();
        }
    }

    public void initTournament(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).initTournament();
        }
    }

    public void addPlayer(UUID userId, UUID tableId, TournamentPlayer player) throws GameException {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).addPlayer(userId, player.getPlayer(), player.getPlayerType(), player.getDeck());
        }
    }

    public void removeTable(UUID tableId) {
        TableController tableController = controllers.get(tableId);
        if (tableController != null) {
            Lock w = controllersLock.writeLock();
            w.lock();
            try {
                controllers.remove(tableId);
            } finally {
                w.unlock();
            }
            tableController.cleanUp();  // deletes the table chat and references to users

            Table table = tables.get(tableId);
            w = tablesLock.writeLock();
            w.lock();
            try {
                tables.remove(tableId);
            } finally {
                w.unlock();
            }

            Match match = table.getMatch();
            Game game = null;
            if (match != null) {
                game = match.getGame();
                if (game != null && !game.hasEnded()) {
                    game.end();
                }
            }

            // If table is not finished, the table has to be removed completly because it's not a normal state (if finished it will be removed in GamesRoomImpl.Update())
            if (table.getState() != TableState.FINISHED) {
                if (game != null) {
                    GameManager.instance.removeGame(game.getId());
                }
                GamesRoomManager.instance.removeTable(tableId);
            }

        }
    }

    public void debugServerState() {
        logger.debug("--- Server state ----------------------------------------------");
        Collection<User> users = UserManager.instance.getUsers();
        logger.debug("--------User: " + users.size() + " [userId | since | lock | name -----------------------");
        for (User user : users) {
            Optional<Session> session = SessionManager.instance.getSession(user.getSessionId());
            String sessionState = "N";
            if (session.isPresent()) {
                if (session.get().isLocked()) {
                    sessionState = "L";
                } else {
                    sessionState = "+";
                }
            }
            logger.debug(user.getId()
                    + " | " + formatter.format(user.getConnectionTime())
                    + " | " + sessionState
                    + " | " + user.getName() + " (" + user.getUserState().toString() + " - " + user.getPingInfo() + ')');
        }
        List<ChatSession> chatSessions = ChatManager.instance.getChatSessions();
        logger.debug("------- ChatSessions: " + chatSessions.size() + " ----------------------------------");
        for (ChatSession chatSession : chatSessions) {
            logger.debug(chatSession.getChatId() + " " + formatter.format(chatSession.getCreateTime()) + ' ' + chatSession.getInfo() + ' ' + chatSession.getClients().values().toString());
        }
        logger.debug("------- Games: " + GameManager.instance.getNumberActiveGames() + " --------------------------------------------");
        logger.debug(" Active Game Worker: " + ThreadExecutor.instance.getActiveThreads(ThreadExecutor.instance.getGameExecutor()));
        for (Entry<UUID, GameController> entry : GameManager.instance.getGameController().entrySet()) {
            logger.debug(entry.getKey() + entry.getValue().getPlayerNameList());
        }
        logger.debug("--- Server state END ------------------------------------------");
    }

    private void checkTableHealthState() {
        if (logger.isDebugEnabled()) {
            debugServerState();
        }
        logger.debug("TABLE HEALTH CHECK");
        for (Table table : getTables()) {
            try {
                if (table.getState() != TableState.FINISHED
                        && ((System.currentTimeMillis() - table.getStartTime().getTime()) / 1000) > 30) { // removeUserFromAllTablesAndChat only if table started longer than 30 seconds ago
                    // removeUserFromAllTablesAndChat tables and games not valid anymore
                    logger.debug(table.getId() + " [" + table.getName() + "] " + formatter.format(table.getStartTime() == null ? table.getCreateTime() : table.getCreateTime()) + " (" + table.getState().toString() + ") " + (table.isTournament() ? "- Tournament" : ""));
                    getController(table.getId()).ifPresent(tableController -> {
                        if ((table.isTournament() && !tableController.isTournamentStillValid())
                                || (!table.isTournament() && !tableController.isMatchTableStillValid())) {
                            try {
                                logger.warn("Removing unhealthy tableId " + table.getId());
                                removeTable(table.getId());
                            } catch (Exception e) {
                                logger.error(e);
                            }
                        }
                    });
                }
            } catch (Exception ex) {
                logger.debug("Table Health check error tableId: " + table.getId());
                logger.debug(Arrays.toString(ex.getStackTrace()));
            }
        }
        logger.debug("TABLE HEALTH CHECK - END");

    }
}
