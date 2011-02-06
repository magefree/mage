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
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.game.tournament.Tournament;
import mage.interfaces.callback.ClientCallback;
import mage.server.Session;
import mage.server.SessionManager;
import mage.util.Logging;
import mage.view.TournamentView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentSession {
	protected final static Logger logger = Logging.getLogger(TournamentSession.class.getName());

	protected UUID sessionId;
	protected UUID playerId;
	protected Tournament tournament;
	protected boolean killed = false;

	public TournamentSession(Tournament tournament, UUID sessionId, UUID playerId) {
		this.sessionId = sessionId;
		this.tournament = tournament;
		this.playerId = playerId;
	}

	public boolean init(final TournamentView tournamentView) {
		if (!killed) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null) {
				session.clearAck();
				session.fireCallback(new ClientCallback("tournamentInit", tournamentView));
				if (waitForAck("tournamentInit"))
					return true;
			}
		}
		return false;
	}

	public boolean waitForAck(String message) {
		Session session = SessionManager.getInstance().getSession(sessionId);
		do {
			//TODO: add timeout
		} while (!session.getAckMessage().equals(message) && !killed);
		return true;
	}

	public void update(final TournamentView tournamentView) {
		if (!killed) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.fireCallback(new ClientCallback("tournamentUpdate", tournamentView));
		}
	}

	public void gameOver(final String message) {
		if (!killed) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			if (session != null)
				session.fireCallback(new ClientCallback("tournamentOver", message));
		}
	}

	protected void handleRemoteException(RemoteException ex) {
		logger.log(Level.SEVERE, null, ex);
		TournamentManager.getInstance().kill(tournament.getId(), sessionId);
	}

	public void setKilled() {
		killed = true;
	}

}
