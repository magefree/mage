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
package mage.cards.w;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Styxo
 */
public class WildHolocron extends CardImpl {

    public WildHolocron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Put a charge counter on Wild Holocron.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)), new TapSourceCost()));

        // {T}, Remove a charge counter from Wild Holocron: Add {R}, {G} or {W} to your mana pool.
        Cost cost = new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1));
        Ability ability = new RedManaAbility();
        ability.addCost(cost);
        this.addAbility(ability);

        ability = new GreenManaAbility();
        ability.addCost(cost);
        this.addAbility(ability);

        ability = new WhiteManaAbility();
        ability.addCost(cost);
        this.addAbility(ability);

        // {T}, Remove two charge counters from Wild Holocron: Add RG or GW to your mana pool.
        cost = new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(2));

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 1, 0, 0, 0, 0, 0, 0), new TapSourceCost());
        ability.addCost(cost);
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 0, 1, 0, 0, 0, 0), new TapSourceCost());
        ability.addCost(cost);
        this.addAbility(ability);
    }

    public WildHolocron(final WildHolocron card) {
        super(card);
    }

    @Override
    public WildHolocron copy() {
        return new WildHolocron(this);
    }
}
