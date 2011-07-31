/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in ability and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of ability code must retain the above copyright notice, this list of
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

package mage.players;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants.RangeOfInfluence;
import mage.Constants.Zone;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.Mode;
import mage.abilities.PlayLandAbility;
import mage.abilities.SpecialAction;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.keyword.*;
import mage.abilities.mana.ManaAbility;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.decks.Deck;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterAbility;
import mage.filter.common.FilterCreatureForAttack;
import mage.filter.common.FilterCreatureForCombat;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.permanent.Permanent;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetDiscard;

public abstract class PlayerImpl<T extends PlayerImpl<T>> implements Player, Serializable {

	protected boolean abort;
	protected final UUID playerId;
	protected String name;
	protected boolean human;
	protected int life;
	protected boolean wins;
	protected boolean loses;
	protected Library library;
	protected Cards hand;
	protected Cards graveyard;
	protected Abilities<Ability> abilities;
	protected Counters counters;
	protected int landsPlayed;
	protected int landsPerTurn = 1;
	protected int maxHandSize = 7;
	protected ManaPool manaPool;
	protected boolean passed;
	protected boolean passedTurn;
	protected boolean left;
	protected RangeOfInfluence range;
	protected Set<UUID> inRange = new HashSet<UUID>();
	protected boolean isTestMode = false;
	protected boolean lifeTotalCanChange = true;

	@Override
	public abstract T copy();

	public PlayerImpl(String name, RangeOfInfluence range) {
		this(UUID.randomUUID());
		this.name = name;
		this.range = range;
		hand = new CardsImpl(Zone.HAND);
		graveyard = new CardsImpl(Zone.GRAVEYARD);
		abilities = new AbilitiesImpl<Ability>();
		counters = new Counters();
		manaPool = new ManaPool();
		library = new Library(playerId);
	}

	protected PlayerImpl(UUID id) {
		this.playerId = id;
	}

	public PlayerImpl(final PlayerImpl<T> player) {
		this.abort = player.abort;
		this.playerId = player.playerId;
		this.name = player.name;
		this.human = player.human;
		this.life = player.life;
		this.wins = player.loses;
		this.loses = player.loses;
		this.library = player.library.copy();
		this.hand = player.hand.copy();
		this.graveyard = player.graveyard.copy();
		this.abilities = player.abilities.copy();
		this.counters = player.counters.copy();
		this.landsPlayed = player.landsPlayed;
		this.landsPerTurn = player.landsPerTurn;
		this.maxHandSize = player.maxHandSize;
		this.manaPool = player.manaPool.copy();
		this.passed = player.passed;
		this.passedTurn = player.passedTurn;
		this.left = player.left;
		this.range = player.range;
		this.lifeTotalCanChange = player.lifeTotalCanChange;
		for (UUID id: player.inRange) {
			this.inRange.add(id);
		}
	}

	@Override
	public void useDeck(Deck deck, Game game) {
		library.clear();
		library.addAll(deck.getCards(), game);
	}

	@Override
	public void init(Game game) {
		init(game, false);
	}

	@Override
	public void init(Game game, boolean testMode) {
		this.abort = false;
		if (!testMode) {
			this.hand.clear();
			this.graveyard.clear();
		}
		this.abilities.clear();
		this.counters.clear();
		this.wins = false;
		this.loses = false;
		this.left = false;
		this.passed = false;
		this.passedTurn = false;
	}

	@Override
	public void reset() {
		this.abilities.clear();
		this.landsPerTurn = 1;
		this.maxHandSize = 7;
		this.lifeTotalCanChange = true;
	}

	@Override
	public Counters getCounters() {
		return counters;
	}

	@Override
	public void beginTurn(Game game) {
		this.landsPlayed = 0;
		findRange(game);
	}

