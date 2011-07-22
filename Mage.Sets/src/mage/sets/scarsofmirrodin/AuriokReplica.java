/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AuriokReplica extends CardImpl<AuriokReplica> {

	public AuriokReplica(UUID ownerId) {
		super(ownerId, 138, "Auriok Replica", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Cleric");
		this.power = new MageInt(2);
		this.toughness = new MageInt(2);

		Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AuriokReplicaEffect(), new ManaCostsImpl("{W}"));
		ability.addCost(new SacrificeSourceCost());
		this.addAbility(ability);
	}

	public AuriokReplica(final AuriokReplica card) {
		super(card);
	}

	@Override
	public AuriokReplica copy() {
		return new AuriokReplica(this);
	}

}

class AuriokReplicaEffect extends PreventionEffectImpl<AuriokReplicaEffect> {

	private TargetSource target = new TargetSource();

	public AuriokReplicaEffect() {
		super(Duration.EndOfTurn);
		staticText = "Prevent all damage a source of your choice would deal to you this turn";
	}

	public AuriokReplicaEffect(final AuriokReplicaEffect effect) {
		super(effect);
		this.target = effect.target.copy();
	}

	@Override
	public AuriokReplicaEffect copy() {
		return new AuriokReplicaEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public void init(Ability source, Game game) {
		this.target.choose(Outcome.PreventDamage, source.getControllerId(), game);
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		if (event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(target.getFirstTarget())) {
			preventDamage(event, source, target.getFirstTarget(), game);
			return true;
		}
		return false;
	}

	private void preventDamage(GameEvent event, Ability source, UUID target, Game game) {
		GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, target, source.getId(), source.getControllerId(), event.getAmount(), false);
		if (!game.replaceEvent(preventEvent)) {
			int damage = event.getAmount();
			event.setAmount(0);
			game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, target, source.getId(), source.getControllerId(), damage));
		}
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		if (!this.used && super.applies(event, source, game)) {
			if (event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(target.getFirstTarget())) {
				return true;
			}
		}
		return false;
	}

}