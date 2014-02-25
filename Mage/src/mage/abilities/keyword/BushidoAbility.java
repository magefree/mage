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
import mage.abilities.common.BlocksOrBecomesBlockedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.constants.Duration;
import mage.game.Game;

public class BushidoAbility extends BlocksOrBecomesBlockedTriggeredAbility {

    private DynamicValue value;
    private String rule = null;

    public BushidoAbility(int value) {
        this(new StaticValue(value));
        rule = new StringBuilder("Bushido ").append(value).toString();
    }

    public BushidoAbility(DynamicValue value) {
        super(new BoostSourceEffect(value, value, Duration.EndOfTurn), false);
        if (rule == null) {
            rule = new StringBuilder("{this} has bushido X, where X is ").append(value.getMessage()).toString();
        }
        rule = new StringBuilder(rule).append("  <i>(When this blocks or becomes blocked, it gets +").append(value.toString()).append("/+").append(value.toString()).append(" until end of turn.)</i>").toString();
        this.value = value;
    }

    public BushidoAbility(final BushidoAbility ability) {
        super(ability);
        this.value = ability.value;
        this.rule = ability.rule;
    }

    @Override
    public BushidoAbility copy() {
        return new BushidoAbility(this);
    }

    public int getValue(Ability source, Game game) {
        return value.calculate(game, source);
    }

    @Override
    public String getRule() {
        return rule;
    }
}
