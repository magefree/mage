
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
public class DestroyAllEffect extends OneShotEffect {

    private FilterPermanent filter;
    private boolean noRegen;

    public DestroyAllEffect(FilterPermanent filter) {
        this(filter, false);
    }

    public DestroyAllEffect(FilterPermanent filter, boolean noRegen) {
        super(Outcome.DestroyPermanent);
        this.filter = filter;
        this.noRegen = noRegen;
        if (noRegen) {
            staticText = "destroy all " + filter.getMessage() + ". They can't be regenerated";
        } else {
            staticText = "destroy all " + filter.getMessage();
        }
    }

    public DestroyAllEffect(final DestroyAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.noRegen = effect.noRegen;
    }

    @Override
    public DestroyAllEffect copy() {
        return new DestroyAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.destroy(source, game, noRegen);
        }
        return true;
    }

}
