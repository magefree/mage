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
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import mage.cards.Cards;
import mage.game.Game;
import mage.interfaces.callback.ClientCallback;
import mage.players.Player;
import mage.players.net.UserData;
import mage.server.User;
import mage.server.UserManager;
import mage.server.util.ConfigSettings;
import mage.server.util.ThreadExecutor;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.GameClientMessage;
import mage.view.GameView;
import mage.view.LookedAtView;
import mage.view.SimpleCardsView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameSession extends GameWatcher {

    private UUID playerId;
    private boolean useTimeout;

    private ScheduledFuture<?> futureTimeout;
    protected static ScheduledExecutorService timeoutExecutor = ThreadExecutor.getInstance().getTimeoutExecutor();

    private UserData userData;

    public GameSession(Game game, UUID userId, UUID playerId, boolean useTimeout) {
        super(userId, game);
        this.playerId = playerId;
        this.useTimeout = useTimeout;
    }

    public void ask(final String question)  {
        if (!killed) {
            setupTimeout();
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameAsk", game.getId(), new GameClientMessage(getGameView(), question)));
            }
        }
    }

    public void target(final String question, final CardsView cardView, final Set<UUID> targets, final boolean required, final Map<String, Serializable> options) {
        if (!killed) {
            setupTimeout();
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameTarget", game.getId(), new GameClientMessage(getGameView(), question, cardView, targets, required, options)));
            }
        }
    }

    public void select(final String message) {
        if (!killed) {
            setupTimeout();
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameSelect", game.getId(), new GameClientMessage(getGameView(), message)));
            }
        }
    }

    public void chooseAbility(final AbilityPickerView abilities) {
        if (!killed) {
            setupTimeout();
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameChooseAbility", game.getId(), abilities));
            }
        }
    }

    public void choosePile(final String message, final CardsView pile1, final CardsView pile2) {
        if (!killed) {
            setupTimeout();
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                List<CardsView> piles = new ArrayList<CardsView>();
                piles.add(pile1);
                piles.add(pile2);
                user.fireCallback(new ClientCallback("gameChoosePile", game.getId(), new GameClientMessage(message, pile1, pile2)));
            }
        }
    }

    public void choose(final String message, final Set<String> choices) {
        if (!killed) {
            setupTimeout();
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameChoose", game.getId(), new GameClientMessage(choices.toArray(new String[0]), message)));
            }
        }
    }

    public void playMana(final String message) {
        if (!killed) {
            setupTimeout();
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gamePlayMana", game.getId(), new GameClientMessage(getGameView(), message)));
            }
        }
    }

    public void playXMana(final String message) {
        if (!killed) {
            setupTimeout();
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gamePlayXMana", game.getId(), new GameClientMessage(getGameView(), message)));
            }
        }
    }

    public void getAmount(final String message, final int min, final int max) {
        if (!killed) {
            setupTimeout();
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameSelectAmount", game.getId(), new GameClientMessage(message, min, max)));
            }
        }
    }

    public void revealCards(final String name, final CardsView cardView) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameReveal", game.getId(), new GameClientMessage(cardView, name)));
            }
        }
    }

    private synchronized void setupTimeout() {
        if (!useTimeout)
            return;
        cancelTimeout();
        futureTimeout = timeoutExecutor.schedule(
            new Runnable() {
                @Override
                public void run() {
                    GameManager.getInstance().timeout(game.getId(), userId);
                }
            },
            ConfigSettings.getInstance().getMaxSecondsIdle(), TimeUnit.SECONDS
        );
    }

    private synchronized void cancelTimeout() {
        if (futureTimeout != null) {
            futureTimeout.cancel(false);
        }
    }

    public void sendPlayerUUID(UUID data) {
        cancelTimeout();
        game.getPlayer(playerId).setResponseUUID(data);
    }

    public void sendPlayerString(String data) {
        cancelTimeout();
        game.getPlayer(playerId).setResponseString(data);
    }

    public void sendPlayerBoolean(Boolean data) {
        cancelTimeout();
        game.getPlayer(playerId).setResponseBoolean(data);
    }

    public void sendPlayerInteger(Integer data) {
        cancelTimeout();
        game.getPlayer(playerId).setResponseInteger(data);
    }

    @Override
    public GameView getGameView() {
        Player player = game.getPlayer(playerId);
        player.setUserData(this.userData);
        GameView gameView = new GameView(game.getState(), game);
        gameView.setHand(new SimpleCardsView(player.getHand().getCards(game)));

        if (player.getPlayersUnderYourControl().size() > 0) {
            Map<String, SimpleCardsView> handCards = new HashMap<String, SimpleCardsView>();
            for (UUID playerId : player.getPlayersUnderYourControl()) {
                Player opponent = game.getPlayer(playerId);
                handCards.put(opponent.getName(), new SimpleCardsView(opponent.getHand().getCards(game)));
            }
            gameView.setOpponentHands(handCards);
        }

        //TODO: should player who controls another player's turn be able to look at all these cards?

        List<LookedAtView> list = new ArrayList<LookedAtView>();
        for (Entry<String, Cards> entry : game.getState().getLookedAt(playerId).entrySet()) {
            list.add(new LookedAtView(entry.getKey(), entry.getValue(), game));
        }
        gameView.setLookedAt(list);
        game.getState().clearLookedAt();

        return gameView;
    }

    public void removeGame() {
        User user = UserManager.getInstance().getUser(userId);
        if (user != null)
            user.removeGame(playerId);
    }

    public UUID getGameId() {
        return game.getId();
    }

    public void kill() {
        game.quit(playerId);
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
