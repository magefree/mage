package mage.abilities.decorator;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;

/**
 * Adds condition to {@link OneShotEffect}. Acts as decorator.
 *
 * @author maurer.it_at_gmail.com
 */
public class ConditionalOneShotEffect extends OneShotEffect {

    private final Effects effects = new Effects();
    private final Effects otherwiseEffects = new Effects();
    private final Condition condition;

    public ConditionalOneShotEffect(OneShotEffect effect, Condition condition) {
        this(effect, null, condition, null);
    }

    public ConditionalOneShotEffect(OneShotEffect effect, Condition condition, String text) {
        this(effect, null, condition, text);
    }

    public ConditionalOneShotEffect(OneShotEffect effect, OneShotEffect otherwiseEffect, Condition condition, String text) {
        super(effect.getOutcome());
        if (effect != null) {
            this.effects.add(effect);
        }
        if (otherwiseEffect != null) {
            this.otherwiseEffects.add(otherwiseEffect);
        }
        this.condition = condition;
        this.staticText = text;
    }

    public ConditionalOneShotEffect(ConditionalOneShotEffect effect) {
        super(effect);
        this.effects.addAll(effect.effects.copy());
        this.otherwiseEffects.addAll(effect.otherwiseEffects.copy());
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // nothing to do - no problem
        Effects toApply = condition.apply(game, source) ? effects : otherwiseEffects;
        if (toApply.isEmpty()) {
            return true;
        }
        toApply.setTargetPointer(this.targetPointer);
        toApply.stream().forEach(effect -> effect.apply(game, source));
        return true;
    }

    public ConditionalOneShotEffect addEffect(OneShotEffect effect) {
        this.effects.add(effect);
        return this;
    }

    public ConditionalOneShotEffect addOtherwiseEffect(OneShotEffect effect) {
        this.otherwiseEffects.add(effect);
        return this;
    }

    @Override
    public ConditionalOneShotEffect copy() {
        return new ConditionalOneShotEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (otherwiseEffects.isEmpty()) {
            return "if " + condition.toString() + ", " + effects.getText(mode);
        }
        return effects.getText(mode) + ". If " + condition.toString() + ", " + otherwiseEffects.getText(mode);
    }
}
