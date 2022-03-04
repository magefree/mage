package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class TransformSourceEffect extends OneShotEffect {

    public TransformSourceEffect() {
        super(Outcome.Transform);
        staticText = "transform {this}";
    }

    public TransformSourceEffect(final TransformSourceEffect effect) {
        super(effect);
    }

    @Override
    public TransformSourceEffect copy() {
        return new TransformSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        // check not to transform twice the same side
        return permanent != null && permanent.transform(source, game);
    }
}
