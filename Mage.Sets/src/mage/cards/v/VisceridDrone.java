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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class VisceridDrone extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("nonartifact creature");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("Swamp");
    private static final FilterControlledPermanent filter3 = new FilterControlledPermanent("snow Swamp");

    static {
        filter1.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
        filter2.add(new SubtypePredicate(SubType.SWAMP));
        filter3.add(new SubtypePredicate(SubType.SWAMP));
        filter3.add(new SupertypePredicate(SuperType.SNOW));
    }

    public VisceridDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HOMARID);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}, Sacrifice a creature and a Swamp: Destroy target nonartifact creature. It can't be regenerated.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(true), new TapSourceCost());
        ability.addCost(new CompositeCost(
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)),
                new SacrificeTargetCost(new TargetControlledPermanent(filter2)),
                "Sacrifice a creature and a Swamp"
        ));
        ability.addTarget(new TargetCreaturePermanent(filter1));
        this.addAbility(ability);

        // {tap}, Sacrifice a creature and a snow Swamp: Destroy target creature. It can't be regenerated.
        ability = new SimpleActivatedAbility(new DestroyTargetEffect(true), new TapSourceCost());
        ability.addCost(new CompositeCost(
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)),
                new SacrificeTargetCost(new TargetControlledPermanent(filter3)),
                "Sacrifice a creature and a snow Swamp"
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public VisceridDrone(final VisceridDrone card) {
        super(card);
    }

    @Override
    public VisceridDrone copy() {
        return new VisceridDrone(this);
    }
}
