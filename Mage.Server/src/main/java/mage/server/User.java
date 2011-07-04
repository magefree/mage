/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.cards.decks.Deck;
import mage.interfaces.callback.ClientCallback;
import mage.server.game.GameManager;
import mage.server.game.GameSession;
import mage.view.TableClientMessage;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class User {

	public enum UserState {
		Created, Connected, Disconnected, Reconnected;
	}
	
	private UUID userId = UUID.randomUUID();
	private String userName;
	private String sessionId = "";
	private String host;
	private Date connectionTime = new Date();
	private UserState userState;
 	private Map<UUID, GameSession> gameSessions = new HashMap<UUID, GameSession>();
	
	public User(String userName, String host) {
		this.userName = userName;
		this.host = host;
		this.userState = UserState.Created;
	}
	
	public String getName() {
		return userName;
	}
	
	public UUID getId() {
		return userId;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
		if (sessionId.isEmpty())
			userState = UserState.Disconnected;
		else if (userState == UserState.Created) 
			userState = UserState.Connected;
		else {
			userState = UserState.Reconnected;
			reconnect();
		}
	}
	
	public boolean isConnected() {
		return userState == UserState.Connected || userState == UserState.Reconnected;
	}
	
	public Date getConnectionTime() {
		return connectionTime;
	}

	public synchronized void fireCallback(final ClientCallback call) {
		if (isConnected()) {
			Session session = SessionManager.getInstance().getSession(sessionId);
			session.fireCallback(call);		
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

	private void reconnect() {
		for (Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
			gameStarted(entry.getValue().getGameId(), entry.getKey());
			entry.getValue().init();
			GameManager.getInstance().sendPlayerString(entry.getValue().getGameId(), userId, "");
		}
	}

	public void addGame(UUID playerId, GameSession gameSession) {
		gameSessions.put(playerId, gameSession);
	}
	
	public void removeGame(UUID playerId) {
		gameSessions.remove(playerId);
	}

}
