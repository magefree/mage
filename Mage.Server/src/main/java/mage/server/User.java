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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.cards.decks.Deck;
import mage.game.Table;
import mage.interfaces.callback.ClientCallback;
import mage.server.draft.DraftSession;
import mage.server.game.GameManager;
import mage.server.game.GameSession;
import mage.server.tournament.TournamentSession;
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
	private Date lastActivity = new Date();
	private UserState userState;
    private Map<UUID, Table> tables = new HashMap<UUID, Table>();
 	private Map<UUID, GameSession> gameSessions = new HashMap<UUID, GameSession>();
	private Map<UUID, DraftSession> draftSessions = new HashMap<UUID, DraftSession>();
	private Map<UUID, TournamentSession> tournamentSessions = new HashMap<UUID, TournamentSession>();
    private Map<UUID, TournamentSession> constructing = new HashMap<UUID, TournamentSession>();
    private Map<UUID, Deck> sideboarding = new HashMap<UUID, Deck>();
	
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

	public void joinedTable(final UUID roomId, final UUID tableId, boolean isTournament) {
		fireCallback(new ClientCallback("joinedTable", tableId, new TableClientMessage(roomId, tableId, isTournament)));
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
        sideboarding.put(tableId, deck);
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

	public void sendPlayerUUID(final UUID gameId, final UUID data) {
		lastActivity = new Date();
		GameManager.getInstance().sendPlayerUUID(gameId, userId, data);
	}

	public void sendPlayerString(final UUID gameId, final String data) {
		lastActivity = new Date();
		GameManager.getInstance().sendPlayerString(gameId, userId, data);
	}

	public void sendPlayerBoolean(final UUID gameId, final Boolean data)  {
		lastActivity = new Date();
		GameManager.getInstance().sendPlayerBoolean(gameId, userId, data);
	}

	public void sendPlayerInteger(final UUID gameId, final Integer data) {
		lastActivity = new Date();
		GameManager.getInstance().sendPlayerInteger(gameId, userId, data);
	}

	public boolean isExpired(Date expired) {
		return userState == UserState.Disconnected && lastActivity.before(expired);
	}
	
	private void reconnect() {
		for (Entry<UUID, Table> entry: tables.entrySet()) {
            joinedTable(entry.getValue().getRoomId(), entry.getValue().getId(), entry.getValue().isTournament());
		}
		for (Entry<UUID, TournamentSession> entry: tournamentSessions.entrySet()) {
			tournamentStarted(entry.getValue().getTournamentId(), entry.getKey());
			entry.getValue().init();
			entry.getValue().update();
		}
		for (Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
			gameStarted(entry.getValue().getGameId(), entry.getKey());
			entry.getValue().init();
			GameManager.getInstance().sendPlayerString(entry.getValue().getGameId(), userId, "");
		}
		for (Entry<UUID, DraftSession> entry: draftSessions.entrySet()) {
			draftStarted(entry.getValue().getDraftId(), entry.getKey());
			entry.getValue().init();
			entry.getValue().update();
		}
        for (Entry<UUID, TournamentSession> entry: constructing.entrySet()) {
            entry.getValue().construct(0);
        }
        for (Entry<UUID, Deck> entry: sideboarding.entrySet()) {
            int remaining = TableManager.getInstance().getController(entry.getKey()).getRemainingTime();
            sideboard(entry.getValue(), entry.getKey(), remaining);
        }
	}

	public void addGame(UUID playerId, GameSession gameSession) {
		gameSessions.put(playerId, gameSession);
	}
	
	public void removeGame(UUID playerId) {
		gameSessions.remove(playerId);
	}
	
	public void addDraft(UUID playerId, DraftSession draftSession) {
		draftSessions.put(playerId, draftSession);
	}
	
	public void removeDraft(UUID playerId) {
		draftSessions.remove(playerId);
	}
	
	public void addTournament(UUID playerId, TournamentSession tournamentSession) {
		tournamentSessions.put(playerId, tournamentSession);
	}
	
	public void removeTournament(UUID playerId) {
		tournamentSessions.remove(playerId);
	}
	
    public void addTable(UUID playerId, Table table) {
        tables.put(playerId, table);
    }
    
    public void removeTable(UUID playerId) {
        tables.remove(playerId);
    }

    public void addConstructing(UUID playerId, TournamentSession tournamentSession) {
        constructing.put(playerId, tournamentSession);
    }
    
    public void removeConstructing(UUID playerId) {
        constructing.remove(playerId);
    }
        
    public void removeSideboarding(UUID tableId) {
        sideboarding.remove(tableId);
    }

    public void kill() {
		for (GameSession session: gameSessions.values()) {
			session.kill();
		}
		for (DraftSession session: draftSessions.values()) {
			session.setKilled();
		}
		for (TournamentSession session: tournamentSessions.values()) {
			session.setKilled();
		}
		for (Entry<UUID, Table> entry: tables.entrySet()) {
			entry.getValue().leaveTable(entry.getKey());
            if (TableManager.getInstance().isTableOwner(entry.getValue().getId(), userId)) {
                TableManager.getInstance().removeTable(userId, entry.getValue().getId());
            }
		}
	}

}
