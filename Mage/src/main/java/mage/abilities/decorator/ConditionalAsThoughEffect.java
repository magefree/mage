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
package mage.abilities.decorator;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.Duration;
import mage.game.Game;

/**
 * @author LevelX2
 */
public class ConditionalAsThoughEffect extends AsThoughEffectImpl {

    protected AsThoughEffect effect;
    protected AsThoughEffect otherwiseEffect;
    protected Condition condition;
    protected boolean conditionState;

    public ConditionalAsThoughEffect(AsThoughEffect effect, Condition condition) {
        this(effect, condition, null);
    }

    public ConditionalAsThoughEffect(AsThoughEffect effect, Condition condition, AsThoughEffect otherwiseEffect) {
        super(effect.getAsThoughEffectType(), effect.getDuration(), effect.getOutcome());
        this.effect = effect;
        this.condition = condition;
        this.otherwiseEffect = otherwiseEffect;
    }

    public ConditionalAsThoughEffect(final ConditionalAsThoughEffect effect) {
        super(effect);
        this.effect = effect.effect.copy();
        if (effect.otherwiseEffect != null) {
            this.otherwiseEffect = effect.otherwiseEffect.copy();
        }
        this.condition = effect.condition;
        this.conditionState = effect.conditionState;
    }

    @Override
    public boolean isDiscarded() {
        return effect.isDiscarded() || (otherwiseEffect != null && otherwiseEffect.isDiscarded());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        conditionState = condition.apply(game, source);
        if (conditionState) {
            effect.setTargetPointer(this.targetPointer);
            return effect.apply(game, source);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            return otherwiseEffect.apply(game, source);
        }
        if (!conditionState && effect.getDuration() == Duration.OneUse) {
            used = true;
        }
        if (!conditionState && effect.getDuration() == Duration.Custom) {
            this.discard();
        }
        return false;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        conditionState = condition.apply(game, source);
        if (conditionState) {
            effect.setTargetPointer(this.targetPointer);
            return effect.applies(sourceId, source, affectedControllerId, game);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            return otherwiseEffect.applies(sourceId, source, affectedControllerId, game);
        }
        return false;
    }

    @Override
    public ConditionalAsThoughEffect copy() {
        return new ConditionalAsThoughEffect(this);
    }
}
