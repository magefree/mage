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

import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.cards.decks.Deck;
import mage.game.GameException;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.match.MatchOptions;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentPairing;
import mage.game.tournament.TournamentPlayer;
import mage.server.ChatManager;
import mage.server.TableManager;
import mage.server.util.ThreadExecutor;
import mage.util.Logging;
import mage.view.ChatMessage.MessageColor;
import mage.view.TournamentView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentController {

	private final static Logger logger = Logging.getLogger(TournamentController.class.getName());

	private UUID sessionId;
	private UUID chatId;
	private UUID tableId;
	private Tournament tournament;
	private ConcurrentHashMap<UUID, UUID> sessionPlayerMap = new ConcurrentHashMap<UUID, UUID>();
	private ConcurrentHashMap<UUID, TournamentSession> tournamentSessions = new ConcurrentHashMap<UUID, TournamentSession>();

	private static final int CONSTRUCT_TIME = 600;

	public TournamentController(Tournament tournament, ConcurrentHashMap<UUID, UUID> sessionPlayerMap, UUID tableId) {
		sessionId = UUID.randomUUID();
		this.sessionPlayerMap = sessionPlayerMap;
		chatId = ChatManager.getInstance().createChatSession();
		this.tournament = tournament;
		this.tableId = tableId;
		init();
	}

	private void init() {
		tournament.addTableEventListener(
			new Listener<TableEvent> () {
				@Override
				public void event(TableEvent event) {
					switch (event.getEventType()) {
						case INFO:
							ChatManager.getInstance().broadcast(chatId, "", event.getMessage(), MessageColor.BLACK);
							logger.finest(tournament.getId() + " " + event.getMessage());
							break;
						case START_DRAFT:
							startDraft(event.getDraft());
							break;
						case START_MATCH:
							startMatch(event.getPair(), event.getMatchOptions());
							break;
						case SUBMIT_DECK:
							submitDeck(event.getPlayerId(), event.getDeck());
							break;
					}
				}
			}
		);
		tournament.addPlayerQueryEventListener(
			new Listener<PlayerQueryEvent> () {
				@Override
				public void event(PlayerQueryEvent event) {
					switch (event.getQueryType()) {
						case CONSTRUCT:
							construct(event.getPlayerId(), event.getDeck(), event.getMax());
							break;
					}
				}
			}
		);
		for (TournamentPlayer player: tournament.getPlayers()) {
			if (!player.getPlayer().isHuman()) {
				player.setJoined();
				logger.info("player " + player.getPlayer().getId() + " has joined tournament " + tournament.getId());
				ChatManager.getInstance().broadcast(chatId, "", player.getPlayer().getName() + " has joined the tournament", MessageColor.BLACK);
			}
		}
		checkStart();
	}

	public synchronized void join(UUID sessionId) {
		UUID playerId = sessionPlayerMap.get(sessionId);
		TournamentSession tournamentSession = new TournamentSession(tournament, sessionId, tableId, playerId);
		tournamentSessions.put(playerId, tournamentSession);
		TournamentPlayer player = tournament.getPlayer(playerId);
		player.setJoined();
		logger.info("player " + playerId + " has joined tournament " + tournament.getId());
		ChatManager.getInstance().broadcast(chatId, "", player.getPlayer().getName() + " has joined the tournament", MessageColor.BLACK);
		checkStart();
	}

	private void checkStart() {
		if (allJoined()) {
			ThreadExecutor.getInstance().getRMIExecutor().execute(
				new Runnable() {
					@Override
					public void run() {
						startTournament();
					}
			});
		}
	}

	private boolean allJoined() {
		if (!tournament.allJoined())
			return false;
		for (TournamentPlayer player: tournament.getPlayers()) {
			if (player.getPlayer().isHuman() && tournamentSessions.get(player.getPlayer().getId()) == null) {
				return false;
			}
		}
		return true;
	}

	private synchronized void startTournament() {
		for (final Entry<UUID, TournamentSession> entry: tournamentSessions.entrySet()) {
			if (!entry.getValue().init(getTournamentView())) {
				logger.severe("Unable to initialize client");
				//TODO: generate client error message
				return;
			}
		}
		tournament.nextStep();
	}

	private void startMatch(TournamentPairing pair, MatchOptions matchOptions) {
		try {
			TableManager tableManager = TableManager.getInstance();
			Table table = tableManager.createTable(sessionId, matchOptions);
			TournamentPlayer player1 = pair.getPlayer1();
			TournamentPlayer player2 = pair.getPlayer2();
			tableManager.addPlayer(getPlayerSessionId(player1.getPlayer().getId()), table.getId(), player1.getPlayer(), player1.getPlayerType(), player1.getDeck());
			tableManager.addPlayer(getPlayerSessionId(player2.getPlayer().getId()), table.getId(), player2.getPlayer(), player2.getPlayerType(), player2.getDeck());
			tableManager.startMatch(sessionId, null, table.getId());
			pair.setMatch(tableManager.getMatch(table.getId()));
		} catch (GameException ex) {
			Logger.getLogger(TournamentController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void startDraft(Draft draft) {
		TableManager.getInstance().startDraft(tableId, draft);
	}

	private synchronized void construct(UUID sessionId, Deck deck, int timeout) {
		if (tournamentSessions.containsKey(sessionId))
			tournamentSessions.get(sessionId).construct(deck, timeout);
	}


	public void submitDeck(UUID sessionId, Deck deck) {
		tournamentSessions.get(sessionPlayerMap.get(sessionId)).submitDeck(deck);
	}

	public void timeout(UUID sessionId) {
		if (sessionPlayerMap.containsKey(sessionId)) {
			TournamentPlayer player = tournament.getPlayer(sessionPlayerMap.get(sessionId));
			tournament.autoSubmit(sessionPlayerMap.get(sessionId), player.getDeck());
		}
	}

	public UUID getSessionId() {
		return this.sessionId;
	}

	public UUID getChatId() {
		return chatId;
	}

	public void kill(UUID sessionId) {
		if (sessionPlayerMap.containsKey(sessionId)) {
			tournamentSessions.get(sessionPlayerMap.get(sessionId)).setKilled();
			tournamentSessions.remove(sessionPlayerMap.get(sessionId));
			leave(sessionId);
			sessionPlayerMap.remove(sessionId);
		}
	}

	private void leave(UUID sessionId) {
		tournament.leave(getPlayerId(sessionId));
	}

	private UUID getPlayerId(UUID sessionId) {
		return sessionPlayerMap.get(sessionId);
	}

	private UUID getPlayerSessionId(UUID playerId) {
		for (Entry<UUID, UUID> entry: sessionPlayerMap.entrySet()) {
			if (entry.getValue().equals(playerId))
				return entry.getKey();
		}
		return null;
	}

	public TournamentView getTournamentView() {
		return new TournamentView(tournament);
	}

}
