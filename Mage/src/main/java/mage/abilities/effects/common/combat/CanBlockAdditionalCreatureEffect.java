
package mage.abilities.effects.common.combat;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.List;

/**
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
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        staticText = setText();
    }

    protected CanBlockAdditionalCreatureEffect(final CanBlockAdditionalCreatureEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CanBlockAdditionalCreatureEffect copy() {
        return new CanBlockAdditionalCreatureEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            // maxBlocks = 0 equals to "can block any number of creatures"
            if (amount > 0) {
                if (permanent.getMaxBlocks() > 0) {
                    permanent.setMaxBlocks(permanent.getMaxBlocks() + amount);
                }
            } else {
                permanent.setMaxBlocks(0);
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            affectedObjects.add(permanent);
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("{this} can block ");
        if (amount == 0) {
            sb.append("any number of creatures");
        } else {
            sb.append(CardUtil.numberToText(amount, "an"));
            sb.append(" additional creature");
            sb.append((amount > 1 ? "s" : ""));
        }
        if (duration == Duration.EndOfTurn) {
            sb.append(" this turn");
        }
        if (duration == Duration.WhileOnBattlefield && amount == 1) {
            sb.append(" each combat");
        }
        return sb.toString();
    }

}
