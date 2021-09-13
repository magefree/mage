package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class TransformTargetEffect extends OneShotEffect {

    public TransformTargetEffect() {
        super(Outcome.Transform);
    }

    public TransformTargetEffect(final TransformTargetEffect effect) {
        super(effect);
    }

    @Override
    public TransformTargetEffect copy() {
        return new TransformTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null && permanent.transform(game);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (mode.getTargets().isEmpty()) {
            return "transform target";
        }
        Target target = mode.getTargets().get(0);
        if (target.getMaxNumberOfTargets() > 1) {
            if (target.getMaxNumberOfTargets() == target.getNumberOfTargets()) {
                return "transform " + CardUtil.numberToText(target.getNumberOfTargets()) + " target " + target.getTargetName();
            } else {
                return "transform up to " + CardUtil.numberToText(target.getMaxNumberOfTargets()) + " target " + target.getTargetName();
            }
        } else {
            return "transform target " + mode.getTargets().get(0).getTargetName();
        }
    }
}
