package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Viserion
 */
public class UntapAllLandsControllerEffect extends OneShotEffect {

    private final FilterLandPermanent filter;

    public UntapAllLandsControllerEffect() {
        this(StaticFilters.FILTER_LANDS);
    }

    public UntapAllLandsControllerEffect(FilterLandPermanent filter) {
        super(Outcome.Untap);
        staticText = "untap all " + filter.getMessage() + " you control";
        this.filter = filter;
    }

    protected UntapAllLandsControllerEffect(final UntapAllLandsControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Permanent land : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                land.untap(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public UntapAllLandsControllerEffect copy() {
        return new UntapAllLandsControllerEffect(this);
    }

}
