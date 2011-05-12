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
import java.util.logging.Level;
import java.util.UUID;
import mage.cards.decks.Deck;
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
	private int messageId = 0;
	private String ackMessage;
	private long lastPing;
	private final CallbackServerSession callback = new CallbackServerSession();

	public Session(String userName, UUID clientId) {
		sessionId = UUID.randomUUID();
		this.username = userName;
		this.clientId = clientId;
	}

	public UUID getId() {
		return sessionId;
	}

	public UUID getClientId() {
		return clientId;
	}

	public void kill() {
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

	public synchronized void fireCallback(final ClientCallback call) {
		call.setMessageId(messageId++);
		if (logger.isDebugEnabled())
			logger.debug(sessionId + " - " + call.getMessageId() + " - " + call.getMethod());
		try {
			callback.setCallback(call);
		} catch (InterruptedException ex) {
			logger.fatal("Session fireCallback error", ex);
		}
	}

	public void gameStarted(final UUID gameId, final UUID playerId) {
		fireCallback(new ClientCallback("startGame", gameId, new TableClientMessage(gameId, playerId)));
	}

	public void draftStarted(final UUID draftId, final UUID playerId) {
		fireCallback(new ClientCallback("startDraft", draftId, new TableClientMessage(draftId, playerId)));
	}

	public void tournamentStarted(final UUID tournamentId, final UUID playerId) {
		fireCallback(new ClientCallback("startTournament", tournamentId, new TableClientMessage(tournamentId, playerId)));
	}

	public void sideboard(final Deck deck, final UUID tableId, final int time) {
		fireCallback(new ClientCallback("sideboard", tableId, new TableClientMessage(deck, tableId, time)));
	}

	public void construct(final Deck deck, final UUID tableId, final int time) {
		fireCallback(new ClientCallback("construct", tableId, new TableClientMessage(deck, tableId, time)));
	}

	public void watchGame(final UUID gameId) {
		fireCallback(new ClientCallback("watchGame", gameId));
	}

	public void replayGame(final UUID gameId) {
		fireCallback(new ClientCallback("replayGame", gameId));
	}

	public void ack(String message) {
		this.ackMessage = message;
	}

	public String getAckMessage() {
		return ackMessage;
	}

	public void clearAck() {
		this.ackMessage = "";
	}

	public String getUsername() {
		return username;
	}

	public void ping() {
		this.lastPing = System.currentTimeMillis();
	}

	public boolean stillAlive() {
		return (System.currentTimeMillis() - lastPing) < 60000;
	}

}
