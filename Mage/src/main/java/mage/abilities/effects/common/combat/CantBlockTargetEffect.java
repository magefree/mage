package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

/**
 * @author North
 */
public class CantBlockTargetEffect extends RestrictionEffect {

    public CantBlockTargetEffect(Duration duration) {
        super(duration);
    }

    public CantBlockTargetEffect(final CantBlockTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.targetPointer.getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public CantBlockTargetEffect copy() {
        return new CantBlockTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (mode.getTargets().isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        if (target.getMaxNumberOfTargets() == Integer.MAX_VALUE) {
            sb.append("any number of ");
        } else if (target.getMaxNumberOfTargets() > 1) {
            if (target.getMaxNumberOfTargets() != target.getNumberOfTargets()) {
                sb.append("up to ");
            }
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(' ');
        }
        sb.append("target ").append(mode.getTargets().get(0).getTargetName());

        sb.append(" can't block");
        if (this.duration == Duration.EndOfTurn) {
            sb.append(" this turn");
        }

        return sb.toString();
    }
}
