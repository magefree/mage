package mage.abilities.decorator;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.CostModificationEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.Duration;
import mage.game.Game;

/**
 * @author JayDi85
 */
public class ConditionalCostModificationEffect extends CostModificationEffectImpl {

    protected CostModificationEffect effect;
    protected CostModificationEffect otherwiseEffect;
    protected Condition condition;
    protected boolean conditionState;

    public ConditionalCostModificationEffect(CostModificationEffect effect, Condition condition, String text) {
        this(effect, condition, null, text);
    }

    public ConditionalCostModificationEffect(CostModificationEffect effect, Condition condition, CostModificationEffect otherwiseEffect,
                                             String text) {
        super(effect.getDuration(), effect.getOutcome(), effect.getModificationType());
        this.effect = effect;
        this.condition = condition;
        this.otherwiseEffect = otherwiseEffect;
        if (text != null) {
            this.setText(text);
        }
    }

    public ConditionalCostModificationEffect(final ConditionalCostModificationEffect effect) {
        super(effect);
        this.effect = (CostModificationEffect) effect.effect.copy();
        if (effect.otherwiseEffect != null) {
            this.otherwiseEffect = (CostModificationEffect) effect.otherwiseEffect.copy();
        }
        this.condition = effect.condition;
        this.conditionState = effect.conditionState;
    }

    @Override
    public boolean isDiscarded() {
        return effect.isDiscarded() || (otherwiseEffect != null && otherwiseEffect.isDiscarded());
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        conditionState = condition.apply(game, source);
        if (conditionState) {
            effect.setTargetPointer(this.targetPointer);
            return effect.apply(game, source, abilityToModify);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            return otherwiseEffect.apply(game, source, abilityToModify);
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
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        conditionState = condition.apply(game, source);
        if (conditionState) {
            effect.setTargetPointer(this.targetPointer);
            return effect.applies(abilityToModify, source, game);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            return otherwiseEffect.applies(abilityToModify, source, game);
        }
        return false;
    }

    @Override
    public ConditionalCostModificationEffect copy() {
        return new ConditionalCostModificationEffect(this);
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
