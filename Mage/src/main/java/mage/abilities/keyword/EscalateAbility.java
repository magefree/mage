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
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author emerald000
 */
public class EscalateAbility extends SimpleStaticAbility {

    public EscalateAbility(Cost cost) {
        super(Zone.ALL, new EscalateEffect(cost));
        this.setRuleAtTheTop(true);
    }

    public EscalateAbility(final EscalateAbility ability) {
        super(ability);
    }

    @Override
    public EscalateAbility copy() {
        return new EscalateAbility(this);
    }
}

class EscalateEffect extends CostModificationEffectImpl {

    private final Cost cost;

    EscalateEffect(Cost cost) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.cost = cost;
        this.staticText = "Escalate " + cost.getText() + " <i>(Pay this cost for each mode chosen beyond the first.)</i>";
    }

    EscalateEffect(final EscalateEffect effect) {
        super(effect);
        this.cost = effect.cost;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int numCosts = abilityToModify.getModes().getSelectedModes().size() - 1;
        for (int i = 0; i < numCosts; i++) {
            abilityToModify.addCost(cost.copy());
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility)) {
            return abilityToModify.getModes().getSelectedModes().size() > 1;
        }
        return false;
    }

    @Override
    public EscalateEffect copy() {
        return new EscalateEffect(this);
    }
}
