package mage.abilities.condition.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

/**
 * @author Jeff
 */
public enum EnchantedTargetCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        for (Target target : source.getTargets()) {
            if (target != null) {
                Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
                if (targetPermanent != null) {
                    for (UUID uuid : targetPermanent.getAttachments()) {
                        Permanent attached = game.getBattlefield().getPermanent(uuid);
                        if (attached != null && attached.isEnchantment(game)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
