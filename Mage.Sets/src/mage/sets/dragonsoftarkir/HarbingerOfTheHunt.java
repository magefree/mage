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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public class HarbingerOfTheHunt extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("other creature with flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter2.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(new AnotherPredicate());
    }

    public HarbingerOfTheHunt(UUID ownerId) {
        super(ownerId, 223, "Harbinger of the Hunt", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Dragon");
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {2}{R}: Harbinger of the Hunt deals 1 damage to each creature without flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(1, filter), new ManaCostsImpl("{2}{R}")));
        
        // {2}{G}: Harbinger of the Hunt deals 1 damage to each other creature with flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(1, filter2), new ManaCostsImpl("{2}{G}")));
    }

    public HarbingerOfTheHunt(final HarbingerOfTheHunt card) {
        super(card);
    }

    @Override
    public HarbingerOfTheHunt copy() {
        return new HarbingerOfTheHunt(this);
    }
}
