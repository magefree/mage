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

package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.mana.MultikickerManaCost;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;



/**
 *
 * @author BetaSteward_at_googlemail.com, North
 */
public class EverflowingChalice extends CardImpl<EverflowingChalice> {

    protected static final String rule = "Everflowing Chalice enters the battlefield with a charge counter on it for each time it was kicked.";

    public EverflowingChalice(UUID ownerId) {
        super(ownerId, 123, "Everflowing Chalice", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "WWK";

        // Multikicker {2} (You may pay an additional {2} any number of times as you cast this spell.)
        this.addAbility(new KickerAbility(new MultikickerManaCost("{2}")));

        // Everflowing Chalice enters the battlefield with a charge counter on it for each time it was kicked.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance(0), new MultikickerCount(), true),
                "with a charge counter on it for each time it was kicked"));

        // {T}: Add {1} to your mana pool for each charge counter on Everflowing Chalice.
        this.addAbility(new DynamicManaAbility(Mana.ColorlessMana, new CountersCount(CounterType.CHARGE)));
    }

    public EverflowingChalice(final EverflowingChalice card) {
        super(card);
    }

    @Override
    public EverflowingChalice copy() {
        return new EverflowingChalice(this);
    }

}

