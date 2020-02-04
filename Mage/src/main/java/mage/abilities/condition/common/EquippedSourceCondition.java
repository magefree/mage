
package mage.abilities.condition.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Describes condition when creature is equipped.
 *
 * @author nantuko
 */
public enum EquippedSourceCondition implements Condition {

   instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            for (UUID uuid : permanent.getAttachments()) {
                Permanent attached = game.getBattlefield().getPermanent(uuid);
                if (attached != null && attached.hasSubtype(SubType.EQUIPMENT, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "equipped";
    }

}
