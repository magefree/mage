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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public class EaterOfHope extends CardImpl<EaterOfHope> {

    private static final FilterControlledCreaturePermanent regenFilter = new FilterControlledCreaturePermanent("another creature");
    private static final FilterControlledCreaturePermanent destroyFilter = new FilterControlledCreaturePermanent("two other creatures");

    static {
        regenFilter.add(new AnotherPredicate());
        destroyFilter.add(new AnotherPredicate());
    }

    public EaterOfHope(UUID ownerId) {
        super(ownerId, 66, "Eater of Hope", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Demon");

        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {B}, Sacrifice another creature: Regenerate Eater of Hope.
        Ability regenAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{B}"));
        regenAbility.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, regenFilter, true, true)));
        this.addAbility(regenAbility);

        // {2}{B}, Sacrifice two other creatures: Destroy target creature.
        Ability destroyAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{2}{B}"));
        destroyAbility.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(2, 2, destroyFilter, true, true)));
        destroyAbility.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(destroyAbility);
    }

    public EaterOfHope(final EaterOfHope card) {
        super(card);
    }

    @Override
    public EaterOfHope copy() {
        return new EaterOfHope(this);
    }
}
