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
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.target.TargetPermanent;
/**
 *
 * @author Saga
 */
public class KindredBoon extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filterDivinity = new FilterControlledCreaturePermanent("Each creature you control with a divinity counter on it");
    static {
        filterDivinity.add(new CounterPredicate(CounterType.DIVINITY));
    }

    public KindredBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // As Kindred Boon enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.AddAbility)));
        
        // {1}{W}: Put a divinity counter on target creature you control of the chosen type.
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control of the chosen type");
        filter.add(new ChosenSubtypePredicate(this.getId()));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.DIVINITY.createInstance()), new ManaCostsImpl("{1}{W}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
        
        // Each creature you control with a divinity counter on it has indestructible.
        Effect effect = new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filterDivinity);
        effect.setText("Each creature you control with a divinity counter on it has indestructible");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public KindredBoon(final KindredBoon card) {
        super(card);
    }

    @Override
    public KindredBoon copy() {
        return new KindredBoon(this);
    }
}
