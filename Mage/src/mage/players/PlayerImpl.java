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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.ActivatedAbility;
import mage.abilities.PlayLandAbility;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.mana.ManaAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.decks.Deck;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetDiscard;
import mage.util.Copier;

public abstract class PlayerImpl implements Player, Serializable {

	private static final long serialVersionUID = 1L;

	protected UUID playerId;
	protected String name;
	protected boolean human;
	protected int life;
	protected boolean wins;
	protected boolean loses;
	protected Deck deck;
	protected Library library;
	protected Cards hand = new CardsImpl(Zone.HAND);
	protected Cards graveyard = new CardsImpl(Zone.GRAVEYARD);
	protected Abilities abilities = new AbilitiesImpl();
	protected Counters counters = new Counters();
	protected int landsPlayed;
	protected int landsPerTurn = 1;
	protected int maxHandSize = 7;
	protected ManaPool manaPool = new ManaPool();
	protected boolean passed;
	protected boolean passedTurn;
	protected boolean left;

	public PlayerImpl(String name) {
		this.playerId = UUID.randomUUID();
		this.name = name;
		library = new Library(playerId);
	}

	@Override
	public void init(Game game) {
		this.hand.clear();
		this.graveyard.clear();
		this.abilities.clear();
		this.library.clear();
		this.wins = false;
		this.loses = false;
		this.left = false;
		this.passed = false;
		this.passedTurn = false;
		library.addAll(deck.getCards());
		for (Card card: deck.getCards().values()) {
			game.getState().getWatchers().addAll(card.getWatchers());
		}
	}

	@Override
	public void reset() {
		this.abilities.clear();
		this.landsPerTurn = 1;
		this.maxHandSize = 7;
	}

	@Override
	public Counters getCounters() {
		return counters;
	}

