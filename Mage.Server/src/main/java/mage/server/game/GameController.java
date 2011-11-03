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

import java.io.BufferedOutputStream;

import mage.MageException;
import mage.server.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.zip.GZIPOutputStream;

import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.game.Game;
import mage.game.GameException;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.server.util.SystemUtil;
import mage.server.util.Splitter;
import mage.server.util.ThreadExecutor;
import mage.sets.Sets;
import mage.view.*;
import mage.view.ChatMessage.MessageColor;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameController implements GameCallback {

	private static ExecutorService gameExecutor = ThreadExecutor.getInstance().getGameExecutor();
	private final static Logger logger = Logger.getLogger(GameController.class);

	private ConcurrentHashMap<UUID, GameSession> gameSessions = new ConcurrentHashMap<UUID, GameSession>();
	private ConcurrentHashMap<UUID, GameWatcher> watchers = new ConcurrentHashMap<UUID, GameWatcher>();
	private ConcurrentHashMap<UUID, UUID> userPlayerMap;
	private UUID gameSessionId;
	private Game game;
	private UUID chatId;
	private UUID tableId;
	private UUID choosingPlayerId;
	private Future<?> gameFuture;
    private boolean useTimeout = true;

	public GameController(Game game, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId, UUID choosingPlayerId) {
		gameSessionId = UUID.randomUUID();
		this.userPlayerMap = userPlayerMap;
		chatId = ChatManager.getInstance().createChatSession();
		this.game = game;
		this.tableId = tableId;
		this.choosingPlayerId = choosingPlayerId;
        for (Player player: game.getPlayers().values()) {
            if (!player.isHuman()) {
                useTimeout = false;
                break;
            }
        }
		init();
	}

	private void init() {
		game.addTableEventListener(
			new Listener<TableEvent> () {
				@Override
				public void event(TableEvent event) {
					try {
						switch (event.getEventType()) {
							case UPDATE:
								updateGame();
								break;
							case INFO:
								ChatManager.getInstance().broadcast(chatId, "", event.getMessage(), MessageColor.BLACK);
								logger.debug(game.getId() + " " + event.getMessage());
								break;
							case REVEAL:
								revealCards(event.getMessage(), event.getCards());
								break;
							case ERROR:
								error(event.getMessage(), event.getException());
								break;
						}
					} catch (MageException ex) {
						logger.fatal("Table event listener error ", ex);
					}
				}
			}
		);
		game.addPlayerQueryEventListener(
			new Listener<PlayerQueryEvent> () {
				@Override
				public void event(PlayerQueryEvent event) {
//					logger.info(event.getPlayerId() + "--" + event.getQueryType() + "--" + event.getMessage());
					try {
						switch (event.getQueryType()) {
							case ASK:
								ask(event.getPlayerId(), event.getMessage());
								break;
							case PICK_TARGET:
								target(event.getPlayerId(), event.getMessage(), event.getCards(), event.getPerms(), event.getTargets(), event.isRequired(), event.getOptions());
								break;
							case PICK_ABILITY:
								target(event.getPlayerId(), event.getMessage(), event.getAbilities(), event.isRequired(), event.getOptions());
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
							case CHOOSE_MODE:
								chooseMode(event.getPlayerId(), event.getModes());
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
					} catch (MageException ex) {
						logger.fatal("Player event listener error ", ex);
					}
				}
			}
		);
		for (Player player: game.getPlayers().values()) {
			if (!player.isHuman()) {
				ChatManager.getInstance().broadcast(chatId, "", player.getName() + " has joined the game", MessageColor.BLACK);
			}
		}
		checkStart();
	}

	private UUID getPlayerId(UUID userId) {
		return userPlayerMap.get(userId);
	}

	public void join(UUID userId) {
		UUID playerId = userPlayerMap.get(userId);
		GameSession gameSession = new GameSession(game, userId, playerId, useTimeout);
		gameSessions.put(playerId, gameSession);
		User user = UserManager.getInstance().getUser(userId);
		gameSession.setUserData(user.getUserData());
		user.addGame(playerId, gameSession);
		logger.info("player " + playerId + " has joined game " + game.getId());
		ChatManager.getInstance().broadcast(chatId, "", game.getPlayer(playerId).getName() + " has joined the game", MessageColor.BLACK);
		checkStart();
	}

	private synchronized void startGame() {
		if (gameFuture == null) {
			for (final Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
				if (!entry.getValue().init()) {
					logger.fatal("Unable to initialize client");
					//TODO: generate client error message
					return;
				}
			}
			GameWorker worker = new GameWorker(game, choosingPlayerId, this);
			gameFuture = gameExecutor.submit(worker);
		}
	}

	private void checkStart() {
		if (allJoined()) {
			ThreadExecutor.getInstance().getCallExecutor().execute(
				new Runnable() {
					@Override
					public void run() {
						startGame();
					}
			});
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

	public void watch(UUID userId) {
		GameWatcher gameWatcher = new GameWatcher(userId, game);
		watchers.put(userId, gameWatcher);
		gameWatcher.init();
		ChatManager.getInstance().broadcast(chatId, "", " has started watching", MessageColor.BLACK);
	}
	
	public void stopWatching(UUID userId) {
		watchers.remove(userId);
		ChatManager.getInstance().broadcast(chatId, "", " has stopped watching", MessageColor.BLACK);
	}
	
	public void concede(UUID userId) {
		game.concede(getPlayerId(userId));
	}

	private void leave(UUID userId) {
		game.quit(getPlayerId(userId));
	}

	public void cheat(UUID userId, UUID playerId, DeckCardLists deckList) {
		Deck deck;
		try {
			deck = Deck.load(deckList);
			game.loadCards(deck.getCards(), playerId);
			for (Card card: deck.getCards()) {
				card.putOntoBattlefield(game, Zone.OUTSIDE, null, playerId);
			}
		} catch (GameException ex) {
			logger.warn(ex.getMessage());
		}
		addCardsForTesting(game);
		updateGame();
	}

    public boolean cheat(UUID userId, UUID playerId, String cardName) {
        Card card = Sets.findCard(cardName, true);
		if (card != null) {
            Set<Card> cards = new HashSet<Card>();
			cards.add(card);
            game.loadCards(cards, playerId);
            card.moveToZone(Zone.HAND, null, game, false);
            return true;
        } else {
            return false;
        }
	}

	public void kill(UUID userId) {
		if (userPlayerMap.containsKey(userId)) {
			gameSessions.get(userPlayerMap.get(userId)).setKilled();
			gameSessions.remove(userPlayerMap.get(userId));
			leave(userId);
			userPlayerMap.remove(userId);
		}
		if (watchers.containsKey(userId)) {
			watchers.get(userId).setKilled();
			watchers.remove(userId);
		}
	}

	public void timeout(UUID userId) {
		if (userPlayerMap.containsKey(userId)) {
			ChatManager.getInstance().broadcast(chatId, "", game.getPlayer(userPlayerMap.get(userId)).getName() + " has timed out.  Auto concede.", MessageColor.BLACK);
			concede(userId);
		}
	}

	public void endGame(final String message) throws MageException {
		for (final GameSession gameSession: gameSessions.values()) {
			gameSession.gameOver(message);
			gameSession.removeGame();
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

	public void sendPlayerUUID(UUID userId, final UUID data) {
		sendMessage(userId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).sendPlayerUUID(data);
			}
		});

	}

	public void sendPlayerString(UUID userId, final String data) {
		sendMessage(userId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).sendPlayerString(data);
			}
		});
	}

	public void sendPlayerBoolean(UUID userId, final Boolean data) {
		sendMessage(userId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).sendPlayerBoolean(data);
			}
		});

	}

	public void sendPlayerInteger(UUID userId, final Integer data) {
		sendMessage(userId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).sendPlayerInteger(data);
			}
		});

	}

	private synchronized void updateGame() {
		for (final GameSession gameSession: gameSessions.values()) {
			gameSession.update();
		}
		for (final GameWatcher gameWatcher: watchers.values()) {
			gameWatcher.update();
		}
	}

	private synchronized void ask(UUID playerId, final String question) throws MageException {
		/*if (question.equals("Do you want to take a mulligan?")) {
			System.out.println("reverted");
			for (UUID uuid : game.getOpponents(playerId)) {
				gameSessions.get(uuid).ask(question);
				informOthers(uuid);
			}
			return;
		}*/
		perform(playerId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).ask(question);
			}
		});

	}

	private synchronized void chooseAbility(UUID playerId, final Collection<? extends Ability> choices) throws MageException {
		perform(playerId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).chooseAbility(new AbilityPickerView(choices));
			}
		});
	}

	private synchronized void chooseMode(UUID playerId, final Map<UUID, String> modes) throws MageException {
		perform(playerId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).chooseAbility(new AbilityPickerView(modes));
			}
		});
	}

	private synchronized void choose(UUID playerId, final String message, final Set<String> choices) throws MageException {
		perform(playerId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).choose(message, choices);
			}
		});
	}

	private synchronized void target(UUID playerId, final String question, final Cards cards, final List<Permanent> perms, final Set<UUID> targets, final boolean required, final Map<String, Serializable> options) throws MageException {
		perform(playerId, new Command() {
			public void execute(UUID playerId) {
				if (cards != null) {
					gameSessions.get(playerId).target(question, new CardsView(cards.getCards(game)), targets, required, options);
				} else if (perms != null) {
					CardsView permsView = new CardsView();
					for (Permanent perm: perms) {
						permsView.put(perm.getId(), new PermanentView(perm, game.getCard(perm.getId())));
					}
					gameSessions.get(playerId).target(question, permsView, targets, required, options);
				} else
					gameSessions.get(playerId).target(question, new CardsView(), targets, required, options);
				}
		});

	}

	private synchronized void target(UUID playerId, final String question, final Collection<? extends Ability> abilities, final boolean required, final Map<String, Serializable> options) throws MageException {
		perform(playerId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).target(question, new CardsView(abilities, game), null, required, options);
			}
		});
	}

	private synchronized void select(final UUID playerId, final String message) throws MageException {
		perform(playerId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).select(message);
			}
		});
	}

	private synchronized void playMana(UUID playerId, final String message) throws MageException {
		perform(playerId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).playMana(message);
			}
		});
	}

	private synchronized void playXMana(UUID playerId, final String message) throws MageException {
		perform(playerId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).playXMana(message);
				}
		});
	}

	private synchronized void amount(UUID playerId, final String message, final int min, final int max) throws MageException {
		perform(playerId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).getAmount(message, min, max);
			}
		});
	}

	private synchronized void revealCards(String name, Cards cards) throws MageException {
		for (GameSession session: gameSessions.values()) {
			session.revealCards(name, new CardsView(cards.getCards(game)));
		}
	}

	private synchronized void lookAtCards(UUID playerId, final String name, final Cards cards) throws MageException {
		perform(playerId, new Command() {
			public void execute(UUID playerId) {
				gameSessions.get(playerId).revealCards(name, new CardsView(cards.getCards(game)));
			}
		}, false);
	}

	private void informOthers(UUID playerId) throws MageException {
		final String message = "Waiting for " + game.getPlayer(playerId).getName();
		for (final Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
			if (!entry.getKey().equals(playerId)) {
				entry.getValue().inform(message);
			}
		}
		for (final GameWatcher watcher: watchers.values()) {
			watcher.inform(message);
		}
	}

	private void informOthers(List<UUID> players) throws MageException {
		// first player is always original controller
		final String message = "Waiting for " + game.getPlayer(players.get(0)).getName();
		for (final Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
			boolean skip = false;
			for (UUID uuid : players) {
				if (entry.getKey().equals(uuid)) {
					skip = true;
					break;
				}
			}
			if (!skip) entry.getValue().inform(message);
		}
		for (final GameWatcher watcher: watchers.values()) {
			watcher.inform(message);
		}
	}

	private void error(String message, Exception ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(ex.toString());
        sb.append("\nServer version: ").append(Main.getVersion().toString());
        sb.append("\n");
        for (StackTraceElement e: ex.getStackTrace()) {
            sb.append(e.toString()).append("\n");
        }
		for (final Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
			entry.getValue().gameError(sb.toString());
		}
	}

	public GameView getGameView(UUID playerId) {
		return gameSessions.get(playerId).getGameView();
	}

	@Override
	public void gameResult(String result) {
		try {
			endGame(result);
		} catch (MageException ex) {
			logger.fatal("Game Result error", ex);
		}
	}

	public void saveGame() {
		try {
			OutputStream file = new FileOutputStream("saved/" + game.getId().toString() + ".game");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(new GZIPOutputStream(buffer));
			try {
				output.writeObject(game);
				output.writeObject(game.getGameStates());
			}
			finally {
				output.close();
			}
			logger.info("Saved game:" + game.getId());
		}
		catch(IOException ex) {
			logger.fatal("Cannot save game.", ex);
		}
	}

	/**
	 * Adds cards in player's hands that are specified in config/init.txt.
	 */
	private void addCardsForTesting(Game game) {
		SystemUtil.addCardsForTesting(game);
	}

	private void perform(UUID playerId, Command command) throws MageException {
		perform(playerId, command, true);
	}

	private void perform(UUID playerId, Command command, boolean informOthers) throws MageException {
		if (game.getPlayer(playerId).isGameUnderControl()) {
			if (gameSessions.containsKey(playerId))
				command.execute(playerId);
			if (informOthers) informOthers(playerId);
		} else {
			List<UUID> players = Splitter.split(game, playerId);
			for (UUID uuid : players) {
				if (gameSessions.containsKey(uuid))
					command.execute(uuid);
			}
			if (informOthers) informOthers(players);
		}
	}

	private void sendMessage(UUID userId, Command command) {
		final UUID playerId = userPlayerMap.get(userId);
		if (game.getPlayer(playerId).isGameUnderControl()) {
				// if it's your priority (or game not started yet in which case it will be null)
				// then execute only your action
				if (game.getPriorityPlayerId() == null || game.getPriorityPlayerId().equals(playerId)) {
					if (gameSessions.containsKey(playerId))
						command.execute(playerId);
				} // otherwise execute the action under other player's control
				else {
					//System.out.println("asThough: " + playerId + " " + game.getPriorityPlayerId());
					Player player = game.getPlayer(playerId);
					for (UUID controlled : player.getPlayersUnderYourControl()) {
						if (gameSessions.containsKey(controlled) && game.getPriorityPlayerId().equals(controlled))
							command.execute(controlled);
					}
				}
		} else {
			// ignore - no control over the turn
			return;
		}
	}

	interface Command {
		public void execute(UUID player);
	}
}
