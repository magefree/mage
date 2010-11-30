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

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.game.Game;
import mage.game.GameException;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.players.Player;
import mage.server.ChatManager;
import mage.server.util.ThreadExecutor;
import mage.sets.Sets;
import mage.util.Logging;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.GameView;
import mage.view.ChatMessage.MessageColor;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameController implements GameCallback {

	private static ExecutorService gameExecutor = ThreadExecutor.getInstance().getGameExecutor();
	private final static Logger logger = Logging.getLogger(GameController.class.getName());
	public static final String INIT_FILE_PATH = "config" + File.separator + "init.txt";

	private ConcurrentHashMap<UUID, GameSession> gameSessions = new ConcurrentHashMap<UUID, GameSession>();
	private ConcurrentHashMap<UUID, GameWatcher> watchers = new ConcurrentHashMap<UUID, GameWatcher>();
	private ConcurrentHashMap<UUID, UUID> sessionPlayerMap;
	private UUID gameSessionId;
	private Game game;
	private UUID chatId;
	private UUID tableId;
	private Future<?> gameFuture;


	public GameController(Game game, ConcurrentHashMap<UUID, UUID> sessionPlayerMap, UUID tableId) {
		gameSessionId = UUID.randomUUID();
		this.sessionPlayerMap = sessionPlayerMap;
		chatId = ChatManager.getInstance().createChatSession();
		this.game = game;
		this.tableId = tableId;
		init();
	}

	private void init() {
		game.addTableEventListener(
			new Listener<TableEvent> () {
				@Override
				public void event(TableEvent event) {
					switch (event.getEventType()) {
						case UPDATE:
							updateGame();
							break;
						case INFO:
							ChatManager.getInstance().broadcast(chatId, "", event.getMessage(), MessageColor.BLACK);
							logger.finest(game.getId() + " " + event.getMessage());
							break;
						case REVEAL:
							revealCards(event.getMessage(), event.getCards());
							break;
					}
				}
			}
		);
		game.addPlayerQueryEventListener(
			new Listener<PlayerQueryEvent> () {
				@Override
				public void event(PlayerQueryEvent event) {
//					logger.info(event.getPlayerId() + "--" + event.getQueryType() + "--" + event.getMessage());
					switch (event.getQueryType()) {
						case ASK:
							ask(event.getPlayerId(), event.getMessage());
							break;
						case PICK_TARGET:
							target(event.getPlayerId(), event.getMessage(), event.getCards(), event.isRequired());
							break;
						case PICK_ABILITY:
							target(event.getPlayerId(), event.getMessage(), event.getAbilities(), event.isRequired());
							break;
						case SELECT:
							select(event.getPlayerId(), event.getMessage());
							break;
						case PLAY_MANA:
							playMana(event.getPlayerId(), event.getMessage());
							break;
						case PLAY_X_MANA:
							playXMana(event.getPlayerId(), event.getMessage());
							break;
						case CHOOSE_ABILITY:
							chooseAbility(event.getPlayerId(), event.getAbilities());
							break;
						case CHOOSE:
							choose(event.getPlayerId(), event.getMessage(), event.getChoices());
							break;
						case AMOUNT:
							amount(event.getPlayerId(), event.getMessage(), event.getMin(), event.getMax());
							break;
						case LOOK:
							lookAtCards(event.getPlayerId(), event.getMessage(), event.getCards());
							break;
					}
				}
			}
		);
	}

	private UUID getPlayerId(UUID sessionId) {
		return sessionPlayerMap.get(sessionId);
	}

	public void join(UUID sessionId) {
		UUID playerId = sessionPlayerMap.get(sessionId);
		GameSession gameSession = new GameSession(game, sessionId, playerId);
		gameSessions.put(playerId, gameSession);
		logger.info("player " + playerId + " has joined game " + game.getId());
		ChatManager.getInstance().broadcast(chatId, "", game.getPlayer(playerId).getName() + " has joined the game", MessageColor.BLACK);
		if (allJoined()) {
			ThreadExecutor.getInstance().getRMIExecutor().execute(
				new Runnable() {
					@Override
					public void run() {
						startGame();
					}
			});
		}
	}

	private synchronized void startGame() {
		if (gameFuture == null) {
			for (final Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
				if (!entry.getValue().init(getGameView(entry.getKey()))) {
					logger.severe("Unable to initialize client");
					//TODO: generate client error message
					return;
				}
			}
			GameWorker worker = new GameWorker(game, this);
			gameFuture = gameExecutor.submit(worker);
		}
	}

	private boolean allJoined() {
		for (Player player: game.getPlayers().values()) {
			if (player.isHuman() && gameSessions.get(player.getId()) == null) {
				return false;
			}
		}
		return true;
	}

	public void watch(UUID sessionId) {
		GameWatcher gameWatcher = new GameWatcher(sessionId, game.getId());
		watchers.put(sessionId, gameWatcher);
		gameWatcher.init(getGameView());
		ChatManager.getInstance().broadcast(chatId, "", " has started watching", MessageColor.BLACK);
	}
	
	public void stopWatching(UUID sessionId) {
		watchers.remove(sessionId);
		ChatManager.getInstance().broadcast(chatId, "", " has stopped watching", MessageColor.BLACK);
	}
	
	public void concede(UUID sessionId) {
		game.concede(getPlayerId(sessionId));
	}

	private void leave(UUID sessionId) {
		game.quit(getPlayerId(sessionId));
	}

	public void cheat(UUID sessionId, UUID playerId, DeckCardLists deckList) {
		Deck deck;
		try {
			deck = Deck.load(deckList);
			game.loadCards(deck.getCards(), playerId);
			for (Card card: deck.getCards()) {
				card.putOntoBattlefield(game, Zone.OUTSIDE, null, playerId);
			}
		} catch (GameException ex) {
			logger.warning(ex.getMessage());
		}
		addCardsForTesting(game);
		updateGame();
	}

	public void kill(UUID sessionId) {
		if (sessionPlayerMap.containsKey(sessionId)) {
			gameSessions.get(sessionPlayerMap.get(sessionId)).setKilled();
			gameSessions.remove(sessionPlayerMap.get(sessionId));
			leave(sessionId);
			sessionPlayerMap.remove(sessionId);
		}
		if (watchers.containsKey(sessionId)) {
			watchers.get(sessionId).setKilled();
			watchers.remove(sessionId);
		}
	}

	public void timeout(UUID sessionId) {
		if (sessionPlayerMap.containsKey(sessionId)) {
			ChatManager.getInstance().broadcast(chatId, "", game.getPlayer(sessionPlayerMap.get(sessionId)).getName() + " has timed out.  Auto concede.", MessageColor.BLACK);
			concede(sessionId);
		}
	}

	public void endGame(final String message) {
		for (final GameSession gameSession: gameSessions.values()) {
			gameSession.gameOver(message);
		}
		for (final GameWatcher gameWatcher: watchers.values()) {
			gameWatcher.gameOver(message);
		}
		TableManager.getInstance().endGame(tableId);
	}

	public UUID getSessionId() {
		return this.gameSessionId;
	}

	public UUID getChatId() {
		return chatId;
	}

	public void sendPlayerUUID(UUID sessionId, UUID data) {
		gameSessions.get(sessionPlayerMap.get(sessionId)).sendPlayerUUID(data);
	}

	public void sendPlayerString(UUID sessionId, String data) {
		gameSessions.get(sessionPlayerMap.get(sessionId)).sendPlayerString(data);
	}

	public void sendPlayerBoolean(UUID sessionId, Boolean data) {
		gameSessions.get(sessionPlayerMap.get(sessionId)).sendPlayerBoolean(data);
	}

	public void sendPlayerInteger(UUID sessionId, Integer data) {
		gameSessions.get(sessionPlayerMap.get(sessionId)).sendPlayerInteger(data);
	}

	private synchronized void updateGame() {

		for (final Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
			entry.getValue().update(getGameView(entry.getKey()));
		}
		for (final GameWatcher gameWatcher: watchers.values()) {
			gameWatcher.update(getGameView());
		}
	}

	private synchronized void ask(UUID playerId, String question) {
		if (gameSessions.containsKey(playerId))
			gameSessions.get(playerId).ask(question, getGameView(playerId));
		informOthers(playerId);
	}

	private synchronized void chooseAbility(UUID playerId, Collection<? extends Ability> choices) {
		if (gameSessions.containsKey(playerId))
			gameSessions.get(playerId).chooseAbility(new AbilityPickerView(choices));
		informOthers(playerId);
	}

	private synchronized void choose(UUID playerId, String message, Set<String> choices) {
		if (gameSessions.containsKey(playerId))
			gameSessions.get(playerId).choose(message, choices);
		informOthers(playerId);
	}

	private synchronized void target(UUID playerId, String question, Cards cards, boolean required) {
		if (gameSessions.containsKey(playerId)) {
			if (cards != null)
				gameSessions.get(playerId).target(question, new CardsView(cards.getCards(game)), required, getGameView(playerId));
			else
				gameSessions.get(playerId).target(question, new CardsView(), required, getGameView(playerId));
		}
		informOthers(playerId);
	}

	private synchronized void target(UUID playerId, String question, Collection<? extends Ability> abilities, boolean required) {
		if (gameSessions.containsKey(playerId))
			gameSessions.get(playerId).target(question, new CardsView(abilities, game.getState()), required, getGameView(playerId));
		informOthers(playerId);
	}

	private synchronized void select(UUID playerId, String message) {
		if (gameSessions.containsKey(playerId))
			gameSessions.get(playerId).select(message, getGameView(playerId));
		informOthers(playerId);
	}

	private synchronized void playMana(UUID playerId, String message) {
		if (gameSessions.containsKey(playerId))
			gameSessions.get(playerId).playMana(message, getGameView(playerId));
		informOthers(playerId);
	}

	private synchronized void playXMana(UUID playerId, String message) {
		if (gameSessions.containsKey(playerId))
			gameSessions.get(playerId).playXMana(message, getGameView(playerId));
		informOthers(playerId);
	}

	private synchronized void amount(UUID playerId, String message, int min, int max) {
		if (gameSessions.containsKey(playerId))
			gameSessions.get(playerId).getAmount(message, min, max);
		informOthers(playerId);
	}

	private synchronized void revealCards(String name, Cards cards) {
		for (GameSession session: gameSessions.values()) {
			session.revealCards(name, new CardsView(cards.getCards(game)));
		}
	}

	private synchronized void lookAtCards(UUID playerId, String name, Cards cards) {
		if (gameSessions.containsKey(playerId))
			gameSessions.get(playerId).revealCards(name, new CardsView(cards.getCards(game)));
	}

	private void informOthers(UUID playerId) {
		final String message = "Waiting for " + game.getPlayer(playerId).getName();
		for (final Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
			if (!entry.getKey().equals(playerId)) {
				entry.getValue().inform(message, getGameView(entry.getKey()));
			}
		}
		for (final GameWatcher watcher: watchers.values()) {
			watcher.inform(message, getGameView());
		}
	}

	private GameView getGameView() {
		return new GameView(game.getState(), game);
	}

	private GameView getGameView(UUID playerId) {
		GameView gameView = new GameView(game.getState(), game);
		gameView.setHand(new CardsView(game.getPlayer(playerId).getHand().getCards(game)));
		return gameView;
	}

	@Override
	public void gameResult(String result) {
		endGame(result);
	}

	/**
	 * Replaces cards in player's hands by specified in config/init.txt.<br/>
	 * <br/>
	 * <b>Implementation note:</b><br/>
	 * 1. Read init.txt line by line<br/>
	 * 2. Parse line using the following format: line ::= <zone>:<nickname>:<card name>:<amount><br/>
	 * 3. If zone equals to 'hand', add card to player's library<br/>
	 *   3a. Then swap added card with any card in player's hand<br/>
	 *   3b. Parse next line (go to 2.), If EOF go to 4.<br/>
	 * 4. Log message to all players that cards were added (to prevent unfair play).<br/>  
	 * 5. Exit<br/>
	 */
	private void addCardsForTesting(Game game) {
		try {
			File f = new File(INIT_FILE_PATH);
			Pattern pattern = Pattern.compile("([a-zA-Z]*):([\\w]*):([a-zA-Z ,.!'\\d]*):([\\d]*)");
			if (!f.exists()) {
				logger.warning("Couldn't find init file: " + INIT_FILE_PATH);
				return;
			}
			
			//logger.info("Parsing init.txt for player : " + player.getName());
			
			Scanner scanner = new Scanner(f);
			try {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine().trim();
					if (line.startsWith("#")) continue;
					Matcher m = pattern.matcher(line);
					if (m.matches()) {

						String zone = m.group(1);
						String nickname = m.group(2);

						Player player = findPlayer(game, nickname);
						if (player != null) {
							Zone gameZone;
							if ("hand".equalsIgnoreCase(zone)) {
								gameZone = Zone.HAND;
							} else if ("battlefield".equalsIgnoreCase(zone)) {
								gameZone = Zone.BATTLEFIELD;
							} else if ("graveyard".equalsIgnoreCase(zone)) {
								gameZone = Zone.GRAVEYARD;
							} else if ("library".equalsIgnoreCase(zone)) {
								gameZone = Zone.LIBRARY;
							} else {
								continue; // go parse next line
							}

							String cardName = m.group(3);
							Integer amount = Integer.parseInt(m.group(4));
							for (int i = 0; i < amount; i++) {
								String clazz = Sets.findCard(cardName);
								if (clazz != null) {
									Card card = CardImpl.createCard(clazz);
									if (card != null) {
										Set<Card> cards = new HashSet<Card>();
										cards.add(card);
										game.loadCards(cards, player.getId());
										swapWithAnyCard(game, player, card, gameZone);
									} else {
										logger.severe("Couldn't create a card: " + cardName);
									}
								} else {
									logger.severe("Couldn't find a card: " + cardName);
								}
							}
						} else {
							logger.warning("Was skipped: " + line);
						}
					} else {
						logger.warning("Init string wasn't parsed: " + line);
					}
				}
			}
			finally {
				scanner.close();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
	}
	
	/**
	 * Swap cards between specified card from library and any hand card.
	 * 
	 * @param game
	 * @param card Card to put to player's hand
	 */
	private void swapWithAnyCard(Game game, Player player, Card card, Zone zone) {
		if (zone.equals(Zone.BATTLEFIELD)) {
			card.putOntoBattlefield(game, Zone.OUTSIDE, null, player.getId());
		} else {
			card.moveToZone(zone, null, game, false);
		}
		logger.info("Added card to player's " + zone.toString() + ": " + card.getName() +", player = " + player.getName());
	}

	private Player findPlayer(Game game, String name) {
		for (Player player: game.getPlayers().values()) {
			if (player.getName().equals(name))
				return player;
		}
		return null;
	}
}