	@Override
	public void beginTurn() {
		this.landsPlayed = 0;
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
	public boolean canTarget(MageObject source) {
		if (source != null) {
			if (abilities.containsKey(ShroudAbility.getInstance().getId()))
				return false;

			for (ProtectionAbility ability: abilities.getProtectionAbilities()) {
				if (!ability.canTarget(source))
					return false;
			}
		}

		return true;
	}

//	@Override
	protected boolean drawCard(Game game) {
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DRAW_CARD, null, playerId))) {
			Card card = getLibrary().removeFromTop(game);
			if (card != null) {
				putInHand(card, game);
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DREW_CARD, card.getId(), playerId));
				return true;
			}
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
			chooseTarget(Outcome.Discard, target, game);
			discard(hand.get(target.getFirstTarget()), game);
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
	public void discard(int amount, Game game) {
		if (amount >= hand.size()) {
			int discardAmount = hand.size();
			for (Card card: hand.values()) {
				discard(card, game);
			}
			game.fireInformEvent(name + " discards " + Integer.toString(discardAmount) + " card" + (discardAmount > 1?"s":""));
			return;
		}
		for (int i = 0; i < amount; i++) {
			TargetDiscard target = new TargetDiscard(playerId);
			chooseTarget(Outcome.Discard, target, game);
			discard(hand.get(target.getFirstTarget()), game);
		}
		game.fireInformEvent(name + " discards " + Integer.toString(amount) + " card" + (amount > 1?"s":""));
	}

	@Override
	public boolean discard(Card card, Game game) {
		//20091005 - 701.1
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DISCARD_CARD, playerId, playerId))) {
			removeFromHand(card, game);
			putInGraveyard(card, game, false);
			game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DISCARDED_CARD, playerId, playerId));
			return true;
		}
		return false;
	}

	@Override
	public boolean putOntoBattlefield(Card card, Game game) {
		PermanentCard permanent = new PermanentCard(card, playerId);
		putOntoBattlefield(permanent, game);
		return true;
	}

	@Override
	public boolean putOntoBattlefield(Token token, Game game) {
		PermanentToken permanent = new PermanentToken(token, playerId, playerId);
		putOntoBattlefield(permanent, game);
		return true;
	}

	protected void putOntoBattlefield(Permanent permanent, Game game) {
		game.getBattlefield().addPermanent(permanent);
		permanent.entersBattlefield(game);
		game.fireEvent(new ZoneChangeEvent(permanent.getId(), playerId, Zone.ALL, Zone.BATTLEFIELD));
	}

	@Override
	public boolean removeFromBattlefield(Permanent permanent, Game game) {
		permanent.removeFromCombat(game);
		game.getBattlefield().removePermanent(permanent.getId());
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
	public boolean cast(Card card, Game game, boolean noMana) {
		//20091005 - 601.2a
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.CAST_SPELL, card.getId(), playerId))) {
			game.bookmarkState();
			removeFromHand(card, game);

			game.getStack().push(new Spell(card, playerId));
			card.getSpellAbility().clear();
			if (card.getSpellAbility().activate(game, noMana)) {
				for (KickerAbility ability: card.getAbilities().getKickerAbilities()) {
					ability.copy().activate(game, false);
				}
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.SPELL_CAST, card.getId(), playerId));
				game.removeLastBookmark();
				return true;
			}
		}
		game.restoreState();
		return false;
	}

	@Override
	public boolean playLand(Card card, Game game) {
		//20091005 - 305.1
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, card.getId(), playerId))) {
			game.bookmarkState();
			removeFromHand(card, game);
			if (putOntoBattlefield(card, game)) {
				landsPlayed++;
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LAND_PLAYED, card.getId(), playerId));
				game.fireInformEvent(name + " plays " + card.getName());
				game.removeLastBookmark();
				return true;
			}
		}
		game.restoreState();
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
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATE_ABILITY, ability.getId(), playerId))) {
			game.bookmarkState();
			game.getStack().push(new StackAbility(ability, playerId));
			if (ability.activate(game, false)) {
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ACTIVATED_ABILITY, ability.getId(), playerId));
				game.fireInformEvent(name + ability.getActivatedMessage(game));
				game.removeLastBookmark();
				return true;
			}
		}
		game.restoreState();
		return false;
	}

	@Override
	public boolean activateAbility(ActivatedAbility ability, Game game) {
		boolean result;
		if (!ability.canActivate(this.playerId, game))
			return false;

		if (ability instanceof PlayLandAbility) {
			result = playLand(hand.get(ability.getSourceId()), game);
		}
		else if (ability instanceof ManaAbility) {
			result = playManaAbility((ManaAbility)ability.copy(), game);
		}
		else if (ability instanceof SpellAbility) {
			result = cast(hand.get(ability.getSourceId()), game, false);
		}
		else {
			result = playAbility((ActivatedAbility)ability.copy(), game);
		}

		return result;
	}

	@Override
	public boolean triggerAbility(TriggeredAbility source, Game game) {
		//20091005 - 603.3c, 603.3d
		game.saveState();
		game.bookmarkState();
		TriggeredAbility ability = (TriggeredAbility) source.copy();
		game.getStack().push(new StackAbility(ability, playerId));
		if (ability.activate(game, false)) {
			game.removeLastBookmark();
			return true;
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
	public void revealCards(Cards cards, Game game) {
		//TODO: implement this
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
		for (Permanent permanent: game.getBattlefield().getAllPermanents(playerId)) {
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
	public Deck getDeck() {
		return deck;
	}

	@Override
	public void setDeck(Deck deck) {
		this.deck = deck;
		deck.setOwnerId(playerId);
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
		this.life = life;
	}

	@Override
	public int loseLife(int amount, Game game) {
		if (amount > life) {
			int curLife = life;
			setLife(0, game);
			return curLife;
		}
		else {
			setLife(this.life - amount, game);
			return amount;
		}
	}

	@Override
	public void gainLife(int amount, Game game) {
		setLife(this.life + amount, game);
	}

	@Override
	public int damage(int damage, UUID sourceId, Game game) {
		if (damage > 0 && canDamage(game.getObject(sourceId))) {
			GameEvent event = new GameEvent(GameEvent.EventType.DAMAGE_PLAYER, playerId, sourceId, playerId, damage);
			if (!game.replaceEvent(event)) {
				int actualDamage = event.getAmount();
				if (actualDamage > 0) {
					actualDamage = this.loseLife(actualDamage, game);
					game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DAMAGED_PLAYER, playerId, sourceId, playerId, actualDamage));
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
	public void setResponseString(String responseString) {}

	@Override
	public void setResponseUUID(UUID responseUUID) {}

	@Override
	public void setResponseBoolean(Boolean responseBoolean) {}

	@Override
	public void setResponseInteger(Integer responseInteger) {}

	@Override
	public Player copy() {
		return new Copier<Player>().copy(this);
	}

	@Override
	public void restore(Player player) {
		this.library = player.getLibrary();
		this.deck = player.getDeck();
		this.hand = player.getHand();
		this.graveyard = player.getGraveyard();
		this.abilities = player.getAbilities();
		this.manaPool = player.getManaPool();
		this.life = player.getLife();
		this.counters = player.getCounters();
	}

	@Override
	public boolean isPassed() {
		return passed;
	}

	@Override
	public boolean isEmptyDraw() {
		return library.isEmtpyDraw();
	}

	@Override
	public void resetPriority() {
		passed = false;
	}

	@Override
	public void concede() {
		this.loses = true;
	}

	@Override
	public void leaveGame() {
		this.loses = true;
		this.left = true;
	}

	@Override
	public boolean hasLeft() {
		return this.left;
	}

	@Override
	public void lost(Game game) {
		if (!game.replaceEvent(new GameEvent(GameEvent.EventType.LOSES, null, null, playerId))) {
			this.loses = true;
		}
	}

	@Override
	public void won(Game game) {
		if (!game.replaceEvent(new GameEvent(GameEvent.EventType.WINS, null, null, playerId))) {
			if (!this.loses) {
				this.wins = true;
				game.end();
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
		if (attacker.canAttack(game)) {
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
		if (group != null && group.canBlock(blocker, game)) {
			group.addBlocker(blockerId, playerId, game);
		}
	}

	@Override
	public boolean searchLibrary(TargetCardInLibrary target, Game game) {
		//20091005 - 701.14c
		if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.SEARCH_LIBRARY, playerId, playerId))) {
			if (library.count(target.getFilter()) < target.getNumberOfTargets()) {
				TargetCardInLibrary newTarget = new TargetCardInLibrary(library.count(target.getFilter()), target.getMaxNumberOfTargets(), target.getFilter());
				searchCards(new CardsImpl(Zone.LIBRARY, getLibrary().getCards()), newTarget, game);
			}
			else {
				searchCards(new CardsImpl(Zone.LIBRARY, getLibrary().getCards()), target, game);
			}
			game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LIBRARY_SEARCHED, playerId, playerId));
			return true;
		}
		return false;
	}

}
