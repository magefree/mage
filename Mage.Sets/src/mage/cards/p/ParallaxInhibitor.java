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
package mage.cards.p;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.keyword.FadingAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.counters.CounterType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LoneFox
 */
public class ParallaxInhibitor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("permanent with fading you control");

    static {
        filter.add(new AbilityPredicate(FadingAbility.class));
    }

    public ParallaxInhibitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {1}, {tap}, Sacrifice Parallax Inhibitor: Put a fade counter on each permanent with fading you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
            new AddCountersAllEffect(CounterType.FADE.createInstance(), filter), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public ParallaxInhibitor(final ParallaxInhibitor card) {
        super(card);
    }

    @Override
    public ParallaxInhibitor copy() {
        return new ParallaxInhibitor(this);
    }
}
