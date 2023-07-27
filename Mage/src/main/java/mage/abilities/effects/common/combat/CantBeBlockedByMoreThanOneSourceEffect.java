
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
 * @author Quercitron
 */
public class CantBeBlockedByMoreThanOneSourceEffect extends EvasionEffect {

    protected int amount;

    public CantBeBlockedByMoreThanOneSourceEffect() {
        this(1);
    }

    public CantBeBlockedByMoreThanOneSourceEffect(int amount) {
        this(amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneSourceEffect(int amount, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        this.staticCantBeBlockedMessage = "can't be blocked by more than "
                + (CardUtil.numberToText(amount)) + " creature" + (amount > 1 ? "s" : "")
                + (duration == Duration.EndOfTurn ? " each combat this turn" : "");
        staticText = "{this} " + this.staticCantBeBlockedMessage;
    }

    protected CantBeBlockedByMoreThanOneSourceEffect(final CantBeBlockedByMoreThanOneSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CantBeBlockedByMoreThanOneSourceEffect copy() {
        return new CantBeBlockedByMoreThanOneSourceEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent == null || !permanent.getId().equals(source.getSourceId())) {
            return false;
        }

        permanent.setMaxBlockedBy(amount);
        return true;
    }
}
