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
package mage.cards.o;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author L_J
 */
public class OrimsCure extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("If you control a Plains");
    private static final FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(new SubtypePredicate(SubType.PLAINS));
        filterCreature.add(Predicates.not(new TappedPredicate()));
    }

    public OrimsCure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // If you control a Plains, you may tap an untapped creature you control rather than pay Orim's Cure's mana cost.
        Cost cost = new TapTargetCost(new TargetControlledPermanent(1,1,filterCreature,false));
        cost.setText(" tap an untapped creature you control");
        this.addAbility(new AlternativeCostSourceAbility(cost, new PermanentsOnTheBattlefieldCondition(filter)));

        // Prevent the next 4 damage that would be dealt to target creature or player this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 4));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public OrimsCure(final OrimsCure card) {
        super(card);
    }

    @Override
    public OrimsCure copy() {
        return new OrimsCure(this);
    }
}
