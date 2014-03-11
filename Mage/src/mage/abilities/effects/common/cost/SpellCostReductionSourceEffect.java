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

package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;


/**
 *
 * @author LevelX2
 */

public class SpellCostReductionSourceEffect extends CostModificationEffectImpl<SpellCostReductionSourceEffect> {

    private final int amount;
    private ManaCosts<ManaCost> manaCostsToReduce = null;
    private final Condition condition;

    public SpellCostReductionSourceEffect(ManaCosts<ManaCost> manaCostsToReduce,  Condition condition) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.amount = 0;
        this.manaCostsToReduce = manaCostsToReduce;
        this.condition = condition;

        StringBuilder sb = new StringBuilder();
        sb.append("{this} costs ");
        for (String manaSymbol :manaCostsToReduce.getSymbols()) {
            sb.append(manaSymbol);
        }
        sb.append(" less to if ").append(this.condition.toString());
        this.staticText = sb.toString();
    }


    public SpellCostReductionSourceEffect(int amount, Condition condition) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.amount = amount;
        this.condition = condition;
        StringBuilder sb = new StringBuilder();
        sb.append("{this} costs {").append(amount).append("} less to cast if ").append(this.condition.toString());
        this.staticText = sb.toString();
    }

    protected SpellCostReductionSourceEffect(SpellCostReductionSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.manaCostsToReduce = effect.manaCostsToReduce;
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (manaCostsToReduce != null){
            CardUtil.adjustCost((SpellAbility) abilityToModify, manaCostsToReduce, false);
        } else {
            CardUtil.reduceCost(abilityToModify, this.amount);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility)) {            
            return condition.apply(game, source);
        }
        return false;
    }

    @Override
    public SpellCostReductionSourceEffect copy() {
        return new SpellCostReductionSourceEffect(this);
    }
}
