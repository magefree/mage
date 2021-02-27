

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public enum AttachedToTappedCondition implements Condition {
    instance;


    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachment = source.getSourcePermanentIfItStillExists(game);
        if (attachment == null || attachment.getAttachedTo() == null) {
            return false;
        }
        Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
        if (attachedTo == null) {
            return false;
        }
        return attachedTo.isTapped();
    }
}
