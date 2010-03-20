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

package mage.server;

import mage.server.util.ThreadExecutor;
import java.rmi.RemoteException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.interfaces.Client;
import mage.server.game.GameManager;
import mage.server.game.TableManager;
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Session {

	private static ExecutorService executor = ThreadExecutor.getInstance().getRMIExecutor();
	private final static Logger logger = Logging.getLogger(Session.class.getName());

	private UUID sessionId;
	private String username;
	private Client client;

	public Session(Client client) {
		sessionId = UUID.randomUUID();
		try {
			username = client.getUserName();
		} catch (RemoteException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		this.client = client;
	}

	public UUID getId() {
		return sessionId;
	}

	public void kill() {
		SessionManager.getInstance().removeSession(sessionId);
		TableManager.getInstance().removeSession(sessionId);
		GameManager.getInstance().removeSession(sessionId);

	}

	public void gameStarted(final UUID gameId, final UUID playerId) {
		executor.submit(
			new Runnable() {
				@Override
				public void run() {
					try {
						client.gameStarted(gameId, playerId);
						logger.info("game started for player " + playerId);
					}
					catch (RemoteException ex) {
						logger.log(Level.WARNING, ex.getMessage());
						kill();
					}
				}
			}
		);
	}

	public void watchGame(final UUID gameId) {
		executor.submit(
			new Runnable() {
				@Override
				public void run() {
					try {
						client.watchGame(gameId);
					}
					catch (RemoteException ex) {
						logger.log(Level.WARNING, ex.getMessage());
						kill();
					}
				}
			}
		);
	}

	public void replayGame(final UUID gameId) {
		executor.submit(
			new Runnable() {
				@Override
				public void run() {
					try {
						client.replayGame(gameId);
					}
					catch (RemoteException ex) {
						logger.log(Level.WARNING, ex.getMessage());
						kill();
					}
				}
			}
		);
	}

	public String getUsername() {
		return username;
	}

}
