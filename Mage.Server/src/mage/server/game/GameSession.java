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

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import mage.game.Game;
import mage.interfaces.callback.ClientCallback;
import mage.server.Session;
import mage.server.SessionManager;
import mage.server.util.ConfigSettings;
import mage.server.util.ThreadExecutor;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.GameClientMessage;
import mage.view.GameView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameSession extends GameWatcher {

	private Game game;
	private UUID playerId;

	private ScheduledFuture<?> futureTimeout;
	protected static ScheduledExecutorService timeoutExecutor = ThreadExecutor.getInstance().getTimeoutExecutor();

	public GameSession(Game game, UUID sessionId, UUID playerId) {
		super(sessionId, game.getId());
		this.game = game;
		this.playerId = playerId;
	}

	public void ask(final String question, final GameView gameView) {
		if (!killed) {
			setupTimeout();
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.fireCallback(new ClientCallback("gameAsk", new GameClientMessage(gameView, question)));
		}
	}

	public void target(final String question, final CardsView cardView, final boolean required, final GameView gameView) {
		if (!killed) {
			setupTimeout();
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.fireCallback(new ClientCallback("gameTarget", new GameClientMessage(gameView, question, cardView, required)));
		}
	}

	public void select(final String message, final GameView gameView) {
		if (!killed) {
			setupTimeout();
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.fireCallback(new ClientCallback("gameSelect", new GameClientMessage(gameView, message)));
		}
	}

	public void chooseAbility(final AbilityPickerView abilities) {
		if (!killed) {
			setupTimeout();
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.fireCallback(new ClientCallback("gameChooseAbility", abilities));
		}
	}

	public void choose(final String message, final Set<String> choices) {
		if (!killed) {
			setupTimeout();
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.fireCallback(new ClientCallback("gameChoose", new GameClientMessage(choices.toArray(new String[0]), message)));
		}
	}

	public void playMana(final String message, final GameView gameView) {
		if (!killed) {
			setupTimeout();
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.fireCallback(new ClientCallback("gamePlayMana", new GameClientMessage(gameView, message)));
		}
	}

	public void playXMana(final String message, final GameView gameView) {
		if (!killed) {
			setupTimeout();
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.fireCallback(new ClientCallback("gamePlayXMana", new GameClientMessage(gameView, message)));
		}
	}

	public void getAmount(final String message, final int min, final int max) {
		if (!killed) {
			setupTimeout();
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.fireCallback(new ClientCallback("gameSelectAmount", new GameClientMessage(message, min, max)));
		}
	}

	public void revealCards(final String name, final CardsView cardView) {
		if (!killed) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.fireCallback(new ClientCallback("gameReveal", new GameClientMessage(cardView, name)));
		}
	}


	private synchronized void setupTimeout() {
		cancelTimeout();
		futureTimeout = timeoutExecutor.schedule(
			new Runnable() {
				@Override
				public void run() {
					GameManager.getInstance().timeout(gameId, sessionId);
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
}
