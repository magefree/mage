package mage.server.game;

import mage.MageException;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.Game;
import mage.game.GameOptions;
import mage.game.GameState;
import mage.game.Table;
import mage.game.command.Plane;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.match.MatchPlayer;
import mage.game.permanent.Permanent;
import mage.game.turn.Phase;
import mage.interfaces.Action;
import mage.players.Player;
import mage.server.Main;
import mage.server.User;
import mage.server.managers.ManagerFactory;
import mage.util.MultiAmountMessage;
import mage.util.ThreadUtils;
import mage.util.XmageThreadFactory;
import mage.utils.StreamUtils;
import mage.utils.timer.PriorityTimer;
import mage.view.*;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class GameController implements GameCallback {

    private static final int GAME_TIMEOUTS_CHECK_JOINING_STATUS_EVERY_SECS = 10; // checks and inform players about joining status
    private static final int GAME_TIMEOUTS_CANCEL_PLAYER_GAME_JOINING_AFTER_INACTIVE_SECS = 2 * 60; // leave player from game if it don't join and inactive on server

    private final ExecutorService gameExecutor;
    private static final Logger logger = Logger.getLogger(GameController.class);

    private ScheduledExecutorService JOIN_WAITING_EXECUTOR = null;

    private ScheduledFuture<?> responseIdleTimeoutFuture;
    private UUID responseIdleTimeoutPlayerId;
    private final ManagerFactory managerFactory;
    protected final ScheduledExecutorService responseIdleTimeoutExecutor;

    private final ConcurrentMap<UUID, GameSessionPlayer> gameSessions = new ConcurrentHashMap<>();
    private final ReadWriteLock gameSessionsLock = new ReentrantReadWriteLock();

    private final ConcurrentMap<UUID, GameSessionWatcher> watchers = new ConcurrentHashMap<>();
    private final ReadWriteLock gameWatchersLock = new ReentrantReadWriteLock();

    private final ConcurrentMap<UUID, PriorityTimer> timers = new ConcurrentHashMap<>();

    private final ConcurrentMap<UUID, UUID> userPlayerMap;
    private final UUID gameSessionId;
    private final Game game;
    private final UUID chatId;
    private final UUID tableId;
    private final UUID choosingPlayerId;
    private Future<?> gameFuture;
    private boolean useResponseIdleTimeout = true; // control currently active player (if no response for 600 seconds then concede him)
    private final GameOptions gameOptions;

    private UUID userRequestingRollback;
    private int turnsToRollback;
    private int requestsOpen;

    public GameController(ManagerFactory managerFactory, Game game, ConcurrentMap<UUID, UUID> userPlayerMap, UUID tableId, UUID choosingPlayerId, GameOptions gameOptions) {
        this.managerFactory = managerFactory;
        this.gameExecutor = managerFactory.threadExecutor().getGameExecutor();
        this.responseIdleTimeoutExecutor = managerFactory.threadExecutor().getTimeoutIdleExecutor();
        this.gameSessionId = UUID.randomUUID();
        this.userPlayerMap = userPlayerMap;
        this.chatId = managerFactory.chatManager().createGameChatSession(game);
        this.userRequestingRollback = null;
        this.game = game;
        this.game.setSaveGame(managerFactory.configSettings().isSaveGameActivated());
        this.tableId = tableId;
        this.choosingPlayerId = choosingPlayerId;
        this.gameOptions = gameOptions;
        this.useResponseIdleTimeout = game.getPlayers().values().stream().filter(Player::isHuman).count() > 1;
        init();
    }

    public void cleanUp() {
        stopResponseIdleTimeout();
        for (PriorityTimer priorityTimer : timers.values()) {
            priorityTimer.cancel();
        }

        getGameSessions().forEach(GameSessionPlayer::cleanUp);
        getGameSessionWatchers().forEach(GameSessionWatcher::cleanUp);

        managerFactory.chatManager().destroyChatSession(chatId);
    }

    private void init() {
        game.addTableEventListener(
                (Listener<TableEvent>) event -> {
                    try {
                        PriorityTimer timer;
                        UUID playerId;
                        switch (event.getEventType()) {
                            case UPDATE:
                                updateGame();
                                break;
                            case INFO:
                                managerFactory.chatManager().broadcast(chatId, "", event.getMessage(), MessageColor.BLACK, true, event.getGame(), MessageType.GAME, null);
                                logger.trace(game.getId() + " " + event.getMessage());
                                break;
                            case STATUS:
                                managerFactory.chatManager().broadcast(chatId, "", event.getMessage(), MessageColor.ORANGE, event.getWithTime(), event.getWithTurnInfo() ? event.getGame() : null, MessageType.GAME, null);
                                logger.trace(game.getId() + " " + event.getMessage());
                                break;
                            case ERROR:
                                error(event.getMessage(), event.getException());
                                break;
                            case END_GAME_INFO:
                                endGameInfo();
                                break;
                            case INIT_TIMER:
                                final UUID initPlayerId = event.getPlayerId();
                                if (initPlayerId == null) {
                                    throw new MageException("INIT_TIMER: playerId can't be null");
                                }
                                createPlayerTimer(event.getPlayerId(), game.getPriorityTime());
                                break;
                            case RESUME_TIMER:
                                playerId = event.getPlayerId();
                                if (playerId == null) {
                                    throw new MageException("RESUME_TIMER: playerId can't be null");
                                }
                                Player player = game.getState().getPlayer(playerId);
                                if (player == null) {
                                    throw new MageException("RESUME_TIMER: player can't be null");
                                }

                                timer = timers.get(playerId);
                                if (timer == null) {
                                    timer = createPlayerTimer(event.getPlayerId(), player.getPriorityTimeLeft());
                                }

                                player.setBufferTimeLeft(game.getBufferTime());
                                timer.setBufferCount(game.getBufferTime());
                                timer.resume();
                                break;
                            case PAUSE_TIMER:
                                playerId = event.getPlayerId();
                                if (playerId == null) {
                                    throw new MageException("PAUSE_TIMER: playerId can't be null");
                                }
                                timer = timers.get(playerId);
                                if (timer == null) {
                                    throw new MageException("PAUSE_TIMER: couldn't find timer for player: " + playerId);
                                }
                                timer.pause();
                                break;
                        }
                    } catch (MageException e) {
                        logger.fatal("Table event listener error: " + e, e);
                    }
                }
        );
        game.addPlayerQueryEventListener(
                (Listener<PlayerQueryEvent>) event -> {
                    logger.trace(new StringBuilder(event.getPlayerId().toString()).append("--").append(event.getQueryType()).append("--").append(event.getMessage()).toString());
                    try {
                        switch (event.getQueryType()) {
                            case ASK:
                                ask(event.getPlayerId(), event.getMessage(), event.getOptions());
                                break;
                            case PICK_TARGET:
                                target(event.getPlayerId(), event.getMessage(), event.getCards(), event.getPerms(), event.getTargets(), event.isRequired(), event.getOptions());
                                break;
                            case PICK_ABILITY:
                                target(event.getPlayerId(), event.getMessage(), event.getAbilities(), event.isRequired(), event.getOptions());
                                break;
                            case SELECT:
                                select(event.getPlayerId(), event.getMessage(), event.getOptions());
                                break;
                            case PLAY_MANA:
                                playMana(event.getPlayerId(), event.getMessage(), event.getOptions());
                                break;
                            case PLAY_X_MANA:
                                playXMana(event.getPlayerId(), event.getMessage());
                                break;
                            case CHOOSE_ABILITY:
                                String objectName = null;
                                if (event.getChoices() != null && !event.getChoices().isEmpty()) {
                                    objectName = event.getChoices().iterator().next();
                                }
                                chooseAbility(event.getPlayerId(), objectName, event.getAbilities(), event.getMessage());
                                break;
                            case CHOOSE_PILE:
                                choosePile(event.getPlayerId(), event.getMessage(), event.getPile1(), event.getPile2());
                                break;
                            case CHOOSE_MODE:
                                chooseMode(event.getPlayerId(), event.getModes(), event.getMessage());
                                break;
                            case CHOOSE_CHOICE:
                                chooseChoice(event.getPlayerId(), event.getChoice());
                                break;
                            case AMOUNT:
                                amount(event.getPlayerId(), event.getMessage(), event.getMin(), event.getMax());
                                break;
                            case MULTI_AMOUNT:
                                multiAmount(event.getPlayerId(), event.getMessages(), event.getMin(), event.getMax(), event.getOptions());
                                break;
                            case PERSONAL_MESSAGE:
                                informPersonal(event.getPlayerId(), event.getMessage());
                                break;
                            case TOURNAMENT_CONSTRUCT:
                            case DRAFT_PICK_CARD:
                                // tournament and draft events, impossible to catch it here
                                break;
                            default:
                                throw new IllegalArgumentException("Unknown game event: " + event.getQueryType());
                        }
                    } catch (MageException ex) {
                        logger.fatal("Player event listener error ", ex);
                    }
                }
        );

        // wait all players
        if (JOIN_WAITING_EXECUTOR == null) {
            JOIN_WAITING_EXECUTOR = Executors.newSingleThreadScheduledExecutor(
                    new XmageThreadFactory(ThreadUtils.THREAD_PREFIX_GAME_JOIN_WAITING + " " + game.getId())
            );
        }
        JOIN_WAITING_EXECUTOR.scheduleAtFixedRate(() -> {
            try {
                sendInfoAboutPlayersNotJoinedYetAndTryToFixIt();
            } catch (Exception ex) {
                logger.fatal("Send info about player not joined yet:", ex);
            }
        }, GAME_TIMEOUTS_CHECK_JOINING_STATUS_EVERY_SECS, GAME_TIMEOUTS_CHECK_JOINING_STATUS_EVERY_SECS, TimeUnit.SECONDS);

        checkJoinAndStart();
    }

    /**
     * We create a timer that will run every 250 ms individually for a player
     * decreasing their internal game counter. Later on this counter is used to
     * get time left to play the whole match.
     * <p>
     * What we also do here is passing Action to PriorityTimer that is the
     * action that will be executed once game timer is over.
     *
     * @param playerId
     * @param count
     * @return
     */
    private PriorityTimer createPlayerTimer(UUID playerId, int count) {
        final UUID initPlayerId = playerId;
        long delayMs = 250L; // run each 250 ms

        Action executeOnNoTimeLeft = () -> {
            // TODO: buggy, must run in game thread, not in timer thread
            game.timerTimeout(initPlayerId);
            logger.debug("Player has no time left to end the match: " + initPlayerId + ". Conceding.");
        };

        PriorityTimer timer = new PriorityTimer(count, delayMs, executeOnNoTimeLeft);
        timer.init(game.getId());
        timers.put(playerId, timer);
        return timer;
    }

    private UUID getPlayerId(UUID userId) {
        return userPlayerMap.get(userId);
    }

    public void join(UUID userId) {
        UUID playerId = userPlayerMap.get(userId);
        if (playerId == null) {
            logger.fatal("Join game failed!");
            logger.fatal("- gameId: " + game.getId());
            logger.fatal("- userId: " + userId);
            return;
        }
        Optional<User> user = managerFactory.userManager().getUser(userId);
        if (!user.isPresent()) {
            logger.fatal("User not found : " + userId);
            return;
        }
        Player player = game.getPlayer(playerId);
        if (player == null) {
            logger.fatal("Player not found - playerId: " + playerId);
            return;
        }
        GameSessionPlayer gameSession = gameSessions.get(playerId);
        String joinType;
        if (gameSession == null) {
            gameSession = new GameSessionPlayer(managerFactory, game, userId, playerId);
            final Lock w = gameSessionsLock.writeLock();
            w.lock();
            try {
                gameSessions.put(playerId, gameSession);
            } finally {
                w.unlock();
            }
            joinType = "joined";
        } else {
            joinType = "rejoined";
        }
        user.get().addGame(playerId, gameSession);
        logger.debug("Player " + player.getName() + ' ' + playerId + " has " + joinType + " gameId: " + game.getId());
        managerFactory.chatManager().broadcast(chatId, "", game.getPlayer(playerId).getLogName() + " has " + joinType + " the game", MessageColor.ORANGE, true, game, MessageType.GAME, null);
        checkJoinAndStart();
    }

    private synchronized void startGame() {
        if (gameFuture == null) {
            // workaround to fill range info (cause real range fills after game start, but users must get start event with game data already)
            for (Player player : game.getPlayers().values()) {
                player.updateRange(game);
            }

            // send first info to users
            for (GameSessionPlayer gameSessionPlayer : getGameSessions()) {
                gameSessionPlayer.init();
            }

            // real game start
            GameWorker worker = new GameWorker(game, choosingPlayerId, this);
            gameFuture = gameExecutor.submit(worker);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {
            }

            if (game.getState().getChoosingPlayerId() != null) {
                // start timer to force player to choose starting player otherwise loosing by being idle
                startResponseIdleTimeout(game.getState().getChoosingPlayerId());
            }
        }
    }

    private void sendInfoAboutPlayersNotJoinedYetAndTryToFixIt() {
        // TODO: need code and feature review - is it useful? 2024-06-23
        // runs every 10 secs until all players join
        // runs BEFORE game start, so it's safe to call player.leave here
        for (Player player : game.getPlayers().values()) {
            if (player.canRespond() && player.isHuman()) {
                Optional<User> requestedUser = getUserByPlayerId(player.getId());
                if (requestedUser.isPresent()) {
                    User user = requestedUser.get();
                    // TODO: workaround to fix not started games in tourneys, need to find out real reason
                    if (gameSessions.get(player.getId()) == null) {
                        // join the game because player has not joined or was removed because of disconnect
                        String problemPlayerFixes;
                        user.removeConstructing(player.getId());
                        managerFactory.gameManager().joinGame(game.getId(), user.getId()); // will generate new session on empty
                        logger.warn("Forced join of player " + player.getName() + " (" + user.getUserState() + ") to gameId: " + game.getId());
                        if (user.isConnected()) {
                            // init game session, see reconnect()
                            GameSessionPlayer session = gameSessions.get(player.getId());
                            if (session != null) {
                                problemPlayerFixes = "re-send start game event";
                                logger.warn("Send forced game start event for player " + player.getName() + " in gameId: " + game.getId());
                                Table table = managerFactory.tableManager().getTable(this.tableId);
                                if (table != null) {
                                    user.ccGameStarted(table.getId(), table.getParentTableId(), session.getGameId(), player.getId());
                                    session.init();
                                    managerFactory.gameManager().sendPlayerString(session.getGameId(), user.getId(), "");
                                } else {
                                    logger.error("Can't find table on fix and re-send start game event: " + this.tableId);
                                }
                            } else {
                                throw new IllegalStateException("Wrong code usage: session can't be null cause it created in forced joinGame already");
                                //player.leave();
                            }
                        } else {
                            problemPlayerFixes = "leave on disconnected";
                            logger.warn("User disconnected, leave him after forced join: player " + player.getName() + " in gameId: " + game.getId());
                            player.leave();
                        }

                        managerFactory.chatManager().broadcast(chatId, player.getName(), user.getPingInfo()
                                        + " is forced to join the game (waiting ends after "
                                        + GAME_TIMEOUTS_CANCEL_PLAYER_GAME_JOINING_AFTER_INACTIVE_SECS
                                        + " secs, applied fixes: " + problemPlayerFixes + ")",
                                MessageColor.BLUE, true, game, ChatMessage.MessageType.STATUS, null);
                    }

                    if (!user.isConnected() && user.getSecondsDisconnected() > GAME_TIMEOUTS_CANCEL_PLAYER_GAME_JOINING_AFTER_INACTIVE_SECS) {
                        // Cancel player join possibility lately
                        logger.debug("Player " + player.getName() + " - canceled joining game (after "
                                + user.getSecondsDisconnected() + " secs of inactivity) gameId: " + game.getId());
                        player.leave();
                    }
                } else if (!player.hasLeft()) {
                    logger.debug("Player " + player.getName() + " canceled game (no user) gameId: " + game.getId());
                    player.leave();
                }
            }
        }
        checkJoinAndStart();
    }

    private Optional<User> getUserByPlayerId(UUID playerId) {
        for (Map.Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
            if (entry.getValue().equals(playerId)) {
                return managerFactory.userManager().getUser(entry.getKey());
            }
        }
        return Optional.empty();
    }

    private void checkJoinAndStart() {
        if (isAllJoined()) {
            JOIN_WAITING_EXECUTOR.shutdownNow();
            managerFactory.threadExecutor().getCallExecutor().execute(this::startGame);
        }
    }

    private boolean isAllJoined() {
        for (Player player : game.getPlayers().values()) {
            if (!player.hasLeft()) {
                Optional<User> user = getUserByPlayerId(player.getId());
                if (user.isPresent()) {
                    if (!user.get().isConnected()) {
                        return false;
                    }
                }
                if (player.isHuman() && !gameSessions.containsKey(player.getId())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean watch(UUID userId) {
        if (userPlayerMap.containsKey(userId)) {
            // You can't watch a game if you already a player in it
            return false;
        }
        if (watchers.containsKey(userId)) {
            // You can't watch a game if you already watch it
            return false;
        }
        if (!isAllowedToWatch(userId)) {
            // Dont want people on our ignore list to stalk us
            managerFactory.userManager().getUser(userId).ifPresent(user -> {
                user.showUserMessage("Not allowed", "You are banned from watching this game");
                managerFactory.chatManager().broadcast(chatId, user.getName(), " tried to join, but is banned", MessageColor.BLUE, true, game, ChatMessage.MessageType.STATUS, null);
            });
            return false;
        }
        managerFactory.userManager().getUser(userId).ifPresent(user -> {
            GameSessionWatcher gameWatcher = new GameSessionWatcher(managerFactory.userManager(), userId, game, false);
            final Lock w = gameWatchersLock.writeLock();
            w.lock();
            try {
                watchers.put(userId, gameWatcher);
            } finally {
                w.unlock();
            }
            gameWatcher.init();
            user.addGameWatchInfo(game.getId());
            managerFactory.chatManager().broadcast(chatId, user.getName(), " has started watching", MessageColor.BLUE, true, game, ChatMessage.MessageType.STATUS, null);
        });
        return true;
    }

    public void stopWatching(UUID userId) {
        final Lock w = gameWatchersLock.writeLock();
        w.lock();
        try {
            watchers.remove(userId);
        } finally {
            w.unlock();
        }
        managerFactory.userManager().getUser(userId).ifPresent(user -> {
            managerFactory.chatManager().broadcast(chatId, user.getName(), " has stopped watching", MessageColor.BLUE, true, game, ChatMessage.MessageType.STATUS, null);
        });
    }

    public void quitMatch(UUID userId) {
        UUID playerId = getPlayerId(userId);
        if (playerId != null) {
            if (isAllJoined()) {
                GameSessionPlayer gameSessionPlayer = gameSessions.get(playerId);
                if (gameSessionPlayer != null) {
                    gameSessionPlayer.quitGame();
                }
            } else {
                // The player did never join the game but the game controller was started because the player was still connected as the
                // game was started. But the Client never called the join action. So now after the user is expired, the
                // quit match is called and has to end the game, because the player never joined the game.
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.leave();
                    checkJoinAndStart(); // => So the game starts and gets an result or multiplayer game starts with active players
                }
            }
        }
    }

    public void sendPlayerAction(PlayerAction playerAction, UUID userId, Object data) {
        // TODO: critical bug, must be enabled and research/rework due:
        // * game change commands must be executed by game thread (example: undo)
        // * user change commands can be executed by network thread??? (example: change skip settings)
        //SystemUtil.ensureRunInGameThread();
        switch (playerAction) {
            case UNDO:
                game.undo(getPlayerId(userId));
                break;
            case ROLLBACK_TURNS: // basic request of a player to rollback
                if (data instanceof Integer) {
                    turnsToRollback = (Integer) data;
                    if (game.canRollbackTurns(turnsToRollback)) {
                        UUID playerId = getPlayerId(userId);
                        if (game.getPriorityPlayerId().equals(playerId)) {
                            // rollback request on own priority - can stop current choose dialog
                            // TODO: make it async on any priority like concede
                            requestsOpen = requestPermissionToRollback(userId, turnsToRollback);
                            if (requestsOpen == 0) {
                                game.rollbackTurns(turnsToRollback);
                                turnsToRollback = -1;
                                requestsOpen = -1;
                            } else {
                                userRequestingRollback = userId;
                            }
                        } else {
                            Player player = game.getPlayer(playerId);
                            if (player != null) {
                                game.informPlayer(player, "You can only request a rollback if you have priority.");
                            }
                        }
                    } else {
                        UUID playerId = getPlayerId(userId);
                        if (playerId != null) {
                            Player player = game.getPlayer(playerId);
                            if (player != null) {
                                game.informPlayer(player, "That turn is not available for rollback.");
                            }
                        }
                    }
                }
                break;
            case ADD_PERMISSION_TO_ROLLBACK_TURN:
                if (userRequestingRollback != null && requestsOpen > 0 && !userId.equals(userRequestingRollback)) {
                    requestsOpen--;
                    if (requestsOpen == 0) {
                        game.rollbackTurns(turnsToRollback);
                        turnsToRollback = -1;
                        userRequestingRollback = null;
                        requestsOpen = -1;
                    }
                }
                break;
            case DENY_PERMISSION_TO_ROLLBACK_TURN: // one player has denied - so cancel the request
            {
                UUID playerId = getPlayerId(userId);
                if (playerId != null) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        if (userRequestingRollback != null && requestsOpen > 0 && !userId.equals(userRequestingRollback)) {
                            turnsToRollback = -1;
                            userRequestingRollback = null;
                            requestsOpen = -1;
                            game.informPlayers("Rollback request denied by " + player.getLogName());
                        }
                    }
                }
            }
            break;
            case CONCEDE: {
                UUID playerId = getPlayerId(userId);
                if (playerId != null) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        game.informPlayers(player.getLogName() + " wants to concede");
                        game.setConcedingPlayer(getPlayerId(userId));
                    }
                }
                break;
            }
            case MANA_AUTO_PAYMENT_OFF:
                game.setManaPaymentMode(getPlayerId(userId), false);
                break;
            case MANA_AUTO_PAYMENT_ON:
                game.setManaPaymentMode(getPlayerId(userId), true);
                break;
            case MANA_AUTO_PAYMENT_RESTRICTED_OFF:
                game.setManaPaymentModeRestricted(getPlayerId(userId), false);
                break;
            case MANA_AUTO_PAYMENT_RESTRICTED_ON:
                game.setManaPaymentModeRestricted(getPlayerId(userId), true);
                break;
            case USE_FIRST_MANA_ABILITY_ON:
                game.setUseFirstManaAbility(getPlayerId(userId), true);
                break;
            case USE_FIRST_MANA_ABILITY_OFF:
                game.setUseFirstManaAbility(getPlayerId(userId), false);
                break;
            case ADD_PERMISSION_TO_SEE_HAND_CARDS:
                if (data instanceof UUID) {
                    UUID playerId = getPlayerId(userId);
                    if (playerId != null) {
                        Player player = game.getPlayer(playerId);
                        if (player != null) {
                            player.addPermissionToShowHandCards((UUID) data);
                        }
                    }
                }
                break;
            case REVOKE_PERMISSIONS_TO_SEE_HAND_CARDS: {
                UUID playerId = getPlayerId(userId);
                if (playerId != null) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.revokePermissionToSeeHandCards();
                    }
                }
                break;
            }
            case REQUEST_PERMISSION_TO_SEE_HAND_CARDS:
                if (data instanceof UUID) {
                    requestPermissionToSeeHandCards(userId, (UUID) data);
                }
                break;
            case VIEW_LIMITED_DECK:
                if (data instanceof UUID) {
                    UUID targetPlayerId = (UUID) data;
                    viewDeckOrSideboard(getPlayerId(userId), userId, targetPlayerId, false);
                }
                break;
            case VIEW_SIDEBOARD:
                if (data instanceof UUID) {
                    UUID targetPlayerId = (UUID) data;
                    viewDeckOrSideboard(getPlayerId(userId), userId, targetPlayerId, true);
                }
                break;
            default:
                game.sendPlayerAction(playerAction, getPlayerId(userId), data);
        }
    }

    private int requestPermissionToRollback(UUID userIdRequester, int numberTurns) {
        int requests = 0;
        for (Player player : game.getState().getPlayers().values()) {
            Optional<User> requestedUser = getUserByPlayerId(player.getId());
            if (player.isInGame() && player.isHuman()
                    && requestedUser.isPresent()
                    && !requestedUser.get().getId().equals(userIdRequester)) {
                requests++;
                GameSessionPlayer gameSession = gameSessions.get(player.getId());
                if (gameSession != null) {
                    gameSession.requestPermissionToRollbackTurn(userIdRequester, numberTurns);
                }
            }
        }
        return requests;
    }

    private void requestPermissionToSeeHandCards(UUID userIdRequester, UUID userIdGranter) {
        Player grantingPlayer = game.getPlayer(userIdGranter);
        if (grantingPlayer != null) {
            if (!grantingPlayer.getUsersAllowedToSeeHandCards().contains(userIdRequester)) {
                if (grantingPlayer.isHuman()) {
                    GameSessionPlayer gameSession = gameSessions.get(userIdGranter);
                    if (gameSession != null) {
                        UUID requestingPlayerId = getPlayerId(userIdRequester);
                        if (requestingPlayerId == null || !requestingPlayerId.equals(grantingPlayer.getId())) { // don't allow request for your own cards
                            if (grantingPlayer.isPlayerAllowedToRequestHand(game.getId(), requestingPlayerId)) {
                                // one time request per user restrict, enable request will reset users list and allows again
                                grantingPlayer.addPlayerToRequestedHandList(game.getId(), requestingPlayerId);
                                gameSession.requestPermissionToSeeHandCards(userIdRequester);
                            } else {
                                // player does not allow the request
                                managerFactory.userManager().getUser(userIdRequester).ifPresent(requester -> {
                                    requester.showUserMessage("Request to show hand cards", "Player " + grantingPlayer.getName() + " does not allow to request to show hand cards!");
                                });
                            }
                        }
                    }
                } else {
                    // Non Human players always allow to see the hand cards
                    grantingPlayer.addPermissionToShowHandCards(userIdRequester);
                }
            } else {
                // user can already see the cards
                managerFactory.userManager().getUser(userIdRequester).ifPresent(requester -> {
                    requester.showUserMessage("Request to show hand cards", "You can see already the hand cards of player " + grantingPlayer.getName() + '!');
                });

            }

        }
    }

    private void viewDeckOrSideboard(UUID playerId, UUID userId, UUID targetPlayerId, boolean isSideboardOnly) {
        Player requestPlayer = game.getPlayer(playerId);
        Player targetPlayer = game.getPlayer(targetPlayerId);
        if (requestPlayer == null || targetPlayer == null) {
            return;
        }

        // allows for itself or computers only
        // TODO: implement allow deck view for all (same as allow hand view for all)
        if (!requestPlayer.getId().equals(targetPlayer.getId()) && !targetPlayer.isComputer()) {
            logger.error("Player " + requestPlayer.getName() + " trying to cheat with deck/sideboard view");
            // TODO: inform other players about cheating?
            return;
        }

        User user = managerFactory.userManager().getUser(userId).orElse(null);
        Table table = managerFactory.tableManager().getTable(tableId);
        if (user == null || table == null) {
            return;
        }

        MatchPlayer deckSource = table.getMatch().getPlayer(targetPlayerId);
        if (deckSource == null) {
            return;
        }

        if (isSideboardOnly) {
            // sideboard data already sent in PlayerView, so no need to re-sent it TODO: re-sent deck instead?
            user.ccViewSideboard(table.getId(), game.getId(), targetPlayerId);
        } else {
            user.ccViewLimitedDeck(deckSource.getDeckForViewer(), table.getId(), table.getParentTableId(), requestsOpen, true);
        }
    }

    public void cheatShow(UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            player.signalPlayerCheat();
        }
    }

    public void onResponseIdleTimeout(UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            String sb = player.getLogName()
                    + " has timed out (player had priority and was not active for "
                    + getResponseIdleTimeoutSecs() + " seconds ) - Auto concede.";
            managerFactory.chatManager().broadcast(chatId, "", sb, MessageColor.BLACK, true, game, MessageType.STATUS, null);
            game.idleTimeout(playerId);
        }
    }

    public void endGame(final String message) throws MageException {
        // send end game message/dialog
        for (final GameSessionPlayer gameSession : getGameSessions()) {
            gameSession.gameOver(message);
            gameSession.removeGame();
        }
        for (final GameSessionWatcher gameWatcher : getGameSessionWatchers()) {
            gameWatcher.gameOver(message);
        }

        // start next game or close finished table
        managerFactory.tableManager().endGame(tableId);
    }

    public UUID getSessionId() {
        return this.gameSessionId;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void sendPlayerUUID(UUID userId, final UUID data) {
        sendMessage(userId, playerId -> sendDirectPlayerUUID(playerId, data));
    }

    public void sendPlayerString(UUID userId, final String data) {
        sendMessage(userId, playerId -> sendDirectPlayerString(playerId, data));
    }

    public void sendPlayerManaType(UUID userId, final UUID manaTypePlayerId, final ManaType data) {
        sendMessage(userId, playerId -> sendDirectPlayerManaType(playerId, manaTypePlayerId, data));
    }

    public void sendPlayerBoolean(UUID userId, final Boolean data) {
        sendMessage(userId, playerId -> sendDirectPlayerBoolean(playerId, data));
    }

    public void sendPlayerInteger(UUID userId, final Integer data) {
        sendMessage(userId, playerId -> sendDirectPlayerInteger(playerId, data));
    }

    private void updatePriorityTimers() {
        // update player timers to actual values
        if (!timers.isEmpty()) {
            for (Player player : game.getState().getPlayers().values()) {
                PriorityTimer timer = timers.get(player.getId());
                if (timer != null) {
                    player.setPriorityTimeLeft(timer.getCount());
                }
            }
        }
    }

    private synchronized void updateGame() {
        updatePriorityTimers();
        for (final GameSessionPlayer gameSession : getGameSessions()) {
            gameSession.update();
        }
        for (final GameSessionWatcher gameWatcher : getGameSessionWatchers()) {
            gameWatcher.update();
        }
    }

    private synchronized void endGameInfo() {
        Table table = managerFactory.tableManager().getTable(tableId);
        if (table != null) {
            if (table.getMatch() != null) {
                for (final GameSessionPlayer gameSession : getGameSessions()) {
                    gameSession.endGameInfo(table);
                }
                // TODO: inform watchers about game end and who won
            }
        }
    }

    private synchronized void ask(UUID playerId, final String question, final Map<String, Serializable> options) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).ask(question, options));

    }

    private synchronized void chooseAbility(UUID playerId, final String objectName, final List<? extends Ability> choices, String message) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).chooseAbility(new AbilityPickerView(getGameView(playerId), objectName, choices, message)));
    }

    private synchronized void choosePile(UUID playerId, final String message, final List<? extends Card> pile1, final List<? extends Card> pile2) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).choosePile(message, new CardsView(game, pile1, playerId), new CardsView(game, pile2, playerId)));
    }

    private synchronized void chooseMode(UUID playerId, final Map<UUID, String> modes, final String message) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).chooseAbility(new AbilityPickerView(getGameView(playerId), modes, message)));
    }

    private synchronized void chooseChoice(UUID playerId, final Choice choice) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).chooseChoice(choice));
    }

    private synchronized void target(UUID playerId, final String question, final Cards cards, final List<Permanent> perms, final Set<UUID> targets, final boolean required, final Map<String, Serializable> options) throws MageException {
        perform(playerId, playerId1 -> {
            if (cards != null) {
                getGameSession(playerId1).target(question, new CardsView(game, cards.getCards(game), playerId, true), targets, required, options);
            } else if (perms != null) {
                CardsView permsView = new CardsView();
                for (Permanent perm : perms) {
                    permsView.put(perm.getId(), new PermanentView(perm, game.getCard(perm.getId()), playerId1, game));
                }
                getGameSession(playerId1).target(question, permsView, targets, required, options);
            } else {
                getGameSession(playerId1).target(question, new CardsView(), targets, required, options);
            }
        });

    }

    private synchronized void target(UUID playerId, final String question, final Collection<? extends Ability> abilities, final boolean required, final Map<String, Serializable> options) throws MageException {
        perform(playerId, playerId1 -> {
            CardsView cardsView = new CardsView(abilities, game);
            getGameSession(playerId1).target(question, cardsView, null, required, options);
        });
    }

    private synchronized void select(final UUID playerId, final String message, final Map<String, Serializable> options) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).select(message, options));
    }

    private synchronized void playMana(UUID playerId, final String message, final Map<String, Serializable> options) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).playMana(message, options));
    }

    private synchronized void playXMana(UUID playerId, final String message) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).playXMana(message));
    }

    private synchronized void amount(UUID playerId, final String message, final int min, final int max) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).getAmount(message, min, max));
    }

    private synchronized void multiAmount(UUID playerId, final List<MultiAmountMessage> messages,
                                          final int min, final int max, final Map<String, Serializable> options)
            throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).getMultiAmount(messages, min, max, options));
    }

    private void informOthers(UUID waitingPlayerId) {
        StringBuilder message = new StringBuilder();
        if (game.getStep() != null) {
            message.append(game.getTurnStepType().toString()).append(" - ");
        }
        message.append("Waiting for ").append(game.getPlayer(waitingPlayerId).getLogName());
        for (final Entry<UUID, GameSessionPlayer> entry : getGameSessionsMap().entrySet()) {
            if (!entry.getKey().equals(waitingPlayerId)) {
                entry.getValue().inform(message.toString());
            }
        }
        for (final GameSessionWatcher watcher : getGameSessionWatchers()) {
            watcher.inform(message.toString());
        }
    }

    private void informOthers(List<UUID> players) {
        // first player is always original controller
        Player controller = null;
        if (players != null && !players.isEmpty()) {
            controller = game.getPlayer(players.get(0));
        }
        if (controller == null || game.getStep() == null || game.getTurnStepType() == null) {
            return;
        }
        final String message = new StringBuilder(game.getTurnStepType().toString()).append(" - Waiting for ").append(controller.getName()).toString();
        for (final Entry<UUID, GameSessionPlayer> entry : getGameSessionsMap().entrySet()) {
            boolean skip = players.stream().anyMatch(playerId -> entry.getKey().equals(playerId));
            if (!skip) {
                entry.getValue().inform(message);
            }
        }
        for (final GameSessionWatcher watcher : getGameSessionWatchers()) {
            watcher.inform(message);
        }
    }

    private void informPersonal(UUID playerId, final String message) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).informPersonal(message), false);
    }

    private void error(String message, Exception ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append("\n");
        sb.append("\n");
        sb.append(ex);
        sb.append("\nServer version: ").append(Main.getVersion().toString());
        sb.append("\nStack trace:");
        sb.append("\n");
        for (StackTraceElement e : ex.getStackTrace()) {
            sb.append(e.toString()).append("\n");
        }
        String mes = sb.toString();

        // send error for each player
        for (final Entry<UUID, GameSessionPlayer> entry : getGameSessionsMap().entrySet()) {
            entry.getValue().gameError(mes);
        }
    }

    public synchronized GameView getGameView(UUID playerId) {
        return getGameSession(playerId).getGameView();
    }

    @Override
    public void endGameWithResult(String result) {
        try {
            endGame(result);
        } catch (MageException ex) {
            logger.fatal("Game Result error", ex);
        }
    }

    public boolean saveGame() {
        OutputStream file = null;
        ObjectOutput output = null;
        OutputStream buffer = null;
        try {
            file = new FileOutputStream("saved/" + game.getId().toString() + ".game");
            buffer = new BufferedOutputStream(file);
            output = new ObjectOutputStream(new GZIPOutputStream(buffer));
            output.writeObject(game);
            output.writeObject(game.getGameStates());
            logger.debug("Saved game:" + game.getId());
            return true;
        } catch (IOException ex) {
            logger.fatal("Cannot save game.", ex);
        } finally {
            StreamUtils.closeQuietly(file);
            StreamUtils.closeQuietly(output);
            StreamUtils.closeQuietly(buffer);
        }
        return false;
    }

    /**
     * Performs a request to a player
     *
     * @param playerId
     * @param command
     */
    private void perform(UUID playerId, Command command) {
        perform(playerId, command, true);
    }

    private void perform(UUID playerId, Command command, boolean informOthers) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            throw new IllegalArgumentException("Can't perform command for unknown player id: " + playerId);
        }

        Player realPlayerController = game.getPlayer(player.getTurnControlledBy());
        if (realPlayerController == null) {
            throw new IllegalArgumentException("Can't find real turn controller for player id: " + playerId);
        }

        if (gameSessions.containsKey(realPlayerController.getId())) {
            startResponseIdleTimeout(realPlayerController.getId());
            command.execute(realPlayerController.getId());
        }
        // TODO: if watcher disconnects then game freezes with active timer, must be fix for such use case
        //  same for another player (can be fixed by super-duper connection)
        if (informOthers) {
            informOthers(realPlayerController.getId());
        }
    }

    /**
     * A player has send an answer / request
     *
     * @param userId
     * @param command
     */
    private void sendMessage(UUID userId, Command command) {
        final UUID playerId = userPlayerMap.get(userId);
        // player has game under control (is not cotrolled by other player)
        Player player = game.getPlayer(playerId);
        if (player != null && player.isGameUnderControl()) {
            // if it's your priority (or game not started yet in which case it will be null)
            // then execute only your action
            if (game.getPriorityPlayerId() == null || game.getPriorityPlayerId().equals(playerId)) {
                if (gameSessions.containsKey(playerId)) {
                    stopResponseIdleTimeout();
                    command.execute(playerId);
                }
            } else {
                // otherwise execute the action under other player's control
                for (UUID controlled : player.getPlayersUnderYourControl()) {
                    Player controlledPlayer = game.getPlayer(controlled);
                    if ((gameSessions.containsKey(controlled) || controlledPlayer.isComputer()) && game.getPriorityPlayerId().equals(controlled)) {
                        stopResponseIdleTimeout();
                        command.execute(controlled);
                    }
                }
                // else player has no priority to do something, so ignore the command
                // e.g. you click at one of your cards, but you can't play something at that moment
            }

        } else {
            // ignore - no control over the turn
        }
    }

    private void startResponseIdleTimeout(final UUID playerId) {
        if (!useResponseIdleTimeout) {
            return;
        }
        stopResponseIdleTimeout();
        responseIdleTimeoutPlayerId = playerId;
        responseIdleTimeoutFuture = responseIdleTimeoutExecutor.schedule(
                () -> onResponseIdleTimeout(playerId),
                getResponseIdleTimeoutSecs(),
                TimeUnit.SECONDS
        );
    }

    private long getResponseIdleTimeoutSecs() {
        return Main.isTestMode() ? 3600 : managerFactory.configSettings().getMaxSecondsIdle();
    }

    private void stopResponseIdleTimeout() {
        logger.debug("cancelTimeout");
        if (responseIdleTimeoutFuture != null) {
            synchronized (responseIdleTimeoutFuture) {
                responseIdleTimeoutFuture.cancel(false);
                responseIdleTimeoutPlayerId = null;
            }
        }
    }

    @FunctionalInterface
    interface Command {
        void execute(UUID player);
    }

    private Map<UUID, GameSessionPlayer> getGameSessionsMap() {
        Map<UUID, GameSessionPlayer> newGameSessionsMap = new HashMap<>();
        final Lock r = gameSessionsLock.readLock();
        r.lock();
        try {
            newGameSessionsMap.putAll(gameSessions);
        } finally {
            r.unlock();
        }
        return newGameSessionsMap;
    }

    private List<GameSessionPlayer> getGameSessions() {
        List<GameSessionPlayer> newGameSessions = new ArrayList<>();
        final Lock r = gameSessionsLock.readLock();
        r.lock();
        try {
            newGameSessions.addAll(gameSessions.values());
        } finally {
            r.unlock();
        }
        return newGameSessions;
    }

    private List<GameSessionWatcher> getGameSessionWatchers() {
        List<GameSessionWatcher> newGameSessionWatchers = new ArrayList<>();
        final Lock r = gameSessionsLock.readLock();
        r.lock();
        try {
            newGameSessionWatchers.addAll(watchers.values());
        } finally {
            r.unlock();
        }
        return newGameSessionWatchers;
    }

    private void sendDirectPlayerUUID(UUID playerId, UUID data) {
        // real player
        GameSessionPlayer session = getGameSession(playerId);
        if (session != null) {
            session.sendPlayerUUID(data);
            return;
        }

        // computer under control
        Player player = game.getPlayer(playerId);
        if (player != null && player.isComputer()) {
            player.setResponseUUID(data);
        }
    }

    private void sendDirectPlayerString(UUID playerId, String data) {
        // real player
        GameSessionPlayer session = getGameSession(playerId);
        if (session != null) {
            session.sendPlayerString(data);
            return;
        }

        // computer under control
        Player player = game.getPlayer(playerId);
        if (player != null && player.isComputer()) {
            player.setResponseString(data);
        }
    }

    private void sendDirectPlayerManaType(UUID playerId, UUID manaTypePlayerId, ManaType manaType) {
        // real player
        GameSessionPlayer session = getGameSession(playerId);
        if (session != null) {
            session.sendPlayerManaType(manaTypePlayerId, manaType);
            return;
        }

        // computer under control
        Player player = game.getPlayer(playerId);
        if (player != null && player.isComputer()) {
            player.setResponseManaType(manaTypePlayerId, manaType);
        }
    }

    private void sendDirectPlayerBoolean(UUID playerId, Boolean data) {
        // real player
        GameSessionPlayer session = getGameSession(playerId);
        if (session != null) {
            session.sendPlayerBoolean(data);
            return;
        }

        // computer under control
        Player player = game.getPlayer(playerId);
        if (player != null && player.isComputer()) {
            player.setResponseBoolean(data);
        }
    }

    private void sendDirectPlayerInteger(UUID playerId, Integer data) {
        // real player
        GameSessionPlayer session = getGameSession(playerId);
        if (session != null) {
            session.sendPlayerInteger(data);
            return;
        }

        // computer under control
        Player player = game.getPlayer(playerId);
        if (player != null && player.isComputer()) {
            player.setResponseInteger(data);
        }
    }

    private GameSessionPlayer getGameSession(UUID playerId) {
        // TODO: check parent callers - there are possible problems with sync, can be related to broken "fix" logs too
        //  It modify players data, but:
        //  * some sendXXX methods calls without synchronized (getGameSession can be in read mode?)
        //  * some informXXX methods calls with synchronized (users must get actual data, so keep write mode and add synchronized?)
        // find actual timers before send data
        updatePriorityTimers();

        // TODO: add data consistence check here: is game view is same for all game cycles?
        return gameSessions.get(playerId);
    }

    public String getPlayerNameList() {
        StringBuilder sb = new StringBuilder(" [");
        for (UUID playerId : userPlayerMap.values()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                sb.append(player.getName()).append("(Left=").append(player.hasLeft() ? "Y" : "N").append(") ");
            } else {
                sb.append("player missing: ").append(playerId).append(' ');
            }
        }
        return sb.append(']').toString();
    }

    public boolean isAllowedToWatch(UUID userId) {
        Optional<User> user = managerFactory.userManager().getUser(userId);
        if (user.isPresent()) {
            return !gameOptions.bannedUsers.contains(user.get().getName());
        }
        return false;
    }

    public String getGameStateDebugMessage() {
        if (game == null) {
            return "";
        }
        GameState state = game.getState();
        if (state == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<br/>Game State:<br/><font size=-2>");
        sb.append(state);

        sb.append("<br>Active player is: ");
        sb.append(game.getPlayer(state.getActivePlayerId()).getName());
        sb.append("<br>isGameOver: ");
        sb.append(state.isGameOver());
        sb.append("<br>Current phase is: ");
        sb.append(state.getTurn().getPhase());
        sb.append("<br>getBattlefield: ");
        sb.append(state.getBattlefield());
        sb.append("<br>getChoosingPlayerId: ");
        if (state.getChoosingPlayerId() != null) {
            sb.append(game.getPlayer(state.getChoosingPlayerId()).getName());
        } else {
            sb.append("noone!");
        }
        sb.append("<br>getCombat: ");
        sb.append(state.getCombat());
        sb.append("<br>getCommand: ");
        sb.append(state.getCommand());
        sb.append("<br>getContinuousEffects: ");
        sb.append(state.getContinuousEffects());
        sb.append("<br>getCopiedCards: ");
        sb.append(state.getCopiedCards());
        sb.append("<br>getDelayed: ");
        sb.append(state.getDelayed());
        sb.append("<br>getDesignations: ");
        sb.append(state.getDesignations());
        sb.append("<br>getExile: ");
        sb.append(state.getExile());
        sb.append("<br>getMonarchId: ");
        sb.append(state.getMonarchId());
        sb.append("<br>getNextPermanentOrderNumber: ");
        sb.append(state.getNextPermanentOrderNumber());
        sb.append("<br>getPlayerByOrderId: ");
        if (state.getPlayerByOrderId() != null) {
            sb.append(game.getPlayer(state.getPlayerByOrderId()).getName());
        } else {
            sb.append("noone!");
        }
        sb.append("<br>getPlayerList: ");
        sb.append(state.getPlayerList());
        sb.append("<br>getPlayers: ");
        sb.append(state.getPlayers());
        sb.append("<br><font color=orange>Player with Priority is: ");
        if (state.getPriorityPlayerId() != null) {
            sb.append(game.getPlayer(state.getPriorityPlayerId()).getName());
        } else {
            sb.append("noone!");
        }
        sb.append("</font><br>getRevealed: ");
        sb.append(state.getRevealed());
        sb.append("<br>getSpecialActions: ");
        sb.append(state.getSpecialActions());
        sb.append("<br>getStack: ");
        sb.append(state.getStack());
        sb.append("<br>getStepNum: ");
        sb.append(state.getStepNum());
        sb.append("<br>getTurn: ");
        sb.append(state.getTurn());
        sb.append("<br>getExtraTurnId: ");
        sb.append(state.getExtraTurnId());
        sb.append("<br>getTurnMods: ");
        sb.append(state.getTurnMods());
        sb.append("<br>getTurnNum: ");
        sb.append(state.getTurnNum());

        sb.append("<br>Using plane chase?:" + state.isPlaneChase());
        if (state.isPlaneChase()) {
            Plane currentPlane = state.getCurrentPlane();
            if (currentPlane != null) {
                sb.append("<br>Current plane:" + currentPlane.getName());
            }
        }

        sb.append("<br>Future Timeout:");
        if (responseIdleTimeoutFuture != null) {
            sb.append("Cancelled?=");
            sb.append(responseIdleTimeoutFuture.isCancelled());
            sb.append(",,,Done?=");
            sb.append(responseIdleTimeoutFuture.isDone());
            sb.append(",,,GetDelay?=");
            sb.append((int) responseIdleTimeoutFuture.getDelay(TimeUnit.SECONDS));
        } else {
            sb.append("Not using future Timeout!");
        }
        sb.append("</font>");
        return sb.toString();
    }

    private String getName(Player player) {
        return player != null ? player.getName() : "none";
    }

    public String getPingsInfo() {
        List<String> usersInfo = new ArrayList<>();
        for (Map.Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
            Optional<User> user = managerFactory.userManager().getUser(entry.getKey());
            user.ifPresent(u -> usersInfo.add("* " + u.getName() + ": " + u.getPingInfo()));
        }
        Collections.sort(usersInfo);
        usersInfo.add(0, "Players ping:");

        List<String> watchersinfo = new ArrayList<>();
        for (Map.Entry<UUID, GameSessionWatcher> entry : watchers.entrySet()) {
            Optional<User> user = managerFactory.userManager().getUser(entry.getValue().userId);
            user.ifPresent(u -> watchersinfo.add("* " + u.getName() + ": " + u.getPingInfo()));
        }
        Collections.sort(watchersinfo);
        if (watchersinfo.size() > 0) {
            watchersinfo.add(0, "Watchers ping:");
        }

        usersInfo.addAll(watchersinfo);
        return String.join("<br>", usersInfo);
    }

    private String asGood(String text) {
        return "<font color='green'><b>" + text + "</b></font>";
    }

    private String asBad(String text) {
        return "<font color='red'><b>" + text + "</b></font>";
    }

    private String asWarning(String text) {
        return "<font color='aqua'><b>" + text + "</b></font>";
    }

    public String attemptToFixGame(User user) {
        // try to fix disconnects

        if (game == null) {
            return "";
        }
        GameState state = game.getState();
        if (state == null) {
            return "";
        }

        String playersInfo = game.getPlayerList().stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(p -> p.getName() + (p.isInGame() ? " (play)" : " (out)"))
                .collect(Collectors.joining(", "));
        logger.warn("FIX command was called for game " + game.getId() + " by " + user.getName()
                + "; players: " + playersInfo + "; " + game);
        StringBuilder sb = new StringBuilder();
        sb.append("<font color='red'>FIX command called by ").append(user.getName()).append("</font>");
        sb.append("<font size='-2'>"); // font resize start for all next logs
        sb.append("<br>Game ID: ").append(game.getId());
        if (game.getTurnPhaseType() == null) {
            sb.append("<br>Phase: not started").append(" Step: not started");
        } else {
            sb.append("<br>Phase: ").append(game.getTurnPhaseType().toString()).append(" Step: ").append(game.getTurnStepType().toString());
        }
        // pings info
        sb.append("<br>");
        sb.append(getPingsInfo());

        boolean fixedAlready = false;
        List<String> fixActions = new ArrayList<>(); // for logs info

        // fix active
        fixedAlready = fixPlayer(game.getPlayer(state.getActivePlayerId()), state, "active", sb, fixActions, fixedAlready, false);

        // fix lost choosing dialog
        fixedAlready = fixPlayer(game.getPlayer(state.getChoosingPlayerId()), state, "choosing", sb, fixActions, fixedAlready, false);

        // fix lost priority
        fixedAlready = fixPlayer(game.getPlayer(state.getPriorityPlayerId()), state, "priority", sb, fixActions, fixedAlready, false);

        // fix response idle timeout (if player has good connection, but can't response)
        sb.append("<br>Fixing response idle timeout: ");
        if (responseIdleTimeoutFuture != null) {
            int delay = (int) responseIdleTimeoutFuture.getDelay(TimeUnit.SECONDS);
            if (delay < 25) {
                fixedAlready = fixPlayer(game.getPlayer(responseIdleTimeoutPlayerId), state, "response idle", sb, fixActions, fixedAlready, true);
            } else {
                sb.append(" (").append(asGood("OK")).append(String.format(", allow to idle %d of %d secs",
                        delay, getResponseIdleTimeoutSecs()));
            }
        } else {
            sb.append(" (").append(asGood("OK")).append(", timeout is not using)");
        }

        // TODO: fix non started game (send game started event to user?)

        // ALL DONE
        if (fixActions.isEmpty()) {
            fixActions.add("none");
        }
        String appliedFixes = fixActions.stream().collect(Collectors.joining(", "));
        sb.append("<br>Applied fixes: ").append(appliedFixes);
        sb.append("</font>"); // font resize end
        sb.append("<br>");

        logger.warn("FIX command result: " + appliedFixes);

        return sb.toString();
    }

    private boolean fixPlayer(Player player, GameState state, String fixType, StringBuilder sb, List<String> fixActions, boolean fixedAlready, boolean forceToConcede) {
        sb.append("<br>Fixing ").append(fixType).append(" player: ").append(getName(player));
        if (player != null && (forceToConcede || !player.canRespond())) {
            fixActions.add(fixType + " fix");

            sb.append("<br><font color='red'>WARNING, ").append(fixType).append(" player can't respond.</font>");
            sb.append("<br>Try to concede...");
            player.concede(game);
            player.leave(); // abort any wait response actions
            sb.append(" (").append(asWarning("OK")).append(", concede done)");

            sb.append("<br>Try to skip step...");
            if (!fixedAlready) {
                Phase currentPhase = game.getPhase();
                if (currentPhase != null) {
                    currentPhase.getStep().skipStep(game, state.getActivePlayerId());
                    fixedAlready = true;
                    sb.append(" (").append(asWarning("OK")).append(", skip step done)");
                } else {
                    sb.append(" (").append(asBad("FAIL")).append(", step is null)");
                }
            } else {
                sb.append(" (OK, already skipped before)");
            }
        } else {
            sb.append(player != null ? " (" + asGood("OK") + ", can respond)" : " (" + asGood("OK") + ", no player)");
        }

        return fixedAlready;
    }

    public UUID getTableId() {
        return tableId;
    }
}
