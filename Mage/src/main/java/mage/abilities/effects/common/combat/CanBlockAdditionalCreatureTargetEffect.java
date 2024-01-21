package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author mzulch
 */
public class CanBlockAdditionalCreatureTargetEffect extends ContinuousEffectImpl {

    protected int amount;

    /**
     * Need to set text manually
     * Target can block an additional creature this turn
     */
    public CanBlockAdditionalCreatureTargetEffect() {
        this(Duration.EndOfTurn, 1);
    }

    /**
     * Need to set text manually
     * @param duration of effect
     * @param amount 0 = any number
     */
    public CanBlockAdditionalCreatureTargetEffect(Duration duration, int amount) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
    }

    protected CanBlockAdditionalCreatureTargetEffect(final CanBlockAdditionalCreatureTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CanBlockAdditionalCreatureTargetEffect copy() {
        return new CanBlockAdditionalCreatureTargetEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent target = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (target == null) {
            return false;
        }

        // maxBlocks = 0 equals to "can block any number of creatures"
        if (amount > 0) {
            target.setMaxBlocks(target.getMaxBlocks() + amount);
        } else {
            target.setMaxBlocks(0);
        }

        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
