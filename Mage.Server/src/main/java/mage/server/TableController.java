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

import mage.MageException;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.InvalidDeckException;
import mage.constants.RangeOfInfluence;
import mage.constants.TableState;
import mage.game.*;
import mage.game.draft.Draft;
import mage.game.draft.DraftPlayer;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.match.MatchPlayer;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentPlayer;
import mage.players.Player;
import mage.players.PlayerType;
import mage.server.draft.DraftManager;
import mage.server.game.DeckValidatorFactory;
import mage.server.game.GameFactory;
import mage.server.game.GameManager;
import mage.server.game.PlayerFactory;
import mage.server.record.TableRecorderImpl;
import mage.server.tournament.TournamentController;
import mage.server.tournament.TournamentFactory;
import mage.server.tournament.TournamentManager;
import mage.server.util.ConfigSettings;
import mage.server.util.ServerMessagesUtil;
import mage.server.util.ThreadExecutor;
import mage.view.ChatMessage;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
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
    protected static final ScheduledExecutorService timeoutExecutor = ThreadExecutor.instance.getTimeoutExecutor();

    public TableController(UUID roomId, UUID userId, MatchOptions options) {
        this.userId = userId;
        this.options = options;
        match = GameFactory.instance.createMatch(options.getGameType(), options);
        if (userId != null) {
            Optional<User> user = UserManager.instance.getUser(userId);
            // TODO: Handle if user == null
            controllerName = user.map(User::getName).orElse("undefined");
        } else {
            controllerName = "System";
        }
        table = new Table(roomId, options.getGameType(), options.getName(), controllerName, DeckValidatorFactory.instance.createDeckValidator(options.getDeckType()), options.getPlayerTypes(), TableRecorderImpl.instance, match, options.getBannedUsers());
        chatId = ChatManager.instance.createChatSession("Match Table " + table.getId());
        init();
    }

    public TableController(UUID roomId, UUID userId, TournamentOptions options) {
        this.userId = userId;
        tournament = TournamentFactory.instance.createTournament(options.getTournamentType(), options);
        if (userId != null) {
            Optional<User> user = UserManager.instance.getUser(userId);
            if (!user.isPresent()) {
                logger.fatal(new StringBuilder("User for userId ").append(userId).append(" could not be retrieved from UserManager").toString());
                controllerName = "[unknown]";
            } else {
                controllerName = user.get().getName();
            }
        } else {
            controllerName = "System";
        }
        table = new Table(roomId, options.getTournamentType(), options.getName(), controllerName, DeckValidatorFactory.instance.createDeckValidator(options.getMatchOptions().getDeckType()), options.getPlayerTypes(), TableRecorderImpl.instance, tournament, options.getMatchOptions().getBannedUsers());
        chatId = ChatManager.instance.createChatSession("Tourn. table " + table.getId());
    }

    private void init() {
        match.addTableEventListener(
                (Listener<TableEvent>) event -> {
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
        );
    }

    public synchronized boolean joinTournament(UUID userId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws GameException {
        if (table.getState() != TableState.WAITING) {
            return false;
        }

        Seat seat = table.getNextAvailableSeat(playerType);
        if (seat == null) {
            throw new GameException("No available seats.");
        }
        Optional<User> _user = UserManager.instance.getUser(userId);
        if (!_user.isPresent()) {
            logger.fatal(new StringBuilder("couldn't get user ").append(name).append(" for join tournament userId = ").append(userId).toString());
            return false;
        }
        User user = _user.get();
        // check password
        if (!table.getTournament().getOptions().getPassword().isEmpty() && playerType == PlayerType.HUMAN) {
            if (!table.getTournament().getOptions().getPassword().equals(password)) {
                user.showUserMessage("Join Table", "Wrong password.");
                return false;
            }
        }
        if (userPlayerMap.containsKey(userId) && playerType == PlayerType.HUMAN) {
            user.showUserMessage("Join Table", "You can join a table only one time.");
            return false;
        }
        Deck deck = null;
        if (!table.getTournament().getTournamentType().isLimited()) {
            if (deckList != null) {
                deck = Deck.load(deckList, false, false);
            } else {
                user.showUserMessage("Join Table", "No valid deck selected!");
                return false;
            }
            if (!Main.isTestMode() && !table.getValidator().validate(deck)) {
                StringBuilder sb = new StringBuilder("You (").append(name).append(") have an invalid deck for the selected ").append(table.getValidator().getName()).append(" Format. \n\n");
                for (Map.Entry<String, String> entry : table.getValidator().getInvalid().entrySet()) {
                    sb.append(entry.getKey()).append(": ").append(entry.getValue()).append('\n');
                }
                sb.append("\n\nSelect a deck that is appropriate for the selected format and try again!");
                user.showUserMessage("Join Table", sb.toString());
                if (isOwner(userId)) {
                    logger.debug("New table removed because owner submitted invalid deck tableId " + table.getId());
                    TableManager.instance.removeTable(table.getId());
                }
                return false;
            }
        }
        // Check quit ratio.
        int quitRatio = table.getTournament().getOptions().getQuitRatio();
        if (quitRatio < user.getTourneyQuitRatio()) {
            String message = new StringBuilder("Your quit ratio ").append(user.getTourneyQuitRatio())
                    .append("% is higher than the table requirement ").append(quitRatio).append('%').toString();
            user.showUserMessage("Join Table", message);
            return false;
        }

        Optional<Player> playerOptional = createPlayer(name, seat.getPlayerType(), skill);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
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
            logger.debug("Player " + player.getName() + " id: " + player.getId() + " joined tableId: " + table.getId());
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

    public synchronized boolean replaceDraftPlayer(Player oldPlayer, String name, PlayerType playerType, int skill) {
        Optional<Player> newPlayerOpt = createPlayer(name, playerType, skill);
        if (!newPlayerOpt.isPresent() || table.getState() != TableState.DRAFTING) {
            return false;
        }
        Player newPlayer = newPlayerOpt.get();
        TournamentPlayer oldTournamentPlayer = tournament.getPlayer(oldPlayer.getId());
        tournament.removePlayer(oldPlayer.getId());
        tournament.addPlayer(newPlayer, playerType);

        TournamentPlayer newTournamentPlayer = tournament.getPlayer(newPlayer.getId());
        newTournamentPlayer.setState(oldTournamentPlayer.getState());
        newTournamentPlayer.setReplacedTournamentPlayer(oldTournamentPlayer);

        DraftManager.instance.getController(table.getId()).ifPresent(controller -> controller.replacePlayer(oldPlayer, newPlayer));
        return true;
    }

    public synchronized boolean joinTable(UUID userId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws MageException {
        Optional<User> _user = UserManager.instance.getUser(userId);
        if (!_user.isPresent()) {
            return false;
        }
        User user = _user.get();
        if (userPlayerMap.containsKey(userId) && playerType == PlayerType.HUMAN) {
            user.showUserMessage("Join Table", new StringBuilder("You can join a table only one time.").toString());
            return false;
        }
        if (table.getState() != TableState.WAITING) {
            user.showUserMessage("Join Table", "No available seats.");
            return false;
        }
        // check password
        if (!table.getMatch().getOptions().getPassword().isEmpty() && playerType == PlayerType.HUMAN) {
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
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append('\n');
            }
            sb.append("\n\nSelect a deck that is appropriate for the selected format and try again!");
            user.showUserMessage("Join Table", sb.toString());
            if (isOwner(userId)) {
                logger.debug("New table removed because owner submitted invalid deck tableId " + table.getId());
                TableManager.instance.removeTable(table.getId());
            }
            return false;
        }
        // Check quit ratio.
        int quitRatio = table.getMatch().getOptions().getQuitRatio();
        if (quitRatio < user.getMatchQuitRatio()) {
            String message = new StringBuilder("Your quit ratio ").append(user.getMatchQuitRatio())
                    .append("% is higher than the table requirement ").append(quitRatio).append('%').toString();
            user.showUserMessage("Join Table", message);
            return false;
        }

        // Check power level for table (currently only used for EDH/Commander table)
        int edhPowerLevel = table.getMatch().getOptions().getEdhPowerLevel();
        if (edhPowerLevel > 0 && table.getValidator().getName().toLowerCase().equals("commander")) {
            int deckEdhPowerLevel = table.getValidator().getEdhPowerLevel(deck);
            if (deckEdhPowerLevel % 100 > edhPowerLevel) {
                String message = new StringBuilder("Your deck appears to be too powerful for this table.\n\nReduce the number of extra turn cards, infect, counters, fogs, reconsider your commander. ")
                        .append("\nThe table requirement has a maximum power level of ").append(edhPowerLevel).append(" whilst your deck has a calculated power level of ")
                        .append(deckEdhPowerLevel % 100).toString();
                user.showUserMessage("Join Table", message);
                return false;
            }

            boolean restrictedColor = false;
            String badColor = "";
            int colorVal = edhPowerLevel % 10;
            if (colorVal == 6 && deckEdhPowerLevel >= 10000000) {
                restrictedColor = true;
                badColor = "white";
            }
            if (colorVal == 4 && deckEdhPowerLevel % 10000000 >= 1000000) {
                restrictedColor = true;
                badColor = "blue";
            }
            if (colorVal == 3 && deckEdhPowerLevel % 1000000 >= 100000) {
                restrictedColor = true;
                badColor = "black";
            }
            if (colorVal == 2 && deckEdhPowerLevel % 100000 >= 10000) {
                restrictedColor = true;
                badColor = "red";
            }
            if (colorVal == 1 && deckEdhPowerLevel % 10000 >= 1000) {
                restrictedColor = true;
                badColor = "green";
            }
            if (restrictedColor) {
                String message = new StringBuilder("Your deck contains ")
                        .append(badColor)
                        .append(".  The creator of the table has requested no ")
                        .append(badColor)
                        .append(" cards to be on the table!").toString();
                user.showUserMessage("Join Table", message);
                return false;
            }
        }

        Optional<Player> playerOpt = createPlayer(name, seat.getPlayerType(), skill);
        if (!playerOpt.isPresent()) {
            String message = new StringBuilder("Could not create player ").append(name).append(" of type ").append(seat.getPlayerType()).toString();
            logger.warn(new StringBuilder("User: ").append(user.getName()).append(" => ").append(message).toString());
            user.showUserMessage("Join Table", message);
            return false;
        }
        Player player = playerOpt.get();
        logger.debug("DECK validated: " + table.getValidator().getName() + ' ' + player.getName() + ' ' + deck.getName());
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

    public void addPlayer(UUID userId, Player player, PlayerType playerType, Deck deck) throws GameException {
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
        } else if (table.getMatch() != null) {
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
            UserManager.instance.getUser(userId).ifPresent(user -> user.removeSideboarding(table.getId()));
        } else {
            TournamentManager.instance.submitDeck(tournament.getId(), playerId, deck);
            UserManager.instance.getUser(userId).ifPresent(user -> user.removeConstructing(playerId));
        }
    }

    private void updateDeck(UUID userId, UUID playerId, Deck deck) {
        if (table.isTournament()) {
            if (tournament != null) {
                TournamentManager.instance.updateDeck(tournament.getId(), playerId, deck);
            } else {
                logger.fatal("Tournament == null  table: " + table.getId() + " userId: " + userId);
            }
        } else if (TableState.SIDEBOARDING == table.getState()) {
            match.updateDeck(playerId, deck);
        } else {
            // deck was meanwhile submitted so the autoupdate can be ignored
        }
    }

    public boolean watchTable(UUID userId) {
        if (table.isTournament()) {
            UserManager.instance.getUser(userId).ifPresent(user -> user.ccShowTournament(table.getTournament().getId()));
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
            return UserManager.instance.getUser(userId).get().ccWatchGame(match.getGame().getId());
        }
    }

    //    public boolean replayTable(UUID userId) {
//        if (table.getState() != TableState.FINISHED) {
//            return false;
//        }
//        ReplayManager.instance.replayGame(table.getId(), userId);
//        return true;
//    }
    private Optional<Player> createPlayer(String name, PlayerType playerType, int skill) {
        Optional<Player> playerOpt;
        if (options == null) {
            playerOpt = PlayerFactory.instance.createPlayer(playerType, name, RangeOfInfluence.ALL, skill);
        } else {
            playerOpt = PlayerFactory.instance.createPlayer(playerType, name, options.getRange(), skill);
        }
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            logger.trace("Player " + player.getName() + " created id: " + player.getId());
        }
        return playerOpt;
    }

    public void leaveTableAll() {
        for (UUID leavingUserId : userPlayerMap.keySet()) {
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
        if (this.userId != null && this.userId.equals(userId) // tourn. sub tables have no creator user
                && (table.getState() == TableState.WAITING
                || table.getState() == TableState.READY_TO_START)) {
            // table not started yet and user is the owner, remove the table
            TableManager.instance.removeTable(table.getId());
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
                    Optional<User> user = UserManager.instance.getUser(userId);
                    if (user.isPresent()) {
                        ChatManager.instance.broadcast(chatId, user.get().getName(), "has left the table", ChatMessage.MessageColor.BLUE, true, ChatMessage.MessageType.STATUS, ChatMessage.SoundToPlay.PlayerLeft);
                        if (!table.isTournamentSubTable()) {
                            user.get().removeTable(playerId);
                        }
                    } else {
                        logger.debug("User not found - userId: " + userId + " tableId:" + table.getId());
                    }
                    userPlayerMap.remove(userId);
                } else if (table.getState() != TableState.FINISHED) {
                    if (table.isTournament()) {
                        logger.debug("Quit tournament sub tables for userId: " + userId);
                        TableManager.instance.userQuitTournamentSubTables(tournament.getId(), userId);
                        logger.debug("Quit tournament  Id: " + table.getTournament().getId() + '(' + table.getTournament().getTournamentState() + ')');
                        TournamentManager.instance.quit(tournament.getId(), userId);
                    } else {
                        MatchPlayer matchPlayer = match.getPlayer(playerId);
                        if (matchPlayer != null && !match.hasEnded() && !matchPlayer.hasQuit()) {
                            Game game = match.getGame();
                            if (game != null && !game.hasEnded()) {
                                Player player = match.getPlayer(playerId).getPlayer();
                                if (player != null && player.isInGame()) {
                                    GameManager.instance.quitMatch(game.getId(), userId);
                                }
                                match.quitMatch(playerId);
                            } else {
                                if (table.getState() == TableState.SIDEBOARDING) {
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
        if (table.getState() == TableState.STARTING) {
            try {
                if (table.isTournamentSubTable()) {
                    logger.info("Tourn. match started id:" + match.getId() + " tournId: " + table.getTournament().getId());
                } else {
                    UserManager.instance.getUser(userId).ifPresent(user -> {
                        logger.info("MATCH started [" + match.getName() + "] " + match.getId() + '(' + user.getName() + ')');
                        logger.debug("- " + match.getOptions().getGameType() + " - " + match.getOptions().getDeckType());
                    });
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
            GameOptions gameOptions = new GameOptions();
            gameOptions.rollbackTurnsAllowed = match.getOptions().isRollbackTurnsAllowed();
            match.getGame().setGameOptions(gameOptions);
            GameManager.instance.createGameSession(match.getGame(), userPlayerMap, table.getId(), choosingPlayerId, gameOptions);
            String creator = null;
            StringBuilder opponent = new StringBuilder();
            for (Entry<UUID, UUID> entry : userPlayerMap.entrySet()) { // do only for no AI players
                if (match.getPlayer(entry.getValue()) != null && !match.getPlayer(entry.getValue()).hasQuit()) {
                    Optional<User> _user = UserManager.instance.getUser(entry.getKey());
                    if (_user.isPresent()) {
                        User user = _user.get();
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
            for (MatchPlayer mPlayer : match.getPlayers()) {
                if (!mPlayer.getPlayer().isHuman()) {
                    if (opponent.length() > 0) {
                        opponent.append(" - ");
                    }
                    opponent.append(mPlayer.getName());
                }
            }
            ServerMessagesUtil.instance.incGamesStarted();

            // log about game started
            logger.info("GAME started " + (match.getGame() != null ? match.getGame().getId() : "no Game") + " [" + match.getName() + "] " + creator + " - " + opponent.toString());
            logger.debug("- matchId: " + match.getId() + " [" + match.getName() + ']');
            if (match.getGame() != null) {
                logger.debug("- chatId:  " + GameManager.instance.getChatId(match.getGame().getId()));
            }
        } catch (Exception ex) {
            logger.fatal("Error starting game table: " + table.getId(), ex);
            if (table != null) {
                TableManager.instance.removeTable(table.getId());
            }
            if (match != null) {
                Game game = match.getGame();
                if (game != null) {
                    GameManager.instance.removeGame(game.getId());
                }
            }
        }
    }

    public synchronized void startTournament(UUID userId) {
        try {
            if (userId.equals(this.userId) && table.getState() == TableState.STARTING) {
                tournament.setStartTime();
                TournamentManager.instance.createTournamentSession(tournament, userPlayerMap, table.getId());
                for (Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
                    UserManager.instance.getUser(entry.getKey()).ifPresent(user -> {
                        logger.info(new StringBuilder("User ").append(user.getName()).append(" tournament started: ").append(tournament.getId()).append(" userId: ").append(user.getId()));
                        user.ccTournamentStarted(tournament.getId(), entry.getValue());
                    });
                }
                ServerMessagesUtil.instance.incTournamentsStarted();
            }
        } catch (Exception ex) {
            logger.fatal("Error starting tournament", ex);
            TableManager.instance.removeTable(table.getId());
            TournamentManager.instance.quit(tournament.getId(), userId);
        }
    }

    public void startDraft(Draft draft) {
        table.initDraft();
        DraftManager.instance.createDraftSession(draft, userPlayerMap, table.getId());
        for (Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
            Optional<User> user = UserManager.instance.getUser(entry.getKey());
            if (user.isPresent()) {
                logger.info(new StringBuilder("User ").append(user.get().getName()).append(" draft started: ").append(draft.getId()).append(" userId: ").append(user.get().getId()));
                user.get().ccDraftStarted(draft.getId(), entry.getValue());
            } else {
                logger.fatal(new StringBuilder("Start draft user not found userId: ").append(entry.getKey()));
            }
        }
    }

    private void sideboard(UUID playerId, Deck deck) throws MageException {

        for (Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
            if (entry.getValue().equals(playerId)) {
                Optional<User> user = UserManager.instance.getUser(entry.getKey());
                int remaining = (int) futureTimeout.getDelay(TimeUnit.SECONDS);
                user.ifPresent(user1 -> user1.ccSideboard(deck, table.getId(), remaining, options.isLimited()));
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
        if (ConfigSettings.instance.isSaveGameActivated() && !game.isSimulation()) {
            if (GameManager.instance.saveGame(game.getId())) {
                match.setReplayAvailable(true);
            }
        }
        GameManager.instance.removeGame(game.getId());
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
            } else {
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
            for (MatchPlayer matchPlayer : match.getPlayers()) {
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
            for (MatchPlayer matchPlayer : match.getPlayers()) {
                TournamentPlayer tournamentPlayer = table.getTournament().getPlayer(matchPlayer.getPlayer().getId());
                if (tournamentPlayer != null && tournamentPlayer.getStateInfo().equals("sideboarding")) {
                    tournamentPlayer.setStateInfo("");
                }
            }
        }
    }

    /**
     * Tables of normal matches or tournament sub tables are no longer needed,
     * if the match ends.
     */
    private void closeTable() {
        this.matchEnd();
        table.closeTable();
    }

    private void matchEnd() {
        if (match != null) {
            for (Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
                MatchPlayer matchPlayer = match.getPlayer(entry.getValue());
                // opponent(s) left during sideboarding
                if (matchPlayer != null) {
                    if (!matchPlayer.hasQuit()) {
                        UserManager.instance.getUser(entry.getKey()).ifPresent(user -> {
                            if (table.getState() == TableState.SIDEBOARDING) {
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
                        });
                    }
                }
                // free resources no longer needed
                match.cleanUpOnMatchEnd(ConfigSettings.instance.isSaveGameActivated(), table.isTournament());
            }
        }
    }

    private synchronized void setupTimeout(int seconds) {
        cancelTimeout();
        if (seconds > 0) {
            futureTimeout = timeoutExecutor.schedule(this::autoSideboard, seconds, TimeUnit.SECONDS);
        }
    }

    private synchronized void cancelTimeout() {
        if (futureTimeout != null) {
            futureTimeout.cancel(false);
        }
    }

    private void autoSideboard() {
        for (MatchPlayer player : match.getPlayers()) {
            if (!player.isDoneSideboarding()) {
                match.submitDeck(player.getPlayer().getId(), player.generateDeck());
            }
        }
    }

    public void endDraft(Draft draft) {
        if (!draft.isAbort()) {
            for (DraftPlayer player : draft.getPlayers()) {
                player.prepareDeck();
                tournament.getPlayer(player.getPlayer().getId()).setDeck(player.getDeck());
            }
        }
        tournament.clearDraft(); // free the draft resources after draft step has ended
        tournament.nextStep();
    }

    public void swapSeats(int seatNum1, int seatNum2) {
        if (table.getState() == TableState.READY_TO_START) {
            if (seatNum1 >= 0 && seatNum2 >= 0 && seatNum1 < table.getSeats().length && seatNum2 < table.getSeats().length) {
                Player swapPlayer = table.getSeats()[seatNum1].getPlayer();
                PlayerType swapType = table.getSeats()[seatNum1].getPlayerType();
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
            if (table.getState() != TableState.WAITING && table.getState() != TableState.READY_TO_START && table.getState() != TableState.STARTING) {
                TournamentController tournamentController = TournamentManager.instance.getTournamentController(table.getTournament().getId());
                if (tournamentController != null) {
                    return tournamentController.isTournamentStillValid(table.getState());
                } else {
                    return false;
                }
            } else {
                // check if table creator is still a valid user, if not remove table
                return UserManager.instance.getUser(userId).isPresent();
            }
        }
        return false;
    }

    public UUID getUserId(UUID playerId) {
        for (Map.Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
            if (entry.getValue().equals(playerId)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isUserStillActive(UUID userId) {
        UUID playerId = userPlayerMap.get(userId);
        if (playerId != null) {
            if (tournament != null) {
                TournamentPlayer tournamentPlayer = tournament.getPlayer(playerId);
                if (tournamentPlayer != null) {
                    return tournamentPlayer.isInTournament();
                }
            } else if (match != null) {
                MatchPlayer matchPlayer = match.getPlayer(playerId);
                return matchPlayer != null && !matchPlayer.hasQuit();
            }
        }
        return false;
    }

    public boolean isMatchTableStillValid() {
        // check only normal match table with state != Finished
        if (!table.isTournament()) {
            if (!(table.getState() == TableState.WAITING || table.getState() == TableState.STARTING || table.getState() == TableState.READY_TO_START)) {
                if (match == null) {
                    logger.debug("- Match table with no match:");
                    logger.debug("-- matchId:" + match.getId() + " [" + match.getName() + ']');
                    // return false;
                } else if (match.isDoneSideboarding() && match.getGame() == null) {
                    // no sideboarding and not active game -> match seems to hang (maybe the Draw bug)
                    logger.debug("- Match with no active game and not in sideboard state:");
                    logger.debug("-- matchId:" + match.getId() + " [" + match.getName() + ']');
                    // return false;
                }
            }
            // check for active players
            int validHumanPlayers = 0;
            int aiPlayers = 0;
            int humanPlayers = 0;
            for (Map.Entry<UUID, UUID> userPlayerEntry : userPlayerMap.entrySet()) {
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
                    if ((table.getState() == TableState.WAITING
                            || table.getState() == TableState.STARTING
                            || table.getState() == TableState.READY_TO_START)
                            || !match.isDoneSideboarding()
                            || (!matchPlayer.hasQuit() && match.getGame() != null && matchPlayer.getPlayer().isInGame())) {
                        Optional<User> user = UserManager.instance.getUser(userPlayerEntry.getKey());
                        if (!user.isPresent()) {
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
            for (Map.Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
                UserManager.instance.getUser(entry.getKey()).ifPresent(user
                        -> user.removeTable(entry.getValue()));
            }

        }
        ChatManager.instance.destroyChatSession(chatId);
    }

    public synchronized TableState getTableState() {
        return table.getState();
    }

    public synchronized boolean changeTableStateToStarting() {
        if (table.getState() != TableState.READY_TO_START) {
            // tournament is not ready, can't start
            return false;
        }
        if (!table.allSeatsAreOccupied()) {
            logger.debug("Not alle Seats are occupied: stop start tableId:" + table.getId());
            return false;
        }
        table.setState(TableState.STARTING);
        return true;
    }
}
