

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author nantuko
 */

public class UntapAllControllerEffect extends OneShotEffect {

    private final FilterPermanent filter;
    private final boolean includeSource;

    public UntapAllControllerEffect(FilterPermanent filter) {
        this(filter, null);
    }

    public UntapAllControllerEffect(FilterPermanent filter, String rule) {
        this(filter, rule, true);
    }

    public UntapAllControllerEffect(FilterPermanent filter, String rule, boolean includeSource) {
        super(Outcome.Untap);
        if (rule == null || rule.isEmpty()) {
            staticText = "untap all " + (includeSource ? "" : "other ") + filter.getMessage() + " you control";
        } else {
            staticText = rule;
        }
        this.filter = filter;
        this.includeSource = includeSource;
    }

    protected UntapAllControllerEffect(final UntapAllControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.includeSource = effect.includeSource;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                if (includeSource || sourcePermanent == null || !sourcePermanent.getId().equals(permanent.getId())) {
                    permanent.untap(game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public UntapAllControllerEffect copy() {
        return new UntapAllControllerEffect(this);
    }

}
