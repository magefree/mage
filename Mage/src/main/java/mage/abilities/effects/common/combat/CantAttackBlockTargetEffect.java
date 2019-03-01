package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
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
        staticText = "Target creature can't attack or block this turn";
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

}