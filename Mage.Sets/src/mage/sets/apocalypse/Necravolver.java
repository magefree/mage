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
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox

 */
public class Necravolver extends CardImpl {

    public Necravolver(UUID ownerId) {
        super(ownerId, 46, "Necravolver", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "APC";
        this.subtype.add("Volver");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{G} and/or {W}
        KickerAbility kickerAbility = new KickerAbility("{1}{G}");
        kickerAbility.addKickerCost("{W}");
        this.addAbility(kickerAbility);
        // If Necravolver was kicked with its {1}{G} kicker, it enters the battlefield with two +1/+1 counters on it and with trample.
        Ability ability = new  EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            new KickedCostCondition("{1}{G}"), "If {this} was kicked with its {1}{G} kicker, it enters the battlefield with two +1/+1 counters on it and with trample.", "");
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
        // If Necravolver was kicked with its {W} kicker, it enters the battlefield with a +1/+1 counter on it and with "Whenever Necravolver deals damage, you gain that much life."
        ability = new  EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
            new KickedCostCondition("{W}"), "If {this} was kicked with its {W} kicker, it enters the battlefield with a +1/+1 counter on it and with \"Whenever {this} deals damage, you gain that much life.\"", "");
        ability.addEffect(new GainAbilitySourceEffect(new DealsDamageGainLifeSourceTriggeredAbility(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    public Necravolver(final Necravolver card) {
        super(card);
    }

    @Override
    public Necravolver copy() {
        return new Necravolver(this);
    }
}
