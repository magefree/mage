
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
 *
 * @author LevelX2
 */
public class CanBlockAdditionalCreatureEffect extends ContinuousEffectImpl {

    protected int amount;

    public CanBlockAdditionalCreatureEffect() {
        this(1);
    }

    /**
     * Changes the number of creatures source creature can block
     *
     * @param amount 0 = any number, 1-x = n additional blocks
     */
    public CanBlockAdditionalCreatureEffect(int amount) {
        this(Duration.WhileOnBattlefield, amount);
    }

    public CanBlockAdditionalCreatureEffect(Duration duration, int amount) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
        staticText = setText();
    }

    public CanBlockAdditionalCreatureEffect(final CanBlockAdditionalCreatureEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CanBlockAdditionalCreatureEffect copy() {
        return new CanBlockAdditionalCreatureEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            // maxBlocks = 0 equals to "can block any number of creatures"
            if (amount > 0) {
                permanent.setMaxBlocks(permanent.getMaxBlocks() + amount);
            } else {
                permanent.setMaxBlocks(0);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private String setText() {
        String text = "{this} can block ";
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
