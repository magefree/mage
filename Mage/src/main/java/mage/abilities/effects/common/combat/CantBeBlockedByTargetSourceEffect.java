package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

/**
 * @author LevelX2
 */

public class CantBeBlockedByTargetSourceEffect extends RestrictionEffect {

    /**
     * Target creature(s) cant block the source creature
     *
     * @param duration
     */

    public CantBeBlockedByTargetSourceEffect(Duration duration) {
        super(duration);
    }

    public CantBeBlockedByTargetSourceEffect(final CantBeBlockedByTargetSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return source.getSourceId().equals(permanent.getId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return !this.getTargetPointer().getTargets(game, source).contains(blocker.getId());
    }

    @Override
    public CantBeBlockedByTargetSourceEffect copy() {
        return new CantBeBlockedByTargetSourceEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        if (target.getNumberOfTargets() > 1) {
            if (target.getNumberOfTargets() < target.getMaxNumberOfTargets()) {
                sb.append("Up to");
            }
            sb.append(target.getMaxNumberOfTargets()).append(" target ");
        } else {
            sb.append("Target ");
        }
        sb.append(target.getTargetName()).append(" can't block {this}");
        if (duration == Duration.EndOfTurn) {
            sb.append(" this turn");
        }

        return sb.toString();
    }
}
