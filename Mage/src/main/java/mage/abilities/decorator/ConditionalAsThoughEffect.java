
package mage.abilities.decorator;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.TargetPointer;

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
    public boolean applies(UUID sourceId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        AsThoughEffect effectOrOtherwiseEffect = condition.apply(game, source) ? effect : otherwiseEffect;
        if (effectOrOtherwiseEffect == null) {
            return false;
        }

        // Function can't have side-effects, so reset target pointer after checking.
        TargetPointer originalTargetPoint = effectOrOtherwiseEffect.getTargetPointer();

        effectOrOtherwiseEffect.setTargetPointer(this.targetPointer);
        boolean appliesTrue = effectOrOtherwiseEffect.applies(sourceId, affectedAbility, source, game, playerId);
        effectOrOtherwiseEffect.setTargetPointer(originalTargetPoint);

        return appliesTrue;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        AsThoughEffect effectOrOtherwiseEffect = condition.apply(game, source) ? effect : otherwiseEffect;
        if (effectOrOtherwiseEffect == null) {
            return false;
        }

        // Function can't have side-effects, so reset target pointer after checking.
        TargetPointer originalTargetPoint = effectOrOtherwiseEffect.getTargetPointer();

        effectOrOtherwiseEffect.setTargetPointer(this.targetPointer);
        boolean appliesTrue = effectOrOtherwiseEffect.applies(sourceId, source, affectedControllerId, game);
        effectOrOtherwiseEffect.setTargetPointer(originalTargetPoint);

        return appliesTrue;
    }

    @Override
    public boolean apply(UUID sourceId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        // If we get to this function then .applies has already returned true, so we know that either
        // effect or otherwiseEffect will be call, no need for the else-if and else like above, the third option
        // will never happen since it will return false from .applies.
        AsThoughEffect effectOrOtherwiseEffect = condition.apply(game, source) ? effect : otherwiseEffect;
        effectOrOtherwiseEffect.setTargetPointer(this.targetPointer);

        return effectOrOtherwiseEffect.apply(sourceId, affectedAbility, source, game, playerId);
    }

    @Override
    public boolean apply(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        // If we get to this function then .applies has already returned true, so we know that either
        // effect or otherwiseEffect will be call, no need for the else-if and else like above, the third option
        // will never happen since it will return false from .applies.
        AsThoughEffect effectOrOtherwiseEffect = condition.apply(game, source) ? effect : otherwiseEffect;
        effectOrOtherwiseEffect.setTargetPointer(this.targetPointer);

        return effectOrOtherwiseEffect.apply(sourceId, source, affectedControllerId, game);
    }

    @Override
    public ConditionalAsThoughEffect copy() {
        return new ConditionalAsThoughEffect(this);
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
