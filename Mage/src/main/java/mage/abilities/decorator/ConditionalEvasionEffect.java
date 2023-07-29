package mage.abilities.decorator;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.FixedCondition;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.effects.EvasionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Susucr
 */
public class ConditionalEvasionEffect extends EvasionEffect {

    protected EvasionEffect effect;
    protected EvasionEffect otherwiseEffect;
    protected Condition condition;
    protected boolean conditionState;
    protected Condition baseCondition;
    protected boolean initDone = false;

    public ConditionalEvasionEffect(EvasionEffect effect, Condition condition) {
        this(effect, condition, null);
    }

    public ConditionalEvasionEffect(EvasionEffect effect, Condition condition, String text) {
        this(effect.getDuration(), effect, condition, null, text);
    }

    public ConditionalEvasionEffect(Duration duration, EvasionEffect effect, Condition condition, EvasionEffect otherwiseEffect) {
        this(duration, effect, condition, otherwiseEffect, null);
    }

    public ConditionalEvasionEffect(Duration duration, EvasionEffect effect, Condition condition, EvasionEffect otherwiseEffect, String text) {
        super(duration);
        this.effect = effect;
        this.baseCondition = condition;
        this.otherwiseEffect = otherwiseEffect;
        if (text != null) {
            this.setText(text);
        }
    }

    public ConditionalEvasionEffect(final ConditionalEvasionEffect effect) {
        super(effect);
        this.effect = (EvasionEffect) effect.effect.copy();
        if (effect.otherwiseEffect != null) {
            this.otherwiseEffect = (EvasionEffect) effect.otherwiseEffect.copy();
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
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (conditionState) {
            return effect.cantBeBlocked(attacker, blocker, source, game, canUseChooseDialogs);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.cantBeBlocked(attacker, blocker, source, game, canUseChooseDialogs);
        }
        return false;
    }

    @Override
    public String cantBeBlockedMessage(Permanent attacker, Ability source, Game game) {
        if (conditionState) {
            return effect.cantBeBlockedMessage(attacker, source, game);
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.cantBeBlockedMessage(attacker, source, game);
        }

        // null is expected when the condition is false and there is no otherwise.
        // The meaning for that is to not display an hint.
        return null;
    }

    @Override
    public ConditionalEvasionEffect copy() {
        return new ConditionalEvasionEffect(this);
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
