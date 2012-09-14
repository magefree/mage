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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.IndestructibleAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.target.TargetPermanent;



/**
 * @author LevelX2
 */
public class ThatWhichWasTaken extends CardImpl<ThatWhichWasTaken> {

    private static final FilterPermanent filter = new FilterPermanent("permanent other than That Which Was Taken");

    private static final FilterPermanent filterIndestructible = new FilterPermanent("Each permanent with a divinity counter on it");

    static {
        filter.add(new AnotherPredicate());
        filterIndestructible.add(new CounterPredicate(CounterType.DIVINITY));
    }

    public ThatWhichWasTaken(UUID ownerId) {
        super(ownerId, 162, "That Which Was Taken", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");

        // {4}, {T}: Put a divinity counter on target permanent other than That Which Was Taken.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.DIVINITY.createInstance()), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Each permanent with a divinity counter on it is indestructible.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new IndestructibleAllEffect(filterIndestructible)));

    }

    public ThatWhichWasTaken(final ThatWhichWasTaken card) {
        super(card);
    }

    @Override
    public ThatWhichWasTaken copy() {
        return new ThatWhichWasTaken(this);
    }
}


