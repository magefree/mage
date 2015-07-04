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
import mage.abilities.condition.Condition;
import mage.abilities.condition.FixedCondition;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class ConditionalRestrictionEffect extends RestrictionEffect {

    protected RestrictionEffect effect;
    protected RestrictionEffect otherwiseEffect;
    protected Condition condition;
    protected boolean conditionState;
    protected Condition baseCondition;
    protected boolean initDone = false;

    public ConditionalRestrictionEffect(RestrictionEffect effect, Condition condition) {
        this(Duration.WhileOnBattlefield, effect, condition, null);
    }

    public ConditionalRestrictionEffect(Duration duration, RestrictionEffect effect, Condition condition, RestrictionEffect otherwiseEffect) {
        super(duration);
        this.effect = effect;
        this.baseCondition = condition;
        this.otherwiseEffect = otherwiseEffect;
    }

    public ConditionalRestrictionEffect(final ConditionalRestrictionEffect effect) {
        super(effect);
        this.effect = (RestrictionEffect) effect.effect.copy();
        if (effect.otherwiseEffect != null) {
            this.otherwiseEffect = (RestrictionEffect) effect.otherwiseEffect.copy();
        }
        this.condition = effect.condition;
        this.conditionState = effect.conditionState;
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
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (!initDone) { // if simpleStaticAbility, init won't be called
            init(source, game);
        }
        conditionState = condition.apply(game, source);
        if (conditionState) {
            effect.setTargetPointer(this.targetPointer);
            return effect.applies(permanent, source, game);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            return otherwiseEffect.applies(permanent, source, game);
        }
        if (effect.getDuration() == Duration.OneUse) {
            used = true;
        }
        if (effect.getDuration() == Duration.Custom) {
            this.discard();
        }
        return false;
    }

    @Override
    public boolean canAttack(Game game) {
        if (conditionState) {
            return effect.canAttack(game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.canAttack(game);
        }
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (conditionState) {
            return effect.canBlock(attacker, blocker, source, game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.canBlock(attacker, blocker, source, game);
        }
        return true;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (conditionState) {
            return effect.canBeBlocked(attacker, blocker, source, game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.canBeBlocked(attacker, blocker, source, game);
        }
        return true;
    }

    @Override
    public boolean canBeUntapped(Permanent permanent, Ability source, Game game) {
        if (conditionState) {
            return effect.canBeUntapped(permanent, source, game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.canBeUntapped(permanent, source, game);
        }
        return true;
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game) {
        if (conditionState) {
            return effect.canUseActivatedAbilities(permanent, source, game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.canUseActivatedAbilities(permanent, source, game);
        }
        return true;
    }

    @Override
    public ConditionalRestrictionEffect copy() {
        return new ConditionalRestrictionEffect(this);
    }

}
