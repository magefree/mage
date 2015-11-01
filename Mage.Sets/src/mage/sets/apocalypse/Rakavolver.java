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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox

 */
public class Rakavolver extends CardImpl {

    public Rakavolver(UUID ownerId) {
        super(ownerId, 68, "Rakavolver", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "APC";
        this.subtype.add("Volver");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{W} and/or {U}
        KickerAbility kickerAbility = new KickerAbility("{1}{W}");
        kickerAbility.addKickerCost("{U}");
        this.addAbility(kickerAbility);
        // If Rakavolver was kicked with its {1}{W} kicker, it enters the battlefield with two +1/+1 counters on it and with "Whenever Rakavolver deals damage, you gain that much life."
        Ability ability = new  EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            new KickedCostCondition("{1}{W}"), "If {this} was kicked with its {1}{W} kicker, it enters the battlefield with two +1/+1 counters on it and with \"Whenever {this} deals damage, you gain that much life.\"", "");
        ability.addEffect(new GainAbilitySourceEffect(new DealsDamageGainLifeSourceTriggeredAbility(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
        // If Rakavolver was kicked with its {U} kicker, it enters the battlefield with a +1/+1 counter on it and with flying.
        ability = new  EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
            new KickedCostCondition("{U}"), "If {this} was kicked with its {U} kicker, it enters the battlefield with a +1/+1 counter on it and with flying.", "");
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);

    }

    public Rakavolver(final Rakavolver card) {
        super(card);
    }

    @Override
    public Rakavolver copy() {
        return new Rakavolver(this);
    }
}
