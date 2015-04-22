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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author Jason E. Wall

 */
public class AuriokSteelshaper extends CardImpl {
    private static final FilterCreaturePermanent soldiersOrKnights = new FilterCreaturePermanent();

    static {
        soldiersOrKnights.add(Predicates.or(
                new SubtypePredicate("Soldier"),
                new SubtypePredicate("Knight")
        ));
    }

    public AuriokSteelshaper(UUID ownerId) {
        super(ownerId, 4, "Auriok Steelshaper", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Equip costs you pay cost {1} less.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AuriokSteelshaperCostReductionEffect()));

        // As long as Auriok Steelshaper is equipped, each creature you control that's a Soldier or a Knight gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, soldiersOrKnights, false),
                EquippedCondition.getInstance(),
                "As long as {this} is equipped, each creature you control that's a Soldier or a Knight gets +1/+1"
        )));
    }

    public AuriokSteelshaper(final AuriokSteelshaper card) {
        super(card);
    }

    @Override
    public AuriokSteelshaper copy() {
        return new AuriokSteelshaper(this);
    }
}

class AuriokSteelshaperCostReductionEffect extends CostModificationEffectImpl {

    public AuriokSteelshaperCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Equip costs you pay cost {1} less";
    }

    public AuriokSteelshaperCostReductionEffect(AuriokSteelshaperCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public AuriokSteelshaperCostReductionEffect copy() {
        return new AuriokSteelshaperCostReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getControllerId().equals(source.getControllerId()) &&
                (abilityToModify instanceof EquipAbility);
    }
}