	protected void findRange(Game game) {
		//20100423 - 801.2c
		inRange.clear();
		if (range == RangeOfInfluence.ALL) {
			for (Player player: game.getPlayers().values()) {
				if (!player.hasLeft())
					inRange.add(player.getId());
			}
		}
		else {
			if ((range.getRange() * 2) + 1 >= game.getPlayers().size()) {
				for (Player player: game.getPlayers().values()) {
					if (!player.hasLeft())
						inRange.add(player.getId());
				}
			}
			else {
				inRange.add(playerId);
				PlayerList players = game.getState().getPlayerList(playerId);
				for (int i = 0; i < range.getRange(); i++) {
					Player player = players.getNext(game);
					while (player.hasLeft())
						player = players.getNext(game);
					inRange.add(player.getId());
				}
				players = game.getState().getPlayerList(playerId);
				for (int i = 0; i < range.getRange(); i++) {
					Player player = players.getPrevious(game);
					while (player.hasLeft())
						player = players.getPrevious(game);
					inRange.add(player.getId());
				}
			}
		}
	}

	@Override
	public Set<UUID> getInRange() {
		return inRange;
	}

	@Override
	public void endOfTurn(Game game) {
		this.passedTurn = false;
	}

	@Override
	public void checkTriggers(GameEvent event, Game game) {
		hand.checkTriggers(event, game);
		graveyard.checkTriggers(event, game);
	}

	@Override
	public boolean canBeTargetedBy(MageObject source) {
		if (this.hasLost() || this.hasLeft())
			return false;
		if (source != null) {
			if (abilities.containsKey(ShroudAbility.getInstance().getId()))
				return false;

			if (hasProtectionFrom(source))
				return false;
		}

		return true;
	}

	@Override
	public boolean hasProtectionFrom(MageObject source) {
		for (ProtectionAbility ability: abilities.getProtectionAbilities()) {
			if (!ability.canTarget(source))
				return true;
		}
		return false;
	}

