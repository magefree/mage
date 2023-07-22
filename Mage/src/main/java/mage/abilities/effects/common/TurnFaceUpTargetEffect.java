package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

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
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null && permanent.turnFaceUp(source, game, source.getControllerId());
    }

    @Override
    public String getText(Mode mode) {
        return "turn target " + mode.getTargets().get(0).getTargetName() + " face up";
    }
}
