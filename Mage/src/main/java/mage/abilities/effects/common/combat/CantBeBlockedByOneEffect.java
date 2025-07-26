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
 * @author North
 */
public class CantBeBlockedByOneEffect extends ContinuousEffectImpl {

    protected int amount;

    public CantBeBlockedByOneEffect(int amount) {
        this(amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByOneEffect(int amount, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        staticText = "{this} can't be blocked except by " + CardUtil.numberToText(amount) + " or more creatures";
    }

    protected CantBeBlockedByOneEffect(final CantBeBlockedByOneEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CantBeBlockedByOneEffect copy() {
        return new CantBeBlockedByOneEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            ((Permanent) object).setMinBlockedBy(amount);
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
}
