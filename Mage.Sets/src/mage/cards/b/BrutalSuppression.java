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
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.AbilityType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author L_J
 */
public class BrutalSuppression extends CardImpl {

    public BrutalSuppression(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // Activated abilities of nontoken Rebels cost an additional "Sacrifice a land" to activate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BrutalSuppressionAdditionalCostEffect()));
    }

    public BrutalSuppression(final BrutalSuppression card) {
        super(card);
    }

    @Override
    public BrutalSuppression copy() {
        return new BrutalSuppression(this);
    }
}

class BrutalSuppressionAdditionalCostEffect extends CostModificationEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a land");
    static{
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    private static final FilterPermanent filter2 = new FilterPermanent("nontoken Rebels");
    static{
        filter2.add(new SubtypePredicate(SubType.REBEL));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    BrutalSuppressionAdditionalCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = "Activated abilities of nontoken Rebels cost an additional \"Sacrifice a land\" to activate";
    }

    BrutalSuppressionAdditionalCostEffect(BrutalSuppressionAdditionalCostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
        target.setRequired(false);
        abilityToModify.addCost(new SacrificeTargetCost(target));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getAbilityType() == AbilityType.ACTIVATED || abilityToModify.getAbilityType() == AbilityType.MANA) {
            Permanent rebelPermanent = game.getPermanent(abilityToModify.getSourceId());
            if (rebelPermanent != null) {
                return filter2.match(rebelPermanent, game);
            }
        }
        return false;
    }

    @Override
    public BrutalSuppressionAdditionalCostEffect copy() {
        return new BrutalSuppressionAdditionalCostEffect(this);
    }
}
