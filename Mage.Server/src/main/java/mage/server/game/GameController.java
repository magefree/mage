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

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;
import mage.MageException;
import mage.abilities.Ability;
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
import mage.game.Game;
import mage.game.GameException;
import mage.game.Table;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.permanent.Permanent;
import mage.interfaces.Action;
import mage.players.Player;
import mage.server.ChatManager;
import mage.server.Main;
import mage.server.TableManager;
import mage.server.User;
import mage.server.UserManager;
import mage.server.util.ConfigSettings;
import mage.server.util.Splitter;
import mage.server.util.SystemUtil;
import mage.server.util.ThreadExecutor;
import mage.utils.timer.PriorityTimer;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.ChatMessage;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import mage.view.GameView;
import mage.view.PermanentView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameController implements GameCallback {

    private static final ExecutorService gameExecutor = ThreadExecutor.getInstance().getGameExecutor();
    private static final Logger logger = Logger.getLogger(GameController.class);

    protected ScheduledExecutorService joinWaitingExecutor = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> futureTimeout;
    protected static ScheduledExecutorService timeoutIdleExecutor = ThreadExecutor.getInstance().getTimeoutIdleExecutor();

    private ConcurrentHashMap<UUID, GameSessionPlayer> gameSessions = new ConcurrentHashMap<>();
    private ConcurrentHashMap<UUID, GameSessionWatcher> watchers = new ConcurrentHashMap<>();
    private ConcurrentHashMap<UUID, PriorityTimer> timers = new ConcurrentHashMap<>();
    
    private ConcurrentHashMap<UUID, UUID> userPlayerMap;
    private UUID gameSessionId;
    private Game game;
    private UUID chatId;
    private UUID tableId;
    private UUID choosingPlayerId;
    private Future<?> gameFuture;
    private boolean useTimeout = true;

    public GameController(Game game, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId, UUID choosingPlayerId) {
        gameSessionId = UUID.randomUUID();
        this.userPlayerMap = userPlayerMap;
        chatId = ChatManager.getInstance().createChatSession("Game " + game.getId());
        this.game = game;
        this.game.setSaveGame(ConfigSettings.getInstance().isSaveGameActivated());
        this.tableId = tableId;
        this.choosingPlayerId = choosingPlayerId;
        for (Player player: game.getPlayers().values()) {
            if (!player.isHuman()) {
                useTimeout = false; // no timeout for AI players because of beeing idle
                break;
            }
        }
        init();

    }

    public void cleanUp() {
        cancelTimeout();
        for (GameSessionPlayer gameSessionPlayer: gameSessions.values()) {
            gameSessionPlayer.CleanUp();
        }
        ChatManager.getInstance().destroyChatSession(chatId);
        for(PriorityTimer priorityTimer: timers.values()) {
            priorityTimer.cancel();
        }
    }

    private void init() {
        game.addTableEventListener(
            new Listener<TableEvent> () {
                @Override
                public void event(TableEvent event) {
                    try {
                        PriorityTimer timer;
                        UUID playerId;
                        switch (event.getEventType()) {
                            case UPDATE:
                                updateGame();
                                break;
                            case INFO:
                                ChatManager.getInstance().broadcast(chatId, "", event.getMessage(), MessageColor.BLACK, true, ChatMessage.MessageType.GAME);
                                logger.trace(game.getId() + " " + event.getMessage());
                                break;
                            case STATUS:
                                ChatManager.getInstance().broadcast(chatId, "", event.getMessage(), MessageColor.ORANGE, event.getWithTime(), ChatMessage.MessageType.GAME);
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
            }
        );
        game.addPlayerQueryEventListener(
            new Listener<PlayerQueryEvent> () {
                @Override
                public void event(PlayerQueryEvent event) {
                    logger.trace(new StringBuilder(event.getPlayerId().toString()).append("--").append(event.getQueryType()).append("--").append(event.getMessage()).toString());
                    try {
                        switch (event.getQueryType()) {
                            case ASK:
                                ask(event.getPlayerId(), event.getMessage());
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
                                playMana(event.getPlayerId(), event.getMessage());
                                break;
                            case PLAY_X_MANA:
                                playXMana(event.getPlayerId(), event.getMessage());
                                break;
                            case CHOOSE_ABILITY:
                                String objectName = null;
                                if(event.getChoices() != null && event.getChoices().size() > 0) {
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
            }
        );
        joinWaitingExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    sendInfoAboutPlayersNotJoinedYet();
                } catch(Exception ex) {
                    logger.fatal("Send info about player not joined yet:", ex);
                }
            }
        }, 15, 15, TimeUnit.SECONDS);
        checkStart();
    }

    /**
     * We create a timer that will run every 250 ms individually for a player decreasing his internal game counter.
     * Later on this counter is used to get time left to play the whole match.
     *
     * What we also do here is passing Action to PriorityTimer that is the action that will be executed once game timer is over.
     *
     * @param playerId
     * @param count
     * @return
     */
    private PriorityTimer createPlayerTimer(UUID playerId, int count) {
        final UUID initPlayerId = playerId;
        long delayMs = 250L; // run each 250 ms

        Action executeOnNoTimeLeft = new Action() {
            @Override
            public void execute() throws MageException {
                game.timerTimeout(initPlayerId);
                logger.debug("Player has no time left to end the match: " + initPlayerId + ". Conceding.");
            }
        };

        PriorityTimer timer = new PriorityTimer(count, delayMs, executeOnNoTimeLeft);
        timers.put(playerId, timer);
        timer.init(game.getId());
        return timer;
    }

    private UUID getPlayerId(UUID userId) {
        return userPlayerMap.get(userId);
    }

    public void join(UUID userId) {
        UUID playerId = userPlayerMap.get(userId);
        User user = UserManager.getInstance().getUser(userId);
        if (userId == null || playerId == null) {
            logger.fatal("Join game failed!");
            logger.fatal("- gameId: " +  game.getId());
            logger.fatal("- userId: " +  userId);
            return;
        }
        Player player = game.getPlayer(playerId);
        if (player == null) {
            logger.fatal("Player not found - playerId: " +playerId);
            return;
        }
        GameSessionPlayer gameSession = gameSessions.get(playerId);
        String joinType;
        if (gameSession == null) {
            gameSession = new GameSessionPlayer(game, userId, playerId);
            gameSessions.put(playerId, gameSession);
            joinType = "joined";
        } else {
            joinType = "rejoined";
        }
        user.addGame(playerId, gameSession);
        logger.debug("Player " + player.getLogName()+ " " + playerId + " has " + joinType + " gameId: " + game.getId());
        ChatManager.getInstance().broadcast(chatId, "", game.getPlayer(playerId).getLogName() + " has " + joinType + " the game", MessageColor.ORANGE, true, MessageType.GAME);
        checkStart();
    }

    private synchronized void startGame() {
        if (gameFuture == null) {
            for (final Entry<UUID, GameSessionPlayer> entry: gameSessions.entrySet()) {
                entry.getValue().init();
            }
            GameWorker worker = new GameWorker(game, choosingPlayerId, this);
            gameFuture = gameExecutor.submit(worker);
        }
    }

    private void sendInfoAboutPlayersNotJoinedYet() {
        for (Player player: game.getPlayers().values()) {
            if (!player.hasLeft() && player.isHuman()) {                
                User user = getUserByPlayerId(player.getId());                
                if (user != null) {
                    if (!user.isConnected()) {
                        if (gameSessions.get(player.getId()) == null) {
                                // join the game because player has not joined are was removed because of disconnect
                                user.removeConstructing(player.getId());
                                GameManager.getInstance().joinGame(game.getId(), user.getId());
                                logger.debug("Player " + player.getLogName() + " (disconnected) has joined gameId: " +game.getId());
                        }
                        ChatManager.getInstance().broadcast(chatId, player.getName(), user.getPingInfo() + " is pending to join the game", MessageColor.BLUE, true, ChatMessage.MessageType.STATUS);
                        if (user.getSecondsDisconnected() > 240) {
                            // Cancel player join possibility lately after 4 minutes
                            logger.debug("Player " + player.getLogName() + " - canceled game (after 240 seconds) gameId: " +game.getId());
                            player.leave();
                        }

                    }
                } else {
                    if (!player.hasLeft()) {
                        logger.debug("Player " + player.getLogName() + " canceled game (no user) gameId: " + game.getId());
                        player.leave();
                    }
                }
            }
        }
        checkStart();
    }

    private User getUserByPlayerId(UUID playerId) {
        for(Map.Entry<UUID,UUID> entry: userPlayerMap.entrySet()) {
            if (entry.getValue().equals(playerId)) {
                return UserManager.getInstance().getUser(entry.getKey());
            }
        }
        return null;
    }

    private void checkStart() {
        if (allJoined()) {
            joinWaitingExecutor.shutdownNow();
            ThreadExecutor.getInstance().getCallExecutor().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        startGame();
                    }
            });
        }
    }

    private boolean allJoined() {
        for (Player player: game.getPlayers().values()) {
            if (!player.hasLeft()) {
                User user = getUserByPlayerId(player.getId());
                if (user != null) {
                    if (!user.isConnected()) {
                        return false;
                    }
                }
                if (player.isHuman() && gameSessions.get(player.getId()) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void watch(UUID userId) {
        if (userPlayerMap.get(userId) != null) {
            // You can't watch a game if you already a player in it
            return;
        }
        if (watchers.get(userId) != null) {
            // You can't watch a game if you already watch it
            return;
        }
        User user = UserManager.getInstance().getUser(userId);
        if (user != null) {
            GameSessionWatcher gameWatcher = new GameSessionWatcher(userId, game, false);
            watchers.put(userId, gameWatcher);
            gameWatcher.init();
            user.addGameWatchInfo(game.getId());
            ChatManager.getInstance().broadcast(chatId, user.getName(), " has started watching", MessageColor.BLUE, true, ChatMessage.MessageType.STATUS);
        }
    }

    public void stopWatching(UUID userId) {
        watchers.remove(userId);
        User user = UserManager.getInstance().getUser(userId);
        if (user != null) {
            ChatManager.getInstance().broadcast(chatId, user.getName(), " has stopped watching", MessageColor.BLUE, true, ChatMessage.MessageType.STATUS);
        }
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
        switch(playerAction) {
            case UNDO:
                game.undo(getPlayerId(userId));
                break;
            case CONCEDE:
                game.concede(getPlayerId(userId));
                break;
            case MANA_AUTO_PAYMENT_OFF:
                game.setManaPoolMode(getPlayerId(userId), false);
                break;
            case MANA_AUTO_PAYMENT_ON:
                game.setManaPoolMode(getPlayerId(userId), true);
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
            default:        
                game.sendPlayerAction(playerAction, getPlayerId(userId));
        }
    }

    private void requestPermissionToSeeHandCards(UUID userIdRequester, UUID userIdGranter) {
        Player grantingPlayer = game.getPlayer(userIdGranter);
        if (grantingPlayer != null) {
            if (!grantingPlayer.getUsersAllowedToSeeHandCards().contains(userIdRequester)) {
                if (grantingPlayer.isHuman()) {
                    GameSessionPlayer gameSession = gameSessions.get(userIdGranter);
                    if (gameSession != null) {
                        UUID requestingPlayer = getPlayerId(userIdRequester);
                        if (requestingPlayer == null || !requestingPlayer.equals(grantingPlayer.getId())) { // don't allow request for your own cards
                            if (grantingPlayer.isRequestToShowHandCardsAllowed()) {
                                gameSession.requestPermissionToSeeHandCards(userIdRequester);
                            } else {
                                // player does not allow the request
                                User requester = UserManager.getInstance().getUser(userIdRequester);
                                if (requester != null) {
                                    requester.showUserMessage("Request to show hand cards", "Player " + grantingPlayer.getName() + " does not allow to request to show hand cards!");
                                }
                            }
                        }
                    }
                } else {
                    // Non Human players always allow to see the hand cards
                    grantingPlayer.addPermissionToShowHandCards(userIdRequester);
                }
            } else {
                // user can already see the cards
                User requester = UserManager.getInstance().getUser(userIdRequester);
                if (requester != null) {
                    requester.showUserMessage("Request to show hand cards", "You can see already the hand cards of player " + grantingPlayer.getName() + "!");
                }
            }

        }

    }

    public void cheat(UUID userId, UUID playerId, DeckCardLists deckList) {
        Deck deck;
        try {
            deck = Deck.load(deckList, false, false);
            game.loadCards(deck.getCards(), playerId);
            for (Card card: deck.getCards()) {
                card.putOntoBattlefield(game, Zone.OUTSIDE, null, playerId);
            }
        } catch (GameException ex) {
            logger.warn(ex.getMessage());
        }
        addCardsForTesting(game);
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
            String sb = player.getLogName() +
                    " has timed out (player had priority and was not active for " +
                    ConfigSettings.getInstance().getMaxSecondsIdle() + " seconds ) - Auto concede.";
            ChatManager.getInstance().broadcast(chatId, "", sb, MessageColor.BLACK, true, MessageType.STATUS);
            game.idleTimeout(playerId);
        }
    }

    public void endGame(final String message) throws MageException {
        for (final GameSessionPlayer gameSession: gameSessions.values()) {
            gameSession.gameOver(message);
            gameSession.removeGame();
        }
        for (final GameSessionWatcher gameWatcher: watchers.values()) {
            gameWatcher.gameOver(message);
        }
        TableManager.getInstance().endGame(tableId);
    }

    public UUID getSessionId() {
        return this.gameSessionId;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void sendPlayerUUID(UUID userId, final UUID data) {
        sendMessage(userId, new Command() {
            @Override
            public void execute(UUID playerId) {                
                getGameSession(playerId).sendPlayerUUID(data);
            }
        });
    }

    public void sendPlayerString(UUID userId, final String data) {
        sendMessage(userId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).sendPlayerString(data);
            }
        });
    }

    public void sendPlayerManaType(UUID userId, final UUID manaTypePlayerId, final ManaType data) {
        sendMessage(userId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).sendPlayerManaType(data, manaTypePlayerId);
            }
        });
    }

    public void sendPlayerBoolean(UUID userId, final Boolean data) {
        sendMessage(userId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).sendPlayerBoolean(data);
            }
        });

    }

    public void sendPlayerInteger(UUID userId, final Integer data) {
        sendMessage(userId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).sendPlayerInteger(data);
            }
        });

    }

    private synchronized void updateGame() {
        if (!timers.isEmpty()) {
            for (Player player: game.getState().getPlayers().values()) {
                PriorityTimer timer = timers.get(player.getId());
                if (timer != null) {
                    player.setPriorityTimeLeft(timer.getCount());
                }
            }
        }
        for (final GameSessionPlayer gameSession: gameSessions.values()) {
            gameSession.update();
        }
        for (final GameSessionWatcher gameWatcher: watchers.values()) {
            gameWatcher.update();
        }
    }

    private synchronized void endGameInfo() {
        Table table = TableManager.getInstance().getTable(tableId);
        if (table != null) {
            if (table.getMatch() != null) {
                for (final GameSessionPlayer gameSession: gameSessions.values()) {
                    gameSession.endGameInfo(table);
                }
            }
        }
        // TODO: inform watchers about game end and who won
    }

    private synchronized void ask(UUID playerId, final String question) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {                
                getGameSession(playerId).ask(question);
            }
        });

    }

    private synchronized void chooseAbility(UUID playerId, final String objectName, final List<? extends Ability> choices) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).chooseAbility(new AbilityPickerView(objectName, choices));
            }
        });
    }

    private synchronized void choosePile(UUID playerId, final String message, final List<? extends Card> pile1, final List<? extends Card> pile2) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).choosePile(message, new CardsView(pile1), new CardsView(pile2));
            }
        });
    }

    private synchronized void chooseMode(UUID playerId, final Map<UUID, String> modes) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).chooseAbility(new AbilityPickerView(modes));
            }
        });
    }

    private synchronized void chooseChoice(UUID playerId, final Choice choice) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).chooseChoice(choice);
            }
        });
    }

    private synchronized void target(UUID playerId, final String question, final Cards cards, final List<Permanent> perms, final Set<UUID> targets, final boolean required, final Map<String, Serializable> options) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                if (cards != null) {
                    getGameSession(playerId).target(question, new CardsView(game, cards.getCards(game)), targets, required, options);
                } else if (perms != null) {
                    CardsView permsView = new CardsView();
                    for (Permanent perm: perms) {
                        permsView.put(perm.getId(), new PermanentView(perm, game.getCard(perm.getId()), playerId, game));
                    }
                    getGameSession(playerId).target(question, permsView, targets, required, options);
                } else {
                    getGameSession(playerId).target(question, new CardsView(), targets, required, options);
                }
            }
        });

    }

    private synchronized void target(UUID playerId, final String question, final Collection<? extends Ability> abilities, final boolean required, final Map<String, Serializable> options) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).target(question, new CardsView(abilities, game), null, required, options);
            }
        });
    }

    private synchronized void select(final UUID playerId, final String message, final Map<String, Serializable> options) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).select(message, options);
            }
        });
    }

    private synchronized void playMana(UUID playerId, final String message) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).playMana(message);
            }
        });
    }

    private synchronized void playXMana(UUID playerId, final String message) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).playXMana(message);
                }
        });
    }

    private synchronized void amount(UUID playerId, final String message, final int min, final int max) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).getAmount(message, min, max);
            }
        });
    }

    private void informOthers(UUID playerId) throws MageException {
        StringBuilder message = new StringBuilder();
        if (game.getStep() != null) {
            message.append(game.getStep().getType().toString()).append(" - ");
        }
        message.append("Waiting for ").append(game.getPlayer(playerId).getLogName());
        for (final Entry<UUID, GameSessionPlayer> entry: gameSessions.entrySet()) {
            if (!entry.getKey().equals(playerId)) {
                entry.getValue().inform(message.toString());
            }
        }
        for (final GameSessionWatcher watcher: watchers.values()) {
            watcher.inform(message.toString());
        }
    }

    private void informOthers(List<UUID> players) throws MageException {
        // first player is always original controller
        Player controller = null;
        if (players != null && players.size() > 0) {
            controller = game.getPlayer(players.get(0));
        }
        if (controller == null || game.getStep() == null || game.getStep().getType() == null) {
            return;
        }
        final String message = new StringBuilder(game.getStep().getType().toString()).append(" - Waiting for ").append(controller.getName()).toString();
        for (final Entry<UUID, GameSessionPlayer> entry: gameSessions.entrySet()) {
            boolean skip = false;
            for (UUID uuid : players) {
                if (entry.getKey().equals(uuid)) {
                    skip = true;
                    break;
                }
            }
            if (!skip) {
                entry.getValue().inform(message);
            }
        }
        for (final GameSessionWatcher watcher: watchers.values()) {
            watcher.inform(message);
        }
    }

    private synchronized void informPersonal(UUID playerId, final String message) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).informPersonal(message);
            }
        });
    }

    private void error(String message, Exception ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(ex.toString());
        sb.append("\nServer version: ").append(Main.getVersion().toString());
        sb.append("\n");
        for (StackTraceElement e: ex.getStackTrace()) {
            sb.append(e.toString()).append("\n");
        }
        for (final Entry<UUID, GameSessionPlayer> entry: gameSessions.entrySet()) {
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
        try {
            OutputStream file = new FileOutputStream("saved/" + game.getId().toString() + ".game");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(new GZIPOutputStream(buffer));
            try {
                output.writeObject(game);
                output.writeObject(game.getGameStates());
            }
            finally {
                output.close();
            }
            logger.debug("Saved game:" + game.getId());
            return true;
        }
        catch(IOException ex) {
            logger.fatal("Cannot save game.", ex);
        }
        return false;
    }

    /**
     * Adds cards in player's hands that are specified in config/init.txt.
     */
    private void addCardsForTesting(Game game) {
        SystemUtil.addCardsForTesting(game);
    }

    /**
     * Performas a request to a player
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

    private synchronized void setupTimeout(final UUID playerId) {
        if (!useTimeout) {
            return;
        }
        cancelTimeout();
        futureTimeout = timeoutIdleExecutor.schedule(
            new Runnable() {
                @Override
                public void run() {
                    idleTimeout(playerId);
                }
            },
            Main.isTestMode() ? 3600 :ConfigSettings.getInstance().getMaxSecondsIdle(),
            TimeUnit.SECONDS
        );
    }

    private synchronized void cancelTimeout() {
        if (futureTimeout != null) {
            futureTimeout.cancel(false);
        }
    }

    interface Command {
        void execute(UUID player);
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
        for (UUID playerId: userPlayerMap.values()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                sb.append(player.getName()).append("(Left=").append(player.hasLeft() ? "Y":"N").append(") ");
            } else {
                sb.append("player missing: ").append(playerId).append(" ");
            }
        }
        return sb.append("]").toString();
    }
}
