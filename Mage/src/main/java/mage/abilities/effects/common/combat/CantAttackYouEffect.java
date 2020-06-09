package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class CantAttackYouEffect extends RestrictionEffect {

    UUID controllerId;

    public CantAttackYouEffect(Duration duration) {
        super(duration);
    }

    public CantAttackYouEffect(Duration duration, UUID controllerId) {
        super(duration);
        this.controllerId = controllerId;
    }

    public CantAttackYouEffect(final CantAttackYouEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
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
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        if (controllerId == null) {
            controllerId = source.getControllerId();
        }
        return !defenderId.equals(controllerId);
    }
}
