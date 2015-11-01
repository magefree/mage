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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class PrismArray extends CardImpl {

    public PrismArray(UUID ownerId) {
        super(ownerId, 81, "Prism Array", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");
        this.expansionSetCode = "BFZ";

        // <i>Converge</i> - Prism Array enters the battlefield with a crystal counter on it for each color of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.CRYSTAL.createInstance(), ColorsOfManaSpentToCastCount.getInstance(), true),
                null, "<i>Converge</i> &mdash; {this} enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.", null));

        // Remove a crystal counter from Prism Array: Tap target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new TapTargetEffect(),
                new RemoveCountersSourceCost(CounterType.CRYSTAL.createInstance(1)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {W}{U}{B}{R}{G}: Scry 3.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(3), new ManaCostsImpl("{W}{U}{B}{R}{G}")));
    }

    public PrismArray(final PrismArray card) {
        super(card);
    }

    @Override
    public PrismArray copy() {
        return new PrismArray(this);
    }
}
