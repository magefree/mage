package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;

public abstract class WhileConditionContinuousEffect extends ContinuousEffectImpl {
    protected Condition condition;

    public WhileConditionContinuousEffect(Duration duration, Layer layer, SubLayer sublayer, Condition condition, Outcome outcome) {
        super(duration, outcome);
        this.condition = condition;
        this.layer = layer;
        this.sublayer = sublayer;
    }

    protected WhileConditionContinuousEffect(final WhileConditionContinuousEffect effect) {
        super(effect);
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (condition.apply(game, source)) {
            return applyEffect(game, source);
        }
        return false;
    }

    protected abstract boolean applyEffect(Game game, Ability source);
}
