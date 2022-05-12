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

    public CantBeBlockedByOneEffect(final CantBeBlockedByOneEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CantBeBlockedByOneEffect copy() {
        return new CantBeBlockedByOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = source.getSourcePermanentIfItStillExists(game);
        if (perm != null) {
            perm.setMinBlockedBy(amount);
            return true;
        }
        return false;
    }
}
