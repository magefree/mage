

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

    protected BlocksIfAbleTargetEffect(final BlocksIfAbleTargetEffect effect) {
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
        return getTargetPointer().describeTargets(mode.getTargets(), "it") +
                (getTargetPointer().isPlural(mode.getTargets()) ? " block " : " blocks ") +
                (duration == Duration.EndOfTurn ? "this" : "each" ) +
                " turn if able";
    }

}
