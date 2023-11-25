package mage.server;

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
import mage.server.managers.TableManager;
import mage.server.managers.ManagerFactory;
import org.apache.log4j.Logger;

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

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TableManagerImpl implements TableManager {
    protected final ScheduledExecutorService expireExecutor = Executors.newSingleThreadScheduledExecutor();

    private final ManagerFactory managerFactory;
    private final Logger logger = Logger.getLogger(TableManagerImpl.class);
    private final DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    private final ConcurrentHashMap<UUID, TableController> controllers = new ConcurrentHashMap<>();
    private final ReadWriteLock controllersLock = new ReentrantReadWriteLock();

    private final ConcurrentHashMap<UUID, Table> tables = new ConcurrentHashMap<>();
    private final ReadWriteLock tablesLock = new ReentrantReadWriteLock();

    // defines how often checking process should be run on server (in minutes)
    private static final int TABLE_HEALTH_CHECK_TIMEOUT_MINS = 10;

    public TableManagerImpl(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    public void init() {
        expireExecutor.scheduleAtFixedRate(() -> {
            try {
                managerFactory.chatManager().clearUserMessageStorage();
                checkTableHealthState();
            } catch (Exception ex) {
                logger.fatal("Check table health state job error:", ex);
            }
        }, TABLE_HEALTH_CHECK_TIMEOUT_MINS, TABLE_HEALTH_CHECK_TIMEOUT_MINS, TimeUnit.MINUTES);
    }

    @Override
    public Table createTable(UUID roomId, UUID userId, MatchOptions options) {
        TableController tableController = new TableController(managerFactory, roomId, userId, options);
        putControllers(tableController.getTable().getId(), tableController);
        putTables(tableController.getTable().getId(), tableController.getTable());
        return tableController.getTable();
    }

    @Override
    public Table createTable(UUID roomId, MatchOptions options) {
        TableController tableController = new TableController(managerFactory, roomId, null, options);
        putControllers(tableController.getTable().getId(), tableController);
        putTables(tableController.getTable().getId(), tableController.getTable());
        return tableController.getTable();
    }

    @Override
    public Table createTournamentTable(UUID roomId, UUID userId, TournamentOptions options) {
        TableController tableController = new TableController(managerFactory, roomId, userId, options);
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

    @Override
    public Table getTable(UUID tableId) {
        return tables.get(tableId);
    }

    @Override
    public Optional<Match> getMatch(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            return Optional.of(controllers.get(tableId).getMatch());
        }
        return Optional.empty();
    }

    @Override
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

    @Override
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

    @Override
    public Optional<TableController> getController(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            return Optional.of(controllers.get(tableId));
        }
        return Optional.empty();
    }

    @Override
    public boolean joinTable(UUID userId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws MageException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).joinTable(userId, name, playerType, skill, deckList, password);
        }
        return false;
    }

    @Override
    public boolean joinTournament(UUID userId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws GameException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).joinTournament(userId, name, playerType, skill, deckList, password);
        }
        return false;
    }

    @Override
    public boolean submitDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).submitDeck(userId, deckList);
        }
        managerFactory.userManager().getUser(userId).ifPresent(user -> {
            user.removeSideboarding(tableId);
            user.showUserMessage("Submit deck", "Table no longer active");

        });
        // return true so the panel closes
        return true;
    }

    @Override
    public void updateDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).updateDeck(userId, deckList);
        }
    }

    // removeUserFromAllTablesAndChat user from all tournament sub tables
    @Override
    public void userQuitTournamentSubTables(UUID userId) {
        for (TableController controller : getControllers()) {
            if (controller.getTable() != null) {
                if (controller.getTable().isTournamentSubTable()) {
                    controller.leaveTable(userId);
                }
            } else {
                logger.error("TableManagerImpl.userQuitTournamentSubTables table == null - userId " + userId);
            }
        }
    }

    // removeUserFromAllTablesAndChat user from all sub tables of a tournament
    @Override
    public void userQuitTournamentSubTables(UUID tournamentId, UUID userId) {
        for (TableController controller : getControllers()) {
            if (controller.getTable().isTournamentSubTable() && controller.getTable().getTournament().getId().equals(tournamentId)) {
                if (controller.hasPlayer(userId)) {
                    controller.leaveTable(userId);
                }
            }
        }
    }

    @Override
    public boolean isTableOwner(UUID tableId, UUID userId) {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).isOwner(userId);
        }
        return false;
    }

    @Override
    public boolean removeTable(UUID userId, UUID tableId) {
        if (isTableOwner(tableId, userId) || managerFactory.userManager().isAdmin(userId)) {
            logger.debug("Table remove request - userId: " + userId + " tableId: " + tableId);
            TableController tableController = controllers.get(tableId);
            if (tableController != null) {
                tableController.leaveTableAll();
                managerFactory.chatManager().destroyChatSession(tableController.getChatId());
                removeTable(tableId);
            }
            return true;
        }
        return false;
    }

    @Override
    public void leaveTable(UUID userId, UUID tableId) {
        TableController tableController = controllers.get(tableId);
        if (tableController != null) {
            tableController.leaveTable(userId);
        }
    }

    @Override
    public Optional<UUID> getChatId(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            return Optional.of(controllers.get(tableId).getChatId());
        }
        return Optional.empty();
    }

    /**
     * Starts the Match from a non tournament table
     *
     * @param userId  table owner
     * @param roomId
     * @param tableId
     */
    @Override
    public void startMatch(UUID userId, UUID roomId, UUID tableId) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).startMatch(userId);
            // chat of start dialog can be killed
            managerFactory.chatManager().destroyChatSession(controllers.get(tableId).getChatId());
        }
    }

    /**
     * Used from tournament to start the sub matches from tournament
     *
     * @param roomId
     * @param tableId
     */
    @Override
    public void startTournamentSubMatch(UUID roomId, UUID tableId) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).startMatch();
        }
    }

    @Override
    public void startTournament(UUID userId, UUID roomId, UUID tableId) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).startTournament(userId);
            managerFactory.chatManager().destroyChatSession(controllers.get(tableId).getChatId());
        }
    }

    @Override
    public void startDraft(UUID tableId, Draft draft) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).startDraft(draft);
        }
    }

    @Override
    public boolean watchTable(UUID userId, UUID tableId) {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).watchTable(userId);
        }
        return false;
    }

    @Override
    public void endGame(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            if (controllers.get(tableId).endGameAndStartNextGame()) {
                removeTable(tableId);
            }
        }
    }

    @Override
    public void endDraft(UUID tableId, Draft draft) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).endDraft(draft);
        }
    }

    @Override
    public void endTournament(UUID tableId, Tournament tournament) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).endTournament(tournament);
        }
    }

    @Override
    public void swapSeats(UUID tableId, UUID userId, int seatNum1, int seatNum2) {
        if (controllers.containsKey(tableId) && isTableOwner(tableId, userId)) {
            controllers.get(tableId).swapSeats(seatNum1, seatNum2);
        }
    }

    @Override
    public void construct(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).construct();
        }
    }

    @Override
    public void initTournament(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).initTournament();
        }
    }

    @Override
    public void addPlayer(UUID userId, UUID tableId, TournamentPlayer player) throws GameException {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).addPlayer(userId, player.getPlayer(), player.getPlayerType(), player.getDeck());
        }
    }

    @Override
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
                    managerFactory.gameManager().removeGame(game.getId());
                    // something goes wrong, so don't add it to ended stats
                }
                managerFactory.gamesRoomManager().removeTable(tableId);
            }

        }
    }

    @Override
    public void debugServerState() {
        logger.debug("--- Server state ----------------------------------------------");
        Collection<User> users = managerFactory.userManager().getUsers();
        logger.debug("--------User: " + users.size() + " [userId | since | lock | name -----------------------");
        for (User user : users) {
            Optional<Session> session = managerFactory.sessionManager().getSession(user.getSessionId());
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
        List<ChatSession> chatSessions = managerFactory.chatManager().getChatSessions();
        logger.debug("------- ChatSessions: " + chatSessions.size() + " ----------------------------------");
        for (ChatSession chatSession : chatSessions) {
            logger.debug(chatSession.getChatId() + " " + formatter.format(chatSession.getCreateTime()) + ' ' + chatSession.getInfo() + ' ' + chatSession.getClients().values().toString());
        }
        logger.debug("------- Games: " + managerFactory.gameManager().getNumberActiveGames() + " --------------------------------------------");
        logger.debug(" Active Game Worker: " + managerFactory.threadExecutor().getActiveThreads(managerFactory.threadExecutor().getGameExecutor()));
        for (Entry<UUID, GameController> entry : managerFactory.gameManager().getGameController().entrySet()) {
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
                    logger.debug(table.getId() + " [" + table.getName() + "] " + formatter.format(table.getStartTime() != null ? table.getStartTime() : table.getCreateTime()) + " (" + table.getState().toString() + ") " + (table.isTournament() ? "- Tournament" : ""));
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
