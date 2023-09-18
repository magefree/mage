package mage.server;

import mage.MageException;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.DeckValidatorError;
import mage.cards.decks.DeckValidatorFactory;
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
import mage.server.game.GameFactory;
import mage.server.game.PlayerFactory;
import mage.server.managers.ManagerFactory;
import mage.server.record.TableRecorderImpl;
import mage.server.tournament.TournamentFactory;
import mage.server.util.ServerMessagesUtil;
import mage.view.ChatMessage;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TableController {

    private static final Logger logger = Logger.getLogger(TableController.class);

    private final ManagerFactory managerFactory;
    private final UUID userId;
    private final UUID chatId;
    private final String controllerName;
    private final Table table;
    private final ConcurrentHashMap<UUID, UUID> userPlayerMap = new ConcurrentHashMap<>(); // human players only, use table.seats for AI

    private Match match;
    private MatchOptions options;
    private Tournament tournament;

    private ScheduledFuture<?> futureTimeout;
    protected final ScheduledExecutorService timeoutExecutor;

    public TableController(ManagerFactory managerFactory, UUID roomId, UUID userId, MatchOptions options) {
        this.managerFactory = managerFactory;
        timeoutExecutor = managerFactory.threadExecutor().getTimeoutExecutor();
        this.userId = userId;
        this.options = options;
        match = GameFactory.instance.createMatch(options.getGameType(), options);
        if (userId != null) {
            Optional<User> user = managerFactory.userManager().getUser(userId);
            // TODO: Handle if user == null
            controllerName = user.map(User::getName).orElse("undefined");
        } else {
            controllerName = "System";
        }
        table = new Table(roomId, options.getGameType(), options.getName(), controllerName, DeckValidatorFactory.instance.createDeckValidator(options.getDeckType()),
                options.getPlayerTypes(), new TableRecorderImpl(managerFactory.userManager()), match, options.getBannedUsers(), options.isPlaneChase());
        chatId = managerFactory.chatManager().createChatSession("Match Table " + table.getId());
        init();
    }

    public TableController(ManagerFactory managerFactory, UUID roomId, UUID userId, TournamentOptions options) {
        this.managerFactory = managerFactory;
        this.timeoutExecutor = managerFactory.threadExecutor().getTimeoutExecutor();
        this.userId = userId;
        tournament = TournamentFactory.instance.createTournament(options.getTournamentType(), options);
        if (userId != null) {
            Optional<User> user = managerFactory.userManager().getUser(userId);
            if (!user.isPresent()) {
                logger.fatal(new StringBuilder("User for userId ").append(userId).append(" could not be retrieved from UserManagerImpl").toString());
                controllerName = "[unknown]";
            } else {
                controllerName = user.get().getName();
            }
        } else {
            controllerName = "System";
        }
        table = new Table(roomId, options.getTournamentType(), options.getName(), controllerName, DeckValidatorFactory.instance.createDeckValidator(options.getMatchOptions().getDeckType()),
                options.getPlayerTypes(), new TableRecorderImpl(managerFactory.userManager()), tournament, options.getMatchOptions().getBannedUsers(), options.isPlaneChase());
        chatId = managerFactory.chatManager().createChatSession("Tourn. table " + table.getId());
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
        Optional<User> _user = managerFactory.userManager().getUser(userId);
        if (!_user.isPresent()) {
            logger.fatal("couldn't get user " + name + " for join tournament userId = " + userId);
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
                List<DeckValidatorError> errorsList = table.getValidator().getErrorsListSorted();
                errorsList.stream().forEach(error -> {
                    sb.append(error.getGroup()).append(": ").append(error.getMessage()).append("\n");
                });
                sb.append("\n\nSelect a deck that is appropriate for the selected format and try again!");
                user.showUserMessage("Join Table", sb.toString());
                if (isOwner(userId)) {
                    logger.debug("New table removed because owner submitted invalid deck tableId " + table.getId());
                    managerFactory.tableManager().removeTable(table.getId());
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

        // Check minimum rating.
        int minimumRating = table.getTournament().getOptions().getMinimumRating();
        int userRating;
        if (table.getTournament().getOptions().getMatchOptions().isLimited()) {
            userRating = user.getUserData().getLimitedRating();
        } else {
            userRating = user.getUserData().getConstructedRating();
        }
        if (userRating < minimumRating) {
            String message = new StringBuilder("Your rating ").append(userRating)
                    .append(" is lower than the table requirement ").append(minimumRating).toString();
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

        managerFactory.draftManager().getController(table.getId()).ifPresent(controller -> controller.replacePlayer(oldPlayer, newPlayer));
        return true;
    }

    public synchronized boolean joinTable(UUID userId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws MageException {
        Optional<User> _user = managerFactory.userManager().getUser(userId);
        if (!_user.isPresent()) {
            logger.error("Join Table: can't find user to join " + name + " Id = " + userId);
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
            List<DeckValidatorError> errorsList = table.getValidator().getErrorsListSorted();
            errorsList.stream().forEach(error -> {
                sb.append(error.getGroup()).append(": ").append(error.getMessage()).append("\n");
            });
            sb.append("\n\nSelect a deck that is appropriate for the selected format and try again!");
            user.showUserMessage("Join Table", sb.toString());
            if (isOwner(userId)) {
                logger.debug("New table removed because owner submitted invalid deck tableId " + table.getId());
                managerFactory.tableManager().removeTable(table.getId());
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

        // Check minimum rating.
        int minimumRating = table.getMatch().getOptions().getMinimumRating();
        int userRating;
        if (table.getMatch().getOptions().isLimited()) {
            userRating = user.getUserData().getLimitedRating();
        } else {
            userRating = user.getUserData().getConstructedRating();
        }
        if (userRating < minimumRating) {
            String message = new StringBuilder("Your rating ").append(userRating)
                    .append(" is lower than the table requirement ").append(minimumRating).toString();
            user.showUserMessage("Join Table", message);
            return false;
        }

        // Check power level for table (currently only used for EDH/Commander table)
        int edhPowerLevel = table.getMatch().getOptions().getEdhPowerLevel();
        if (edhPowerLevel > 0 && table.getValidator().getName().toLowerCase(Locale.ENGLISH).equals("commander")) {
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
            String message = "Could not create player " + name + " of type " + seat.getPlayerType();
            logger.warn("User: " + user.getName() + " => " + message);
            user.showUserMessage("Join Table", message);
            return false;
        }
        Player player = playerOpt.get();
        if (!player.canJoinTable(table)) {
            user.showUserMessage("Join Table", "A " + seat.getPlayerType() + " player can't join this table.");
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
            Optional<User> _user = managerFactory.userManager().getUser(userId);
            if (!_user.isPresent()) {
                return false;
            }
            StringBuilder sb = new StringBuilder("Invalid deck for the selected ").append(table.getValidator().getName()).append(" format. \n\n");
            List<DeckValidatorError> errorsList = table.getValidator().getErrorsListSorted();
            errorsList.stream().forEach(error -> {
                sb.append(error.getGroup()).append(": ").append(error.getMessage()).append("\n");
            });
            sb.append("\n\nAdd enough cards and try again!");
            _user.get().showUserMessage("Submit deck", sb.toString());
            return false;
        }
        submitDeck(userId, playerId, deck);
        return true;
    }

    public void updateDeck(UUID userId, DeckCardLists deckList) throws MageException {
        boolean validDeck;
        UUID playerId = userPlayerMap.get(userId);
        if (table.getState() != TableState.SIDEBOARDING && table.getState() != TableState.CONSTRUCTING) {
            return;
        }
        Deck deck = Deck.load(deckList, false, false);
        validDeck = updateDeck(userId, playerId, deck);
        if (!validDeck && getTableState() == TableState.SIDEBOARDING) {
            logger.warn(" userId: " + userId + " - Modified deck card list!");
        }
    }

    private void submitDeck(UUID userId, UUID playerId, Deck deck) {
        if (table.getState() == TableState.SIDEBOARDING) {
            match.submitDeck(playerId, deck);
            managerFactory.userManager().getUser(userId).ifPresent(user -> user.removeSideboarding(table.getId()));
        } else {
            managerFactory.tournamentManager().submitDeck(tournament.getId(), playerId, deck);
            managerFactory.userManager().getUser(userId).ifPresent(user -> user.removeConstructing(playerId));
        }
    }

    private boolean updateDeck(UUID userId, UUID playerId, Deck deck) {
        boolean validDeck = true;
        if (table.isTournament()) {
            if (tournament != null) {
                validDeck = managerFactory.tournamentManager().updateDeck(tournament.getId(), playerId, deck);
            } else {
                logger.fatal("Tournament == null  table: " + table.getId() + " userId: " + userId);
            }
        } else if (TableState.SIDEBOARDING == table.getState()) {
            validDeck = match.updateDeck(playerId, deck);
        } else {
            // deck was meanwhile submitted so the autoupdate can be ignored
        }
        return validDeck;
    }

    public boolean watchTable(UUID userId) {
        if (table.isTournament()) {
            managerFactory.userManager().getUser(userId).ifPresent(user -> user.ccShowTournament(table.getTournament().getId()));
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
            Optional<User> _user = managerFactory.userManager().getUser(userId);
            if (!_user.isPresent()) {
                return false;
            }
            return _user.get().ccWatchGame(match.getGame().getId());
        }
    }

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
            // table not started yet and user is the owner, removeUserFromAllTablesAndChat the table
            managerFactory.tableManager().removeTable(table.getId());
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
                    Optional<User> user = managerFactory.userManager().getUser(userId);
                    if (user.isPresent()) {
                        managerFactory.chatManager().broadcast(chatId, user.get().getName(), "has left the table", ChatMessage.MessageColor.BLUE, true, null, ChatMessage.MessageType.STATUS, ChatMessage.SoundToPlay.PlayerLeft);
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
                        managerFactory.tableManager().userQuitTournamentSubTables(tournament.getId(), userId);
                        logger.debug("Quit tournament  Id: " + table.getTournament().getId() + '(' + table.getTournament().getTournamentState() + ')');
                        managerFactory.tournamentManager().quit(tournament.getId(), userId);
                    } else {
                        MatchPlayer matchPlayer = match.getPlayer(playerId);
                        if (matchPlayer != null && !match.hasEnded() && !matchPlayer.hasQuit()) {
                            Game game = match.getGame();
                            if (game != null && !game.hasEnded()) {
                                Player player = match.getPlayer(playerId).getPlayer();
                                if (player != null && player.isInGame()) {
                                    managerFactory.gameManager().quitMatch(game.getId(), userId);
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
                    managerFactory.userManager().getUser(userId).ifPresent(user -> {
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
            gameOptions.bannedUsers = match.getOptions().getBannedUsers();
            gameOptions.planeChase = match.getOptions().isPlaneChase();
            match.getGame().setGameOptions(gameOptions);
            managerFactory.gameManager().createGameSession(match.getGame(), userPlayerMap, table.getId(), choosingPlayerId, gameOptions);
            String creator = null;
            StringBuilder opponent = new StringBuilder();
            for (Entry<UUID, UUID> entry : userPlayerMap.entrySet()) { // do only for no AI players
                if (match.getPlayer(entry.getValue()) != null && !match.getPlayer(entry.getValue()).hasQuit()) {
                    Optional<User> _user = managerFactory.userManager().getUser(entry.getKey());
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
            logger.info("GAME started " + (match.getGame() != null ? match.getGame().getId() : "no Game") + " [" + match.getName() + "] " + creator + " - " + opponent);
            logger.debug("- matchId: " + match.getId() + " [" + match.getName() + ']');
            if (match.getGame() != null) {
                logger.debug("- chatId:  " + managerFactory.gameManager().getChatId(match.getGame().getId()));
            }
        } catch (Exception ex) {
            logger.fatal("Error starting game table: " + table.getId(), ex);
            if (table != null) {
                managerFactory.tableManager().removeTable(table.getId());
            }
            if (match != null) {
                Game game = match.getGame();
                if (game != null) {
                    managerFactory.gameManager().removeGame(game.getId());
                    // game ended by error, so don't add it to ended stats
                }
            }
        }
    }

    public synchronized void startTournament(UUID userId) {
        try {
            if (userId.equals(this.userId) && table.getState() == TableState.STARTING) {
                tournament.setStartTime();
                managerFactory.tournamentManager().createTournamentSession(tournament, userPlayerMap, table.getId());
                for (Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
                    managerFactory.userManager().getUser(entry.getKey()).ifPresent(user -> {
                        logger.info(new StringBuilder("User ").append(user.getName()).append(" tournament started: ").append(tournament.getId()).append(" userId: ").append(user.getId()));
                        user.ccTournamentStarted(tournament.getId(), entry.getValue());
                    });
                }
                ServerMessagesUtil.instance.incTournamentsStarted();
            }
        } catch (Exception ex) {
            logger.fatal("Error starting tournament", ex);
            managerFactory.tableManager().removeTable(table.getId());
            managerFactory.tournamentManager().quit(tournament.getId(), userId);
        }
    }

    public void startDraft(Draft draft) {
        table.initDraft();
        managerFactory.draftManager().createDraftSession(draft, userPlayerMap, table.getId());
        for (Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
            Optional<User> user = managerFactory.userManager().getUser(entry.getKey());
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
                Optional<User> user = managerFactory.userManager().getUser(entry.getKey());
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
        ServerMessagesUtil.instance.incTournamentsEnded();
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
        if (managerFactory.configSettings().isSaveGameActivated() && !game.isSimulation()) {
            if (managerFactory.gameManager().saveGame(game.getId())) {
                match.setReplayAvailable(true);
            }
        }
        managerFactory.gameManager().removeGame(game.getId());
        ServerMessagesUtil.instance.incGamesEnded();

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
                        managerFactory.userManager().getUser(entry.getKey()).ifPresent(user -> {
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
                            // removeUserFromAllTablesAndChat table from user - table manager holds table for display of finished matches
                            if (!table.isTournamentSubTable()) {
                                user.removeTable(entry.getValue());
                            }
                        });
                    }
                }
                // free resources no longer needed
                match.cleanUpOnMatchEnd(managerFactory.configSettings().isSaveGameActivated(), table.isTournament());
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
                match.submitDeck(player.getPlayer().getId(), player.generateDeck(table.getValidator()));
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
                return managerFactory.tournamentManager().getTournamentController(table.getTournament().getId())
                        .map(tc -> tc.isTournamentStillValid(table.getState()))
                        .orElse(false);

            } else {
                // check if table creator is still a valid user, if not removeUserFromAllTablesAndChat table
                return managerFactory.userManager().getUser(userId).isPresent();
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
        // removes active match only, not tourney
        if (table.isTournament()) {
            return true;
        }

        // only started games need to check
        if (Arrays.asList(
                TableState.WAITING,
                TableState.READY_TO_START,
                TableState.STARTING
        ).contains(table.getState())) {
            // waiting in start dialog
            return true;
        }
        if (match != null && !match.isDoneSideboarding()) {
            // waiting sideboard complete
            return true;
        }

        // no games in started match (error in match init code?)
        if (match.getGame() == null) {
            logger.error("- Match without started games:");
            logger.error("-- matchId:" + match.getId());
            return false; // critical error
        }

        // find player stats
        int validHumanPlayers = 0;
        int validAIPlayers = 0;

        // check humans
        for (Map.Entry<UUID, UUID> userPlayerEntry : userPlayerMap.entrySet()) {
            MatchPlayer matchPlayer = match.getPlayer(userPlayerEntry.getValue());

            // de-synced users and players listst?
            if (matchPlayer == null) {
                logger.error("- Match player not found in started game:");
                logger.error("-- matchId:" + match.getId());
                logger.error("-- userId:" + userPlayerEntry.getKey());
                logger.error("-- playerId:" + userPlayerEntry.getValue());
                continue;
            }

            if (matchPlayer.getPlayer().isHuman()) {
                if (matchPlayer.getPlayer().isInGame()) {
                    Optional<User> user = managerFactory.userManager().getUser(userPlayerEntry.getKey());

                    // user was logout or disconnected from server, but still in the game somehow
                    if (!user.isPresent() || !user.get().isActive()) {
                        logger.error("- Active user of match is missing: " + matchPlayer.getName());
                        logger.error("-- matchId:" + match.getId());
                        logger.error("-- userId:" + userPlayerEntry.getKey());
                        logger.error("-- playerId:" + userPlayerEntry.getValue());
                        return false; // critical error
                    }

                    // user exits on the server and match player has not quit -> player is valid
                    validHumanPlayers++;
                }
            }
        }

        // check AI
        for (MatchPlayer matchPlayer : match.getPlayers()) {
            if (!matchPlayer.getPlayer().isHuman()) {
                if (matchPlayer.getPlayer().isInGame()) {
                    validAIPlayers++;
                }
            }
        }

        // if someone can play 1 vs 1 (e.g. 2+ players) then keep table
        return validAIPlayers + validHumanPlayers >= 2;
    }

    void cleanUp() {
        if (!table.isTournamentSubTable()) {
            for (Map.Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
                managerFactory.userManager().getUser(entry.getKey()).ifPresent(user
                        -> user.removeTable(entry.getValue()));
            }

        }
        managerFactory.chatManager().destroyChatSession(chatId);
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
