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

package mage.game.permanent;

import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.events.*;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class PermanentImpl<T extends PermanentImpl<T>> extends CardImpl<T> implements Permanent {

	protected boolean tapped;
	protected boolean flipped;
	protected UUID controllerId;
	protected int damage;
	protected boolean controlledFromStartOfTurn;
	protected int turnsOnBattlefield;
	protected boolean phasedIn = true;
	protected boolean faceUp = true;
	protected boolean attacking;
	protected int blocking;
	protected int maxBlocks = 1;
	protected boolean loyaltyUsed;
	protected boolean deathtouched;
	protected Counters counters;
	protected List<UUID> attachments = new ArrayList<UUID>();
	protected UUID attachedTo;

	public PermanentImpl(UUID ownerId, UUID controllerId, String name) {
		super(ownerId, name);
		this.controllerId = controllerId;
		this.counters = new Counters();
	}

	public PermanentImpl(UUID id, UUID ownerId, UUID controllerId, String name) {
		super(id, ownerId, name);
		this.controllerId = controllerId;
		this.counters = new Counters();
	}

	public PermanentImpl(final PermanentImpl<T> permanent) {
		super(permanent);
		this.tapped = permanent.tapped;
		this.flipped = permanent.flipped;
		this.controllerId = permanent.controllerId;
		this.damage = permanent.damage;
		this.controlledFromStartOfTurn = permanent.controlledFromStartOfTurn;
		this.turnsOnBattlefield = permanent.turnsOnBattlefield;
		this.phasedIn = permanent.phasedIn;
		this.faceUp = permanent.faceUp;
		this.attacking = permanent.attacking;
		this.blocking = permanent.blocking;
		this.maxBlocks = permanent.maxBlocks;
		this.loyaltyUsed = permanent.loyaltyUsed;
		this.deathtouched = permanent.deathtouched;
		this.counters = permanent.counters.copy();
		for (UUID attachmentId : permanent.attachments) {
			this.attachments.add(attachmentId);
		}
		this.attachedTo = permanent.attachedTo;
	}

	@Override
	public String toString() {
		return this.name + "-" + this.expansionSetCode;
	}

	@Override
	public void reset(Game game) {
//		this.controllerId = ownerId;
		this.maxBlocks = 1;
	}

	@Override
	public String getValue() {
		StringBuilder sb = new StringBuilder(1024);
		sb.append(controllerId).append(name).append(tapped).append(damage);
		sb.append(subtype).append(supertype).append(power.getValue()).append(toughness.getValue());
		sb.append(abilities);
		return sb.toString();
	}

	@Override
	public void addAbility(Ability ability) {
		Ability copy = ability.copy();
		copy.setControllerId(controllerId);
		copy.setSourceId(objectId);
		abilities.add(copy);
	}

	@Override
	public Counters getCounters() {
		return counters;
	}

	@Override
	public void addCounters(String name, int amount) {
		counters.addCounter(name, amount);
	}

	@Override
	public void addCounters(Counter counter) {
		counters.addCounter(counter);
	}

	@Override
	public void removeCounters(String name, int amount, Game game) {
		counters.removeCounter(name, amount);
		GameEvent event = GameEvent.getEvent(EventType.COUNTER_REMOVED, objectId, controllerId);
		event.setData(name);
		for (int i = 0; i < amount; i++)
			game.fireEvent(event);
	}

	@Override
	public int getTurnsOnBattlefield() {
		return turnsOnBattlefield;
	}

	@Override
	public void endOfTurn(Game game) {
		this.damage = 0;
		this.loyaltyUsed = false;
		this.turnsOnBattlefield++;
		for (Ability ability : this.abilities) {
			ability.reset(game);
		}
		this.controllerId = this.ownerId;
	}

	@Override
	public void checkTriggers(GameEvent event, Game game) {
		if (event.getType() == EventType.BEGINNING_PHASE_PRE && game.getActivePlayerId().equals(controllerId))
			this.controlledFromStartOfTurn = true;
		for (TriggeredAbility ability : abilities.getTriggeredAbilities(Zone.BATTLEFIELD)) {
			if (ability.checkTrigger(event, game)) {
				ability.trigger(game, controllerId);
			}
		}
	}

	@Override
	public void setLoyaltyUsed(boolean used) {
		this.loyaltyUsed = used;
	}

	@Override
	public boolean isLoyaltyUsed() {
		return this.loyaltyUsed;
	}

	@Override
	public boolean isTapped() {
		return tapped;
	}

	@Override
	public void setTapped(boolean tapped) {
		this.tapped = tapped;
	}

	@Override
	public boolean canTap() {
		//20100423 - 302.6
		if (!cardType.contains(CardType.CREATURE) || !hasSummoningSickness()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean untap(Game game) {
		//20091005 - 701.15b
		if (tapped) {
			if (!replaceEvent(EventType.UNTAP, game)) {
				this.tapped = false;
				fireEvent(EventType.UNTAPPED, game);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean tap(Game game) {
		//20091005 - 701.15a
		if (!tapped) {
			if (!replaceEvent(EventType.TAP, game)) {
				this.tapped = true;
				fireEvent(EventType.TAPPED, game);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isFlipped() {
		return flipped;
	}

	@Override
	public boolean unflip(Game game) {
		if (flipped) {
			if (!replaceEvent(EventType.UNFLIP, game)) {
				this.flipped = false;
				fireEvent(EventType.UNFLIPPED, game);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean flip(Game game) {
		if (!flipped) {
			if (!replaceEvent(EventType.FLIP, game)) {
				this.flipped = true;
				fireEvent(EventType.FLIPPED, game);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isPhasedIn() {
		return phasedIn;
	}

	@Override
	public boolean phaseIn(Game game) {
		if (!phasedIn) {
			if (!replaceEvent(EventType.PHASE_IN, game)) {
				this.phasedIn = true;
				fireEvent(EventType.PHASED_IN, game);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean phaseOut(Game game) {
		if (phasedIn) {
			if (!replaceEvent(EventType.PHASE_OUT, game)) {
				this.phasedIn = false;
				fireEvent(EventType.PHASED_OUT, game);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isFaceUp() {
		return faceUp;
	}

	@Override
	public boolean turnFaceUp(Game game) {
		//TODO: implement this
		return false;
	}

	@Override
	public boolean turnFaceDown(Game game) {
		//TODO: implement this
		return false;
	}

	@Override
	public boolean hasSummoningSickness() {
		return !(this.controlledFromStartOfTurn || this.abilities.containsKey(HasteAbility.getInstance().getId()));
	}

	@Override
	public boolean isAttacking() {
		return attacking;
	}

	@Override
	public int getBlocking() {
		return blocking;
	}

	@Override
	public int getMaxBlocks() {
		return maxBlocks;
	}

	@Override
	public UUID getControllerId() {
		return this.controllerId;
	}

	@Override
	public boolean changeControllerId(UUID controllerId, Game game) {
		if (!controllerId.equals(this.controllerId)) {
			Player newController = game.getPlayer(controllerId);
			if (newController != null && (!newController.hasLeft() || !newController.hasLost())) {
				this.removeFromCombat(game);
				this.controlledFromStartOfTurn = false;
				this.controllerId = controllerId;
				this.abilities.setControllerId(controllerId);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<UUID> getAttachments() {
		return attachments;
	}

	@Override
	public boolean addAttachment(UUID permanentId, Game game) {
		if (!this.attachments.contains(permanentId)) {
			if (!game.replaceEvent(new GameEvent(GameEvent.EventType.ATTACH, objectId, permanentId, controllerId))) {
				this.attachments.add(permanentId);
				Permanent attachment = game.getPermanent(permanentId);
				if (attachment != null) {
					attachment.attachTo(objectId, game);
					game.fireEvent(new GameEvent(GameEvent.EventType.ATTACHED, objectId, permanentId, controllerId));
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean removeAttachment(UUID permanentId, Game game) {
		if (this.attachments.contains(permanentId)) {
			if (!game.replaceEvent(new GameEvent(GameEvent.EventType.UNATTACH, objectId, permanentId, controllerId))) {
				this.attachments.remove(permanentId);
				Permanent attachment = game.getPermanent(permanentId);
				if (attachment != null) {
					attachment.attachTo(null, game);
				}
				game.fireEvent(new GameEvent(GameEvent.EventType.UNATTACHED, objectId, permanentId, controllerId));
				return true;
			}
		}
		return false;
	}

	@Override
	public UUID getAttachedTo() {
		return attachedTo;
	}

	@Override
	public void attachTo(UUID permanentId, Game game) {
		if (this.attachedTo != null) {
			Permanent attachment = game.getPermanent(this.attachedTo);
			if (attachment != null) {
				attachment.removeAttachment(this.objectId, game);
			}
		}
		this.attachedTo = permanentId;
	}

	@Override
	public boolean isDeathtouched() {
		return deathtouched;
	}

	@Override
	public int getDamage() {
		return this.damage;
	}

	@Override
	public int damage(int damageAmount, UUID sourceId, Game game, boolean preventable, boolean combat) {
		int damageDone = 0;
		if (damageAmount > 0 && canDamage(game.getObject(sourceId))) {
			if (cardType.contains(CardType.PLANESWALKER)) {
				damageDone = damagePlaneswalker(damageAmount, sourceId, game, preventable, combat);
			} else {
				damageDone = damageCreature(damageAmount, sourceId, game, preventable, combat);
			}
			if (damageDone > 0) {
				Permanent source = game.getPermanent(sourceId);
				if (source != null && source.getAbilities().containsKey(LifelinkAbility.getInstance().getId())) {
					Player player = game.getPlayer(source.getControllerId());
					player.gainLife(damageDone, game);
				}
				if (source != null && source.getAbilities().containsKey(DeathtouchAbility.getInstance().getId())) {
					deathtouched = true;
				}
			}
		}
		return damageDone;
	}

	@Override
	public void removeAllDamage(Game game) {
		damage = 0;
	}

	protected int damagePlaneswalker(int damage, UUID sourceId, Game game, boolean preventable, boolean combat) {
		GameEvent event = new DamagePlaneswalkerEvent(objectId, sourceId, controllerId, damage, preventable, combat);
		if (!game.replaceEvent(event)) {
			int actualDamage = event.getAmount();
			if (actualDamage > 0) {
				if (event.getAmount() > this.loyalty.getValue()) {
					actualDamage = this.loyalty.getValue();
				}
				this.loyalty.boostValue(-actualDamage);
				game.fireEvent(new DamagedPlaneswalkerEvent(objectId, sourceId, controllerId, actualDamage, combat));
				return actualDamage;
			}
		}
		return 0;
	}

	protected int damageCreature(int damage, UUID sourceId, Game game, boolean preventable, boolean combat) {
		GameEvent event = new DamageCreatureEvent(objectId, sourceId, controllerId, damage, preventable, combat);
		if (!game.replaceEvent(event)) {
			int actualDamage = event.getAmount();
			if (actualDamage > 0) {
				if (this.damage + event.getAmount() > this.toughness.getValue()) {
					actualDamage = this.toughness.getValue() - this.damage;
				}
				Permanent source = game.getPermanent(sourceId);
				if (source != null && (source.getAbilities().containsKey(InfectAbility.getInstance().getId())
						|| source.getAbilities().containsKey(WitherAbility.getInstance().getId()))) {
					addCounters(CounterType.M1M1.createInstance(actualDamage));
				} else {
					this.damage += actualDamage;
				}
				game.fireEvent(new DamagedCreatureEvent(objectId, sourceId, controllerId, actualDamage, combat));
				return actualDamage;
			}
		}
		return 0;
	}

	@Override
	public void entersBattlefield(UUID sourceId, Game game) {
		controlledFromStartOfTurn = false;
		game.replaceEvent(GameEvent.getEvent(EventType.ENTERS_THE_BATTLEFIELD, objectId, sourceId, ownerId));
	}

	@Override
	public boolean canBeTargetedBy(MageObject source) {
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
		for (ProtectionAbility ability : abilities.getProtectionAbilities()) {
			if (!ability.canTarget(source))
				return true;
		}
		return false;
	}

	protected boolean canDamage(MageObject source) {
		return (!hasProtectionFrom(source));
	}

	@Override
	public boolean destroy(UUID sourceId, Game game, boolean noRegen) {
		//20091005 - 701.6
		//TODO: handle noRegen
		if (!game.replaceEvent(GameEvent.getEvent(EventType.DESTROY_PERMANENT, objectId, sourceId, controllerId, noRegen ? 1 : 0))) {
			if (!this.getAbilities().containsKey(IndestructibleAbility.getInstance().getId())) {
				if (moveToZone(Zone.GRAVEYARD, sourceId, game, false)) {
					game.fireEvent(GameEvent.getEvent(EventType.DESTROYED_PERMANENT, objectId, sourceId, controllerId));
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean sacrifice(UUID sourceId, Game game) {
		//20091005 - 701.13
		if (!game.replaceEvent(GameEvent.getEvent(EventType.SACRIFICE_PERMANENT, objectId, sourceId, controllerId))) {
			if (moveToZone(Zone.GRAVEYARD, sourceId, game, true)) {
				game.fireEvent(GameEvent.getEvent(EventType.SACRIFICED_PERMANENT, objectId, sourceId, controllerId));
				return true;
			}
		}
		return false;
	}


	@Override
	public void addPower(int power) {
		this.power.boostValue(power);
	}

	@Override
	public void addToughness(int toughness) {
		this.toughness.boostValue(toughness);
	}

	protected void fireEvent(EventType eventType, Game game) {
		game.fireEvent(GameEvent.getEvent(eventType, this.objectId, ownerId));
	}

	protected boolean replaceEvent(EventType eventType, Game game) {
		return game.replaceEvent(GameEvent.getEvent(eventType, this.objectId, ownerId));
	}

	@Override
	public boolean canAttack(Game game) {
		if (tapped)
			return false;
		if (hasSummoningSickness())
			return false;
		//20101001 - 508.1c
		for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(this, game)) {
			if (!effect.canAttack(game))
				return false;
		}
		if (abilities.containsKey(DefenderAbility.getInstance().getId()))
			return false;
		return true;
	}

	@Override
	public boolean canBlock(UUID attackerId, Game game) {
		if (tapped)
			return false;
		Permanent attacker = game.getPermanent(attackerId);
		//20101001 - 509.1b
		for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(attacker, game)) {
			if (!effect.canBlock(attacker, this, game))
				return false;
		}
		if (attacker.hasProtectionFrom(this))
			return false;
		return true;
	}

	@Override
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	@Override
	public void setBlocking(int blocking) {
		this.blocking = blocking;
	}

	@Override
	public void setMaxBlocks(int maxBlocks) {
		this.maxBlocks = maxBlocks;
	}

	@Override
	public boolean removeFromCombat(Game game) {
		game.getCombat().removeFromCombat(objectId, game);
		return true;
	}

}
