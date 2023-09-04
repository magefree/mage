package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public class CantBeBlockedAllEffect extends EvasionEffect {

    private final FilterPermanent filter;

    public CantBeBlockedAllEffect(FilterPermanent filter, Duration duration) {
        super(duration);
        this.filter = filter;

        this.staticCantBeBlockedMessage = "can't be blocked"
            + (duration == Duration.EndOfTurn ? " this turn" : "");

        this.staticText = filter.getMessage() + " "
            + this.staticCantBeBlockedMessage;
    }

    private CantBeBlockedAllEffect(final CantBeBlockedAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public CantBeBlockedAllEffect copy() {
        return new CantBeBlockedAllEffect(this);
    }

    @Override
    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }
}
