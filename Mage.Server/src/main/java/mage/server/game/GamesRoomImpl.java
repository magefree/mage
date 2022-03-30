package mage.server.game;

import mage.cards.decks.DeckCardLists;
import mage.constants.TableState;
import mage.game.Table;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.players.PlayerType;
import mage.server.RoomImpl;
import mage.server.User;
import mage.server.managers.ManagerFactory;
import mage.view.MatchView;
import mage.view.RoomUsersView;
import mage.view.RoomView;
import mage.view.TableView;
import mage.view.UsersView;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import mage.game.GameException;
import mage.server.Session;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GamesRoomImpl extends RoomImpl implements GamesRoom, Serializable {

    private static final Logger LOGGER = Logger.getLogger(GamesRoomImpl.class);

    private static final ScheduledExecutorService UPDATE_EXECUTOR = Executors.newSingleThreadScheduledExecutor();
    private static List<TableView> tableView = new ArrayList<>();
    private RoomView roomView;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final ManagerFactory managerFactory;
    private final ConcurrentHashMap<UUID, Table> tables = new ConcurrentHashMap<>();
    private String pingInfo = "";

    public GamesRoomImpl(ManagerFactory managerFactory) {
        super(managerFactory.chatManager());
        this.managerFactory = managerFactory;
        UPDATE_EXECUTOR.scheduleAtFixedRate(() -> {
            try {
                update();
            } catch (Exception ex) {
                LOGGER.fatal("Games room update exception! " + ex.toString(), ex);
            }

        }, 2, 2, TimeUnit.SECONDS);
    }

    private void update() {
        List<Table> allTables = new ArrayList<>(tables.values());
        allTables.sort(new TableListSorter());
        ArrayList<TableView> tableList = new ArrayList<>();
        ArrayList<MatchView> matchView = new ArrayList<>();
        for (Table table : allTables) {
            if (table.getState() != TableState.FINISHED) {
                tableList.add(new TableView(table));
            }
            else if (matchView.size() < 50) { 
                 if (table.isTournament()) {
                    matchView.add(new MatchView(table));
                 } else {
                     matchView.add(new MatchView(table));
                 }
            } else {
                // more since 50 matches finished since this match so removeUserFromAllTablesAndChat it
                if (table.isTournament()) {
                    managerFactory.tournamentManager().removeTournament(table.getTournament().getId());
                }
                this.removeTable(table.getId());
            }
        }
        tableView = tableList;
        List<UsersView> users = new ArrayList<>();
        for (User user : managerFactory.userManager().getUsers()) {
            managerFactory.sessionManager().getSession(user.getSessionId()).ifPresent(session -> {
                String pingInfo;
                pingInfo = session == null ? "<no ping>" : session.getPingInfo();
                if (user.getUserState() != User.UserState.Offline && !user.getName().equals("Admin")) {
                    try {
                        users.add(new UsersView(user.getUserData().getFlagName(), user.getName(),
                                user.getMatchHistory(), user.getMatchQuitRatio(), user.getTourneyHistory(),
                                user.getTourneyQuitRatio(), user.getGameInfo(), pingInfo,
                                user.getUserData().getGeneralRating(), user.getUserData().getConstructedRating(),
                                user.getUserData().getLimitedRating()));
                    } catch (Exception ex) {
                        LOGGER.fatal("User update exception: " + user.getName() + " - " + ex.toString(), ex);
                        users.add(new UsersView(
                                (user.getUserData() != null && user.getUserData().getFlagName() != null) ? user.getUserData().getFlagName() : "world",
                                user.getName() != null ? user.getName() : "<no name>",
                                user.getMatchHistory() != null ? user.getMatchHistory() : "<no match history>",
                                user.getMatchQuitRatio(),
                                user.getTourneyHistory() != null ? user.getTourneyHistory() : "<no tourney history>",
                                user.getTourneyQuitRatio(),
                                "[exception]",
                                pingInfo,
                                user.getUserData() != null ? user.getUserData().getGeneralRating() : 0,
                                user.getUserData() != null ? user.getUserData().getConstructedRating() : 0,
                                user.getUserData() != null ? user.getUserData().getLimitedRating() : 0));
                    }
                }
            });
        }

        users.sort((one, two) -> one.getUserName().compareToIgnoreCase(two.getUserName()));
        List<RoomUsersView> roomUserInfo = new ArrayList<>();
        RoomUsersView roomUsersView = new RoomUsersView(users,
                managerFactory.gameManager().getNumberActiveGames(),
                managerFactory.threadExecutor().getActiveThreads(managerFactory.threadExecutor().getGameExecutor()),
                managerFactory.configSettings().getMaxGameThreads()
        );
        lock.writeLock().lock();
        try {
            roomView = new RoomView(roomUsersView, tableList, matchView);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean joinTable(UUID userId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) {
        if (tables.containsKey(tableId)) {
            return managerFactory.tableManager().joinTable(userId, tableId, name, playerType, skill, deckList, password);
        } else {
            return false;
        }
    }

    @Override
    public TableView createTable(UUID userId, MatchOptions options) {
        Table table = managerFactory.tableManager().createTable(this.getRoomId(), userId, options);
        tables.put(table.getId(), table);
        return new TableView(table);
    }

    @Override
    public boolean joinTournamentTable(UUID userId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password){
        if (tables.containsKey(tableId)) {
            return managerFactory.tableManager().joinTournament(userId, tableId, name, playerType, skill, deckList, password);
        } else {
            return false;
        }
    }

    @Override
    public TableView createTournamentTable(UUID userId, TournamentOptions options) {
        Table table = managerFactory.tableManager().createTournamentTable(this.getRoomId(), userId, options);
        tables.put(table.getId(), table);
        return new TableView(table);
    }

    @Override
    public Optional<TableView> getTable(UUID tableId) {
        if (tables.containsKey(tableId)) {
            return Optional.of(new TableView(tables.get(tableId)));
        }
        return Optional.empty();
    }

    @Override
    public void removeTable(UUID userId, UUID tableId) {
        tables.remove(tableId);
    }

    @Override
    public void removeTable(UUID tableId) {
        Table table = tables.get(tableId);
        if (table != null) {
            table.cleanUp();
            tables.remove(tableId);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Table removed: " + tableId);
            }
        }
    }

    @Override
    public void leaveTable(UUID userId, UUID tableId) {
        managerFactory.tableManager().leaveTable(userId, tableId);
    }

    @Override
    public void watchTable(UUID userId, UUID tableId) {
        managerFactory.tableManager().watchTable(userId, tableId);
    }
    
    @Override
    public RoomView getRoomView() {
        lock.readLock().lock();
        try {
            return roomView;
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public String getPingInfo() {
        return pingInfo;
    }
    
    @Override
    public List<TableView> getTables() {
        return tableView;
    }

}

/**
 * Sorts the tables for table and match view of the client room
 *
 * @author LevelX2
 */
class TableListSorter implements Comparator<Table> {

    @Override
    public int compare(Table one, Table two) {
        if (one.getState() != null && two.getState() != null) {
            if (TableState.SIDEBOARDING != one.getState() && TableState.DUELING != one.getState()) {
                if (one.getState().compareTo(two.getState()) != 0) {
                    return one.getState().compareTo(two.getState());
                }
            } else if (TableState.SIDEBOARDING != two.getState() && TableState.DUELING != two.getState()) {
                if (one.getState().compareTo(two.getState()) != 0) {
                    return one.getState().compareTo(two.getState());
                }
            }
        }
        if (two.getEndTime() != null) {
            if (one.getEndTime() == null) {
                return 1;
            } else {
                return two.getEndTime().compareTo(one.getEndTime());
            }
        } else if (one.getEndTime() != null) {
            return -1;
        }

        if (two.getStartTime() != null) {
            if (one.getStartTime() == null) {
                return 1;
            } else {
                return two.getStartTime().compareTo(one.getStartTime());
            }
        } else if (one.getStartTime() != null) {
            return -1;
        }

        if (two.getCreateTime() != null) {
            if (one.getCreateTime() == null) {
                return 1;
            } else {
                return two.getCreateTime().compareTo(one.getCreateTime());
            }
        } else if (one.getCreateTime() != null) {
            return -1;
        }
        return 0;
    }
}

class UserNameSorter implements Comparator<UsersView> {

    @Override
    public int compare(UsersView one, UsersView two) {
        return one.getUserName().compareToIgnoreCase(two.getUserName());
    }
}
