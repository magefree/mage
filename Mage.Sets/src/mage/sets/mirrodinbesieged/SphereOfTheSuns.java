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
package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;

/**
 *
 * @author North, Loki
 */
public class SphereOfTheSuns extends CardImpl<SphereOfTheSuns> {

    public SphereOfTheSuns(UUID ownerId) {
        super(ownerId, 134, "Sphere of the Suns", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "MBS";

        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(3)),
                "Sphere of the Suns enters the battlefield tapped and with three charge counters on it.");
        ability.addEffect(new TapSourceEffect());
        this.addAbility(ability);

        RemoveCountersSourceCost removeCounterCost = new RemoveCountersSourceCost(CounterType.CHARGE.getName(), 1);
        ability = new AnyColorManaAbility();
        ability.addCost(removeCounterCost);
        this.addAbility(ability);
    }

    public SphereOfTheSuns(final SphereOfTheSuns card) {
        super(card);
    }

    @Override
    public SphereOfTheSuns copy() {
        return new SphereOfTheSuns(this);
    }
}
