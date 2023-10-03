package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author mzulch
 */
public class CanBlockAdditionalCreatureTargetEffect extends ContinuousEffectImpl {

    protected int amount;

    public CanBlockAdditionalCreatureTargetEffect() {
        this(Duration.EndOfTurn, 1);
    }

    public CanBlockAdditionalCreatureTargetEffect(Duration duration, int amount) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
        staticText = setText();
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

    private String setText() {
        String text = "target can block ";
        switch (amount) {
            case 0:
                text += "any number of creatures";
                break;
            default:
                text += CardUtil.numberToText(amount, "an") + " additional creature" + (amount > 1 ? "s" : "");
        }
        if (duration == Duration.EndOfTurn) {
            text += " this turn";
        }
        if (duration == Duration.WhileOnBattlefield) {
            text += " each combat";
        }
        return text;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
