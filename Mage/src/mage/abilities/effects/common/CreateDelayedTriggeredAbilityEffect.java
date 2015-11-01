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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CreateDelayedTriggeredAbilityEffect extends OneShotEffect {

    protected DelayedTriggeredAbility ability;
    protected boolean copyTargets;
    protected boolean initAbility;

    public CreateDelayedTriggeredAbilityEffect(DelayedTriggeredAbility ability) {
        this(ability, true);
    }

    public CreateDelayedTriggeredAbilityEffect(DelayedTriggeredAbility ability, boolean copyTargets) {
        this(ability, copyTargets, false);
    }

    public CreateDelayedTriggeredAbilityEffect(DelayedTriggeredAbility ability, boolean copyTargets, boolean initAbility) {
        super(ability.getEffects().get(0).getOutcome());
        this.ability = ability;
        this.copyTargets = copyTargets;
        this.initAbility = initAbility;
    }

    public CreateDelayedTriggeredAbilityEffect(final CreateDelayedTriggeredAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.copyTargets = effect.copyTargets;
        this.initAbility = effect.initAbility;
    }

    @Override
    public CreateDelayedTriggeredAbilityEffect copy() {
        return new CreateDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = ability.copy();
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        delayedAbility.setSourceObject(source.getSourceObject(game), game);
        if (this.copyTargets) {
            if (source.getTargets().isEmpty()) {
                for (Effect effect : delayedAbility.getEffects()) {
                    effect.setTargetPointer(targetPointer);
                }
            } else {
                delayedAbility.getTargets().addAll(source.getTargets());
                for (Effect effect : delayedAbility.getEffects()) {
                    effect.getTargetPointer().init(game, source);
                }
            }
        }
        if (initAbility) {
            delayedAbility.init(game);
        }
        game.addDelayedTriggeredAbility(delayedAbility);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (ability.getRuleVisible()) {
            return ability.getRule();
        } else {
            return "";
        }
    }

}
