package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DestroyAllControlledTargetEffect extends OneShotEffect {

    private final FilterPermanent filter;

    public DestroyAllControlledTargetEffect(FilterPermanent filter) {
        super(Outcome.DestroyPermanent);
        this.filter = filter;
        staticText = "Destroy all " + filter.getMessage() + " target player controls";
    }

    public DestroyAllControlledTargetEffect(final DestroyAllControlledTargetEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public DestroyAllControlledTargetEffect copy() {
        return new DestroyAllControlledTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getFirstTarget(), game)) {
            permanent.destroy(source, game, false);
        }
        return true;
    }
}
