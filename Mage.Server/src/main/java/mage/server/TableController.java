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

import mage.Constants.RangeOfInfluence;
import mage.Constants.TableState;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.game.GameOptions;
import mage.game.Seat;
import mage.game.Table;
import mage.game.draft.Draft;
import mage.game.draft.DraftPlayer;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentOptions;
import mage.players.Player;
import mage.server.challenge.ChallengeManager;
import mage.server.draft.DraftManager;
import mage.server.game.*;
import mage.server.tournament.TournamentFactory;
import mage.server.tournament.TournamentManager;
import org.apache.log4j.Logger;

import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableController {

	private final static Logger logger = Logger.getLogger(TableController.class);

	private UUID sessionId;
	private UUID chatId;
	private String controllerName;
	private Table table;
	private Match match;
	private MatchOptions options;
	private Tournament tournament;
	private ConcurrentHashMap<UUID, UUID> sessionPlayerMap = new ConcurrentHashMap<UUID, UUID>();

	public TableController(UUID roomId, UUID sessionId, MatchOptions options) {
		this.sessionId = sessionId;
		chatId = ChatManager.getInstance().createChatSession();
		this.options = options;
		match = GameFactory.getInstance().createMatch(options.getGameType(), options);
		Session session = SessionManager.getInstance().getSession(sessionId);
		if (session != null)
			controllerName = session.getUsername();
		else
			controllerName = "System";
		table = new Table(roomId, options.getGameType(), options.getName(), controllerName, DeckValidatorFactory.getInstance().createDeckValidator(options.getDeckType()), options.getPlayerTypes(), match);
		init();
	}

	public TableController(UUID roomId, UUID sessionId, TournamentOptions options) {
		this.sessionId = sessionId;
		chatId = ChatManager.getInstance().createChatSession();
		tournament = TournamentFactory.getInstance().createTournament(options.getTournamentType(), options);
		Session session = SessionManager.getInstance().getSession(sessionId);
		if (session != null)
			controllerName = session.getUsername();
		else
			controllerName = "System";
		table = new Table(roomId, options.getTournamentType(), options.getName(), controllerName, DeckValidatorFactory.getInstance().createDeckValidator(options.getMatchOptions().getDeckType()), options.getPlayerTypes(), tournament);
	}

	private void init() {
		match.addTableEventListener(
			new Listener<TableEvent> () {
				@Override
				public void event(TableEvent event) {
					switch (event.getEventType()) {
						case SIDEBOARD:
							sideboard(event.getPlayerId(), event.getDeck(), event.getTimeout());
							break;
						case SUBMIT_DECK:
							submitDeck(event.getPlayerId(), event.getDeck());
							break;
					}
				}
			}
		);
	}

	public synchronized boolean joinTournament(UUID sessionId, String name, String playerType, int skill) throws GameException {
		if (table.getState() != TableState.WAITING) {
			return false;
		}
		Seat seat = table.getNextAvailableSeat(playerType);
		if (seat == null) {
			throw new GameException("No available seats.");
		}
		Player player = createPlayer(name, seat.getPlayerType(), skill);
		tournament.addPlayer(player, seat.getPlayerType());
		table.joinTable(player, seat);
		logger.info("player joined " + player.getId());
		//only add human players to sessionPlayerMap
		if (seat.getPlayer().isHuman()) {
			sessionPlayerMap.put(sessionId, player.getId());
		}

		return true;
	}

	public synchronized boolean joinTable(UUID sessionId, String name, String playerType, int skill, DeckCardLists deckList) throws GameException {
		if (table.getState() != TableState.WAITING) {
			return false;
		}
		Seat seat = table.getNextAvailableSeat(playerType);
		if (seat == null) {
			throw new GameException("No available seats.");
		}
		Deck deck = Deck.load(deckList);
		if (!Main.server.isTestMode() && !validDeck(deck)) {
			throw new GameException(name + " has an invalid deck for this format");
		}
		
		Player player = createPlayer(name, seat.getPlayerType(), skill);
		match.addPlayer(player, deck);
		table.joinTable(player, seat);
		logger.info("player joined " + player.getId());
		//only add human players to sessionPlayerMap
		if (seat.getPlayer().isHuman()) {
			sessionPlayerMap.put(sessionId, player.getId());
		}

		return true;
	}

	public void addPlayer(UUID sessionId, Player player, String playerType, Deck deck) throws GameException  {
		if (table.getState() != TableState.WAITING) {
			return;
		}
		Seat seat = table.getNextAvailableSeat(playerType);
		if (seat == null) {
			throw new GameException("No available seats.");
		}
		match.addPlayer(player, deck);
		table.joinTable(player, seat);
		if (player.isHuman()) {
			sessionPlayerMap.put(sessionId, player.getId());
		}
	}

	public synchronized boolean submitDeck(UUID sessionId, DeckCardLists deckList) throws GameException {
		if (table.getState() != TableState.SIDEBOARDING && table.getState() != TableState.CONSTRUCTING) {
			return false;
		}
		Deck deck = Deck.load(deckList);
		if (!Main.server.isTestMode() && !validDeck(deck)) {
			throw new GameException("Invalid deck for this format");
		}
		submitDeck(sessionId, deck);
		return true;
	}

	private void submitDeck(UUID sessionId, Deck deck) {
		if (table.getState() == TableState.SIDEBOARDING) {
			match.submitDeck(sessionPlayerMap.get(sessionId), deck);
		}
		else {
			TournamentManager.getInstance().submitDeck(tournament.getId(), sessionId, deck);
		}
	}

	public boolean watchTable(UUID sessionId) {
		if (table.getState() != TableState.DUELING) {
			return false;
		}
		SessionManager.getInstance().getSession(sessionId).watchGame(match.getGame().getId());
		return true;
	}

	public boolean replayTable(UUID sessionId) {
		if (table.getState() != TableState.FINISHED) {
			return false;
		}
		ReplayManager.getInstance().replayGame(sessionId, table.getId());
		return true;
	}

	private boolean validDeck(Deck deck) {
		return table.getValidator().validate(deck);
	}

	private Player createPlayer(String name, String playerType, int skill) {
		Player player;
		if (options == null) {
			player = PlayerFactory.getInstance().createPlayer(playerType, name, RangeOfInfluence.ALL, skill);
		}
		else {
			player = PlayerFactory.getInstance().createPlayer(playerType, name, options.getRange(), skill);
		}
		logger.info("Player created " + player.getId());
		return player;
	}

	public synchronized void leaveTable(UUID sessionId) {
		if (table.getState() == TableState.WAITING || table.getState() == TableState.STARTING)
			table.leaveTable(sessionPlayerMap.get(sessionId));
	}

	public synchronized void startMatch(UUID sessionId) {
		if (sessionId.equals(this.sessionId)) {
			startMatch();
		}
	}

	public synchronized void startChallenge(UUID sessionId, UUID challengeId) {
		if (sessionId.equals(this.sessionId)) {
			try {
				match.startMatch();
				match.startGame();
				table.initGame();
				GameOptions options = new GameOptions();
				options.testMode = true;
//				match.getGame().setGameOptions(options);
				GameManager.getInstance().createGameSession(match.getGame(), sessionPlayerMap, table.getId(), null);
				ChallengeManager.getInstance().prepareChallenge(getPlayerId(), match);
				SessionManager sessionManager = SessionManager.getInstance();
				for (Entry<UUID, UUID> entry: sessionPlayerMap.entrySet()) {
					sessionManager.getSession(entry.getKey()).gameStarted(match.getGame().getId(), entry.getValue());
				}
			} catch (GameException ex) {
				logger.fatal(null, ex);
			}
		}
	}

	private UUID getPlayerId() throws GameException {
		UUID playerId = null;
		for (Entry<UUID, UUID> entry : sessionPlayerMap.entrySet()) {
			playerId = entry.getValue();
			break;
		}
		if (playerId == null)
			throw new GameException("Couldn't find a player in challenge mode.");
		return playerId;
	}

	public synchronized void startMatch() {
		if (table.getState() == TableState.STARTING) {
			try {
				match.startMatch();
				startGame(null);
			} catch (GameException ex) {
				logger.fatal(null, ex);
			}
		}
	}

	private void startGame(UUID choosingPlayerId) throws GameException {
		match.startGame();
		table.initGame();
		GameManager.getInstance().createGameSession(match.getGame(), sessionPlayerMap, table.getId(), choosingPlayerId);
		SessionManager sessionManager = SessionManager.getInstance();
		for (Entry<UUID, UUID> entry: sessionPlayerMap.entrySet()) {
			sessionManager.getSession(entry.getKey()).gameStarted(match.getGame().getId(), entry.getValue());
		}
	}

	public synchronized void startTournament(UUID sessionId) {
		if (sessionId.equals(this.sessionId) && table.getState() == TableState.STARTING) {
			TournamentManager.getInstance().createTournamentSession(tournament, sessionPlayerMap, table.getId());
			SessionManager sessionManager = SessionManager.getInstance();
			for (Entry<UUID, UUID> entry: sessionPlayerMap.entrySet()) {
				Session session = sessionManager.getSession(entry.getKey());
				session.tournamentStarted(tournament.getId(), entry.getValue());
			}
		}
	}

	public void startDraft(Draft draft) {
		table.initDraft();
		DraftManager.getInstance().createDraftSession(draft, sessionPlayerMap, table.getId());
		SessionManager sessionManager = SessionManager.getInstance();
		for (Entry<UUID, UUID> entry: sessionPlayerMap.entrySet()) {
			sessionManager.getSession(entry.getKey()).draftStarted(draft.getId(), entry.getValue());
		}
	}

	private void sideboard(UUID playerId, Deck deck, int timeout) {
		SessionManager sessionManager = SessionManager.getInstance();
		for (Entry<UUID, UUID> entry: sessionPlayerMap.entrySet()) {
			if (entry.getValue().equals(playerId)) {
				sessionManager.getSession(entry.getKey()).sideboard(deck, table.getId(), timeout);
				break;
			}
		}
	}

	public void construct() {
		table.construct();
	}

	public void endGame() {
		UUID choosingPlayerId = match.getChooser();
		match.endGame();
		table.endGame();
//		GameManager.getInstance().saveGame(match.getGame().getId());
		GameManager.getInstance().removeGame(match.getGame().getId());
		try {
			if (!match.isMatchOver()) {
				table.sideboard();
				match.sideboard();
				startGame(choosingPlayerId);
			}
			else {
				GamesRoomManager.getInstance().getRoom(table.getRoomId()).removeTable(sessionId, table.getId());
			}
		} catch (GameException ex) {
			logger.fatal(null, ex);
		}
	}

	public void endDraft(Draft draft) {
		for (DraftPlayer player: draft.getPlayers()) {
			tournament.getPlayer(player.getPlayer().getId()).setDeck(player.getDeck());
		}
		tournament.nextStep();
	}

	public void swapSeats(int seatNum1, int seatNum2) {
		if (table.getState() == TableState.STARTING) {
			if (seatNum1 >= 0 && seatNum2 >= 0 && seatNum1 < table.getSeats().length && seatNum2 < table.getSeats().length) {
				Player swapPlayer = table.getSeats()[seatNum1].getPlayer();
				String swapType = table.getSeats()[seatNum1].getPlayerType();
				table.getSeats()[seatNum1].setPlayer(table.getSeats()[seatNum2].getPlayer());
				table.getSeats()[seatNum1].setPlayerType(table.getSeats()[seatNum2].getPlayerType());
				table.getSeats()[seatNum2].setPlayer(swapPlayer);
				table.getSeats()[seatNum2].setPlayerType(swapType);
			}
		}
	}

	public boolean isOwner(UUID sessionId) {
		return sessionId.equals(this.sessionId);
	}

	public Table getTable() {
		return table;
	}

	public UUID getChatId() {
		return chatId;
	}

	public Match getMatch() {
		return match;
	}

}
