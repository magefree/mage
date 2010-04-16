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
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.Constants.TableState;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.game.Game;
import mage.game.GameException;
import mage.game.GameStates;
import mage.game.Seat;
import mage.players.Player;
import mage.server.ChatManager;
import mage.server.Main;
import mage.server.SessionManager;
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TableController {

	private final static Logger logger = Logging.getLogger(TableController.class.getName());

	private UUID sessionId;
	private UUID chatId;
	private UUID gameId;
	private Table table;
	private Game game;
	private ConcurrentHashMap<UUID, UUID> sessionPlayerMap = new ConcurrentHashMap<UUID, UUID>();

	public TableController(UUID sessionId, String gameType, String deckType, List<String> playerTypes) {
		this.sessionId = sessionId;
		chatId = ChatManager.getInstance().createChatSession();
		game = GameFactory.getInstance().createGame(gameType);
		gameId = game.getId();
		table = new Table(gameType, DeckValidatorFactory.getInstance().createDeckValidator(deckType), playerTypes);
	}

	public synchronized boolean joinTable(UUID sessionId, int seatNum, String name, DeckCardLists deckList) throws GameException {
		if (table.getState() != TableState.WAITING) {
			return false;
		}
		Seat seat = table.getSeats()[seatNum];
		Deck deck = Deck.load(deckList);
		if (!Main.server.isTestMode() && !validDeck(deck)) {
			throw new GameException(name + " has an invalid deck for this format");
		}
		
		Player player = createPlayer(name, deck, seat.getPlayerType());
		table.joinTable(player, seatNum);
		logger.info("player joined " + player.getId());
		//only add human players to sessionPlayerMap
		if (table.getSeats()[seatNum].getPlayer().isHuman()) {
			sessionPlayerMap.put(sessionId, player.getId());
		}

		return true;
	}

	public boolean watchTable(UUID sessionId) {
		if (table.getState() != TableState.DUELING) {
			return false;
		}
		SessionManager.getInstance().getSession(sessionId).watchGame(game.getId());
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
		Player player = PlayerFactory.getInstance().createPlayer(playerType, name);
		logger.info("Player created " + player.getId());
		player.setDeck(deck);
		return player;
	}

	public synchronized void leaveTable(UUID sessionId) {
		if (table.getState() == TableState.WAITING)
			table.leaveTable(sessionPlayerMap.get(sessionId));
	}

	public synchronized void startGame(UUID sessionId) {
		if (sessionId.equals(this.sessionId) && table.getState() == TableState.STARTING) {
			try {
				table.initGame(game);
			} catch (GameException ex) {
				logger.log(Level.SEVERE, null, ex);
			}
			GameManager.getInstance().createGameSession(game, sessionPlayerMap, table.getId());
			SessionManager sessionManager = SessionManager.getInstance();
			for (Entry<UUID, UUID> entry: sessionPlayerMap.entrySet()) {
				sessionManager.getSession(entry.getKey()).gameStarted(game.getId(), entry.getValue());
			}
		}
	}

	public void endGame() {
		table.endGame();
		saveGame();
		GameManager.getInstance().removeGame(game.getId());
		game = null;
	}

	private void saveGame() {
		try {
			//use buffering
			OutputStream file = new FileOutputStream("saved/" + game.getId().toString() + ".game");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(game.getGameStates());
			}
			finally {
				output.close();
				logger.log(Level.SEVERE, "Saved game:" + game.getId());
			}
		}
		catch(IOException ex) {
			logger.log(Level.SEVERE, "Cannot save game.", ex);
		}
	}

	private GameStates loadGame() {
		try{
			//use buffering
			InputStream file = new FileInputStream("saved/" + gameId.toString() + ".game");
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				//deserialize the List
				GameStates gameStates = (GameStates)input.readObject();
				return gameStates;
			}
			finally {
				input.close();
			}
		}
		catch(ClassNotFoundException ex) {
			logger.log(Level.SEVERE, "Cannot load game. Class not found.", ex);
		}
		catch(IOException ex) {
			logger.log(Level.SEVERE, "Cannot load game:" + game.getId(), ex);
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
