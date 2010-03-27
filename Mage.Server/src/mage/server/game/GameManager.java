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
import mage.game.Game;
import mage.game.GameReplay;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameManager {
	private final static GameManager INSTANCE = new GameManager();

	public static GameManager getInstance() {
		return INSTANCE;
	}

	private GameManager() {}

	private ConcurrentHashMap<UUID, GameController> gameControllers = new ConcurrentHashMap<UUID, GameController>();

	public UUID createGameSession(Game game, ConcurrentHashMap<UUID, UUID> sessionPlayerMap, UUID tableId) {
		GameController gameController = new GameController(game, sessionPlayerMap, tableId);
		gameControllers.put(game.getId(), gameController);
		return gameController.getSessionId();
	}

	public void joinGame(UUID gameId, UUID sessionId) {
		gameControllers.get(gameId).join(sessionId);
	}

//	public void leaveGame(UUID gameId, UUID clientId) {
//		gameControllers.get(gameId).leave(clientId);
//	}

	public void destroyChatSession(UUID gameId) {
		gameControllers.remove(gameId);
	}

	public UUID getChatId(UUID gameId) {
		return gameControllers.get(gameId).getChatId();
	}

	public void sendPlayerUUID(UUID gameId, UUID sessionId, UUID data) {
		gameControllers.get(gameId).sendPlayerUUID(sessionId, data);
	}

	public void sendPlayerString(UUID gameId, UUID sessionId, String data) {
		gameControllers.get(gameId).sendPlayerString(sessionId, data);
	}

	public void sendPlayerBoolean(UUID gameId, UUID sessionId, Boolean data) {
		gameControllers.get(gameId).sendPlayerBoolean(sessionId, data);
	}

	public void sendPlayerInteger(UUID gameId, UUID sessionId, Integer data) {
		gameControllers.get(gameId).sendPlayerInteger(sessionId, data);
	}

	public void concedeGame(UUID gameId, UUID sessionId) {
		gameControllers.get(gameId).concede(sessionId);
	}

	public void watchGame(UUID gameId, UUID sessionId) {
		gameControllers.get(gameId).watch(sessionId);
	}

	public void stopWatching(UUID gameId, UUID sessionId) {
		gameControllers.get(gameId).stopWatching(sessionId);
	}

	public void removeSession(UUID sessionId) {
		for (GameController controller: gameControllers.values()) {
			controller.kill(sessionId);
		}
	}

	public void kill(UUID gameId, UUID sessionId) {
		gameControllers.get(gameId).kill(sessionId);
	}

	public GameReplay createReplay(UUID gameId) {
		return gameControllers.get(gameId).createReplay();
	}

	public void cheat(UUID gameId, UUID sessionId, DeckCardLists deckList) {
		gameControllers.get(gameId).cheat(sessionId, deckList);
	}

	void timeout(UUID gameId, UUID sessionId) {
		gameControllers.get(gameId).timeout(sessionId);
	}

}
