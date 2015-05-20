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
package mage.sets.alliances;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetPermanent;

/**
 *
 * @author Quercitron
 */
public class Tornado extends CardImpl {

    public Tornado(UUID ownerId) {
        super(ownerId, 86, "Tornado", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");
        this.expansionSetCode = "ALL";


        // Cumulative upkeep {G}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{G}")));
        // {2}{G}, Pay 3 life for each velocity counter on Tornado: Destroy target permanent and put a velocity counter on Tornado. Activate this ability only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{2}{G}"));
        DynamicValue lifeToPayAmount = new MultipliedValue(new CountersCount(CounterType.VELOCITY), 3);
        ability.addCost(new PayLifeCost(lifeToPayAmount, "3 life for each velocity counter on {source}"));
        ability.addTarget(new TargetPermanent());
        Effect effect = new AddCountersSourceEffect(CounterType.VELOCITY.createInstance());
        effect.setText("and put a velocity counter on {source}");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public Tornado(final Tornado card) {
        super(card);
    }

    @Override
    public Tornado copy() {
        return new Tornado(this);
    }
}
