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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import mage.MageException;
import mage.cards.decks.Deck;
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
import mage.players.Player;
import mage.server.game.GameController;
import mage.server.game.GameManager;
import mage.server.game.GamesRoomManager;
import mage.server.util.ThreadExecutor;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableManager {

    protected static ScheduledExecutorService expireExecutor = Executors.newSingleThreadScheduledExecutor();

    // protected static ScheduledExecutorService expireExecutor = ThreadExecutor.getInstance().getExpireExecutor();
    
    private static final TableManager INSTANCE = new TableManager();
    private static final Logger logger = Logger.getLogger(TableManager.class);
    private static final DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    
    private final ConcurrentHashMap<UUID, TableController> controllers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Table> tables = new ConcurrentHashMap<>();

    /**
     * Defines how often checking process should be run on server.
     *
     * In minutes.
     */
    private static final int EXPIRE_CHECK_PERIOD = 10;
    
    public static TableManager getInstance() {
        return INSTANCE;
    }

    private TableManager() {
        expireExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    checkTableHealthState();
                } catch(Exception ex) {
                    logger.fatal("Check table health state job error:", ex);
                }
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

    public boolean joinTable(UUID userId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password) throws MageException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).joinTable(userId, name, playerType, skill, deckList, password);
        }
        return false;
    }

    public boolean joinTournament(UUID userId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password) throws GameException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).joinTournament(userId, name, playerType, skill, deckList, password);
        }
        return false;
    }

    public boolean submitDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException {
        if (controllers.containsKey(tableId)) {
            return controllers.get(tableId).submitDeck(userId, deckList);
        }
        User user = UserManager.getInstance().getUser(userId);
        if (user != null) {
            user.removeSideboarding(tableId);
            user.showUserMessage("Submit deck", "Table no longer active");
        }
        // return true so the panel closes
        return true;
    }

    public void updateDeck(UUID userId, UUID tableId, DeckCardLists deckList) throws MageException {
        if (controllers.containsKey(tableId)) {
            controllers.get(tableId).updateDeck(userId, deckList);
        }
    }

    // remove user from all tournament sub tables
    public void userQuitTournamentSubTables(UUID userId) {
        for (TableController controller: controllers.values()) {
            if (controller.getTable() != null) {
                if (controller.getTable().isTournamentSubTable()) {
                    controller.leaveTable(userId);
                }
            } else {
                logger.error("TableManager.userQuitTournamentSubTables table == null - userId " + userId);
            }
        }
    }

    // remove user from all sub tables of a tournament
    public void userQuitTournamentSubTables(UUID tournamentId, UUID userId) {
        for (TableController controller: controllers.values()) {
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
        if (isTableOwner(tableId, userId) || UserManager.getInstance().isAdmin(userId)) {
            logger.debug("Table remove request - userId: " + userId + " tableId: " + tableId);
            TableController tableController = controllers.get(tableId);
            if (tableController != null) {
                tableController.leaveTableAll();
                ChatManager.getInstance().destroyChatSession(tableController.getChatId());
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
            // chat of start dialog can be killed
            ChatManager.getInstance().destroyChatSession(controllers.get(tableId).getChatId());            
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
                ChatManager.getInstance().destroyChatSession(controllers.get(tableId).getChatId());
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
        TableController tableController = controllers.get(tableId);            
        if (tableController != null) {
            controllers.remove(tableId);
            tableController.cleanUp();  // deletes the table chat and references to users           
            
            Table table = tables.get(tableId);
            tables.remove(tableId);
            Match match = table.getMatch();
            Game game = null;
            if (match != null) {
                game = match.getGame();
                if (game != null && !game.hasEnded()) {
                    game.end();
                }                
            }
                        
            // If table is not finished, the table has to be removed completly because it's not a normal state (if finished it will be removed in GamesRoomImpl.Update())
            if (!table.getState().equals(TableState.FINISHED)) {
                if (game != null) {
                    GameManager.getInstance().removeGame(game.getId());
                }
                GamesRoomManager.getInstance().removeTable(tableId);
            }
            
        }
    }

    public void debugServerState() {
        logger.debug("--- Server state ----------------------------------------------");
        Collection<User> users = UserManager.getInstance().getUsers();
        logger.debug("--------User: " + users.size() + " [userId | since | lock | name -----------------------");
        for (User user :users) {
            Session session = SessionManager.getInstance().getSession(user.getSessionId());
            String sessionState = "N";
            if (session != null) {
                if (session.isLocked()) {
                    sessionState = "L";
                } else {
                    sessionState = "+";
                }
            }
            logger.debug(user.getId()
                    + " | " + formatter.format(user.getConnectionTime())
                    + " | " + sessionState
                    + " | " + user.getName() +" (" +user.getUserState().toString() + " - " + user.getPingInfo() + ")");
        }
        ArrayList<ChatSession> chatSessions = ChatManager.getInstance().getChatSessions();
        logger.debug("------- ChatSessions: " + chatSessions.size() + " ----------------------------------");
        for (ChatSession chatSession: chatSessions) {
            logger.debug(chatSession.getChatId() + " " +formatter.format(chatSession.getCreateTime()) +" " + chatSession.getInfo()+ " "+ chatSession.getClients().values().toString());
        }
        logger.debug("------- Games: " + GameManager.getInstance().getNumberActiveGames() + " --------------------------------------------");
        logger.debug(" Active Game Worker: " + ThreadExecutor.getInstance().getActiveThreads(ThreadExecutor.getInstance().getGameExecutor()));
        for (Entry<UUID, GameController> entry: GameManager.getInstance().getGameController().entrySet()) {
            logger.debug(entry.getKey() + entry.getValue().getPlayerNameList());
        }
        logger.debug("--- Server state END ------------------------------------------");
    }
    
    private void checkTableHealthState() {
        if (logger.isDebugEnabled()) {
            debugServerState();
        }
        logger.debug("TABLE HEALTH CHECK");
        ArrayList<Table> tableCopy = new ArrayList<>();
        tableCopy.addAll(tables.values());
        for (Table table : tableCopy) {
            try {
                if (!table.getState().equals(TableState.FINISHED)) {
                    // remove tables and games not valid anymore
                    logger.debug(table.getId() + " [" + table.getName()+ "] " + formatter.format(table.getStartTime() == null ? table.getCreateTime() : table.getCreateTime()) +" (" + table.getState().toString() + ") " + (table.isTournament() ? "- Tournament":""));
                    TableController tableController = getController(table.getId());
                    if (tableController != null) {
                        if ((table.isTournament() && !tableController.isTournamentStillValid()) ||
                            (!table.isTournament() &&   !tableController.isMatchTableStillValid())) {
                            try {
                                logger.warn("Removing unhealthy tableId " + table.getId());
                                removeTable(table.getId());
                            } catch (Exception e) {
                                logger.error(e);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                logger.debug("Table Health check error tableId: " + table.getId());
                logger.debug(Arrays.toString(ex.getStackTrace()));
            }
        }
        logger.debug("TABLE HEALTH CHECK - END");

    }
}
