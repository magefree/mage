package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DestroySourceEffect extends OneShotEffect {

    protected boolean noRegen;

    public DestroySourceEffect() {
        this(false);
    }

    public DestroySourceEffect(boolean noRegen) {
        super(Outcome.DestroyPermanent);
        this.noRegen = noRegen;
        staticText = "destroy {this}" + (noRegen ? ". It can't be regenerated" : "");
    }

    protected DestroySourceEffect(final DestroySourceEffect effect) {
        super(effect);
        this.noRegen = effect.noRegen;
    }

    @Override
    public DestroySourceEffect copy() {
        return new DestroySourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null
                && permanent.isPhasedIn()
                && !permanent.isPhasedOutIndirectly()) {
            permanent.destroy(source, game, noRegen);
            return true;
        }
        return false;
    }

}
