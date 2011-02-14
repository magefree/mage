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

package mage.game;

import mage.Constants.*;
import mage.MageObject;
import mage.abilities.*;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffects;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.*;
import mage.game.combat.Combat;
import mage.game.events.*;
import mage.game.events.TableEvent.EventType;
import mage.game.permanent.Battlefield;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.SpellStack;
import mage.game.stack.StackObject;
import mage.game.turn.Phase;
import mage.game.turn.Step;
import mage.game.turn.Turn;
import mage.players.Library;
import mage.players.Player;
import mage.players.PlayerList;
import mage.players.Players;
import mage.target.TargetPlayer;
import mage.util.Logging;
import mage.watchers.Watcher;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

public abstract class GameImpl<T extends GameImpl<T>> implements Game, Serializable {

	private final static transient Logger logger = Logging.getLogger(GameImpl.class.getName());

	private static FilterPlaneswalkerPermanent filterPlaneswalker = new FilterPlaneswalkerPermanent();
	private static FilterLegendaryPermanent filterLegendary = new FilterLegendaryPermanent();
	private static FilterLegendaryPermanent filterLegendName = new FilterLegendaryPermanent();
	private static FilterAura filterAura = new FilterAura();
	private static FilterEquipment filterEquipment = new FilterEquipment();
	private static FilterFortification filterFortification = new FilterFortification();

	private transient Stack<Integer> savedStates = new Stack<Integer>();
	private transient Object customData;

	protected final UUID id;
	protected boolean ready;
	protected transient TableEventSource tableEventSource = new TableEventSource();
	protected transient PlayerQueryEventSource playerQueryEventSource = new PlayerQueryEventSource();

	protected Map<UUID, Card> gameCards = new HashMap<UUID, Card>();
	protected Map<UUID, Card> lki = new HashMap<UUID, Card>();
	protected GameState state;
	protected UUID startingPlayerId;
	protected UUID winnerId;

	protected transient GameStates gameStates = new GameStates();
	protected RangeOfInfluence range;
	protected MultiplayerAttackOption attackOption;

	@Override
	public abstract T copy();

