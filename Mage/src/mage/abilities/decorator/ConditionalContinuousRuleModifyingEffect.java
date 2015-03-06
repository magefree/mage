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

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.abilities.condition.FixedCondition;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class ConditionalContinuousRuleModifyingEffect extends ContinuousRuleModifyingEffectImpl  {

    protected ContinuousRuleModifyingEffect effect;
    protected ContinuousRuleModifyingEffect otherwiseEffect;
    protected Condition condition;
    protected Condition baseCondition;
    protected boolean initDone = false;

    public ConditionalContinuousRuleModifyingEffect(ContinuousRuleModifyingEffect effect, Condition condition) {
        this(effect, condition, null);
    }

    public ConditionalContinuousRuleModifyingEffect(ContinuousRuleModifyingEffect effect, Condition condition, ContinuousRuleModifyingEffect otherwiseEffect) {
        super(effect.getDuration(), effect.getOutcome());
        this.effect = effect;
        this.baseCondition = condition;
        this.otherwiseEffect = otherwiseEffect;
    }

    public ConditionalContinuousRuleModifyingEffect(final ConditionalContinuousRuleModifyingEffect effect) {
        super(effect);
        this.effect = (ContinuousRuleModifyingEffect) effect.effect.copy();
        if (effect.otherwiseEffect != null) {
            this.otherwiseEffect = (ContinuousRuleModifyingEffect) effect.otherwiseEffect.copy();
        }
        this.condition = effect.condition;
        this.baseCondition = effect.baseCondition;
        this.initDone = effect.initDone;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (baseCondition instanceof LockedInCondition) {
            condition = new FixedCondition(((LockedInCondition) baseCondition).getBaseCondition().apply(game, source));
        } else {
            condition = baseCondition;
        }
        effect.setTargetPointer(this.targetPointer);
        effect.init(source, game);
        if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            otherwiseEffect.init(source, game);
        }
        initDone = true;
    }


    @Override
    public boolean isDiscarded() {
        return effect.isDiscarded() || (otherwiseEffect != null && otherwiseEffect.isDiscarded());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        if (effect.checksEventType(event, game)) {
            return true;
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.checksEventType(event, game);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!initDone) { // if simpleStaticAbility, init won't be called
            init(source, game);
        }
        if (condition.apply(game, source)) {
            effect.setTargetPointer(this.targetPointer);
            return effect.applies(event, source, game);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            return otherwiseEffect.applies(event, source, game);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText == null || staticText.isEmpty() && this.effect != null) { // usefull for conditional night/day card abilities
            return effect.getText(mode);
        }
        return staticText;
    }

    @Override
    public ConditionalContinuousRuleModifyingEffect copy() {
        return new ConditionalContinuousRuleModifyingEffect(this);
    }
}
