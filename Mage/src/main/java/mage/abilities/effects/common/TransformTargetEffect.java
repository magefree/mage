package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class TransformTargetEffect extends OneShotEffect {

    public TransformTargetEffect() {
        super(Outcome.Transform);
    }

    protected TransformTargetEffect(final TransformTargetEffect effect) {
        super(effect);
    }

    @Override
    public TransformTargetEffect copy() {
        return new TransformTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.transform(source, game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "transform " + getTargetPointer().describeTargets(mode.getTargets(), "that creature");
    }
}
