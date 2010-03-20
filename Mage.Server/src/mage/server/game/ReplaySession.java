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

import java.rmi.RemoteException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.game.GameReplay;
import mage.game.GameState;
import mage.interfaces.GameReplayClient;
import mage.server.util.ThreadExecutor;
import mage.util.Logging;
import mage.view.GameView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReplaySession implements GameCallback {

	protected static ExecutorService rmiExecutor = ThreadExecutor.getInstance().getRMIExecutor();
	private final static Logger logger = Logging.getLogger(ReplaySession.class.getName());

	private GameReplay game;
	private GameReplayClient client;

	ReplaySession(UUID gameId) {
		this.game = GameManager.getInstance().createReplay(gameId);
	}

	public void replay(GameReplayClient replayClient) {
		this.client = replayClient;
		game.start();
		rmiExecutor.submit(
			new Runnable() {
				public void run() {
					try {
						client.init(new GameView(game.next()));
					} catch (RemoteException ex) {
						logger.log(Level.SEVERE, null, ex);
					}
				}
			}
		);
	}

	public void stop() {
		gameResult("stopped replay");
	}

	public synchronized void next() {
		updateGame(game.next());
	}

	public synchronized void previous() {
		updateGame(game.previous());
	}

	public void gameResult(final String result) {
		rmiExecutor.submit(
			new Runnable() {
				public void run() {
					try {
						client.gameOver(result);
					} catch (RemoteException ex) {
						logger.log(Level.SEVERE, null, ex);
					}
				}
			}
		);
	}

	private void updateGame(final GameState state) {
		rmiExecutor.submit(
			new Runnable() {
				public void run() {
					try {
						client.update(new GameView(state));
					} catch (RemoteException ex) {
						logger.log(Level.SEVERE, null, ex);
					}
				}
			}
		);
	}

}
