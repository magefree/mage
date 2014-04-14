/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.abilities.abilityword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.Target;

/**
 *
 * @author LevelX2
 */

public class StriveAbility extends SimpleStaticAbility {

    private final String striveCost;
            
    public StriveAbility(String manaString) {
        super(Zone.STACK, new StriveCostIncreasementEffect(new ManaCostsImpl(manaString)));
        setRuleAtTheTop(true);
        this.striveCost = manaString;
    }

    public StriveAbility(final StriveAbility ability) {
        super(ability);
        this.striveCost = ability.striveCost;
    }

    @Override
    public SimpleStaticAbility copy() {
        return new StriveAbility(this);
    }

    @Override
    public String getRule() {
        return new StringBuilder("<i>Strive<i/> - {this} costs ").append(striveCost).append(" more to cast for each target beyond the first.").toString();
    }
}

class StriveCostIncreasementEffect extends CostModificationEffectImpl<StriveCostIncreasementEffect> {

    private ManaCostsImpl striveCosts = null;

    public StriveCostIncreasementEffect(ManaCostsImpl striveCosts) {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.striveCosts = striveCosts;
    }

    protected StriveCostIncreasementEffect(StriveCostIncreasementEffect effect) {
        super(effect);
        this.striveCosts = effect.striveCosts;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        // Target target = abilityToModify.getTargets().get(0);
        for (Target target : abilityToModify.getTargets()) {
            if (target.getMaxNumberOfTargets() == Integer.MAX_VALUE) {
                int additionalTargets = target.getTargets().size() - 1;
                for (int i = 0; i < additionalTargets; i++) {
                    abilityToModify.getManaCostsToPay().add(striveCosts.copy());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility);
    }

    @Override
    public StriveCostIncreasementEffect copy() {
        return new StriveCostIncreasementEffect(this);
    }
}
