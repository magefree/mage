package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
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
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
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
        StringBuilder sb = new StringBuilder("transform ");
        Target target = mode.getTargets().get(0);
        if (target.getMaxNumberOfTargets() == Integer.MAX_VALUE
                && target.getMinNumberOfTargets() == 0) {
            sb.append("any number of ");
        } else if (target.getMaxNumberOfTargets() != target.getNumberOfTargets()) {
            sb.append("up to ");
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets()));
            sb.append(' ');
        } else if (target.getMaxNumberOfTargets() > 1) {
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets()));
            sb.append(' ');
        }
        String targetName = mode.getTargets().get(0).getTargetName();
        if (!targetName.contains("target ")) {
            sb.append("target ");
        }
        sb.append(targetName);
        return sb.toString();
    }
}
