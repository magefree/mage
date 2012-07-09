package mage.abilities.decorator;

import mage.Constants;
import mage.Constants.Duration;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;

/**
 * Adds condition to {@link ContinuousEffect}. Acts as decorator.
 *
 * @author nantuko
 */
public class ConditionalContinousEffect extends ContinuousEffectImpl<ConditionalContinousEffect> {

    protected ContinuousEffect effect;
    protected ContinuousEffect otherwiseEffect;
    protected Condition condition;

    public ConditionalContinousEffect(ContinuousEffect effect, Condition condition, String text) {
        this(effect, null, condition, text);
    }

    /**
     * Only use this if both effects have the same layers
     *
     * @param effect
     * @param otherwiseEffect
     * @param condition
     * @param text
     */
    public ConditionalContinousEffect(ContinuousEffect effect, ContinuousEffect otherwiseEffect, Condition condition, String text) {
        super(effect.getDuration(), effect.getLayer(), effect.getSublayer(), effect.getOutcome());
        this.effect = effect;
        this.otherwiseEffect = otherwiseEffect;
        this.condition = condition;
        this.staticText = text;
    }

    public ConditionalContinousEffect(final ConditionalContinousEffect effect) {
        super(effect);
        this.effect = effect.effect;
        this.otherwiseEffect = effect.otherwiseEffect;
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
        if (condition.apply(game, source)) {
            effect.setTargetPointer(this.targetPointer);
            return effect.apply(layer, sublayer, source, game);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            return otherwiseEffect.apply(layer, sublayer, source, game);
        }
        if (!condition.apply(game, source) && effect.getDuration() == Duration.OneUse) {
            used = true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (condition.apply(game, source)) {
            effect.setTargetPointer(this.targetPointer);
            return effect.apply(game, source);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            return otherwiseEffect.apply(game, source);
        }
        if (!condition.apply(game, source) && effect.getDuration() == Duration.OneUse) {
            used = true;
        }
        return false;
    }

    @Override
    public boolean hasLayer(Constants.Layer layer) {
        return effect.hasLayer(layer);
    }

    @Override
    public ConditionalContinousEffect copy() {
        return new ConditionalContinousEffect(this);
    }
}
