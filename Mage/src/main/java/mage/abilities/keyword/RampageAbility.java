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
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;

/**
 *
 * @author LoneFox
 */

public class RampageAbility extends BecomesBlockedTriggeredAbility {

    private final String rule;

    public RampageAbility(int amount) {
        super(null, false);
        rule = "rampage " + amount;
        RampageValue rv = new RampageValue(amount);
        this.addEffect(new BoostSourceEffect(rv, rv, Duration.EndOfTurn));
    }

    public RampageAbility(final RampageAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public RampageAbility copy() {
        return new RampageAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }
}


class RampageValue implements DynamicValue {

    private final int amount;

    public RampageValue(int amount) {
        this.amount = amount;
    }

    public RampageValue(final RampageValue value) {
        this.amount = value.amount;
    }

    @Override
    public RampageValue copy() {
        return new RampageValue(this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for(CombatGroup combatGroup : game.getCombat().getGroups()) {
            if(combatGroup.getAttackers().contains(sourceAbility.getSourceId())) {
                 int blockers = combatGroup.getBlockers().size();
                 return blockers > 1 ? (blockers - 1) * amount : 0;
            }
        }
        return 0;
    }

    @Override
    public String getMessage() {
        return "Rampage " + amount;
    }
}
