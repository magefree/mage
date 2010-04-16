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

import mage.game.stack.SpellStack;
import java.io.Serializable;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Random;
import java.util.Stack;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.PhaseStep;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.ActivatedAbility;
import mage.abilities.TriggeredAbilities;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffects;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterEquipment;
import mage.filter.common.FilterFortification;
import mage.filter.common.FilterLegendaryPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
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
import mage.game.turn.Turn;
import mage.players.PlayerList;
import mage.players.Players;
import mage.target.TargetPlayer;

public abstract class GameImpl implements Game, Serializable {

	private Stack<Integer> savedStates = new Stack<Integer>();

	protected UUID id;
	protected boolean ready = false;
	protected TableEventSource tableEventSource = new TableEventSource();
	protected PlayerQueryEventSource playerQueryEventSource = new PlayerQueryEventSource();

	protected GameState state;
	protected UUID startingPlayerId;
	protected UUID choosingPlayerId;
	protected Player winner;
	protected GameStates gameStates;

	public GameImpl() {
		id = UUID.randomUUID();
		state = new GameState();
		gameStates = new GameStates();
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public void addPlayer(Player player) throws GameException {
		state.addPlayer(player);
	}

	@Override
	public Player getPlayer(UUID playerId) {
		return state.getPlayer(playerId);
	}

	@Override
	public MageObject getObject(UUID objectId) {
		return state.getObject(objectId);
	}

	@Override
	public Permanent getPermanent(UUID permanentId) {
		return state.getPermanent(permanentId);
	}

	@Override
	public GameStates getGameStates() {
		return gameStates;
	}

	@Override
	public void saveState() {
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
		if (winner == null)
			return "Game is a draw";
		return "Player " + winner.getName() + " is the winner";
	}

	@Override
	public GameState getState() {
		return state;
	}

	@Override
	public void bookmarkState() {
		savedStates.push(gameStates.getSize());
	}

	@Override
	public void restoreState() {
		state.restore(gameStates.rollback(savedStates.pop()));
	}

	@Override
	public void removeLastBookmark() {
		savedStates.pop();
	}

	@Override
	public void start() {
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
			Player choosingPlayer = getPlayer(pickChoosingPlayer());
			if (choosingPlayer.chooseTarget(Outcome.Benefit, targetPlayer, this)) {
				startingPlayerId = targetPlayer.getTargets().get(0);
				fireInformEvent(state.getPlayer(startingPlayerId).getName() + " will start");
			}
			else {
				return;
			}
		}
		saveState();

		//20091005 - 103.3
		for (Player player: state.getPlayerList(startingPlayerId)) {
			player.setLife(this.getLife(), this);
			player.drawCards(7, this);
		}

		//20091005 - 103.4
		for (Player player: state.getPlayerList(startingPlayerId)) {
			while (player.getHand().size() > 0 && player.chooseMulligan(this)) {
				mulligan(player.getId());
			}
			fireInformEvent(player.getName() + " keeps hand");
			saveState();
		}

		while (!isGameOver()) {
			state.setTurnNum(state.getTurnNum() + 1);
			fireInformEvent("Turn " + Integer.toString(state.getTurnNum()));
			for (Player player: state.getPlayerList(startingPlayerId)) {
				state.setActivePlayerId(player.getId());
				state.getTurn().play(this, player.getId());
				if (isGameOver())
					break;
				endOfTurn();
			}
		}

		winner = findWinner();

		saveState();
	}

