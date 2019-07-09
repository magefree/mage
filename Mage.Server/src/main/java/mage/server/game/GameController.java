package mage.server.game;

import mage.MageException;
import mage.abilities.Ability;
import mage.abilities.common.PassAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.constants.Zone;
import mage.game.*;
import mage.game.command.Plane;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.match.MatchPlayer;
import mage.game.permanent.Permanent;
import mage.game.turn.Phase;
import mage.interfaces.Action;
import mage.players.Player;
import mage.server.*;
import mage.server.util.ConfigSettings;
import mage.server.util.Splitter;
import mage.server.util.SystemUtil;
import mage.server.util.ThreadExecutor;
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
import java.util.zip.GZIPOutputStream;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GameController implements GameCallback {

    private static final int GAME_TIMEOUTS_CHECK_JOINING_STATUS_EVERY_SECS = 15; // checks and inform players about joining status
    private static final int GAME_TIMEOUTS_CANCEL_PLAYER_GAME_JOINING_AFTER_INACTIVE_SECS = 4 * 60; // leave player from game if it don't join and inactive on server

    private static final ExecutorService gameExecutor = ThreadExecutor.instance.getGameExecutor();
    private static final Logger logger = Logger.getLogger(GameController.class);

    protected final ScheduledExecutorService joinWaitingExecutor = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> futureTimeout;
    protected static final ScheduledExecutorService timeoutIdleExecutor = ThreadExecutor.instance.getTimeoutIdleExecutor();

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
    private boolean useTimeout = true;
    private final GameOptions gameOptions;

    private UUID userReqestingRollback;
    private int turnsToRollback;
    private int requestsOpen;

    public GameController(Game game, ConcurrentMap<UUID, UUID> userPlayerMap, UUID tableId, UUID choosingPlayerId, GameOptions gameOptions) {
        gameSessionId = UUID.randomUUID();
        this.userPlayerMap = userPlayerMap;
        chatId = ChatManager.instance.createChatSession("Game " + game.getId());
        this.userReqestingRollback = null;
        this.game = game;
        this.game.setSaveGame(ConfigSettings.instance.isSaveGameActivated());
        this.tableId = tableId;
        this.choosingPlayerId = choosingPlayerId;
        this.gameOptions = gameOptions;
        useTimeout = game.getPlayers().values().stream().allMatch(Player::isHuman);
        init();

    }

    public void cleanUp() {
        cancelTimeout();
        for (GameSessionPlayer gameSessionPlayer : getGameSessions()) {
            gameSessionPlayer.cleanUp();
        }
        ChatManager.instance.destroyChatSession(chatId);
        for (PriorityTimer priorityTimer : timers.values()) {
            priorityTimer.cancel();
        }
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
                                ChatManager.instance.broadcast(chatId, "", event.getMessage(), MessageColor.BLACK, true, MessageType.GAME, null);
                                logger.trace(game.getId() + " " + event.getMessage());
                                break;
                            case STATUS:
                                ChatManager.instance.broadcast(chatId, "", event.getMessage(), MessageColor.ORANGE, event.getWithTime(), MessageType.GAME, null);
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
                                timer = timers.get(playerId);
                                if (timer == null) {
                                    Player player = game.getState().getPlayer(playerId);
                                    if (player != null) {
                                        timer = createPlayerTimer(event.getPlayerId(), player.getPriorityTimeLeft());
                                    } else {
                                        throw new MageException("RESUME_TIMER: player can't be null");
                                    }
                                }
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
                    } catch (MageException ex) {
                        logger.fatal("Table event listener error ", ex);
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
                                chooseAbility(event.getPlayerId(), objectName, event.getAbilities());
                                break;
                            case CHOOSE_PILE:
                                choosePile(event.getPlayerId(), event.getMessage(), event.getPile1(), event.getPile2());
                                break;
                            case CHOOSE_MODE:
                                chooseMode(event.getPlayerId(), event.getModes());
                                break;
                            case CHOOSE_CHOICE:
                                chooseChoice(event.getPlayerId(), event.getChoice());
                                break;
                            case AMOUNT:
                                amount(event.getPlayerId(), event.getMessage(), event.getMin(), event.getMax());
                                break;
                            case PERSONAL_MESSAGE:
                                informPersonal(event.getPlayerId(), event.getMessage());
                                break;
                        }
                    } catch (MageException ex) {
                        logger.fatal("Player event listener error ", ex);
                    }
                }
        );
        joinWaitingExecutor.scheduleAtFixedRate(() -> {
            try {
                sendInfoAboutPlayersNotJoinedYet();
            } catch (Exception ex) {
                logger.fatal("Send info about player not joined yet:", ex);
            }
        }, GAME_TIMEOUTS_CHECK_JOINING_STATUS_EVERY_SECS, GAME_TIMEOUTS_CHECK_JOINING_STATUS_EVERY_SECS, TimeUnit.SECONDS);
        checkStart();
    }

    /**
     * We create a timer that will run every 250 ms individually for a player
     * decreasing his internal game counter. Later on this counter is used to
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
        Optional<User> user = UserManager.instance.getUser(userId);
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
            gameSession = new GameSessionPlayer(game, userId, playerId);
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
        ChatManager.instance.broadcast(chatId, "", game.getPlayer(playerId).getLogName() + " has " + joinType + " the game", MessageColor.ORANGE, true, MessageType.GAME, null);
        checkStart();
    }

    private synchronized void startGame() {
        if (gameFuture == null) {
            for (GameSessionPlayer gameSessionPlayer : getGameSessions()) {
                gameSessionPlayer.init();
            }

            GameWorker worker = new GameWorker(game, choosingPlayerId, this);
            gameFuture = gameExecutor.submit(worker);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
            if (game.getState().getChoosingPlayerId() != null) {
                // start timer to force player to choose starting player otherwise loosing by being idle
                setupTimeout(game.getState().getChoosingPlayerId());
            }
        }
    }

    private void sendInfoAboutPlayersNotJoinedYet() {
        // runs every 15 secs untill all players join
        for (Player player : game.getPlayers().values()) {
            if (!player.hasLeft() && player.isHuman()) {
                Optional<User> requestedUser = getUserByPlayerId(player.getId());
                if (requestedUser.isPresent()) {
                    User user = requestedUser.get();
                    if (!user.isConnected()) {
                        if (gameSessions.get(player.getId()) == null) {
                            // join the game because player has not joined are was removed because of disconnect
                            user.removeConstructing(player.getId());
                            GameManager.instance.joinGame(game.getId(), user.getId());
                            logger.debug("Player " + player.getName() + " (disconnected) has joined gameId: " + game.getId());
                        }
                        ChatManager.instance.broadcast(chatId, player.getName(), user.getPingInfo() + " is pending to join the game", MessageColor.BLUE, true, ChatMessage.MessageType.STATUS, null);
                        if (user.getSecondsDisconnected() > GAME_TIMEOUTS_CANCEL_PLAYER_GAME_JOINING_AFTER_INACTIVE_SECS) {
                            // TODO: 2019.04.22 - if user playing another game on server but not joining (that's the reason?), then that's check will never trigger
                            // Cancel player join possibility lately after 4 minutes
                            logger.debug("Player " + player.getName() + " - canceled game (after 240 seconds) gameId: " + game.getId());
                            player.leave();
                        }
                    }
                } else if (!player.hasLeft()) {
                    logger.debug("Player " + player.getName() + " canceled game (no user) gameId: " + game.getId());
                    player.leave();
                }
            }
        }
        checkStart();
    }

    private Optional<User> getUserByPlayerId(UUID playerId) {
        for (Map.Entry<UUID, UUID> entry : userPlayerMap.entrySet()) {
            if (entry.getValue().equals(playerId)) {
                return UserManager.instance.getUser(entry.getKey());
            }
        }
        return Optional.empty();
    }

    private void checkStart() {
        if (allJoined()) {
            joinWaitingExecutor.shutdownNow();
            ThreadExecutor.instance.getCallExecutor().execute(this::startGame);
        }
    }

    private boolean allJoined() {
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
            UserManager.instance.getUser(userId).ifPresent(user -> {
                user.showUserMessage("Not allowed", "You are banned from watching this game");
                ChatManager.instance.broadcast(chatId, user.getName(), " tried to join, but is banned", MessageColor.BLUE, true, ChatMessage.MessageType.STATUS, null);
            });
            return false;
        }
        UserManager.instance.getUser(userId).ifPresent(user -> {
            GameSessionWatcher gameWatcher = new GameSessionWatcher(userId, game, false);
            final Lock w = gameWatchersLock.writeLock();
            w.lock();
            try {
                watchers.put(userId, gameWatcher);
            } finally {
                w.unlock();
            }
            gameWatcher.init();
            user.addGameWatchInfo(game.getId());
            ChatManager.instance.broadcast(chatId, user.getName(), " has started watching", MessageColor.BLUE, true, ChatMessage.MessageType.STATUS, null);
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
        UserManager.instance.getUser(userId).ifPresent(user -> {
            ChatManager.instance.broadcast(chatId, user.getName(), " has stopped watching", MessageColor.BLUE, true, ChatMessage.MessageType.STATUS, null);
        });
    }

    public void quitMatch(UUID userId) {
        UUID playerId = getPlayerId(userId);
        if (playerId != null) {
            if (allJoined()) {
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
                    checkStart(); // => So the game starts and gets an result or multiplayer game starts with active players
                }
            }
        }
    }

    public void sendPlayerAction(PlayerAction playerAction, UUID userId, Object data) {
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
                            requestsOpen = requestPermissionToRollback(userId, turnsToRollback);
                            if (requestsOpen == 0) {
                                game.rollbackTurns(turnsToRollback);
                                turnsToRollback = -1;
                                requestsOpen = -1;
                            } else {
                                userReqestingRollback = userId;
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
                if (userReqestingRollback != null && requestsOpen > 0 && !userId.equals(userReqestingRollback)) {
                    requestsOpen--;
                    if (requestsOpen == 0) {
                        game.rollbackTurns(turnsToRollback);
                        turnsToRollback = -1;
                        userReqestingRollback = null;
                        requestsOpen = -1;
                    }
                }
                break;
            case DENY_PERMISSON_TO_ROLLBACK_TURN: // one player has denied - so cancel the request
            {
                UUID playerId = getPlayerId(userId);
                if (playerId != null) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        if (userReqestingRollback != null && requestsOpen > 0 && !userId.equals(userReqestingRollback)) {
                            turnsToRollback = -1;
                            userReqestingRollback = null;
                            requestsOpen = -1;
                            game.informPlayers("Rollback request denied by " + player.getLogName());
                        }
                    }
                }
            }
            break;
            case CONCEDE:
                game.concede(getPlayerId(userId));
                break;
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
            case REVOKE_PERMISSIONS_TO_SEE_HAND_CARDS:
                UUID playerId = getPlayerId(userId);
                if (playerId != null) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.revokePermissionToSeeHandCards();
                    }
                }
                break;
            case REQUEST_PERMISSION_TO_SEE_HAND_CARDS:
                if (data instanceof UUID) {
                    requestPermissionToSeeHandCards(userId, (UUID) data);
                }
                break;
            case VIEW_LIMITED_DECK:
                viewLimitedDeck(getPlayerId(userId), userId);
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
                                UserManager.instance.getUser(userIdRequester).ifPresent(requester -> {
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
                UserManager.instance.getUser(userIdRequester).ifPresent(requester -> {
                    requester.showUserMessage("Request to show hand cards", "You can see already the hand cards of player " + grantingPlayer.getName() + '!');
                });

            }

        }
    }

    private void viewLimitedDeck(UUID userIdRequester, UUID origId) {
        Player viewLimitedDeckPlayer = game.getPlayer(userIdRequester);
        if (viewLimitedDeckPlayer != null) {
            if (viewLimitedDeckPlayer.isHuman()) {
                for (MatchPlayer p : TableManager.instance.getTable(tableId).getMatch().getPlayers()) {
                    if (p.getPlayer().getId().equals(userIdRequester)) {
                        Optional<User> u = UserManager.instance.getUser(origId);
                        if (u.isPresent() && p.getDeck() != null) {
                            u.get().ccViewLimitedDeck(p.getDeck(), tableId, requestsOpen, true);
                        }
                    }
                }
            }
        }
    }

    public void cheat(UUID userId, UUID playerId, DeckCardLists deckList) {
        try {
            Deck deck = Deck.load(deckList, false, false);
            game.loadCards(deck.getCards(), playerId);
            for (Card card : deck.getCards()) {
                card.putOntoBattlefield(game, Zone.OUTSIDE, null, playerId);
            }
        } catch (GameException ex) {
            logger.warn(ex.getMessage());
        }
        addCardsForTesting(game, playerId);
        updateGame();
    }

    public boolean cheat(UUID userId, UUID playerId, String cardName) {
        CardInfo cardInfo = CardRepository.instance.findCard(cardName);
        Card card = cardInfo != null ? cardInfo.getCard() : null;
        if (card != null) {
            Set<Card> cards = new HashSet<>();
            cards.add(card);
            game.loadCards(cards, playerId);
            card.moveToZone(Zone.HAND, null, game, false);
            return true;
        } else {
            return false;
        }
    }

    public void idleTimeout(UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            String sb = player.getLogName()
                    + " has timed out (player had priority and was not active for "
                    + ConfigSettings.instance.getMaxSecondsIdle() + " seconds ) - Auto concede.";
            ChatManager.instance.broadcast(chatId, "", sb, MessageColor.BLACK, true, MessageType.STATUS, null);
            game.idleTimeout(playerId);
        }
    }

    public void endGame(final String message) throws MageException {
        for (final GameSessionPlayer gameSession : getGameSessions()) {
            gameSession.gameOver(message);
            gameSession.removeGame();
        }
        for (final GameSessionWatcher gameWatcher : getGameSessionWatchers()) {
            gameWatcher.gameOver(message);
        }
        TableManager.instance.endGame(tableId);
    }

    public UUID getSessionId() {
        return this.gameSessionId;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void sendPlayerUUID(UUID userId, final UUID data) {
        sendMessage(userId, playerId -> getGameSession(playerId).sendPlayerUUID(data));
    }

    public void sendPlayerString(UUID userId, final String data) {
        sendMessage(userId, playerId -> getGameSession(playerId).sendPlayerString(data));
    }

    public void sendPlayerManaType(UUID userId, final UUID manaTypePlayerId, final ManaType data) {
        sendMessage(userId, playerId -> getGameSession(playerId).sendPlayerManaType(data, manaTypePlayerId));
    }

    public void sendPlayerBoolean(UUID userId, final Boolean data) {
        sendMessage(userId, playerId -> getGameSession(playerId).sendPlayerBoolean(data));

    }

    public void sendPlayerInteger(UUID userId, final Integer data) {
        sendMessage(userId, playerId -> getGameSession(playerId).sendPlayerInteger(data));

    }

    private synchronized void updateGame() {
        if (!timers.isEmpty()) {
            for (Player player : game.getState().getPlayers().values()) {
                PriorityTimer timer = timers.get(player.getId());
                if (timer != null) {
                    player.setPriorityTimeLeft(timer.getCount());
                }
            }
        }
        for (final GameSessionPlayer gameSession : getGameSessions()) {
            gameSession.update();
        }
        for (final GameSessionWatcher gameWatcher : getGameSessionWatchers()) {
            gameWatcher.update();
        }
    }

    private synchronized void endGameInfo() {
        Table table = TableManager.instance.getTable(tableId);
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

    private synchronized void chooseAbility(UUID playerId, final String objectName, final List<? extends Ability> choices) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).chooseAbility(new AbilityPickerView(objectName, choices)));
    }

    private synchronized void choosePile(UUID playerId, final String message, final List<? extends Card> pile1, final List<? extends Card> pile2) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).choosePile(message, new CardsView(pile1), new CardsView(pile2)));
    }

    private synchronized void chooseMode(UUID playerId, final Map<UUID, String> modes) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).chooseAbility(new AbilityPickerView(modes)));
    }

    private synchronized void chooseChoice(UUID playerId, final Choice choice) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).chooseChoice(choice));
    }

    private synchronized void target(UUID playerId, final String question, final Cards cards, final List<Permanent> perms, final Set<UUID> targets, final boolean required, final Map<String, Serializable> options) throws MageException {
        perform(playerId, playerId1 -> {
            if (cards != null) {
                // Zone targetZone = (Zone) options.get("targetZone");
                // Are there really situations where a player selects from a list of face down cards?
                // So always show face up for selection
                // boolean showFaceDown = targetZone != null && targetZone.equals(Zone.PICK);
                boolean showFaceDown = true;
                getGameSession(playerId1).target(question, new CardsView(game, cards.getCards(game), showFaceDown, true), targets, required, options);
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

    private void informOthers(UUID playerId) throws MageException {
        StringBuilder message = new StringBuilder();
        if (game.getStep() != null) {
            message.append(game.getStep().getType().toString()).append(" - ");
        }
        message.append("Waiting for ").append(game.getPlayer(playerId).getLogName());
        for (final Entry<UUID, GameSessionPlayer> entry : getGameSessionsMap().entrySet()) {
            if (!entry.getKey().equals(playerId)) {
                entry.getValue().inform(message.toString());
            }
        }
        for (final GameSessionWatcher watcher : getGameSessionWatchers()) {
            watcher.inform(message.toString());
        }
    }

    private void informOthers(List<UUID> players) throws MageException {
        // first player is always original controller
        Player controller = null;
        if (players != null && !players.isEmpty()) {
            controller = game.getPlayer(players.get(0));
        }
        if (controller == null || game.getStep() == null || game.getStep().getType() == null) {
            return;
        }
        final String message = new StringBuilder(game.getStep().getType().toString()).append(" - Waiting for ").append(controller.getName()).toString();
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

    private synchronized void informPersonal(UUID playerId, final String message) throws MageException {
        perform(playerId, playerId1 -> getGameSession(playerId1).informPersonal(message));
    }

    private void error(String message, Exception ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(ex.toString());
        sb.append("\nServer version: ").append(Main.getVersion().toString());
        sb.append('\n');
        for (StackTraceElement e : ex.getStackTrace()) {
            sb.append(e.toString()).append('\n');
        }
        for (final Entry<UUID, GameSessionPlayer> entry : getGameSessionsMap().entrySet()) {
            entry.getValue().gameError(sb.toString());
        }
    }

    public GameView getGameView(UUID playerId) {
        return getGameSession(playerId).getGameView();
    }

    @Override
    public void gameResult(String result) {
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
     * Adds cards in player's hands that are specified in config/init.txt.
     */
    private void addCardsForTesting(Game game, UUID playerId) {
        SystemUtil.addCardsForTesting(game, null, game.getPlayer(playerId));
    }

    /**
     * Performs a request to a player
     *
     * @param playerId
     * @param command
     * @throws MageException
     */
    private void perform(UUID playerId, Command command) throws MageException {
        perform(playerId, command, true);
    }

    private void perform(UUID playerId, Command command, boolean informOthers) throws MageException {
        if (game.getPlayer(playerId).isGameUnderControl()) { // is the player controlling it's own turn
            if (gameSessions.containsKey(playerId)) {
                setupTimeout(playerId);
                command.execute(playerId);
            }
            if (informOthers) {
                informOthers(playerId);
            }
        } else {
            List<UUID> players = Splitter.split(game, playerId);
            for (UUID uuid : players) {
                if (gameSessions.containsKey(uuid)) {
                    setupTimeout(uuid);
                    command.execute(uuid);
                }
            }
            if (informOthers) {
                informOthers(players);
            }
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
                    cancelTimeout();
                    command.execute(playerId);
                }
            } else {
                // otherwise execute the action under other player's control
                for (UUID controlled : player.getPlayersUnderYourControl()) {
                    if (gameSessions.containsKey(controlled) && game.getPriorityPlayerId().equals(controlled)) {
                        cancelTimeout();
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

    private void setupTimeout(final UUID playerId) {
        if (!useTimeout) {
            return;
        }
        cancelTimeout();
        futureTimeout = timeoutIdleExecutor.schedule(
                () -> idleTimeout(playerId),
                Main.isTestMode() ? 3600 : ConfigSettings.instance.getMaxSecondsIdle(),
                TimeUnit.SECONDS
        );
    }

    private void cancelTimeout() {
        logger.debug("cancelTimeout");
        if (futureTimeout != null) {
            synchronized (futureTimeout) {
                futureTimeout.cancel(false);
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

    private GameSessionPlayer getGameSession(UUID playerId) {
        if (!timers.isEmpty()) {
            Player player = game.getState().getPlayer(playerId);
            PriorityTimer timer = timers.get(playerId);
            if (timer != null) {
                //logger.warn("Timer Player " + player.getName()+ " " + player.getPriorityTimeLeft() + " Timer: " + timer.getCount());
                player.setPriorityTimeLeft(timer.getCount());
            }
        }
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
        Optional<User> user = UserManager.instance.getUser(userId);
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
        sb.append("<br>getTurnId: ");
        sb.append(state.getTurnId());
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
        if (futureTimeout != null) {
            sb.append("Cancelled?=");
            sb.append(futureTimeout.isCancelled());
            sb.append(",,,Done?=");
            sb.append(futureTimeout.isDone());
            sb.append(",,,GetDelay?=");
            sb.append((int) futureTimeout.getDelay(TimeUnit.SECONDS));
        } else {
            sb.append("Not using future Timeout!");
        }
        sb.append("</font>");
        return sb.toString();
    }

    private String getName(Player player) {
        return player != null ? player.getName() : "-";
    }

    public String attemptToFixGame() {
        // try to fix disconnects

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
        boolean fixedAlready = false;

        Player activePlayer = game.getPlayer(state.getActivePlayerId());

        // fix active
        sb.append("<br>Checking active player: " + getName(activePlayer));
        if (activePlayer != null && activePlayer.hasLeft()) {
            sb.append("<br>Found disconnected player! Concede...");
            activePlayer.concede(game);

            Phase currentPhase = game.getPhase();
            if (currentPhase != null) {
                currentPhase.getStep().skipStep(game, state.getActivePlayerId());
                sb.append("<br>Forcibly passing the phase!");
                fixedAlready = true;
            } else {
                sb.append("<br>Current phase null");
            }
            sb.append("<br>Active player has left");
        }

        // fix lost choosing dialog
        sb.append("<br>Checking choosing player: " + getName(game.getPlayer(state.getChoosingPlayerId())));
        if (state.getChoosingPlayerId() != null) {
            if (game.getPlayer(state.getChoosingPlayerId()).hasLeft()) {
                sb.append("<br>Found disconnected player! Concede...");
                Player p = game.getPlayer(state.getChoosingPlayerId());
                if (p != null) {
                    p.concede(game);
                }
                Phase currentPhase = game.getPhase();
                if (currentPhase != null && !fixedAlready) {
                    currentPhase.getStep().endStep(game, state.getActivePlayerId());
                    fixedAlready = true;
                    sb.append("<br>Forcibly passing the phase!");
                } else if (currentPhase == null) {
                    sb.append("<br>Current phase null");
                }
                sb.append("<br>Choosing player has left");
            }
        }

        // fix lost priority
        sb.append("<br>Checking priority player: " + getName(game.getPlayer(state.getPriorityPlayerId())));
        if (state.getPriorityPlayerId() != null) {
            if (game.getPlayer(state.getPriorityPlayerId()).hasLeft()) {
                sb.append("<br>Found disconnected player! Concede...");
                Player p = game.getPlayer(state.getPriorityPlayerId());
                if (p != null) {
                    p.concede(game);
                }
                Phase currentPhase = game.getPhase();
                if (currentPhase != null && !fixedAlready) {
                    currentPhase.getStep().skipStep(game, state.getActivePlayerId());
                    fixedAlready = true;
                    sb.append("<br>Forcibly passing the phase!");
                }
            }
            sb.append(game.getPlayer(state.getPriorityPlayerId()).getName());
            sb.append("</font>");
        }

        // fix timeout
        sb.append("<br>Checking Future Timeout: ");
        if (futureTimeout != null) {
            sb.append("Cancelled?=");
            sb.append(futureTimeout.isCancelled());
            sb.append(",,,Done?=");
            sb.append(futureTimeout.isDone());
            sb.append(",,,GetDelay?=");
            sb.append((int) futureTimeout.getDelay(TimeUnit.SECONDS));
            if ((int) futureTimeout.getDelay(TimeUnit.SECONDS) < 25) {
                PassAbility pass = new PassAbility();
                game.endTurn(pass);
                sb.append("<br>Forcibly passing the turn!");
            }
        } else {
            sb.append("Not using future Timeout!");
        }
        sb.append("</font>");
        return sb.toString();
    }
}
