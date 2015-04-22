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

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import mage.cards.decks.DeckCardLists;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.Game;
import mage.view.GameView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameManager {
    private static final GameManager INSTANCE = new GameManager();

    public static GameManager getInstance() {
        return INSTANCE;
    }

    private GameManager() {}

    private final ConcurrentHashMap<UUID, GameController> gameControllers = new ConcurrentHashMap<>();

    public UUID createGameSession(Game game, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId, UUID choosingPlayerId) {
        GameController gameController = new GameController(game, userPlayerMap, tableId, choosingPlayerId);
        gameControllers.put(game.getId(), gameController);
        return gameController.getSessionId();
    }

    public void joinGame(UUID gameId, UUID userId) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            gameController.join(userId);
        }
    }

    public UUID getChatId(UUID gameId) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            return gameController.getChatId();
        }
        return null;
    }

    public void sendPlayerUUID(UUID gameId, UUID userId, UUID data) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            gameController.sendPlayerUUID(userId, data);
        }
    }

    public void sendPlayerString(UUID gameId, UUID userId, String data) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            gameController.sendPlayerString(userId, data);
        }
    }

    public void sendPlayerManaType(UUID gameId, UUID playerId, UUID userId, ManaType data) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            gameController.sendPlayerManaType(userId, playerId, data);
        }
    }

    public void sendPlayerBoolean(UUID gameId, UUID userId, Boolean data) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            gameController.sendPlayerBoolean(userId, data);
        }
    }

    public void sendPlayerInteger(UUID gameId, UUID userId, Integer data) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            gameController.sendPlayerInteger(userId, data);
        }
    }

    public void quitMatch(UUID gameId, UUID userId) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            gameController.quitMatch(userId);
        }
    }
   
    public void sendPlayerAction(PlayerAction playerAction, UUID gameId, UUID userId, Object data) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {        
            gameController.sendPlayerAction(playerAction, userId, data);
        }
    }

    public void watchGame(UUID gameId, UUID userId) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            gameController.watch(userId);
        }
    }

    public void stopWatching(UUID gameId, UUID userId) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            gameController.stopWatching(userId);
        }
    }

    public void cheat(UUID gameId, UUID userId, UUID playerId, DeckCardLists deckList) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            gameController.cheat(userId, playerId, deckList);
        }
    }

    public boolean cheat(UUID gameId, UUID userId, UUID playerId, String cardName) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            return gameController.cheat(userId, playerId, cardName);
        }
        return false;
    }

    public void removeGame(UUID gameId) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            gameController.cleanUp();
            gameControllers.remove(gameId);
        }
    }

    public boolean saveGame(UUID gameId) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            return gameController.saveGame();
        }
        return false;
    }

    public GameView getGameView(UUID gameId, UUID userId, UUID playerId) {
        GameController gameController = gameControllers.get(gameId);
        if (gameController != null) {
            return gameController.getGameView(playerId);
        }
        return null;
    }

    public int getNumberActiveGames() {
        return gameControllers.size();
    }
    
    public ConcurrentHashMap<UUID, GameController> getGameController() {
        return gameControllers;
    }
}