	protected Player findWinner() {
		for (Player player: state.getPlayers().values()) {
			if (player.hasWon() || (!player.hasLost() && !player.hasLeft())) {
				return player;
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
		player.getLibrary().addAll(player.getHand());
		player.getHand().clear();
		player.shuffleLibrary(this);
		player.drawCards(numCards - 1, this);
		fireInformEvent(player.getName() + " mulligans down to " + Integer.toString(numCards - 1) + " cards");
	}

	@Override
	public void quit(UUID playerId) {
		Player player = state.getPlayer(playerId);
		if (player != null) {
			player.leaveGame();
			fireInformEvent(player.getName() + " has left the game.");
			player.abort();
		}
	}

	@Override
	public void concede(UUID playerId) {
		Player player = state.getPlayer(playerId);
		if (player != null) {
			player.concede();
			fireInformEvent(player.getName() + " has conceded.");
			player.abort();
		}
	}

	@Override
	public void playPriority(UUID activePlayerId) {
		while (!isGameOver()) {
			while (!isGameOver()) {
				int stackSize = state.getStack().size();
				state.getPlayers().resetPriority();
				for (Player player: getPlayerList(activePlayerId)) {
					state.setPriorityPlayerId(player.getId());
					while (!player.isPassed() && !isGameOver()) {
						checkStateAndTriggered();
						if (isGameOver()) 
							return;
						player.priority(this);
						if (isGameOver())
							return;
						applyEffects();
						saveState();
					}
				}
				//no items have been added to the stack
				if (isGameOver() || stackSize == state.getStack().size())
					break;
			}
			if (isGameOver() || state.getStack().isEmpty())
				break;
			//20091005 - 115.4
			state.getStack().resolve(this);
			applyEffects();
			saveState();
		}
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
	public void applyEffects() {
		state.applyEffects(this);
	}

	@Override
	public void addEffect(ContinuousEffect continuousEffect) {
		state.addEffect(continuousEffect);
	}

	@Override
	public void addTriggeredAbility(TriggeredAbility ability) {
		state.addTriggeredAbility((TriggeredAbility) ability.copy());
	}
	
	protected boolean checkStateAndTriggered() {
		boolean somethingHappened = false;
		//20091005 - 115.5
		while (true) {
			if (!checkStateBasedActions() ) {
				if (!checkTriggered()) {
					break;
				}
			}
			somethingHappened = true;
		}
		return somethingHappened;
	}

	public boolean checkTriggered() {
		boolean played = false;
		for (Player player: getPlayerList(state.getActivePlayerId())) {
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
				somethingHappened = true;
			}
		}
		for (Permanent perm: getBattlefield().getActivePermanents(CardType.CREATURE)) {
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
		for (Permanent perm: getBattlefield().getActivePermanents(CardType.PLANESWALKER)) {
			if (perm.getLoyalty().getValue() == 0) {
				perm.moveToZone(Zone.GRAVEYARD, this, false);
				return true;
			}
		}
		//20091005 - 704.5j
		FilterPlaneswalkerPermanent filterPlaneswalker = new FilterPlaneswalkerPermanent();
		if (getBattlefield().count(filterPlaneswalker) > 1) {  //don't bother checking if less than 2 planeswalkers in play
			for (String planeswalkerType: Constants.PlaneswalkerTypes) {
				filterPlaneswalker.getSubtype().clear();
				filterPlaneswalker.getSubtype().add(planeswalkerType);
				filterPlaneswalker.setScopeSubtype(ComparisonScope.Any);
				if (getBattlefield().count(filterPlaneswalker) > 1) {
					for (Permanent perm: getBattlefield().getActivePermanents(filterPlaneswalker)) {
						perm.moveToZone(Zone.GRAVEYARD, this, false);
					}
					somethingHappened = true;
				}
			}
		}
		//20091005 - 704.5k
		FilterLegendaryPermanent filterLegendary = new FilterLegendaryPermanent();
		if (getBattlefield().count(filterPlaneswalker) > 1) {  //don't bother checking if less than 2 legends in play
			for (Permanent legend: getBattlefield().getActivePermanents(filterLegendary)) {
				FilterLegendaryPermanent filterLegendName = new FilterLegendaryPermanent();
				filterLegendName.getName().add(legend.getName());
				if (getBattlefield().count(filterLegendName) > 1) {
					for (Permanent dupLegend: getBattlefield().getActivePermanents(filterLegendName)) {
						dupLegend.moveToZone(Zone.GRAVEYARD, this, false);
					}
					return true;
				}
			}
		}
		//20091005 - 704.5p
		for (Permanent perm: getBattlefield().getActivePermanents(new FilterEquipment())) {
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
		for (Permanent perm: getBattlefield().getActivePermanents(new FilterFortification())) {
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
		for (Permanent perm: getBattlefield().getActivePermanents()) {
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
	public boolean playUntapStep(UUID activePlayerId) {
		fireEvent(new GameEvent(GameEvent.EventType.BEGINNING_PHASE_PRE, null, null, activePlayerId));
		if (!replaceEvent(new GameEvent(GameEvent.EventType.UNTAP_STEP, null, null, activePlayerId))) {
			//20091005 - 502.1/703.4a
			getPlayer(activePlayerId).phasing(this);
			//20091005 - 502.2/703.4b
			getPlayer(activePlayerId).untap(this);
			fireEvent(new GameEvent(GameEvent.EventType.UNTAP_STEP_PRE, null, null, activePlayerId));
			fireEvent(new GameEvent(GameEvent.EventType.UNTAP_STEP_POST, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	@Override
	public boolean playUpkeepStep(UUID activePlayerId) {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.UPKEEP_STEP, null, null, activePlayerId))) {
			fireEvent(new GameEvent(GameEvent.EventType.UPKEEP_STEP_PRE, null, null, activePlayerId));
			playPriority(activePlayerId);
			fireEvent(new GameEvent(GameEvent.EventType.UPKEEP_STEP_POST, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	@Override
	public boolean playDrawStep(UUID activePlayerId) {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.DRAW_STEP, null, null, activePlayerId))) {
			//20091005 - 504.1/703.4c
			getPlayer(activePlayerId).drawCards(1, this);
			fireEvent(new GameEvent(GameEvent.EventType.DRAW_STEP_PRE, null, null, activePlayerId));
			playPriority(activePlayerId);
			fireEvent(new GameEvent(GameEvent.EventType.DRAW_STEP_POST, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	@Override
	public boolean playPreCombatMainStep(UUID activePlayerId) {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.PRECOMBAT_MAIN_STEP, null, null, activePlayerId))) {
			fireEvent(new GameEvent(GameEvent.EventType.PRECOMBAT_MAIN_STEP_PRE, null, null, activePlayerId));
			playPriority(activePlayerId);
			fireEvent(new GameEvent(GameEvent.EventType.PRECOMBAT_MAIN_STEP_POST, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	@Override
	public boolean playBeginCombatStep(UUID activePlayerId) {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.BEGIN_COMBAT_STEP, null, null, activePlayerId))) {
			//20091005 - 507.1
			state.getCombat().clear();
			state.getCombat().setAttacker(activePlayerId);
			for (Player player: state.getPlayers().values()) {
				if (!player.getId().equals(state.getActivePlayerId()))
					state.getCombat().getDefenders().add(player.getId());
			}
			fireEvent(new GameEvent(GameEvent.EventType.BEGIN_COMBAT_STEP_PRE, null, null, activePlayerId));
			playPriority(activePlayerId);
			fireEvent(new GameEvent(GameEvent.EventType.PRECOMBAT_MAIN_STEP_POST, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	@Override
	public boolean playDeclareAttackersStep(UUID activePlayerId) {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP, null, null, activePlayerId))) {
			state.getCombat().selectAttackers(this);
			fireEvent(new GameEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE, null, null, activePlayerId));
			playPriority(activePlayerId);
			fireEvent(new GameEvent(GameEvent.EventType.DECLARE_ATTACKERS_STEP_POST, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	@Override
	public boolean playDeclareBlockersStep(UUID activePlayerId) {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP, null, null, activePlayerId))) {
			state.getCombat().selectBlockers(this);
			fireEvent(new GameEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_PRE, null, null, activePlayerId));
			playPriority(activePlayerId);
			fireEvent(new GameEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_POST, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	@Override
	public boolean playCombatDamageStep(UUID activePlayerId, boolean first) {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.COMBAT_DAMAGE_STEP, null, null, activePlayerId))) {
			fireEvent(new GameEvent(GameEvent.EventType.COMBAT_DAMAGE_STEP_PRE, null, null, activePlayerId));
			for (CombatGroup group: getCombat().getGroups()) {
				group.assignDamage(first, this);
			}
			playPriority(activePlayerId);
			fireEvent(new GameEvent(GameEvent.EventType.COMBAT_DAMAGE_STEP_POST, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	@Override
	public boolean playEndCombatStep(UUID activePlayerId) {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.END_COMBAT_STEP, null, null, activePlayerId))) {
			fireEvent(new GameEvent(GameEvent.EventType.END_COMBAT_STEP_PRE, null, null, activePlayerId));
			playPriority(activePlayerId);
			fireEvent(new GameEvent(GameEvent.EventType.END_COMBAT_STEP_POST, null, null, activePlayerId));
			removeCreaturesFromCombat();
			return true;
		}
		return false;
	}

	@Override
	public boolean playPostMainStep(UUID activePlayerId) {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.POSTCOMBAT_MAIN_STEP, null, null, activePlayerId))) {
			fireEvent(new GameEvent(GameEvent.EventType.POSTCOMBAT_MAIN_STEP_PRE, null, null, activePlayerId));
			playPriority(activePlayerId);
			fireEvent(new GameEvent(GameEvent.EventType.POSTCOMBAT_MAIN_STEP_POST, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	@Override
	public boolean playEndStep(UUID activePlayerId) {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.END_TURN_STEP, null, null, activePlayerId))) {
			fireEvent(new GameEvent(GameEvent.EventType.END_TURN_STEP_PRE, null, null, activePlayerId));
			playPriority(activePlayerId);
			fireEvent(new GameEvent(GameEvent.EventType.END_TURN_STEP_POST, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	@Override
	public boolean playCleanupStep(UUID activePlayerId) {
		if (!replaceEvent(new GameEvent(GameEvent.EventType.CLEANUP_STEP, null, null, activePlayerId))) {
			fireEvent(new GameEvent(GameEvent.EventType.CLEANUP_STEP_PRE, null, null, activePlayerId));
			//20091005 - 514.1
			Player player = getPlayer(activePlayerId);
			player.discardToMax(this);
			state.getBattlefield().endOfTurn(activePlayerId, this);
			state.removeEotEffects(this);
			if (checkStateAndTriggered()) {
				playPriority(activePlayerId);
				playCleanupStep(activePlayerId);
			}
			fireEvent(new GameEvent(GameEvent.EventType.CLEANUP_STEP_POST, null, null, activePlayerId));
			return true;
		}
		return false;
	}

	@Override
	public void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener) {
		playerQueryEventSource.addListener(listener);
	}

	@Override
	public void firePriorityEvent(UUID playerId) {
		String message = this.state.getTurn().getStep().toString();
		if (this.canPlaySorcery(playerId))
			message += " - play spells and sorceries.";
		else
			message +=  " - play instants and activated abilites.";

		playerQueryEventSource.select(playerId, message);
	}

	@Override
	public void fireSelectEvent(UUID playerId, String message) {
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
	public void fireGetAmountEvent(UUID playerId, String message, int min, int max) {
		playerQueryEventSource.amount(playerId, message, min, max);
	}

	@Override
	public void fireChooseEvent(UUID playerId, Choice choice) {
		playerQueryEventSource.choose(playerId, choice.getMessage(), choice.getChoices().toArray(new String[0]));
	}

	@Override
	public void informPlayers(String message) {
		state.addMessage(message);
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
	public PlayerList getPlayerList(UUID playerId) {
		return state.getPlayerList(playerId);
	}

	@Override
	public Turn getTurn() {
		return state.getTurn();
	}

//	@Override
//	public TurnPhase getPhase() {
//		return state.getPhase();
//	}
//
//	@Override
//	public PhaseStep getStep() {
//		return state.getStep();
//	}

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
		return state.getTurn().getStep() == PhaseStep.PRECOMBAT_MAIN || state.getTurn().getStep() == PhaseStep.POSTCOMBAT_MAIN;
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

}
