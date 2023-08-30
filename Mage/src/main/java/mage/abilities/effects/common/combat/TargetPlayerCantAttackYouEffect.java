package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public class TargetPlayerCantAttackYouEffect extends RestrictionEffect {

    public TargetPlayerCantAttackYouEffect(Duration duration) {
        super(duration);
    }

    protected TargetPlayerCantAttackYouEffect(final TargetPlayerCantAttackYouEffect effect) {
        super(effect);
    }

    @Override
    public TargetPlayerCantAttackYouEffect copy() {
        return new TargetPlayerCantAttackYouEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getTargets(game, source).contains(permanent.getControllerId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        // A planeswalker/battle controlled by the defender, not affected by the effect
        if (game.getPermanent(defenderId) != null) {
            return true;
        }
        // The controller is the defender
        return !defenderId.equals(source.getControllerId());
    }
}
