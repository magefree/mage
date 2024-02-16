package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author xenohedron
 */
public enum AttachedPermanentPowerCount implements DynamicValue {
    instance;


    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent attachmentPermanent = game.getPermanent(sourceAbility.getSourceId());
        if (attachmentPermanent == null) {
            attachmentPermanent = (Permanent) game.getLastKnownInformation(sourceAbility.getSourceId(), Zone.BATTLEFIELD, sourceAbility.getSourceObjectZoneChangeCounter());
        }
        if (attachmentPermanent == null || attachmentPermanent.getAttachedTo() == null) {
            return 0;
        }
        Permanent attached;
        if (effect.getValue("attachedTo") instanceof Permanent) {
            // This way is needed to obtain correct LKI (e.g. Persist)
            attached = (Permanent) effect.getValue("attachedTo");
        } else {
            attached = game.getPermanentOrLKIBattlefield(attachmentPermanent.getAttachedTo());
        }
        if (attached != null && attached.getPower().getValue() >= 0) {
            return attached.getPower().getValue();
        }
        return 0;
    }

    @Override
    public AttachedPermanentPowerCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "its power";
    }
}
