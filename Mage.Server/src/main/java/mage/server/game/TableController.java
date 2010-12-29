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

package mage.server.game;

import mage.game.Table;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import mage.Constants.TableState;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.game.Game;
import mage.game.GameException;
import mage.game.GameStates;
import mage.game.match.Match;
import mage.game.Seat;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import mage.game.match.MatchOptions;
import mage.game.match.MatchPlayer;
import mage.players.Player;
import mage.server.ChatManager;
import mage.server.Main;
import mage.server.SessionManager;
import mage.util.CopierObjectInputStream;
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableController {

	private final static Logger logger = Logging.getLogger(TableController.class.getName());

	private UUID sessionId;
	private UUID chatId;
	private Table table;
	private Match match;
	private MatchOptions options;
	private ConcurrentHashMap<UUID, UUID> sessionPlayerMap = new ConcurrentHashMap<UUID, UUID>();

	public TableController(UUID sessionId, MatchOptions options) {
		this.sessionId = sessionId;
		chatId = ChatManager.getInstance().createChatSession();
		this.options = options;
		match = GameFactory.getInstance().createMatch(options.getGameType(), options);
		table = new Table(options.getGameType(), options.getName(), DeckValidatorFactory.getInstance().createDeckValidator(options.getDeckType()), options.getPlayerTypes());
		init();
	}

	private void init() {
		table.addTableEventListener(
			new Listener<TableEvent> () {
				@Override
				public void event(TableEvent event) {
					switch (event.getEventType()) {
						case SIDEBOARD:
							sideboard(event.getPlayerId());
							break;
						case SUBMIT_DECK:
							submitDeck(event.getPlayerId(), event.getDeck());
							break;
					}
				}
			}
		);
	}

	public synchronized boolean joinTable(UUID sessionId, String name, DeckCardLists deckList) throws GameException {
		if (table.getState() != TableState.WAITING) {
			return false;
		}
		Seat seat = table.getNextAvailableSeat();
		if (seat == null) {
			throw new GameException("No available seats.");
		}
		Deck deck = Deck.load(deckList);
		if (!Main.server.isTestMode() && !validDeck(deck)) {
			throw new GameException(name + " has an invalid deck for this format");
		}
		
		Player player = createPlayer(name, deck, seat.getPlayerType());
		match.addPlayer(player, deck);
		table.joinTable(player, seat);
		logger.info("player joined " + player.getId());
		//only add human players to sessionPlayerMap
		if (seat.getPlayer().isHuman()) {
			sessionPlayerMap.put(sessionId, player.getId());
		}

		return true;
	}

	public synchronized boolean submitDeck(UUID sessionId, DeckCardLists deckList) throws GameException {
		if (table.getState() != TableState.SIDEBOARDING) {
			return false;
		}
		MatchPlayer player = match.getPlayer(sessionPlayerMap.get(sessionId));
		Deck deck = Deck.load(deckList);
		if (!Main.server.isTestMode() && !validDeck(deck)) {
			throw new GameException(player.getPlayer().getName() + " has an invalid deck for this format");
		}
		submitDeck(sessionPlayerMap.get(sessionId), deck);
		return true;
	}

	private void submitDeck(UUID playerId, Deck deck) {
		MatchPlayer player = match.getPlayer(playerId);
		player.submitDeck(deck);
	}

	public boolean watchTable(UUID sessionId) {
		if (table.getState() != TableState.DUELING) {
			return false;
		}
		SessionManager.getInstance().getSession(sessionId).watchGame(match.getGame().getId());
		return true;
	}

	public GameReplay createReplay() {
		if (table.getState() == TableState.FINISHED) {
			return new GameReplay(loadGame());
		}
		return null;
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

	private Player createPlayer(String name, Deck deck, String playerType) {
		Player player = PlayerFactory.getInstance().createPlayer(playerType, name, deck, options.getRange());
		logger.info("Player created " + player.getId());
		return player;
	}

	public synchronized void leaveTable(UUID sessionId) {
		if (table.getState() == TableState.WAITING)
			table.leaveTable(sessionPlayerMap.get(sessionId));
	}

	public synchronized void startMatch(UUID sessionId) {
		if (sessionId.equals(this.sessionId) && table.getState() == TableState.STARTING) {
			try {
				match.startMatch();
				startGame(null);
			} catch (GameException ex) {
				logger.log(Level.SEVERE, null, ex);
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

	private void sideboard() {
		table.sideboard();
		for (MatchPlayer player: match.getPlayers()) {
			player.setSideboarding();
			player.getPlayer().sideboard(table);
		}
		while (!match.isDoneSideboarding()){}
	}

	private void sideboard(UUID playerId) {
		SessionManager sessionManager = SessionManager.getInstance();
		for (Entry<UUID, UUID> entry: sessionPlayerMap.entrySet()) {
			if (entry.getValue().equals(playerId)) {
				MatchPlayer player = match.getPlayer(entry.getValue());
				sessionManager.getSession(entry.getKey()).sideboard(player.getDeck(), table.getId());
				break;
			}
		}
	}

	public void endGame() {
		UUID choosingPlayerId = match.getChooser();
		match.endGame();
		table.endGame();
		saveGame();
		GameManager.getInstance().removeGame(match.getGame().getId());
		try {
			if (!match.isMatchOver()) {
				sideboard();
				startGame(choosingPlayerId);
			}
		} catch (GameException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		endMatch();
	}

	public void endMatch() {
		match = null;
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

	private void saveGame() {
		try {
			OutputStream file = new FileOutputStream("saved/" + match.getGame().getId().toString() + ".game");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(new GZIPOutputStream(buffer));
			try {
				output.writeObject(match.getGame());
				output.writeObject(match.getGame().getGameStates());
			}
			finally {
				output.close();
			}
			logger.log(Level.INFO, "Saved game:" + match.getGame().getId());
		}
		catch(IOException ex) {
			logger.log(Level.SEVERE, "Cannot save game.", ex);
		}
	}

	private Game loadGame() {
		try{
			InputStream file = new FileInputStream("saved/" + match.getGame().toString() + ".game");
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new CopierObjectInputStream(Main.classLoader, new GZIPInputStream(buffer));
			try {
				Game game = (Game)input.readObject();
				GameStates states = (GameStates)input.readObject();
				game.loadGameStates(states);
				return game;
			}
			finally {
				input.close();
			}
		}
		catch(ClassNotFoundException ex) {
			logger.log(Level.SEVERE, "Cannot load game. Class not found.", ex);
		}
		catch(IOException ex) {
			logger.log(Level.SEVERE, "Cannot load game:" + match.getGame().getId(), ex);
		}
		return null;
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

}
