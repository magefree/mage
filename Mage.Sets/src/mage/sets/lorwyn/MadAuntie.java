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
package mage.sets.lorwyn;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

import java.util.UUID;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class MadAuntie extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("another target Goblin");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Goblin creatures");

    static {
        filter1.add(new SubtypePredicate("Goblin"));
        filter1.add(new AnotherPredicate());
        filter2.add(new SubtypePredicate("Goblin"));
    }

    public MadAuntie(UUID ownerId) {
        super(ownerId, 123, "Mad Auntie", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Goblin");
        this.subtype.add("Shaman");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Goblin creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter2, true)));

        // {T}: Regenerate another target Goblin.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter1));
        this.addAbility(ability);
    }

    public MadAuntie(final MadAuntie card) {
        super(card);
    }

    @Override
    public MadAuntie copy() {
        return new MadAuntie(this);
    }
}
