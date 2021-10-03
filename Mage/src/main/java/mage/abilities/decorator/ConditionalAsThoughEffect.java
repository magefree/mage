
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
    public boolean applies(UUID sourceId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        conditionState = condition.apply(game, source);
        if (conditionState) {
            effect.setTargetPointer(this.targetPointer);
            return effect.applies(sourceId, affectedAbility, source, game, playerId);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            return otherwiseEffect.applies(sourceId, affectedAbility, source, game, playerId);
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
