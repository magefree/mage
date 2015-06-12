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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.game.Game;
import mage.game.Table;
import mage.interfaces.callback.ClientCallback;
import mage.players.Player;
import mage.server.User;
import mage.server.UserManager;
import mage.view.GameClientMessage;
import mage.view.GameEndView;
import mage.view.GameView;
import mage.view.SimpleCardsView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameSessionWatcher {

    protected final static Logger logger = Logger.getLogger(GameSessionWatcher.class);

    protected UUID userId;
    protected Game game;
    protected boolean killed = false;
    protected boolean isPlayer;

    public GameSessionWatcher(UUID userId, Game game, boolean isPlayer) {
        this.userId = userId;
        this.game = game;
        this.isPlayer = isPlayer;
    }

    public boolean init() {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameInit", game.getId(), getGameView()));
                return true;
            }
        }
        return false;
    }

    public void update() {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameUpdate", game.getId(), getGameView()));
            }
        }
    }

    public void inform(final String message) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameInform", game.getId(), new GameClientMessage(getGameView(), message)));
            }
        }
    }

    public void informPersonal(final String message) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameInformPersonal", game.getId(), new GameClientMessage(getGameView(), message)));
            }
        }
    }

    public void gameOver(final String message) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.removeGameWatchInfo(game.getId());
                user.fireCallback(new ClientCallback("gameOver", game.getId(), message));
            }
        }
    }

    /**
     * Cleanup if Session ends
     * 
     */
    public void CleanUp() {

    }

    public void gameError(final String message) {
        if (!killed) {
            User user = UserManager.getInstance().getUser(userId);
            if (user != null) {
                user.fireCallback(new ClientCallback("gameError", game.getId(), message));
            }
        }
    }

    public void setKilled() {
        killed = true;
    }

    public GameView getGameView() {
        GameView gameView = new GameView(game.getState(), game, null, userId);
        processWatchedHands(userId, gameView);
        return gameView;

    }

    protected void processWatchedHands(UUID userId, GameView gameView) {
        Map<String, SimpleCardsView> handCards = new HashMap<>();
        for (Player player: game.getPlayers().values()) {
            if (player.hasUserPermissionToSeeHand(userId)) {
                handCards.put(player.getName(), new SimpleCardsView(player.getHand().getCards(game), true));
                gameView.setWatchedHands(handCards);
            }
        }
    }
    public GameEndView getGameEndView(UUID playerId, Table table) {
        return new GameEndView(game.getState(), game, playerId, table);
    }

    public boolean isPlayer() {
        return isPlayer;
    }
       
}