	protected boolean drawCard(Game game) {
		Card card = getLibrary().removeFromTop(game);
		if (card != null) {
			card.moveToZone(Zone.HAND, null, game, false);
			game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DREW_CARD, card.getId(), playerId));
			return true;
		}
		return false;
	}

	@Override
	public int drawCards(int num, Game game) {
		int numDrawn = 0;
		for (int i = 0; i < num; i++) {
			if (drawCard(game))
				numDrawn++;
			else
				break;
		}
		game.fireInformEvent(name + " draws " + Integer.toString(numDrawn) + " card" + (numDrawn > 1?"s":""));
		return numDrawn;
	}

	@Override
	public void discardToMax(Game game) {
		while (hand.size() > this.maxHandSize) {
			TargetDiscard target = new TargetDiscard(playerId);
			chooseTarget(Outcome.Discard, target, null, game);
			discard(hand.get(target.getFirstTarget(), game), null, game);
		}
	}

	@Override
	public boolean putInHand(Card card, Game game) {
		if (card.getOwnerId().equals(playerId)) {
			this.hand.add(card);
		} else {
			return game.getPlayer(card.getOwnerId()).putInHand(card, game);
		}
		return true;
	}

	@Override
	public boolean removeFromHand(Card card, Game game) {
		hand.remove(card);
		return true;
	}
	
	@Override
	public boolean removeFromLibrary(Card card, Game game) {
		if (card == null) return false;
		library.remove(card.getId(), game);
		return true;
	}

	@Override
	public void discard(int amount, Ability source, Game game) {
		if (amount >= hand.size()) {
			int discardAmount = hand.size();
			while (hand.size() > 0) {
				discard(hand.get(hand.iterator().next(), game), source, game);
			}
			game.fireInformEvent(name + " discards " + Integer.toString(discardAmount) + " card" + (discardAmount > 1?"s":""));
			return;
		}
		for (int i = 0; i < amount; i++) {
			TargetDiscard target = new TargetDiscard(playerId);
			choose(Outcome.Discard, target, game);
			discard(hand.get(target.getFirstTarget(), game), source, game);
		}
		game.fireInformEvent(name + " discards " + Integer.toString(amount) + " card" + (amount > 1?"s":""));
	}

	@Override
	public boolean discard(Card card, Ability source, Game game) {
		//20100716 - 701.7
		removeFromHand(card, game);
		card.moveToZone(Zone.GRAVEYARD, source==null?null:source.getId(), game, false);
		
		game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DISCARDED_CARD, card.getId(), source==null?null:source.getId(), playerId));
		return true;
	}

	@Override
	public boolean removeFromBattlefield(Permanent permanent, Game game) {
		permanent.removeFromCombat(game);
		game.getBattlefield().removePermanent(permanent.getId());
		if (permanent.getAttachedTo() != null) {
			Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
			if (attachedTo != null)
				attachedTo.removeAttachment(permanent.getId(), game);
		}
		return true;
	}

	@Override
	public boolean putInGraveyard(Card card, Game game, boolean fromBattlefield) {
		if (card.getOwnerId().equals(playerId)) {
			this.graveyard.add(card);
		} else {
			return game.getPlayer(card.getOwnerId()).putInGraveyard(card, game, fromBattlefield);
		}
		return true;
	}

	@Override
	public boolean removeFromGraveyard(Card card, Game game) {
		this.graveyard.remove(card);
		return true;
	}

	@Override
	public boolean cast(SpellAbility ability, Game game, boolean noMana) {
		//20091005 - 601.2a
		Card card = game.getCard(ability.getSourceId());
		if (card != null) {
			if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.CAST_SPELL, ability.getId(), playerId))) {
				game.bookmarkState();
				card.cast(game, game.getZone(card.getId()), ability, playerId);
				removeFromHand(card, game);
				Ability spellAbility = game.getStack().getSpell(ability.getId()).getSpellAbility();
				if (spellAbility.activate(game, noMana)) {
					for (KickerAbility kicker: card.getAbilities().getKickerAbilities()) {
						kicker.activate(game, false);
					}
					game.fireEvent(GameEvent.getEvent(GameEvent.EventType.SPELL_CAST, spellAbility.getId(), playerId));
					game.fireInformEvent(name + " casts " + card.getName());
					game.removeLastBookmark();
					return true;
				}
				game.restoreState();
			}
		}
		return false;
	}

	@Override
	public boolean playLand(Card card, Game game) {
		//20091005 - 305.1
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, card.getId(), playerId))) {
			game.bookmarkState();
			removeFromHand(card, game);
			if (card.putOntoBattlefield(game, Zone.HAND, null, playerId)) {
				landsPlayed++;
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LAND_PLAYED, card.getId(), playerId));
				game.fireInformEvent(name + " plays " + card.getName());
				game.removeLastBookmark();
				return true;
			}
			game.restoreState();
		}
		return false;
	}

	protected boolean playManaAbility(ManaAbility ability, Game game) {
		game.bookmarkState();
		if (ability.activate(game, false)) {
			ability.resolve(game);
			game.removeLastBookmark();
			return true;
		}
		game.restoreState();
		return false;
	}

	protected boolean playAbility(ActivatedAbility ability, Game game) {
		//20091005 - 602.2a
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATE_ABILITY, ability.getSourceId(), ability.getId(), playerId))) {
			game.bookmarkState();
			ability.newId();
			game.getStack().push(new StackAbility(ability, playerId));
			String message = ability.getActivatedMessage(game);
			if (ability.activate(game, false)) {
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATED_ABILITY, ability.getSourceId(), ability.getId(), playerId));
				game.fireInformEvent(name + message);
				game.removeLastBookmark();
				return true;
			}
			game.restoreState();
		}
		return false;
	}

	protected boolean specialAction(SpecialAction action, Game game) {
		//20091005 - 114
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATE_ABILITY, action.getSourceId(), action.getId(), playerId))) {
			game.bookmarkState();
			if (action.activate(game, false)) {
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATED_ABILITY, action.getSourceId(), action.getId(), playerId));
				game.fireInformEvent(name + action.getActivatedMessage(game));
				if (action.resolve(game)) {
					game.removeLastBookmark();
					return true;
				}
			}
			game.restoreState();
		}
		return false;
	}

	@Override
	public boolean activateAbility(ActivatedAbility ability, Game game) {
		boolean result;
		if (!ability.canActivate(this.playerId, game))
			return false;

		if (ability instanceof PassAbility) {
			return true;
		}
		else if (ability instanceof PlayLandAbility) {
			result = playLand(hand.get(ability.getSourceId(), game), game);
		}
		else if (ability instanceof SpecialAction) {
			result = specialAction((SpecialAction)ability.copy(), game);
		}
		else if (ability instanceof ManaAbility) {
			result = playManaAbility((ManaAbility)ability.copy(), game);
		}
		else if (ability instanceof SpellAbility) {
			result = cast((SpellAbility)ability, game, false);
		}
		else {
			result = playAbility((ActivatedAbility)ability.copy(), game);
		}

		//if player has taken an action then reset all player passed flags
		if (result)
			game.getPlayers().resetPassed();
		return result;
	}

	@Override
	public boolean triggerAbility(TriggeredAbility source, Game game) {
		//20091005 - 603.3c, 603.3d
		game.bookmarkState();
		TriggeredAbility ability = (TriggeredAbility) source.copy();
		if (ability.getTargets().canChoose(ability.getSourceId(), playerId, game)) {
			game.getStack().push(new StackAbility(ability, playerId));
			if (ability.activate(game, false)) {
				game.removeLastBookmark();
				return true;
			}
		}
		game.restoreState();
		return false;
	}

	protected Map<UUID, ActivatedAbility> getUseableAbilities(List<? extends ActivatedAbility> abilities, Game game) {
		Map<UUID, ActivatedAbility> useable = new HashMap<UUID, ActivatedAbility>();
		for (ActivatedAbility ability: abilities) {
			if (ability.canActivate(playerId, game))
				useable.put(ability.getId(), ability);
		}
		return useable;
	}

	@Override
	public int getLandsPlayed() {
		return landsPlayed;
	}

	@Override
	public boolean canPlayLand() {
		//20091005 - 114.2a
		return landsPlayed < landsPerTurn;
	}

	protected boolean isActivePlayer(Game game) {
		return game.getActivePlayerId().equals(this.playerId);
	}

	@Override
	public void shuffleLibrary(Game game) {
		this.library.shuffle();
	}

	@Override
	public void revealCards(String name, Cards cards, Game game) {
		game.getState().getRevealed().add(name, cards);
//		game.fireRevealCardsEvent(this.name + " revealed", cards);
	}

	@Override
	public void lookAtCards(String name, Cards cards, Game game) {
		game.getState().getLookedAt(this.playerId).add(name, cards);
		game.fireLookAtCardsEvent(playerId, this.name + " looking at", cards);
	}

	@Override
	public void phasing(Game game) {
		//20091005 - 502.1
		for (Permanent permanent: game.getBattlefield().getPhasedIn(playerId)) {
			permanent.phaseOut(game);
		}
		for (Permanent permanent: game.getBattlefield().getPhasedOut(playerId)) {
			permanent.phaseIn(game);
		}
	}

	@Override
	public void untap(Game game) {
		//20091005 - 502.2
		for (Permanent permanent: game.getBattlefield().getAllActivePermanents(playerId)) {
			permanent.untap(game);
		}
	}

	@Override
	public UUID getId() {
		return playerId;
	}

	@Override
	public Cards getHand() {
		return hand;
	}

	@Override
	public Cards getGraveyard() {
		return graveyard;
	}

	@Override
	public ManaPool getManaPool() {
		return this.manaPool;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isHuman() {
		return human;
	}

	@Override
	public Library getLibrary() {
		return library;
	}

	@Override
	public int getLife() {
		return life;
	}

	@Override
	public void setLife(int life, Game game) {
		if (this.lifeTotalCanChange) this.life = life;
	}

	@Override
	public void setLifeTotalCanChange(boolean lifeTotalCanChange) {
		this.lifeTotalCanChange = lifeTotalCanChange;
	}

	@Override
	public boolean isLifeTotalCanChange() {
		return this.lifeTotalCanChange;
	}

	@Override
	public int loseLife(int amount, Game game) {
		if (!lifeTotalCanChange) return 0;
		GameEvent event = new GameEvent(GameEvent.EventType.LOSE_LIFE, playerId, playerId, playerId, amount, false);
		if (!game.replaceEvent(event)) {
			setLife(this.life - amount, game);
			game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LOST_LIFE, playerId, playerId, playerId, amount));
			return amount;
		}
		return 0;
	}

	@Override
	public void gainLife(int amount, Game game) {
		if (!lifeTotalCanChange) return;
		GameEvent event = new GameEvent(GameEvent.EventType.GAIN_LIFE, playerId, playerId, playerId, amount, false);
		if (!game.replaceEvent(event)) {
			setLife(this.life + amount, game);
			game.fireEvent(GameEvent.getEvent(GameEvent.EventType.GAINED_LIFE, playerId, playerId, playerId, amount));
		}
	}

	@Override
	public int damage(int damage, UUID sourceId, Game game, boolean combatDamage, boolean preventable) {
		if (damage > 0 && canDamage(game.getObject(sourceId))) {
			GameEvent event = new DamagePlayerEvent(playerId, sourceId, playerId, damage, preventable, combatDamage);
			if (!game.replaceEvent(event)) {
				int actualDamage = event.getAmount();
				if (actualDamage > 0) {
					Permanent source = game.getPermanent(sourceId);
					if (source != null && (source.getAbilities().containsKey(InfectAbility.getInstance().getId()))) {
						getCounters().addCounter(CounterType.POISON.createInstance(actualDamage));
					} else {
						// fixed: damage dealt should not be equal to life lost
						// actualDamage = this.loseLife(actualDamage, game);
						this.loseLife(actualDamage, game);
					}
					if (source != null && source.getAbilities().containsKey(LifelinkAbility.getInstance().getId())) {
						Player player = game.getPlayer(source.getControllerId());
						player.gainLife(damage, game);
					}
					game.fireEvent(new DamagedPlayerEvent(playerId, sourceId, playerId, actualDamage, combatDamage));
					return actualDamage;
				}
			}
		}
		return 0;
	}

	protected boolean canDamage(MageObject source) {
		for (ProtectionAbility ability: abilities.getProtectionAbilities()) {
			if (!ability.canTarget(source))
				return false;
		}
		return true;
	}

	@Override
	public Abilities getAbilities() {
		return this.abilities;
	}

	@Override
	public int getLandsPerTurn() {
		return this.landsPerTurn;
	}

	@Override
	public void setLandsPerTurn(int landsPerTurn) {
		this.landsPerTurn = landsPerTurn;
	}

	@Override
	public int getMaxHandSize() {
		return maxHandSize;
	}

	@Override
	public void setMaxHandSize(int maxHandSize) {
		this.maxHandSize = maxHandSize;
	}

	@Override
	public void setResponseString(String responseString) {}

	@Override
	public void setResponseUUID(UUID responseUUID) {}

	@Override
	public void setResponseBoolean(Boolean responseBoolean) {}

	@Override
	public void setResponseInteger(Integer responseInteger) {}

	@Override
	public void restore(Player player) {
		this.library = player.getLibrary();
		this.hand = player.getHand();
		this.graveyard = player.getGraveyard();
		this.abilities = player.getAbilities();
		this.manaPool = player.getManaPool();
		this.life = player.getLife();
		this.counters = player.getCounters();
		this.inRange = player.getInRange();
		this.landsPlayed = player.getLandsPlayed();
		this.name = player.getName();
	}

	@Override
	public boolean isPassed() {
		return passed;
	}

	@Override
	public void pass() {
		this.passed = true;
	}

	@Override
	public boolean isEmptyDraw() {
		return library.isEmtpyDraw();
	}

	@Override
	public void resetPassed() {
		if (!this.loses && !this.left)
			this.passed = false;
		else
			this.passed = true;
	}

	@Override
	public void concede(Game game) {
		this.loses = true;
		this.abort();
		game.leave(playerId);
	}

	@Override
	public void leave() {
		this.passed = true;
		this.abort();
		this.loses = true;
		this.left = true;
		//20100423 - 800.4a
		this.hand.clear();
		this.graveyard.clear();
		this.library.clear();
	}

	@Override
	public boolean hasLeft() {
		return this.left;
	}

	@Override
	public void lost(Game game) {
		if (!game.replaceEvent(new GameEvent(GameEvent.EventType.LOSES, null, null, playerId))) {
			this.loses = true;
			//20100423 - 603.9
			if (!this.wins)
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LOST, null, null, playerId));
			game.leave(playerId);
		}
	}

	@Override
	public void won(Game game) {
		if (!game.replaceEvent(new GameEvent(GameEvent.EventType.WINS, null, null, playerId))) {
			if (!this.loses) {
				//20100423 - 800.6, 801.16
				if (game.getPlayers().size() > 2) {
					for (UUID opponentId: game.getOpponents(playerId)) {
						game.getPlayer(opponentId).lost(game);
					}
				}
				else {
					this.wins = true;
					game.end();
				}
			}
		}
	}

	@Override
	public boolean hasLost() {
		return this.loses;
	}

	@Override
	public boolean hasWon() {
		if (!this.loses)
			return this.wins;
		else
			return false;
	}

	@Override
	public void declareAttacker(UUID attackerId, UUID defenderId, Game game) {
		Permanent attacker = game.getPermanent(attackerId);
		if (attacker != null && attacker.canAttack(game)) {
			if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_ATTACKER, defenderId, attackerId, playerId))) {
				game.getCombat().declareAttacker(attackerId, defenderId, game);
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ATTACKER_DECLARED, defenderId, attackerId, playerId));
			}
		}
	}

	@Override
	public void declareBlocker(UUID blockerId, UUID attackerId, Game game) {
		Permanent blocker = game.getPermanent(blockerId);
		CombatGroup group = game.getCombat().findGroup(attackerId);
		if (blocker != null && group != null && group.canBlock(blocker, game)) {
			group.addBlocker(blockerId, playerId, game);
		}
	}

	@Override
	public boolean searchLibrary(TargetCardInLibrary target, Game game) {
		//20091005 - 701.14c
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.SEARCH_LIBRARY, playerId, playerId))) {
			TargetCardInLibrary newTarget;
			int count = library.count(target.getFilter(), game);
			if (count < target.getNumberOfTargets())
				newTarget = new TargetCardInLibrary(library.count(target.getFilter(), game), target.getMaxNumberOfTargets(), target.getFilter());
			else
				newTarget = target;
			if (newTarget.choose(Outcome.Neutral, playerId, game)) {
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LIBRARY_SEARCHED, playerId, playerId));
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @return true if player won the toss
	 */
	@Override
	public boolean flipCoin() {
		return new Random().nextBoolean();
	}

	@Override
	public List<Permanent> getAvailableAttackers(Game game) {
		FilterCreatureForAttack attackFilter = new FilterCreatureForAttack();
		attackFilter.getControllerId().add(playerId);
		List<Permanent> attackers = game.getBattlefield().getAllActivePermanents(attackFilter);
		return attackers;
	}

	@Override
	public List<Permanent> getAvailableBlockers(Game game) {
		FilterCreatureForCombat blockFilter = new FilterCreatureForCombat();
		List<Permanent> blockers = game.getBattlefield().getAllActivePermanents(blockFilter, playerId);
		return blockers;
	}

	protected ManaOptions getManaAvailable(Game game) {
		List<Permanent> manaPerms = this.getAvailableManaProducers(game);

		ManaOptions available = new ManaOptions();
		for (Permanent perm: manaPerms) {
			available.addMana(perm.getAbilities().getManaAbilities(Zone.BATTLEFIELD), game);
		}
		return available;
	}

	protected List<Permanent> getAvailableManaProducers(Game game) {
		List<Permanent> result = new ArrayList<Permanent>();
		for (Permanent permanent: game.getBattlefield().getAllActivePermanents(playerId)) {
			for (ManaAbility ability: permanent.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
				if (ability.canActivate(playerId, game)) {
					result.add(permanent);
					break;
				}
			}
		}
		return result;
	}

	protected boolean canPlay(ActivatedAbility ability, ManaOptions available, Game game) {
		if (!(ability instanceof ManaAbility) && ability.canActivate(playerId, game)) {
			ManaOptions abilityOptions = ability.getManaCosts().getOptions();
			if (abilityOptions.size() == 0) {
				return true;
			}
			else {
				for (Mana mana: abilityOptions) {
					for (Mana avail: available) {
						if (mana.enough(avail)) {
							return true;
						}
					}
				}
			}

		}
		return false;
	}

	@Override
	public List<Ability> getPlayable(Game game, FilterAbility filter, ManaOptions available, boolean hidden) {
		List<Ability> playable = new ArrayList<Ability>();

//		ManaOptions available = getManaAvailable(game);
//		available.addMana(manaPool.getMana());

		if (hidden) {
			for (Card card: hand.getUniqueCards(game)) {
				for (ActivatedAbility ability: card.getAbilities().getActivatedAbilities(Zone.HAND, filter)) {
					if (canPlay(ability, available, game))
						playable.add(ability);
				}
			}
		}
		for (Card card: graveyard.getUniqueCards(game)) {
			for (ActivatedAbility ability: card.getAbilities().getActivatedAbilities(Zone.GRAVEYARD, filter)) {
				if (canPlay(ability, available, game))
					playable.add(ability);
			}
		}
		for (Permanent permanent: game.getBattlefield().getAllActivePermanents(playerId)) {
			for (ActivatedAbility ability: permanent.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD, filter)) {
				if (canPlay(ability, available, game))
					playable.add(ability);
			}
		}
		return playable;
	}

	@Override
	public List<Ability> getPlayableOptions(Ability ability, Game game) {
		List<Ability> options = new ArrayList<Ability>();
		
		if (ability.isModal())
			addModeOptions(options, ability, game);
		else if (ability.getTargets().getUnchosen().size() > 0)
			addTargetOptions(options, ability, 0, game);
		else if (ability.getChoices().getUnchosen().size() > 0)
			addChoiceOptions(options, ability, 0, game);
		else if (ability.getCosts().getTargets().getUnchosen().size() > 0)
			addCostTargetOptions(options, ability, 0, game);

		return options;
	}

	private void addModeOptions(List<Ability> options, Ability option, Game game) {
		for (Mode mode: option.getModes().values()) {
			Ability newOption = option.copy();
			newOption.getModes().setMode(mode);
			if (option.getTargets().getUnchosen().size() > 0)
				addTargetOptions(options, option, 0, game);
			else if (option.getChoices().getUnchosen().size() > 0)
				addChoiceOptions(options, option, 0, game);
			else if (option.getCosts().getTargets().getUnchosen().size() > 0)
				addCostTargetOptions(options, option, 0, game);
			else
				options.add(newOption);
		}
	}
	
	private void addTargetOptions(List<Ability> options, Ability option, int targetNum, Game game) {
		for (UUID targetId: option.getTargets().getUnchosen().get(targetNum).possibleTargets(option.getSourceId(), playerId, game)) {
			Ability newOption = option.copy();
			newOption.getTargets().get(targetNum).addTarget(targetId, option, game);
			if (targetNum < option.getTargets().size() - 2) {
				//addTargetOptions(options, newOption, targetNum + 1, game);
				// ayrat: bug fix
				addTargetOptions(options, newOption, targetNum + 1, game);
			}
			else {
				if (option.getChoices().size() > 0)
					addChoiceOptions(options, newOption, 0, game);
				else if (option.getCosts().getTargets().size() > 0)
					addCostTargetOptions(options, newOption, 0, game);
				else
					options.add(newOption);
			}
		}
	}

	private void addChoiceOptions(List<Ability> options, Ability option, int choiceNum, Game game) {
		for (String choice: option.getChoices().get(choiceNum).getChoices()) {
			Ability newOption = option.copy();
			newOption.getChoices().get(choiceNum).setChoice(choice);
			if (choiceNum < option.getChoices().size() - 1) {
				addChoiceOptions(options, newOption, choiceNum + 1, game);
			}
			else {
				if (option.getCosts().getTargets().size() > 0)
					addCostTargetOptions(options, newOption, 0, game);
				else
					options.add(newOption);
			}
		}
	}

	private void addCostTargetOptions(List<Ability> options, Ability option, int targetNum, Game game) {
		for (UUID targetId: option.getCosts().getTargets().get(targetNum).possibleTargets(option.getSourceId(), playerId, game)) {
			Ability newOption = option.copy();
			newOption.getCosts().getTargets().get(targetNum).addTarget(targetId, option, game);
			if (targetNum < option.getCosts().getTargets().size() - 1) {
				addCostTargetOptions(options, newOption, targetNum + 1, game);
			}
			else {
				options.add(newOption);
			}
		}
	}

	@Override
	public boolean isTestMode() {
		return isTestMode;
	}

	@Override
	public void setTestMode(boolean value) {
		this.isTestMode = value;
	}
}
