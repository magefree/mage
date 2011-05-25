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

package mage.server.draft;

import java.rmi.RemoteException;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import mage.game.draft.Draft;
import mage.MageException;
import mage.interfaces.callback.CallbackException;
import mage.interfaces.callback.ClientCallback;
import mage.server.Session;
import mage.server.SessionManager;
import mage.server.util.ThreadExecutor;
import mage.view.DraftClientMessage;
import mage.view.DraftPickView;
import mage.view.DraftView;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DraftSession {

	protected final static Logger logger = Logger.getLogger(DraftSession.class);

	protected UUID sessionId;
	protected UUID playerId;
	protected Draft draft;
	protected boolean killed = false;

	private ScheduledFuture<?> futureTimeout;
	protected static ScheduledExecutorService timeoutExecutor = ThreadExecutor.getInstance().getTimeoutExecutor();

	public DraftSession(Draft draft, UUID sessionId, UUID playerId) {
		this.sessionId = sessionId;
		this.draft = draft;
		this.playerId = playerId;
	}

	public boolean init(final DraftView draftView) {
		if (!killed) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null) {
				try {
					session.fireCallback(new ClientCallback("draftInit", draft.getId(), draftView));
					return true;
				} catch (CallbackException ex) {
					logger.fatal("Unable to start draft ", ex);
					return false;
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

	public void update(final DraftView draftView) {
		if (!killed) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null) {
				try {
					session.fireCallback(new ClientCallback("draftUpdate", draft.getId(), draftView));
				} catch (CallbackException ex) {
					logger.fatal("update draft exception", ex);
				}
			}
		}
	}

	public void inform(final String message, final DraftView draftView) {
		if (!killed) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null) {
				try {
					session.fireCallback(new ClientCallback("draftInform", draft.getId(), new DraftClientMessage(draftView, message)));
				} catch (CallbackException ex) {
					logger.fatal("draft inform exception", ex);
				}
			}
		}
	}

	public void draftOver() {
		if (!killed) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null) {
				try {
					session.fireCallback(new ClientCallback("draftOver", draft.getId()));
				} catch (CallbackException ex) {
					logger.fatal("draft end exception", ex);
				}
			}
		}
	}

	public void pickCard(final DraftPickView draftPickView, int timeout) {
		if (!killed) {
			setupTimeout(timeout);
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null) {
				try {
					session.fireCallback(new ClientCallback("draftPick", draft.getId(), new DraftClientMessage(draftPickView)));
				} catch (CallbackException ex) {
					logger.fatal("draft pick exception", ex);
				}
			}
		}
	}

	private synchronized void setupTimeout(int seconds) {
		cancelTimeout();
		if (seconds > 0) {
			futureTimeout = timeoutExecutor.schedule(
				new Runnable() {
					@Override
					public void run() {
						DraftManager.getInstance().timeout(draft.getId(), sessionId);
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

	protected void handleRemoteException(RemoteException ex) {
		logger.fatal("DraftSession error ", ex);
		DraftManager.getInstance().kill(draft.getId(), sessionId);
	}

	public void setKilled() {
		killed = true;
	}

	public boolean sendCardPick(UUID cardId) {
		cancelTimeout();
		return draft.addPick(playerId, cardId);

	}

}
