

package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author magenoxx_at_googlemail.com
 */
public class BlocksIfAbleTargetEffect extends RequirementEffect {

    public BlocksIfAbleTargetEffect(Duration duration) {
        super(duration);
    }

    public BlocksIfAbleTargetEffect(final BlocksIfAbleTargetEffect effect) {
        super(effect);
    }

    @Override
    public BlocksIfAbleTargetEffect copy() {
        return new BlocksIfAbleTargetEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public boolean mustBlockAny(Game game) {
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (this.duration == Duration.EndOfTurn) {
            return "target " + mode.getTargets().get(0).getTargetName() + " blocks this turn if able";
        }
        else {
            return "target " + mode.getTargets().get(0).getTargetName() + " blocks each turn if able";
        }
    }

}