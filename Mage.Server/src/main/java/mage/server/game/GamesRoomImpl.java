/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.server.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import mage.cards.decks.DeckCardLists;
import mage.constants.TableState;
import mage.game.Table;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.server.RoomImpl;
import mage.server.Session;
import mage.server.SessionManager;
import mage.server.TableManager;
import mage.server.User;
import mage.server.UserManager;
import mage.server.tournament.TournamentManager;
import mage.server.util.ConfigSettings;
import mage.server.util.ThreadExecutor;
import mage.view.MatchView;
import mage.view.RoomUsersView;
import mage.view.RoomView;
import mage.view.TableView;
import mage.view.UsersView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GamesRoomImpl extends RoomImpl implements GamesRoom, Serializable {

    private static final Logger logger = Logger.getLogger(GamesRoomImpl.class);

    private static final ScheduledExecutorService updateExecutor = Executors.newSingleThreadScheduledExecutor();
    private RoomView roomView;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final ConcurrentHashMap<UUID, Table> tables = new ConcurrentHashMap<>();

    public GamesRoomImpl() {
        updateExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    update();
                } catch (Exception ex) {
                    logger.fatal("Games room update exception! " + ex.toString(), ex);
                }

            }
        }, 2, 2, TimeUnit.SECONDS);
    }

    private void update() {
        ArrayList<TableView> tableView = new ArrayList<>();
        ArrayList<MatchView> matchView = new ArrayList<>();
        List<Table> allTables = new ArrayList<>(tables.values());
        Collections.sort(allTables, new TableListSorter());
        for (Table table : allTables) {
            if (table.getState() != TableState.FINISHED) {
                tableView.add(new TableView(table));
            }
            else if (matchView.size() < 50) { 
                 if (table.isTournament()) {
                    matchView.add(new MatchView(table));
                } else {
                    matchView.add(new MatchView(table));
                }
            } else {
                // more since 50 matches finished since this match so remove it
                if (table.isTournament()) {
                    TournamentManager.getInstance().removeTournament(table.getTournament().getId());
                }
                this.removeTable(table.getId());
            }
        }
        List<UsersView> users = new ArrayList<>();
        for (User user : UserManager.getInstance().getUsers()) {
            Session session = SessionManager.getInstance().getSession(user.getSessionId());
            try {
               users.add(new UsersView(user.getUserData().getFlagName(), user.getName(), user.getInfo(), user.getGameInfo(), session.getPingInfo()));
            } catch (Exception ex) {
                logger.fatal("User update exception: " + user.getName() + " - " + ex.toString(), ex);
                users.add(new UsersView(
                        (user.getUserData() != null && user.getUserData().getFlagName() != null) ? user.getUserData().getFlagName() : "world",
                        user.getName() != null ? user.getName() : "<no name>",
                        user.getInfo() != null ? user.getInfo() : "<no info>",
                        "[exception]",
                        session.getPingInfo() != null ? session.getPingInfo() : "<no ping>"));
            }
        }

        Collections.sort(users, new UserNameSorter());
        RoomUsersView roomUsersView = new RoomUsersView(users, 
                GameManager.getInstance().getNumberActiveGames(),
                ThreadExecutor.getInstance().getActiveThreads(ThreadExecutor.getInstance().getGameExecutor()),
                ConfigSettings.getInstance().getMaxGameThreads()
        );
        lock.writeLock().lock();
        try {
            roomView = new RoomView(roomUsersView, tableView, matchView);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean joinTable(UUID userId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password) {
        if (tables.containsKey(tableId)) {
            return TableManager.getInstance().joinTable(userId, tableId, name, playerType, skill, deckList, password);
        } else {
            return false;
        }
    }

    @Override
    public TableView createTable(UUID userId, MatchOptions options) {
        Table table = TableManager.getInstance().createTable(this.getRoomId(), userId, options);
        tables.put(table.getId(), table);
        return new TableView(table);
    }

    @Override
    public boolean joinTournamentTable(UUID userId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password) {
        if (tables.containsKey(tableId)) {
            return TableManager.getInstance().joinTournament(userId, tableId, name, playerType, skill, deckList, password);
        } else {
            return false;
        }
    }

    @Override
    public TableView createTournamentTable(UUID userId, TournamentOptions options) {
        Table table = TableManager.getInstance().createTournamentTable(this.getRoomId(), userId, options);
        tables.put(table.getId(), table);
        return new TableView(table);
    }

    @Override
    public TableView getTable(UUID tableId) {
        if (tables.containsKey(tableId)) {
            return new TableView(tables.get(tableId));
        }
        return null;
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
            if (logger.isDebugEnabled()) {
                logger.debug("Table removed: " + tableId);
            }
        }
    }

    @Override
    public void leaveTable(UUID userId, UUID tableId) {
        TableManager.getInstance().leaveTable(userId, tableId);
    }

    @Override
    public void watchTable(UUID userId, UUID tableId) {
        TableManager.getInstance().watchTable(userId, tableId);
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

}

/**
 * Sorts the tables for table and match view of the client room
 *
 * @author LevelX2
 */
class TableListSorter implements Comparator<Table> {

    @Override
    public int compare(Table one, Table two) {
        if (!one.getState().equals(TableState.SIDEBOARDING) && !one.getState().equals(TableState.DUELING)) {
            if (one.getState().compareTo(two.getState()) != 0) {
                return one.getState().compareTo(two.getState());
            }
        } else if (!two.getState().equals(TableState.SIDEBOARDING) && !two.getState().equals(TableState.DUELING)) {
            if (one.getState().compareTo(two.getState()) != 0) {
                return one.getState().compareTo(two.getState());
            }
        }
        if (two.getEndTime() != null) {
            if (one.getEndTime() == null) {
                return 1;
            } else {
                return two.getEndTime().compareTo(one.getEndTime());
            }
        }
        if (two.getStartTime() != null) {
            if (one.getStartTime() == null) {
                return 1;
            } else {
                return two.getStartTime().compareTo(one.getStartTime());
            }
        }
        if (two.getCreateTime() != null) {
            if (one.getCreateTime() == null) {
                return 1;
            } else {
                return two.getCreateTime().compareTo(one.getCreateTime());
            }
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
