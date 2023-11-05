package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public enum AttachedToTappedCondition implements Condition {
    TAPPED(true),
    UNTAPPED(false);
    private final boolean tapped;

    AttachedToTappedCondition(boolean tapped) {
        this.tapped = tapped;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment == null || attachment.getAttachedTo() == null) {
            return false;
        }
        Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
        if (attachedTo == null) {
            return false;
        }
        return attachedTo.isTapped() == this.tapped;
    }
}
