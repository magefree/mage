
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes condition when equipped permanent has subType
 *
 * @author nantuko
 */
public class EquippedHasSubtypeCondition implements Condition {

    private final List<SubType> subTypes = new ArrayList<>(); // scope = Any

    public EquippedHasSubtypeCondition(SubType... subTypes) {
        for (SubType subType : subTypes) {
            this.subTypes.add(subType);
        }
    }


    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent == null || permanent.getAttachedTo() == null) {
            return false;
        }
        Permanent attachedTo = game.getBattlefield().getPermanent(permanent.getAttachedTo());
        if (attachedTo == null) {
            attachedTo = (Permanent) game.getLastKnownInformation(permanent.getAttachedTo(), Zone.BATTLEFIELD);
        }
        if (attachedTo == null) {
            return false;
        }
        for (SubType s : subTypes) {
            if (attachedTo.hasSubtype(s, game)) {
                return true;
            }
        }
        return false;
    }
}
