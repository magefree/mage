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
        this(effect, condition, null);
    }

    public ConditionalRestrictionEffect(RestrictionEffect effect, Condition condition, String text) {
        this(effect.getDuration(), effect, condition, null, text);
    }

    public ConditionalRestrictionEffect(Duration duration, RestrictionEffect effect, Condition condition, RestrictionEffect otherwiseEffect) {
        this(duration, effect, condition, otherwiseEffect, null);
    }

    public ConditionalRestrictionEffect(Duration duration, RestrictionEffect effect, Condition condition, RestrictionEffect otherwiseEffect, String text) {
        super(duration);
        this.effect = effect;
        this.baseCondition = condition;
        this.otherwiseEffect = otherwiseEffect;
        if (text != null) {
            this.setText(text);
        }
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
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        if (conditionState) {
            return effect.canAttack(game, canUseChooseDialogs);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.canAttack(game, canUseChooseDialogs);
        }
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (conditionState) {
            return effect.canBlock(attacker, blocker, source, game, canUseChooseDialogs);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.canBlock(attacker, blocker, source, game, canUseChooseDialogs);
        }
        return true;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (conditionState) {
            return effect.canBeBlocked(attacker, blocker, source, game, canUseChooseDialogs);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.canBeBlocked(attacker, blocker, source, game, canUseChooseDialogs);
        }
        return true;
    }

    @Override
    public boolean canBeUntapped(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        if (conditionState) {
            return effect.canBeUntapped(permanent, source, game, canUseChooseDialogs);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.canBeUntapped(permanent, source, game, canUseChooseDialogs);
        }
        return true;
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        if (conditionState) {
            return effect.canUseActivatedAbilities(permanent, source, game, canUseChooseDialogs);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.canUseActivatedAbilities(permanent, source, game, canUseChooseDialogs);
        }
        return true;
    }

    @Override
    public ConditionalRestrictionEffect copy() {
        return new ConditionalRestrictionEffect(this);
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
