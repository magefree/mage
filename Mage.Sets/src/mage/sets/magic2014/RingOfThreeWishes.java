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
package mage.sets.magic2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public class RingOfThreeWishes extends CardImpl<RingOfThreeWishes> {

    public RingOfThreeWishes(UUID ownerId) {
        super(ownerId, 216, "Ring of Three Wishes", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "M14";

        // Ring of Three Wishes enters the battlefield with three wish counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.WISH.createInstance(3)), "with three wish counters on it"));
        // {5}, {T}, Remove a wish counter from Ring of Three Wishes: Search your library for a card and put that card into your hand. Then shuffle your library. 
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true), new ManaCostsImpl("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.WISH.createInstance()));
        this.addAbility(ability);
    }

    public RingOfThreeWishes(final RingOfThreeWishes card) {
        super(card);
    }

    @Override
    public RingOfThreeWishes copy() {
        return new RingOfThreeWishes(this);
    }
}
