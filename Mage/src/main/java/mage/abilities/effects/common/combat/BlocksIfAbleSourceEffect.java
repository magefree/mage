
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class BlocksIfAbleSourceEffect extends RequirementEffect {

    public BlocksIfAbleSourceEffect(Duration duration) {
        super(duration);
        staticText = "{this} blocks each combat if able.";
    }

    protected BlocksIfAbleSourceEffect(final BlocksIfAbleSourceEffect effect) {
        super(effect);
    }

    @Override
    public BlocksIfAbleSourceEffect copy() {
        return new BlocksIfAbleSourceEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent creature = game.getPermanent(source.getSourceId());
        return creature != null && creature.getId().equals(permanent.getId());
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
}
