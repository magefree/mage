package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Quercitron
 */
public class AttachedCondition implements Condition {

    private static final AttachedCondition fInstance = new AttachedCondition();

    public static AttachedCondition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment == null || attachment.getAttachedTo() == null) {
            return false;
        }
        Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
        return (attachedTo != null);
    }
}
