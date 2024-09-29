
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * The controlling player of the source ability has to sacrifice all permanents
 * under their control that match the [filter].
 *
 * @author Susucr
 */
public class SacrificeAllControllerEffect extends OneShotEffect {

    private final FilterPermanent filter;

    /**
     * sacrifice all [filter] you control
     */
    public SacrificeAllControllerEffect(FilterPermanent filter) {
        super(Outcome.Sacrifice);
        this.filter = filter;
        this.staticText = "sacrifice all " + filter.getMessage() + " you control";
    }

    private SacrificeAllControllerEffect(final SacrificeAllControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public SacrificeAllControllerEffect copy() {
        return new SacrificeAllControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = source.getControllerId();
        boolean result = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (!filter.match(permanent, playerId, source, game)) {
                continue;
            }
            permanent.sacrifice(source, game);
            result = true;
        }
        return result;
    }
}

