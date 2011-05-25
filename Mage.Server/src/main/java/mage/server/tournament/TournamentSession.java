/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.server.tournament;

import java.rmi.RemoteException;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import mage.cards.decks.Deck;
import mage.game.tournament.Tournament;
import mage.MageException;
import mage.interfaces.callback.CallbackException;
import mage.interfaces.callback.ClientCallback;
import mage.server.Session;
import mage.server.SessionManager;
import mage.server.util.ThreadExecutor;
import mage.view.TournamentView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentSession {
	protected final static Logger logger = Logger.getLogger(TournamentSession.class);

	protected UUID sessionId;
	protected UUID playerId;
	protected UUID tableId;
	protected Tournament tournament;
	protected boolean killed = false;

	private ScheduledFuture<?> futureTimeout;
	protected static ScheduledExecutorService timeoutExecutor = ThreadExecutor.getInstance().getTimeoutExecutor();

	public TournamentSession(Tournament tournament, UUID sessionId, UUID tableId, UUID playerId) {
		this.sessionId = sessionId;
		this.tournament = tournament;
		this.playerId = playerId;
		this.tableId = tableId;
	}

	public boolean init(final TournamentView tournamentView) {
		if (!killed) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null) {
				try {
					session.fireCallback(new ClientCallback("tournamentInit", tournament.getId(), tournamentView));
					return true;
				} catch (CallbackException ex) {
					logger.fatal("Unable to start tournament", ex);
				}
			}
		}
		return false;
	}

//	public boolean waitForAck(String message) {
//		Session session = SessionManager.getInstance().getSession(sessionId);
//		do {
//			//TODO: add timeout
//		} while (!session.getAckMessage().equals(message) && !killed);
//		return true;
//	}

	public void update(final TournamentView tournamentView) {
		if (!killed) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null) {
				try {
					session.fireCallback(new ClientCallback("tournamentUpdate", tournament.getId(), tournamentView));
				} catch (CallbackException ex) {
					logger.fatal("tournament update error", ex);
				}
			}
		}
	}

	public void gameOver(final String message) {
		if (!killed) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null) {
				try {
					session.fireCallback(new ClientCallback("tournamentOver", tournament.getId(), message));
				} catch (CallbackException ex) {
					logger.fatal("tournament over error", ex);
				}
			}
		}
	}

	public void construct(Deck deck, int timeout) throws MageException {
		if (!killed) {
			setupTimeout(timeout);
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.construct(deck, tableId, timeout);
		}
	}

	public void submitDeck(Deck deck) {
		cancelTimeout();
		tournament.submitDeck(playerId, deck);
	}

	protected void handleRemoteException(RemoteException ex) {
		logger.fatal("TournamentSession error ", ex);
		TournamentManager.getInstance().kill(tournament.getId(), sessionId);
	}

	public void setKilled() {
		killed = true;
	}

	private synchronized void setupTimeout(int seconds) {
		cancelTimeout();
		if (seconds > 0) {
			futureTimeout = timeoutExecutor.schedule(
				new Runnable() {
					@Override
					public void run() {
						TournamentManager.getInstance().timeout(tournament.getId(), sessionId);
					}
				},
				seconds, TimeUnit.SECONDS
			);
		}
	}

	private synchronized void cancelTimeout() {
		if (futureTimeout != null) {
			futureTimeout.cancel(false);
		}
	}

}
