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
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author anonymous
 */
public class MercadianBazaar extends CardImpl {

    public MercadianBazaar(UUID ownerId) {
        super(ownerId, 321, "Mercadian Bazaar", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "MMQ";

        // Mercadian Bazaar enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Put a storage counter on Mercadian Bazaar.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance()), new TapSourceCost()));
        // {tap}, Remove any number of storage counters from Mercadian Bazaar: Add {R} to your mana pool for each storage counter removed this way.
        Ability ability = new DynamicManaAbility(Mana.RedMana, new RemovedCountersForCostValue(),
                "Add {R} to your mana pool for each storage counter removed this way");
        ability.addCost(new RemoveVariableCountersSourceCost(
                CounterType.STORAGE.createInstance(), "Remove any number of storage counters from {this}"));
        this.addAbility(ability);
    }

    public MercadianBazaar(final MercadianBazaar card) {
        super(card);
    }

    @Override
    public MercadianBazaar copy() {
        return new MercadianBazaar(this);
    }
}
