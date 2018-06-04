
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * Describes condition when creature is equipped with more than one Equipment.
 *
 * @author Saga
 */
public enum EquippedMultipleSourceCondition implements Condition {

   instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        int countEquipped = 0;
        if (permanent != null) {
            for (UUID uuid : permanent.getAttachments()) {
                Permanent attached = game.getBattlefield().getPermanent(uuid);
                if (attached != null && attached.hasSubtype(SubType.EQUIPMENT, game)) {
                    countEquipped++;
                    if (countEquipped >= 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "has multiple Equipments attached";
    }

}