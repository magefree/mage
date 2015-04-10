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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.Game;
import mage.game.Table;
import mage.interfaces.callback.ClientCallback;
import mage.players.Player;
import mage.players.net.UserData;
import mage.server.User;
import mage.server.UserManager;
import mage.server.util.ThreadExecutor;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.GameClientMessage;
import mage.view.GameView;
import mage.view.LookedAtView;
import mage.view.SimpleCardsView;
import mage.view.UserRequestMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameSessionPlayer extends GameSessionWatcher {

    private static final Logger logger = Logger.getLogger(GameSessionPlayer.class);

    private final UUID playerId;

    private static final ExecutorService callExecutor = ThreadExecutor.getInstance().getCallExecutor();

    public GameSessionPlayer(Game game, UUID userId, UUID playerId) {
        super(userId, game, true);
        this.playerId = playerId;
    }

    @Override
    public void CleanUp() {
        super.CleanUp();
    }

    public void ask(final String question)  {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameAsk", game.getId(), new GameClientMessage(getGameView(), question)));
            }
        }
    }

    public void target(final String question, final CardsView cardView, final Set<UUID> targets, final boolean required, final Map<String, Serializable> options) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameTarget", game.getId(), new GameClientMessage(getGameView(), question, cardView, targets, required, options)));
            }
        }
    }

    public void select(final String message, final Map<String, Serializable> options) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameSelect", game.getId(), new GameClientMessage(getGameView(), message, options)));
            }
        }
    }

    public void chooseAbility(final AbilityPickerView abilities) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameChooseAbility", game.getId(), abilities));
            }
        }
    }

    public void choosePile(final String message, final CardsView pile1, final CardsView pile2) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameChoosePile", game.getId(), new GameClientMessage(message, pile1, pile2)));
            }
        }
    }

    public void chooseChoice(final Choice choice) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameChooseChoice", game.getId(), new GameClientMessage(choice)));
            }
        }
    }

    public void playMana(final String message) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gamePlayMana", game.getId(), new GameClientMessage(getGameView(), message)));
            }
        }
    }

    public void playXMana(final String message) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gamePlayXMana", game.getId(), new GameClientMessage(getGameView(), message)));
            }
        }
    }

    public void getAmount(final String message, final int min, final int max) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameSelectAmount", game.getId(), new GameClientMessage(message, min, max)));
            }
        }
    }

    public void endGameInfo(Table table) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("endGameInfo", game.getId(), getGameEndView(playerId, table)));
            }
        }
    }

    public void requestPermissionToSeeHandCards(UUID watcherId) {
        if (!killed) {
            User watcher = UserManager.getInstance().getUser(watcherId);
            User user = UserManager.getInstance().getUser(userId);
            if (user != null && watcher != null) {
                UserRequestMessage userRequestMessage = new UserRequestMessage(
                        "User request",
                        "Allow user <b>" + watcher.getName() + "</b> for this match to see your hand cards?<br>" +
                        "(You can revoke this every time using related popup menu item of your battlefield.)"
                        , PlayerAction.REQUEST_PERMISSION_TO_SEE_HAND_CARDS);
                userRequestMessage.setRelatedUser(watcherId, watcher.getName());
                userRequestMessage.setGameId(game.getId());
                userRequestMessage.setButton1("Accept", PlayerAction.ADD_PERMISSION_TO_SEE_HAND_CARDS);
                userRequestMessage.setButton2("Reject", null);
                user.fireCallback(new ClientCallback("userRequestDialog", game.getId(), userRequestMessage));
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
        gameView.setHand(new CardsView(player.getHand().getCards(game)));
        if (gameView.getPriorityPlayerName().equals(player.getName())) {
            gameView.setCanPlayInHand(player.getPlayableInHand(game));
        }

        processControlledPlayers(player, gameView);
        processWatchedHands(userId, gameView);
        //TODO: should player who controls another player's turn be able to look at all these cards?

        List<LookedAtView> list = new ArrayList<>();
        for (Entry<String, Cards> entry : game.getState().getLookedAt(playerId).entrySet()) {
            list.add(new LookedAtView(entry.getKey(), entry.getValue(), game));
        }
        gameView.setLookedAt(list);
        game.getState().clearLookedAt(playerId);

        return gameView;
    }

    private void processControlledPlayers(Player player, GameView gameView) {
        if (player.getPlayersUnderYourControl().size() > 0) {
            Map<String, SimpleCardsView> handCards = new HashMap<>();
            for (UUID controlledPlayerId : player.getPlayersUnderYourControl()) {
                Player opponent = game.getPlayer(controlledPlayerId);
                handCards.put(opponent.getName(), new SimpleCardsView(opponent.getHand().getCards(game)));
            }
            gameView.setOpponentHands(handCards);
        }
    }

    public void removeGame() {
        User user = UserManager.getInstance().getUser(userId);
        if (user != null) {
            user.removeGame(playerId);
        }
    }

    public UUID getGameId() {
        return game.getId();
    }

    public void quitGame() {
        if (game != null) {
            final Player player = game.getPlayer(playerId);
            if (player != null && player.isInGame()) {
                callExecutor.execute(
                    new Runnable() {
                        @Override
                        public void run() {
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
                                    logger.fatal("Game session game quit exception " + (ex.getMessage() == null ? "null":ex.getMessage()));
                                    logger.debug("- gameId:" + game.getId() +"  playerId: " + playerId);
                                    if (ex.getCause() != null) {
                                        logger.debug("- Cause: " + (ex.getCause().getMessage() == null ? "null":ex.getCause().getMessage()), ex);
                                    } else {
                                        logger.debug("- ex: " + ex.toString(), ex);
                                    }
                                }else {
                                    logger.fatal("Game session game quit exception - null  gameId:" + game.getId() +"  playerId: " + playerId);
                                }
                            }
                        }
                    }
                );
                
            }
        } else {
            logger.error("game object missing   playerId: " + (playerId == null ? "[null]":playerId));
        }
    }

}
