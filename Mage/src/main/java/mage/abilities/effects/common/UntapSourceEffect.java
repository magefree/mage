
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class UntapSourceEffect extends OneShotEffect {

    public UntapSourceEffect() {
        super(Outcome.Untap);
        staticText = "untap {this}";
    }

    protected UntapSourceEffect(final UntapSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.untap(game);
        }
        return true;
    }

    @Override
    public UntapSourceEffect copy() {
        return new UntapSourceEffect(this);
    }

}
