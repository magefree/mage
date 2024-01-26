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
public class SuspectTargetEffect extends OneShotEffect {

    public SuspectTargetEffect() {
        super(Outcome.Benefit);
    }

    private SuspectTargetEffect(final SuspectTargetEffect effect) {
        super(effect);
    }

    @Override
    public SuspectTargetEffect copy() {
        return new SuspectTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.setSuspected(true, game, source);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "suspect " + getTargetPointer().describeTargets(mode.getTargets(), "that creature");
    }
}
