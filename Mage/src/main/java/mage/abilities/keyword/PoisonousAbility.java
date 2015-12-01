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

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;

/**
 * @author dokkaebi
 *
 * 702.69. Poisonous
 * 702.69a Poisonous is a triggered ability. “Poisonous N” means “Whenever this creature deals combat damage to a player, that player gets N poison counters.” (For information about poison counters, see rule 104.3d.)
 * 702.69b If a creature has multiple instances of poisonous, each triggers separately.
 */
public class PoisonousAbility extends DealsCombatDamageToAPlayerTriggeredAbility {

    int n;

    public PoisonousAbility(int n) {
        super(new AddPoisonCountersEffect(n), false, true);
        this.n = n;
    }

    public PoisonousAbility(PoisonousAbility ability) {
        super(ability);
        this.n = ability.n;
    }

    @Override
    public String getRule() {
        return String.format("Poisonous %d. <i>(%s)</i>", n, super.getRule());
    }

    @Override
    public PoisonousAbility copy() {
        return new PoisonousAbility(this);
    }
}

class AddPoisonCountersEffect extends AddCountersTargetEffect {
    public AddPoisonCountersEffect(int n) {
        super(CounterType.POISON.createInstance(n), Outcome.Detriment);
        setText(n == 1 ? "that player gets a poison counter"
                : String.format("that player gets %d poison counters", n));
    }
}