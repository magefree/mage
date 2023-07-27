
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author Quercitron
 */
public class CantBeBlockedByMoreThanOneAllEffect extends EvasionEffect {

    private FilterPermanent filter;
    protected int amount;

    public CantBeBlockedByMoreThanOneAllEffect(FilterPermanent filter) {
        this(1, filter, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneAllEffect(int amount, FilterPermanent filter) {
        this(amount, filter, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneAllEffect(int amount, FilterPermanent filter, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        this.filter = filter;
        this.staticCantBeBlockedMessage = "can't be blocked by more than "
                + (CardUtil.numberToText(amount)) + " creature" + (amount > 1 ? "s" : "")
                + (duration == Duration.EndOfTurn ? " each combat this turn" : "");
        staticText = "Each " + filter.getMessage() + " "
                + this.staticCantBeBlockedMessage;
    }

    protected CantBeBlockedByMoreThanOneAllEffect(final CantBeBlockedByMoreThanOneAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter;
    }

    @Override
    public CantBeBlockedByMoreThanOneAllEffect copy() {
        return new CantBeBlockedByMoreThanOneAllEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent == null || !filter.match(permanent, source.getControllerId(), source, game)) {
            return false;
        }

        permanent.setMaxBlockedBy(amount);
        return true;
    }
}
