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

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import mage.MageException;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.InvalidDeckException;
import mage.constants.RangeOfInfluence;
import mage.constants.TableState;
import mage.game.Game;
import mage.game.GameException;
import mage.game.Seat;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.draft.DraftPlayer;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import static mage.game.events.TableEvent.EventType.SIDEBOARD;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.match.MatchPlayer;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentPlayer;
import mage.players.Player;
import mage.server.draft.DraftManager;
import mage.server.game.DeckValidatorFactory;
import mage.server.game.GameFactory;
import mage.server.game.GameManager;
import mage.server.game.PlayerFactory;
import mage.server.services.LogKeys;
import mage.server.services.impl.LogServiceImpl;
import mage.server.tournament.TournamentController;
import mage.server.tournament.TournamentFactory;
import mage.server.tournament.TournamentManager;
import mage.server.util.ConfigSettings;
import mage.server.util.ServerMessagesUtil;
import mage.server.util.ThreadExecutor;
import mage.view.ChatMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableController {

    private static final Logger logger = Logger.getLogger(TableController.class);

    private final UUID userId;
    private final UUID chatId;
    private final String controllerName;
    private final Table table;
    private final ConcurrentHashMap<UUID, UUID> userPlayerMap = new ConcurrentHashMap<>();

    private Match match;
    private MatchOptions options;
    private Tournament tournament;
    
    private ScheduledFuture<?> futureTimeout;
    protected static ScheduledExecutorService timeoutExecutor = ThreadExecutor.getInstance().getTimeoutExecutor();

    public TableController(UUID roomId, UUID userId, MatchOptions options) {
        this.userId = userId;
        this.options = options;
        match = GameFactory.getInstance().createMatch(options.getGameType(), options);
        if (userId != null) {
            User user = UserManager.getInstance().getUser(userId);
            // TODO: Handle if user == null
            controllerName = user.getName();
        } 
        else {
            controllerName = "System";
        }
        table = new Table(roomId, options.getGameType(), options.getName(), controllerName, DeckValidatorFactory.getInstance().createDeckValidator(options.getDeckType()), options.getPlayerTypes(), match);
        chatId = ChatManager.getInstance().createChatSession("Match Table " + table.getId());        
        init();
    }

    public TableController(UUID roomId, UUID userId, TournamentOptions options) {
        this.userId = userId;
        tournament = TournamentFactory.getInstance().createTournament(options.getTournamentType(), options);
        if (userId != null) {
            User user = UserManager.getInstance().getUser(userId);
            if (user == null) {
                logger.fatal(new StringBuilder("User for userId ").append(userId).append(" could not be retrieved from UserManager").toString());
                controllerName = "[unknown]";
            } else {
                controllerName = user.getName();
            }
        } 
        else {
            controllerName = "System";
        }
        table = new Table(roomId, options.getTournamentType(), options.getName(), controllerName, DeckValidatorFactory.getInstance().createDeckValidator(options.getMatchOptions().getDeckType()), options.getPlayerTypes(), tournament);
        chatId = ChatManager.getInstance().createChatSession("Tourn. table " + table.getId());        
    }

    private void init() {
        match.addTableEventListener(
            new Listener<TableEvent> () {
                @Override
                public void event(TableEvent event) {
                    try {
                        switch (event.getEventType()) {
                            case SIDEBOARD:
                                sideboard(event.getPlayerId(), event.getDeck());
                                break;
                        }
                    } catch (MageException ex) {
                        logger.fatal("Table event listener error", ex);
                    }
                }
            }
        );
    }

    public synchronized boolean joinTournament(UUID userId, String name, String playerType, int skill, DeckCardLists deckList, String password) throws GameException {
        if (table.getState() != TableState.WAITING) {
            return false;
        }

        Seat seat = table.getNextAvailableSeat(playerType);
        if (seat == null) {
            throw new GameException("No available seats.");
        }
        User user = UserManager.getInstance().getUser(userId);
        if (user == null) {
            logger.fatal(new StringBuilder("couldn't get user ").append(name).append(" for join tournament userId = ").append(userId).toString());
            return false;
        }
        // check password
        if (!table.getTournament().getOptions().getPassword().isEmpty() && playerType.equals("Human")) {
            if (!table.getTournament().getOptions().getPassword().equals(password)) {
                user.showUserMessage("Join Table", "Wrong password.");
                return false;
            }
        }
        if (userPlayerMap.containsKey(userId) && playerType.equals("Human")){
            user.showUserMessage("Join Table", new StringBuilder("You can join a table only one time.").toString());
            return false;
        }
        Deck deck = null;
        if (!table.getTournament().getTournamentType().isLimited()) {
            if  (deckList != null) {
                deck = Deck.load(deckList, false, false);
            } else {
                user.showUserMessage("Join Table", "No valid deck selected!");
                return false;
            }
            if (!Main.isTestMode() && !table.getValidator().validate(deck)) {
                StringBuilder sb = new StringBuilder("You (").append(name).append(") have an invalid deck for the selected ").append(table.getValidator().getName()).append(" Format. \n\n");
                for (Map.Entry<String, String> entry : table.getValidator().getInvalid().entrySet()) {
                    sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
                sb.append("\n\nSelect a deck that is appropriate for the selected format and try again!");
                user.showUserMessage("Join Table", sb.toString());
                if (isOwner(userId)) {
                    logger.debug("New table removed because owner submitted invalid deck tableId " + table.getId());
                    TableManager.getInstance().removeTable(table.getId());
                }
                return false;
            }
        }

        Player player = createPlayer(name, seat.getPlayerType(), skill);
        if (player != null) {
            if (!player.canJoinTable(table)) {
                user.showUserMessage("Join Table", new StringBuilder("A ").append(seat.getPlayerType()).append(" player can't join this table.").toString());
                return false;
            }
            tournament.addPlayer(player, seat.getPlayerType());
            TournamentPlayer tournamentPlayer = tournament.getPlayer(player.getId());
            if (deck != null && tournamentPlayer != null) {
                tournamentPlayer.submitDeck(deck);
            } 
            table.joinTable(player, seat);            
            logger.debug("Player " + player.getName() + " id: "+ player.getId() + " joined tableId: " + table.getId());
            //only inform human players and add them to sessionPlayerMap
            if (seat.getPlayer().isHuman()) {
                seat.getPlayer().setUserData(user.getUserData());
                user.addTable(player.getId(), table);
                user.ccJoinedTable(table.getRoomId(), table.getId(), true);
                userPlayerMap.put(userId, player.getId());
            }

            return true;
        } else {
            throw new GameException("Playertype " + seat.getPlayerType() + " could not be created.");
        }
    }

    public boolean hasPlayer(UUID userId) {
        return userPlayerMap.containsKey(userId);
    }

    public synchronized boolean replaceDraftPlayer(Player oldPlayer, String name, String playerType, int skill) {
        Player newPlayer = createPlayer(name, playerType, skill);
        if (newPlayer == null || table.getState() != TableState.DRAFTING) {
            return false;
        }
        TournamentPlayer oldTournamentPlayer = tournament.getPlayer(oldPlayer.getId());
        tournament.removePlayer(oldPlayer.getId());       
        tournament.addPlayer(newPlayer, playerType);

        TournamentPlayer newTournamentPlayer = tournament.getPlayer(newPlayer.getId());        
        newTournamentPlayer.setState(oldTournamentPlayer.getState());
        
        DraftManager.getInstance().getController(table.getId()).replacePlayer(oldPlayer, newPlayer);
        return true;
    }

    public synchronized boolean joinTable(UUID userId, String name, String playerType, int skill, DeckCardLists deckList, String password) throws MageException {
        User user = UserManager.getInstance().getUser(userId);
        if (user == null) {
            return false;
        }
        if (userPlayerMap.containsKey(userId) && playerType.equals("Human")){
            user.showUserMessage("Join Table", new StringBuilder("You can join a table only one time.").toString());
            return false;
        }
        if (table.getState() != TableState.WAITING) {
            user.showUserMessage("Join Table", "No available seats.");
            return false;
        }
        // check password
        if (!table.getMatch().getOptions().getPassword().isEmpty() && playerType.equals("Human")) {
            if (!table.getMatch().getOptions().getPassword().equals(password)) {
                user.showUserMessage("Join Table", "Wrong password.");
                return false;
            }
        }
        Seat seat = table.getNextAvailableSeat(playerType);
        if (seat == null) {
            user.showUserMessage("Join Table", "No available seats.");
            return false;
        }
        Deck deck = Deck.load(deckList, false, false);

        if (!Main.isTestMode() && !table.getValidator().validate(deck)) {
            StringBuilder sb = new StringBuilder("You (").append(name).append(") have an invalid deck for the selected ").append(table.getValidator().getName()).append(" Format. \n\n");
            for (Map.Entry<String, String> entry : table.getValidator().getInvalid().entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            sb.append("\n\nSelect a deck that is appropriate for the selected format and try again!");
            user.showUserMessage("Join Table", sb.toString());
            if (isOwner(userId)) {
                logger.debug("New table removed because owner submitted invalid deck tableId " + table.getId());
                TableManager.getInstance().removeTable(table.getId());
            }
            return false;
        }
        Player player = createPlayer(name, seat.getPlayerType(), skill);
        if (player == null) {
            String message = new StringBuilder("Could not create player ").append(name).append(" of type ").append(seat.getPlayerType()).toString();
            logger.warn(new StringBuilder("User: ").append(user.getName()).append(" => ").append(message).toString());
            user.showUserMessage("Join Table",message);
            return false;
        }
        logger.debug("DECK validated: " + table.getValidator().getName() + " " + player.getName() + " " + deck.getName());
        if (!player.canJoinTable(table)) {
            user.showUserMessage("Join Table", new StringBuilder("A ").append(seat.getPlayerType()).append(" player can't join this table.").toString());
            return false;
        }
        match.addPlayer(player, deck);
        table.joinTable(player, seat);
        logger.trace(player.getName() + " joined tableId: " + table.getId());
        //only inform human players and add them to sessionPlayerMap
        if (seat.getPlayer().isHuman()) {
            seat.getPlayer().setUserData(user.getUserData());
            if (!table.isTournamentSubTable()) {
                user.addTable(player.getId(), table);
            }
            user.ccJoinedTable(table.getRoomId(), table.getId(), false);
            userPlayerMap.put(userId, player.getId());
        }
        return true;
    }

    public void addPlayer(UUID userId, Player player, String playerType, Deck deck) throws GameException  {
        if (table.getState() != TableState.WAITING) {
            return;
        }
        Seat seat = table.getNextAvailableSeat(playerType);
        if (seat == null) {
            throw new GameException("No available seats.");
        }
        match.addPlayer(player, deck);
        table.joinTable(player, seat);
        if (player.isHuman()) {
            userPlayerMap.put(userId, player.getId());
        }
    }

    public synchronized boolean submitDeck(UUID userId, DeckCardLists deckList) throws MageException {
        UUID playerId = userPlayerMap.get(userId);
        if (table.isTournament()) {
            TournamentPlayer player = tournament.getPlayer(playerId);
            if (player == null || player.hasQuit()) {
                return true; // so the construct panel closes after submit
            }
        } else {
            if (table.getMatch() != null) {
                MatchPlayer mPlayer = table.getMatch().getPlayer(playerId);
                if (mPlayer == null || mPlayer.hasQuit()) {
                    return true; // so the construct panel closes after submit
                }
                if (table.isTournamentSubTable()) {
                    TournamentPlayer tournamentPlayer = table.getTournament().getPlayer(mPlayer.getPlayer().getId());
                    if (tournamentPlayer != null) {
                        tournamentPlayer.setStateInfo(""); // reset sideboarding state
                    }
                }
            }
        }
        if (table.getState() != TableState.SIDEBOARDING && table.getState() != TableState.CONSTRUCTING) {
            return false;
        }
        Deck deck = Deck.load(deckList, false, false);
        if (table.getState() == TableState.SIDEBOARDING && table.getMatch() != null) {
            MatchPlayer mPlayer = table.getMatch().getPlayer(playerId);
            if (mPlayer != null) {
                deck.setName(mPlayer.getDeck().getName());
            }
        }
        if (!Main.isTestMode() && !table.getValidator().validate(deck)) {
            throw new InvalidDeckException("Invalid deck for this format", table.getValidator().getInvalid());
        }
        submitDeck(userId, playerId, deck);
        return true;
    }

    public void updateDeck(UUID userId, DeckCardLists deckList) throws MageException {
        UUID playerId = userPlayerMap.get(userId);
        if (table.getState() != TableState.SIDEBOARDING && table.getState() != TableState.CONSTRUCTING) {
            return;
        }
        Deck deck = Deck.load(deckList, false, false);
        updateDeck(userId, playerId, deck);
    }

    private void submitDeck(UUID userId, UUID playerId, Deck deck) {
        if (table.getState() == TableState.SIDEBOARDING) {
            match.submitDeck(playerId, deck);
            UserManager.getInstance().getUser(userId).removeSideboarding(table.getId());
        }
        else {
            TournamentManager.getInstance().submitDeck(tournament.getId(), playerId, deck);
            UserManager.getInstance().getUser(userId).removeConstructing(playerId);
        }
    }

    private void updateDeck(UUID userId, UUID playerId, Deck deck) {
        if (table.isTournament()) {
            if (tournament != null) {
                TournamentManager.getInstance().updateDeck(tournament.getId(), playerId, deck);
            } else {
                logger.fatal("Tournament == null  table: " + table.getId() +" userId: " + userId);
            }
        } else {
            if (TableState.SIDEBOARDING.equals(table.getState())) {
                match.updateDeck(playerId, deck);
            } else {
                // deck was meanwhile submitted so the autoupdate can be ignored
            }
        }
    }

    public boolean watchTable(UUID userId) {
        if (table.isTournament()) {
            UserManager.getInstance().getUser(userId).ccShowTournament(table.getTournament().getId());
            return true;
        } else {
            if (table.isTournamentSubTable() && !table.getTournament().getOptions().isWatchingAllowed()) {
                return false;
            }
            if (table.getState() != TableState.DUELING) {
                return false;
            }
            // you can't watch your own game
            if (userPlayerMap.get(userId) != null) {
                return false;
            }
            return UserManager.getInstance().getUser(userId).ccWatchGame(match.getGame().getId());
        }
    }

//    public boolean replayTable(UUID userId) {
//        if (table.getState() != TableState.FINISHED) {
//            return false;
//        }
//        ReplayManager.getInstance().replayGame(table.getId(), userId);
//        return true;
//    }

    private Player createPlayer(String name, String playerType, int skill) {
        Player player;
        if (options == null) {
            player = PlayerFactory.getInstance().createPlayer(playerType, name, RangeOfInfluence.ALL, skill);
        }
        else {
            player = PlayerFactory.getInstance().createPlayer(playerType, name, options.getRange(), skill);
        }
        if (player != null) {
            logger.trace("Player " + player.getName() + " created id: " + player.getId());
        }
        return player;
    }

    public void leaveTableAll() {
        for (UUID leavingUserId: userPlayerMap.keySet()) {
            leaveTable(leavingUserId);
        }
        closeTable();
    }
    
    public synchronized void leaveTable(UUID userId) {
        if (table == null) {
            logger.error("No table object - userId: " + userId);
            return;
        }
        if (table.isTournament() && tournament == null) {
            logger.error("No tournament object - userId: " + userId + "  table: " + table.getId());
            return;
        }
        if (table != null
                && this.userId != null && this.userId.equals(userId) // tourn. sub tables have no creator user
                && (table.getState().equals(TableState.WAITING)
                || table.getState().equals(TableState.READY_TO_START))) {
            // table not started yet and user is the owner, remove the table
            TableManager.getInstance().removeTable(table.getId());
        } else {
            UUID playerId = userPlayerMap.get(userId);
            if (playerId != null) {
                if (table.getState() == TableState.WAITING || table.getState() == TableState.READY_TO_START) {
                    table.leaveNotStartedTable(playerId);
                    if (table.isTournament()) {
                        tournament.removePlayer(playerId);
                    } else {
                        match.quitMatch(playerId);
                    }
                    User user = UserManager.getInstance().getUser(userId);
                    if (user != null) {
                        ChatManager.getInstance().broadcast(chatId, user.getName(), "has left the table", ChatMessage.MessageColor.BLUE, true, ChatMessage.MessageType.STATUS, ChatMessage.SoundToPlay.PlayerLeft);
                        if (!table.isTournamentSubTable()) {
                            user.removeTable(playerId);
                        }
                    } else {
                        logger.debug("User not found - userId: " + userId + " tableId:" + table.getId());
                    }
                    userPlayerMap.remove(userId);
                } else if (!table.getState().equals(TableState.FINISHED)) {
                    if (table.isTournament()) {
                        logger.debug("Quit tournament sub tables for userId: " + userId);
                        TableManager.getInstance().userQuitTournamentSubTables(tournament.getId(), userId);
                        logger.debug("Quit tournament  Id: " + table.getTournament().getId() + "(" +table.getTournament().getTournamentState() + ")");
                        TournamentManager.getInstance().quit(tournament.getId(), userId);
                    } else {
                        MatchPlayer matchPlayer = match.getPlayer(playerId);
                        if (matchPlayer != null && !match.hasEnded() && !matchPlayer.hasQuit()) {
                            Game game = match.getGame();
                            if (game != null && !game.hasEnded()){
                                Player player = match.getPlayer(playerId).getPlayer();
                                if (player != null && player.isInGame()) {
                                    GameManager.getInstance().quitMatch(game.getId(), userId);
                                }
                                match.quitMatch(playerId);
                            } else {                                
                                if (table.getState().equals(TableState.SIDEBOARDING)) {
                                    if (!matchPlayer.isDoneSideboarding()) {
                                        // submit deck to finish sideboarding and trigger match start / end
                                        matchPlayer.submitDeck(matchPlayer.getDeck());
                                    }
                                }
                                match.quitMatch(playerId);
                            }
                        }
                    }
                }
            } else {
                logger.error("No playerId found for userId: " + userId);
            }
        }
    }

    /**
     * Used from non tournament match to start
     * 
     * @param userId owner of the tabel
     */
    public synchronized void startMatch(UUID userId) {
        if (isOwner(userId)) {
            startMatch();
        }
    }

    public synchronized void startMatch() {
        if (table.getState().equals(TableState.STARTING)) {
            try {
                if (table.isTournamentSubTable()) {
                    logger.info("Tourn. match started id:" + match.getId() + " tournId: " + table.getTournament().getId());
                } else {
                    User user = UserManager.getInstance().getUser(userId);
                    logger.info("MATCH started [" + match.getName() + "] " + match.getId() + "(" + user.getName() +")");
                    logger.debug("- " + match.getOptions().getGameType() + " - " + match.getOptions().getDeckType());
                }
                match.startMatch();
                startGame(null);
            } catch (GameException ex) {
                logger.fatal("Error starting match ", ex);
                match.endGame();
            }
        }
    }

    private void startGame(UUID choosingPlayerId) throws GameException {
        try {
            match.startGame();
            table.initGame();
            GameManager.getInstance().createGameSession(match.getGame(), userPlayerMap, table.getId(), choosingPlayerId);
            String creator = null;
            StringBuilder opponent = new StringBuilder();
            for (Entry<UUID, UUID> entry: userPlayerMap.entrySet()) { // no AI players
                if (!match.getPlayer(entry.getValue()).hasQuit()) {
                    User user = UserManager.getInstance().getUser(entry.getKey());
                    if (user != null) {
                        Player player = match.getPlayer(entry.getValue()).getPlayer();
                        user.ccGameStarted(match.getGame().getId(), entry.getValue());
                        
                        if (creator == null) {
                            creator = user.getName();
                        } else {
                            if (opponent.length() > 0) {
                                opponent.append(" - ");
                            }
                            opponent.append(user.getName());
                        }
                    } else {
                        logger.error("Unable to find user: " + entry.getKey() + "  playerId: " + entry.getValue());
                        MatchPlayer matchPlayer = match.getPlayer(entry.getValue());
                        if (matchPlayer != null && !matchPlayer.hasQuit()) {
                            matchPlayer.setQuit(true);
                        }
                    }
                }
            }
            // Append AI opponents to the log file
            for (MatchPlayer mPlayer :match.getPlayers()) {
                if (!mPlayer.getPlayer().isHuman()) {
                    if (opponent.length() > 0) {
                        opponent.append(" - ");
                    }
                    opponent.append(mPlayer.getName());
                }
            }
            ServerMessagesUtil.getInstance().incGamesStarted();


            // log about game started
            logger.info("GAME started " + match.getGame().getId() + " [" + match.getName() +"] "+ creator + " - " + opponent.toString());
            logger.debug("- matchId: " + match.getId() + " [" + match.getName() + "]");
            if (match.getGame() != null) {
                logger.debug("- chatId:  " + GameManager.getInstance().getChatId(match.getGame().getId()));
            } else {
                logger.debug("- no valid game object");
            }
            LogServiceImpl.instance.log(LogKeys.KEY_GAME_STARTED, String.valueOf(userPlayerMap.size()), creator, opponent.toString());
        }
        catch (Exception ex) {
            logger.fatal("Error starting game", ex);
            if (table != null) {
                TableManager.getInstance().removeTable(table.getId());
            }
            if (match != null) {
                Game game = match.getGame();
                if (game != null) {
                    GameManager.getInstance().removeGame(game.getId());
                }
            }
        }
    }

    public synchronized void startTournament(UUID userId) {
        try {            
            if (userId.equals(this.userId) && table.getState().equals(TableState.STARTING)) {
                tournament.setStartTime();
                TournamentManager.getInstance().createTournamentSession(tournament, userPlayerMap, table.getId());
                for (Entry<UUID, UUID> entry: userPlayerMap.entrySet()) {
                    User user = UserManager.getInstance().getUser(entry.getKey());
                    if (user != null) {
                        logger.info(new StringBuilder("User ").append(user.getName()).append(" tournament started: ").append(tournament.getId()).append(" userId: ").append(user.getId()));
                        user.ccTournamentStarted(tournament.getId(), entry.getValue());
                    }
                }
                ServerMessagesUtil.getInstance().incTournamentsStarted();
            }
        }
        catch (Exception ex) {
            logger.fatal("Error starting tournament", ex);
            TableManager.getInstance().removeTable(table.getId());
            TournamentManager.getInstance().quit(tournament.getId(), userId);
        }
    }

    public void startDraft(Draft draft) {
        table.initDraft();
        DraftManager.getInstance().createDraftSession(draft, userPlayerMap, table.getId());
        for (Entry<UUID, UUID> entry: userPlayerMap.entrySet()) {
            User user = UserManager.getInstance().getUser(entry.getKey());
            if (user != null) {
                logger.info(new StringBuilder("User ").append(user.getName()).append(" draft started: ").append(draft.getId()).append(" userId: ").append(user.getId()));
                user.ccDraftStarted(draft.getId(), entry.getValue());
            } else {
                logger.fatal(new StringBuilder("Start draft user not found userId: ").append(entry.getKey()));
            }
        }
    }

    private void sideboard(UUID playerId, Deck deck) throws MageException {
        for (Entry<UUID, UUID> entry: userPlayerMap.entrySet()) {
            if (entry.getValue().equals(playerId)) {
                User user = UserManager.getInstance().getUser(entry.getKey());
                int remaining = (int) futureTimeout.getDelay(TimeUnit.SECONDS);
                if (user != null) {
                    user.ccSideboard(deck, table.getId(), remaining, options.isLimited());
                }
                break;
            }
        }
    }

    public int getRemainingTime() {
        return (int) futureTimeout.getDelay(TimeUnit.SECONDS);
    }    

    public void construct() {
        table.construct();
    }

    public void initTournament() {
        table.initTournament();
    }

    public void endTournament(Tournament tournament) {
        table.endTournament();
    }

    public MatchOptions getOptions() {
        return options;
    }

    /**
     * Ends the current game and starts if neccessary the next game
     *
     * @return true if table can be closed
     */
    public boolean endGameAndStartNextGame() {
        // get player that chooses who goes first
        Game game = match.getGame();
        if (game == null) {
            return true;
        }
        UUID choosingPlayerId = match.getChooser();
        match.endGame();
        if (ConfigSettings.getInstance().isSaveGameActivated() && !game.isSimulation()) {
            if (GameManager.getInstance().saveGame(game.getId())) {
                match.setReplayAvailable(true);
            }
        }
        GameManager.getInstance().removeGame(game.getId());
        try {
            if (!match.hasEnded()) {
                if (match.getGame() != null && match.getGame().getGameType().isSideboardingAllowed()) {
                    sideboard();
                }
                if (!match.hasEnded()) {
                    startGame(choosingPlayerId);
                } else {
                    closeTable();
                }
            }
            else {
                closeTable();
            }
        } catch (GameException ex) {
            logger.fatal(null, ex);
        }
        return match.hasEnded();
    }

    private void sideboard() {
        table.sideboard();
        setupTimeout(Match.SIDEBOARD_TIME);
        if (table.isTournamentSubTable()) {
            for (MatchPlayer matchPlayer :match.getPlayers()) {
                if (!matchPlayer.hasQuit()) {
                    TournamentPlayer tournamentPlayer = table.getTournament().getPlayer(matchPlayer.getPlayer().getId());
                    if (tournamentPlayer != null) {
                        tournamentPlayer.setStateInfo("sideboarding");
                    }
                }
            }
        }
        match.sideboard();
        cancelTimeout();
        if (table.isTournamentSubTable()) {
            for (MatchPlayer matchPlayer :match.getPlayers()) {
                TournamentPlayer tournamentPlayer = table.getTournament().getPlayer(matchPlayer.getPlayer().getId());
                if (tournamentPlayer != null && tournamentPlayer.getStateInfo().equals("sideboarding")) {
                    tournamentPlayer.setStateInfo("");
                }
            }
        }
    }
    /**
     * Tables of normal matches or tournament sub tables are no longer
     * needed, if the match ends.
     * 
     */
    private void closeTable() {
        this.matchEnd();
        table.closeTable();
    }

    private void matchEnd() {
        if (match != null) {
            for (Entry<UUID, UUID> entry: userPlayerMap.entrySet()) {
                MatchPlayer matchPlayer = match.getPlayer(entry.getValue());
                // opponent(s) left during sideboarding
                if (matchPlayer != null) {
                    if(!matchPlayer.hasQuit()) {
                        User user = UserManager.getInstance().getUser(entry.getKey());
                        if (user != null) {
                            if (table.getState().equals(TableState.SIDEBOARDING)) {
                                StringBuilder sb = new StringBuilder();
                                if (table.isTournamentSubTable()) {
                                    sb.append("Your tournament match of round ");
                                    sb.append(table.getTournament().getRounds().size());
                                    sb.append(" is over. ");
                                } else {
                                    sb.append("Match [").append(match.getName()).append("] is over. ");
                                }
                                if (match.getPlayers().size() > 2) {
                                    sb.append("All your opponents have lost or quit the match.");
                                } else {
                                    sb.append("Your opponent has quit the match.");
                                }
                                user.showUserMessage("Match info", sb.toString());
                            }
                            // remove table from user - table manager holds table for display of finished matches
                            if (!table.isTournamentSubTable()) {
                                user.removeTable(entry.getValue());
                            }
                        }
                    }
                }
            }
            // free resources no longer needed
            match.cleanUpOnMatchEnd(ConfigSettings.getInstance().isSaveGameActivated(), table.isTournament());
        }
    }

    private synchronized void setupTimeout(int seconds) {
        cancelTimeout();
        if (seconds > 0) {
            futureTimeout = timeoutExecutor.schedule(
                new Runnable() {
                    @Override
                    public void run() {
                        autoSideboard();
                    }
                },
                seconds, TimeUnit.SECONDS
            );
        }
    }

    private synchronized void cancelTimeout() {
        if (futureTimeout != null) {
            futureTimeout.cancel(false);
        }
    }

    private void autoSideboard() {
        for (MatchPlayer player: match.getPlayers()) {
            if (!player.isDoneSideboarding()) {
                match.submitDeck(player.getPlayer().getId(), player.generateDeck());
            }
        }
    }

    public void endDraft(Draft draft) {
        if (!draft.isAbort()) {
            for (DraftPlayer player: draft.getPlayers()) {
                player.prepareDeck();
                tournament.getPlayer(player.getPlayer().getId()).setDeck(player.getDeck());
            }
        }
        tournament.clearDraft(); // free the draft resources after draft step has ended
        tournament.nextStep();
    }

    public void swapSeats(int seatNum1, int seatNum2) {
        if (table.getState().equals(TableState.READY_TO_START)) {
            if (seatNum1 >= 0 && seatNum2 >= 0 && seatNum1 < table.getSeats().length && seatNum2 < table.getSeats().length) {
                Player swapPlayer = table.getSeats()[seatNum1].getPlayer();
                String swapType = table.getSeats()[seatNum1].getPlayerType();
                table.getSeats()[seatNum1].setPlayer(table.getSeats()[seatNum2].getPlayer());
                table.getSeats()[seatNum1].setPlayerType(table.getSeats()[seatNum2].getPlayerType());
                table.getSeats()[seatNum2].setPlayer(swapPlayer);
                table.getSeats()[seatNum2].setPlayerType(swapType);
            }
        }
    }

    public boolean isOwner(UUID userId) {
        if (userId == null) {
            return false;
        }
        return userId.equals(this.userId);
    }

    public Table getTable() {
        return table;
    }

    public UUID getChatId() {
        return chatId;
    }

    public Match getMatch() {
        return match;
    }

    public boolean isTournamentStillValid() {
        if (table.getTournament() != null) {
            if (!table.getState().equals(TableState.WAITING) && !table.getState().equals(TableState.READY_TO_START) && !table.getState().equals(TableState.STARTING) ) {
                TournamentController tournamentController = TournamentManager.getInstance().getTournamentController(table.getTournament().getId());
                if (tournamentController != null) {
                    return tournamentController.isTournamentStillValid(table.getState());
                } else {
                    return false;
                }
            } else {
                // check if table creator is still a valid user, if not remove table
                User user = UserManager.getInstance().getUser(userId);
                return user != null;
            }
        }
        return false;
    }

    public UUID getUserId(UUID playerId) {
        for (Map.Entry<UUID, UUID> entry: userPlayerMap.entrySet()) {
            if (entry.getValue().equals(playerId)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isMatchTableStillValid() {
        // check only normal match table with state != Finished
        if (!table.isTournament()) {
            int humanPlayers = 0;
            int aiPlayers = 0 ;
            int validHumanPlayers = 0;
            if (!(table.getState().equals(TableState.WAITING) || table.getState().equals(TableState.STARTING) || table.getState().equals(TableState.READY_TO_START))) {
                if (match == null) {
                    logger.debug("- Match table with no match:");
                    logger.debug("-- matchId:" + match.getId() + " [" + match.getName() + "]");
                    // return false;
                } else {
                    if (match.isDoneSideboarding() && match.getGame() == null) {
                        // no sideboarding and not active game -> match seems to hang (maybe the Draw bug)
                        logger.debug("- Match with no active game and not in sideboard state:");
                        logger.debug("-- matchId:" + match.getId() + " [" + match.getName() + "]");
                        // return false;
                    }
                }
            }
            // check for active players
            for(Map.Entry<UUID, UUID> userPlayerEntry: userPlayerMap.entrySet()) {
                MatchPlayer matchPlayer = match.getPlayer(userPlayerEntry.getValue());
                if (matchPlayer == null) {
                    logger.debug("- Match player not found:");
                    logger.debug("-- matchId:" + match.getId());
                    logger.debug("-- userId:" + userPlayerEntry.getKey());
                    logger.debug("-- playerId:" + userPlayerEntry.getValue());
                    continue;
                }
                if (matchPlayer.getPlayer().isHuman()) {
                    humanPlayers++;                    
                    if ((table.getState().equals(TableState.WAITING) || 
                         table.getState().equals(TableState.STARTING) ||
                         table.getState().equals(TableState.READY_TO_START)) ||
                            !match.isDoneSideboarding() || 
                            (!matchPlayer.hasQuit() && match.getGame() != null && matchPlayer.getPlayer().isInGame())) {
                        User user = UserManager.getInstance().getUser(userPlayerEntry.getKey());
                        if (user == null) {
                            logger.debug("- Active user of match is missing: " + matchPlayer.getName());
                            logger.debug("-- matchId:" + match.getId());
                            logger.debug("-- userId:" + userPlayerEntry.getKey());
                            logger.debug("-- playerId:" + userPlayerEntry.getValue());
                            return false;
                        }
                        // user exits on the server and match player has not quit -> player is valid
                        validHumanPlayers++;
                    }
                } else {
                    aiPlayers++;
                }
            }
            // if at least 2 human players are valid (multiplayer) or all human players are valid the table is valid or it's an AI match
            return validHumanPlayers >= 2 || validHumanPlayers == humanPlayers || aiPlayers > 1;
        }
        return true;
    }
    
    void cleanUp() {
        if (!table.isTournamentSubTable()) {
            for(Map.Entry<UUID, UUID> entry: userPlayerMap.entrySet()) {
                User user = UserManager.getInstance().getUser(entry.getKey());
                if (user != null) {
                    user.removeTable(entry.getValue());
                }
            }
        }
        ChatManager.getInstance().destroyChatSession(chatId);
    }

    public synchronized TableState getTableState() {
        return getTable().getState();
    }
            
    public synchronized boolean changeTableState(TableState newTableState) {
        switch (newTableState) {
            case WAITING:
                if (getTable().getState().equals(TableState.STARTING)){
                    // tournament already started
                    return false;
                }
                break;
            case STARTING:
                if (!getTable().getState().equals(TableState.READY_TO_START)) {
                    // tournament is not ready, can't start
                    return false;
                }
                if (!table.allSeatsAreOccupied()) {
                    logger.debug("Not alle Seats are occupied: stop start tableId:" + table.getId());
                    return false;
                }
                break;
        }
        getTable().setState(newTableState);
        return true;
    }
}
