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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public class Degavolver extends CardImpl<Degavolver> {

    public Degavolver(UUID ownerId) {
        super(ownerId, 6, "Degavolver", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "APC";
        this.subtype.add("Volver");
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

         // Kicker {1}{B} and/or {R} (You may pay an additional {1}{B} and/or {R} as you cast this spell.)
        KickerAbility kickerAbility = new KickerAbility("{1}{B}");
        kickerAbility.addKickerCost("{R}");
        this.addAbility(kickerAbility);

        // If Degavolver was kicked with its {1}{B} kicker, it enters the battlefield with two +1/+1 counters on it and with "Pay 3 life: Regenerate Degavolver."
        EntersBattlefieldAbility ability1 = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2),false),
                new KickedCostCondition("{1}{B}"), true, "If Degavolver was kicked with its {1}{B} kicker, it enters the battlefield with two +1/+1 counters on it and with \"Pay 3 life: Regenerate Degavolver.\"",
                "{this} enters the battlefield with two +1/+1 counters on it and with \"Pay 3 life: Regenerate Degavolver.\"");
        ((EntersBattlefieldEffect)ability1.getEffects().get(0)).addEffect(new GainAbilitySourceEffect(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new PayLifeCost(3)), Constants.Duration.WhileOnBattlefield));
        this.addAbility(ability1);

        // If Degavolver was kicked with its {R} kicker, it enters the battlefield with a +1/+1 counter on it and with first strike.
        EntersBattlefieldAbility ability2 = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1),false), new KickedCostCondition("{R}"), true,
                "If Degavolver was kicked with its {R} kicker, it enters the battlefield with a +1/+1 counter on it and with first strike.",
                "{this} enters the battlefield with a +1/+1 counter on it and with first strike");
        ((EntersBattlefieldEffect)ability2.getEffects().get(0)).addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability2);
    }

    public Degavolver(final Degavolver card) {
        super(card);
    }

    @Override
    public Degavolver copy() {
        return new Degavolver(this);
    }
}