	public GameImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range) {
		this.id = UUID.randomUUID();
		this.range = range;
		this.attackOption = attackOption;
		this.state = new GameState();
	}

	public GameImpl(final GameImpl<T> game) {
		this.id = game.id;
		this.ready = game.ready;
		this.startingPlayerId = game.startingPlayerId;
		this.winnerId = game.winnerId;
		this.range = game.range;
		this.attackOption = game.attackOption;
		this.state = game.state.copy();
		for (Map.Entry<UUID, Card> entry: game.gameCards.entrySet()) {
			this.gameCards.put(entry.getKey(), entry.getValue().copy());
		}
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public Object getCustomData() {
		return customData;
	}

	@Override
	public void setCustomData(Object data) {
		this.customData = data;
	}

	@Override
	public void loadCards(Set<Card> cards, UUID ownerId) {
		for (Card card: cards) {
			card.setOwnerId(ownerId);
			gameCards.put(card.getId(), card);
			state.setZone(card.getId(), Zone.OUTSIDE);
			for (Watcher watcher: card.getWatchers()) {
				watcher.setControllerId(ownerId);
				state.getWatchers().add(watcher);
			}
		}
	}

	@Override
	public Collection<Card> getCards() {
		return gameCards.values();
	}

	@Override
	public void addPlayer(Player player, Deck deck) throws GameException {
		player.useDeck(deck, this);
		state.addPlayer(player);
	}

	@Override
	public RangeOfInfluence getRangeOfInfluence() {
		return range;
	}

	@Override
	public MultiplayerAttackOption getAttackOption() {
		return attackOption;
	}

	@Override
	public Player getPlayer(UUID playerId) {
		if (playerId == null)
			return null;
		return state.getPlayer(playerId);
	}

	@Override
	public MageObject getObject(UUID objectId) {
		if (objectId == null)
			return null;
		MageObject object;
		if (state.getBattlefield().containsPermanent(objectId)) {
			object = state.getBattlefield().getPermanent(objectId);
			state.setZone(objectId, Zone.BATTLEFIELD);
			return object;
		}
		object = getCard(objectId);
		if (object != null)
			return object;
		for (StackObject item: state.getStack()) {
			if (item.getId().equals(objectId)) {
				state.setZone(objectId, Zone.STACK);
				return item;
			}
		}

		return null;
	}

	@Override
	public Permanent getPermanent(UUID permanentId) {
		if (permanentId == null)
			return null;
		return state.getPermanent(permanentId);
	}

	@Override
	public Card getCard(UUID cardId) {
		if (cardId == null)
			return null;
		return gameCards.get(cardId);
	}

	@Override
	public Zone getZone(UUID objectId) {
		if (objectId == null)
			return null;
		return state.getZone(objectId);
	}

	@Override
	public void setZone(UUID objectId, Zone zone) {
		state.setZone(objectId, zone);
	}

	@Override
	public GameStates getGameStates() {
		return gameStates;
	}
	
	@Override
	public void loadGameStates(GameStates states) {
		this.gameStates = states;
	}

	@Override
	public void saveState() {
		if (gameStates != null)
			gameStates.save(state);
	}

	@Override
	public boolean isGameOver() {
		if (state.isGameOver())
			return true;
		int remainingPlayers = 0;
		int numLosers = 0;
		for (Player player: state.getPlayers().values()) {
			if (!player.hasLeft())
				remainingPlayers++;
			if (player.hasLost())
				numLosers++;
		}
		if (remainingPlayers <= 1 || numLosers >= state.getPlayers().size() - 1) {
			state.endGame();
			return true;
		}
		return false;
	}

	@Override
	public String getWinner() {
		if (winnerId == null)
			return "Game is a draw";
		return "Player " + state.getPlayer(winnerId).getName() + " is the winner";
	}

	@Override
	public GameState getState() {
		return state;
	}

	@Override
	public void bookmarkState() {
		saveState();
		logger.fine("Bookmarking state: " + gameStates.getSize());
		savedStates.push(gameStates.getSize() - 1);
	}

	@Override
	public void restoreState() {
		GameState restore = gameStates.rollback(savedStates.pop());
		if (restore != null)
			state.restore(restore);
	}

	@Override
	public void removeLastBookmark() {
		savedStates.pop();
	}

	@Override
	public void start(UUID choosingPlayerId) {
		start(choosingPlayerId, false);
	}

	@Override
	public void start(UUID choosingPlayerId, boolean testMode) {
		init(choosingPlayerId, testMode);
		PlayerList players = state.getPlayerList(startingPlayerId);
		Player player = getPlayer(players.get());
		while (!isGameOver()) {
			if (player.getId().equals(startingPlayerId)) {
				state.setTurnNum(state.getTurnNum() + 1);
				fireInformEvent("Turn " + Integer.toString(state.getTurnNum()));
			}
			state.setActivePlayerId(player.getId());
			state.getTurn().play(this, player.getId());
			if (isGameOver())
				break;
			endOfTurn();
			player = players.getNext(this);
		}

		winnerId = findWinnersAndLosers();

		saveState();
	}

	protected void init(UUID choosingPlayerId) {
		init(choosingPlayerId, false);
	}

	protected void init(UUID choosingPlayerId, boolean testMode) {
		for (Player player: state.getPlayers().values()) {
			player.init(this, testMode);
		}
		fireInformEvent("game has started");
		saveState();

		//20091005 - 103.1
		for (Player player: state.getPlayers().values()) {
			player.shuffleLibrary(this);
		}

		//20091005 - 103.2
		TargetPlayer targetPlayer = new TargetPlayer();
		targetPlayer.setRequired(true);
		targetPlayer.setTargetName("starting player");
		Player choosingPlayer;
		if (choosingPlayerId == null) {
			choosingPlayer = getPlayer(pickChoosingPlayer());
		}
		else {
			choosingPlayer = this.getPlayer(choosingPlayerId);
		}
		if (choosingPlayer.chooseTarget(Outcome.Benefit, targetPlayer, null, this)) {
			startingPlayerId = ((List<UUID>)targetPlayer.getTargets()).get(0);
			fireInformEvent(state.getPlayer(startingPlayerId).getName() + " will start");
		}
		else {
			return;
		}

		saveState();

		//20091005 - 103.3
		for (UUID playerId: state.getPlayerList(startingPlayerId)) {
			Player player = getPlayer(playerId);
			player.setLife(this.getLife(), this);
			if (!testMode) {
				player.drawCards(7, this);
			}
		}

		//20091005 - 103.4
		for (UUID playerId: state.getPlayerList(startingPlayerId)) {
			Player player = getPlayer(playerId);
			while (player.getHand().size() > 0 && player.chooseMulligan(this)) {
				mulligan(player.getId());
			}
			fireInformEvent(player.getName() + " keeps hand");
			saveState();
		}

		//20100716 - 103.5
		for (UUID playerId: state.getPlayerList(startingPlayerId)) {
			Player player = getPlayer(playerId);
			for (Card card: player.getHand().getCards(this)) {
				if (card.getAbilities().containsKey(LeylineAbility.getInstance().getId())) {
					if (player.chooseUse(Outcome.PutCardInPlay, "Do you wish to put " + card.getName() + " on the battlefield?", this)) {
						player.getHand().remove(card.getId());
						card.putOntoBattlefield(this, Zone.HAND, null, player.getId());
					}
				}
			}
		}
	}

	protected UUID findWinnersAndLosers() {
		UUID winner = null;
		for (Player player: state.getPlayers().values()) {
			if (player.hasWon() || (!player.hasLost() && !player.hasLeft())) {
				player.won(this);
				winner = player.getId();
				break;
			}
		}
		for (Player player: state.getPlayers().values()) {
			if (winner != null && !player.getId().equals(winner)) {
				player.lost(this);
			}
		}
		return winner;
	}

	protected void endOfTurn() {
		for (Player player: getPlayers().values()) {
			player.endOfTurn(this);
		}
		state.getWatchers().reset();
	}

	protected UUID pickChoosingPlayer() {
		UUID[] players = getPlayers().keySet().toArray(new UUID[0]);
		UUID playerId = players[new Random().nextInt(players.length)];
		fireInformEvent(state.getPlayer(playerId).getName() + " won the toss");
		return playerId;
	}

	@Override
	public void end() {
		state.endGame();
		for (Player player: state.getPlayers().values()) {
			player.abort();
		}
	}

	@Override
	public void addTableEventListener(Listener<TableEvent> listener) {
		tableEventSource.addListener(listener);
	}

	@Override
	public void mulligan(UUID playerId) {
		Player player = getPlayer(playerId);
		int numCards = player.getHand().size();
		player.getLibrary().addAll(player.getHand().getCards(this), this);
		player.getHand().clear();
		player.shuffleLibrary(this);
		player.drawCards(numCards - 1, this);
		fireInformEvent(player.getName() + " mulligans down to " + Integer.toString(numCards - 1) + " cards");
	}

	@Override
	public synchronized void quit(UUID playerId) {
		Player player = state.getPlayer(playerId);
		if (player != null) {
			player.leaveGame(this);
			fireInformEvent(player.getName() + " has left the game.");
		}
	}

	@Override
	public synchronized void concede(UUID playerId) {
		Player player = state.getPlayer(playerId);
		if (player != null) {
			player.concede(this);
			fireInformEvent(player.getName() + " has conceded.");
		}
	}

	@Override
	public void playPriority(UUID activePlayerId) {
		try {
			while (!isGameOver()) {
				state.getPlayers().resetPassed();
				state.getPlayerList().setCurrent(activePlayerId);
				Player player;
				while (!isGameOver()) {
					player = getPlayer(state.getPlayerList().get());
					state.setPriorityPlayerId(player.getId());
					while (!player.isPassed() && !player.hasLost() && !player.hasLeft() && !isGameOver()) {
						checkStateAndTriggered();
						if (isGameOver()) return;
						// resetPassed should be called if player performs any action
						player.priority(this);
						if (isGameOver()) return;
						applyEffects();
					}
					if (isGameOver()) return;
					if (allPassed()) {
						if (!state.getStack().isEmpty()) {
							//20091005 - 115.4
							state.getStack().resolve(this);
							applyEffects();
							state.getPlayers().resetPassed();
							fireUpdatePlayersEvent();
						state.getRevealed().reset();
							break;
						} else
							return;
					}
					state.getPlayerList().getNext();
				}
			}
		} finally {
			resetLKI();
		}
	}

	protected boolean allPassed() {
		for (Player player: state.getPlayers().values()) {
			if (!player.isPassed() && !player.hasLost() && !player.hasLeft())
				return false;
		}
		return true;
	}

	@Override
	public void emptyManaPools() {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.EMPTY_MANA_POOLS, null, null, null))) {
			for (Player player: getPlayers().values()) {
				player.getManaPool().emptyPool();
			}
		}
	}

	@Override
	public synchronized void applyEffects() {
		state.applyEffects(this);
	}

	@Override
	public void addEffect(ContinuousEffect continuousEffect, Ability source) {
		ContinuousEffect newEffect = (ContinuousEffect)continuousEffect.copy();
		Ability newAbility = source.copy();
		newEffect.newId();
		newEffect.setTimestamp();
		newEffect.init(newAbility, this);
		state.addEffect(newEffect, newAbility);
	}

	@Override
	public void addTriggeredAbility(TriggeredAbility ability) {
		TriggeredAbility newAbility = (TriggeredAbility) ability.copy();
		newAbility.newId();
		state.addTriggeredAbility(newAbility);
	}
	
	@Override
	public void addDelayedTriggeredAbility(DelayedTriggeredAbility delayedAbility) {
		DelayedTriggeredAbility newAbility = (DelayedTriggeredAbility) delayedAbility.copy();
		newAbility.newId();
		state.addDelayedTriggeredAbility(newAbility);
	}

	@Override
	public boolean checkStateAndTriggered() {
		boolean somethingHappened = false;
		//20091005 - 115.5
		while (!this.isGameOver()) {
			if (!checkStateBasedActions() ) {
				if (this.isGameOver() || !checkTriggered()) {
					break;
				}
			}
			somethingHappened = true;
		}
		return somethingHappened;
	}

	public boolean checkTriggered() {
		boolean played = false;
		for (UUID playerId: state.getPlayerList(state.getActivePlayerId())) {
			Player player = getPlayer(playerId);
			while (true) {
				TriggeredAbilities abilities = state.getTriggered().getControlledBy(player.getId());
				if (abilities.size() == 0)
					break;
				if (abilities.size() == 1) {
					state.getTriggered().remove(abilities.get(0));
					played |= player.triggerAbility(abilities.get(0), this);
				}
				else {
					TriggeredAbility ability = player.chooseTriggeredAbility(abilities, this);
					state.getTriggered().remove(ability);
					played |= player.triggerAbility(ability, this);
				}
			}
		}
		return played;
	}

	protected boolean checkStateBasedActions() {
		boolean somethingHappened = false;

		//20091005 - 704.5a/704.5b/704.5c
		for (Player player: state.getPlayers().values()) {
			if (!player.hasLost() && (player.getLife() <= 0 || player.isEmptyDraw() || player.getCounters().getCount(CounterType.POISON) >= 10)) {
				player.lost(this);
			}
		}
		for (Permanent perm: getBattlefield().getAllActivePermanents(CardType.CREATURE)) {
			//20091005 - 704.5f
			if (perm.getToughness().getValue() == 0) {
				if (perm.moveToZone(Zone.GRAVEYARD, null, this, false))
					somethingHappened = true;
			}
			//20091005 - 704.5g/704.5h
			else if (perm.getToughness().getValue() <= perm.getDamage() || perm.isDeathtouched()) {
				if (perm.destroy(null, this, false))
					somethingHappened = true;
			}
		}
		//20091005 - 704.5i
		for (Permanent perm: getBattlefield().getAllActivePermanents(CardType.PLANESWALKER)) {
			if (perm.getLoyalty().getValue() == 0) {
				if (perm.moveToZone(Zone.GRAVEYARD, null, this, false))
					return true;
			}
		}
		//20091005 - 704.5j, 801.14
		if (getBattlefield().countAll(filterPlaneswalker) > 1) {  //don't bother checking if less than 2 planeswalkers in play
			for (Permanent planeswalker: getBattlefield().getAllActivePermanents(CardType.PLANESWALKER)) {
				for (String planeswalkertype: planeswalker.getSubtype()) {
					filterPlaneswalker.getSubtype().clear();
					filterPlaneswalker.getSubtype().add(planeswalkertype);
					filterPlaneswalker.setScopeSubtype(ComparisonScope.Any);
					if (getBattlefield().count(filterPlaneswalker, planeswalker.getControllerId(), this) > 1) {
						for (Permanent perm: getBattlefield().getActivePermanents(filterPlaneswalker, planeswalker.getControllerId(), this)) {
							perm.moveToZone(Zone.GRAVEYARD, null, this, false);
						}
						return true;
					}
				}
			}
		}
		//20091005 - 704.5n, 702.14c
		for (Permanent perm: getBattlefield().getAllActivePermanents(filterAura)) {
			if (perm.getAttachedTo() == null) {
				if (perm.moveToZone(Zone.GRAVEYARD, null, this, false))
					somethingHappened = true;
			}
			else {
				//TODO: handle player auras
				Permanent attachedTo = getPermanent(perm.getAttachedTo());
				if (attachedTo == null) {
					if (perm.moveToZone(Zone.GRAVEYARD, null, this, false))
						somethingHappened = true;
				}
				else {
					Filter auraFilter = perm.getSpellAbility().getTargets().get(0).getFilter();
					if (!auraFilter.match(attachedTo) || attachedTo.hasProtectionFrom(perm)) {
						if (perm.moveToZone(Zone.GRAVEYARD, null, this, false))
							somethingHappened = true;
					}
				}
			}
		}
		//20091005 - 704.5k, 801.12
		if (getBattlefield().countAll(filterLegendary) > 1) {  //don't bother checking if less than 2 legends in play
			for (Permanent legend: getBattlefield().getAllActivePermanents(filterLegendary)) {
				filterLegendName.getName().clear();
				filterLegendName.getName().add(legend.getName());
				if (getBattlefield().count(filterLegendName, legend.getControllerId(), this) > 1) {
					for (Permanent dupLegend: getBattlefield().getActivePermanents(filterLegendName, legend.getControllerId(), this)) {
						dupLegend.moveToZone(Zone.GRAVEYARD, null, this, false);
					}
					return true;
				}
			}
		}
		//20091005 - 704.5p, 702.14d
		for (Permanent perm: getBattlefield().getAllActivePermanents(filterEquipment)) {
			if (perm.getAttachedTo() != null) {
				Permanent creature = getPermanent(perm.getAttachedTo());
				if (creature == null) {
					perm.attachTo(null, this);
				}
				else if (!creature.getCardType().contains(CardType.CREATURE) || creature.hasProtectionFrom(perm)) {
					if (creature.removeAttachment(perm.getId(), this))
						somethingHappened = true;
				}
			}
		}
		for (Permanent perm: getBattlefield().getAllActivePermanents(filterFortification)) {
			if (perm.getAttachedTo() != null) {
				Permanent land = getPermanent(perm.getAttachedTo());
				if (land == null) {
					perm.attachTo(null, this);
				}
				else if (!land.getCardType().contains(CardType.LAND) || land.hasProtectionFrom(perm)) {
					if (land.removeAttachment(perm.getId(), this))
						somethingHappened = true;
				}
			}
		}
		//20091005 - 704.5q
		for (Permanent perm: getBattlefield().getAllActivePermanents()) {
			if (perm.getAttachments().size() > 0) {
				for (UUID attachmentId: perm.getAttachments()) {
					Permanent attachment = getPermanent(attachmentId);
					if (attachment != null && !(attachment.getSubtype().contains("Aura") ||
							attachment.getSubtype().contains("Equipment") ||
							attachment.getSubtype().contains("Fortification"))) {
						if (perm.removeAttachment(id, this))
							return true;
					}
				}
			}
		}

		//TODO: implement the rest

		return somethingHappened;
	}

	@Override
	public void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener) {
		playerQueryEventSource.addListener(listener);
	}

	@Override
	public synchronized void firePriorityEvent(UUID playerId) {
		String message = this.state.getTurn().getStepType().toString();
		if (this.canPlaySorcery(playerId))
			message += " - play spells and sorceries.";
		else
			message +=  " - play instants and activated abilities.";

		playerQueryEventSource.select(playerId, message);
	}

	@Override
	public synchronized void fireSelectEvent(UUID playerId, String message) {
		playerQueryEventSource.select(playerId, message);
	}

	@Override
	public void firePlayManaEvent(UUID playerId, String message) {
		playerQueryEventSource.playMana(playerId, message);
	}

	@Override
	public void firePlayXManaEvent(UUID playerId, String message) {
		playerQueryEventSource.playXMana(playerId, message);
	}

	@Override
	public void fireAskPlayerEvent(UUID playerId, String message) {
		playerQueryEventSource.ask(playerId, message);
	}

	@Override
	public void fireGetChoiceEvent(UUID playerId, String message, Collection<? extends ActivatedAbility> choices) {
		playerQueryEventSource.chooseAbility(playerId, message, choices);
	}

	@Override
	public void fireSelectTargetEvent(UUID playerId, String message, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
		playerQueryEventSource.target(playerId, message, targets, required, options);
	}

	@Override
	public void fireSelectTargetEvent(UUID playerId, String message, Cards cards, boolean required) {
		playerQueryEventSource.target(playerId, message, cards, required);
	}

	@Override
	public void fireSelectTargetEvent(UUID playerId, String message, TriggeredAbilities abilities, boolean required) {
		playerQueryEventSource.target(playerId, message, abilities, required);
	}

