
package mage.abilities.effects.common.combat;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public class CantAttackYouEffect extends RestrictionEffect {

    public CantAttackYouEffect(Duration duration) {
        super(duration);
    }

    public CantAttackYouEffect(final CantAttackYouEffect effect) {
        super(effect);
    }

    @Override
    public CantAttackYouEffect copy() {
        return new CantAttackYouEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game) {
        return !defenderId.equals(source.getControllerId());
    }
}
