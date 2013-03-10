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
import mage.Constants.TableState;
import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.game.Table;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.server.RoomImpl;
import mage.server.TableManager;
import mage.server.User;
import mage.server.UserManager;
import mage.view.MatchView;
import mage.view.TableView;
import org.apache.log4j.Logger;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GamesRoomImpl extends RoomImpl implements GamesRoom, Serializable {

    private static final Logger logger = Logger.getLogger(GamesRoomImpl.class);

    private static ScheduledExecutorService updateExecutor = Executors.newSingleThreadScheduledExecutor();
    private static List<TableView> tableView = new ArrayList<TableView>();
    private static List<MatchView> matchView = new ArrayList<MatchView>();
    private static List<String> playersView = new ArrayList<String>();

    private ConcurrentHashMap<UUID, Table> tables = new ConcurrentHashMap<UUID, Table>();

    public GamesRoomImpl() {
        updateExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                update();
            }
        }, 2, 2, TimeUnit.SECONDS);
    }

    @Override
    public List<TableView> getTables() {
        return tableView;
    }

    private void update() {
        ArrayList<TableView> tableList = new ArrayList<TableView>();
        ArrayList<MatchView> matchList = new ArrayList<MatchView>();
        List<Table> t = new ArrayList<Table>(tables.values());
        Collections.sort(t, new TimestampSorter());
        for (Table table: t) {
            if (table.getState() != TableState.FINISHED) {
                tableList.add(new TableView(table));
            }
            else if (matchList.size() < 50) {
                matchList.add(new MatchView(table.getMatch()));
            }
        }
        tableView = tableList;
        matchView = matchList;
        List<String> players = new ArrayList<String>();
        for (User user : UserManager.getInstance().getUsers()) {
            players.add(user.getName());
        }
        playersView = players;
    }

    @Override
    public List<MatchView> getFinished() {
        return matchView;
    }

    @Override
    public boolean joinTable(UUID userId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList) throws MageException {
        if (tables.containsKey(tableId)) {
            return TableManager.getInstance().joinTable(userId, tableId, name, playerType, skill, deckList);
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
    public boolean joinTournamentTable(UUID userId, UUID tableId, String name, String playerType, int skill) throws GameException {
        if (tables.containsKey(tableId)) {
            return TableManager.getInstance().joinTournament(userId, tableId, name, playerType, skill);
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
        if (tables.containsKey(tableId))
            return new TableView(tables.get(tableId));
        return null;
    }

    @Override
    public void removeTable(UUID userId, UUID tableId) {
        tables.remove(tableId);
    }

    @Override
    public void removeTable(UUID tableId) {
        tables.remove(tableId);
        if (logger.isDebugEnabled())
            logger.debug("Table removed: " + tableId);
    }

    @Override
    public void leaveTable(UUID userId, UUID tableId) {
        TableManager.getInstance().leaveTable(userId, tableId);
    }

    @Override
    public boolean watchTable(UUID userId, UUID tableId) throws MageException {
        return TableManager.getInstance().watchTable(userId, tableId);
    }

    @Override
    public List<String> getPlayers() {
        return playersView;
    }

}

class TimestampSorter implements Comparator<Table> {
    @Override
    public int compare(Table one, Table two) {
        return one.getCreateTime().compareTo(two.getCreateTime());
    }
}
