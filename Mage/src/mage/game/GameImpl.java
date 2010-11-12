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

import java.io.IOException;
import mage.game.stack.SpellStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;
import java.util.logging.Logger;
import mage.Constants.CardType;
import mage.Constants.MultiplayerAttackOption;
import mage.Constants.Outcome;
import mage.Constants.PhaseStep;
import mage.Constants.RangeOfInfluence;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.TriggeredAbilities;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffects;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.filter.Filter;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterAura;
import mage.filter.common.FilterEquipment;
import mage.filter.common.FilterFortification;
import mage.filter.common.FilterLegendaryPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.game.combat.Combat;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.game.events.Listener;
import mage.game.events.TableEvent;
import mage.game.events.TableEvent.EventType;
import mage.game.events.TableEventSource;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.PlayerQueryEventSource;
import mage.game.permanent.Battlefield;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.game.turn.Phase;
import mage.game.turn.Step;
import mage.game.turn.Turn;
import mage.players.PlayerList;
import mage.players.Players;
import mage.target.TargetPlayer;
import mage.util.Logging;
import mage.watchers.Watcher;

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
	protected GameState state;
	protected UUID startingPlayerId;
	protected UUID choosingPlayerId;
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
		this.choosingPlayerId = game.choosingPlayerId;
		this.winnerId = game.winnerId;
		this.range = game.range;
		this.attackOption = game.attackOption;
		this.state = game.state.copy();
		for (UUID cardId: game.gameCards.keySet()) {
			this.gameCards.put(cardId, game.gameCards.get(cardId).copy());
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
			for (Watcher watcher: card.getWatchers()) {
				watcher.setControllerId(ownerId);
				state.getWatchers().add(watcher);
			}
		}
	}

	@Override
	public void addPlayer(Player player) throws GameException {
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
		return state.getPlayer(playerId);
	}

	@Override
	public MageObject getObject(UUID objectId) {
		MageObject object;
		if (state.getBattlefield().containsPermanent(objectId)) {
			object = state.getBattlefield().getPermanent(objectId);
			object.setZone(Zone.BATTLEFIELD);
			return object;
		}
		object = getCard(objectId);
		if (object != null)
			return object;
		for (StackObject item: state.getStack()) {
			if (item.getId().equals(objectId)) {
				item.setZone(Zone.STACK);
				return item;
			}
		}

		return null;
	}

	@Override
	public Permanent getPermanent(UUID permanentId) {
		return state.getPermanent(permanentId);
	}

	@Override
	public Card getCard(UUID cardId) {
		return gameCards.get(cardId);
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
	public void start() {
		init();
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

		winnerId = findWinner();

		saveState();
	}

	@Override
	public void init() {
		for (Player player: state.getPlayers().values()) {
			player.init(this);
		}
		fireInformEvent("game has started");
		saveState();

		//20091005 - 103.1
		for (Player player: state.getPlayers().values()) {
			player.shuffleLibrary(this);
		}

		//20091005 - 103.2
		if (startingPlayerId == null) {
			TargetPlayer targetPlayer = new TargetPlayer();
			targetPlayer.setRequired(true);
			targetPlayer.setTargetName("starting player");
			Player choosingPlayer = getPlayer(pickChoosingPlayer());
			if (choosingPlayer.chooseTarget(Outcome.Benefit, targetPlayer, null, this)) {
				startingPlayerId = ((List<UUID>)targetPlayer.getTargets()).get(0);
				fireInformEvent(state.getPlayer(startingPlayerId).getName() + " will start");
			}
			else {
				return;
			}
		}
		saveState();

		//20091005 - 103.3
		for (UUID playerId: state.getPlayerList(startingPlayerId)) {
			Player player = getPlayer(playerId);
			player.setLife(this.getLife(), this);
			player.drawCards(7, this);
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
					if (!player.chooseUse(Outcome.PutCardInPlay, "Do you wish to put " + card.getName() + " on the battlefield?", this)) {
						player.getHand().remove(card);
						card.putOntoBattlefield(this, Zone.HAND, player.getId());
					}
				}
			}
		}
	}

	protected UUID findWinner() {
		for (Player player: state.getPlayers().values()) {
			if (player.hasWon() || (!player.hasLost() && !player.hasLeft())) {
				return player.getId();
			}
		}
		return null;
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
		player.getLibrary().addAll(player.getHand().getCards(this));
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
		while (!isGameOver()) {
			state.getPlayers().resetPassed();
			state.getPlayerList().setCurrent(activePlayerId);
			Player player;
			while (!isGameOver()) {
				player = getPlayer(state.getPlayerList().get());
				state.setPriorityPlayerId(player.getId());
				while (!player.isPassed() && !player.hasLost() && !player.hasLeft()&& !isGameOver()) {
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
						break;
					}
					else
						return;
				}
				state.getPlayerList().getNext();
			}
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
		state.addEffect(continuousEffect, source);
	}

	@Override
	public void addTriggeredAbility(TriggeredAbility ability) {
		state.addTriggeredAbility((TriggeredAbility) ability.copy());
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
			if (!player.hasLost() && (player.getLife() <= 0 || player.isEmptyDraw() || player.getCounters().getCount("Poison") >= 10)) {
				player.lost(this);
				return false;
			}
		}
		for (Permanent perm: getBattlefield().getAllActivePermanents(CardType.CREATURE)) {
			//20091005 - 704.5f
			if (perm.getToughness().getValue() == 0) {
				perm.moveToZone(Zone.GRAVEYARD, this, false);
				somethingHappened = true;
			}
			//20091005 - 704.5g/704.5h
			else if (perm.getToughness().getValue() <= perm.getDamage() || perm.isDeathtouched()) {
				perm.destroy(null, this, false);
				somethingHappened = true;
			}
		}
		//20091005 - 704.5i
		for (Permanent perm: getBattlefield().getAllActivePermanents(CardType.PLANESWALKER)) {
			if (perm.getLoyalty().getValue() == 0) {
				perm.moveToZone(Zone.GRAVEYARD, this, false);
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
							perm.moveToZone(Zone.GRAVEYARD, this, false);
						}
						return true;
					}
				}
			}
		}
		//20091005 - 704.5n
		for (Permanent perm: getBattlefield().getAllActivePermanents(filterAura)) {
			if (perm.getAttachedTo() == null) {
				perm.moveToZone(Zone.GRAVEYARD, this, false);
			}
			else {
				//TODO: handle player auras
				Permanent attachedTo = getPermanent(perm.getAttachedTo());
				if (attachedTo == null) {
					perm.moveToZone(Zone.GRAVEYARD, this, false);
				}
				else {
					Filter auraFilter = perm.getSpellAbility().getTargets().get(0).getFilter();
					if (!auraFilter.match(attachedTo)) {
						perm.moveToZone(Zone.GRAVEYARD, this, false);
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
						dupLegend.moveToZone(Zone.GRAVEYARD, this, false);
					}
					return true;
				}
			}
		}
		//20091005 - 704.5p
		for (Permanent perm: getBattlefield().getAllActivePermanents(filterEquipment)) {
			if (perm.getAttachedTo() != null) {
				Permanent creature = getPermanent(perm.getAttachedTo());
				if (creature == null) {
					perm.attachTo(null);
				}
				else if (!creature.getCardType().contains(CardType.CREATURE)) {
					creature.removeAttachment(perm.getId(), this);
					somethingHappened = true;
				}
			}
		}
		for (Permanent perm: getBattlefield().getAllActivePermanents(filterFortification)) {
			if (perm.getAttachedTo() != null) {
				Permanent land = getPermanent(perm.getAttachedTo());
				if (land == null) {
					perm.attachTo(null);
				}
				else if (!land.getCardType().contains(CardType.LAND)) {
					land.removeAttachment(perm.getId(), this);
					somethingHappened = true;
				}
			}
		}
		//20091005 - 704.5q
		for (Permanent perm: getBattlefield().getAllActivePermanents()) {
			if (perm.getAttachments().size() > 0) {
				for (UUID attachmentId: perm.getAttachments()) {
					Permanent attachment = getPermanent(attachmentId);
					if (!(attachment.getSubtype().contains("Aura") ||
							attachment.getSubtype().contains("Equipment") ||
							attachment.getSubtype().contains("Fortification"))) {
						perm.removeAttachment(id, this);
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
			message +=  " - play instants and activated abilites.";

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
	public void fireSelectTargetEvent(UUID playerId, String message, boolean required) {
		playerQueryEventSource.target(playerId, message, required);
	}

	@Override
	public void fireSelectTargetEvent(UUID playerId, String message, Cards cards, boolean required) {
		playerQueryEventSource.target(playerId, message, cards, required);
	}

	@Override
	public void fireSelectTargetEvent(UUID playerId, String message, TriggeredAbilities abilities, boolean required) {
		playerQueryEventSource.target(playerId, message, abilities, required);
	}

	@Override
	public void fireRevealCardsEvent(String message, Cards cards) {
		tableEventSource.fireTableEvent(EventType.REVEAL, message, cards, this);
	}

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
		playerQueryEventSource.choose(playerId, choice.getMessage(), ((List<String>)choice.getChoices()).toArray(new String[0]));
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

}
