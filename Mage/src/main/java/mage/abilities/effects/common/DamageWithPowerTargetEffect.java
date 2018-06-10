
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class DamageWithPowerTargetEffect extends OneShotEffect {

    public DamageWithPowerTargetEffect() {
        super(Outcome.Damage);
        staticText = "Target creature you control deals damage equal to its power to target creature you don't control";
    }

    public DamageWithPowerTargetEffect(final DamageWithPowerTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent controlledCreature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        Permanent targetCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (controller != null) {
            if (targetCreature != null && controlledCreature != null) {
                targetCreature.damage(controlledCreature.getPower().getValue(), controlledCreature.getId(), game, false, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public DamageWithPowerTargetEffect copy() {
        return new DamageWithPowerTargetEffect(this);
    }

}
