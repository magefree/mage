
package mage.abilities.effects.common.combat;

import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;


import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public class CantBeBlockedByMoreThanOneSourceEffect extends ContinuousEffectImpl {

    protected int amount;

    public CantBeBlockedByMoreThanOneSourceEffect() {
        this(1);
    }

    public CantBeBlockedByMoreThanOneSourceEffect(int amount) {
        this(amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneSourceEffect(int amount, Duration duration) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
        staticText = "{this} can't be blocked by more than " + CardUtil.numberToText(amount) + " creature" + (amount > 1 ? "s" : "")
        + (duration == Duration.EndOfTurn ? " each combat this turn" : "");
    }

    public CantBeBlockedByMoreThanOneSourceEffect(final CantBeBlockedByMoreThanOneSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CantBeBlockedByMoreThanOneSourceEffect copy() {
        return new CantBeBlockedByMoreThanOneSourceEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm != null) {
            if (layer == Layer.RulesEffects) {
                perm.setMaxBlockedBy(amount);
            }
            return true;
        }
        return false;
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
