
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class UntapAllEffect extends OneShotEffect {

    private final FilterPermanent filter;

    public UntapAllEffect(FilterPermanent filter) {
        super(Outcome.Untap);
        staticText = "untap all " + filter.getMessage();
        this.filter = filter;
    }

    protected UntapAllEffect(final UntapAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.untap(game);
        }
        return true;
    }

    @Override
    public UntapAllEffect copy() {
        return new UntapAllEffect(this);
    }

}
