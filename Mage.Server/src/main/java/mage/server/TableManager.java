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

package mage.server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import mage.MageException;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.constants.TableState;
import mage.game.GameException;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.match.MatchPlayer;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentOptions;
import mage.players.Player;
import mage.server.game.GameManager;
import mage.server.game.GamesRoomManager;
import org.apache.log4j.Logger;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableManager {

    protected static ScheduledExecutorService expireExecutor = Executors.newSingleThreadScheduledExecutor();

    private static final TableManager INSTANCE = new TableManager();
    private static final Logger logger = Logger.getLogger(TableManager.class);

    private final ConcurrentHashMap<UUID, TableController> controllers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Table> tables = new ConcurrentHashMap<>();

    /**
     * Defines how often checking process should be run on server.
     *
     * In minutes.
     */
    private static final int EXPIRE_CHECK_PERIOD = 10;
    
    /**
     * This parameters defines when table can be counted as expired.
     * Uses EXPIRE_TIME_UNIT_VALUE as unit of measurement.
     *
     * The time pass is calculated as (table_created_at - now) / EXPIRE_TIME_UNIT_VALUE.
     * Then this values is compared to EXPIRE_TIME.
     */
    private static final int EXPIRE_TIME = 3;

    /**
     * Defines unit of measurement for expiration time of tables created.
     */
    private static final int EXPIRE_TIME_UNIT_VALUE = 1000 * 60 * 60; // 1 hour

    public static TableManager getInstance() {
        return INSTANCE;
    }

    private TableManager() {
        expireExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                checkExpired();
            }
        }, EXPIRE_CHECK_PERIOD, EXPIRE_CHECK_PERIOD, TimeUnit.MINUTES);
    }

    public Table createTable(UUID roomId, UUID userId, MatchOptions options) {
        TableController tableController = new TableController(roomId, userId, options);
        controllers.put(tableController.getTable().getId(), tableController);
        tables.put(tableController.getTable().getId(), tableController.getTable());
        return tableController.getTable();
    }

    public Table createTable(UUID roomId, MatchOptions options) {
        TableController tableController = new TableController(roomId, null, options);
        controllers.put(tableController.getTable().getId(), tableController);
        tables.put(tableController.getTable().getId(), tableController.getTable());
        return tableController.getTable();
    }

    public Table createTournamentTable(UUID roomId, UUID userId, TournamentOptions options) {
        TableController tableController = new TableController(roomId, userId, options);
        controllers.put(tableController.getTable().getId(), tableController);
        tables.put(tableController.getTable().getId(), tableController.getTable());
        return tableController.getTable();
    }

    public Table getTable(UUID tableId) {
        return tables.get(tableId);
    }

    public Match getMatch(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).getMatch();
        }
        return null;
    }

    public Collection<Table> getTables() {
        return tables.values();
    }

    public TableController getController(UUID tableId) {
        return controllers.get(tableId);
    }

    public boolean joinTable(UUID userId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList) throws MageException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).joinTable(userId, name, playerType, skill, deckList);
        }
        return false;
    }

    public boolean joinTournament(UUID userId, UUID tableId, String name, String playerType, int skill) throws GameException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).joinTournament(userId, name, playerType, skill);
        }
        return false;
    }

    public boolean submitDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).submitDeck(userId, deckList);
        }
        return false;
    }

    public void updateDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).updateDeck(userId, deckList);
        }
    }

    // remove user from all tournament sub tables
    public void userQuitTournamentSubTables(UUID userId) {
        for (TableController controller: controllers.values()) {
            if (controller.getTable().isTournamentSubTable()) {
                controller.leaveTable(userId);
            }
        }
    }

    // remove user from all sub tables of a tournament
    public void userQuitTournamentSubTables(UUID tournamentId, UUID userId) {
        for (TableController controller: controllers.values()) {
            if (controller.getTable().isTournamentSubTable() && controller.getTable().getTournament().getId().equals(tournamentId)) {
                Match match = controller.getTable().getMatch();
                if (match != null) {
                    if (match.getGame() != null) {
                        GameManager.getInstance().quitMatch(match.getGame().getId(), userId);
                    }
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
        if (isTableOwner(tableId, userId) || UserManager.getInstance().isAdmin(userId)) {
            leaveTable(userId, tableId);
            removeTable(tableId);
            return true;
        }
        return false;
    }

    public void leaveTable(UUID userId, UUID tableId) {
        if (controllers.containsKey(tableId)) {
            // table not started yet and user is the owner, remove the table
            if (isTableOwner(tableId, userId)
                    && (getTable(tableId).getState().equals(TableState.WAITING)
                    || getTable(tableId).getState().equals(TableState.STARTING))) {
                removeTable(tableId);

            } else {
                controllers.get(tableId).leaveTable(userId);
            }
        }
    }

    public UUID getChatId(UUID tableId) {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).getChatId();
        }
        return null;
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

//    public void startChallenge(UUID userId, UUID roomId, UUID tableId, UUID challengeId) {
//        if (controllers.containsKey(tableId)) {
//            controllers.get(tableId).startChallenge(userId, challengeId);
//        }
//    }

    public void startTournament(UUID userId, UUID roomId, UUID tableId) {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).startTournament(userId);
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

//    public boolean replayTable(UUID userId, UUID tableId) {
//        if (controllers.containsKey(tableId)) {
//            return controllers.get(tableId).replayTable(userId);
//        }
//        return false;
//    }

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

    public void addPlayer(UUID userId, UUID tableId, Player player, String playerType, Deck deck) throws GameException {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).addPlayer(userId, player, playerType, deck);
        }
    }

    public void removeTable(UUID tableId) {
        if (tables.containsKey(tableId)) {
            Table table = tables.get(tableId);
            controllers.remove(tableId);
            tables.remove(tableId);
            // If table is not finished, the table has to removed completly (if finished it will be removed in GamesRoomImpl.Update())
            if (!table.getState().equals(TableState.FINISHED)) {
                GamesRoomManager.getInstance().removeTable(tableId);
            }
            if (table.getMatch() != null && table.getMatch().getGame() != null) {
                table.getMatch().getGame().end();
            }
        }
    }

    private void checkExpired() {
        logger.debug("Table expire checking...");

        Date now = new Date();
        List<UUID> toRemove = new ArrayList<>();
        for (Table table : tables.values()) {
            if (!table.getState().equals(TableState.FINISHED)) {
                // remove all not finished tables created more than expire_time ago
                long diff = (now.getTime() - table.getCreateTime().getTime()) / EXPIRE_TIME_UNIT_VALUE;
                if (diff >= EXPIRE_TIME) {
                    logger.warn("Table expired: id = " + table.getId() + ", created_by=" + table.getControllerName() + ". Removing...");
                    toRemove.add(table.getId());
                }
                // remove tables not valid anymore
                else if (!table.isTournament()) {
                    TableController tableController = getController(table.getId());
                    if (!tableController.isMatchTableStillValid()) {
                        logger.warn("Table with no active human player: id = " + table.getId() + ", created_by=" + table.getControllerName() + ". Removing...");
                        toRemove.add(table.getId());
                    }
                }
            }
        }
        for (UUID tableId : toRemove) {
            try {
                removeTable(tableId);
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }
}
