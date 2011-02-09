/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public class TrigonofRage extends CardImpl<TrigonofRage> {

	public TrigonofRage(UUID ownerId) {
		super(ownerId, 216, "Trigon of Rage", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
		this.expansionSetCode = "SOM";

		this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.getName(), 3)));

		Costs costs = new CostsImpl();
		costs.add(new ManaCostsImpl("{2}"));
		costs.add(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
		costs.add(new TapSourceCost());
		Effect pumpEffect = new BoostTargetEffect(3, 0, Duration.EndOfTurn);
		Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, pumpEffect, costs);
		Target target = new TargetCreaturePermanent();
		target.setRequired(true);
		ability.addTarget(target);
		this.addAbility(ability);

		Costs costs2 = new CostsImpl();
		costs2.add(new ManaCostsImpl("{R}{R}"));
		costs2.add(new TapSourceCost());
		this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), costs2));
	}

	public TrigonofRage(final TrigonofRage card) {
		super(card);
	}

	@Override
	public TrigonofRage copy() {
		return new TrigonofRage(this);
	}

}
