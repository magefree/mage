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
package mage.sets.planeshift;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.Filter;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public class RushingRiver extends CardImpl {

    public RushingRiver(UUID ownerId) {
        super(ownerId, 30, "Rushing River", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "PLS";


        // Kicker-Sacrifice a land.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land")))));

        // Return target nonland permanent to its owner's hand. If Rushing River was kicked, return another target nonland permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());        
        Effect effect = new ConditionalOneShotEffect(
                new ReturnToHandTargetEffect(),
                KickedCondition.getInstance(),
                "If {this} was kicked, return another target nonland permanent to its owner's hand");
        effect.setTargetPointer(new SecondTargetPointer());
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility && KickedCondition.getInstance().apply(game, ability)) {
            ability.addTarget(new TargetOtherNonlandPermanent(new FilterNonlandPermanent("another target nonland permanent")));
        }

    }

    public RushingRiver(final RushingRiver card) {
        super(card);
    }

    @Override
    public RushingRiver copy() {
        return new RushingRiver(this);
    }
}

class TargetOtherNonlandPermanent extends TargetNonlandPermanent {

    public TargetOtherNonlandPermanent(FilterNonlandPermanent filter) {
        super(filter);
    }

    public TargetOtherNonlandPermanent(final TargetOtherNonlandPermanent target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (source.getTargets().get(0).getTargets().contains(id)) {
            return false;
        }
        return super.canTarget(controllerId, id, source, game);
    }

    @Override
    public TargetOtherNonlandPermanent copy() {
        return new TargetOtherNonlandPermanent(this);
    }
}
