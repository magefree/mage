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
import mage.game.Game;
import mage.game.GameState;
import mage.MageException;
import mage.interfaces.callback.CallbackException;
import mage.interfaces.callback.ClientCallback;
import mage.server.Session;
import mage.server.SessionManager;
import mage.view.GameView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReplaySession implements GameCallback {

	private final static Logger logger = Logger.getLogger(ReplaySession.class);
	private GameReplay replay;
	protected UUID sessionId;

	ReplaySession(UUID gameId, UUID sessionId) {
		this.replay = new GameReplay(gameId);
		this.sessionId = sessionId;
	}

	public void replay() {
		replay.start();
		Session session = SessionManager.getInstance().getSession(sessionId);
		if (session != null) {
			session.fireCallback(new ClientCallback("replayInit", replay.getGame().getId(), new GameView(replay.next(), replay.getGame())));
		}
	}

	public void stop() {
		gameResult("stopped replay");
	}

	public synchronized void next() {
		updateGame(replay.next(), replay.getGame());
	}

	public synchronized void previous() {
		updateGame(replay.previous(), replay.getGame());
	}

	@Override
	public void gameResult(final String result) {
		Session session = SessionManager.getInstance().getSession(sessionId);
		if (session != null) {
			session.fireCallback(new ClientCallback("replayDone", replay.getGame().getId(), result));
		}
	}

	private void updateGame(final GameState state, Game game) {
		if (state == null) {
			gameResult("game ended");
		}
		else {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null) {
				session.fireCallback(new ClientCallback("replayUpdate", replay.getGame().getId(), new GameView(state, game)));
			}
		}
	}

}
