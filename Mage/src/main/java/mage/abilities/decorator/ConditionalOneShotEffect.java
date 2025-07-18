package mage.abilities.decorator;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.targetpointer.TargetPointer;
import mage.util.CardUtil;

/**
 * Adds condition to {@link OneShotEffect}. Acts as decorator.
 *
 * @author maurer.it_at_gmail.com
 */
public class ConditionalOneShotEffect extends OneShotEffect {

    private final Effects effects = new Effects();
    private final Effects otherwiseEffects = new Effects();
    private final Condition condition;
    private boolean withConditionTextAtEnd = false;

    public ConditionalOneShotEffect(OneShotEffect effect, Condition condition) {
        this(effect, null, condition, null);
    }

    public ConditionalOneShotEffect(OneShotEffect effect, Condition condition, String text) {
        this(effect, null, condition, text);
    }

    private static Outcome generateOutcome(OneShotEffect effect, OneShotEffect otherwiseEffect) {
        if (effect != null) {
            return effect.getOutcome();
        }
        if (otherwiseEffect != null) {
            return Outcome.inverse(otherwiseEffect.getOutcome());
        }
        throw new IllegalArgumentException("Wrong code usage: ConditionalOneShot should start with an effect to generate Outcome.");
    }

    public ConditionalOneShotEffect(OneShotEffect effect, OneShotEffect otherwiseEffect, Condition condition, String text) {
        super(generateOutcome(effect, otherwiseEffect));
        if (effect != null) {
            this.effects.add(effect);
        }
        if (otherwiseEffect != null) {
            this.otherwiseEffects.add(otherwiseEffect);
        }
        this.condition = condition;
        this.staticText = text;
    }

    protected ConditionalOneShotEffect(final ConditionalOneShotEffect effect) {
        super(effect);
        this.effects.addAll(effect.effects.copy());
        this.otherwiseEffects.addAll(effect.otherwiseEffects.copy());
        this.condition = effect.condition;
        this.withConditionTextAtEnd = effect.withConditionTextAtEnd;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // nothing to do - no problem
        Effects toApply = condition.apply(game, source) ? effects : otherwiseEffects;
        if (toApply.isEmpty()) {
            return true;
        }
        toApply.setTargetPointer(this.getTargetPointer().copy());
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

    public ConditionalOneShotEffect withConditionTextAtEnd(boolean withConditionTextAtEnd) {
        this.withConditionTextAtEnd = withConditionTextAtEnd;
        return this;
    }

    @Override
    public void setValue(String key, Object value) {
        super.setValue(key, value);
        this.effects.setValue(key, value);
        this.otherwiseEffects.setValue(key, value);
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

        String conditionText = condition.toString();
        if (conditionText.startsWith("if ") || conditionText.startsWith("If ")) {
            conditionText = conditionText.substring(3);
        }

        if (otherwiseEffects.isEmpty()) {
            if (withConditionTextAtEnd) {
                String effectText = effects.getText(mode);
                return CardUtil.getTextWithFirstCharLowerCase(effectText.substring(0, effectText.length() - 1))
                        + " if " + conditionText;
            } else {
                return "if " + conditionText + ", "
                        + CardUtil.getTextWithFirstCharLowerCase(effects.getText(mode));
            }
        }
        return effects.getText(mode) + ". If " + conditionText + ", "
                + CardUtil.getTextWithFirstCharLowerCase(otherwiseEffects.getText(mode));
    }

    @Override
    public ConditionalOneShotEffect setTargetPointer(TargetPointer targetPointer) {
        effects.setTargetPointer(targetPointer);
        otherwiseEffects.setTargetPointer(targetPointer);
        super.setTargetPointer(targetPointer);
        return this;
    }

    @Override
    public ConditionalOneShotEffect withTargetDescription(String target) {
        effects.forEach(effect -> effect.withTargetDescription(target));
        otherwiseEffects.forEach(effect -> effect.withTargetDescription(target));
        return this;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
