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
package mage.sets.exodus;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PreventAllDamageEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class SpikeWeaver extends CardImpl<SpikeWeaver> {

    public SpikeWeaver(UUID ownerId) {
        super(ownerId, 128, "Spike Weaver", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "EXO";
        this.subtype.add("Spike");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Spike Weaver enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3))));
        
        // {2}, Remove a +1/+1 counter from Spike Weaver: Put a +1/+1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new GenericManaCost(2));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // {1}, Remove a +1/+1 counter from Spike Weaver: Prevent all combat damage that would be dealt this turn.
        Ability ability2 = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new PreventAllDamageEffect(Constants.Duration.EndOfTurn, true), new GenericManaCost(1));
        ability2.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        this.addAbility(ability2);
    }

    public SpikeWeaver(final SpikeWeaver card) {
        super(card);
    }

    @Override
    public SpikeWeaver copy() {
        return new SpikeWeaver(this);
    }
}
