

package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;



/**
 *
 * @author LevelX2
 */
public class TapAllEffect extends OneShotEffect {

    protected FilterPermanent filter;

    public TapAllEffect(FilterPermanent filter) {
        super(Outcome.Tap);
        this.filter = filter;
        setText();
    }

    public TapAllEffect(final TapAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public TapAllEffect copy() {
        return new TapAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.tap(source, game);
        }
        return true;
    }

    private void setText() {
        staticText = "tap all " + filter.getMessage();
    }

}
