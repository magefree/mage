package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author cg5
 */
public class TurnFaceUpTargetEffect extends OneShotEffect {

    public TurnFaceUpTargetEffect() {
        super(Outcome.Benefit);
    }

    public TurnFaceUpTargetEffect(final TurnFaceUpTargetEffect effect) {
        super(effect);
    }

    @Override
    public TurnFaceUpTargetEffect copy() {
        return new TurnFaceUpTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID target = targetPointer.getFirst(game, source);
        if (target != null) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                return permanent.turnFaceUp(source, game, source.getControllerId());
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "turn target " + mode.getTargets().get(0).getTargetName() + " face-up";
    }
}
