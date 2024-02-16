package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public class CantBeBlockedAllEffect extends RestrictionEffect {

    private final FilterPermanent filter;

    public CantBeBlockedAllEffect(FilterPermanent filter, Duration duration) {
        super(duration);
        this.filter = filter;

        this.staticText = filter.getMessage() + " can't be blocked";
        if (duration == Duration.EndOfTurn) {
            this.staticText += " this turn";
        }
    }

    public CantBeBlockedAllEffect(CantBeBlockedAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public CantBeBlockedAllEffect copy() {
        return new CantBeBlockedAllEffect(this);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }
}
