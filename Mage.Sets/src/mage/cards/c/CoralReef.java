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
package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class CoralReef extends CardImpl {
    
    private static final FilterControlledPermanent islandFilter = new FilterControlledPermanent("an Island");
    private static final FilterControlledCreaturePermanent untappedBlueCreatureFilter = new FilterControlledCreaturePermanent("an untapped blue creature you control");
    
    static {
        islandFilter.add(new SubtypePredicate(SubType.ISLAND));
        untappedBlueCreatureFilter.add(Predicates.not(new TappedPredicate()));
        untappedBlueCreatureFilter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public CoralReef(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}{U}");

        // Coral Reef enters the battlefield with four polyp counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.POLYP.createInstance(4));
        effect.setText("with four polyp counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));
        
        // Sacrifice an Island: Put two polyp counters on Coral Reef.
        effect = new AddCountersSourceEffect(CounterType.POLYP.createInstance(2), true);
        effect.setText("Put two polyp counters on {this}");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, 
                new SacrificeTargetCost(new TargetControlledPermanent(islandFilter))));
        
        // {U}, Tap an untapped blue creature you control, Remove a polyp counter from Coral Reef: Put a +0/+1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P0P1.createInstance()), new ManaCostsImpl("{U}"));
        ability.addCost(new TapTargetCost(new TargetControlledCreaturePermanent(untappedBlueCreatureFilter)));
        ability.addCost(new RemoveCountersSourceCost(CounterType.POLYP.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public CoralReef(final CoralReef card) {
        super(card);
    }

    @Override
    public CoralReef copy() {
        return new CoralReef(this);
    }
}
