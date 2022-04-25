package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class CantAttackBlockTargetEffect extends RestrictionEffect {

    public CantAttackBlockTargetEffect(Duration duration) {
        super(duration);
    }

    public CantAttackBlockTargetEffect(final CantAttackBlockTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(targetPointer.getFirst(game, source));
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public CantAttackBlockTargetEffect copy() {
        return new CantAttackBlockTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("target ");
        if (mode.getTargets().isEmpty()) {
            sb.append("creature");
        } else {
            sb.append(mode.getTargets().get(0).getTargetName());
        }
        sb.append(" can't attack or block ");
        if (duration == Duration.EndOfTurn) {
            sb.append("this turn");
        } else {
            sb.append(duration);
        }
        return sb.toString();
    }
}