//	@Override
//	public void fireRevealCardsEvent(String message, Cards cards) {
//		tableEventSource.fireTableEvent(EventType.REVEAL, message, cards, this);
//	}

	@Override
	public void fireLookAtCardsEvent(UUID playerId, String message, Cards cards) {
		playerQueryEventSource.target(playerId, message, cards);
	}

	@Override
	public void fireGetAmountEvent(UUID playerId, String message, int min, int max) {
		playerQueryEventSource.amount(playerId, message, min, max);
	}

	@Override
	public void fireChooseEvent(UUID playerId, Choice choice) {
		playerQueryEventSource.choose(playerId, choice.getMessage(), choice.getChoices());
	}

	@Override
	public void informPlayers(String message) {
//		state.addMessage(message);
		fireInformEvent(message);
	}

	@Override
	public void fireInformEvent(String message) {
		tableEventSource.fireTableEvent(EventType.INFO, message, this);
	}

	@Override
	public void fireUpdatePlayersEvent() {
		tableEventSource.fireTableEvent(EventType.UPDATE, null, this);
	}
	
	@Override
	public Players getPlayers() {
		return state.getPlayers();
	}

	@Override
	public PlayerList getPlayerList() {
		return state.getPlayerList();
	}

	@Override
	public Turn getTurn() {
		return state.getTurn();
	}

	@Override
	public Phase getPhase() {
		return state.getTurn().getPhase();
	}

	@Override
	public Step getStep() {
		return state.getTurn().getStep();
	}

	@Override
	public Battlefield getBattlefield() {
		return state.getBattlefield();
	}

	@Override
	public SpellStack getStack() {
		return state.getStack();
	}

	@Override
	public Exile getExile() {
		return state.getExile();
	}

	@Override
	public Combat getCombat() {
		return state.getCombat();
	}

	@Override
	public int getTurnNum() {
		return state.getTurnNum();
	}

	@Override
	public boolean isMainPhase() {
		return state.getTurn().getStepType() == PhaseStep.PRECOMBAT_MAIN || state.getTurn().getStepType() == PhaseStep.POSTCOMBAT_MAIN;
	}

	@Override
	public boolean canPlaySorcery(UUID playerId) {
		return getActivePlayerId().equals(playerId) && getStack().isEmpty() && isMainPhase();
	}

	@Override
	public UUID getActivePlayerId() {
		return state.getActivePlayerId();
	}

	@Override
	public UUID getPriorityPlayerId() {
		return state.getPriorityPlayerId();
	}

	@Override
	public void fireEvent(GameEvent event) {
		applyEffects();
		state.handleEvent(event, this);
	}

	@Override
	public boolean replaceEvent(GameEvent event) {
		applyEffects();
		return state.replaceEvent(event, this);
	}

	protected void removeCreaturesFromCombat() {
		//20091005 - 511.3
		getCombat().endCombat(this);
	}

	@Override
	public ContinuousEffects getContinuousEffects() {
		return state.getContinuousEffects();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		//initialize transient objects during deserialization
		in.defaultReadObject();
		savedStates = new Stack<Integer>();
		tableEventSource = new TableEventSource();
		playerQueryEventSource = new PlayerQueryEventSource();
		gameStates = new GameStates();
	}

	/**
	 * Gets last known information about object in the zone.
	 * At the moment doesn't take into account zone (it is expected that it doesn't really matter, if not, then Map<UUID, Map<Zone, Card>> should be used instead).
	 *
	 * Can return null.
	 *
	 * @param objectId
	 * @param zone
	 * @return
	 */
	public Card getLastKnownInformation(UUID objectId, Zone zone) {
		return lki.get(objectId);
	}

	/**
	 * Remembers object state to be used as Last Known Information.
	 *
	 * @param objectId
	 * @param zone
	 * @param card
	 */
	public void rememberLKI(UUID objectId, Zone zone, Card card) {
		Card copy = card.copy();
		lki.put(objectId, copy);
	}

	/**
	 * Reset objects stored for Last Known Information.
	 */
	public void resetLKI() {
		lki.clear();
	}

	public void cheat(UUID ownerId, Map<Zone, String> commands) {
		if (commands != null) {
			Player player = getPlayer(ownerId);
			if (player != null) {
				for (Map.Entry<Zone, String> command : commands.entrySet()) {
					switch (command.getKey()) {
						case HAND:
							if (command.getValue().equals("clear")) {
								removeCards(player.getHand());
							}
							break;
						case LIBRARY:
							if (command.getValue().equals("clear")) {
								for (UUID card : player.getLibrary().getCardList()) {
									gameCards.remove(card);
								}
								player.getLibrary().clear();
							}
							break;
					}
				}
			}
		}
	}

	private void removeCards(Cards cards) {
		for (UUID card : cards) {
			gameCards.remove(card);
		}
		cards.clear();
	}

	public void cheat(UUID ownerId, List<Card> library, List<Card> hand, List<Card> battlefield, List<Card> graveyard) {
		Player player = getPlayer(ownerId);
		if (player != null) {
			loadCards(ownerId, library);
			loadCards(ownerId, hand);
			loadCards(ownerId, battlefield);
			loadCards(ownerId, graveyard);

			for (Card card : library) {
				setZone(card.getId(), Zone.LIBRARY);
				player.getLibrary().putOnTop(card, this);
			}
			for (Card card : hand) {
				setZone(card.getId(), Zone.HAND);
				player.getHand().add(card);
			}
			for (Card card : graveyard) {
				setZone(card.getId(), Zone.GRAVEYARD);
				player.getGraveyard().add(card);
			}
			List<Card> permanents = new ArrayList<Card>();
			for (Card card : battlefield) {
				card.setOwnerId(ownerId);
				PermanentCard permanent = new PermanentCard(card, ownerId);
				getBattlefield().addPermanent(permanent);
			}
			applyEffects();
		}
	}

	private void loadCards(UUID ownerId, List<Card> cards) {
		if (cards == null) {
			return;
		}
		Set<Card> set = new HashSet<Card>();
		for (Card card : cards) {
			set.add(card);
		}
		loadCards(set, ownerId);
	}

	public void replaceLibrary(List<Card> cardsDownToTop, UUID ownerId) {
		Player player = getPlayer(ownerId);
		if (player != null) {
			for (UUID card : player.getLibrary().getCardList()) {
				gameCards.remove(card);
			}
			player.getLibrary().clear();
			Set<Card> cards = new HashSet<Card>();
			for (Card card : cardsDownToTop) {
				cards.add(card);
			}
			loadCards(cards, ownerId);

			for (Card card : cards) {
				player.getLibrary().putOnTop(card, this);
			}
		}
	}

	public void clearGraveyard(UUID playerId) {

	}
}
