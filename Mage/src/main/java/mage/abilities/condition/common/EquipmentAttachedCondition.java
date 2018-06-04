

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Describes condition when Equipment is attached to an object
 *
 * @author LevelX2
 */
public enum EquipmentAttachedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        return attachment != null && attachment.getAttachedTo() != null;
    }

}
