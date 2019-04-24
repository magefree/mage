

package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */

public class CanBlockOnlyFlyingEffect extends RestrictionEffect {


    public CanBlockOnlyFlyingEffect(Duration duration) {
        super(duration);
        this.staticText = "{this} can block only creatures with flying";
    }

    public CanBlockOnlyFlyingEffect(final CanBlockOnlyFlyingEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return attacker.getAbilities().contains(FlyingAbility.getInstance());
    }

    @Override
    public CanBlockOnlyFlyingEffect copy() {
        return new CanBlockOnlyFlyingEffect(this);
    }

}
