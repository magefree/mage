package mage.server.game;

import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.Table;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.players.Player;
import mage.server.User;
import mage.server.UserManager;
import mage.server.util.ThreadExecutor;
import mage.view.*;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GameSessionPlayer extends GameSessionWatcher {

    private static final Logger logger = Logger.getLogger(GameSessionPlayer.class);

    private final UUID playerId;

    private static final ExecutorService callExecutor = ThreadExecutor.instance.getCallExecutor();

    public GameSessionPlayer(Game game, UUID userId, UUID playerId) {
        super(userId, game, true);
        this.playerId = playerId;
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
    }

    public void ask(final String question, final Map<String, Serializable> options) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_ASK, game.getId(), new GameClientMessage(getGameView(), question, options)))
            );
        }
    }

    public void target(final String question, final CardsView cardView, final Set<UUID> targets, final boolean required, final Map<String, Serializable> options) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user -> {
                user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_TARGET, game.getId(), new GameClientMessage(getGameView(), question, cardView, targets, required, options)));
            });

        }
    }

    public void select(final String message, final Map<String, Serializable> options) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_SELECT, game.getId(), new GameClientMessage(getGameView(), message, options))));
        }
    }

    public void chooseAbility(final AbilityPickerView abilities) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user
                    -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_CHOOSE_ABILITY, game.getId(), abilities)));
        }

    }

    public void choosePile(final String message, final CardsView pile1, final CardsView pile2) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user
                    -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_CHOOSE_PILE, game.getId(), new GameClientMessage(message, pile1, pile2))));
        }

    }

    public void chooseChoice(final Choice choice) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user
                    -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_CHOOSE_CHOICE, game.getId(), new GameClientMessage(choice))));
        }

    }

    public void playMana(final String message, final Map<String, Serializable> options) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user
                    -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_PLAY_MANA, game.getId(), new GameClientMessage(getGameView(), message, options))));
        }
    }

    public void playXMana(final String message) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user
                    -> user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_PLAY_XMANA, game.getId(), new GameClientMessage(getGameView(), message))));

        }
    }

    public void getAmount(final String message, final int min, final int max) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user -> {
                user.fireCallback(new ClientCallback(ClientCallbackMethod.GAME_GET_AMOUNT, game.getId(), new GameClientMessage(message, min, max)));
            });
        }
    }

    public void endGameInfo(Table table) {
        if (!killed) {
            UserManager.instance.getUser(userId).ifPresent(user -> user.fireCallback(new ClientCallback(ClientCallbackMethod.END_GAME_INFO, game.getId(), getGameEndView(playerId, table))));

        }
    }

    public void requestPermissionToRollbackTurn(UUID requestingUserId, int numberTurns) {
        if (!killed) {
            Optional<User> requestingUser = UserManager.instance.getUser(requestingUserId);
            Optional<User> requestedUser = UserManager.instance.getUser(userId);
            if (requestedUser.isPresent() && requestingUser.isPresent()) {
                String message;
                switch (numberTurns) {
                    case 0:
                        message = "Allow rollback to the start of the current turn?";
                        break;
                    case 1:
                        message = "Allow rollback to the start of the previous turn?";
                        break;
                    default:
                        message = "Allow to rollback " + numberTurns + " turns?";
                }
                UserRequestMessage userRequestMessage = new UserRequestMessage(
                        "Request by " + requestedUser.get().getName(), message);
                userRequestMessage.setRelatedUser(requestingUserId, requestingUser.get().getName());
                userRequestMessage.setGameId(game.getId());
                userRequestMessage.setButton1("Accept", PlayerAction.ADD_PERMISSION_TO_ROLLBACK_TURN);
                userRequestMessage.setButton2("Deny", PlayerAction.DENY_PERMISSON_TO_ROLLBACK_TURN);
                requestedUser.get().fireCallback(new ClientCallback(ClientCallbackMethod.USER_REQUEST_DIALOG, game.getId(), userRequestMessage));
            }
        }
    }

    public void requestPermissionToSeeHandCards(UUID watcherId) {
        if (!killed) {
            Optional<User> watcher = UserManager.instance.getUser(watcherId);
            Optional<User> user = UserManager.instance.getUser(userId);
            if (user.isPresent() && watcher.isPresent()) {
                UserRequestMessage userRequestMessage = new UserRequestMessage(
                        "User request",
                        "Allow user <b>" + watcher.get().getName() + "</b> for this match to see your hand cards?<br>"
                                + "(You can revoke this every time using related popup menu item of your battlefield.)");
                userRequestMessage.setRelatedUser(watcherId, watcher.get().getName());
                userRequestMessage.setGameId(game.getId());
                userRequestMessage.setButton1("Accept", PlayerAction.ADD_PERMISSION_TO_SEE_HAND_CARDS);
                userRequestMessage.setButton2("Reject", null);
                user.get().fireCallback(new ClientCallback(ClientCallbackMethod.USER_REQUEST_DIALOG, game.getId(), userRequestMessage));
            }
        }
    }

    public void sendPlayerUUID(UUID data) {
        game.getPlayer(playerId).setResponseUUID(data);
    }

    public void sendPlayerString(String data) {
        game.getPlayer(playerId).setResponseString(data);
    }

    public void sendPlayerManaType(ManaType manaType, UUID manaTypePlayerId) {
        game.getPlayer(playerId).setResponseManaType(manaTypePlayerId, manaType);
    }

    public void sendPlayerBoolean(Boolean data) {
        game.getPlayer(playerId).setResponseBoolean(data);
    }

    public void sendPlayerInteger(Integer data) {
        game.getPlayer(playerId).setResponseInteger(data);
    }

    @Override
    public GameView getGameView() {
        Player player = game.getPlayer(playerId);
        GameView gameView = new GameView(game.getState(), game, playerId, null);
        gameView.setHand(new CardsView(game, player.getHand().getCards(game)));
        if (gameView.getPriorityPlayerName().equals(player.getName())) {
            gameView.setCanPlayObjects(player.getPlayableObjects(game, Zone.ALL));
        }

        processControlledPlayers(player, gameView);
        processWatchedHands(userId, gameView);
        //TODO: should player who controls another player's turn be able to look at all these cards?

        List<LookedAtView> list = new ArrayList<>();
        for (Entry<String, Cards> entry : game.getState().getLookedAt(playerId).entrySet()) {
            list.add(new LookedAtView(entry.getKey(), entry.getValue(), game));
        }
        gameView.setLookedAt(list);

        return gameView;
    }

    private void processControlledPlayers(Player player, GameView gameView) {
        if (!player.getPlayersUnderYourControl().isEmpty()) {
            Map<String, SimpleCardsView> handCards = new HashMap<>();
            for (UUID controlledPlayerId : player.getPlayersUnderYourControl()) {
                Player opponent = game.getPlayer(controlledPlayerId);
                handCards.put(opponent.getName(), new SimpleCardsView(opponent.getHand().getCards(game), true));
            }
            gameView.setOpponentHands(handCards);
        }
    }

    public void removeGame() {
        UserManager.instance.getUser(userId).ifPresent(user -> user.removeGame(playerId));

    }

    public UUID getGameId() {
        return game.getId();
    }

    public void quitGame() {
        if (game != null) {
            final Player player = game.getPlayer(playerId);
            if (player != null && player.isInGame()) {
                callExecutor.execute(
                        () -> {
                            try {
                                if (game.getStartTime() == null) {
                                    // gameController is still waiting to start the game
                                    player.leave();
                                } else {
                                    // game was already started
                                    player.quit(game);
                                }

                            } catch (Exception ex) {
                                if (ex != null) {
                                    // It seems this can happen if two threads try to end the game at the exact same time (one wins and one ends here)
                                    logger.fatal("Game session game quit exception " + (ex.getMessage() == null ? "null" : ex.getMessage()));
                                    logger.debug("- gameId:" + game.getId() + "  playerId: " + playerId);
                                    if (ex.getCause() != null) {
                                        logger.debug("- Cause: " + (ex.getCause().getMessage() == null ? "null" : ex.getCause().getMessage()), ex);
                                    } else {
                                        logger.debug("- ex: " + ex.toString(), ex);
                                    }
                                } else {
                                    logger.fatal("Game session game quit exception - null  gameId:" + game.getId() + "  playerId: " + playerId);
                                }
                            }
                        }
                );

            }
        } else {
            logger.error("game object missing   playerId: " + (playerId == null ? "[null]" : playerId));
        }
    }

}
