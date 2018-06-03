

package mage.abilities.decorator;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;

/**
 * Adds condition to {@link OneShotEffect}. Acts as decorator.
 *
 * @author maurer.it_at_gmail.com
 */
public class ConditionalOneShotEffect extends OneShotEffect {

    private OneShotEffect effect;
    private OneShotEffect otherwiseEffect;
    private Condition condition;

    public ConditionalOneShotEffect(OneShotEffect effect, Condition condition) {
        this(effect, null, condition, null);
    }

    public ConditionalOneShotEffect(OneShotEffect effect, Condition condition, String text) {
        this(effect, null, condition, text);
    }

    public ConditionalOneShotEffect(OneShotEffect effect, OneShotEffect otherwiseEffect, Condition condition, String text) {
        super(effect.getOutcome());
        this.effect = effect;
        this.otherwiseEffect = otherwiseEffect;
        this.condition = condition;
        this.staticText = text;
    }

    public ConditionalOneShotEffect(ConditionalOneShotEffect effect) {
        super(effect);
        this.effect = (OneShotEffect) effect.effect.copy();
        if (effect.otherwiseEffect != null) {
            this.otherwiseEffect = (OneShotEffect) effect.otherwiseEffect.copy();
        }
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (condition.apply(game, source)) {
            effect.setTargetPointer(this.targetPointer);
            return effect.apply(game, source);
        } else if (otherwiseEffect == null) {
            return true; // nothing to do - no problem
        }
        otherwiseEffect.setTargetPointer(this.targetPointer);
        return otherwiseEffect.apply(game, source);
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
        if (otherwiseEffect == null) {
            return "if " + condition.toString() + ", " + effect.getText(mode);
        }
        return effect.getText(mode) + ". If " + condition.toString() + ", " + otherwiseEffect.getText(mode);
    }

}
