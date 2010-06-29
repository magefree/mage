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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructableAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class PermanentImpl extends CardImpl implements Permanent
{
	protected boolean tapped = false;
	protected boolean flipped = false;
	protected UUID controllerId;
	protected int damage;
	protected boolean controlledFromStartOfTurn = false;
	protected int turnsOnBattlefield = 0;
	protected boolean phasedIn = true;
	protected boolean faceUp = true;
	protected boolean attacking = false;
	protected boolean blocking = false;
	protected boolean loyaltyUsed = false;
	protected boolean deathtouched = false;
	protected Counters counters = new Counters();
	protected List<UUID> attachments = new ArrayList<UUID>();
	protected UUID attachedTo;

	public PermanentImpl(UUID ownerId, UUID controllerId, String name) {
		super(ownerId, name);
		this.controllerId = controllerId;
	}

	@Override
	public void reset() {
		this.controllerId = ownerId;
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
	public int getTurnsOnBattlefield() {
		return turnsOnBattlefield;
	}

	@Override
	public void endOfTurn(Game game) {
		this.damage = 0;
		this.loyaltyUsed = false;
		this.turnsOnBattlefield++;
		for (Ability ability: this.abilities) {
			ability.reset(game);
		}
	}

	@Override
	public void checkTriggers(GameEvent event, Game game) {
		if (event.getType() == EventType.BEGINNING_PHASE_PRE && game.getActivePlayerId().equals(controllerId))
			this.controlledFromStartOfTurn = true;
		for (TriggeredAbility ability: abilities.getTriggeredAbilities(Zone.BATTLEFIELD)) {
			ability.checkTrigger(event, game);
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
		if (!cardType.contains(CardType.CREATURE) || this.controlledFromStartOfTurn || this.abilities.containsKey(HasteAbility.getInstance().getId())) {
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
				addEffects(game);
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
	public boolean isAttacking() {
		return attacking;
	}

	@Override
	public boolean isBlocking() {
		return blocking;
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
			if (!game.replaceEvent(new GameEvent(GameEvent.EventType.ATTACH, objectId, permanentId, controllerId, damage))) {
				this.attachments.add(permanentId);
				game.getPermanent(permanentId).attachTo(objectId);
				game.fireEvent(new GameEvent(GameEvent.EventType.ATTACHED, objectId, permanentId, controllerId, damage));
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAttachment(UUID permanentId, Game game) {
		if (this.attachments.contains(permanentId)) {
			if (!game.replaceEvent(new GameEvent(GameEvent.EventType.UNATTACH, objectId, permanentId, controllerId, damage))) {
				this.attachments.remove(permanentId);
				game.getPermanent(permanentId).attachTo(null);
				game.fireEvent(new GameEvent(GameEvent.EventType.UNATTACHED, objectId, permanentId, controllerId, damage));
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
	public void attachTo(UUID permanentId) {
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
	public int damage(int damage, UUID sourceId, Game game) {
		int damageDone = 0;
		if (damage > 0 && canDamage(game.getObject(sourceId))) {
			if (cardType.contains(CardType.PLANESWALKER)) {
				damageDone = damagePlaneswalker(damage, sourceId, game);
			}
			else {
				damageDone = damageCreature(damage, sourceId, game);
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

	protected int damagePlaneswalker(int damage, UUID sourceId, Game game) {
		GameEvent event = new GameEvent(GameEvent.EventType.DAMAGE_PLANESWALKER, objectId, sourceId, controllerId, damage);
		if (!game.replaceEvent(event)) {
			int actualDamage = event.getAmount();
			if (actualDamage > 0) {
				if (event.getAmount() > this.loyalty.getValue()) {
					actualDamage = this.loyalty.getValue();
				}
				this.loyalty.boostValue(-actualDamage);
				game.fireEvent(GameEvent.getEvent(EventType.DAMAGED_PLANESWALKER, objectId, sourceId, controllerId, actualDamage));
				return actualDamage;
			}
		}
		return 0;
	}

	protected int damageCreature(int damage, UUID sourceId, Game game) {
		GameEvent event = new GameEvent(GameEvent.EventType.DAMAGE_CREATURE, objectId, sourceId, controllerId, damage);
		if (!game.replaceEvent(event)) {
			int actualDamage = event.getAmount();
			if (actualDamage > 0) {
				if (this.damage + event.getAmount() > this.toughness.getValue()) {
					actualDamage = this.toughness.getValue() - this.damage;
				}
				this.damage += actualDamage;
				game.fireEvent(GameEvent.getEvent(EventType.DAMAGED_CREATURE, objectId, sourceId, controllerId, actualDamage));
				return actualDamage;
			}
		}
		return 0;
	}

	@Override
	public void entersBattlefield(Game game) {
		controlledFromStartOfTurn = false;
		addEffects(game);
	}

	@Override
	public boolean canBeTargetedBy(MageObject source) {
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

	protected boolean canDamage(MageObject source) {
		for (ProtectionAbility ability: abilities.getProtectionAbilities()) {
			if (!ability.canTarget(source))
				return false;
		}
		return true;
	}

	protected void addEffects(Game game) {
		for (StaticAbility ability: abilities.getStaticAbilities(Zone.BATTLEFIELD)) {
			for (Effect effect: ability.getEffects()) {
				if (effect instanceof ContinuousEffect)
					game.addEffect((ContinuousEffect)effect);
			}
		}
	}

	@Override
	public boolean destroy(UUID sourceId, Game game, boolean noRegen) {
		//20091005 - 701.6
		//TODO: handle noRegen
		if (!game.replaceEvent(GameEvent.getEvent(EventType.DESTROY_PERMANENT, objectId, sourceId, controllerId))) {
			if (!this.getAbilities().containsKey(IndestructableAbility.getInstance().getId())) {
				if (moveToZone(Zone.GRAVEYARD, game, false)) {
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
			if (moveToZone(Zone.GRAVEYARD, game, true)) {
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
		if (!controlledFromStartOfTurn && !abilities.containsKey(HasteAbility.getInstance().getId()))
			return false;
		if (abilities.containsKey(DefenderAbility.getInstance().getId()))
			return false;
		return true;
	}

	@Override
	public boolean canBlock(UUID attackerId, Game game) {
		if (tapped)
			return false;
		Permanent attacker = game.getPermanent(attackerId);
		for (EvasionAbility ability: attacker.getAbilities().getEvasionAbilities()) {
			if (!ability.canBlock(this, game))
				return false;
		}
		for (ProtectionAbility ability: attacker.getAbilities().getProtectionAbilities()) {
			if (!ability.canTarget(this))
				return false;
		}
		return true;
	}

	@Override
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	@Override
	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}

	@Override
	public boolean removeFromCombat(Game game) {
		game.getCombat().removeFromCombat(objectId, game);
		return true;
	}

}
