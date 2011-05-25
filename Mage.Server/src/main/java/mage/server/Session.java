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

import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import mage.cards.decks.Deck;
import mage.game.GameException;
import mage.MageException;
import mage.interfaces.callback.CallbackAck;
import mage.interfaces.callback.CallbackException;
import mage.interfaces.callback.CallbackServerSession;
import mage.interfaces.callback.ClientCallback;
import mage.server.game.GameManager;
import mage.view.TableClientMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Session {

	private final static Logger logger = Logger.getLogger(Session.class);

	private UUID sessionId;
	private UUID clientId;
	private String username;
	private String host;
	private int messageId = 0;
	private Date timeConnected;
	private long lastPing;
	private boolean isAdmin = false;
	private boolean killed = false;
	private final CallbackServerSession callback = new CallbackServerSession();
	private final CallbackAck ackResponse = new CallbackAck();

	public Session(String userName, String host, UUID clientId) {
		sessionId = UUID.randomUUID();
		this.username = userName;
		this.host = host;
		this.clientId = clientId;
		this.isAdmin = false;
		this.timeConnected = new Date();
		ping();
	}

	public Session(String host) {
		sessionId = UUID.randomUUID();
		this.username = "Admin";
		this.host = host;
		this.isAdmin = true;
		this.timeConnected = new Date();
		ping();
	}

	public UUID getId() {
		return sessionId;
	}

	public UUID getClientId() {
		return clientId;
	}

	public void kill() {
		this.killed = true;
		ackResponse.notify();
		SessionManager.getInstance().removeSession(sessionId);
		TableManager.getInstance().removeSession(sessionId);
		GameManager.getInstance().removeSession(sessionId);
		ChatManager.getInstance().removeSession(sessionId);
	}

	public ClientCallback callback() {
		try {
			return callback.callback();
		} catch (InterruptedException ex) {
			logger.fatal("Session callback error", ex);
		}
		return null;
	}

	public synchronized void fireCallback(final ClientCallback call) throws CallbackException {
		call.setMessageId(messageId++);
		if (logger.isDebugEnabled())
			logger.debug(sessionId + " - " + call.getMessageId() + " - " + call.getMethod());
		try {
			int retryCount = 0;
			while (retryCount < 3) {
				callback.setCallback(call);
				if (waitForAck(call.getMessageId()))
					return;
				retryCount++;
				try {
					Thread.sleep(2000 * retryCount);
				} 
				catch (InterruptedException ignored) {}
			}
		} catch (InterruptedException ex) {
			logger.fatal("Session fireCallback error", ex);
		}
		throw new CallbackException("Callback failed for " + call.getMethod());
	}
	
	protected boolean waitForAck(int messageId) {
		ackResponse.clear();
		if (logger.isDebugEnabled())
			logger.debug(sessionId + " - waiting for ack: " + messageId);
		synchronized(ackResponse) {
			try {
				if (!ackResponse.isAck())
					ackResponse.wait(10000);
				if (logger.isDebugEnabled()) {
					if (!ackResponse.isAck())
						logger.debug(sessionId + " - ack timed out waiting for " + messageId);
					else
						logger.debug(sessionId + " - ack received: " + messageId);
				}
				return ackResponse.getValue() == messageId;
			} catch (InterruptedException ex) {	}
		}
		return false;
	}

	public void gameStarted(final UUID gameId, final UUID playerId) throws GameException {
		try {
			fireCallback(new ClientCallback("startGame", gameId, new TableClientMessage(gameId, playerId)));
		} catch (CallbackException ex) {
			logger.fatal("gameStarted exception", ex);
			throw new GameException("callback failed");
		}
	}

	public void draftStarted(final UUID draftId, final UUID playerId) throws MageException {
		try {
			fireCallback(new ClientCallback("startDraft", draftId, new TableClientMessage(draftId, playerId)));
		} catch (CallbackException ex) {
			logger.fatal("draftStarted exception", ex);
			throw new MageException("callback failed");
		}
	}

	public void tournamentStarted(final UUID tournamentId, final UUID playerId) throws MageException {
		try {
			fireCallback(new ClientCallback("startTournament", tournamentId, new TableClientMessage(tournamentId, playerId)));
		} catch (CallbackException ex) {
			logger.fatal("tournamentStarted exception", ex);
			throw new MageException("callback failed");
		}
	}

	public void sideboard(final Deck deck, final UUID tableId, final int time) throws MageException {
		try {
			fireCallback(new ClientCallback("sideboard", tableId, new TableClientMessage(deck, tableId, time)));
		} catch (CallbackException ex) {
			logger.fatal("sideboard exception", ex);
			throw new MageException("callback failed");
		}
	}

	public void construct(final Deck deck, final UUID tableId, final int time) throws MageException {
		try {
			fireCallback(new ClientCallback("construct", tableId, new TableClientMessage(deck, tableId, time)));
		} catch (CallbackException ex) {
			logger.fatal("construct exception", ex);
			throw new MageException("callback failed");
		}
	}

	public void watchGame(final UUID gameId) throws MageException {
		try {
			fireCallback(new ClientCallback("watchGame", gameId));
		} catch (CallbackException ex) {
			logger.fatal("watchGame exception", ex);
			throw new MageException("callback failed");
		}
	}

	public void replayGame(final UUID gameId) {
		try {
			fireCallback(new ClientCallback("replayGame", gameId));
		} catch (CallbackException ex) {
			logger.fatal("replayGame exception", ex);
		}
	}

	public void ack(int messageId) {
		synchronized(ackResponse) {
			ackResponse.setAck(true);
			ackResponse.setValue(messageId);
			ackResponse.notify();
		}
	}

	public String getUsername() {
		return username;
	}

	public void ping() {
		this.lastPing = System.currentTimeMillis();
		if (logger.isTraceEnabled())
			logger.trace("Ping received from" + username + ":" + sessionId);
	}

	public boolean stillAlive() {
		return (System.currentTimeMillis() - lastPing) < 60000;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public String getHost() {
		return host;
	}
	
	public Date getConnectionTime() {
		return timeConnected;
	}
}
