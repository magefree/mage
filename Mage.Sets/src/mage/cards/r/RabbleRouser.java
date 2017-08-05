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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.BloodthirstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author BursegSardaukar
 */
public class RabbleRouser extends CardImpl {

    public RabbleRouser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add("Goblin");
        this.subtype.add("Shaman");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Bloodthirst 1 (If an opponent was dealt damage this turn, this creature enters the battlefield with a +1/+1 counter on it.)
        this.addAbility(new BloodthirstAbility(1));

        //{R}, {T}: Attacking creatures get +X/+0 until end of turn, where X is Rabble-Rouser's power.
        DynamicValue amount = new SourcePermanentPowerCount();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostAllEffect(amount, new StaticValue(0), Duration.EndOfTurn, new FilterAttackingCreature(), false,
                        "Attacking creatures get +X/+0 until end of turn, where X is {this}'s power", true),
                new ManaCostsImpl("{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public RabbleRouser(final RabbleRouser card) {
        super(card);
    }

    @Override
    public RabbleRouser copy() {
        return new RabbleRouser(this);
    }
}
