/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EntersBattlefieldAbility extends StaticAbility {

    protected String abilityRule;
    protected boolean optional;

    public EntersBattlefieldAbility(Effect effect) {
        this(effect, false);
    }

    /**
     *
     * @param effect effect that happens when the permanent enters the
     * battlefield
     * @param optional
     */
    public EntersBattlefieldAbility(Effect effect, boolean optional) {
        this(effect, optional, null, null, null);
    }

    public EntersBattlefieldAbility(Effect effect, String effectText) {
        this(effect, null, null, effectText);
    }

    public EntersBattlefieldAbility(Effect effect, Condition condition, String abilityRule, String effectText) {
        this(effect, false, condition, abilityRule, effectText);
    }

    /**
     *
     * @param effect effect that happens when the permanent enters the
     * battlefield
     * @param optional
     * @param condition only if this condition is true, the effect will happen
     * @param abilityRule rule for this ability (no text from effects will be
     * added)
     * @param effectText this text will be used for the EnterBattlefieldEffect
     */
    public EntersBattlefieldAbility(Effect effect, boolean optional, Condition condition, String abilityRule, String effectText) {
        super(Zone.ALL, new EntersBattlefieldEffect(effect, condition, effectText, true, optional));
        this.abilityRule = abilityRule;
        this.optional = optional;
    }

    public EntersBattlefieldAbility(final EntersBattlefieldAbility ability) {
        super(ability);
        this.abilityRule = ability.abilityRule;
        this.optional = ability.optional;
    }

    @Override
    public void addEffect(Effect effect) {
        if (!getEffects().isEmpty()) {
            Effect entersBattlefieldEffect = this.getEffects().get(0);
            if (entersBattlefieldEffect instanceof EntersBattlefieldEffect) {
                ((EntersBattlefieldEffect) entersBattlefieldEffect).addEffect(effect);
                return;
            }
        }
        super.addEffect(effect);
    }

    @Override
    public EntersBattlefieldAbility copy() {
        return new EntersBattlefieldAbility(this);
    }

    @Override
    public String getRule() {
        if (abilityRule != null && !abilityRule.isEmpty()) {
            return abilityRule;
        }
        String superRule = super.getRule();
        return (optional ? "you may have " : "") + "{this} enter" + (optional ? "" : "s") + " the battlefield" + (superRule.charAt(0) == ' ' ? "" : " ") + superRule;
    }
}
