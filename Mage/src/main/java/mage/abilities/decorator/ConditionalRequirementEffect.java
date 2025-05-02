package mage.abilities.decorator;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.FixedCondition;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */

public class ConditionalRequirementEffect extends RequirementEffect {

    protected RequirementEffect effect;
    protected RequirementEffect otherwiseEffect;
    protected Condition condition;
    protected boolean conditionState;
    protected Condition baseCondition;
    protected boolean initDone = false;

    public ConditionalRequirementEffect(RequirementEffect effect, Condition condition) {
        this(effect, condition, null);
    }

    public ConditionalRequirementEffect(RequirementEffect effect, Condition condition, String text) {
        this(effect.getDuration(), effect, condition, null);
        if (text != null) {
            setText(text);
        }
    }

    public ConditionalRequirementEffect(Duration duration, RequirementEffect effect, Condition condition, RequirementEffect otherwiseEffect) {
        super(duration);
        this.effectType = EffectType.REQUIREMENT;
        this.effect = effect;
        this.baseCondition = condition;
        this.otherwiseEffect = otherwiseEffect;
    }

    protected ConditionalRequirementEffect(final ConditionalRequirementEffect effect) {
        super(effect);
        this.effect = (RequirementEffect) effect.effect.copy();
        if (effect.otherwiseEffect != null) {
            this.otherwiseEffect = (RequirementEffect) effect.otherwiseEffect.copy();
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
        effect.setTargetPointer(this.getTargetPointer().copy());
        effect.init(source, game);
        if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.getTargetPointer().copy());
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
            effect.setTargetPointer(this.getTargetPointer().copy());
            return effect.applies(permanent, source, game);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.getTargetPointer().copy());
            return otherwiseEffect.applies(permanent, source, game);
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
    public boolean mustAttack(Game game) {
        if (conditionState) {
            return effect.mustAttack(game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.mustAttack(game);
        }
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        if (conditionState) {
            return effect.mustBlock(game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.mustBlock(game);
        }
        return false;
    }

    @Override
    public boolean mustBlockAny(Game game) {
        if (conditionState) {
            return effect.mustBlockAny(game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.mustBlockAny(game);
        }
        return false;
    }

    @Override
    public boolean mustBlockAllAttackers(Game game) {
        if (conditionState) {
            return effect.mustBlockAllAttackers(game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.mustBlockAllAttackers(game);
        }
        return false;
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        if (conditionState) {
            return effect.mustAttackDefender(source, game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.mustAttackDefender(source, game);
        }
        return null;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        if (conditionState) {
            return effect.mustBlockAttacker(source, game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.mustBlockAttacker(source, game);
        }
        return null;
    }

    @Override
    public UUID mustBlockAttackerIfElseUnblocked(Ability source, Game game) {
        if (conditionState) {
            return effect.mustBlockAttackerIfElseUnblocked(source, game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.mustBlockAttackerIfElseUnblocked(source, game);
        }
        return null;
    }

    @Override
    public int getMinNumberOfBlockers() {
        if (conditionState) {
            return effect.getMinNumberOfBlockers();
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.getMinNumberOfBlockers();
        }
        return super.getMinNumberOfBlockers();
    }

    @Override
    public ConditionalRequirementEffect copy() {
        return new ConditionalRequirementEffect(this);
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
