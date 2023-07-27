package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
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
public class CantBeBlockedByOneEffect extends EvasionEffect {

    protected int amount;

    public CantBeBlockedByOneEffect(int amount) {
        this(amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByOneEffect(int amount, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        this.staticCantBeBlockedMessage = "can't be blocked except by "
                + (CardUtil.numberToText(amount))
                + " or more creatures";
        staticText = "{this} " + this.staticCantBeBlockedMessage;
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
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent == null || !permanent.getId().equals(source.getSourceId())) {
            return false;
        }

        permanent.setMinBlockedBy(amount);
        return true;
    }
}
