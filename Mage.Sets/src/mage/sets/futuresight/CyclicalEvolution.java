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
package mage.sets.futuresight;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class CyclicalEvolution extends CardImpl {

    public CyclicalEvolution(UUID ownerId) {
        super(ownerId, 125, "Cyclical Evolution", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");
        this.expansionSetCode = "FUT";

        // Target creature gets +3/+3 until end of turn. Exile Cyclical Evolution with three time counters on it.
        getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        getSpellAbility().addTarget(new TargetCreaturePermanent());
        getSpellAbility().addEffect(ExileSpellEffect.getInstance());
        Effect effect = new AddCountersSourceEffect(CounterType.TIME.createInstance(), new StaticValue(3), true, true);
        effect.setText("with 3 time counters on it");
        getSpellAbility().addEffect(effect);

        // Suspend 3-{2}{G}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{2}{G}"), this));
    }

    public CyclicalEvolution(final CyclicalEvolution card) {
        super(card);
    }

    @Override
    public CyclicalEvolution copy() {
        return new CyclicalEvolution(this);
    }
}
